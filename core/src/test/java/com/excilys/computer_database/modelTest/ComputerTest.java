package com.excilys.computer_database.modelTest;


import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

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
	public void testEquals() {
		assertEquals(computer1, computer1);
		assertNotEquals(computer1, null);
		assertNotEquals(computer1, new Object());
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
	
	@Test
	public void testToString() {
		computer1 = new Computer(1,"Test",Timestamp.valueOf("2000-01-01 00:00:00"),Timestamp.valueOf("2000-01-01 02:00:00"),new Company(1,"Name"));
		assertEquals(computer1.toString(),"Id: 1, Name: Test, Introduced: 2000-01-01 00:00:00.0, Discontinued: 2000-01-01 02:00:00.0, Company: (Id: '1', Name: 'Name')");
	}
	
	
}