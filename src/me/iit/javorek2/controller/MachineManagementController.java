package me.iit.javorek2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.controller.dto.MachineWrapper;
import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.service.MachineManagementService;

@ManagedBean
public class MachineManagementController {
	
	@ManagedProperty(value="#{machineManagementServiceImpl}")
	private MachineManagementService service;
	
	private List<MachineWrapper> availableMachines = new ArrayList<>();
	
	public void setService(MachineManagementService service) {
		this.service = service;
	}
	
	public List<MachineWrapper> getAvailableMachines() {
		return availableMachines;
	}
	
	@PostConstruct
	public void updateMachineList() {
		availableMachines.clear();
		
		try {
			for (Machine machine : service.getAllMachines()) {
				availableMachines.add(new MachineWrapper(machine));
			}	
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
