package com.excilys.computer_database.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.model.Computer;

@Repository
public interface DaoComputer extends PagingAndSortingRepository<Computer,Integer>{

	Page<Computer> findAllByNameContains(String search, Pageable pageable);

	Integer countByNameContains(String search);
}
