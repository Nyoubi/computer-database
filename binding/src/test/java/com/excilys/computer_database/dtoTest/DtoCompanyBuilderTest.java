package com.excilys.computer_database.dtoTest;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.excilys.computer_database.binding_dto.CompanyDtoBuilder;


public class DtoCompanyBuilderTest {
	
	@Test
	public void testDtoCompanyBuilder() {
		CompanyDtoBuilder dtoCompanyBuilder = new CompanyDtoBuilder();
		dtoCompanyBuilder = dtoCompanyBuilder.setId(1)
									   .setName("Name");
		
		assertEquals(1,(int)dtoCompanyBuilder.getId());
		assertEquals("Name",dtoCompanyBuilder.getName());

	}
}