package me.iit.javorek2.repository;

import java.util.List;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.RepositoryException;

// TODO: Auto-generated Javadoc
/**
 * The Interface JobRepository.
 */
public interface JobRepository {
	
	/**
	 * Gets the jobs without tasks.
	 *
	 * @return the jobs without tasks
	 * @throws RepositoryException the repository exception
	 */
	List<Job> getJobsWithoutTasks() throws RepositoryException;
	
	/**
	 * Adds the job.
	 *
	 * @param job the job
	 * @throws RepositoryException the repository exception
	 */
	void addJob(Job job) throws RepositoryException;
	
	/**
	 * Adds the task under job.
	 *
	 * @param task the task
	 * @param job the job
	 * @throws RepositoryException the repository exception
	 */
	void addTaskUnderJob(Task task, Job job) throws RepositoryException;
	
	/**
	 * Removes the task under job.
	 *
	 * @param task the task
	 * @param job the job
	 * @throws RepositoryException the repository exception
	 */
	void deleteTaskUnderJob(Task task, Job job) throws RepositoryException;
	
	/**
	 * Delete job.
	 *
	 * @param job the job
	 * @throws RepositoryException the repository exception
	 */
	void deleteJob(Job job) throws RepositoryException;
}
