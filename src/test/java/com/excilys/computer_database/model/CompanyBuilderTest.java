package com.excilys.computer_database.model;


import org.junit.jupiter.api.Test;

import com.excilys.computer_database.util.PojoTestUtils;

import junit.framework.TestCase;

public class CompanyBuilderTest extends TestCase {
	
	@Test
	public void testAccesors() {
		 PojoTestUtils.validateAccessors(CompanyBuilderTest.class);
	}
	
	@Test
	public void testCompanyBuilder() {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		companyBuilder = companyBuilder.setId(1)
									   .setName("Name");
		
		assertEquals((int)companyBuilder.getId(),1);
		assertEquals(companyBuilder.getName(),"Name");

	}
}