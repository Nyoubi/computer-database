package com.excilys.computer_database.mapper;

import java.util.Optional;

import com.excilys.computer_database.binding_dto.CompanyDto;
import com.excilys.computer_database.binding_dto.ComputerDto;
import com.excilys.computer_database.binding_dto.ComputerDtoBuilder;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.utils.Util;

public class ComputerMapper{

	//Private constructor
	private ComputerMapper () {}
	
	public static ComputerDto computerToDtoComputer(Computer computer){
		ComputerDtoBuilder dtoComputerBuilder = new ComputerDtoBuilder();

		dtoComputerBuilder.setId(computer.getId())
						  .setName(computer.getName());
		dtoComputerBuilder.setIntroduced(Util.checkOptional(Util.timestampToString(computer.getIntroduced())));
		
		dtoComputerBuilder.setDiscontinued(Util.checkOptional(Util.timestampToString(computer.getDiscontinued())));

		Optional<CompanyDto> dtoCompany = CompanyMapper.companyToDtoCompany(computer.getCompany());

		if (dtoCompany.isPresent()) {
			dtoComputerBuilder.setCompanyName(dtoCompany.get().getName())
							  .setCompanyId(dtoCompany.get().getId());
		}
		return dtoComputerBuilder.build();
	}
}
