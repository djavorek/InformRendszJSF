package me.iit.javorek2.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import me.iit.javorek2.dao.Dao;
import me.iit.javorek2.dao.impl.config.MariaDBConfiguration;
import me.iit.javorek2.model.exception.DaoException;

@ManagedBean(name="mariadbDao")
public class MariadbDaoImpl implements Dao {
	
	@ManagedProperty(value="#{mariadbConfiguration}")
	private MariaDBConfiguration configuration;

	public void setConfiguration(MariaDBConfiguration configuration) {
		this.configuration = configuration;
	}

	public Connection getConnection() throws DaoException {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(configuration.getConnectionURL(), configuration.getProperties());
		} catch (SQLException e) {
			throw new DaoException("Cannot acquire connection to: " + configuration.getConnectionURL(), e);
		}
		return connection;
	}

}