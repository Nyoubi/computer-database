package com.excilys.computer_database.persistenceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.appTest.AppConfigTest;
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
		daoComputer.insert(computer);
		computer2 = daoComputer.findById(computer.getId()).get();
		assertNotNull(computer2);
		assertEquals(computer,computer2);

		daoComputer.deleteById(computer.getId());
		
		daoComputer.resetAutoIncrement(computer.getId()-1);
	}


	@Test
	public void testUpdateComputer() {
		daoComputer.insert(computer);
		computer.setName("updated");

		daoComputer.update(computer);
		computer2 = daoComputer.findById(computer.getId()).get();

		assertEquals(computer2,computer);

		daoComputer.deleteById(computer.getId());
		daoComputer.resetAutoIncrement(computer.getId()-1);
	}

	@Test
	public void testDeleteComputer() {
		daoComputer.insert(computer);
		daoComputer.deleteById(computer.getId());
		assertEquals(daoComputer.findById(computer.getId()), Optional.empty());
		daoComputer.resetAutoIncrement(computer.getId()-1);		
	}

	@Test
	public void testFindComputerById() {
		computer = daoComputer.findById(1).get();
		assertNotNull(computer);
		computer2.setId(1);
		computer2.setName("Computer 1");
		computer2.setIntroduced(Util.stringToTimestamp(("2000-01-01")).get());
		computer2.setDiscontinued(Util.stringToTimestamp(("2000-01-06")).get());
		computer2.setCompany(new Company(2,"Company 2"));
		assertEquals(computer, computer2);	
	}

	@Test
	public void testListComputerOrderName() {
		List<Computer> list = new ArrayList<>();
		
		list = daoComputer.findAll("");
		assertTrue(list.get(0).getId() == 1);
		assertTrue(list.get(0).getName().equals("Computer 1"));

		list = daoComputer.findAll(orderEnum.NAME_ASC.getValue());
		assertTrue(list.get(1).getId() == 3 && list.get(2).getName().equals("test"));

		list = daoComputer.findAll(orderEnum.NAME_DESC.getValue());
		assertTrue(list.get(0).getId() == 2 	&& list.get(1).getName().equals("Computer 3"));
	}

	@Test
	public void testListComputerOrderIntro() {
		List<Computer> list = new ArrayList<>();
		
		list = daoComputer.findAll(orderEnum.INTRO_ASC.getValue());
		assertTrue(list.get(0).getId() == 1 && list.get(0).getName().equals("Computer 1"));

		list = daoComputer.findAll(orderEnum.INTRO_DESC.getValue());
		assertTrue(list.get(0).getId() == 1 && list.get(0).getName().equals("Computer 1"));
	}

	@Test
	public void testListComputerOrderDiscon() {
		List<Computer> list = new ArrayList<>();
		
		list = daoComputer.findAll(orderEnum.DISCON_ASC.getValue());
		assertTrue(list.get(0).getId() == 1 && list.get(0).getName().equals("Computer 1"));

		list = daoComputer.findAll(orderEnum.DISCON_DESC.getValue());
		assertTrue(list.get(0).getId() == 1 && list.get(0).getName().equals("Computer 1"));
	}

	@Test
	public void testListComputerOrderCompany() {
		List<Computer> list = new ArrayList<>();
		
		list = daoComputer.findAll(orderEnum.COMPANY_ASC.getValue());
		assertTrue(list.get(0).getCompany().getId() == 1 && list.get(2).getCompany().getId() == 2);
		
		list = daoComputer.findAll(orderEnum.COMPANY_DESC.getValue());
		assertTrue(list.get(0).getId() == 1);
	}

	@Test
	public void testListComputerSearch() {
		List<Computer> list = new ArrayList<>();
		list = daoComputer.findAll("test","");
		assertTrue(list.get(0).getId() == 2 && list.size() == 1);
	}
}
