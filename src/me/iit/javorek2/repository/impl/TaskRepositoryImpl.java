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
import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.Task;
import me.iit.javorek2.model.exception.DaoException;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.repository.TaskRepository;

@ApplicationScoped
@ManagedBean(name = "taskRepositoryImpl")
public class TaskRepositoryImpl implements TaskRepository {
	
	@ManagedProperty(value = "#{mariadbDao}")
	private Dao dao;

	public void setDao(Dao dao) {
		this.dao = dao;
	}

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
			preparedStatement = connection.prepareStatement("SELECT task.name, machine_type.name, task.duration FROM task INNER JOIN machine_type ON task.req_mach_type=machine_type.id " +
					"WHERE task.id IN (SELECT job_task.task FROM job_task INNER JOIN job ON job_task.job=job.id WHERE job.name=?)");
			preparedStatement.setString(1, job.getName());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Task taskToAdd = new Task();
				taskToAdd.setName(resultSet.getString(1));
				taskToAdd.setRequiredMachineType(resultSet.getString(2));
				taskToAdd.setDuration(resultSet.getInt(3));

				taskList.add(taskToAdd);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}

		return taskList;
	}

	@Override
	public void addTask(Task task) throws RepositoryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTask(Task task) throws RepositoryException {
		//TODO: DONT FORGET TO REMOVE FROM job_task table

	}

	@Override
	public void updateDuration(Task task) throws RepositoryException {
		// TODO Auto-generated method stub

	}

}
