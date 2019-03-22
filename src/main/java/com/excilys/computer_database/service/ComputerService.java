package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.exception.ExceptionDao;
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

	public Optional<DtoComputer> showDetails(Integer id)  throws ExceptionDao, ExceptionModel {
		return Optional.of(ComputerMapper.computerToDtoComputer(Util.checkOptional(daoComputer.findComputerById(id))));
	}

	public void deleteComputer(Integer id) throws ExceptionDao {
		daoComputer.deleteComputerById(id);
	}

	public Optional<DtoComputer> createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId)  throws ExceptionDao, ExceptionModel{
		CompanyService companyService = CompanyService.getInstance();
		Optional<Company> company = Optional.of(CompanyMapper.dtoCompanyToCompany(Util.checkOptional(companyService.findCompanyById(companyId))));
		Optional<Integer> createdId = daoComputer.createComputer(new ComputerBuilder().setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(Util.checkOptional(company))
				.build());
		Optional<DtoComputer> computer = showDetails(createdId.get());
		return computer;
	}


	public Optional<DtoComputer> updateComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) throws ExceptionDao, ExceptionModel {
		CompanyService companyService = CompanyService.getInstance();
		Optional<Company> company = Optional.of(CompanyMapper.dtoCompanyToCompany(Util.checkOptional(companyService.findCompanyById(companyId))));
		daoComputer.updateComputer(new ComputerBuilder().setId(companyId)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(Util.checkOptional(company))
				.build());
		Optional<DtoComputer> computer = showDetails(id);
		return computer;
	}

	public Optional<Page<DtoComputer>> pageDtoComputer(Integer index, Integer size) throws ExceptionDao, ExceptionModel{
		ArrayList<DtoComputer> result = listComputers();
		Optional<Page<DtoComputer>> page = new PageBuilder<DtoComputer>()
				.setContent(result)
				.setIndex(index)
				.setSize(size)
				.build();
		return page;		
	}
	
	public Optional<Integer> getNbComputer() throws ExceptionDao{
		return daoComputer.getNbComputer();
	}
}
