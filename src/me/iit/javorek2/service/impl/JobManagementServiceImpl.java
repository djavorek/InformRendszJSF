package me.iit.javorek2.service.impl;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.repository.JobRepository;
import me.iit.javorek2.repository.TaskRepository;
import me.iit.javorek2.service.JobManagementService;

@ApplicationScoped
@ManagedBean(name="jobManagementServiceImpl")
public class JobManagementServiceImpl implements JobManagementService {

	@ManagedProperty(value="#{jobRepositoryImpl}")
	private JobRepository jobRepository;
	
	@ManagedProperty(value="#{taskRepositoryImpl}")
	private TaskRepository taskRepository;
	
	public void setJobRepository(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public List<Job> getAllJobs() throws ServiceException {
		List<Job> jobs = Collections.emptyList();
		
		try {
			jobs =  jobRepository.getJobsWithoutTasks();
			for (Job job : jobs) {
				job.setTasks(taskRepository.getTasksForJob(job));
			}
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
		
		return jobs;
	}

	@Override
	public void addTaskUnderJob(Task task, Job job) throws ServiceException {
		try {
			jobRepository.addTaskUnderJob(task, job);
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}
}
