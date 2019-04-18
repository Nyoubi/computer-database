package com.excilys.computer_database.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.app.AppConfigTest;
import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.dto.ComputerDtoBuilder;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.InvalidInputException;
import com.excilys.computer_database.exception.ModelException;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.util.Util;

public class computerServiceTest {
	static private ComputerService computerService;
	static private DaoComputer daoComputer;
	static GenericApplicationContext context;
	
	@BeforeAll
	public static void init() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		computerService = context.getBean(ComputerService.class);
		daoComputer = context.getBean(DaoComputer.class);
	}

	@Test
	public void testListComputers() {
		List<ComputerDto> computers = new ArrayList<>();
		try {
			computers = computerService.listAllComputer("");
		} catch (DaoException e) {
			fail();
		}
		assertTrue((int)computers.size() == 3);
	}

	@Test
	public void testListComputersSearch() {
		List<ComputerDto> computers = new ArrayList<>();
		try {
			computers = computerService.listAllComputer("test","");
		} catch (DaoException e) {
			fail();
		}
		assertTrue((int)computers.size() == 1);
		assertTrue(computers.get(0).getId() == 2);
	}

	@Test
	public void testFindById() {
		try {
			ComputerDto computer2 = new ComputerDtoBuilder().setId(2).setName("test").setCompanyId(2).setCompanyName("Company 2").build();
			ComputerDto computer = computerService.showDetails("2").get();
			assertEquals(computer.toString(),computer2.toString());

		} catch (DaoException | InvalidInputException e) {
			fail();
		}
	}
	
	@Test
	public void testDeleteComputer() {
		try {
			Computer computer = new ComputerBuilder().setId(4).setName("deleted").build();
			computerService.createComputer(computer);
			computerService.deleteComputer("4");

			daoComputer.resetAutoIncrement(Integer.valueOf(3));

			computerService.showDetails("4").isPresent();
		} catch (ModelException|DaoException | InvalidInputException e) {
			assertTrue(true);
		}
	}
	
	
	@Test
	public void testPageComputer() {
		try {
			Page<ComputerDto> test = computerService.pageDtoComputer("testurl","1","10","test","");
			assertTrue(test.getContent().size() == 1);
			
			test = computerService.pageDtoComputer("testurl","1","10","","nameDesc");
			assertTrue(test.getContent().size() == 3);
			assertTrue(test.getContent().get(0).getName().equals("test"));
			
			test = computerService.pageDtoComputer("testurl","1","10","Computer","companyAsc");
			assertTrue(test.getContent().size() == 2);
			assertTrue(test.getContent().get(0).getName().equals("Computer 3"));

			test = computerService.pageDtoComputer("testurl","1","10",null,"");
			assertTrue(test.getContent().size() == 3);
			
		} catch (DaoException | ModelException e) {
			fail();
		}
			
	}
	
	@Test
	public void testGetOrder() {
		assertTrue(computerService.getOrder(null) == "");
		assertTrue(computerService.getOrder("nameAsc") == "ORDER BY c.name ASC");
	}
	
	@Test
	public void testUpdateComputer() {
		try {
			Computer computer = new ComputerBuilder().setId(1).setName("testUpdate")
													 .setIntroduced(Util.stringToTimestamp("2000-01-01").get())
													 .setDiscontinued(Util.stringToTimestamp("2000-01-06").get())
													 .setCompany(new Company(2,"Company 2")).build();
			
			computerService.updateComputer(computer);
			assertTrue(computerService.showDetails("1").get().getCompanyId()==2);
			assertTrue(computerService.showDetails("1").get().getName().equals("testUpdate"));
			computer.setName("Computer 1");
			computerService.updateComputer(computer);
		} catch (DaoException | ModelException | InvalidInputException e) {
			fail();
		}
	}
}
