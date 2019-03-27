//package com.excilys.computer_database.controller;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Optional;
//
//import com.excilys.computer_database.dto.DtoCompany;
//import com.excilys.computer_database.dto.DtoComputer;
//import com.excilys.computer_database.dto.DtoComputerBuilder;
//import com.excilys.computer_database.exception.ExceptionDao;
//import com.excilys.computer_database.exception.ExceptionModel;
//import com.excilys.computer_database.model.Page;
//import com.excilys.computer_database.service.CompanyService;
//import com.excilys.computer_database.service.ComputerService;
//import com.excilys.computer_database.util.Util;
//
//public class Controler {
//	private static volatile Controler instance = null;
//	private ComputerService computerService;
//	private CompanyService companyService;
//	
//	private Controler () {
//		this.companyService = CompanyService.getInstance();
//		this.computerService = ComputerService.getInstance();
//	}
//	
//	public static Controler getInstance()
//	{   
//		if (instance == null) {
//			synchronized(Controler.class) {
//				if (instance == null) {
//					instance = new Controler();
//				}
//			}
//		}
//		return instance;
//	}
//	
//	public ArrayList<DtoComputer> getListComputers() throws ExceptionDao, ExceptionModel{
//		ArrayList<DtoComputer> computers = computerService.listComputers();
//		return computers;
//	}
//	
//	public Optional<Page<DtoComputer>> getPageListDtoComputer(String url, Integer index, Integer size) throws ExceptionDao, ExceptionModel{
//		Optional<Page<DtoComputer>> pageListDtoComputer = computerService.pageDtoComputer(url, index, size);
//		return pageListDtoComputer;
//	}
//	
//	public Optional<DtoComputer> getComputerDetails(Integer id) throws ExceptionDao, ExceptionModel{
//		Optional<DtoComputer> computers = computerService.showDetails(id);
//		return computers;
//	}
//	
//	public ArrayList<DtoCompany> getListCompany() throws ExceptionDao, ExceptionModel{
//		ArrayList<DtoCompany> companies = companyService.listCompanies();
//		return companies;
//	}
//	
//	public void createComputer(String name, String introduced, String discontinued, int companyId) throws ExceptionDao, ExceptionModel {
//		checkData(name, introduced, discontinued, companyId);
//		DtoComputerBuilder dtoComputerBuilder = new DtoComputerBuilder();
//		dtoComputerBuilder.setName(name);
//		dtoComputerBuilder.setIntroduced(introduced);
//		dtoComputerBuilder.setDiscontinued(discontinued);
//		dtoComputerBuilder.setCompanyId(companyId);
//		computerService.createComputer(dtoComputerBuilder.build());
//	}
//	
//	public Optional<DtoComputer> updateComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) throws ExceptionDao , ExceptionModel{
//		Optional<DtoComputer> computer = computerService.updateComputer(id,name,introduced,discontinued,companyId);
//		return computer;
//	}
//	
//	public void deleteComputer(Integer id) throws ExceptionDao{
//		computerService.deleteComputer(id);
//	}
//	
//	public Optional<Integer> getNbComputer() throws ExceptionDao{
//		return computerService.getNbComputer();
//	}
//	
//	public void checkData(String name, String introduced, String discontinued, int companyId) throws ExceptionModel {
//	    if (name == null || name == "") {
//	      throw new ExceptionModel("Failed to create computer/update : Invalid name");
//	    }
//	    Optional<Timestamp> OptIntroduced = Util.stringToTimestamp(introduced);
//	    Optional<Timestamp> OptDiscontinued = Util.stringToTimestamp(discontinued);
//
//	    if (!OptIntroduced.isPresent() && OptDiscontinued.isPresent()) {
//		      throw new ExceptionModel("Failed to create computer/update : Discontinued but not introduced");
//		}
//	    if (OptIntroduced.isPresent() && OptDiscontinued.isPresent()
//	    		&& OptIntroduced.get().after(OptDiscontinued.get())) {
//		      throw new ExceptionModel("Failed to create computer/update : Introduced can't be after discontinued date");
//		}
//	  }
//}
