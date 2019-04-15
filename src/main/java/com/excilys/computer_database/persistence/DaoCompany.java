package com.excilys.computer_database.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;


@Repository
public class DaoCompany {
	
	private final static String SELECT_ALL = "SELECT id as cId, name as cName FROM company ";
	private final static String SELECT_ID = SELECT_ALL + "WHERE id=? ";
	private final static String DELETE_ID = "DELETE FROM company WHERE id = ? ";
	private final static String DELETE_COMPUTER_ID = "DELETE FROM computer WHERE company_id = ? ";
	private final String CREATE = "INSERT INTO company (name) VALUES (?)";
	private final String ALTER_AUTO_INCREMENTE = "ALTER TABLE company AUTO_INCREMENT = ?";

	private static Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public DaoCompany(JdbcTemplate jdbcTemplate) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		 this.jdbcTemplate = jdbcTemplate;
	}
	
	public Optional<Company> findCompanyById(Integer id) {
		Company company = jdbcTemplate.queryForObject(SELECT_ID, new Object[]{id} ,new CompanyMapper());
		return Optional.ofNullable(company);
	}
	
	public List<Company> listAllCompany(){
		List<Company> companies = jdbcTemplate.query(SELECT_ALL, new CompanyMapper());
		return companies;
	}
	
//	public void deleteCompanyById(Integer id) throws ExceptionDao {
//		Connection conn = null;
//		try {
//			conn = dataSource.getConnection();
//
//			try  {
//				conn.setAutoCommit(false);
//				PreparedStatement stmt = conn.prepareStatement(DELETE_COMPUTER_ID);
//				stmt.setInt(1, id);
//				stmt.executeUpdate();
//				stmt = conn.prepareStatement(DELETE_ID);
//				stmt.setInt(1, id);
//				stmt.executeUpdate();
//				conn.commit();
//			} catch (SQLException e) {
//				if(conn != null) {
//					conn.rollback();
//				}
//				logger.error("Error when deleting company " + id);
//				throw new ExceptionDao("Error when deleting the company");
//			} finally {
//				if (conn != null && !conn.isClosed()) {
//					conn.close();
//				}
//			}
//		}catch (SQLException e1) {
//			throw new ExceptionDao("Error when deleting the company");
//		}
//	}
	
	public Optional<Integer> createCompany(Company company) throws ExceptionDao {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		Integer lineAffected = jdbcTemplate.update(
				connection -> {
					PreparedStatement stmt = connection.prepareStatement(CREATE);
					stmt.setString(1, company.getName());
					return stmt;
				}, keyHolder);

		if( lineAffected == 0 ) {
			logger.error("Error when creating the company " + company.getName());
			throw new ExceptionDao("Couldn't insert "+ company.getName() );
		} else {
			return Optional.ofNullable(keyHolder.getKey().intValue());
		}
	}
	
	public void resetAutoIncrement(Integer value) {
		jdbcTemplate.update(ALTER_AUTO_INCREMENTE, new Object[] {value});
	}
}
