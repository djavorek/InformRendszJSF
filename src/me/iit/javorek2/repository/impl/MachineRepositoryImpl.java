package me.iit.javorek2.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.dao.Dao;
import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.DaoException;
import me.iit.javorek2.model.exception.RepositoryException;
import me.iit.javorek2.repository.MachineRepository;

@ManagedBean(name="machineRepositoryImpl")
public class MachineRepositoryImpl implements MachineRepository {
	
	@ManagedProperty(value="#{mariadbDao}")
	private Dao dao;

	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Machine> getMachines() throws RepositoryException {
		List<Machine> machineList = new ArrayList<>();
		Connection connection = null;
		
//		String sqlString;
		Statement statement;
//		PreparedStatement preparedStatement;
		ResultSet resultSet;
		
		try {
			connection = dao.getConnection();
		} catch (DaoException e) {
			throw new RepositoryException("Error in underlying layer, database is not ready.", e);
		}
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT machine.name, machine_type.name, machine.working FROM machine INNER JOIN machine_type ON machine.type=machine_type.id");
			
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
	
	
}
