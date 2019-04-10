package me.iit.javorek2.repository;

import java.util.List;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.RepositoryException;

// TODO: Auto-generated Javadoc
/**
 * The Interface TaskRepository.
 */
public interface TaskRepository {

	/**
	 * Gets the tasks for job.
	 *
	 * @param job the job
	 * @return the tasks for job
	 * @throws RepositoryException the repository exception
	 */
	List<Task> getTasksForJob(Job job) throws RepositoryException;
	
	/**
	 * Gets the task by name.
	 *
	 * @param taskName the task name
	 * @return the task by name
	 * @throws RepositoryException the repository exception
	 */
	Task getTaskByName(String taskName) throws RepositoryException;
	
	/**
	 * Adds the task.
	 *
	 * @param task the task
	 * @throws RepositoryException the repository exception
	 */
	void addTask(Task task) throws RepositoryException;
	
	/**
	 * Delete task.
	 *
	 * @param task the task
	 * @throws RepositoryException the repository exception
	 */
	void deleteTask(Task task) throws RepositoryException;
	
	/**
	 * Update task.
	 *
	 * @param task the task
	 * @throws RepositoryException the repository exception
	 */
	void updateTask(Task task) throws RepositoryException;
}
