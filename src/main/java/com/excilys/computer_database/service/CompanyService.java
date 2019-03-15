package com.excilys.computer_database.service;

import java.util.ArrayList;

import com.excilys.computer_database.controller.Controller;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.persistence.DaoCompany;


public class CompanyService {
	private DaoCompany daoCompany;
	
    private static volatile CompanyService instance = null;
    
	public static CompanyService getInstance()
    {   
		if (instance == null) {
			synchronized(Controller.class) {
				if (instance == null) {
					instance = new CompanyService();
				}
			}
		}
		return instance;
    }
	
	private CompanyService () {
		this.daoCompany = DaoCompany.getInstance();
	}
	
	public ArrayList<Company> listCompanies(){
		ArrayList<Company> result = new ArrayList<>();
		result = daoCompany.listAllCompany();
		return result;
	}
	
}
