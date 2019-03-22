//package com.excilys.computer_database.persistenceTest;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.fail;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import com.excilys.computer_database.model.Company;
//import com.excilys.computer_database.model.CompanyBuilder;
//import com.excilys.computer_database.persistence.Dao;
//import com.excilys.computer_database.persistence.DaoCompany;
//
//public class DaoCompanyTest {
//	private DaoCompany daoCompany;
//	private Company company;
//	CompanyBuilder companyBuilder;
//	@BeforeEach
//	public void setUp() {
//		daoCompany = DaoCompany.getInstance("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/computer-database-test",
//											  "admintest","test1234");
//		companyBuilder = new CompanyBuilder().setName("Test");
//				company = companyBuilder.build();
//	}
//	
//	@Test
//	public void testConnection() {
//		try (Connection conn = Dao.openConnection()){
//			assertNotNull(conn);
//		} catch (SQLException e) {
//			fail("Exception catched when trying to connect.");
//		}
//	}
//
//}