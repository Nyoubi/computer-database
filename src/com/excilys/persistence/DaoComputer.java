package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.exceptions.ExceptionDaoMessage;
import com.excilys.exceptions.ExceptionMessage;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class DaoComputer {
	
	private final String SELECT_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName FROM computer c "
									+ "LEFT JOIN company cn ON c.company_id=cn.id ";
	private final String SELECT_ID = SELECT_ALL + " WHERE c.id=? ";
	private final String INSERT = "INSERT INTO computer VALUES(?,?,?,?)";
	private final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final String DELETE_ID = "DELETE FROM computer WHERE id=?";
	private final String CREATE = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?,?,?,?)";
	
	private Connection conn = null;
	
	DaoComputer() {
		try {
			this.conn = Dao.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Computer resultToComputer(ResultSet resultSet)throws SQLException{
			
			Integer id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			Timestamp introduced = resultSet.getTimestamp("introduced");
			Timestamp discontinued = resultSet.getTimestamp("discontinued");
			Integer companyId = resultSet.getInt("cId");
			String companyName = resultSet.getString("cName");
			
			return new Computer(id,name,introduced,discontinued,new Company(companyId,companyName));
		}
	
	
	private Optional<Computer> findComputerById(int id)  throws ExceptionDaoMessage{
		Optional<Computer> result = Optional.empty();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = conn.prepareStatement(SELECT_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				result= Optional.of(resultToComputer(resultSet));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	public ArrayList<Computer> listAllComputer() throws ExceptionDaoMessage{
		ArrayList<Computer> computer_list = new ArrayList<>();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SELECT_ALL);
			
			while(resultSet.next()) {
				computer_list.add(resultToComputer(resultSet));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionDaoMessage("Error when listing all computers");
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
	
	public Optional<Computer> createComputer(String name, Timestamp introduced, Timestamp discontinued , int companyId) throws ExceptionMessage {
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
				result= Optional.of(resultToComputer(resultSet));
			}
			return result;
		} catch (SQLException e){
			e.printStackTrace();
			throw new ExceptionMessage("Error when creating the computer");
		}
	}
	
}
