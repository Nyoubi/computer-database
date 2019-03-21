package com.excilys.computer_database.dtoTest;


import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.dto.DtoCompany;

import junit.framework.TestCase;

public class DtoCompanyTest extends TestCase {

	DtoCompany company1;
	DtoCompany company2;
	
	@BeforeEach
	public void setUp() {
		company1 = new DtoCompany(null,null);
		company2 = new DtoCompany(null,null);
	}
	
	@Test
	public void testEquals() {
		assertEquals(company1, company1);
		assertNotEquals(company1, null);
		assertNotEquals(company1, new Object());
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
	
	@Test
	public void testToString() {
		company1.setId(1);
		company1.setName("test");
		assertEquals(company1.toString(),"Id: '1', Name: 'test'");
	}
}