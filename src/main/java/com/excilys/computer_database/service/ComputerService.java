package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionInvalidInput;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.model.PageBuilder;
import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.util.Util;


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

	public ArrayList<DtoComputer> listComputers()  throws ExceptionDao, ExceptionModel{
		ArrayList<DtoComputer> result = new ArrayList<>();
		
		for (Computer computer : daoComputer.listAllComputer()) {
			result.add(ComputerMapper.computerToDtoComputer(computer));
		}
		return result;
	}

	public ArrayList<DtoComputer> listComputers(String search)  throws ExceptionDao, ExceptionModel{
		ArrayList<DtoComputer> result = new ArrayList<>();
		
		for (Computer computer : daoComputer.listAllComputer(search)) {
			result.add(ComputerMapper.computerToDtoComputer(computer));
		}
		return result;
	}
	
	public Optional<DtoComputer> showDetails(String id)  throws ExceptionDao, ExceptionModel, ExceptionInvalidInput {
		Optional<Integer> ident = Util.parseInt(id);
		if (ident.isPresent()) {
			Optional<Computer> computer = daoComputer.findComputerById(ident.get());
			if (computer.isPresent()) {
				return Optional.of(ComputerMapper.computerToDtoComputer(computer.get()));
			} else {
				throw new ExceptionDao("The computer " + id + " doesn't exist in the database.");
			}
		} else {
			throw new ExceptionInvalidInput("This id can't be converted to an integer.");
		}
		}

	public void deleteComputer(String id) throws ExceptionDao, ExceptionInvalidInput {
		
		Optional<Integer> ident = Util.parseInt(id);
		if (Util.checkOptional(ident) != null) {
			daoComputer.deleteComputerById(ident.get());
		} else {
			throw new ExceptionInvalidInput("This id : " + id + " is invalid.");
		}
	}

	public void createComputer(String name, String introduced, String discontinued, Integer companyId) throws ExceptionDao, ExceptionModel {
		checkDataCreateComputer(name, introduced, discontinued, companyId);
		CompanyService companyService = CompanyService.getInstance();
		Optional<DtoCompany> dtoCompany = companyService.findCompanyById(companyId);
		Optional<Company> company = Optional.empty();
		if (dtoCompany.isPresent()) {
			company = CompanyMapper.dtoCompanyToCompany(dtoCompany.get());
			if (!company.isPresent()) {
				throw new ExceptionModel("Mapper : Can't convert the dto company to company");
			}
		}
		Optional<Timestamp> timeIntro = Util.stringToTimestamp(introduced);
		Optional<Timestamp> timeDiscon = Util.stringToTimestamp(discontinued);
		ComputerBuilder computerBuilder = new ComputerBuilder().setName(name)
															   .setIntroduced(timeIntro.isPresent() ? timeIntro.get() : null)
															   .setDiscontinued(timeDiscon.isPresent() ? timeDiscon.get() : null)
															   .setCompany(company.isPresent() ? company.get() : null)
															   .setId(null);
		Computer computer = computerBuilder.build();
		daoComputer.createComputer(computer);
	}


	public void updateComputer(Integer id, String name, String introduced, String discontinued, Integer companyId) throws ExceptionDao, ExceptionModel {
		checkDataUpdateComputer(id, name, introduced, discontinued, companyId);
		CompanyService companyService = CompanyService.getInstance();
		Optional<DtoCompany> dtoCompany = companyService.findCompanyById(companyId);
		Optional<Company> company = Optional.empty();
		if (dtoCompany.isPresent()) {
			company = CompanyMapper.dtoCompanyToCompany(dtoCompany.get());
			if (!company.isPresent()) {
				throw new ExceptionModel("Mapper : Can't convert the dto company to company");
			}
		}
		
		Optional<Timestamp> timeIntro = Util.stringToTimestamp(introduced);
		Optional<Timestamp> timeDiscon = Util.stringToTimestamp(discontinued);
		ComputerBuilder computerBuilder = new ComputerBuilder().setName(name)
															   .setIntroduced(timeIntro.isPresent() ? timeIntro.get() : null)
															   .setDiscontinued(timeDiscon.isPresent() ? timeDiscon.get() : null)
															   .setCompany(company.isPresent() ? company.get() : null)
															   .setId(id);
		Computer computer = computerBuilder.build();
		daoComputer.updateComputer(computer);
	}
	
	public Optional<Page<DtoComputer>> pageDtoComputer(String url, Integer index, Integer size, String search, String order) throws ExceptionDao, ExceptionModel{
		ArrayList<DtoComputer> result = new ArrayList<>();
		if (search == null || search.equals("")) {
			result = listComputers();
		} else {
			result = listComputers(search);
		}
		Optional<Page<DtoComputer>> page = new PageBuilder<DtoComputer>()
				.setContent(result)
				.setIndex(index)
				.setSize(size)
				.setUrl(url)
				.setSearch(search)
				.build();
		return page;		
	}
	
	public void checkDataCreateComputer(String name, String introduced, String discontinued, Integer companyId) throws ExceptionModel {
	    if (name == null || name == "") {
	      throw new ExceptionModel("Failed to create computer : Invalid name");
	    }
	    
	    Optional<Timestamp> OptIntroduced = Util.stringToTimestamp(introduced);
	    Optional<Timestamp> OptDiscontinued = Util.stringToTimestamp(discontinued);

	    if (!OptIntroduced.isPresent() && OptDiscontinued.isPresent()) {
		      throw new ExceptionModel("Failed to create computer : Discontinued but not introduced");
		}
	    if (OptIntroduced.isPresent() && OptDiscontinued.isPresent()
	    		&& OptIntroduced.get().after(OptDiscontinued.get())) {
		      throw new ExceptionModel("Failed to create computer : Introduced can't be after discontinued date");
		}
	  }
	
	public void checkDataUpdateComputer(Integer id, String name, String introduced, String discontinued, Integer companyId) throws ExceptionModel {
	    if (name == null || name == "") {
	      throw new ExceptionModel("Failed to create update : Invalid new name");
	    }
	    
	    Optional<Timestamp> OptIntroduced = Util.stringToTimestamp(introduced);
	    Optional<Timestamp> OptDiscontinued = Util.stringToTimestamp(discontinued);

	    if (!OptIntroduced.isPresent() && OptDiscontinued.isPresent()) {
		      throw new ExceptionModel("Failed to update computer : Discontinued but not introduced");
		}
	    if (OptIntroduced.isPresent() && OptDiscontinued.isPresent()
	    		&& OptIntroduced.get().after(OptDiscontinued.get())) {
		      throw new ExceptionModel("Failed to update computer : Introduced can't be after discontinued date");
		}
	    
	    if (id == null) {
	    	throw new ExceptionModel("Failed to update computer : Incorrect id");
	    }
	  }
}
