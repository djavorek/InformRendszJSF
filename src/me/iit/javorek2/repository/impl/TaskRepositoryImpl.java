package me.iit.javorek2.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.dao.Dao;
import me.iit.javorek2.model.Job;
import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.DaoException;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.repository.TaskRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskRepositoryImpl.
 */
@ApplicationScoped
@ManagedBean(name = "taskRepositoryImpl")
public class TaskRepositoryImpl implements TaskRepository {
	
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
	 * @see me.iit.javorek2.repository.TaskRepository#getTasksForJob(me.iit.javorek2.model.Job)
	 */
	@Override
	public List<Task> getTasksForJob(Job job) throws RepositoryException {
		List<Task> taskList = new ArrayList<>();
		Connection connection = null;

		PreparedStatement preparedStatement;
		ResultSet resultSet;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("SELECT task.name, machine_type.name, task.duration, machine.name FROM task "
					+ "INNER JOIN machine_type ON task.req_mach_type=machine_type.id "
					+ "LEFT JOIN machine ON task.executor=machine.id "
					+ "WHERE task.id IN (SELECT job_task.task FROM job_task INNER JOIN job ON job_task.job=job.id "
					+ "WHERE job.name=?)");
			preparedStatement.setString(1, job.getName());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Task taskToAdd = new Task(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), new Machine(resultSet.getString(4), resultSet.getString(2)));
				taskList.add(taskToAdd);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}

		return taskList;
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.TaskRepository#addTask(me.iit.javorek2.model.Task)
	 */
	@Override
	public void addTask(Task task) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("INSERT INTO task (name, req_mach_type, duration) VALUES "
					+ "(?, (SELECT id FROM machine_type WHERE name = ?), ?)");
			preparedStatement.setString(1, task.getName());
			preparedStatement.setString(2, task.getRequiredMachineType());
			preparedStatement.setInt(3, task.getDuration());
			preparedStatement.setString(4, task.getExecutor().getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.TaskRepository#deleteTask(me.iit.javorek2.model.Task)
	 */
	@Override
	public void deleteTask(Task task) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("DELETE FROM task WHERE name=?");
			preparedStatement.setString(1, task.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.TaskRepository#updateDuration(me.iit.javorek2.model.Task)
	 */
	@Override
	public void updateDuration(Task task) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("UPDATE task SET duration=? WHERE name=?");
			preparedStatement.setInt(1, task.getDuration());
			preparedStatement.setString(2, task.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

}
