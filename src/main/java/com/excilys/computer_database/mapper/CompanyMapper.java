package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;

public abstract class CompanyMapper {
	private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

	public static Company resultSetToCompany(ResultSet resultSet){
		Company company = null;
		CompanyBuilder companyBuilder = new CompanyBuilder();
		try {
			Integer id = resultSet.getInt("cId");
			String name = resultSet.getString("cName");
			if (id == 0 && name == null)  {
				company = null;
			}
			else 
				company = companyBuilder.setId(id).setName(name).build();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error when converting the resultSet to company.");
		}
		return company;
	}
}
