package me.iit.javorek2.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import me.iit.javorek2.service.WorkerManagementService;

@ViewScoped
@ManagedBean
public class WorkerManagementController {

	@ManagedProperty(value = "#{workerManagementServiceImpl}")
	private WorkerManagementService service;

	public void setService(WorkerManagementService service) {
		this.service = service;
	}	

}
