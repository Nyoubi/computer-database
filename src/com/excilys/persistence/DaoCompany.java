package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;

public class DaoCompany {
	
	private final String SELECT_ALL = "SELECT id, name FROM company ";
	private final String SELECT_ID = SELECT_ALL + "WHERE id=? ";

	private Connection conn;
	private static Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
	public DaoCompany() {
		this.conn = Dao.openConnection();

	}
	
//	DaoCompany() {
//		try {
//			this.conn = Dao.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	private Company resultSetToCompany(ResultSet resultSet){
		Company company = null;
		try {
			Integer companyId = resultSet.getInt("id");
			String companyName = resultSet.getString("name");
			company = new Company(companyId,companyName);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when converting the resultSet to company.");
		}
		return company;
	}
	
	
	public Optional<Company> findCompanyById(Integer id){
		
		Optional<Company> result = Optional.empty();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = conn.prepareStatement(SELECT_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				result= Optional.of(resultSetToCompany(resultSet));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when searching the company id " + id);
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
	
	public ArrayList<Company> listAllCompany(){
		
		ArrayList<Company> company_list = new ArrayList<>();
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SELECT_ALL);
			
			while(resultSet.next()) {
				company_list.add(resultSetToCompany(resultSet));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when listing all companies.");
		} finally {
			try {
				resultSet.close();
				statement.close();
			} catch (SQLException ex){
				ex.printStackTrace();
			}
		}
		return company_list;
	}
	
}
