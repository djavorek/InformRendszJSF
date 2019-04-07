package me.iit.javorek2.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.service.JobManagementService;

@ViewScoped
@ManagedBean
public class JobManagementController {

	@ManagedProperty(value = "#{jobManagementServiceImpl}")
	private JobManagementService service;
	
	private TreeNode jobTreeRoot;
	private List<Job> availableJobs;

	public void setService(JobManagementService service) {
		this.service = service;
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

	@PostConstruct
	public void init() {
		buildJobTree();
	}
	
	public void addTaskUnderJob(String taskName, String jobName) {
		Task taskToAdd = new Task();
		taskToAdd.setName(taskName);
		
		try {
			service.addTaskUnderJob(taskToAdd, new Job(jobName));
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("addTask", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Task cannot be added!", "Maybe it exists already."));		}
		refreshAvailableJobs();
	}
	
    private void buildJobTree() {
        jobTreeRoot = new DefaultTreeNode("Root", null);
        refreshAvailableJobs();
        
        for(Job job : availableJobs) {
        	TreeNode jobNode = new DefaultTreeNode(job, jobTreeRoot);
        	
        	for (Task task : job.getTasks()) {
        		new DefaultTreeNode(task, jobNode);
        	}
        }
    }
    
    private void refreshAvailableJobs() {
        try {
			availableJobs = service.getAllJobs();
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("general", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Job list cannot be fetched!", "Try again later.."));
		}
    }
	
}
