package me.iit.javorek2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoMariaDB {

	private static String address = "localhost";
	private static String port = "3306";
	private static String databaseName = "operationcontrol";
	private static String username = "root";
	private static String password = "";
	private static String connectionString;
	private static Connection connection = null;
	
	static
	{
		connectionString = "jdbc:mariadb://" + address + ":" + port + "/" + databaseName +
				"?user=" + username + "&password=" + password;
	}

	public static Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(connectionString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}