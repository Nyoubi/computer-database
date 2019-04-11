package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Computer;

@Repository
public class DaoComputer {

	private final String SELECT_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName FROM computer c "
			+ "LEFT JOIN company cn ON c.company_id=cn.id ";
	private final String SELECT_NAME = SELECT_ALL + "WHERE c.name LIKE ? OR cn.name LIKE ? ";
	private final String SELECT_ID = SELECT_ALL + "WHERE c.id=? ";
	private final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final String DELETE_ID = "DELETE FROM computer WHERE id=?";
	private final String CREATE = "INSERT INTO computer (name, introduced, discontinued,company_id) VALUES (?,?,?,?)";
	private final String ALTER_AUTO_INCREMENTE = "ALTER TABLE computer AUTO_INCREMENT = ?";
	private static Logger logger = LoggerFactory.getLogger(DaoComputer.class); 

	@Autowired
	private DataSource dataSource;
	
	public DaoComputer() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	public Optional<Computer> findComputerById(Integer id) throws ExceptionModel, ExceptionDao{

		Optional<Computer> result = Optional.empty();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement statement = conn.prepareStatement(SELECT_ID);){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					result = Optional.of(ComputerMapper.resultSetToComputer(resultSet));
				}
			} 
		} catch (SQLException e) {
			logger.error("Error when looking for the id : " + id);
			throw new ExceptionDao("Error when searching the computer.");
		}
		return result;
	}


	/*
	 * List all computers with default order
	 */
	public ArrayList<Computer> listAllComputer(String order) throws ExceptionModel, ExceptionDao{
		ArrayList<Computer> computerList = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_ALL + order);) {
			while (resultSet.next()) {
				computerList.add(ComputerMapper.resultSetToComputer(resultSet));
			}
		} catch (SQLException e) {
			logger.error("Error when listing computers");
			throw new ExceptionDao("Error when listing all computers.");
		}

		return computerList;
	}

	/*
	 * List all computers with name search
	 */
	public ArrayList<Computer> listAllComputer(String search, String order) throws ExceptionModel, ExceptionDao{
		ArrayList<Computer> computerList = new ArrayList<>();

		try (Connection conn = dataSource.getConnection();
				PreparedStatement statement = conn.prepareStatement(SELECT_NAME + order);) {
			statement.setString(1, "%" + search + "%");
			statement.setString(2, "%" + search + "%");
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					computerList.add(ComputerMapper.resultSetToComputer(resultSet));
				}
			}
		} catch (SQLException e) {
			logger.error("Error when listing computers with research " + search);
			throw new ExceptionDao("Error when listing computers with name.");
		}

		return computerList;
	}

	public Optional<Integer> createComputer(Computer computer) throws ExceptionDao {
		Optional<Integer> idCreated = Optional.empty();
		Integer lineAffected = null;
		try (Connection conn = dataSource.getConnection();
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
			logger.error("Error when creating the computer " + computer);
			throw new ExceptionDao ("Error when creating the computer.");
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
		try (Connection conn = dataSource.getConnection();
				PreparedStatement statement = conn.prepareStatement(UPDATE);){

			fillComputer(computer, statement);
			lineAffected = statement.executeUpdate();
			if (lineAffected <= 0) {
				throw new ExceptionDao("Update impossible");
			}
		} catch (SQLException e) {
			logger.error("Error when updating the computer.");
			throw new ExceptionDao("Update impossible");
		}
	}

	public void deleteComputerById(Integer id)  throws ExceptionDao {
		Integer lineAffected = 0;

		try (Connection conn = dataSource.getConnection();
				PreparedStatement statement = conn.prepareStatement(DELETE_ID);){
			statement.setInt(1, id);
			lineAffected = statement.executeUpdate();
			if (lineAffected <= 0) {
				throw new ExceptionDao("Delete impossible");
			}
		} catch (SQLException e) {
			logger.error("Error when deleting the computer id " + id + ".");
			throw new ExceptionDao("Delete impossible");
		}
	}

	public void resetAutoIncrement(Integer value) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement statement = conn.prepareStatement(ALTER_AUTO_INCREMENTE);){
			statement.setInt(1,value);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error when reseting auto increment value.");
		}
	}
}
