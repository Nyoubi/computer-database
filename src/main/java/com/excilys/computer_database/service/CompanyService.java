package com.excilys.computer_database.service;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.persistence.DaoCompany;


public class CompanyService {
	private DaoCompany daoCompany;
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/"+ DB_NAME;
	private static final String USER = "admincdb";
	private static final String PASS = "qwerty1234";
	
    private static volatile CompanyService instance = null;
    
	public static CompanyService getInstance()
    {   
		if (instance == null) {
			synchronized(CompanyService.class) {
				if (instance == null) {
					instance = new CompanyService();
				}
			}
		}
		return instance;
    }
	
	private CompanyService () {
		this.daoCompany = DaoCompany.getInstance(JDBC_DRIVER, DB_URL, USER, PASS);
	}
	
	public ArrayList<Company> listCompanies(){
		ArrayList<Company> result = new ArrayList<>();
		result = daoCompany.listAllCompany();
		return result;
	}
	
	public Optional<Company> findCompanyById(Integer id){
		return daoCompany.findCompanyById(id);
	}
}
