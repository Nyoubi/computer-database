package com.excilys.computer_database.persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer_database.model.Computer;


@Repository
public interface DaoComputer extends JpaRepository<Computer,Integer>{

	final String SELECT_ALL = "SELECT c.id, c.name, c.introduced, c.discontinued, cn.id as cId, cn.name as cName FROM computer c "
			+ "LEFT JOIN company cn ON c.company_id=cn.id ";
	final String ORDER_BY = "ORDER BY :order ";
	final String SELECT_NAME = SELECT_ALL + "WHERE c.name LIKE :search OR cn.name LIKE :search ";
	final String UPDATE = "UPDATE computer SET name = :#{#computer.name}, introduced = :#{#computer.introduced}, discontinued = :#{#computer.discontinued}, company_id = :#{#computer.company.id} WHERE id = :#{#computer.id}";
	final String CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (:#{#computer.name}, :#{#computer.introduced},  :#{#computer.discontinued}, :#{#computer.company.id})";
	final String ALTER_AUTO_INCREMENTE = "ALTER TABLE computer AUTO_INCREMENT = ?";
	static Logger logger = LoggerFactory.getLogger(DaoComputer.class); 

	public Computer getById(Integer id);
	
	@Query(value = SELECT_ALL + ORDER_BY , nativeQuery = true)
	public List<Computer> findAll(@Param("order") String order);

	@Query(value = SELECT_NAME + ORDER_BY, nativeQuery = true)
	public List<Computer> findAll(@Param("search") String search, @Param("order") String order);
	
	@Transactional
	@Modifying
	@Query(value = CREATE, nativeQuery = true)
	public int insert(@Param("computer") Computer computer);


	@Transactional
	@Modifying
	@Query(value = UPDATE, nativeQuery = true)
	public int update(@Param("computer") Computer computer);
	
	@Transactional
	@Modifying
	public int deleteById(@Param("id") Long id);

	@Modifying
	@Query(ALTER_AUTO_INCREMENTE)
	public int resetAutoIncrement(@Param("id") Integer id);
}
