package com.excilys.computer_database.dtoTest;


import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.dto.DtoComputerBuilder;
import com.excilys.computer_database.model.Company;

import junit.framework.TestCase;

public class DtoComputerBuilderTest extends TestCase {
	
	@Test
	public void testDtoComputerBuilder() {
		DtoComputerBuilder dtoComputerBuilder = new DtoComputerBuilder();
		dtoComputerBuilder = dtoComputerBuilder.setId(1)
					.setName("Name")
					.setIntroduced(Timestamp.valueOf("2010-01-01 11:11:11"))
					.setDiscontinued(null)
					.setCompany(new DtoCompany(1,"Company name"));
		
		assertEquals((int)dtoComputerBuilder.getId(),1);
		assertEquals(dtoComputerBuilder.getName(),"Name");
		assertEquals(dtoComputerBuilder.getIntroduced(),Timestamp.valueOf("2010-01-01 11:11:11"));
		assertEquals(dtoComputerBuilder.getDiscontinued(),null);
		assertEquals(dtoComputerBuilder.getCompany(),new Company(1,"Company name"));
	}
}