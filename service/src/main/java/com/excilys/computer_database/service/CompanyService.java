package com.excilys.computer_database.service;

import java.util.ArrayList;
import java.util.Optional;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.Company;
import com.excilys.computer_database.CompanyBuilder;
import com.excilys.computer_database.dao.DaoCompany;
import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.InvalidInputException;
import com.excilys.computer_database.mapper.CompanyMapper;


@Service
public class CompanyService {
	
	@Autowired
	private DaoCompany daoCompany;
	
	public ArrayList<CompanyDto> listCompanies() {
		ArrayList<CompanyDto> result = new ArrayList<>();
		for (Company company : daoCompany.findAll()) {
			Optional<CompanyDto> dtoCompany = CompanyMapper.companyToDtoCompany(company);
			if (dtoCompany.isPresent()) {
				result.add(dtoCompany.get());
			}
		}
		return result;
	}
	
	public Optional<CompanyDto> findCompanyById(Integer id) throws DaoException {
		Optional<Company> company;
		try {
			company = daoCompany.findById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new DaoException("daoException.findCompany");
		}
		return company.isPresent() ? CompanyMapper.companyToDtoCompany(company.get()) : Optional.empty();
	}
	
	public void deleteCompany(Integer id) throws InvalidInputException {
			daoCompany.deleteById(id);
	}
	
	public void resetAutoIncrement(Integer id) throws DaoException {
		daoCompany.resetAutoIncrement(id);
	}
	
	public Company checkDataCreateCompany(String name) throws DaoException , ValidationException{
		if (name == null || name == "") {
			throw new ValidationException("Failed to create company : Invalid name");
		}
		CompanyBuilder companyBuilder = new CompanyBuilder().setName(name);
		return companyBuilder.build();
		
	}
}
