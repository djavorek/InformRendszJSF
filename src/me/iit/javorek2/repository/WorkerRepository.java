package me.iit.javorek2.repository;

import java.util.List;

import me.iit.javorek2.model.Worker;
import me.iit.javorek2.model.exception.RepositoryException;

public interface WorkerRepository {

	List<Worker> getWorker(Worker worker) throws RepositoryException;
	List<Worker> getWorkersWithAlikeName(Worker worker) throws RepositoryException;
	void addWorker(Worker worker) throws RepositoryException;
	void deleteWorker(Worker worker) throws RepositoryException;
	void updateWorkerJob(Worker worker) throws RepositoryException;
	void updateWorkerWage(Worker worker) throws RepositoryException;
}
