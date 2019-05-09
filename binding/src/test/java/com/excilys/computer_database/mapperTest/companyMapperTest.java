package com.excilys.computer_database.mapperTest;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.excilys.computer_database.binding_dto.CompanyDto;
import com.excilys.computer_database.binding_dto.CompanyDtoBuilder;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

public class companyMapperTest {
	
	@Test
	public void testCompanyToDtoMapper() throws SQLException {
		
		Company company = new Company(1,"company 1");
		
		Optional<CompanyDto> dtoCompany = CompanyMapper.companyToDtoCompany(company);
		if (!dtoCompany.isPresent()) {
			fail();
		}
		assertEquals("company 1", dtoCompany.get().getName());
		assertEquals(1, (int)dtoCompany.get().getId());
	}
	
	@Test
	public void testDtoToCompanyMapper() throws SQLException {
		
		CompanyDto dtoCompany = new CompanyDtoBuilder().setId(1).setName("test").build();
		
		Optional<Company> company = CompanyMapper.dtoCompanyToCompany(dtoCompany);

		if (!company.isPresent()) {
			fail();
		}
		assertEquals("test", company.get().getName());
		assertEquals(1, (int)company.get().getId());
	}
}