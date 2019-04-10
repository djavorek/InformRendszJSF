package me.iit.javorek2.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.service.JobManagementService;
import me.iit.javorek2.service.MachineManagementService;

@ViewScoped
@ManagedBean
public class JobManagementController {

	@ManagedProperty(value = "#{jobManagementServiceImpl}")
	private JobManagementService jobService;
	
	@ManagedProperty(value = "#{machineManagementServiceImpl}")
	private MachineManagementService machineService;
	
	private TreeNode jobTreeRoot;
	private List<Job> availableJobs;
	private List<String> jobNames;
	private List<String> machineTypes;
	private List<String> freeMachineNames;
	private TreeNode selectedJobTaskNode;
	private Task taskToView;
	private boolean taskToViewSelected;

	public void setJobService(JobManagementService service) {
		this.jobService = service;
	}
	
	public void setMachineService(MachineManagementService machineService) {
		this.machineService = machineService;
	}
	
	public TreeNode getJobTreeRoot() {
		return jobTreeRoot;
	}
	
	public void setJobTreeRoot(TreeNode jobTreeRoot) {
		this.jobTreeRoot = jobTreeRoot;
	}
	
	public List<Job> getAvailableJobs() {
		return availableJobs;
	}

	public void setAvailableJobs(List<Job> availableJobs) {
		this.availableJobs = availableJobs;
	}

	public List<String> getJobNames() {
		return jobNames;
	}

	public void setJobNames(List<String> jobNames) {
		this.jobNames = jobNames;
	}

	public List<String> getMachineTypes() {
		return machineTypes;
	}

	public void setMachineTypes(List<String> machineTypes) {
		this.machineTypes = machineTypes;
	}

	public TreeNode getSelectedJobTaskNode() {
		return selectedJobTaskNode;
	}

	public void setSelectedJobTaskNode(TreeNode selectedJobTaskNode) {
		this.selectedJobTaskNode = selectedJobTaskNode;
	}

	public List<String> getFreeMachineNames() {
		return freeMachineNames;
	}

	public void setFreeMachineNames(List<String> freeMachineNames) {
		this.freeMachineNames = freeMachineNames;
	}


	public Task getTaskToView() {
		return taskToView;
	}

	public void setTaskToView(Task taskToView) {
		this.taskToView = taskToView;
	}

	public boolean isTaskToViewSelected() {
		return taskToViewSelected;
	}

	public void setTaskToViewSelected(boolean taskToViewSelected) {
		this.taskToViewSelected = taskToViewSelected;
	}

	@PostConstruct
	public void init() {
		refreshAvailableJobs();
		refreshAvailableMachineTypes();
		refreshFreeMachineNames();
		
		//Initializing a dummy task, for valid xhtml, will change it surely later on.
		taskToView = new Task("", "", 0, new Machine());
	}
	
	public void addJob(String jobName) {
		Job jobToAdd = new Job(jobName);
		
		try {
			jobService.addJob(jobToAdd);
			FacesContext.getCurrentInstance().addMessage("addJob", new FacesMessage(FacesMessage.SEVERITY_INFO, "Job added successfully!", ""));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("addJob", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Job cannot be added!", "Maybe it exists already."));
			e.printStackTrace();
		}
		refreshAvailableJobs();
	}

	public void addTaskUnderJob(String taskName, String taskDuration, String requiredMachineType, String executor, String jobName) {
		Task taskToAdd = new Task();
		taskToAdd.setName(taskName);
		taskToAdd.setDuration(Integer.parseInt(taskDuration));
		taskToAdd.setRequiredMachineType(requiredMachineType);
		
		if("".equals(requiredMachineType) || "".equals(jobName)) {
			FacesContext.getCurrentInstance().addMessage("addTask", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Task cannot be added", "If you dont fill the form."));
		}
		
		if (!executor.equals("")) {
			try {
				taskToAdd.setExecutor(machineService.getMachineByName(executor));
			} catch (ServiceException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("addTask", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Task cannot be added!", "It looks invalid."));
			}
		}
		
		try {
			jobService.addTaskUnderJob(taskToAdd, new Job(jobName));
			FacesContext.getCurrentInstance().addMessage("addTask", new FacesMessage(FacesMessage.SEVERITY_INFO, "Task added successfully!", ""));
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("addTask", new FacesMessage(FacesMessage.SEVERITY_WARN, "Task cannot be added!", "Maybe it exists already."));
		}
		refreshAvailableJobs();
	}
	
	public void deleteSelectedNode() {
		Object dataObject = selectedJobTaskNode.getData();
		
		if(dataObject.getClass() == Task.class) {
			try {
				jobService.deleteTaskUnderJob((Task)dataObject, (Job)selectedJobTaskNode.getParent().getData());
				FacesContext.getCurrentInstance().addMessage("general", new FacesMessage(FacesMessage.SEVERITY_INFO, "Task deleted successfully!", ""));
			} catch (ServiceException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("general", new FacesMessage(FacesMessage.SEVERITY_WARN, "Task cannot be deleted!", "Try again later.."));
			}
		} else if (selectedJobTaskNode.getData() instanceof Job) {
			try {
				jobService.deleteJob((Job)dataObject);
				FacesContext.getCurrentInstance().addMessage("general", new FacesMessage(FacesMessage.SEVERITY_INFO, "Job deleted successfully!", ""));
			} catch (ServiceException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("general", new FacesMessage(FacesMessage.SEVERITY_WARN, "Job cannot be deleted!", "Maybe it is used somewhere."));
			}
		}
		
		refreshAvailableJobs();
	}
	
	public void modifySelectedNode() {
		Object dataObject = selectedJobTaskNode.getData();
		
		if(dataObject.getClass() == Task.class) {
			taskToView = ((Task) dataObject);
			taskToViewSelected = true;
		} else if (selectedJobTaskNode.getData() instanceof Job) {
			FacesContext.getCurrentInstance().addMessage("general", new FacesMessage(FacesMessage.SEVERITY_INFO, "Jobs cannot be modified!", "Choose a task next time."));
		}
	}
	
	public void saveViewedTask() {
		try {
			// Setting the real machine object to executor, instead of its name
			if(taskToView.getExecutor() != null && taskToView.getExecutor().getName() != null && !"".equals(taskToView.getExecutor().getName())) {
				taskToView.setExecutor(machineService.getMachineByName(taskToView.getExecutor().getName()));
			}
			jobService.updateTask(taskToView);
			taskToViewSelected = false;
			FacesContext.getCurrentInstance().addMessage("modifyTask", new FacesMessage(FacesMessage.SEVERITY_INFO, "Task saved successfully!", ""));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("modifyTask", new FacesMessage(FacesMessage.SEVERITY_WARN, "Task cannot be saved!", "Did you select a proper machine?"));
			e.printStackTrace();
		}
	}
    
    private void refreshAvailableJobs() {
        try {
			availableJobs = jobService.getAllJobs();
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("general", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Job list cannot be fetched!", "Try again later.."));
		}
        jobNames = availableJobs
        		.stream()
        		.map(job -> job.getName())
        		.collect(Collectors.toList());
        
		buildJobTree();
    }
    
    private void buildJobTree() {
        jobTreeRoot = new DefaultTreeNode("Root", null);
        
        for(Job job : availableJobs) {
        	TreeNode jobNode = new DefaultTreeNode(job, jobTreeRoot);
        	
        	for (Task task : job.getTasks()) {
        		new DefaultTreeNode(task, jobNode);
        	}
        }
    }
    
    private void refreshAvailableMachineTypes() {
        try {
			machineTypes = machineService.getPossibleMachineTypes();
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("general", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Machine type list cannot be fetched!", "Try again later.."));
		}
    }
    
	private void refreshFreeMachineNames() {
        try {
			setFreeMachineNames(machineService.getFreeMachines()
					.stream()
					.map(job -> job.getName())
					.collect(Collectors.toList()));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("general", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Free machine list cannot be fetched!", "Try again later.."));
			e.printStackTrace();
		}
	}
}
