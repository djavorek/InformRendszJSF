package me.iit.javorek2.repository;

import java.util.List;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.RepositoryException;

public interface TaskRepository {

	List<Task> getTasksForJob(Job job) throws RepositoryException;
	void addTask(Task task) throws RepositoryException;
	void deleteTask(Task task) throws RepositoryException;
	void updateDuration(Task task) throws RepositoryException;
}
