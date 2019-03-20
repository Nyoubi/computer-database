package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.persistence.DaoComputer;


public class ComputerService {
	private DaoComputer daoComputer;
	
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
		this.daoComputer = DaoComputer.getInstance();
	}
	
	public ArrayList<Computer> listComputers(){
		ArrayList<Computer> result = new ArrayList<>();
		result = daoComputer.listAllComputer();
		return result;
	}
	
	public Optional<Computer> showDetails(Integer id) {
		return daoComputer.findComputerById(id);
	}
	
	public Boolean deleteComputer(Integer id) {
		return daoComputer.deleteComputerById(id);
	}
	
	public Integer createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		CompanyService companyService = CompanyService.getInstance();
		Company company = null;
		if (companyId != null && companyId != -1)
			company = companyService.findCompanyById(companyId).get();
		return daoComputer.createComputer(computerBuilder.setName(name).setIntroduced(introduced).setDiscontinued(discontinued).setCompany(company).build());
	}
	
	
	public Boolean updateComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		CompanyService companyService = CompanyService.getInstance();
		Company company = null;
		if (companyId != null && companyId != -1)
			company = companyService.findCompanyById(companyId).get();
		return daoComputer.updateComputer(computerBuilder.setId(id)
														 .setName(name)
														 .setIntroduced(introduced)
														 .setDiscontinued(discontinued)
														 .setCompany(company)
														 .build());
	}
	
}
