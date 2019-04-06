package me.iit.javorek2.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.service.MachineManagementService;

@ViewScoped
@ManagedBean
public class MachineManagementController {

	@ManagedProperty(value = "#{machineManagementServiceImpl}")
	private MachineManagementService service;

	private List<Machine> availableMachines;
	private List<Machine> selectedMachines;
	private List<String> possibleMachineTypes;
	private boolean deleteButtonEnabled;

	public void setService(MachineManagementService service) {
		this.service = service;
	}

	public List<Machine> getAvailableMachines() {
		return availableMachines;
	}
	
	public List<Machine> getSelectedMachines() {
		return selectedMachines;
	}

	public void setSelectedMachines(List<Machine> selectedMachines) {
		this.selectedMachines = selectedMachines;
	}

	public List<String> getPossibleMachineTypes() {
		return possibleMachineTypes;
	}

	public void setPossibleMachineTypes(List<String> possibleMachineTypes) {
		this.possibleMachineTypes = possibleMachineTypes;
	}

	public boolean isDeleteButtonEnabled() {
		return deleteButtonEnabled;
	}

	public void setDeleteButtonEnabled(boolean deleteButtonEnabled) {
		this.deleteButtonEnabled = deleteButtonEnabled;
	}

	@PostConstruct
	public void updateMachineList() {
		try {
			availableMachines = service.getAllMachines();
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("general", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Machine list cannot be fetched!", "Try again later.."));
		}
	}

	public void deleteSelectedMachines() {
		try {
			service.deleteMachines(selectedMachines);
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("machineManagement", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Machine cannot be delete!", "Maybe it is used?"));
		}
		updateMachineList();
	}

	public void addMachine(String name, String type) {
		Machine machineToAdd = new Machine(name, type);

		try {
			service.addMachine(machineToAdd);
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage("machineManagement", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Machine cannot be added!", "Check if it was valid or not."));
		}
		updateMachineList();
	}
	
	public void fetchPossibleMachineTypes() {
		try {
			possibleMachineTypes = service.getPossibleMachineTypes();
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("typeManagement", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Machine Types cannot be fetched!", "Try again later.."));
		}
	}
	
	public void addMachineType(String type) {
		try {
			service.addMachineType(type);
			fetchPossibleMachineTypes();
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("typeManagement", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Machine Type cannot be added!", "Check if it was valid or not."));
		}
	}
	
	public void deleteMachineType(String type) {
		try {
			service.deleteMachineType(type);
			fetchPossibleMachineTypes();
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage("typeManagement", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Machine Type cannot be deleted!", "Maybe it is used?"));
		}
	}
	
	public void machineSelectionListener() {
		deleteButtonEnabled = selectedMachines.size() > 0 ? true : false;
	}
}
