package me.iit.javorek2.service.impl;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.model.Worker;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.model.exception.ServiceException;
import me.iit.javorek2.repository.WorkerRepository;
import me.iit.javorek2.service.JobManagementService;
import me.iit.javorek2.service.WorkerManagementService;

@ApplicationScoped
@ManagedBean(name="workerManagementServiceImpl")
public class WorkerManagementServiceImpl implements WorkerManagementService {

	@ManagedProperty(value="#{workerRepositoryImpl}")
	private WorkerRepository workerRepository;
	
	@ManagedProperty(value="#{jobManagementServiceImpl}")
	private JobManagementService jobService;

	public void setWorkerRepository(WorkerRepository workerRepository) {
		this.workerRepository = workerRepository;
	}


	public void setJobService(JobManagementService jobService) {
		this.jobService = jobService;
	}


	@Override
	public List<Worker> getAllWorkers() throws ServiceException {
		List<Worker> workers = Collections.emptyList();
		
		try {
			workers =  workerRepository.getAllWorkers();
			for (Worker worker : workers) {
				// TODO: Is it okay like this?
				jobService.fillJobWithItsTasks(worker.getCurrentJob());
			}
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
		
		return workers;
	}

	@Override
	public void addWorker(Worker workerToAdd) throws ServiceException {
		try {
			workerRepository.addWorker(workerToAdd);
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void deleteWorker(Worker workerToDelete) throws ServiceException {
		try {
			workerRepository.deleteWorker(workerToDelete);
		} catch (RepositoryException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateWorker(Worker updatingWorker) throws ServiceException {
		Worker oldWorker = null;
		
		try {
			oldWorker = workerRepository.getWorker(updatingWorker);
		} catch (RepositoryException e) {
			throw new ServiceException("Updatable worker is not available for some case..", e);
		}
		
		// Bad design, but I don't care anymore, collect BMs next time
		try {
			if(oldWorker.getHourlyWage() != updatingWorker.getHourlyWage()) {
				workerRepository.updateWorkerWage(updatingWorker);
			}
			if(!oldWorker.getCurrentJob().getName().equals(updatingWorker.getCurrentJob().getName())) {
				workerRepository.updateWorkerJob(updatingWorker);
			}
		} catch (RepositoryException e) {
			throw new ServiceException("Updating worker was unsuccessful", e);
		}
	}
}
