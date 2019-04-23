package com.excilys.computer_database.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer_database.model.Company;


@Repository
public interface DaoCompany extends JpaRepository<Company,Integer> {
	
	final String CREATE = "INSERT INTO company (name) VALUES (:#{#company.name})";
	final String ALTER_AUTO_INCREMENTE = "ALTER TABLE company AUTO_INCREMENT = ?";
	final String DELETE = "DELETE from Company where id = :id";

	
	public Company getById(Integer id);

	public List<Company> findAll();
	
	@Transactional
	@Modifying
	public void deleteById(@Param("id") Integer id);
	
	@Transactional
	@Modifying
	@Query(value = CREATE, nativeQuery = true)
	public int insert(@Param("company") Company company);
	
	@Modifying
	@Query(ALTER_AUTO_INCREMENTE)
	public int resetAutoIncrement(@Param("id") Integer id);
}
