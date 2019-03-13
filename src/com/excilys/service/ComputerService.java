package com.excilys.service;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.model.Computer;
import com.excilys.persistence.DaoComputer;


public class ComputerService {
	private DaoComputer daoComputer;
	public ComputerService () {
		this.daoComputer = new DaoComputer();
	}
	
	public ArrayList<Computer> listComputers(){
		ArrayList<Computer> result = new ArrayList<>();
		result = daoComputer.listAllComputer();
		return result;
	}
	
	public Optional<Computer> showDetails(Integer id) {
		return daoComputer.findComputerById(id);
	}
	
}
