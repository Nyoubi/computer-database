package com.excilys.computer_database.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.ModelException;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;
import com.excilys.computer_database.persistence.DaoCompany;


@Service
public class CompanyService {
	
	@Autowired
	private DaoCompany daoCompany;
	
	public ArrayList<CompanyDto> listCompanies()  throws DaoException {
		ArrayList<CompanyDto> result = new ArrayList<>();
		for (Company company : daoCompany.listAllCompany()) {
			Optional<CompanyDto> dtoCompany = CompanyMapper.companyToDtoCompany(company);
			if (dtoCompany.isPresent()) {
				result.add(dtoCompany.get());
			}
		}
		return result;
	}
	
	public Optional<CompanyDto> findCompanyById(Integer id) throws ModelException, DaoException{
		Optional<Company> company = daoCompany.findCompanyById(id);
		Optional<CompanyDto> dtoCompany = Optional.empty();
		if (company.isPresent()) {
			dtoCompany = CompanyMapper.companyToDtoCompany(company.get());
		}
		return dtoCompany;
	}
	
	public void deleteCompany(Integer id) throws DaoException {
		daoCompany.deleteCompanyById(id);
	}
	
	public void createCompany(String name) throws DaoException, ModelException {
		Company company = checkDataCreateCompany(name);
		daoCompany.createCompany(company);
	}
	
	public void resetAutoIncrement(Integer id) throws DaoException, ModelException {
		daoCompany.resetAutoIncrement(id);
	}
	
	public Company checkDataCreateCompany(String name) throws ModelException, DaoException {
		if (name == null || name == "") {
			throw new ModelException("Failed to create company : Invalid name");
		}
		CompanyBuilder companyBuilder = new CompanyBuilder().setName(name);
		return companyBuilder.build();
		
	}
}
