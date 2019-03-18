package com.excilys.computer_database.model;


import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.util.PojoTestUtils;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {

	Computer computer1;
	Computer computer2;
	
	@BeforeEach
	public void setUp() {
		computer1 = new Computer(null,null,null,null,null);
		computer2 = new Computer(null,null,null,null,null);
	}
	
	@Test
	public void equalsTest() {
		assertEquals(computer1, computer1);
		assertNotEquals(computer1, null);
		assertNotEquals(computer1, new Object());
	}
	
	@Test
	public void testAccesors() {
		 PojoTestUtils.validateAccessors(Computer.class);
	}
	
	@Test
	public void equalsTestId() {
		computer1.setId(0);
		assertNotEquals(computer1, computer2);
		
		computer2.setId(1);
		assertNotEquals(computer1, computer2);
		
		computer2.setId(0);
		assertEquals(computer1, computer2);
	}
	
	@Test
	public void equalsTestName() {
		computer2.setName("notNull");
		assertNotEquals(computer1, computer2);
		
		computer1.setName("Different");
		assertNotEquals(computer1, computer2);
		
		computer1.setName("same");
		computer2.setName("same");
		assertEquals(computer1, computer2);
	}
	
	@Test
	public void equalsTestIntroduced() {		
		computer2.setIntroduced(Timestamp.valueOf("2000-01-01 00:00:00"));
		assertNotEquals(computer1, computer2);
		
		computer1.setIntroduced(Timestamp.valueOf("2000-01-01 00:00:00"));
		computer2.setIntroduced(null);
		assertNotEquals(computer1, computer2);
		
		computer2.setIntroduced(Timestamp.valueOf("2011-11-11 11:11:11"));
		assertNotEquals(computer1, computer2);
		
		computer1.setIntroduced(Timestamp.valueOf("2011-11-11 11:11:11"));
		assertEquals(computer1, computer2);
	}

	@Test
	public void equalsTestDiscontinued() {
		computer2.setDiscontinued(Timestamp.valueOf("2000-01-01 00:00:00"));
		assertNotEquals(computer1, computer2);
		
		computer1.setDiscontinued(Timestamp.valueOf("2000-01-01 00:00:00"));
		computer2.setDiscontinued(null);
		assertNotEquals(computer1, computer2);
		
		computer2.setDiscontinued(Timestamp.valueOf("2011-11-11 11:11:11"));
		assertNotEquals(computer1, computer2);
		
		computer1.setDiscontinued(Timestamp.valueOf("2011-11-11 11:11:11"));
		assertEquals(computer1, computer2);
	}
	
	@Test
	public void equalsTestCompany() {
		computer1.setCompany(new Company(1,"NotNull"));
		assertNotEquals(computer1, computer2);
		
		computer2.setCompany(new Company(1,"NotNull"));
		computer1.setCompany(null);
		assertNotEquals(computer1, computer2);
		
		computer1.setCompany(new Company(2,"NotNull"));
		assertNotEquals(computer1, computer2);
		
		computer2.setCompany(new Company(2,"NotNull"));
		assertEquals(computer1, computer2);
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