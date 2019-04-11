package com.excilys.computer_database.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.CompanyBuilder;
import com.excilys.computer_database.persistence.DaoCompany;


@Service
public class CompanyService {
	
	@Autowired
	private DaoCompany daoCompany;
	
	public ArrayList<DtoCompany> listCompanies()  throws ExceptionDao {
		ArrayList<DtoCompany> result = new ArrayList<>();
		for (Company company : daoCompany.listAllCompany()) {
			Optional<DtoCompany> dtoCompany = CompanyMapper.companyToDtoCompany(company);
			if (dtoCompany.isPresent()) {
				result.add(dtoCompany.get());
			}
		}
		return result;
	}
	
	public Optional<DtoCompany> findCompanyById(Integer id) throws ExceptionModel, ExceptionDao{
		Optional<Company> company = daoCompany.findCompanyById(id);
		Optional<DtoCompany> dtoCompany = Optional.empty();
		if (company.isPresent()) {
			dtoCompany = CompanyMapper.companyToDtoCompany(company.get());
		}
		return dtoCompany;
	}
	
	public void deleteCompany(Integer id) throws ExceptionDao {
		daoCompany.deleteCompanyById(id);
	}
	
	public void createCompany(String name) throws ExceptionDao, ExceptionModel {
		Company company =checkDataCreateCompany(name);
		daoCompany.createCompany(company);
	}
	
	public void resetAutoIncrement(Integer id) throws ExceptionDao, ExceptionModel {
		daoCompany.resetAutoIncrement(id);
	}
	
	public Company checkDataCreateCompany(String name) throws ExceptionModel, ExceptionDao {
		if (name == null || name == "") {
			throw new ExceptionModel("Failed to create company : Invalid name");
		}
		CompanyBuilder companyBuilder = new CompanyBuilder().setName(name);
		return companyBuilder.build();
		
	}
}
