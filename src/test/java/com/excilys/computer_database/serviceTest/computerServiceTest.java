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
import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.dto.DtoComputerBuilder;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionInvalidInput;
import com.excilys.computer_database.exception.ExceptionModel;
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
		List<DtoComputer> computers = new ArrayList<>();
		try {
			computers = computerService.listAllComputer("");
		} catch (ExceptionDao | ExceptionModel e) {
			fail();
		}
		assertTrue((int)computers.size() == 3);
	}

	@Test
	public void testListComputersSearch() {
		List<DtoComputer> computers = new ArrayList<>();
		try {
			computers = computerService.listAllComputer("test","");
		} catch (ExceptionDao | ExceptionModel e) {
			fail();
		}
		assertTrue((int)computers.size() == 1);
		assertTrue(computers.get(0).getId() == 2);
	}

	@Test
	public void testFindById() {
		try {
			DtoComputer computer2 = new DtoComputerBuilder().setId(2).setName("test").setCompanyId(2).setCompanyName("Company 2").build();
			DtoComputer computer = computerService.showDetails("2").get();
			assertEquals(computer.toString(),computer2.toString());

		} catch (ExceptionModel|ExceptionDao | ExceptionInvalidInput e) {
			fail();
		}
	}
	
	@Test
	public void testDeleteComputer() {
		try {
			computerService.createComputer("delete", "", "", 1);
			computerService.deleteComputer("4");

			daoComputer.resetAutoIncrement(Integer.valueOf(3));

			computerService.showDetails("4").isPresent();
			fail();
		} catch (ExceptionModel|ExceptionDao | ExceptionInvalidInput e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testCheckCreateComputer() {
		try {
			computerService.checkDataCreateComputer("test","1996-02-01",null,1);
			assertTrue(true);
		} catch (ExceptionModel|ExceptionDao e) {
			fail();
		}

		try {
			computerService.checkDataCreateComputer(null,null,null,0);
			fail();
		} catch (ExceptionModel|ExceptionDao e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataCreateComputer("test",null,"1995-12-12",0);
			fail();
		} catch (ExceptionModel|ExceptionDao e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataCreateComputer("test","1995-12-12","1995-10-10",-1);
			fail();
		} catch (ExceptionModel|ExceptionDao e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataCreateComputer("test","1995-10-10","1995-12-12",-1);
			fail();
		} catch (ExceptionModel|ExceptionDao e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testPageComputer() {
		try {
			Page<DtoComputer> test = computerService.pageDtoComputer("testurl",1,10,"test","");
			assertTrue(test.getContent().size() == 1);
			
			test = computerService.pageDtoComputer("testurl",1,10,"","nameDesc");
			assertTrue(test.getContent().size() == 3);
			assertTrue(test.getContent().get(0).getName().equals("test"));
			
			test = computerService.pageDtoComputer("testurl",1,10,"Computer","companyAsc");
			assertTrue(test.getContent().size() == 2);
			assertTrue(test.getContent().get(0).getName().equals("Computer 1"));

			test = computerService.pageDtoComputer("testurl",1,10,null,"");
			assertTrue(test.getContent().size() == 3);
			
		} catch (ExceptionDao | ExceptionModel e) {
			fail();
		}
			
	}
	
	@Test
	public void testCheckUpdateComputer() {
		try {
			computerService.checkDataUpdateComputer(1,"test","1996-02-01",null,1);
		} catch (ExceptionModel|ExceptionDao e) {
			fail();
		}
		
		try {
			computerService.checkDataUpdateComputer(1,null,null,null,0);
			fail();
		} catch (ExceptionModel|ExceptionDao e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataUpdateComputer(1,"test",null,"1995-12-12",0);
			fail();
		} catch (ExceptionModel|ExceptionDao e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataUpdateComputer(1,"test","1995-12-10","1995-10-12",0);
			fail();
		} catch (ExceptionModel|ExceptionDao e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataUpdateComputer(null,"test","1995-12-12","1995-12-13",0);
			fail();
		} catch (ExceptionModel|ExceptionDao e) {
			assertTrue(true);
		}
		
		try {
			computerService.checkDataUpdateComputer(1,"test","1996-02-01",null,5);
			fail();
		} catch (ExceptionModel|ExceptionDao e) {
			assertTrue(true);
		}
	}
	
}
