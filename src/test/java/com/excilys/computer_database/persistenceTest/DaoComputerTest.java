package com.excilys.computer_database.persistenceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.app.AppConfigTest;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.model.Page.orderEnum;
import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.util.Util;

public class DaoComputerTest {

	static private DaoComputer daoComputer;

	private static Computer computer;
	private static Computer computer2;
	ComputerBuilder computerBuilder;

	static GenericApplicationContext context;


	@BeforeAll
	public static void start() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		daoComputer = context.getBean(DaoComputer.class);
	}

	@BeforeEach
	public void setUp() {
		computerBuilder = new ComputerBuilder().setName("Computer 4")
				.setId(4)
				.setIntroduced(Util.stringToTimestamp(("2000-01-01")).get())
				.setDiscontinued(Util.stringToTimestamp(("2000-01-06")).get())
				.setCompany(new Company(1, "Company 1"));

		computer = computerBuilder.build();
		computerBuilder.setId(0).setName("Computer 1");
		computer2 = computerBuilder.build();

	}
	
	@AfterEach
	public void end() {
		context.close();
	}

	@Test
	public void testCreateComputer() {
		try {
			daoComputer.createComputer(computer);
		} catch (DaoException e2) {
			fail();
		}
		computer2 = daoComputer.findComputerById(computer.getId()).get();
		assertNotNull(computer2);
		assertEquals(computer,computer2);

		try {
			daoComputer.deleteComputerById(computer.getId());
		} catch (DaoException e) {
			fail();
		}
		daoComputer.resetAutoIncrement(computer.getId()-1);
	}


	@Test
	public void testUpdateComputer() {
		try {
			daoComputer.createComputer(computer);
		} catch (DaoException e2) {
			fail();
		}
		computer.setName("updated");

		try {
			daoComputer.updateComputer(computer);
		} catch (DaoException e) {
			fail();
		}
		computer2 = daoComputer.findComputerById(computer.getId()).get();

		assertEquals(computer2,computer);
		
		try {
			daoComputer.deleteComputerById(computer.getId());
		} catch (DaoException e) {
			fail();
		}
		daoComputer.resetAutoIncrement(computer.getId()-1);
	}

	@Test
	public void testDeleteComputer() {
		try {
			daoComputer.createComputer(computer);
		} catch (DaoException e1) {
			fail();
		}
		try {
			daoComputer.deleteComputerById(computer.getId());
		} catch (DaoException e) {
			fail();
		}
		assertEquals(daoComputer.findComputerById(computer.getId()), Optional.empty());
		daoComputer.resetAutoIncrement(computer.getId()-1);		
	}

	@Test
	public void testFindComputerById() {
		computer = daoComputer.findComputerById(1).get();

		assertNotNull(computer);
		computer2.setId(1);
		computer2.setName("Computer 1");
		computer2.setIntroduced(Util.stringToTimestamp(("2000-01-01")).get());
		computer2.setDiscontinued(Util.stringToTimestamp(("2000-01-06")).get());
		computer2.setCompany(new Company(1,"Company 1"));
		assertEquals(computer, computer2);	
	}

	@Test
	public void testListComputerOrder() {
		List<Computer> list = new ArrayList<>();
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
	}

	@Test
	public void testListComputerSearch() {
		List<Computer> list = new ArrayList<>();
		list = daoComputer.listAllComputer("test","");
		assertTrue(list.get(0).getId() == 2 && list.size() == 1);
	}
}
