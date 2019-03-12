package com.excilys.model;

import com.excilys.exceptions.ExceptionMessage;

import java.sql.*;
import java.util.*;

public class ComputerDB {
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/"+ DB_NAME;

	private static final String USER = "admincdb";
	private static final String PASS = "qwerty1234";
	 
	private Connection conn = null;
	
	
	
	public ComputerDB() throws ClassNotFoundException, SQLException{
		 
		      Class.forName(JDBC_DRIVER);
		      
		      System.out.println("Connecting to database...");
		      
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      System.out.println("Connected to " + DB_NAME);
	}
	 
	public ArrayList<Computer> listComputers () throws SQLException {
		ArrayList<Computer> computer_list = new ArrayList<>();
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("Select c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName from computer c "
										+"Left join company cn on cn.id = c.company_id");
		while (resultSet.next()) {
			Computer computer = new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"),new Company(resultSet.getInt("cId"),resultSet.getString("cName")));
			computer_list.add(computer);
		}
		stmt.close();
		return computer_list;
	}
	
	public ArrayList<Company> listCompanies () throws SQLException {
		ArrayList<Company> company_list = new ArrayList<>();
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("Select id, name from company");
		while (resultSet.next()) {
			Company company = new Company(resultSet.getInt("id"),resultSet.getString("name"));
			company_list.add(company);
		}
		return company_list;
	}
	 
	private Computer findComputer(int id) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("Select c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName from computer c "
				 						 	+"Left join company cn on cn.id = c.company_id "
				 						 	+"WHERE c.id = "+id);
		if (resultSet.next()) {
			return new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"),new Company(resultSet.getInt("cId"),resultSet.getString("cName")));
		}
		else 
			return null;
	}
	 
	public int showDetails(int id) throws SQLException {
		Computer c = findComputer(id);
		if (c!=null) {
			System.out.println(c + "\n");
			return 0;
		} else {
			System.out.println("This computer id doesn't exist.\n");
			return -1;
		}
	}
	 
	public void createComputer(String name, Timestamp introduced, Timestamp discontinued , int companyId) throws ExceptionMessage {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?,?,?,?)");
			stmt.setString(1, name);
			stmt.setTimestamp(2, introduced);
			stmt.setTimestamp(3, discontinued);
			stmt.setInt(4, companyId);
			stmt.executeUpdate();
			System.out.println("Computer "+ name + " has been created\n");
		} catch (SQLException e){
			throw new ExceptionMessage("Error when creating the computer");
		}
	}
	 
	public void updateComputer(int id, String name, Timestamp introduced, Timestamp discontinued , Integer companyId) throws ExceptionMessage {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");
			stmt.setString(1, name);
			stmt.setTimestamp(2, introduced);
			stmt.setTimestamp(3, discontinued);
			stmt.setInt(4, companyId);
			stmt.setInt(5, id);
			stmt.executeUpdate();
			System.out.println("Computer "+ id + " has been updated\n");
		} catch (SQLException e){
			e.printStackTrace();
			throw new ExceptionMessage("Error when updating the computer");
		}
	}
	 
	public void deleteComputer(int id) throws SQLException {
		if (findComputer(id)==null) {
			System.out.println("This computer id doesn't exist.\n");
		} else { 
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM computer WHERE id = " + id);
			System.out.println("The computer has been deleted.\n");
		}
	}
	 
	@Override
	protected void finalize()
	{
		try {
			if (conn != null && !conn.isClosed())
				conn.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 
}
