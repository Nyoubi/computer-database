package com.excilys.computer_database.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.model.Company;


@Repository
public interface DaoCompany extends JpaRepository<Company,Integer> {
	
	final String ALTER_AUTO_INCREMENTE = "ALTER TABLE company AUTO_INCREMENT = :id";
	
	@Modifying
	@Query(value = ALTER_AUTO_INCREMENTE, nativeQuery = true)
	public int resetAutoIncrement(@Param("id") Integer id);
}
