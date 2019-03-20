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

import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

public class DaoCompany  extends Dao{
	
	private final static String SELECT_ALL = "SELECT id as cId, name as cName FROM company ";
	private final static String SELECT_ID = SELECT_ALL + "WHERE id=? ";
	private static Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
    private static volatile DaoCompany instance = null;
	
	private DaoCompany(String driver, String dbUrl, String user, String pass) {
		Dao.driver = driver;  
		Dao.dbUrl = dbUrl;
		Dao.user = user;
		Dao.pass = pass;
	}
    
	public static DaoCompany getInstance(String driver, String dbUrl, String user, String pass)
    {   
		if (instance == null) {
			synchronized(DaoCompany.class) {
				if (instance == null) {
					instance = new DaoCompany(driver,dbUrl,user,pass);
				}
			}
		}
		return instance;
    }

	public Optional<Company> findCompanyById(Integer id){
		
		Optional<Company> result = Optional.empty();
		
		try (Connection conn = openConnection();
		    PreparedStatement statement = conn.prepareStatement(SELECT_ID);){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				if(resultSet.next()) {
					result= Optional.of(CompanyMapper.resultSetToCompany(resultSet));			
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when searching the company id " + id);
		}
		return result;
	}
	
	public ArrayList<Company> listAllCompany(){
		
		ArrayList<Company> company_list = new ArrayList<>();
		
		try (Connection conn = openConnection();
			 Statement statement = conn.createStatement();){
			
			try (ResultSet resultSet = statement.executeQuery(SELECT_ALL);) {
				while(resultSet.next()) {
					Optional<Company> company = Optional.of(CompanyMapper.resultSetToCompany(resultSet));
					if (company.isPresent()) {
						company_list.add(company.get());	
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when listing all companies.");
		}
		return company_list;
	}
}
