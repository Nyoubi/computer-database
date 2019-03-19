package com.excilys.computer_database.modelTest;


import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.utilTest.PojoTestUtils;

import junit.framework.TestCase;

public class CompanyTest extends TestCase {

	Company company1;
	Company company2;
	
	@BeforeEach
	public void setUp() {
		company1 = new Company(null,null);
		company2 = new Company(null,null);
	}
	
	@Test
	public void equalsTest() {
		assertEquals(company1, company1);
		assertNotEquals(company1, null);
		assertNotEquals(company1, new Object());
	}
	
	@Test
	public void testAccesors() {
		 PojoTestUtils.validateAccessors(Company.class);
	}
	
	@Test
	public void equalsTestId() {
		company1.setId(0);
		assertNotEquals(company1, company2);
		
		company2.setId(1);
		assertNotEquals(company1, company2);
		
		company2.setId(0);
		assertEquals(company1, company2);
	}
	
	@Test
	public void equalsTestName() {
		company2.setName("notNull");
		assertNotEquals(company1, company2);
		
		company1.setName("Different");
		assertNotEquals(company1, company2);
		
		company1.setName("same");
		company2.setName("same");
		assertEquals(company1, company2);
	}
	@Test
	public void equalsTestCompany() {
		equalsTestId();
		equalsTestName();
	}
}