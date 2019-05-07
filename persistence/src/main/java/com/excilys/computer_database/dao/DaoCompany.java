package com.excilys.computer_database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.computer_database.model.Company;

@Repository
public interface DaoCompany extends JpaRepository<Company,Integer> {
	
}
