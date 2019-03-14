package com.excilys.computer_database.service;

import java.util.ArrayList;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.persistence.DaoCompany;


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
