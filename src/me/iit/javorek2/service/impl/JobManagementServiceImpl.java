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
	public void fillJobWithItsTasks(Job job) throws ServiceException {
		// Not copying the original parameter Object is generally a bad practice! 
		try {
			job.setTasks(taskRepository.getTasksForJob(job));
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void addTaskUnderJob(Task task, Job job) throws ServiceException {
		if (!isValidTask(task)) {
			throw new ServiceException("The task is not valid!");
		} 
		
		try {
			jobRepository.addTaskUnderJob(task, job);
			
			if(task.getExecutor() != null && !task.getExecutor().getName().equals("")) {
				Machine machineToUpdate = new Machine();
				machineToUpdate.setName(task.getExecutor().getName());
				machineToUpdate.setWorking(true);
				machineService.updateMachineStatus(machineToUpdate);
				updateMachineStatus(task.getExecutor().getName(), true);
			}
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteTaskUnderJob(Task task, Job job) throws ServiceException {
		try {
			jobRepository.deleteTaskUnderJob(task, job);
			
			if(task.getExecutor() != null && task.getExecutor().getName() != null) {
				updateMachineStatus(task.getExecutor().getName(), false);
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

	@Override
	public void addJob(Job job) throws ServiceException {
		try {
			jobRepository.addJob(job);
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void updateTask(Task task) throws ServiceException {
		if (!isValidTask(task)) {
			throw new ServiceException("The task is not valid!");
		}
		
		Task oldStatusOfTask = null;
		
		try {
			oldStatusOfTask = taskRepository.getTaskByName(task.getName());
			
			if (!oldStatusOfTask.getExecutor().getName().equals(task.getExecutor().getName())) {
				if(oldStatusOfTask.getExecutor() != null && oldStatusOfTask.getExecutor().getName() != null && !"".equals(oldStatusOfTask.getExecutor().getName())) {
					updateMachineStatus(oldStatusOfTask.getExecutor().getName(), false);
					updateMachineStatus(task.getExecutor().getName(), true);
				}
			}
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
		
		try {
			taskRepository.updateTask(task);
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}
	
	private boolean isValidTask(Task task) {
		if (task.getExecutor() != null && !task.getRequiredMachineType().equals(task.getExecutor().getType())) {
			return false;
		}
		return true;
	}
	
	private void updateMachineStatus(String machineName, boolean newStatus) throws ServiceException {
		Machine machineToUpdate = new Machine();
		machineToUpdate.setName(machineName);
		machineToUpdate.setWorking(newStatus);
		try {
			machineService.updateMachineStatus(machineToUpdate);
		} catch (ServiceException e) {
			throw new ServiceException("Machine status is not updateable");
		}
	}
}
