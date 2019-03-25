package com.excilys.computer_database.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.dto.DtoComputerBuilder;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.model.Page;
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
	
	public ArrayList<DtoComputer> getListComputers() throws ExceptionDao, ExceptionModel{
		ArrayList<DtoComputer> computers = computerService.listComputers();
		return computers;
	}
	
	public Optional<Page<DtoComputer>> getPageListDtoComputer(Integer index, Integer size) throws ExceptionDao, ExceptionModel{
		Optional<Page<DtoComputer>> pageListDtoComputer = computerService.pageDtoComputer(index, size);
		return pageListDtoComputer;
	}
	
	public Optional<DtoComputer> getComputerDetails(Integer id) throws ExceptionDao, ExceptionModel{
		Optional<DtoComputer> computers = computerService.showDetails(id);
		return computers;
	}
	
	public ArrayList<DtoCompany> getListCompany() throws ExceptionDao, ExceptionModel{
		ArrayList<DtoCompany> companies = companyService.listCompanies();
		return companies;
	}
	
	public void createComputer(String name, String introduced, String discontinued, int companyId) throws ExceptionDao, ExceptionModel {
		DtoComputerBuilder dtoComputerBuilder = new DtoComputerBuilder();
		dtoComputerBuilder.setName(name);
		dtoComputerBuilder.setIntroduced(introduced);
		dtoComputerBuilder.setDiscontinued(discontinued);
		dtoComputerBuilder.setCompanyId(companyId);
		computerService.createComputer(dtoComputerBuilder.build());
	}
	
	public Optional<DtoComputer> updateComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) throws ExceptionDao , ExceptionModel{
		Optional<DtoComputer> computer = computerService.updateComputer(id,name,introduced,discontinued,companyId);
		return computer;
	}
	
	public void deleteComputer(Integer id) throws ExceptionDao{
		computerService.deleteComputer(id);
	}
	
	public Optional<Integer> getNbComputer() throws ExceptionDao{
		return computerService.getNbComputer();
	}
}
