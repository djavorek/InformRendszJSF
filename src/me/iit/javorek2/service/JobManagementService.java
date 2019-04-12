package me.iit.javorek2.service;

import java.util.List;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.ServiceException;

public interface JobManagementService {
	
	List<Job> getAllJobs() throws ServiceException;
	void fillJobWithItsTasks(Job job) throws ServiceException;
	void addJob(Job jobToAdd) throws ServiceException;
	void addTaskUnderJob(Task taskToAdd, Job parentJob) throws ServiceException;
	void deleteTaskUnderJob(Task taskToDelete, Job parentJob) throws ServiceException;
	void deleteJob(Job jobToDelete) throws ServiceException;
	void updateTask(Task updatingTask) throws ServiceException;
	
}
