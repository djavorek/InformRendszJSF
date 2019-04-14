package me.iit.javorek2.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import me.iit.javorek2.model.Worker;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.service.WorkerManagementService;

@ViewScoped
@ManagedBean
public class WorkerManagementController {

	@ManagedProperty(value = "#{workerManagementServiceImpl}")
	private WorkerManagementService workerService;

	private List<Worker> workers;
	private Worker selectedWorker;


	public void setWorkerService(WorkerManagementService workerService) {
		this.workerService = workerService;
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

	@PostConstruct
	public void init() {
		try {
			workers = workerService.getAllWorkers();
		} catch (ServiceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("messages",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Workers cannot be fetched!", "Try again later.."));
		}
	}
	
	public void modifyWorker() {
		
	}
}
