package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Computer;

public class DaoComputer extends Dao{

	private final String SELECT_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName FROM computer c "
			+ "LEFT JOIN company cn ON c.company_id=cn.id ";
	private final String SELECT_ID = SELECT_ALL + "WHERE c.id=? ";
	private final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final String DELETE_ID = "DELETE FROM computer WHERE id=?";
	private final String CREATE = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?,?,?,?)";
	private final String ALTER_AUTO_INCREMENTE = "ALTER TABLE computer AUTO_INCREMENT = ?";
	private final String COUNT = "SELECT COUNT(id) FROM computer";
	private static Logger logger = LoggerFactory.getLogger(DaoComputer.class); 
	
	private static volatile DaoComputer instance = null;
	
	private DaoComputer(String driver, String dbUrl, String user, String pass) {
		Dao.driver = driver;  
		Dao.dbUrl = dbUrl;
		Dao.user = user;
		Dao.pass = pass;
	}

	public static DaoComputer getInstance(String driver, String dbUrl, String user, String pass)
	{   
		if (instance == null) {
			synchronized(DaoComputer.class) {
				if (instance == null) {
					instance = new DaoComputer(driver,dbUrl,user,pass);
				}
			}
		}
		return instance;
	}
	
	public static DaoComputer getInstance()
	{   
		if (instance == null) {
			synchronized(DaoComputer.class) {
				if (instance == null) {
					instance = new DaoComputer("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/computer-database-db","admincdb","qwerty1234");
				}
			}
		}
		return instance;
	}

	public Optional<Computer> findComputerById(Integer id) throws ExceptionModel{

		Optional<Computer> result = Optional.empty();
		try (Connection conn = openConnection();
				PreparedStatement statement = conn.prepareStatement(SELECT_ID);){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					result = Optional.of(ComputerMapper.resultSetToComputer(resultSet));
				}
			}
		} catch (SQLException e) {
			logger.error("Error when searching the computer id " + id + ".");
		}
		return result;
	}

	public ArrayList<Computer> listAllComputer() throws ExceptionModel{
		ArrayList<Computer> computerList = new ArrayList<>();

		try (Connection conn = openConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_ALL);) {
			while (resultSet.next()) {
				computerList.add(ComputerMapper.resultSetToComputer(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when listing all computers.");
		}
		
		return computerList;
	}
	
	public Optional<Integer> createComputer(Computer computer) throws ExceptionDao {
		Optional<Integer> idCreated = Optional.empty();
		Integer lineAffected = null;
		try (Connection conn = openConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE,Statement.RETURN_GENERATED_KEYS);){
			fillComputer(computer, statement);
			lineAffected = statement.executeUpdate();
			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				if (lineAffected > 0) {
					resultSet.next();
				}
				idCreated = Optional.of(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			throw new ExceptionDao ("Erro when creating the computer.");
		}
		return idCreated;
	}

	private PreparedStatement fillComputer(Computer computer, PreparedStatement statement) throws SQLException {
		if (computer.getId() != null && computer.getId() != 0) {
			statement.setInt(5,computer.getId());
		}
		statement.setString(1, computer.getName());
		statement.setTimestamp(2, computer.getIntroduced());
		statement.setTimestamp(3, computer.getDiscontinued());
		if (computer.getCompany() != null)
			statement.setInt(4, computer.getCompany().getId());
		else
			statement.setNull(4, Types.INTEGER);
		return statement;
	}

	public void updateComputer(Computer computer) throws ExceptionDao {
		Integer lineAffected = 0;
		try (Connection conn = openConnection();
				PreparedStatement statement = conn.prepareStatement(UPDATE);){

			fillComputer(computer, statement);
			lineAffected = statement.executeUpdate();
			if (lineAffected <= 0) {
				throw new ExceptionDao("Update impossible");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when updating the computer.");
		}
	}

	public void deleteComputerById(Integer id)  throws ExceptionDao {
		Integer lineAffected = 0;

		try (Connection conn = openConnection();
				PreparedStatement statement = conn.prepareStatement(DELETE_ID);){
			statement.setInt(1, id);
			lineAffected = statement.executeUpdate();
			if (lineAffected <= 0) {
				throw new ExceptionDao("Update impossible");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when deleting the computer id " + id + ".");
		}
	}
	
	public Optional<Integer> getNbComputer() throws ExceptionDao{
		Optional<Integer> result = Optional.empty();
		try (Connection conn = openConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(COUNT)){
			if (resultSet.next()) {
				result = Optional.of(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			throw new ExceptionDao("Error when getting computer count int the database.");
		}
		return result;
	}

	public void resetAutoIncrement(Integer value) {
		try (Connection conn = openConnection();
				PreparedStatement statement = conn.prepareStatement(ALTER_AUTO_INCREMENTE);){
			statement.setInt(1,value);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error when reseting auto increment value.");
		}
	}
}
