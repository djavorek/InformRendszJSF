package me.iit.javorek2.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.service.MachineManagementService;

@ManagedBean
public class MachineManagementController {
	
	@ManagedProperty(value="#{machineManagementServiceImpl}")
	private MachineManagementService service;
	
	public void setService(MachineManagementService service) {
		this.service = service;
	}

	public void getMachineInfo(String machineName) {
		try {
			service.getMachineInfo(machineName);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
