package me.iit.javorek2.controller;

import java.util.Collections;
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
	private List<String> eligibleMachineNamesForAdd;
	private List<String> eligibleMachineNamesForModify;
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

	public List<String> getEligibleMachineNamesForAdd() {
		return eligibleMachineNamesForAdd;
	}

	public void setEligibleMachineNamesForAdd(List<String> eligibleMachineNamesForAdd) {
		this.eligibleMachineNamesForAdd = eligibleMachineNamesForAdd;
	}

	public List<String> getEligibleMachineNamesForModify() {
		return eligibleMachineNamesForModify;
	}

	public void setEligibleMachineNamesForModify(List<String> eligibleMachineNamesForModify) {
		this.eligibleMachineNamesForModify = eligibleMachineNamesForModify;
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

		// Initializing a dummy task, for valid xhtml, will change it surely later on.
		taskToView = new Task("", "", 0, new Machine());
	}

	public void addJob(String jobName) {
		Job jobToAdd = new Job(jobName);

		try {
			jobService.addJob(jobToAdd);
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Job added successfully!", ""));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Job cannot be added!", "Maybe it exists already."));
			e.printStackTrace();
		}
		refreshAvailableJobs();
	}

	public void addTaskUnderJob(String taskName, String taskDuration, String requiredMachineType, String executor,
			String jobName) {
		Task taskToAdd = new Task();
		taskToAdd.setName(taskName);
		taskToAdd.setDuration(Integer.parseInt(taskDuration));
		taskToAdd.setRequiredMachineType(requiredMachineType);

		if (!executor.equals("")) {
			try {
				taskToAdd.setExecutor(machineService.getMachineByName(executor));
			} catch (ServiceException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("messages",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Task cannot be added!", "It looks invalid."));
			}
		}

		try {
			jobService.addTaskUnderJob(taskToAdd, new Job(jobName));
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Task added successfully!", ""));
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Task cannot be added!", "Maybe it exists already."));
		}
		refreshAvailableJobs();
	}

	public void deleteSelectedNode() {
		Object dataObject = selectedJobTaskNode.getData();

		if (dataObject.getClass() == Task.class) {
			try {
				jobService.deleteTaskUnderJob((Task) dataObject, (Job) selectedJobTaskNode.getParent().getData());
				FacesContext.getCurrentInstance().addMessage("messages",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Task deleted successfully!", ""));
			} catch (ServiceException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("messages",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Task cannot be deleted!", "Try again later.."));
			}
		} else if (selectedJobTaskNode.getData() instanceof Job) {
			try {
				jobService.deleteJob((Job) dataObject);
				FacesContext.getCurrentInstance().addMessage("messages",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Job deleted successfully!", ""));
			} catch (ServiceException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Job cannot be deleted!", "Maybe it is used somewhere."));
			}
		}

		refreshAvailableJobs();
	}

	public void modifySelectedNode() {
		Object dataObject = selectedJobTaskNode.getData();

		if (dataObject.getClass() == Task.class) {
			Task selectedTask = (Task)dataObject;
			taskToView = selectedTask;
			refreshEligibleMachinesForModify(taskToView.getRequiredMachineType());
			taskToViewSelected = true;
		} else if (selectedJobTaskNode.getData() instanceof Job) {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Jobs cannot be modified!", "Choose a task next time."));
		}
	}

	public void saveViewedTask() {
		try {
			// Setting the real machine object to executor, instead of its name
			if (taskToView.getExecutor() != null && taskToView.getExecutor().getName() != null
					&& !"".equals(taskToView.getExecutor().getName())) {
				taskToView.setExecutor(machineService.getMachineByName(taskToView.getExecutor().getName()));
			}
			jobService.updateTask(taskToView);
			taskToViewSelected = false;
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Task saved successfully!", ""));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Task cannot be saved!", "Did you select a proper machine?"));
			e.printStackTrace();
		}
	}
	
	public void refreshEligibleMachinesForAdd(String machineType) {
		eligibleMachineNamesForAdd = refreshEligibleMachines(machineType);
	}
	
	private void refreshEligibleMachinesForModify(String machineType) {
		setEligibleMachineNamesForModify(refreshEligibleMachines(machineType));
	}	
	
	private List<String> refreshEligibleMachines(String machineType) {
		try {
			if(!"".equals(machineType)) {
				 return machineService.getFreeMachines()
						.stream()
						.filter(machine -> machine.getType().equals(machineType))
						.map(machine -> machine.getName())
						.collect(Collectors.toList());
			} else {
				return machineService.getFreeMachines()
						.stream()
						.map(machine -> machine.getName())
						.collect(Collectors.toList());
			}
			
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Proper machines cannot be fetched for type!", "Maybe there are none"));
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private void refreshAvailableJobs() {
		try {
			availableJobs = jobService.getAllJobs();
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Job list cannot be fetched!", "Try again later.."));
		}
		jobNames = availableJobs.stream().map(job -> job.getName()).collect(Collectors.toList());

		buildJobTree();
	}

	private void buildJobTree() {
		jobTreeRoot = new DefaultTreeNode("Root", null);

		for (Job job : availableJobs) {
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
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Machine type list cannot be fetched!", "Try again later.."));
		}
	}
}
