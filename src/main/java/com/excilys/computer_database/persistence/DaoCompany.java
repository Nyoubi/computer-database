package com.excilys.computer_database.persistence;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;


@Repository
public class DaoCompany {
	
	private final static String SELECT_ALL = "SELECT id as cId, name as cName FROM company ";
	private final static String SELECT_ID = SELECT_ALL + "WHERE id= ? ";
	private final static String DELETE_ID = "DELETE FROM company WHERE id = ? ";
	private final static String DELETE_COMPUTER_ID = "DELETE FROM computer WHERE company_id = ? ";
	private final String CREATE = "INSERT INTO company (id,name) VALUES (?,?)";
	private final String ALTER_AUTO_INCREMENTE = "ALTER TABLE company AUTO_INCREMENT = ?";

	private static Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public DaoCompany(JdbcTemplate jdbcTemplate) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		 this.jdbcTemplate = jdbcTemplate;
	}
	
	public Optional<Company> findCompanyById(Integer id) {
		try {
			Company company = jdbcTemplate.queryForObject(SELECT_ID, new Object[]{id} ,new CompanyMapper());
			return Optional.of(company);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
	
	public List<Company> listAllCompany(){
		List<Company> companies = jdbcTemplate.query(SELECT_ALL, new CompanyMapper());
		return companies;
	}
	
	@Transactional(rollbackFor = {DataAccessException.class})
	public void deleteCompanyById(Integer id) throws ExceptionDao {
		try {
			jdbcTemplate.update(DELETE_COMPUTER_ID, new Object[]{id} );
			jdbcTemplate.update(DELETE_ID, new Object[]{id});
		} catch (DataAccessException e) {
			logger.error("Error when deleting company " + id);
			throw new ExceptionDao("Error when deleting the company");
		}
		
	}
	
	public void createCompany(Company company) throws ExceptionDao {

		Integer lineAffected = jdbcTemplate.update(CREATE, new Object[] {
				company.getId(),
				company.getName()
		});

		if(lineAffected == 0) {
			logger.error("Error when creating the company " + company.getName());
			throw new ExceptionDao("Couldn't insert "+ company.getName() );
		}
	}
	
	public void resetAutoIncrement(Integer value) {
		jdbcTemplate.update(ALTER_AUTO_INCREMENTE, new Object[] {value});
	}
}
