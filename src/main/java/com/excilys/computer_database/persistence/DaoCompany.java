package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

public class DaoCompany {
	
	private final static String SELECT_ALL = "SELECT id as cId, name as cName FROM company ";
	private final static String SELECT_ID = SELECT_ALL + "WHERE id=? ";
	private final static String DELETE_ID = "DELETE FROM company WHERE id = ? ";
	private final static String DELETE_COMPUTER_ID = "DELETE FROM computer WHERE company_id = ? ";

	private static Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
    private static volatile DaoCompany instance = null;
	private String dataSource = null;

	private DaoCompany(String dataSource) {
		this.dataSource = dataSource;
	}
    
	public static DaoCompany getInstance(String dataSource)
    {   
		if (instance == null) {
			synchronized(DaoCompany.class) {
				if (instance == null) {
					instance = new DaoCompany(dataSource);
				}
			}
		}
		return instance;
    }

	public Optional<Company> findCompanyById(Integer id) throws ExceptionModel, ExceptionDao{
		
		Optional<Company> result = Optional.empty();
		
		try (Connection conn = ConnectionPool.getInstance(dataSource).getDataSource().getConnection();
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
		
		try (Connection conn = ConnectionPool.getInstance(dataSource).getDataSource().getConnection();
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
	
	public void deleteCompanyById(Integer id) {
		Connection conn = null;
		try {
			conn = ConnectionPool.getInstance(dataSource).getDataSource().getConnection();

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
			} finally {
				if (conn != null && ! conn.isClosed()) {
					conn.close();
				}
			} 
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
