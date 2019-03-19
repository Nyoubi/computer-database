package com.excilys.computer_database.persistenceTest;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.computer_database.persistence.DaoCompany;

public class DaoCompanyTest {
	 
	  @InjectMocks private DaoCompany DaoCompany;
	 
	  @BeforeClass
	  public void setUp() {
	    MockitoAnnotations.initMocks(this);
	  }
	 
//	  @Test
//	  public void listAllCompanyTest() {
//	    Mockito.when(DaoCompany.listAllCompany()).thenReturn(mockStatement);
//	    Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
//	    int value = dbConnection.executeQuery("");
//	    Assert.assertEquals(value, 1);
//	    Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
//	  }
	}