package com.excilys.computer_database.dtoTest;


import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.binding_dto.CompanyDto;

import junit.framework.TestCase;

public class DtoCompanyTest extends TestCase {

	CompanyDto company1;
	CompanyDto company2;
	
	@BeforeEach
	public void setUp() {
		company1 = new CompanyDto(0,"Name");
		company2 = new CompanyDto(0,"Name");
	}
	
	@Test
	public void testEquals() {
		assertEquals(company1, company1);
		assertNotEquals(company1, null);
		assertNotEquals(company1, new Object());
	}
	
	@Test
	public void equalsTestId() {
			
		company2.setId(1);
		assertNotEquals(company1, company2);
		
	}
	
	@Test
	public void equalsTestName() {
		
		company2.setName("Test");
		assertNotEquals(company1, company2);
		
	}
	@Test
	public void equalsTestCompany() {
		equalsTestId();
		equalsTestName();
	}
	
	@Test
	public void testToString() {
		assertEquals(company1.toString(),"Id: '0', Name: 'Name'");
	}
}