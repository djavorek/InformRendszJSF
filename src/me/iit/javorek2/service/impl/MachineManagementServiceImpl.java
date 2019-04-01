package me.iit.javorek2.service.impl;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.repository.MachineRepository;
import me.iit.javorek2.service.MachineManagementService;

@ManagedBean(name="machineManagementServiceImpl")
public class MachineManagementServiceImpl implements MachineManagementService {
	
	@ManagedProperty(value="#{machineRepositoryImpl}")
	private MachineRepository machineRepository;
	
	public void setMachineRepository(MachineRepository machineRepository) {
		this.machineRepository = machineRepository;
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
	public void addMachine(Machine machine) throws ServiceException {
		
	}

	@Override
	public void deleteMachine(Machine machine) throws ServiceException {
		// TODO Auto-generated method stub

	}
}
