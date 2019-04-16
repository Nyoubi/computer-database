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
import com.excilys.computer_database.dto.ComputerDtobuilder;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.InvalidInputException;
import com.excilys.computer_database.exception.ModelException;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.service.ComputerService;

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
			ComputerDto computer2 = new ComputerDtobuilder().setId(2).setName("test").setCompanyId(2).setCompanyName("Company 2").build();
			ComputerDto computer = computerService.showDetails("2").get();
			assertEquals(computer.toString(),computer2.toString());

		} catch (DaoException | InvalidInputException e) {
			fail();
		}
	}
	
	@Test
	public void testDeleteComputer() {
		try {
			computerService.createComputer(4,"delete", "", "", 1);
			computerService.deleteComputer("4");

			daoComputer.resetAutoIncrement(Integer.valueOf(3));

			computerService.showDetails("4").isPresent();
		} catch (ModelException|DaoException | InvalidInputException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCheckDataComputer() {
		try {
			computerService.checkDataComputer(1,"test","1996-02-01",null,1);
			assertTrue(true);
		} catch (ModelException|DaoException e) {
			fail();
		}

		try {
			computerService.checkDataComputer(null, null,null,null,0);
		} catch (ModelException|DaoException e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataComputer(1, null,null,null,0);
		} catch (ModelException|DaoException e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataComputer(1,"test",null,"1995-12-12",0);
		} catch (ModelException|DaoException e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataComputer(1,"test","1995-12-12","1995-10-10",-1);
		} catch (ModelException|DaoException e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataComputer(1,"test","1995-10-10","1995-12-12",-1);
		} catch (ModelException|DaoException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCheckId() {
		try {
			assertTrue(computerService.checkId(1)==1);
		} catch (ModelException e) {
			fail();
		}
		
		try {
			computerService.checkId(null);
			fail();
		} catch (ModelException e) {
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
			assertTrue(test.getContent().get(0).getName().equals("Computer 1"));

			test = computerService.pageDtoComputer("testurl","1","10",null,"");
			assertTrue(test.getContent().size() == 3);
			
		} catch (DaoException | ModelException e) {
			fail();
		}
			
	}
	
	@Test
	public void testGetOrder() {
		assertTrue(computerService.getOrder(null) == "");
		assertTrue(computerService.getOrder("nameAsc") == "ORDER BY c.name");
	}
	
	@Test
	public void testUpdateComputer() {
		try {
			computerService.updateComputer(1, "testUpdate", "2000-01-01", "2000-01-06", 2);
			assertTrue(computerService.showDetails("1").get().getCompanyId()==2);
			assertTrue(computerService.showDetails("1").get().getName().equals("testUpdate"));
			computerService.updateComputer(1, "Computer 1", "2000-01-01", "2000-01-06", 1);
		} catch (DaoException | ModelException | InvalidInputException e) {
			fail();
		}
	}
}
