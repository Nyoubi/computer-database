package com.excilys.computer_database.validationTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.appTest.AppConfigTest;
import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.dto.ComputerDtoBuilder;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.ValidationException;
import com.excilys.computer_database.service.CompanyService;

import validation.DtoComputerValidation;

public class DtoComputerValidationTest {
	
	static private CompanyService companyService;
	static GenericApplicationContext context;
	
	
	@BeforeAll
	public static void init() {
		context = new AnnotationConfigApplicationContext(AppConfigTest.class);
		companyService = context.getBean(CompanyService.class);
	}
	
	@Test
	public void testCheckDataComputer() {
		ComputerDto computer = new ComputerDtoBuilder().setName("test")
												 .setIntroduced("1996-02-01")
												 .setDiscontinued("1996-02-02")
												 .setCompanyId(1).build();
		
		try {
			DtoComputerValidation.checkDataComputer(computer,companyService,false);
			computer.setId(1);
			DtoComputerValidation.checkDataComputer(computer,companyService,true);
		} catch (DaoException | ValidationException e) {
			fail();
		}

		try {
			DtoComputerValidation.checkDataComputer(new ComputerDtoBuilder().build(),companyService,true);
		} catch (DaoException | ValidationException e) {
			assertTrue(true);
		}
		
		try {
			computer.setName(null);
			DtoComputerValidation.checkDataComputer(computer,companyService,true);
		} catch (DaoException | ValidationException e) {
			assertTrue(true);
		}
		
		try {
			computer.setName("a");
			computer.setIntroduced(null);
			DtoComputerValidation.checkDataComputer(computer,companyService,true);
		} catch (DaoException | ValidationException e) {
			assertTrue(true);
		}
		
		try {
			computer.setIntroduced("1995-12-12");
			computer.setDiscontinued("1995-12-11");
			DtoComputerValidation.checkDataComputer(computer,companyService,true);
		} catch (DaoException | ValidationException e) {
			assertTrue(true);
		}
		
		try {
			computer.setDiscontinued("1995-12-13");
			computer.setCompanyId(-1);
			DtoComputerValidation.checkDataComputer(computer,companyService,true);
		} catch (DaoException | ValidationException e) {
			assertTrue(true);
		}
	}
}
