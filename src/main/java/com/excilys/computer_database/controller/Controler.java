package com.excilys.computer_database.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

public class Controler {
	private static volatile Controler instance = null;
	private ComputerService computerService;
	private CompanyService companyService;
	
	private Controler () {
		this.companyService = CompanyService.getInstance();
		this.computerService = ComputerService.getInstance();
	}
	
	public static Controler getInstance()
	{   
		if (instance == null) {
			synchronized(Controler.class) {
				if (instance == null) {
					instance = new Controler();
				}
			}
		}
		return instance;
	}
	
	public ArrayList<DtoComputer> getListComputers() throws ExceptionDao{
		ArrayList<DtoComputer> computers = computerService.listComputers();
		return computers;
	}
	
	public Optional<DtoComputer> getComputerDetails(Integer id) throws ExceptionDao{
		Optional<DtoComputer> computers = computerService.showDetails(id);
		return computers;
	}
	
	public ArrayList<DtoCompany> getListCompany() throws ExceptionDao{
		ArrayList<DtoCompany> companies = companyService.listCompanies();
		return companies;
	}
	
	public Optional<DtoComputer> createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) throws ExceptionDao {
		Optional<DtoComputer> computer =  computerService.createComputer(name,introduced,discontinued,companyId);
		return computer;
	}
	
	public Optional<DtoComputer> updateComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) throws ExceptionDao {
		Optional<DtoComputer> computer = computerService.updateComputer(id,name,introduced,discontinued,companyId);
		return computer;
	}
	
	public void deleteComputer(Integer id) throws ExceptionDao{
		computerService.deleteComputer(id);
	}
}
