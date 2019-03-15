package com.excilys.computer_database.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.computer_database.controller.Controller;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.persistence.DaoComputer;


public class ComputerService {
	private DaoComputer daoComputer;
	
    private static volatile ComputerService instance = null;
    
	public static ComputerService getInstance()
    {   
		if (instance == null) {
			synchronized(Controller.class) {
				if (instance == null) {
					instance = new ComputerService();
				}
			}
		}
		return instance;
    }
	
	private ComputerService () {
		this.daoComputer = DaoComputer.getInstance();
	}
	
	public ArrayList<Computer> listComputers(){
		ArrayList<Computer> result = new ArrayList<>();
		result = daoComputer.listAllComputer();
		return result;
	}
	
	public Optional<Computer> showDetails(Integer id) {
		return daoComputer.findComputerById(id);
	}
	
	public Boolean deleteComputer(Integer id) {
		return daoComputer.deleteComputerById(id);
	}
	
	public Boolean createComputer(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		return daoComputer.createComputer(name,introduced,discontinued,companyId);
	}
	
	public Boolean updateComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		return daoComputer.updateComputer(id, name, introduced, discontinued, companyId);
	}
	
}
