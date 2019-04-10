package com.excilys.computer_database.persistenceTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.app.AppConfigTest;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;
import com.excilys.computer_database.persistence.DaoCompany;

public class DaoCompanyTest {

	static private DaoCompany daoCompany;
	private Company company;
	CompanyBuilder companyBuilder;
	static GenericApplicationContext context;
	
	@BeforeAll
	public static void init() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		daoCompany = context.getBean(DaoCompany.class);
	}
	
	@BeforeEach
	public void setUp() {
		companyBuilder = new CompanyBuilder().setName("Company 1")
				.setId(1);

		company = companyBuilder.build();
	}

	@Test
	public void testListCompany() {
		ArrayList<Company> companies = daoCompany.listAllCompany();
		assertTrue((int)companies.size() == 2);

		assertTrue(companies.get(0).equals(company));
	}


	@Test
	public void testFindById() {
		Company company2;
		try {
			company2 = daoCompany.findCompanyById(1).get();
			assertTrue(company.equals(company2));

			Optional<Company> optCompany= daoCompany.findCompanyById(3);

			assertFalse(optCompany.isPresent());

		} catch (ExceptionModel|ExceptionDao e) {
			fail();
		}
	}
}
