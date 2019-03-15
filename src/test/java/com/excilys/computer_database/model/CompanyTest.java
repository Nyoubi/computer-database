package com.excilys.computer_database.model;


import static org.junit.Assert.assertNotEquals;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.util.PojoTestUtils;

import junit.framework.TestCase;

public class CompanyTest extends TestCase {

	public CompanyTest() {}
	
	@Test
	public void testAccesors() {
		 PojoTestUtils.validateAccessors(Company.class);
	}
	
	@Test
	public void equalsTest() {
		CompanyBuilder companyBuilder1 = new CompanyBuilder();
		Company company1 = companyBuilder1.build();
		CompanyBuilder companyBuilder2 = new CompanyBuilder();
		Company company2;
		

		assertEquals(company1, company1);
		assertNotEquals(company1, null);
		assertNotEquals(company1, new Object());
		
		company1 = companyBuilder1.setId(null).build();
		company2 = companyBuilder2.setId(null).build();
		assertEquals(company1, company2);
		
		company1 = companyBuilder1.setId(0).build();
		company2 = companyBuilder2.setId(1).build();
		assertNotEquals(company1, company2);
		
		company2 = companyBuilder2.setId(0).build();	
		
		company1 = companyBuilder1.setName(null).build();
		company2 = companyBuilder2.setName(null).build();
		assertEquals(company1, company2);
		
		company1 = companyBuilder1.setName(null).build();
		company2 = companyBuilder2.setName("notNull").build();
		assertNotEquals(company1, company2);
				
		company1 = companyBuilder1.setName("notNull").build();
		company2 = companyBuilder2.setName(null).build();
		assertNotEquals(company1, company2);
		
		company1 = companyBuilder1.setName("Different").build();
		company2 = companyBuilder2.setName("Names").build();
		assertNotEquals(company1, company2);
		
		company1 = companyBuilder1.setName("same").build();
		company2 = companyBuilder2.setName("same").build();
		assertEquals(company1, company2);
	}
	
	

}