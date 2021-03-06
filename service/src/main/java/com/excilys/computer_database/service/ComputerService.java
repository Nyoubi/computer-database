package com.excilys.computer_database.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.binding_dto.ComputerDto;
import com.excilys.computer_database.binding_exception.DaoException;
import com.excilys.computer_database.binding_exception.InvalidInputException;
import com.excilys.computer_database.binding_exception.ValidationException;
import com.excilys.computer_database.dao.DaoComputer;
import com.excilys.computer_database.mapper.ComputerMapper;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.model.Page.orderEnum;
import com.excilys.computer_database.model.PageBuilder;
import com.excilys.computer_database.utils.Util;

@Service
public class ComputerService {
	
	@Autowired
	private DaoComputer daoComputer;

	
	public List<ComputerDto> listAllComputer(String search, Pageable page) {
		return daoComputer.findAllByNameContains(search,page)
				.stream()
				.map(ComputerMapper::computerToDtoComputer)
				.collect(Collectors.toList());
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

	
	public void deleteComputer(String id) throws InvalidInputException {
		Optional<Integer> ident = Util.parseInt(id);
		if (ident.isPresent() && Util.checkOptional(ident) != null) {
			daoComputer.deleteById(ident.get());
		} else {
			throw new InvalidInputException("This id : " + id + " is invalid.");
		}
	}
	
	public Computer createComputer(Computer computer) throws DaoException {
		try {
			return daoComputer.save(computer);
		} catch (DataAccessException e) {
			throw new DaoException("daoException.insertComputer");
		}
	}

	public Computer updateComputer(Computer computer) throws DaoException {
		try {
			return daoComputer.save(computer);
		} catch (DataAccessException e) {
			throw new DaoException("daoException.updateComputer");
		}
	}

	public Page<ComputerDto> pageDtoComputer(String url, String index, String size, String search, String order) throws ValidationException {
		List<ComputerDto> result;
		
		Optional<Integer> optIndex = Util.parseInt(index);
		Optional<Integer> optSize = Util.parseInt(size);
		
		result = listAllComputer(search, PageRequest.of(optIndex.isPresent() ? optIndex.get()-1 : 0, optSize.isPresent() ? optSize.get() : 10, getOrder(order)));

		Integer count = 0;
		
		if ("".equals(search)) {
			count = (int) daoComputer.count();
		} else {
			count = daoComputer.countByNameContains(search);
		}
		
		PageBuilder<ComputerDto> builder = checkPage(url,
													 result,
													 count,
													 optIndex.isPresent() ? optIndex.get() : 0,
													 optSize.isPresent() ? optSize.get() : 10,
													 search,order);

		return builder.build();		
	}

	public Sort getOrder(String order) {
		for (orderEnum o : Page.orderEnum.values()) {
			if (o.getTag().equals(order)) {
				return o.getValue();
			}
		}
		return Page.orderEnum.DEFAULT.getValue();
	}
	
	public PageBuilder<ComputerDto> checkPage(String url, List<ComputerDto> content,Integer count, Integer index, Integer size, String search, String order) throws ValidationException{
		PageBuilder<ComputerDto> pageBuilder = new PageBuilder<>();

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
		.setTotalSize(count)
		.setOrder(order)
		.setContent(content)
		.setIndex(index);

		return pageBuilder;
	}
}
