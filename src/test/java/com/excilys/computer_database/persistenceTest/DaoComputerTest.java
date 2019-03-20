package com.excilys.computer_database.persistenceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.persistence.DaoComputer;

public class DaoComputerTest {
	
	private DaoComputer daoComputer;
	private Computer computer;
	private Computer computer2;
	ComputerBuilder computerBuilder;
	@BeforeEach
	public void setUp() {
		daoComputer = DaoComputer.getInstance();
		computerBuilder = new ComputerBuilder().setName("Test")
				.setIntroduced(Timestamp.valueOf("2000-12-12 10:10:10"))
				.setDiscontinued(Timestamp.valueOf("2000-12-12 11:10:10"))
				.setCompany(null);
		computer = computerBuilder.build();
		computer2 = computerBuilder.build();
	}

	@Test
	public void testCreateComputer() {
		Integer test = 0;
		test = daoComputer.createComputer(computer);
		assertNotNull(test);
		assertNotEquals(test,Integer.valueOf(0));
		computer2.setId(test);

		computer = daoComputer.findComputerById(test).get();
		assertNotNull(computer);
		assertEquals(computer,computer2);
		
		daoComputer.deleteComputerById(test);
		daoComputer.resetAutoIncrement(test);
	}
	
	
	@Test
	public void testUpdateComputer() {
		Integer created = 0;
		created = daoComputer.createComputer(computer);
		assertNotNull(created);

		computer.setId(created);
		computer2.setId(created);
		computer.setName("updated");
		
		Boolean test = daoComputer.updateComputer(computer);
		assertTrue(test);
		computer = daoComputer.findComputerById(created).get();
		
		assertNotEquals(computer2,computer);
		
		computer2.setName("updated");
		
		assertEquals(computer,computer2);
		
		daoComputer.deleteComputerById(created);
		daoComputer.resetAutoIncrement(created);
	}
	
	@Test
	public void testDeleteComputer() {
		Integer created = daoComputer.createComputer(computer);
		assertNotNull(daoComputer.createComputer(computer));
		assertTrue(daoComputer.deleteComputerById(created));
		assertEquals(daoComputer.findComputerById(created), Optional.empty());
		daoComputer.resetAutoIncrement(created);		
	}
	
	@Test
	public void testFindComputerById() {
		Integer created = daoComputer.createComputer(computer);
		computer2 = daoComputer.findComputerById(created).get();
		computer.setId(created);
		assertNotNull(computer2);
		assertEquals(computer2, computer);
		daoComputer.deleteComputerById(created);
		daoComputer.resetAutoIncrement(created);		
	}
	
}
