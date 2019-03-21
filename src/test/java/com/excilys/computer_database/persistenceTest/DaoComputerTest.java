package com.excilys.computer_database.persistenceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.persistence.Dao;
import com.excilys.computer_database.persistence.DaoComputer;

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
				.setIntroduced(Timestamp.valueOf("2000-12-12 10:10:10"))
				.setDiscontinued(Timestamp.valueOf("2000-12-12 11:10:10"))
				.setCompany(null);
		computer = computerBuilder.build();
		computer2 = computerBuilder.build();
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
		computer = daoComputer.findComputerById(test).get();
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
		computer = daoComputer.findComputerById(created).get();
		
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
		assertEquals(daoComputer.findComputerById(created), Optional.empty());
		daoComputer.resetAutoIncrement(created);		
	}
	
	@Test
	public void testFindComputerById() {
		computer = daoComputer.findComputerById(1).get();
		
		assertNotNull(computer);
		computer2.setId(1);
		computer2.setName("Computer 1");
		computer2.setIntroduced(Timestamp.valueOf("2000-12-12 10:10:10"));
		computer2.setDiscontinued(Timestamp.valueOf("2000-12-12 11:10:10"));
		computer2.setCompany(new Company(1,"Company 1"));
		
		assertEquals(computer, computer2);
		
		try {
			daoComputer.deleteComputerById(1);
		} catch (ExceptionDao e) {
			fail();
		}
		daoComputer.resetAutoIncrement(1);		
	}
	
}
