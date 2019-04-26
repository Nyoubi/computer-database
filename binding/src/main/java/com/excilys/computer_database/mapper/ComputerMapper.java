package com.excilys.computer_database.mapper;

import java.util.Optional;

import com.excilys.computer_database.Computer;
import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.dto.ComputerDtoBuilder;
import com.excilys.computer_database.util.Util;

public class ComputerMapper{


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
}
