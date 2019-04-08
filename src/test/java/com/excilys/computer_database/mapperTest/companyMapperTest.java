package com.excilys.computer_database.mapperTest;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.dto.DtoCompanyBuilder;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;

@ExtendWith(MockitoExtension.class)
public class companyMapperTest {
	
	@Mock
	ResultSet rs;
	
	@Mock
	ResultSet rs2;
	
	@Test
	public void testCompanyToDtoMapper() throws SQLException {
		
		Mockito.when(rs.getInt("cId")).thenReturn(Integer.valueOf(1));
		Mockito.when(rs.getString("cName")).thenReturn("Company name");
		
		Mockito.when(rs2.getInt("cId")).thenReturn(Integer.valueOf(0));
		Mockito.when(rs2.getString("cName")).thenReturn("Company null");
		
		Company company = CompanyMapper.resultSetToCompany(rs);
		assertEquals((int)company.getId(), 1);
		assertEquals(company.getName(),"Company name");
		
		Optional<DtoCompany> dtoCompany = CompanyMapper.companyToDtoCompany(company);
		if (!dtoCompany.isPresent()) {
			fail();
		}
		assertEquals(dtoCompany.get().getName(),"Company name");
		assertEquals((int)dtoCompany.get().getId(), 1);
		
		company = CompanyMapper.resultSetToCompany(rs2);
		assertNull(company);
	}
	
	@Test
	public void testDtoToCompanyMapper() throws SQLException {
		
		DtoCompany dtoCompany = new DtoCompanyBuilder().setId(1).setName("test").build();
		
		Optional<Company> company = CompanyMapper.dtoCompanyToCompany(dtoCompany);

		if (!company.isPresent()) {
			fail();
		}
		assertEquals(company.get().getName(),"test");
		assertEquals((int)company.get().getId(), 1);
	}
}