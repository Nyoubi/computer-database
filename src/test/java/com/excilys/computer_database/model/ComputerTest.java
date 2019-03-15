package com.excilys.computer_database.model;


import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.excilys.computer_database.util.PojoTestUtils;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {

	public ComputerTest() {}
	
	@Test
	public void testAccesors() {
		 PojoTestUtils.validateAccessors(Computer.class);
	}
	
	@Test
	public void equalsTest() {
		ComputerBuilder computerBuilder1 = new ComputerBuilder();
		Computer computer1 = computerBuilder1.build();
		ComputerBuilder computerBuilder2 = new ComputerBuilder();
		Computer computer2;
		
		
		assertEquals(computer1, computer1);
		assertNotEquals(computer1, null);
		assertNotEquals(computer1, new Object());
		
		computer1 = computerBuilder1.setId(null).build();
		computer2 = computerBuilder2.setId(null).build();
		assertEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setId(0).build();
		computer2 = computerBuilder2.setId(1).build();
		assertNotEquals(computer1, computer2);
		
		computer2 = computerBuilder2.setId(0).build();
		
		computer1 = computerBuilder1.setName(null).build();
		computer2 = computerBuilder2.setName(null).build();
		assertEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setName(null).build();
		computer2 = computerBuilder2.setName("notNull").build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setName("notNull").build();
		computer2 = computerBuilder2.setName(null).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setName("Different").build();
		computer2 = computerBuilder2.setName("Names").build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setName("same").build();
		computer2 = computerBuilder2.setName("same").build();
		assertEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setIntroduced(null).build();
		computer2 = computerBuilder2.setIntroduced(null).build();
		assertEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setIntroduced(null).build();
		computer2 = computerBuilder2.setIntroduced(Timestamp.valueOf("2000-01-01 00:00:00")).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setIntroduced(Timestamp.valueOf("2000-01-01 00:00:00")).build();
		computer2 = computerBuilder2.setIntroduced(null).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setIntroduced(Timestamp.valueOf("2011-11-11 11:11:11")).build();
		computer2 = computerBuilder2.setIntroduced(Timestamp.valueOf("2000-01-01 00:00:00")).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setIntroduced(Timestamp.valueOf("2000-01-01 00:00:00")).build();
		computer2 = computerBuilder2.setIntroduced(Timestamp.valueOf("2000-01-01 00:00:00")).build();
		assertEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setDiscontinued(null).build();
		computer2 = computerBuilder2.setDiscontinued(null).build();
		assertEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setDiscontinued(null).build();
		computer2 = computerBuilder2.setDiscontinued(Timestamp.valueOf("2000-01-01 00:00:00")).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setDiscontinued(Timestamp.valueOf("2000-01-01 00:00:00")).build();
		computer2 = computerBuilder2.setDiscontinued(null).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setDiscontinued(Timestamp.valueOf("2011-11-11 11:11:11")).build();
		computer2 = computerBuilder2.setDiscontinued(Timestamp.valueOf("2000-01-01 00:00:00")).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setDiscontinued(Timestamp.valueOf("2000-01-01 00:00:00")).build();
		computer2 = computerBuilder2.setDiscontinued(Timestamp.valueOf("2000-01-01 00:00:00")).build();
		assertEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setCompany(null).build();
		computer2 = computerBuilder2.setCompany(null).build();
		assertEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setCompany(null).build();
		computer2 = computerBuilder2.setCompany(new Company(1,"test")).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setCompany(new Company(1,"test")).build();
		computer2 = computerBuilder2.setCompany(null).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setCompany(new Company(1,"test")).build();
		computer2 = computerBuilder2.setCompany(new Company(2,"test")).build();
		assertNotEquals(computer1, computer2);
		
		computer1 = computerBuilder1.setCompany(new Company(1,"test")).build();
		computer2 = computerBuilder2.setCompany(new Company(1,"test")).build();
		assertEquals(computer1, computer2);
	}
	
	

}