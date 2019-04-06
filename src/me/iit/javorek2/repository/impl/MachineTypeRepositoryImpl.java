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
import me.iit.javorek2.model.exception.DaoException;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.repository.MachineTypeRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class MachineTypeRepositoryImpl.
 */
@ApplicationScoped
@ManagedBean(name = "machineTypeRepositoryImpl")
public class MachineTypeRepositoryImpl implements MachineTypeRepository {

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
	 * @see me.iit.javorek2.repository.MachineTypeRepository#getMachineTypes()
	 */
	@Override
	public List<String> getMachineTypes() throws RepositoryException {
		List<String> machineTypes = new ArrayList<>();

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
			resultSet = statement.executeQuery("SELECT name FROM machine_type");

			while (resultSet.next()) {
				machineTypes.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}

		return machineTypes;
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.MachineTypeRepository#addMachineType(java.lang.String)
	 */
	@Override
	public void addMachineType(String type) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("INSERT INTO machine_type (name) VALUES (?)");
			preparedStatement.setString(1, type);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}

	/* (non-Javadoc)
	 * @see me.iit.javorek2.repository.MachineTypeRepository#deleteMachineType(java.lang.String)
	 */
	@Override
	public void deleteMachineType(String type) throws RepositoryException {
		Connection connection = null;
		PreparedStatement preparedStatement;

		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}

		try {
			preparedStatement = connection.prepareStatement("DELETE FROM machine_type WHERE name=?");
			preparedStatement.setString(1, type);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RepositoryException(e);
		}
	}
}
