package com.excilys.model;

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
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName from computer c "
											+"Left join company cn on cn.id = c.company_id");
			stmt.close();
			ArrayList<Computer> res = new ArrayList<>();
			while (rs.next()) {
				res.add(new Computer(rs.getInt("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),new Company(rs.getInt("cId"),rs.getString("cName"))));
			}
			return res;
	 }
	
	 public ArrayList<Company> listCompanies () throws SQLException {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select id, name from company");
			ArrayList<Company> res = new ArrayList<>();
			while (rs.next()) {
				res.add(new Company(rs.getInt("id"),rs.getString("name")));
			}
			return res;
	 }
	 
	 private Computer findComputer(int id) throws SQLException {
		 Statement stmt = conn.createStatement();
		 ResultSet rs = stmt.executeQuery("Select c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName from computer c "
				 						 +"Left join company cn on cn.id = c.company_id "
				 						 +"WHERE c.id = "+id);
		 if (rs.next()) {
			 return new Computer(rs.getInt("id"),rs.getString("name"),rs.getTimestamp("introduced"),rs.getTimestamp("discontinued"),new Company(rs.getInt("cId"),rs.getString("cName")));
		 }
		 else 
			 return null;
	 }
	 
	 public void showDetails(int id) throws SQLException {
		 Computer c = findComputer(id);
		 if (c!=null)
			 System.out.println(c);
		 else 
			 System.out.println("This computer doesn't exist.");
	 }
	 
	 public void createComputer(String name, Timestamp introduced, Timestamp discontinued , int company_id) throws SQLException {
		 Statement stmt = conn.createStatement();
		 stmt.executeUpdate("INSERT INTO computer VALUES ("+name+","+introduced+","+discontinued+","+company_id+")");
		 System.out.println("Computer "+ name + " has been deleted");
	 }
	 
	 public boolean updateComputer(int id) throws SQLException {
		 if (findComputer(id)==null)
			 return false;
		 
		 Statement stmt = conn.createStatement();
		 Scanner in = new Scanner(System. in);
		 System.out.println("Entrer un nouveau nom :");
		 String name = in. nextLine();
		 return true;//TODO	 
		 
		 //stmt.executeUpdate("INSERT INTO computer VALUES ("+name+","+introduced+","+discontinued+","+company_id+")");
	 }
	 
	 public void deleteComputer(int id) throws SQLException {
		 Statement stmt = conn.createStatement();
		 stmt.executeUpdate("DELETE FROM computer c "
				 		   +"WHERE c.id =" + id);
		 System.out.println("Computer "+id+" has been deleted");
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
