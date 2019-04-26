package com.excilys.computer_database.mapper;

import java.util.Optional;

import com.excilys.computer_database.Company;
import com.excilys.computer_database.CompanyBuilder;
import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.CompanyDtoBuilder;

public class CompanyMapper {

	public static Optional<CompanyDto> companyToDtoCompany(Company company){
		Optional<CompanyDto> dtoCompany = Optional.empty();
		if (company != null) {
			CompanyDtoBuilder dtoCompanyBuilder = new CompanyDtoBuilder();
			dtoCompany = Optional.of(dtoCompanyBuilder.setId(company.getId())
					.setName(company.getName()).build());
		}	
		return dtoCompany;
	}

	public static Optional<Company> dtoCompanyToCompany(CompanyDto dtoCompany){
		
		if(dtoCompany == null) {
			return Optional.empty();
		} else {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		Company company = companyBuilder.setId(dtoCompany.getId())
										.setName(dtoCompany.getName())
										.build();
		return Optional.of(company);
		}
	}
}
