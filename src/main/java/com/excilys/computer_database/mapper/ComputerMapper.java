package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.dto.ComputerDtoBuilder;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.util.Util;

public class ComputerMapper implements RowMapper<Computer>{
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

	public static ComputerDto computerToDtoComputer(Computer computer){
		ComputerDtoBuilder dtoComputerBuilder = new ComputerDtoBuilder();

		dtoComputerBuilder.setId(computer.getId())
						  .setName(computer.getName());
		dtoComputerBuilder.setIntroduced(Util.checkOptional(Util.timestampToString(computer.getIntroduced())));
		
		dtoComputerBuilder.setDiscontinued(Util.checkOptional(Util.timestampToString(computer.getDiscontinued())));

		Optional<CompanyDto> dtoCompany = CompanyMapper.companyToDtoCompany(computer.getCompany());

		if (dtoCompany.isPresent()) {
			dtoComputerBuilder.setCompanyName(dtoCompany.get().getName().toString())
							  .setCompanyId(dtoCompany.get().getId());
		}
		return dtoComputerBuilder.build();
	}

	@Override
	  public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
	    return resultSetToComputer(rs);
	  }
	
	
	
	
}
