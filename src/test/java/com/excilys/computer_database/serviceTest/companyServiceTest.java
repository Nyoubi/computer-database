package com.excilys.computer_database.serviceTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.appTest.AppConfigTest;
import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.CompanyDtoBuilder;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.service.CompanyService;

public class companyServiceTest {
	static private CompanyService companyService;
	static GenericApplicationContext context;
	
	@BeforeAll
	public static void init() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		companyService = context.getBean(CompanyService.class);
	}

	@Test
	public void testListCompany() {
		ArrayList<CompanyDto> companies = new ArrayList<>();
		try {
			companies = companyService.listCompanies();
		} catch (DaoException e) {
			fail();
		}
		
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
