package com.example.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

	private String url = "jdbc:mysql://localhost:3306/app_development?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "daiki120";

	public DatabaseConnector() {

	}

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}
	}

	public Connection getConnection() throws SQLException {

		return DriverManager.getConnection(url, user, password);
	}
}
