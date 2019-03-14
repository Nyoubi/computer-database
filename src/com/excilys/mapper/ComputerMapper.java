package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public abstract class ComputerMapper {
	private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

	public static Computer resultSetToComputer(ResultSet resultSet){
		Computer computer = null;
		try {
			Integer id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			Timestamp introduced = resultSet.getTimestamp("introduced");
			Timestamp discontinued = resultSet.getTimestamp("discontinued");
			Company company = CompanyMapper.resultSetToCompany(resultSet.getInt("cId"),resultSet.getString("cName"));
			computer = new Computer(id,name,introduced,discontinued,company);
		} catch (SQLException e){
			e.printStackTrace();
			logger.error("Error when converting the resultSet to computer.");
		}
		return computer;
	}
}
