package com.excilys.computer_database.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Computer;

@Repository
public class DaoComputer {

	private final String SELECT_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName FROM computer c "
			+ "LEFT JOIN company cn ON c.company_id=cn.id ";
	private final String SELECT_NAME = SELECT_ALL + "WHERE c.name LIKE ? OR cn.name LIKE ? ";
	private final String SELECT_ID = SELECT_ALL + "WHERE c.id=? ";
	private final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final String DELETE_ID = "DELETE FROM computer WHERE id=?";
	private final String CREATE = "INSERT INTO computer (id, name, introduced, discontinued,company_id) VALUES (?,?,?,?,?)";
	private final String ALTER_AUTO_INCREMENTE = "ALTER TABLE computer AUTO_INCREMENT = ?";
	private static Logger logger = LoggerFactory.getLogger(DaoComputer.class); 

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public DaoComputer(JdbcTemplate jdbcTemplate) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		this.jdbcTemplate = jdbcTemplate;
	}

	public Optional<Computer> findComputerById(Integer id) {
		try {
			Computer computer = jdbcTemplate.queryForObject(SELECT_ID, new Object[]{id} ,new ComputerMapper());
			return Optional.of(computer);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Computer> listAllComputer(String order) {
		List<Computer> computers = jdbcTemplate.query(SELECT_ALL + order, new ComputerMapper());
		return computers;
	}

	public List<Computer> listAllComputer(String search, String order) {
		try {
			List<Computer> computers = jdbcTemplate.query(SELECT_NAME + order, new Object[]{"%"+search+"%","%"+search+"%"} ,new ComputerMapper());
			return computers;
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<Computer>();
		}

	}

	public void createComputer(Computer computer) throws DaoException {

		Integer lineAffected = jdbcTemplate.update(CREATE, new Object[] {
				computer.getId(),
				computer.getName(), 
				computer.getIntroduced(), 
				computer.getDiscontinued(), 
				computer.getCompany().getId()
		});

		if(lineAffected == 0) {
			logger.error("Error when creating the computer " + computer.getName());
			throw new DaoException("Couldn't insert "+ computer.getName() );
		}
	}

	public void updateComputer(Computer computer) throws DaoException {
		Integer lineAffected = jdbcTemplate.update(UPDATE, new Object[] {
				computer.getName(), 
				computer.getIntroduced(), 
				computer.getDiscontinued(), 
				computer.getCompany().getId(), 
				computer.getId()
		});
		if(lineAffected == 0) {
			logger.error("Error when updating the computer.");
			throw new DaoException("Couldn't update "+ computer.getName() );
		}
	}

	public void deleteComputerById(Integer id)  throws DaoException {
		Integer lineAffected = jdbcTemplate.update(DELETE_ID, new Object[] {id});
		if( lineAffected == 0 ) {
			logger.error("Error when deleting the computer.");
			throw new DaoException("Couldn't delete computer "+ id );
		}
	}

	public void resetAutoIncrement(Integer value) {
		jdbcTemplate.update(ALTER_AUTO_INCREMENTE, new Object[] {value});
	}
}
