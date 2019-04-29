package com.excilys.computer_database.serviceTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.computer_database.binding_dto.CompanyDto;
import com.excilys.computer_database.binding_dto.CompanyDtoBuilder;
import com.excilys.computer_database.binding_exception.DaoException;
import com.excilys.computer_database.service.CompanyService;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class companyServiceTest {
	
	@Autowired
	static private CompanyService companyService;
	
	@Autowired
	static GenericApplicationContext context;

	@Test
	public void testListCompany() {
		ArrayList<CompanyDto> companies = new ArrayList<>();
		companies = companyService.listCompanies();
		assertTrue((int)companies.size() == 2);
	}


	@Test
	public void testFindById() {
		try {
			CompanyDto company2 = new CompanyDtoBuilder().setId(1).setName("Company 1").build();
			CompanyDto company = companyService.findCompanyById(1).get();
			assertTrue(company.equals(company2));

		} catch (DaoException e) {
			fail();
		}
	}
	
//	@Test
//	public void testDeleteCompany() {
//		try {
//			companyService.createCompany("delete");
//
//			companyService.deleteCompany(3);
//
//			assertFalse(companyService.findCompanyById(3).isPresent());
//
//			companyService.resetAutoIncrement(Integer.valueOf(2));
//
//		} catch (ExceptionModel|ExceptionDao e) {
//			fail();
//		}
//	}

	@Test
	public void testCheckCreateCompany() {
		try {
			companyService.checkDataCreateCompany("a");
		} catch (DaoException | javax.xml.bind.ValidationException e) {
			fail();
		}
		
		try {
			companyService.checkDataCreateCompany(null);
		} catch (DaoException | javax.xml.bind.ValidationException e) {
			assertTrue(true);
		}
	}
	

	
}
