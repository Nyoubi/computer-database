package com.excilys.computer_database.persistenceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.persistence.Dao;
import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.util.Util;

public class DaoComputerTest {
	
	private DaoComputer daoComputer;
	private Computer computer;
	private Computer computer2;
	ComputerBuilder computerBuilder;
	@BeforeEach
	public void setUp() {
		daoComputer = DaoComputer.getInstance("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/computer-database-test",
											  "admintest","test1234");
		computerBuilder = new ComputerBuilder().setName("Computer 1")
				.setId(0)
				.setIntroduced(Util.stringToTimestamp(("2000-12-12")).get())
				.setDiscontinued(Util.stringToTimestamp(("2000-12-13")).get())
				.setCompany(new Company(1, "Company 1"));
		
		try {
		computer = computerBuilder.build();
		computer2 = computerBuilder.build();
		computer.setId(null);
		computer2.setId(null);
		} catch (ExceptionModel e) {
			fail();
		}
	}
	
	@Test
	public void testConnection() {
		try (Connection conn = Dao.openConnection()){
			assertNotNull(conn);
		} catch (SQLException e) {
			fail("Exception catched when trying to connect.");
		}
	}

	@Test
	public void testCreateComputer() {
		Integer test = daoComputer.createComputer(computer).get();
		assertNotNull(test);
		assertEquals(test,Integer.valueOf(2));
		computer2.setId(test);
		try {
			computer = daoComputer.findComputerById(test).get();
		} catch (ExceptionModel e1) {
			fail();
		}
		assertNotNull(computer);
		assertEquals(computer,computer2);
		
		try {
			daoComputer.deleteComputerById(test);
		} catch (ExceptionDao e) {
			fail();
		}
		daoComputer.resetAutoIncrement(test);
	}
	
	
	@Test
	public void testUpdateComputer() {
		Integer created = daoComputer.createComputer(computer).get();

		computer.setId(created);
		computer2.setId(created);
		computer.setName("updated");
		
		try {
			daoComputer.updateComputer(computer);
		} catch (ExceptionDao e) {
			fail();
		}
		try {
			computer = daoComputer.findComputerById(created).get();
		} catch (ExceptionModel e1) {
			fail();
		}
		
		assertNotEquals(computer2,computer);
		
		computer2.setName("updated");
		
		assertEquals(computer,computer2);
		
		try {
			daoComputer.deleteComputerById(created);
		} catch (ExceptionDao e) {
			fail();
		}
		daoComputer.resetAutoIncrement(created);
	}
	
	@Test
	public void testDeleteComputer() {
		Integer created = daoComputer.createComputer(computer).get();
		try {
			daoComputer.deleteComputerById(created);
		} catch (ExceptionDao e) {
			fail();
		}
		try {
			assertEquals(daoComputer.findComputerById(created), Optional.empty());
		} catch (ExceptionModel e) {
			fail();
		}
		daoComputer.resetAutoIncrement(created);		
	}
	
	@Test
	public void testFindComputerById() {
		try {
			computer = daoComputer.findComputerById(1).get();
		} catch (ExceptionModel e1) {
			fail();
		}
		
		assertNotNull(computer);
		computer2.setId(1);
		computer2.setName("Computer 1");
		computer2.setIntroduced(Util.stringToTimestamp(("2000-12-12")).get());
		computer2.setDiscontinued(Util.stringToTimestamp(("2000-12-13")).get());
		computer2.setCompany(new Company(1,"Company 1"));
		
		assertEquals(computer, computer2);	
	}
	
}
