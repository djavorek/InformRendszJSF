package me.iit.javorek2.service;

import java.util.List;

import me.iit.javorek2.model.Worker;
import me.iit.javorek2.model.exception.ServiceException;

public interface WorkerManagementService {
	List<Worker> getAllWorkers() throws ServiceException;
	void addWorker(Worker workerToAdd) throws ServiceException;
	void deleteWorker(Worker workerToDelete) throws ServiceException;
	void updateWorker(Worker updatingWorker) throws ServiceException;
}
