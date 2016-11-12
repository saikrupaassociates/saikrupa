package com.saikrupa.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PersistentManager {
	
	private static PersistentManager persistentManager;
	
	private Connection connection;

	private PersistentManager() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("database");
			createConnection(bundle);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createConnection(ResourceBundle bundle) {
		try {
			Class.forName(bundle.getString("database.driver.class.name"));
			connection = DriverManager.getConnection(
					bundle.getString("database.connection.string"), 
					bundle.getString("database.db.user"), 
					bundle.getString("database.db.password"));
			
			if(connection == null) {
				throw new Exception("Could not be Connected to database");
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public static PersistentManager getPersistentManager() {
		if(persistentManager == null) {
			persistentManager = new PersistentManager();
		}
		return persistentManager;
	}
	
	public void closeConnection() {
		if(connection != null) {
			try {
				connection.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
