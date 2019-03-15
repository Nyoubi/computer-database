package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.controller.Controller;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Computer;

public class DaoComputer {

	private final String SELECT_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName FROM computer c "
			+ "LEFT JOIN company cn ON c.company_id=cn.id ";
	private final String SELECT_ID = SELECT_ALL + "WHERE c.id=? ";
	private final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final String DELETE_ID = "DELETE FROM computer WHERE id=?";
	private final String CREATE = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?,?,?,?)";
	private static Logger logger = LoggerFactory.getLogger(DaoComputer.class);

    private static volatile DaoComputer instance = null;
    
    private DaoComputer() {}
    
	public static DaoComputer getInstance()
    {   
		if (instance == null) {
			synchronized(Controller.class) {
				if (instance == null) {
					instance = new DaoComputer();
				}
			}
		}
		return instance;
    }
	
	public Optional<Computer> findComputerById(Integer id) {
		
		Optional<Computer> result = Optional.empty();
		try (Connection conn = Dao.openConnection();
			 PreparedStatement statement = conn.prepareStatement(SELECT_ID);){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					result = Optional.of(ComputerMapper.resultSetToComputer(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when searching the computer id " + id + ".");
		}
		return result;
	}

	public ArrayList<Computer> listAllComputer() {

		ArrayList<Computer> computer_list = new ArrayList<>();

		try (Connection conn = Dao.openConnection();
			 Statement statement = conn.createStatement();
			 ResultSet resultSet = statement.executeQuery(SELECT_ALL);) {
			while (resultSet.next()) {
				computer_list.add(ComputerMapper.resultSetToComputer(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when listing all computers.");
		}
		return computer_list;
	}

	public Boolean createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {

		Integer lineAffected = 0;
		try (Connection conn = Dao.openConnection();
			 PreparedStatement statement = conn.prepareStatement(CREATE);){
			statement.setString(1, name);

			if (introduced != null) {
				statement.setTimestamp(2, introduced);
			} else {
				statement.setNull(2, Types.TIMESTAMP);
			}

			if (discontinued != null) {
				statement.setTimestamp(3, discontinued);
			} else {
				statement.setNull(3, Types.TIMESTAMP);
			}

			if (companyId != null) {
				if(DaoCompany.findCompanyById(companyId).isPresent())
					statement.setInt(4, companyId);
				else
					logger.error("The company " + companyId + " doesn't exist in the database.");
			} else {
				statement.setNull(4, Types.INTEGER);
			}

			lineAffected = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when creating the computer.");
		}
		return lineAffected > 0;
	}

	public boolean updateComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		Integer lineAffected = 0;
		try (Connection conn = Dao.openConnection();
			 PreparedStatement statement = conn.prepareStatement(UPDATE);){
			statement.setString(1, name);
			if (introduced != null) {
				statement.setTimestamp(2, introduced);
			} else {
				statement.setNull(2, Types.TIMESTAMP);
			}

			if (discontinued != null) {
				statement.setTimestamp(3, discontinued);
			} else {
				statement.setNull(3, Types.TIMESTAMP);
			}

			if (companyId != null) {
				statement.setInt(4, companyId);
			} else {
				statement.setNull(4, Types.INTEGER);
			}

			if (id != null) {
				statement.setInt(4, companyId);
			} else {
				logger.error("Id can't be null when updating a computer");
			}

			
			lineAffected = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when updating the computer.");
		}

		return lineAffected > 0;

	}

	public boolean deleteComputerById(Integer id) {
		Integer lineAffected = 0;
		
		try (Connection conn = Dao.openConnection();
			 PreparedStatement statement = conn.prepareStatement(DELETE_ID);){
			statement.setInt(1, id);
			lineAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when deleting the computer id " + id + ".");
		}
		return lineAffected > 0;
	}
}
