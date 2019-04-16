package me.iit.javorek2.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Worker;
import me.iit.javorek2.model.Worker.WorkerStatus;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.service.JobManagementService;
import me.iit.javorek2.service.WorkerManagementService;

@ViewScoped
@ManagedBean
public class WorkerManagementController {

	@ManagedProperty(value = "#{workerManagementServiceImpl}")
	private WorkerManagementService workerService;
	
	@ManagedProperty(value = "#{jobManagementServiceImpl}")
	private JobManagementService jobService;

	private List<Worker> workers;
	private Worker selectedWorker;
	private List<String> eligibleJobNames;


	public void setWorkerService(WorkerManagementService workerService) {
		this.workerService = workerService;
	}

	public void setJobService(JobManagementService jobService) {
		this.jobService = jobService;
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}

	public Worker getSelectedWorker() {
		return selectedWorker;
	}

	public void setSelectedWorker(Worker selectedWorker) {
		this.selectedWorker = selectedWorker;
	}

	public List<String> getEligibleJobNames() {
		return eligibleJobNames;
	}

	public void setEligibleJobNames(List<String> eligibleJobNames) {
		this.eligibleJobNames = eligibleJobNames;
	}

	@PostConstruct
	public void init() {
		fetchWorkers();
		fetchEligibleJobNames();
	}
	
	public void modifyWorker() {
		if(selectedWorker.getStatus().equals(WorkerStatus.free)) {
			selectedWorker.setCurrentJob(new Job(""));
		}
		try {
			workerService.updateWorker(selectedWorker);
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Worker cannot be saved!", "Is everything valid?"));
		}
	}
	
	public WorkerStatus[] getWorkerStatuses() {
		return WorkerStatus.values();
	}
	
	public void deleteSelectedWorker() {
		try {
			workerService.deleteWorker(selectedWorker);
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Worker cannot be deleted!", "Maybe it does not exist already.."));
		}
	}
	
	private void fetchWorkers() {
		try {
			workers = workerService.getAllWorkers();
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Workers cannot be fetched!", "Try again later.."));
		}
	}
	
	private void fetchEligibleJobNames() {
		try {
			eligibleJobNames = jobService.getAllJobs()
					.stream()
					.map(job -> job.getName())
					.collect(Collectors.toList());
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Eligible job names cannot be fetched!", "Maybe there are none.."));
			e.printStackTrace();
		}
	}
}
