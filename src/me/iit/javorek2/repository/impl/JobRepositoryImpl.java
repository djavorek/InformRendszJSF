package me.iit.javorek2.repository.impl;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.dao.Dao;
import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.repository.JobRepository;

@ApplicationScoped
@ManagedBean(name = "jobRepositoryImpl")
public class JobRepositoryImpl implements JobRepository {
	
	@ManagedProperty(value = "#{mariadbDao}")
	private Dao dao;

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@Override
	public List<Job> getAllJobs() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Job getJobByName() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addJob(Job job) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTaskUnderJob(Task task, Job job) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTaskUnderJob(Task task, Job job) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteJob(Job job) throws RepositoryException {
		// TODO Auto-generated method stub

	}

}
