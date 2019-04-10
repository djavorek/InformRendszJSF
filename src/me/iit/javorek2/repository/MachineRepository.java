package me.iit.javorek2.repository;

import java.util.List;

import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.RepositoryException;

// TODO: Auto-generated Javadoc
/**
 * The Interface MachineRepository.
 */
public interface MachineRepository {

	/**
	 * Gets the machines.
	 *
	 * @return the machines
	 * @throws RepositoryException the repository exception
	 */
	List<Machine> getMachines() throws RepositoryException;
	
	/**
	 * Gets the machine by name.
	 *
	 * @param name the name
	 * @return the machine by name
	 * @throws RepositoryException the repository exception
	 */
	Machine getMachineByName(String name) throws RepositoryException;
	
	/**
	 * Adds the machine.
	 *
	 * @param machine the machine
	 * @throws RepositoryException the repository exception
	 */
	void addMachine(Machine machine) throws RepositoryException;
	
	/**
	 * Delete machine.
	 *
	 * @param machine the machine
	 * @throws RepositoryException the repository exception
	 */
	void deleteMachine(Machine machine) throws RepositoryException;
	
	/**
	 * Updates a machine 'WORKING' column with simply the new domain object.
	 *
	 * @param machine New domain object, its name used to find the object waiting for update,
	 * 					working parameter will be updated into the database.
	 * @throws RepositoryException the repository exception
	 */
	void updateMachineStatus(Machine machine) throws RepositoryException;
}