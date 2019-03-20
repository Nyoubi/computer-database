package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;

public abstract class CompanyMapper {
	public static Company resultSetToCompany(ResultSet resultSet) throws SQLException{
		Company company = null;
		CompanyBuilder companyBuilder = new CompanyBuilder();

		Integer id = resultSet.getInt("cId");
		String name = resultSet.getString("cName");
		if (id == 0 && name == null)  {
			company = null;
		}
		else 
			company = companyBuilder.setId(id).setName(name).build();

		return company;
	}
}
