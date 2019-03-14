package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.model.Company;

public abstract class CompanyMapper {
	private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

	public static Company resultSetToCompany(ResultSet resultSet){
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
	
	public static Company resultSetToCompany(Integer id, String name){

			Company company = new Company(id,name);
			return company;
	}
}
