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
import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.DaoException;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.repository.MachineRepository;

@ApplicationScoped
@ManagedBean(name = "machineRepositoryImpl")
public class MachineRepositoryImpl implements MachineRepository {

	@ManagedProperty(value = "#{mariadbDao}")
	private Dao dao;

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@Override
	public List<Machine> getMachines() throws RepositoryException {
		List<Machine> machineList = new ArrayList<>();
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
					"SELECT machine.name, machine_type.name, machine.working FROM machine INNER JOIN machine_type ON machine.type=machine_type.id");

			while (resultSet.next()) {
				Machine machineToAdd = new Machine();
				machineToAdd.setName(resultSet.getString(1));
				machineToAdd.setType(resultSet.getString(2));
				machineToAdd.setWorking(resultSet.getBoolean(3));

				machineList.add(machineToAdd);
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}

		return machineList;
	}

	@Override
	public void addMachine(Machine machine) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("INSERT INTO machine (name,type,working) VALUES (?,"
					+ "(SELECT id FROM machine_type WHERE name = ?),?)");
			preparedStatement.setString(1, machine.getName());
			preparedStatement.setString(2, machine.getType());
			preparedStatement.setBoolean(3, machine.isWorking());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public void deleteMachine(Machine machine) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("DELETE FROM machine WHERE name=?");
			preparedStatement.setString(1, machine.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}
	
	/**
	 * Updates a machine 'WORKING' column with simply the new domain object
	 * @param machine	New domain object, its name used to find the object waiting for update,
	 * 					working parameter will be updated into the database.
	 * @throws RepositoryException
	 */
	@Override
	public void updateMachineStatus(Machine machine) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("UPDATE machine SET working=? WHERE name=?");
			preparedStatement.setString(1, machine.getName());
			preparedStatement.setBoolean(2, machine.isWorking());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

}
