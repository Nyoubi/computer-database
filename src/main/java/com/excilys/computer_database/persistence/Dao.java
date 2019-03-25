package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Dao{
    
	protected static String driver = "com.mysql.cj.jdbc.Driver"; // Connect to test db by default
	protected static String dbUrl = "jdbc:mysql://localhost:3306/computer-database-test";
	protected static String user = "admintest";
	protected static String pass = "test1234";
	
	private static Logger logger = LoggerFactory.getLogger(Dao.class);
	
	public static Connection openConnection(){
		Connection conn = null;
			try {
				Class.forName(driver);
			    conn = DriverManager.getConnection(dbUrl,user,pass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("Error in Dao.openConnection, class not found");
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("Error in Dao.openConnection, sql error");
			}
		    return conn;
	}
}
