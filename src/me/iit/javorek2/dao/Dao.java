package me.iit.javorek2.dao;

import java.sql.Connection;

import me.iit.javorek2.model.exception.DaoException;

public interface Dao {
	
	Connection getConnection() throws DaoException;
}
