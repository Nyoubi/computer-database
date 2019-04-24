package com.excilys.computer_database.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.InvalidInputException;
import com.excilys.computer_database.exception.ValidationException;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.model.Page.orderEnum;
import com.excilys.computer_database.model.PageBuilder;
import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.util.Util;

@Service
public class ComputerService {
	
	@Autowired
	private DaoComputer daoComputer;

	public List<ComputerDto> listAllComputer(String order)  throws DaoException{
		return daoComputer.findAll(order).stream().map(computer -> ComputerMapper.computerToDtoComputer(computer)).collect(Collectors.toList());
	}
	
	public List<ComputerDto> listAllComputer(String search, String order)  throws DaoException{
		return daoComputer.findAll("%"+search+"%",order).stream().map(computer -> ComputerMapper.computerToDtoComputer(computer)).collect(Collectors.toList());

	}
	
	public Optional<ComputerDto> showDetails(String id)  throws DaoException, InvalidInputException {
		Optional<Integer> ident = Util.parseInt(id);
		if (ident.isPresent()) {
			Optional<Computer> computer = daoComputer.findById(ident.get());
			if (computer.isPresent()) {
				return Optional.of(ComputerMapper.computerToDtoComputer(computer.get()));
			} else {
				throw new DaoException("The computer " + id + " doesn't exist in the database.");
			}
		} else {
			throw new InvalidInputException("This id can't be converted to an integer.");
		}
	}

	public void deleteComputer(String id) throws DaoException, InvalidInputException {

		Optional<Integer> ident = Util.parseInt(id);
		if (Util.checkOptional(ident) != null) {
			daoComputer.deleteById(ident.get());
		} else {
			throw new InvalidInputException("This id : " + id + " is invalid.");
		}
	}

	public void createComputer(Computer computer) throws DaoException {
			if(daoComputer.insert(computer) == 0) {
				throw new DaoException("daoException.insertComputer");
			}
	}


	public void updateComputer(Computer computer) throws DaoException {
		if(daoComputer.update(computer) == 0) {
			throw new DaoException("daoException.updateComputer");
		}
	}

	public Page<ComputerDto> pageDtoComputer(String url, String index, String size, String search, String order) throws ValidationException, DaoException {
		List<ComputerDto> result = new ArrayList<>();
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
		
		Optional<Integer> optIndex = Util.parseInt(index);
		Optional<Integer> optSize = Util.parseInt(size);

		PageBuilder<ComputerDto> builder = checkPage(url,
													 result,
													 optIndex.isPresent() ? optIndex.get() : null,
													 optIndex.isPresent() ? optSize.get() : null,
													 search,order);
		Page<ComputerDto> page = builder.build();
		return page;		
	}

	public String getOrder(String order) {
	
		for (orderEnum o : Page.orderEnum.values()) {
			if (o.getTag().equals(order)) {
				return o.getValue();
			}
		}
		return Page.orderEnum.DEFAULT.getValue();
	}
	
	public PageBuilder<ComputerDto> checkPage(String url, List<ComputerDto> content, Integer index, Integer size, String search, String order) throws ValidationException{
		PageBuilder<ComputerDto> pageBuilder = new PageBuilder<ComputerDto>();

		if(index == null) {
			index = Integer.valueOf(1);
		}
		if(size == null) {
			size = Integer.valueOf(10);
		}
		if(url == null) {
			throw new ValidationException("An url is needed");
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
