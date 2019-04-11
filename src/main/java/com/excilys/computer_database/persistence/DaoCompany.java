package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;


@Repository
public class DaoCompany {
	
	private final static String SELECT_ALL = "SELECT id as cId, name as cName FROM company ";
	private final static String SELECT_ID = SELECT_ALL + "WHERE id=? ";
	private final static String DELETE_ID = "DELETE FROM company WHERE id = ? ";
	private final static String DELETE_COMPUTER_ID = "DELETE FROM computer WHERE company_id = ? ";
	private final String CREATE = "INSERT INTO company (name) VALUES (?)";
	private final String ALTER_AUTO_INCREMENTE = "ALTER TABLE company AUTO_INCREMENT = ?";

	private static Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
	@Autowired
	private DataSource dataSource;
	
	public DaoCompany() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	public Optional<Company> findCompanyById(Integer id) throws ExceptionModel, ExceptionDao{
		
		Optional<Company> result = Optional.empty();
		
		try (Connection conn = dataSource.getConnection();
		    PreparedStatement statement = conn.prepareStatement(SELECT_ID);){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				if(resultSet.next()) {
					result= Optional.of(CompanyMapper.resultSetToCompany(resultSet));			
				}
			}
		} catch (SQLException e) {
			logger.error("Error when searching the company id " + id);
			throw new ExceptionDao("Sql error when searching the computer.");
		}
		return result;
	}
	
	public ArrayList<Company> listAllCompany(){
		
		ArrayList<Company> companyList = new ArrayList<>();
		
		try (Connection conn = dataSource.getConnection();
			 Statement statement = conn.createStatement();){
			
			try (ResultSet resultSet = statement.executeQuery(SELECT_ALL);) {
				while(resultSet.next()) {
					Optional<Company> company = Optional.of(CompanyMapper.resultSetToCompany(resultSet));
					if (company.isPresent()) {
						companyList.add(company.get());	
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when listing all companies.");
		}
		return companyList;
	}
	
	public void deleteCompanyById(Integer id) throws ExceptionDao {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			try  {
				conn.setAutoCommit(false);
				PreparedStatement stmt = conn.prepareStatement(DELETE_COMPUTER_ID);
				stmt.setInt(1, id);
				stmt.executeUpdate();
				stmt = conn.prepareStatement(DELETE_ID);
				stmt.setInt(1, id);
				stmt.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				if(conn != null) {
					conn.rollback();
				}
				logger.error("Error when deleting company " + id);
				throw new ExceptionDao("Error when deleting the company");
			} finally {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			}
		}catch (SQLException e1) {
			throw new ExceptionDao("Error when deleting the company");
		}
	}
	
	public Optional<Integer> createCompany(Company company) throws ExceptionDao {
		Optional<Integer> idCreated = Optional.empty();
		Integer lineAffected = null;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE,Statement.RETURN_GENERATED_KEYS);){
			statement.setString(1, company.getName());
			lineAffected = statement.executeUpdate();
			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				if (lineAffected > 0) {
					resultSet.next();
				}
				idCreated = Optional.of(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			logger.error("Error when creating the company " + company);
			throw new ExceptionDao ("Error when creating the company.");
		}
		return idCreated;
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
