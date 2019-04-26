package com.excilys.computer_database.persistenceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.computer_database.Company;
import com.excilys.computer_database.CompanyBuilder;
import com.excilys.computer_database.config.AppConfigTest;
import com.excilys.computer_database.dao.DaoCompany;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class DaoCompanyTest {

	@Autowired
	static private DaoCompany daoCompany;
	
	private Company company;
	CompanyBuilder companyBuilder;
	
	@Autowired
	static GenericApplicationContext context;
	
	@BeforeAll
	public static void init() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
	}
	
	@BeforeEach
	public void setUp() {
		companyBuilder = new CompanyBuilder().setName("Company 1")
				.setId(1);

		company = companyBuilder.build();
	}

	@Test
	public void testListCompany() {
		List<Company> companies = daoCompany.findAll();
		assertTrue((int)companies.size() == 2);

		assertTrue(companies.get(0).equals(company));
	}


	@Test
	public void testFindById() {
		Company company2;
		company2 = daoCompany.findById(1).get();
		assertTrue(company.equals(company2));

		Optional<Company> optCompany= daoCompany.findById(3);

		assertFalse(optCompany.isPresent());
	}

	@Test
	public void testDeleteCompany() {
		company.setId(3);
		company.setName("Test Delete");
		daoCompany.save(company);
		daoCompany.deleteById(3);
		assertEquals(daoCompany.findById(3), Optional.empty());
		daoCompany.resetAutoIncrement(3);		
	}
}
