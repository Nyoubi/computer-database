package com.excilys.computer_database.dtoTest;


import org.junit.jupiter.api.Test;

import com.excilys.computer_database.dto.CompanyDtoBuilder;

import junit.framework.TestCase;


public class DtoCompanyBuilderTest extends TestCase {
	
	@Test
	public void testDtoCompanyBuilder() {
		CompanyDtoBuilder dtoCompanyBuilder = new CompanyDtoBuilder();
		dtoCompanyBuilder = dtoCompanyBuilder.setId(1)
									   .setName("Name");
		
		assertEquals((int)dtoCompanyBuilder.getId(),1);
		assertEquals(dtoCompanyBuilder.getName(),"Name");

	}
}