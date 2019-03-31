package me.iit.javorek2.service;

import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.ServiceException;

public interface MachineManagementService {
	
	Machine getMachineInfo(String name) throws ServiceException;
	void addMachine(Machine machine) throws ServiceException;
	void deleteMachine(Machine machine) throws ServiceException;
}
