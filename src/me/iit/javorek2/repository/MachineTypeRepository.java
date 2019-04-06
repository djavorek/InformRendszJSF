package me.iit.javorek2.repository;

import java.util.List;

import me.iit.javorek2.model.exception.RepositoryException;

// TODO: Auto-generated Javadoc
/**
 * The Interface MachineTypeRepository.
 */
public interface MachineTypeRepository {

	/**
	 * Gets the machine types.
	 *
	 * @return the machine types
	 * @throws RepositoryException the repository exception
	 */
	List<String> getMachineTypes() throws RepositoryException;
	
	/**
	 * Adds the machine type.
	 *
	 * @param type the type
	 * @throws RepositoryException the repository exception
	 */
	void addMachineType(String type) throws RepositoryException;
	
	/**
	 * Delete machine type.
	 *
	 * @param type the type
	 * @throws RepositoryException the repository exception
	 */
	void deleteMachineType(String type) throws RepositoryException;
}
