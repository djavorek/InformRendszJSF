package me.iit.javorek2.repository;

import java.util.List;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.RepositoryException;

public interface JobRepository {
	
	List<Job> getAllJobs() throws RepositoryException;
	Job getJobByName() throws RepositoryException;
	void addJob(Job job) throws RepositoryException;
	void addTaskUnderJob(Task task, Job job) throws RepositoryException;
	void removeTaskUnderJob(Task task, Job job) throws RepositoryException;
	void deleteJob(Job job) throws RepositoryException;
}
