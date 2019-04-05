package me.iit.javorek2.repository;

import java.util.List;

import me.iit.javorek2.model.exception.RepositoryException;

public interface MachineTypeRepository {

	List<String> getMachineTypes() throws RepositoryException;
	void addMachineType(String type) throws RepositoryException;
	void deleteMachineType(String type) throws RepositoryException;
}
