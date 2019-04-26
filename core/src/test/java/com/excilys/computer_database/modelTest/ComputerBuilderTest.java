package com.excilys.computer_database.modelTest;


import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.excilys.computer_database.Company;
import com.excilys.computer_database.ComputerBuilder;

import junit.framework.TestCase;

public class ComputerBuilderTest extends TestCase {
	
	@Test
	public void testComputerBuilder() {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder = computerBuilder.setId(1)
					.setName("Name")
					.setIntroduced(Timestamp.valueOf("2010-01-01 11:11:11"))
					.setDiscontinued(null)
					.setCompany(new Company(1,"Company name"));
		
		assertEquals((int)computerBuilder.getId(),1);
		assertEquals(computerBuilder.getName(),"Name");
		assertEquals(computerBuilder.getIntroduced(),Timestamp.valueOf("2010-01-01 11:11:11"));
		assertEquals(computerBuilder.getDiscontinued(),null);
		assertEquals(computerBuilder.getCompany(),new Company(1,"Company name"));
	}
}