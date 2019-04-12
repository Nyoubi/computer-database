package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.excilys.computer_database.model.Page.orderEnum;
import com.excilys.computer_database.model.PageBuilder;
import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.util.Util;

@Service
public class ComputerService {
	
	@Autowired
	private DaoComputer daoComputer;
	
	@Autowired
	private CompanyService companyService;

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
		Computer computer = checkDataComputer(name, introduced, discontinued, companyId).build();
		daoComputer.createComputer(computer);
	}


	public void updateComputer(Integer id, String name, String introduced, String discontinued, Integer companyId) throws ExceptionDao, ExceptionModel {
		Computer computer = checkDataComputer(name, introduced, discontinued, companyId).setId(checkId(id)).build();
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
		Page<DtoComputer> page = builder.build();

		return page;		
	}

	public String getOrder(String order) {
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
	
	public Integer checkId(Integer id) throws ExceptionModel {
		if (id == null) {
			throw new ExceptionModel("Failed to check computer : Incorrect id");
		}
		return id;
	}
	
	public void checkName(String name) throws ExceptionModel {
		if (name == null || name == "") {
			throw new ExceptionModel("Failed to check computer : Invalid name");
		}
	}
	
	public ArrayList<Optional<Timestamp>> checkDate(String introduced, String discontinued) throws ExceptionModel {
		Optional<Timestamp> OptIntroduced = Util.stringToTimestamp(introduced);
		Optional<Timestamp> OptDiscontinued = Util.stringToTimestamp(discontinued);

		if (!OptIntroduced.isPresent() && OptDiscontinued.isPresent()) {
			throw new ExceptionModel("Failed to check computer : Discontinued but not introduced");
		}
		if (OptIntroduced.isPresent() && OptDiscontinued.isPresent()
				&& OptIntroduced.get().after(OptDiscontinued.get())) {
			throw new ExceptionModel("Failed to check computer : Introduced can't be after discontinued date");
		}
		
		return new ArrayList<Optional<Timestamp>>(Arrays.asList(OptIntroduced,OptDiscontinued));
	}
	
	public Optional<Company> checkCompany(Integer companyId) throws ExceptionModel, ExceptionDao {
		Optional<DtoCompany> dtoCompany = companyService.findCompanyById(companyId);
		Optional<Company> company = Optional.empty();
		if (dtoCompany.isPresent()) {
			company = CompanyMapper.dtoCompanyToCompany(dtoCompany.get());
			if (!company.isPresent()) {
				throw new ExceptionModel("Mapper : Can't convert the dto company to company");
			}
		} else if (companyId != null){
			throw new ExceptionModel("This company doesn't exist");
		}
		
		return company;
	}

	public ComputerBuilder checkDataComputer(String name, String introduced, String discontinued, Integer companyId) throws ExceptionModel, ExceptionDao {
		checkName(name);
		ArrayList<Optional<Timestamp>> date = checkDate(introduced,discontinued);
		Optional<Company> company = checkCompany(companyId);
		
		ComputerBuilder computerBuilder = new ComputerBuilder().setName(name)
				.setIntroduced(date.get(0).isPresent() ? date.get(0).get() : null)
				.setDiscontinued(date.get(1).isPresent() ? date.get(1).get() : null)
				.setCompany(company.isPresent() ? company.get() : null)
				.setId(null);
		return computerBuilder;
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
