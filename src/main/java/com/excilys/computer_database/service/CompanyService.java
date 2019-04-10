package com.excilys.computer_database.service;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.persistence.DaoCompany;

public class CompanyService {	
	private DaoCompany daoCompany;

	public CompanyService (DaoCompany daoCompany) {
		this.daoCompany = daoCompany;
	}
	
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
}
