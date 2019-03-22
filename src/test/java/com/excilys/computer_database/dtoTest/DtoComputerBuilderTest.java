package com.excilys.computer_database.dtoTest;


import org.junit.jupiter.api.Test;

import com.excilys.computer_database.dto.DtoComputerBuilder;

import junit.framework.TestCase;

public class DtoComputerBuilderTest extends TestCase {
	
	@Test
	public void testDtoComputerBuilder() {
		DtoComputerBuilder dtoComputerBuilder = new DtoComputerBuilder();
		dtoComputerBuilder = dtoComputerBuilder.setId(1)
					.setName("Name")
					.setIntroduced("2010-01-01 11:11:11")
					.setDiscontinued("")
					.setCompanyId(1)
					.setCompanyName("Company name");
		
		assertEquals((int)dtoComputerBuilder.getId(),1);
		assertEquals(dtoComputerBuilder.getName(),"Name");
		assertEquals(dtoComputerBuilder.getIntroduced(),"2010-01-01 11:11:11");
		assertEquals(dtoComputerBuilder.getDiscontinued(),"");
		assertEquals(dtoComputerBuilder.getCompanyId(), 1);
		assertEquals(dtoComputerBuilder.getCompanyName(),"Company name");

	}
}