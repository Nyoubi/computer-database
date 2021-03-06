package com.excilys.computer_database.mapperTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.excilys.computer_database.binding_dto.ComputerDto;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

public class ComputerMapperTest {

	@Test
	public void testComputerToDtoMapper(){

		Computer computer = new Computer(1,"Computer name",Timestamp.valueOf("2010-09-11 11:11:11"),Timestamp.valueOf("2011-10-11 11:11:11"),new Company(1,"Company name"));

		ComputerDto dtoComputer = new ComputerDto();
		dtoComputer = ComputerMapper.computerToDtoComputer(computer);

		assertEquals(1,(int)dtoComputer.getId());
		assertEquals("Computer name", dtoComputer.getName());
		assertEquals("2010-09-11 11:11:11.0", dtoComputer.getIntroduced());
		assertEquals("2011-10-11 11:11:11.0", dtoComputer.getDiscontinued());
		assertEquals(1,dtoComputer.getCompanyId());
		assertEquals("Company name", dtoComputer.getCompanyName());
	}
}