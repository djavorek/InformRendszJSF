package me.iit.javorek2.service.impl;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.repository.JobRepository;
import me.iit.javorek2.repository.TaskRepository;
import me.iit.javorek2.service.JobManagementService;
import me.iit.javorek2.service.MachineManagementService;

@ApplicationScoped
@ManagedBean(name="jobManagementServiceImpl")
public class JobManagementServiceImpl implements JobManagementService {

	@ManagedProperty(value="#{jobRepositoryImpl}")
	private JobRepository jobRepository;
	
	@ManagedProperty(value="#{taskRepositoryImpl}")
	private TaskRepository taskRepository;
	
	@ManagedProperty(value ="#{machineManagementServiceImpl}")
	private MachineManagementService machineService;
	
	public void setJobRepository(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public void setMachineService(MachineManagementService machineService) {
		this.machineService = machineService;
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
		
		if (task.getExecutor() != null && task.getRequiredMachineType() != task.getExecutor().getType()) {
			throw new ServiceException("The chosen executor is not from the required type!");
		} 
		
		try {
			jobRepository.addTaskUnderJob(task, job);
			
			if(task.getExecutor() != null && !task.getExecutor().getName().equals("")) {
				Machine machineToUpdate = new Machine();
				machineToUpdate.setName(task.getExecutor().getName());
				machineToUpdate.setWorking(true);
				machineService.updateMachineStatus(machineToUpdate);
			}
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteTaskUnderJob(Task task, Job job) throws ServiceException {
		try {
			jobRepository.deleteTaskUnderJob(task, job);
			
			if(task.getExecutor() != null && !task.getExecutor().getName().equals("")) {
				Machine machineToUpdate = new Machine();
				machineToUpdate.setName(task.getExecutor().getName());
				machineToUpdate.setWorking(false);
				machineService.updateMachineStatus(machineToUpdate);
			}
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteJob(Job job) throws ServiceException {
		try {
			jobRepository.deleteJob(job);
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}
}
