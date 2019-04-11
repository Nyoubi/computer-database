package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.dto.DtoComputerBuilder;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.util.Util;

public abstract class ComputerMapper {
	public static Computer resultSetToComputer(ResultSet resultSet) throws SQLException, ExceptionModel{
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

	public static DtoComputer computerToDtoComputer(Computer computer){
		DtoComputerBuilder dtoComputerBuilder = new DtoComputerBuilder();

		dtoComputerBuilder.setId(computer.getId())
						  .setName(computer.getName());
		dtoComputerBuilder.setIntroduced(Util.checkOptional(Util.timestampToString(computer.getIntroduced())));
		
		dtoComputerBuilder.setDiscontinued(Util.checkOptional(Util.timestampToString(computer.getDiscontinued())));

		Optional<DtoCompany> dtoCompany = CompanyMapper.companyToDtoCompany(computer.getCompany());

		if (dtoCompany.isPresent()) {
			dtoComputerBuilder.setCompanyName(dtoCompany.get().getName().toString())
							  .setCompanyId(dtoCompany.get().getId());
		}
		return dtoComputerBuilder.build();
	}
}
