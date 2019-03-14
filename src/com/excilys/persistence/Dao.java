package com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Dao{
		
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/"+ DB_NAME;

	private static final String USER = "admincdb";
	private static final String PASS = "qwerty1234";
	
	private static Logger logger = LoggerFactory.getLogger(Dao.class);
	
	public static Connection openConnection(){
		Connection conn = null;
			try {
				Class.forName(JDBC_DRIVER);
			    conn = DriverManager.getConnection(DB_URL,USER,PASS);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("Error in Dao.openConnection, class not found");
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("Error in Dao.openConnection, sql error");
			}
		    return conn;
	}
	
//	public void closeConnection() throws SQLException{
//		if (conn != null)
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//	}
	
	
}
