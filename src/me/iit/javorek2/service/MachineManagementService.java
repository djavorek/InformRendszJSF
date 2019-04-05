package me.iit.javorek2.service;

import java.util.List;

import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.ServiceException;

public interface MachineManagementService {
	
	List<Machine> getAllMachines() throws ServiceException;
	List<String> getPossibleMachineTypes() throws ServiceException;
	void addMachine(Machine machineToAdd) throws ServiceException;
	void addMachineType(String typeToAdd) throws ServiceException;
	void deleteMachines(List<Machine> machinesToDelete) throws ServiceException;
	void deleteMachineType(String typeToDelete) throws ServiceException;
}
