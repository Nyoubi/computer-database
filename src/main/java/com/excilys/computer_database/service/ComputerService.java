package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.controller.Controller;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.persistence.DaoComputer;


public class ComputerService {
	private DaoComputer daoComputer;
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/"+ DB_NAME;
	private static final String USER = "admincdb";
	private static final String PASS = "qwerty1234";

	private static volatile ComputerService instance = null;

	public static ComputerService getInstance()
	{   
		if (instance == null) {
			synchronized(ComputerService.class) {
				if (instance == null) {
					instance = new ComputerService();
				}
			}
		}
		return instance;
	}

	private ComputerService () {
		this.daoComputer = DaoComputer.getInstance(JDBC_DRIVER, DB_URL, USER, PASS);
	}

	public ArrayList<Computer> listComputers(){
		ArrayList<Computer> result = new ArrayList<>();
		result = daoComputer.listAllComputer();
		return result;
	}

	public Optional<Computer> showDetails(Integer id) {
		return daoComputer.findComputerById(id);
	}

	public void deleteComputer(Integer id) throws ExceptionDao {
		daoComputer.deleteComputerById(id);
	}

	public Optional<Integer> createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		CompanyService companyService = CompanyService.getInstance();
		Company company = null;
		if (companyId != null && companyId != -1)
			company = companyService.findCompanyById(companyId).get();
		return daoComputer.createComputer(computerBuilder.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(company)
				.build());
	}


	public void updateComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) throws ExceptionDao {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		CompanyService companyService = CompanyService.getInstance();
		Company company = null;
		if (companyId != null && companyId != -1) {
			company = companyService.findCompanyById(companyId).get();
		}
		daoComputer.updateComputer(computerBuilder.setId(id)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(company)
				.build());
	}
}
