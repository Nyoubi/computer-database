package com.excilys.computer_database.modelTest;


import org.junit.jupiter.api.Test;

import com.excilys.computer_database.model.CompanyBuilder;

import junit.framework.TestCase;

public class CompanyBuilderTest extends TestCase {
	
	@Test
	public void testCompanyBuilder() {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		companyBuilder = companyBuilder.setId(1)
									   .setName("Name");
		
		assertEquals((int)companyBuilder.getId(),1);
		assertEquals(companyBuilder.getName(),"Name");

	}
}