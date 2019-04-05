package me.iit.javorek2.repository.impl;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.dao.Dao;
import me.iit.javorek2.model.Worker;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.repository.WorkerRepository;

@ApplicationScoped
@ManagedBean(name = "workerRepositoryImpl")
public class WorkerRepositoryImpl implements WorkerRepository {
	
	@ManagedProperty(value = "#{mariadbDao}")
	private Dao dao;

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@Override
	public List<Worker> getWorker(Worker worker) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Worker> getWorkersWithAlikeName(Worker worker) throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addWorker(Worker worker) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteWorker(Worker worker) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateWorkerJob(Worker worker) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateWorkerWage(Worker worker) throws RepositoryException {
		// TODO Auto-generated method stub

	}

}
