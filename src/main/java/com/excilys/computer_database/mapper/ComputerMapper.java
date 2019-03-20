package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;

public abstract class ComputerMapper {

	public static Computer resultSetToComputer(ResultSet resultSet) throws SQLException{
		ComputerBuilder computerBuilder = new ComputerBuilder();
		Integer id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		Timestamp introduced = resultSet.getTimestamp("introduced");
		Timestamp discontinued = resultSet.getTimestamp("discontinued");
		Company company = CompanyMapper.resultSetToCompany(resultSet);
		Computer computer = computerBuilder.setId(id)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(company).build();

		return computer;
	}
}
