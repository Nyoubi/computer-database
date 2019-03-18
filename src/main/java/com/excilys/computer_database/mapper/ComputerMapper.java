package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;

public abstract class ComputerMapper {
	private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

	public static Computer resultSetToComputer(ResultSet resultSet){
		ComputerBuilder computerBuilder = new ComputerBuilder();
		Computer computer = null;
		try {
			Integer id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			Timestamp introduced = resultSet.getTimestamp("introduced");
			Timestamp discontinued = resultSet.getTimestamp("discontinued");
			Company company = CompanyMapper.resultSetToCompany(resultSet);
			computer = computerBuilder.setId(id)
									  .setName(name)
									  .setIntroduced(introduced)
									  .setDiscontinued(discontinued)
									  .setCompany(company).build();
		} catch (SQLException e){
			e.printStackTrace();
			logger.error("Error when converting the resultSet to computer.");
		}
		return computer;
	}
}
