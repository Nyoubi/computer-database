package com.excilys.computer_database.persistenceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.computer_database.app.App;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.util.Util;

public class DaoComputerTest {

	private DaoComputer daoComputer;
	private Computer computer;
	private Computer computer2;
	ComputerBuilder computerBuilder;
	@BeforeEach
	public void setUp() {
		daoComputer = DaoComputer.getInstance(App.dataSourceTest);
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
	public void testCreateComputer() {
		Integer test = null;
		try {
			test = daoComputer.createComputer(computer).get();
		} catch (ExceptionDao e2) {
			fail();
		}
		assertNotNull(test);
		assertEquals(test,Integer.valueOf(2));
		computer2.setId(test);
		try {
			computer = daoComputer.findComputerById(test).get();
		} catch (ExceptionModel | ExceptionDao e1) {
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
		Integer created = null;
		try {
			created = daoComputer.createComputer(computer).get();
		} catch (ExceptionDao e2) {
			fail();
		}

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
		} catch (ExceptionModel | ExceptionDao e1) {
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
		Integer created = null;
		try {
			created = daoComputer.createComputer(computer).get();
		} catch (ExceptionDao e1) {
			fail();
		}
		try {
			daoComputer.deleteComputerById(created);
		} catch (ExceptionDao e) {
			fail();
		}
		try {
			assertEquals(daoComputer.findComputerById(created), Optional.empty());
		} catch (ExceptionModel | ExceptionDao e) {
			fail();
		}
		daoComputer.resetAutoIncrement(created);		
	}

	@Test
	public void testFindComputerById() {
		try {
			computer = daoComputer.findComputerById(1).get();
		} catch (ExceptionModel | ExceptionDao e1) {
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
