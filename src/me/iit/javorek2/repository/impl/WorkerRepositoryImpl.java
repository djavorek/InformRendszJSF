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
import me.iit.javorek2.model.Worker;
import me.iit.javorek2.model.exception.DaoException;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.repository.WorkerRepository;

/**
 * The Class WorkerRepositoryImpl.
 */
@ApplicationScoped
@ManagedBean(name = "workerRepositoryImpl")
public class WorkerRepositoryImpl implements WorkerRepository {
	
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
	 * @see me.iit.javorek2.repository.WorkerRepository#getWorker(me.iit.javorek2.model.Worker)
	 */
	@Override
	public Worker getWorker(Worker worker) throws RepositoryException {
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
			resultSet = statement.executeQuery(
					"SELECT worker.name, worker.hourlywage, job.name FROM worker INNER JOIN job "
					+ "ON worker.current_job=job.id WHERE worker.name=?");

			if (resultSet.next()) {
				return new Worker(resultSet.getString(1), resultSet.getInt(2), new Job(resultSet.getString(3)));
			}
			else {
				throw new RepositoryException("There is no worker with this name!");
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.WorkerRepository#getWorkersWithAlikeName(me.iit.javorek2.model.Worker)
	 */
	@Override
	public List<Worker> getWorkersWithAlikeName(Worker worker) throws RepositoryException {
		List<Worker> workerList = new ArrayList<>();
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
			resultSet = statement.executeQuery(
					"SELECT machine.name, machine_type.name, machine.working FROM machine INNER JOIN machine_type "
					+ "ON machine.type=machine_type.id WHERE worker.name LIKE '%?%'");

			while (resultSet.next()) {
				Worker workerToAdd = new Worker(resultSet.getString(1), resultSet.getInt(2), new Job(resultSet.getString(3)));
				workerList.add(workerToAdd);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
		return workerList;
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.WorkerRepository#addWorker(me.iit.javorek2.model.Worker)
	 */
	@Override
	public void addWorker(Worker worker) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("INSERT INTO worker (name,hourlywage) VALUES (?,?)");
			preparedStatement.setString(1, worker.getName());
			preparedStatement.setInt(2, worker.getHourlyWage());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.WorkerRepository#deleteWorker(me.iit.javorek2.model.Worker)
	 */
	@Override
	public void deleteWorker(Worker worker) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("DELETE FROM worker WHERE name=?");
			preparedStatement.setString(1, worker.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.WorkerRepository#updateWorkerJob(me.iit.javorek2.model.Worker)
	 */
	@Override
	public void updateWorkerJob(Worker worker) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			String newWorkingParam;
			
			if(worker.getCurrentJob() == null) {
				newWorkingParam = "NULL";
			}
			else {
				newWorkingParam = "(SELECT id FROM job WHERE name=?)";
			}
				
				
			preparedStatement = connection.prepareStatement("UPDATE worker SET working="+ newWorkingParam +" WHERE name=?");
			preparedStatement.setString(1, worker.getCurrentJob().getName());
			preparedStatement.setString(2, worker.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.WorkerRepository#updateWorkerWage(me.iit.javorek2.model.Worker)
	 */
	@Override
	public void updateWorkerWage(Worker worker) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
		
			preparedStatement = connection.prepareStatement("UPDATE worker SET hourlywage=? WHERE name=?");
			preparedStatement.setInt(1, worker.getHourlyWage());
			preparedStatement.setString(2, worker.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}
}
