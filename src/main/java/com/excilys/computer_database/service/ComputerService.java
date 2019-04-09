package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.dto.DtoComputerBuilder;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionInvalidInput;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.mapper.CompanyMapper;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.ComputerBuilder;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.model.Page.orderEnum;
import com.excilys.computer_database.model.PageBuilder;
import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.util.Util;


public class ComputerService {
	private DaoComputer daoComputer;
	public String dataSource;
	private static volatile ComputerService instance = null;

	public static ComputerService getInstance(String dataSource)
	{   
		if (instance == null) {
			synchronized(ComputerService.class) {
				if (instance == null) {
					instance = new ComputerService(dataSource);
				}
			}
		}
		return instance;
	}

	private ComputerService (String dataSource) {
		this.daoComputer = DaoComputer.getInstance(dataSource);
		this.dataSource = dataSource;
	}

	public List<DtoComputer> listAllComputer(String order)  throws ExceptionDao, ExceptionModel{
		return daoComputer.listAllComputer(order).stream().map(computer -> ComputerMapper.computerToDtoComputer(computer)).collect(Collectors.toList());
	}

	public List<DtoComputer> listAllComputer(String search, String order)  throws ExceptionDao, ExceptionModel{
		return daoComputer.listAllComputer(search,order).stream().map(computer -> ComputerMapper.computerToDtoComputer(computer)).collect(Collectors.toList());

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
		CompanyService companyService = CompanyService.getInstance(this.dataSource);
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

		DtoComputerBuilder dtoComputerBuilder = new DtoComputerBuilder().setId(id)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyId(companyId);
		Computer computer = ComputerMapper.dtoComputerTocomputer(dtoComputerBuilder.build(),dataSource);
		daoComputer.updateComputer(computer);
	}

	public Page<DtoComputer> pageDtoComputer(String url, Integer index, Integer size, String search, String order) throws ExceptionDao, ExceptionModel{
		List<DtoComputer> result = new ArrayList<>();
		String orderBy = getOrder(order);
		if (search == null || search.equals("")) {
			if ("".equals(orderBy)) {
				result = listAllComputer("");
			} else {
				result = listAllComputer(orderBy);
			}
		} else {
			if ("".equals(orderBy)) {
				result = listAllComputer(search,"");

			} else {
				result = listAllComputer(search,orderBy);
			}
		}
		PageBuilder<DtoComputer> builder = checkPage(url,result,index,size,search,order);
		System.out.println(builder.getIndex());
		Page<DtoComputer> page = builder.build();

		return page;		
	}

	private String getOrder(String order) {
		String orderBy = "";

		if (order == null) {
			return orderBy;
		}

		for (orderEnum o : Page.orderEnum.values()) {
			if (o.getTag().equals(order)) {
				orderBy = o.getValue();
			}
		}		
		return orderBy;
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

	public PageBuilder<DtoComputer> checkPage(String url, List<DtoComputer> content, Integer index, Integer size, String search, String order) throws ExceptionModel {
		PageBuilder<DtoComputer> pageBuilder = new PageBuilder<DtoComputer>();

		if(index == null) {
			index = Integer.valueOf(0);
		}
		if(size == null) {
			size = Integer.valueOf(10);
		}
		if(url == null) {
			throw new ExceptionModel("An url is needed");
		}
		if(search == null) {
			search = "";
		}
		if(order == null) {
			order = "";
		}

		pageBuilder.setSize(size)
		.setUrl(url)
		.setSearch(search)
		.setOrder(order)
		.setContent(content)
		.setIndex(index);

		return pageBuilder;
	}
}
