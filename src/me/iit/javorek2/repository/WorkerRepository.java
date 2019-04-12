package me.iit.javorek2.repository;

import java.util.List;

import me.iit.javorek2.model.Worker;
import me.iit.javorek2.model.exception.RepositoryException;

// TODO: Auto-generated Javadoc
/**
 * The Interface WorkerRepository.
 */
public interface WorkerRepository {
	
	/**
	 * Gets the all workers.
	 *
	 * @return the all workers
	 * @throws RepositoryException the repository exception
	 */
	List<Worker> getAllWorkers() throws RepositoryException;

	/**
	 * Gets the worker.
	 *
	 * @param worker Containing only the name
	 * @return Filled worker object
	 * @throws RepositoryException the repository exception
	 */
	Worker getWorker(Worker worker) throws RepositoryException;
	
	/**
	 * Gets the workers with alike name.
	 *
	 * @param worker Containing only a part of the name
	 * @return the workers with alike name
	 * @throws RepositoryException the repository exception
	 */
	List<Worker> getWorkersWithAlikeName(Worker worker) throws RepositoryException;
	
	/**
	 * Adds the worker.
	 *
	 * @param worker the worker
	 * @throws RepositoryException the repository exception
	 */
	void addWorker(Worker worker) throws RepositoryException;
	
	/**
	 * Delete worker.
	 *
	 * @param worker the worker
	 * @throws RepositoryException the repository exception
	 */
	void deleteWorker(Worker worker) throws RepositoryException;
	
	/**
	 * Update worker job.
	 *
	 * @param worker the worker
	 * @throws RepositoryException the repository exception
	 */
	void updateWorkerJob(Worker worker) throws RepositoryException;
	
	/**
	 * Update worker wage.
	 *
	 * @param worker the worker
	 * @throws RepositoryException the repository exception
	 */
	void updateWorkerWage(Worker worker) throws RepositoryException;
}
