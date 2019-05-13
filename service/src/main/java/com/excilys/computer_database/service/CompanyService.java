package com.excilys.computer_database.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer_database.binding_dto.CompanyDto;
import com.excilys.computer_database.binding_exception.DaoException;
import com.excilys.computer_database.binding_exception.ValidationException;
import com.excilys.computer_database.dao.DaoCompany;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;



@Service
public class CompanyService {
	
	@Autowired
	private DaoCompany daoCompany;
	
	public List<CompanyDto> listCompanies() {
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
	
	@Transactional
	public void deleteCompany(Integer id) {
			daoCompany.deleteById(id);
	}
	
	public Company checkDataCreateCompany(String name) throws ValidationException{
		if ("".equals(name)) {
			throw new ValidationException("Failed to create company : Invalid name");
		}
		CompanyBuilder companyBuilder = new CompanyBuilder().setName(name);
		return companyBuilder.build();
		
	}
}
