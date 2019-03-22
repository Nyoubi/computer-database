package com.excilys.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.dto.DtoCompanyBuilder;
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
		else {
			company = companyBuilder.setId(id).setName(name).build();
		}
		return company;
	}

	public static Optional<DtoCompany> companyToDtoCompany(Company company){
		Optional<DtoCompany> dtoCompany = Optional.empty();
		if (company != null) {
			DtoCompanyBuilder dtoCompanyBuilder = new DtoCompanyBuilder();
			dtoCompany = Optional.of(dtoCompanyBuilder.setId(company.getId())
					.setName(company.getName()).build());
		}	
		return dtoCompany;
	}

	public static Company dtoCompanyToCompany(DtoCompany dtoCompany){
		CompanyBuilder companyBuilder = new CompanyBuilder();

		Company company = companyBuilder.setId(dtoCompany.getId())
				.setName(dtoCompany.getName()).build();
		return company;
	}
}
