package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.CompanyDtoBuilder;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;

public class CompanyMapper implements RowMapper<Company>{
	
	public static Company resultSetToCompany(ResultSet resultSet) throws SQLException{
		Company company = null;
		CompanyBuilder companyBuilder = new CompanyBuilder();

		Integer id = resultSet.getInt("cId");
		String name = resultSet.getString("cName");
		
		if (id == 0 || id == null)  {
			company = null;
		}
		else {
			company = companyBuilder.setId(id).setName(name).build();
		}
		return company;
	}

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
	
	@Override
	  public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
	    return resultSetToCompany(rs);
	  }
}
