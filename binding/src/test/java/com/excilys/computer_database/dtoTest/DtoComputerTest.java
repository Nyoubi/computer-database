package com.excilys.computer_database.dtoTest;


import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.binding_dto.ComputerDto;

import junit.framework.TestCase;

public class DtoComputerTest extends TestCase {

	ComputerDto computer1;
	ComputerDto computer2;
	
	@BeforeEach
	public void setUp() {
		computer1 = new ComputerDto(0,"Name","2000-01-01 01:01:01","2000-01-01 01:01:05",1,"Name");
		computer2 = new ComputerDto(0,"Name","2000-01-01 01:01:01","2000-01-01 01:01:05",1,"Name");
	}
	
	@Test
	public void testEquals() {
		assertEquals(computer1, computer1);
		assertNotEquals(computer1, null);
		assertNotEquals(computer1, new Object());
	}
	
	@Test
	public void equalsTestId() {
				
		computer2.setId(1);
		assertNotEquals(computer1, computer2);
	}
	
	@Test
	public void equalsTestName() {
		
		computer2.setName("Test");
		assertNotEquals(computer1, computer2);
	}
	
	@Test
	public void equalsTestIntroduced() {		
		
		computer2.setIntroduced("2000-01-01 01:00:01");
		assertNotEquals(computer1, computer2);
	}

	@Test
	public void equalsTestDiscontinued() {
		
		computer2.setDiscontinued("2000-01-01 01:01:02");
		assertNotEquals(computer1, computer2);
	}
	
	@Test
	public void equalsTestCompany() {
		
		computer2.setCompanyName("Test");
		assertNotEquals(computer1, computer2);
				
		computer2.setCompanyId(0);
		computer2.setName("Name");
		assertNotEquals(computer1, computer2);
		
		computer2.setCompanyId(1);
		assertNotEquals(computer1, computer2);
	}
	
	@Test
	public void equalsTestComputer() {
		equalsTestId();
		equalsTestName();
		equalsTestIntroduced();
		equalsTestDiscontinued();
		equalsTestCompany();
	}

}