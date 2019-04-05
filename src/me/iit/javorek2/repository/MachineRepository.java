package me.iit.javorek2.repository;

import java.util.List;

import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.RepositoryException;

public interface MachineRepository {

	List<Machine> getMachines() throws RepositoryException;
	void addMachine(Machine machine) throws RepositoryException;
	void deleteMachine(Machine machine) throws RepositoryException;
	void updateMachineStatus(Machine machine) throws RepositoryException;
}