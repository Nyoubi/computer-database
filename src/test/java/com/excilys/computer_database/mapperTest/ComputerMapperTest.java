package com.excilys.computer_database.mapperTest;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

@ExtendWith(MockitoExtension.class)
public class ComputerMapperTest {

	@Mock
	ResultSet rs;


	@Test
	public void testComputerToDtoMapper() throws ExceptionModel{

		Computer computer = null;
		try {
			Mockito.when(rs.getInt("id")).thenReturn(Integer.valueOf(1));
			Mockito.when(rs.getString("name")).thenReturn("Computer name");
			Mockito.doReturn(Timestamp.valueOf("2010-09-11 11:11:11")).when(rs).getTimestamp("introduced");
			Mockito.doReturn(Timestamp.valueOf("2011-10-11 11:11:11")).when(rs).getTimestamp("discontinued");
			Mockito.doReturn(1).when(rs).getInt("cId");
			Mockito.doReturn("Company name").when(rs).getString("cName");
			computer = ComputerMapper.resultSetToComputer(rs);
		} catch (SQLException |ExceptionModel e) {
			fail();
		}


		assertEquals((int)computer.getId(), 1);
		assertEquals(computer.getName(),"Computer name");
		assertEquals(computer.getIntroduced(),Timestamp.valueOf("2010-09-11 11:11:11"));
		assertEquals(computer.getDiscontinued(),Timestamp.valueOf("2011-10-11 11:11:11"));
		assertEquals(computer.getCompany(),new Company(1,"Company name"));

		DtoComputer dtoComputer = new DtoComputer();
		dtoComputer = ComputerMapper.computerToDtoComputer(computer);

		assertEquals((int)dtoComputer.getId(), 1);
		assertEquals(dtoComputer.getName(),"Computer name");
		assertEquals(dtoComputer.getIntroduced(),"2010-09-11 11:11:11.0");
		assertEquals(dtoComputer.getDiscontinued(),"2011-10-11 11:11:11.0");
		assertEquals(dtoComputer.getCompanyId(),1);
		assertEquals(dtoComputer.getCompanyName(), "Company name");
	}
}