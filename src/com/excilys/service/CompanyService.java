package com.excilys.service;

import java.util.ArrayList;

import com.excilys.model.Company;
import com.excilys.persistence.DaoCompany;


public class CompanyService {
	private DaoCompany daoCompany;
	public CompanyService () {
		this.daoCompany = new DaoCompany();
	}
	
	public ArrayList<Company> listCompanies(){
		ArrayList<Company> result = new ArrayList<>();
		result = daoCompany.listAllCompany();
		return result;
	}
	
}
