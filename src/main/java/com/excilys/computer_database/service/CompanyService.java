package com.excilys.computer_database.service;

import java.util.ArrayList;
import java.util.Optional;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;
import com.excilys.computer_database.persistence.DaoCompany;


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
		Company company;
		try {
			company = daoCompany.getById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new DaoException("daoException.findCompany");
		}
		Optional<CompanyDto> dtoCompany = Optional.empty();
		dtoCompany = CompanyMapper.companyToDtoCompany(company);
		return dtoCompany;
	}
	
	public void deleteCompany(Integer id) throws DaoException {
		if(daoCompany.delete(id) == 0) {
			throw new DaoException("daoException.deleteCompany");
		}
	}
	
	public void resetAutoxœIncrement(Integer id) throws DaoException {
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
