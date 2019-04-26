package com.excilys.computer_database.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.Computer;

@Repository
public interface DaoComputer extends PagingAndSortingRepository<Computer,Integer>{

	Page<Computer> findAllByNameContains(String search, Pageable pageable);

	@Modifying
	@Query(value = "ALTER TABLE computer AUTO_INCREMENT = :id", nativeQuery = true)
	public int resetAutoIncrement(@Param("id") Integer id);
}
