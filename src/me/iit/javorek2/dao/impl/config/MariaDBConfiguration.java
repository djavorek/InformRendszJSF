package me.iit.javorek2.dao.impl.config;

import java.util.Properties;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="mariadbConfiguration")
public class MariaDBConfiguration {
	private static final String ADDRESS = "localhost";
	private static final String PORT = "3306";
	private static final String DATABASENAME = "operationcontrol";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String MAX_POOL = "250";
	
	private Properties properties;
	
	public String getConnectionURL() {
		return "jdbc:mariadb://" + ADDRESS + ":" + PORT + "/" + DATABASENAME;
	}
	
	public Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			properties.setProperty("user", USERNAME);
			properties.setProperty("password", PASSWORD);
			properties.setProperty("MaxPooledStatements", MAX_POOL);
		}
		return properties;
	}
}
