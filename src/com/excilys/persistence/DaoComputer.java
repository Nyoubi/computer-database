package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class DaoComputer {
	
	private final String SELECT_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName FROM computer c "
									+ "LEFT JOIN company cn ON c.company_id=cn.id ";
	private final String SELECT_ID = SELECT_ALL + "WHERE c.id=? ";
	private final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final String DELETE_ID = "DELETE FROM computer WHERE id=?";
	private final String CREATE = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?,?,?,?)";
	private static Logger logger = LoggerFactory.getLogger(DaoComputer.class);
	
	private Connection conn;
	public DaoComputer() {
		this.conn = Dao.openConnection();
	}
	
//	DaoComputer() {
//		try {
//			this.conn = Dao.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	private Computer resultSetToComputer(ResultSet resultSet){
		Computer computer = null;
		try {
			Integer id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			Timestamp introduced = resultSet.getTimestamp("introduced");
			Timestamp discontinued = resultSet.getTimestamp("discontinued");
			Integer companyId = resultSet.getInt("cId");
			String companyName = resultSet.getString("cName");
			computer = new Computer(id,name,introduced,discontinued,new Company(companyId,companyName));
		} catch (SQLException e){
			e.printStackTrace();
			logger.error("Error when converting the resultSet to computer.");
		}
		return computer;
	}
	
	
	public Optional<Computer> findComputerById(Integer id){
		
		Optional<Computer> result = Optional.empty();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = conn.prepareStatement(SELECT_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				result= Optional.of(resultSetToComputer(resultSet));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when searching the computer id " + id + ".");
		} finally {
			try {
				resultSet.close();
				statement.close();
			} catch (SQLException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public ArrayList<Computer> listAllComputer(){
		
		ArrayList<Computer> computer_list = new ArrayList<>();
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SELECT_ALL);
			
			while(resultSet.next()) {
				computer_list.add(resultSetToComputer(resultSet));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when listing all computers.");
		} finally {
			try {
				resultSet.close();
				statement.close();
			} catch (SQLException ex){
				ex.printStackTrace();
			}
		}
		return computer_list;
	}
	
	public Optional<Computer> createComputer(String name, Timestamp introduced, Timestamp discontinued , Integer companyId){
		
		Optional<Computer> result = Optional.empty();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = conn.prepareStatement(CREATE);
			statement.setString(1, name);
			statement.setTimestamp(2, introduced);
			statement.setTimestamp(3, discontinued);
			statement.setInt(4, companyId);
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if(resultSet.next()) {
				result= Optional.of(resultSetToComputer(resultSet));
			}
		} catch (SQLException e){
			e.printStackTrace();
			logger.error("Error when creating the computer.");
		} finally {
			try {
				resultSet.close();
				statement.close();
			} catch (SQLException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean updateComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued , Integer companyId){
		PreparedStatement statement = null;
		Integer lineAffected = 0;
		try {
			statement = conn.prepareStatement(UPDATE);
			statement.setString(1, name);
			statement.setTimestamp(2, introduced);
			statement.setTimestamp(3, discontinued);
			statement.setInt(4, companyId);
			lineAffected = statement.executeUpdate();

		} catch (SQLException e){
			e.printStackTrace();
			logger.error("Error when updating the computer.");
		} finally {
			try {
				statement.close();
			} catch (SQLException ex){
				ex.printStackTrace();
			}
		}
		
		return lineAffected > 0;
		
	}
	
	public boolean deleteComputerById(Integer id){
		PreparedStatement statement = null;
		Integer lineAffected = 0;
		try {
			statement = conn.prepareStatement(DELETE_ID);
			statement.setInt(1, id);
			lineAffected = statement.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			logger.error("Error when deleting the computer id " + id + ".");
		} finally {
			try {
				statement.close();
			} catch (SQLException ex){
				ex.printStackTrace();
			}
		}
		return lineAffected > 0;
	}
}
