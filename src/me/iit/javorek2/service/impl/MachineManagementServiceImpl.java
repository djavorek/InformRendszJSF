package me.iit.javorek2.service.impl;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.repository.MachineRepository;
import me.iit.javorek2.repository.MachineTypeRepository;
import me.iit.javorek2.service.MachineManagementService;

@ApplicationScoped
@ManagedBean(name="machineManagementServiceImpl")
public class MachineManagementServiceImpl implements MachineManagementService {
	
	@ManagedProperty(value="#{machineRepositoryImpl}")
	private MachineRepository machineRepository;
	
	@ManagedProperty(value="#{machineTypeRepositoryImpl}")
	private MachineTypeRepository machineTypeRepository;
	
	public void setMachineRepository(MachineRepository machineRepository) {
		this.machineRepository = machineRepository;
	}

	public void setMachineTypeRepository(MachineTypeRepository machineTypeRepository) {
		this.machineTypeRepository = machineTypeRepository;
	}

	@Override
	public List<Machine> getAllMachines() throws ServiceException {
		try {
			return machineRepository.getMachines();
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<String> getPossibleMachineTypes() throws ServiceException {
		try {
			return machineTypeRepository.getMachineTypes();
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void addMachine(Machine machine) throws ServiceException {
		try {
			machineRepository.addMachine(machine);
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void addMachineType(String typeToAdd) throws ServiceException {
		try {
			machineTypeRepository.addMachineType(typeToAdd);
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteMachines(List<Machine> machinesToDelete) throws ServiceException {
		try {
			for (Machine machine : machinesToDelete) {
				machineRepository.deleteMachine(machine);
			}
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteMachineType(String typeToDelete) throws ServiceException {
		try {
			machineTypeRepository.deleteMachineType(typeToDelete);;
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}
}
