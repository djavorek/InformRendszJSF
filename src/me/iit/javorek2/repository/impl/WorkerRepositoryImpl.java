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
import me.iit.javorek2.model.Worker.WorkerStatus;
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
	

	@Override
	public List<Worker> getAllWorkers() throws RepositoryException {
		List<Worker> workers = new ArrayList<>();

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
			resultSet = statement.executeQuery("SELECT worker.name, worker.qualification, worker.hourlywage, job.name, worker.status FROM worker "
					+ "LEFT JOIN job ON worker.current_job=job.id");

			while (resultSet.next()) {
				Worker workerToAdd = new Worker(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
				Job workersJob = new Job(resultSet.getString(4));
				workerToAdd.setCurrentJob(workersJob);
				workerToAdd.setStatus(WorkerStatus.valueOf(resultSet.getString(5)));
				
				workers.add(workerToAdd);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}

		return workers;
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.WorkerRepository#getWorker(me.iit.javorek2.model.Worker)
	 */
	@Override
	public Worker getWorker(Worker worker) throws RepositoryException {
		Connection connection = null;

		PreparedStatement preparedStatement;
		ResultSet resultSet;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("SELECT worker.name, worker.qualification, worker.hourlywage, job.name, worker.status "
					+ "FROM worker LEFT JOIN job ON worker.current_job=job.id WHERE worker.name=?");
			preparedStatement.setString(1, worker.getName());
			resultSet = preparedStatement.executeQuery();
					

			if (resultSet.next()) {
				return new Worker(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), 
						new Job(resultSet.getString(4)), Worker.WorkerStatus.valueOf(resultSet.getString(5)));
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

		PreparedStatement preparedStatement;
		ResultSet resultSet;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement(" worker.name, worker.qualification, worker.hourlywage, job.name, worker.status FROM "
					+ "worker LEFT JOIN job ON worker.current_job=job.id WHERE worker.name LIKE '%?%'");
			resultSet = preparedStatement.executeQuery();
					

			while (resultSet.next()) {
				Worker workerToAdd = new Worker(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), 
						new Job(resultSet.getString(4)), Worker.WorkerStatus.valueOf(resultSet.getString(5)));
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
			preparedStatement = connection.prepareStatement("INSERT INTO worker (name, qualification, hourlywage, current_job, status) "
					+ "VALUES (?, ?, ?, (SELECT id FROM job WHERE name = ?), ?)");
			preparedStatement.setString(1, worker.getName());
			preparedStatement.setString(2, worker.getQualification());
			preparedStatement.setInt(3, worker.getHourlyWage());
			preparedStatement.setString(4, worker.getCurrentJob().getName());
			preparedStatement.setString(5, worker.getStatus().getCode());
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
			preparedStatement = connection.prepareStatement("UPDATE worker SET working=(SELECT id FROM job WHERE name=?) WHERE name=?");
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
