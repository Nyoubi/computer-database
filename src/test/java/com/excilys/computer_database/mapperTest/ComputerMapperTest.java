package com.excilys.computer_database.mapperTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

@ExtendWith(MockitoExtension.class)
public class ComputerMapperTest {

	@Test
	public void testComputerToDtoMapper(){

		Computer computer = new Computer(1,"Computer name",Timestamp.valueOf("2010-09-11 11:11:11"),Timestamp.valueOf("2011-10-11 11:11:11"),new Company(1,"Company name"));

		ComputerDto dtoComputer = new ComputerDto();
		dtoComputer = ComputerMapper.computerToDtoComputer(computer);

		assertEquals((int)dtoComputer.getId(), 1);
		assertEquals(dtoComputer.getName(),"Computer name");
		assertEquals(dtoComputer.getIntroduced(),"2010-09-11 11:11:11.0");
		assertEquals(dtoComputer.getDiscontinued(),"2011-10-11 11:11:11.0");
		assertEquals(dtoComputer.getCompanyId(),1);
		assertEquals(dtoComputer.getCompanyName(), "Company name");
	}
}