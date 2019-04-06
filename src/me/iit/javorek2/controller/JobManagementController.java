package me.iit.javorek2.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import me.iit.javorek2.service.JobManagementService;

@ViewScoped
@ManagedBean
public class JobManagementController {

	@ManagedProperty(value = "#{jobManagementServiceImpl}")
	private JobManagementService service;

	public void setService(JobManagementService service) {
		this.service = service;
	}

}
