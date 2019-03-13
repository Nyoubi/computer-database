package com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao{
	
	private static Connection conn;
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/"+ DB_NAME;

	private static final String USER = "admincdb";
	private static final String PASS = "qwerty1234";
	
	
	public static Connection getConnection() throws SQLException{
		    try {
				Class.forName(JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      System.out.println("Connected to " + DB_NAME);
		      return conn;
	}
}
