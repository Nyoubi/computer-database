package com.excilys.computer_database.mapperTest;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.CompanyDtoBuilder;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

@ExtendWith(MockitoExtension.class)
public class companyMapperTest {
	
	@Test
	public void testCompanyToDtoMapper() throws SQLException {
		
		Company company = new Company(1,"company 1");
		
		Optional<CompanyDto> dtoCompany = CompanyMapper.companyToDtoCompany(company);
		if (!dtoCompany.isPresent()) {
			fail();
		}
		assertEquals(dtoCompany.get().getName(),"company 1");
		assertEquals((int)dtoCompany.get().getId(), 1);
	}
	
	@Test
	public void testDtoToCompanyMapper() throws SQLException {
		
		CompanyDto dtoCompany = new CompanyDtoBuilder().setId(1).setName("test").build();
		
		Optional<Company> company = CompanyMapper.dtoCompanyToCompany(dtoCompany);

		if (!company.isPresent()) {
			fail();
		}
		assertEquals(company.get().getName(),"test");
		assertEquals((int)company.get().getId(), 1);
	}
}