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

import com.excilys.computer_database.controller.Controller;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

public class DaoCompany {
	
	private final static String SELECT_ALL = "SELECT id, name FROM company ";
	private final static String SELECT_ID = SELECT_ALL + "WHERE id=? ";

	private static Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
    private static volatile DaoCompany instance = null;
    
    private DaoCompany() {}
    
	public static DaoCompany getInstance()
    {   
		if (instance == null) {
			synchronized(Controller.class) {
				if (instance == null) {
					instance = new DaoCompany();
				}
			}
		}
		return instance;
    }

	public static Optional<Company> findCompanyById(Integer id){
		
		Optional<Company> result = Optional.empty();
		
		try (Connection conn = Dao.openConnection();
		    PreparedStatement statement = conn.prepareStatement(SELECT_ID);){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				while(resultSet.next()) {
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
		
		try (Connection conn = Dao.openConnection();
			 Statement statement = conn.createStatement();){
			
			try (ResultSet resultSet = statement.executeQuery(SELECT_ALL);) {
				while(resultSet.next()) {
					company_list.add(CompanyMapper.resultSetToCompany(resultSet));				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when listing all companies.");
		}
		return company_list;
	}
	
}
