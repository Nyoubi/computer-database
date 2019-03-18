package com.excilys.computer_database.mapperTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;

@ExtendWith(MockitoExtension.class)
public class ComputerMapperTest {
	
	@Mock
	ResultSet rs;
	
	@BeforeEach
	public void setUp() throws SQLException {
		Mockito.when(rs.getInt("id")).thenReturn(Integer.valueOf(1));
		Mockito.when(rs.getString("name")).thenReturn("Computer name");
		Mockito.doReturn(Timestamp.valueOf("2010-09-11 11:11:11")).when(rs).getTimestamp("introduced");
		Mockito.doReturn(Timestamp.valueOf("2011-10-11 11:11:11")).when(rs).getTimestamp("discontinued");
		Mockito.doReturn(1).when(rs).getInt("cId");
		Mockito.doReturn("Company name").when(rs).getString("cName");
	}
	
	@Test
	public void testComputerMapper() throws SQLException {
		Computer computer = ComputerMapper.resultSetToComputer(rs);
		assertEquals((int)computer.getId(), 1);
		assertEquals(computer.getName(),"Computer name");
		assertEquals(computer.getIntroduced(),Timestamp.valueOf("2010-09-11 11:11:11"));
		assertEquals(computer.getDiscontinued(),Timestamp.valueOf("2011-10-11 11:11:11"));
		assertEquals(computer.getCompany(),new Company(1,"Company name"));
	}
}