package me.iit.javorek2.service;

import java.util.List;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.ServiceException;

public interface JobManagementService {
	
	List<Job> getAllJobs() throws ServiceException;
	void addJob(Job job) throws ServiceException;
	void addTaskUnderJob(Task task, Job job) throws ServiceException;
	void deleteTaskUnderJob(Task task, Job job) throws ServiceException;
	void deleteJob(Job job) throws ServiceException;
	void updateTask(Task task) throws ServiceException;
	
}
