package me.iit.javorek2.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.dao.Dao;
import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.DaoException;
import me.iit.javorek2.model.exception.RepositoyException;
import me.iit.javorek2.repository.MachineRepository;

@ManagedBean(name="machineRepositoryImpl")
public class MachineRepositoryImpl implements MachineRepository {
	
	@ManagedProperty(value="#{mariadbDao}")
	private Dao dao;

	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	@Override
	public Machine getMachineByName(String machineName) throws RepositoyException {
		Machine machine = new Machine();
		Connection connection = null;
		
//		String sqlString;
//		Statement statement;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		
		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoyException("Error in underlying layer, database is not ready.", e);
		}
		
		try {
			preparedStatement = connection.prepareStatement("Select * from MACHINE where NAME=?");
			preparedStatement.setString(1, machineName);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				machine.setName(resultSet.getString(2));
				machine.setType(resultSet.getString(3));
				machine.setWorking(resultSet.getBoolean(4));
			}
		} catch (SQLException e) {
			throw new RepositoyException(e);
		}
		System.out.println("Yaay: " + machine.getName() + ", " + machine.isWorking());
		
		return machine;
	}
	
	
}
