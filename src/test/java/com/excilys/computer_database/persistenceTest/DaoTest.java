package com.excilys.computer_database.persistenceTest;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.excilys.computer_database.persistence.Dao;

import junit.framework.TestCase;

public class DaoTest extends TestCase {
	
	@Test
	public void testDao() {
		try (Connection conn = Dao.openConnection()){
			assertNotNull(conn);
		} catch (SQLException e) {
			fail("Exception catched when trying to connect.");
		} 
	}
}