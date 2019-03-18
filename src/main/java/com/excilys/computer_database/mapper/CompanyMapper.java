package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;

public abstract class CompanyMapper {
	private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

	public static Company resultSetToCompany(ResultSet resultSet){
		Company company = null;
		CompanyBuilder companyBuilder = new CompanyBuilder();
		try {
			Integer id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			company = companyBuilder.setId(id).setName(name).build();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when converting the resultSet to company.");
		}
		return company;
	}
	
	public static Company resultSetToCompany(Integer id, String name){
			CompanyBuilder companyBuilder = new CompanyBuilder();
			return companyBuilder.setId(id).setName(name).build();
	}
}
