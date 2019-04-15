package com.excilys.computer_database.persistenceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.app.AppConfigTest;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.model.Page.orderEnum;
import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.util.Util;

public class DaoComputerTest {

	static private DaoComputer daoComputer;

	private Computer computer;
	private Computer computer2;
	ComputerBuilder computerBuilder;

	static GenericApplicationContext context;


	@BeforeAll
	public static void start() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		daoComputer = context.getBean(DaoComputer.class);
	}

	@AfterAll
	public static void end() {
		context.close();
	}

	@BeforeEach
	public void setUp() {
		computerBuilder = new ComputerBuilder().setName("Computer 1")
				.setId(0)
				.setIntroduced(Util.stringToTimestamp(("2000-12-12")).get())
				.setDiscontinued(Util.stringToTimestamp(("2000-12-13")).get())
				.setCompany(new Company(1, "Company 1"));

		computer = computerBuilder.build();
		computer2 = computerBuilder.build();
		computer.setId(null);
		computer2.setId(null);

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
		assertEquals(test,Integer.valueOf(4));
		computer2.setId(test);
		try {
			computer = daoComputer.findComputerById(test).get();
		} catch (ExceptionDao e1) {
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
		} catch (ExceptionDao e1) {
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
		} catch (ExceptionDao e) {
			fail();
		}
		daoComputer.resetAutoIncrement(created);		
	}

	@Test
	public void testFindComputerById() {
		try {
			computer = daoComputer.findComputerById(1).get();
		} catch (ExceptionDao e1) {
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

	@Test
	public void testListComputerOrder() {
		ArrayList<Computer> list = new ArrayList<>();
		try {
			list = daoComputer.listAllComputer("");
			assertTrue(list.get(0).getId() == 1 && list.get(0).getName().equals("Computer 1"));

			list = daoComputer.listAllComputer(orderEnum.NAME_ASC.getValue());
			assertTrue(list.get(1).getId() == 3 && list.get(2).getName().equals("test"));

			list = daoComputer.listAllComputer(orderEnum.NAME_DESC.getValue());
			assertTrue(list.get(0).getId() == 2 	&& list.get(1).getName().equals("Computer 3"));

			list = daoComputer.listAllComputer(orderEnum.INTRO_ASC.getValue());
			assertTrue(list.get(0).getId() == 1 && list.get(0).getName().equals("Computer 1"));

			list = daoComputer.listAllComputer(orderEnum.INTRO_DESC.getValue());
			assertTrue(list.get(0).getId() == 1 && list.get(0).getName().equals("Computer 1"));


			list = daoComputer.listAllComputer(orderEnum.DISCON_ASC.getValue());
			assertTrue(list.get(0).getId() == 1 && list.get(0).getName().equals("Computer 1"));

			list = daoComputer.listAllComputer(orderEnum.DISCON_DESC.getValue());
			assertTrue(list.get(0).getId() == 1 && list.get(0).getName().equals("Computer 1"));

			list = daoComputer.listAllComputer(orderEnum.COMPANY_ASC.getValue());
			assertTrue(list.get(0).getCompany().getId() == 1 && list.get(1).getCompany().getId() == 1);

			list = daoComputer.listAllComputer(orderEnum.COMPANY_DESC.getValue());
			assertTrue(list.get(0).getId() == 2);

		} catch (ExceptionDao e) {
			fail();
		}
	}

	@Test
	public void testListComputerSearch() {
		ArrayList<Computer> list = new ArrayList<>();
		try {
			list = daoComputer.listAllComputer("test","");
			assertTrue(list.get(0).getId() == 2 && list.size() == 1);
		} catch (ExceptionDao e) {
			fail();
		}
	}
}
