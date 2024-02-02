package com.gladikov.crud.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceProvider {
	private static Connection connection;
	private static String url = "jdbc:postgresql://db:5432/university";
	private static String username = "root";
	private static String password = "secret";
	
	public  static Connection getConnection() {
		if(connection == null) {
			try { connection = DriverManager.getConnection(url, username, password);
				connection.setAutoCommit(false);
			 } catch (SQLException e) {
			    log.error(e.getMessage());
			}
		}
		return connection;
	}
}
