package me.iit.javorek2.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.dao.Dao;
import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.DaoException;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.repository.JobRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class JobRepositoryImpl.
 */
@ApplicationScoped
@ManagedBean(name = "jobRepositoryImpl")
public class JobRepositoryImpl implements JobRepository {
	
	/** The dao. */
	@ManagedProperty(value = "#{mariadbDao}")
	private Dao dao;

	/**
	 * Sets the dao.
	 *
	 * @param dao the new dao
	 */
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.JobRepository#getJobsWithoutTasks()
	 */
	@Override
	public List<Job> getJobsWithoutTasks() throws RepositoryException {
		List<Job> jobs = new ArrayList<>();

		Connection connection = null;
		Statement statement;
		ResultSet resultSet;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT name FROM job");

			while (resultSet.next()) {
				Job jobToAdd = new Job();
				jobToAdd.setName(resultSet.getString(1));
				jobs.add(jobToAdd);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}

		return jobs;
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.JobRepository#addJob(me.iit.javorek2.model.Job)
	 */
	@Override
	public void addJob(Job job) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("INSERT INTO job (name) VALUES (?)");
			preparedStatement.setString(1, job.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.JobRepository#addTaskUnderJob(me.iit.javorek2.model.Task, me.iit.javorek2.model.Job)
	 */
	@Override
	public void addTaskUnderJob(Task task, Job job) throws RepositoryException {
		Connection connection = null;
		ResultSet resultSet;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("SELECT name FROM task WHERE name=?");
			preparedStatement.setString(1, task.getName());
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				preparedStatement = connection.prepareStatement("INSERT INTO task (name, req_mach_type, duration) VALUES "
						+ "(?, (SELECT id FROM machine_type WHERE name = ?), ?)");
				preparedStatement.setString(1, task.getName());
				preparedStatement.setString(2, task.getRequiredMachineType());
				preparedStatement.setInt(3, task.getDuration());
				preparedStatement.executeUpdate();
			} else {
				throw new RepositoryException("Task with same name already exists.");
			}
			
			preparedStatement = connection.prepareStatement("INSERT INTO job_task (job, task) VALUES ((SELECT id FROM job WHERE name=?), "
														+ "(SELECT id FROM task WHERE name=?))");
			preparedStatement.setString(1, job.getName());
			preparedStatement.setString(2, task.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.JobRepository#removeTaskUnderJob(me.iit.javorek2.model.Task, me.iit.javorek2.model.Job)
	 */
	@Override
	public void deleteTaskUnderJob(Task task, Job job) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("DELETE FROM job_task WHERE (job=(SELECT id FROM job WHERE name=?)) AND (task=(SELECT id FROM task WHERE name=?))");
			preparedStatement.setString(1, job.getName());
			preparedStatement.setString(2, task.getName());
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement("SELECT id FROM job_task WHERE task=(SELECT id FROM task WHERE name=?)");
			preparedStatement.setString(1, task.getName());
			resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next()) {
				preparedStatement = connection.prepareStatement("DELETE FROM task WHERE name=?");
				preparedStatement.setString(1, task.getName());
				preparedStatement.executeUpdate();
			}
			
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.JobRepository#deleteJob(me.iit.javorek2.model.Job)
	 */
	@Override
	public void deleteJob(Job job) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("DELETE FROM job_task WHERE job=(SELECT id FROM job WHERE name=?)");
			preparedStatement.setString(1, job.getName());
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement("DELETE FROM job WHERE name=?");
			preparedStatement.setString(1, job.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

}
