package com.excilys.computer_database.controller;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.util.Util;
import com.excilys.computer_database.view.menu.CompanyListView;
import com.excilys.computer_database.view.menu.ComputerListView;
import com.excilys.computer_database.view.menu.ComputerView;
import com.excilys.computer_database.view.menu.CreateComputerView;
import com.excilys.computer_database.view.menu.DeleteComputerView;
import com.excilys.computer_database.view.menu.MenuView;
import com.excilys.computer_database.view.menu.MenuViewOptions;
import com.excilys.computer_database.view.menu.UpdateComputerView;


public class Controller {
	private ComputerService computerService;
	private CompanyService companyService;
	
    private static volatile Controller instance = null;
	private static Logger logger = LoggerFactory.getLogger(Controller.class);

	private Controller () {
		this.companyService = CompanyService.getInstance();
		this.computerService = ComputerService.getInstance();
	}
	
	public static Controller getInstance()
    {   
		if (instance == null) {
			synchronized(Controller.class) {
				if (instance == null) {
					instance = new Controller();
				}
			}
		}
		return instance;
    }
	
	public void run() {
		MenuView menu = MenuView.getInstance();
		boolean run = true;
		while (run) {
			String input = menu.show();
			switch(MenuViewOptions.getById(Util.parseInt(input))) {
				case LIST_COMPUTERS: ComputerListView computerListView = ComputerListView.getInstance(computerService.listComputers());
					 computerListView.show();
					 break;
					 
				case LIST_COMPANIES: CompanyListView companyListView = CompanyListView.getInstance(companyService.listCompanies());
					 companyListView.show();
					 break;
					 
				case COMPUTER_DETAILS: ComputerView computerView = ComputerView.getInstance();
					 Integer id = Util.parseInt(computerView.show());
					 ComputerView.exec(computerService.showDetails(id));
					 break;
					 
				case CREATE_COMPUTER: CreateComputerView createComputerView = CreateComputerView.getInstance();
					 ArrayList<String> info = createComputerView.show();
					 createComputerView.exec(computerService.createComputer(info.get(0),
							 												Util.stringToTimestamp(info.get(1)),
							 												Util.stringToTimestamp(info.get(2)),
							 												Util.parseInt(info.get(3))));
					 break;
					 
				case UPDATE_COMPUTER: UpdateComputerView updateComputerView = UpdateComputerView.getInstance();
					 ArrayList<String> infoUpdate = updateComputerView.show();
					 updateComputerView.exec(computerService.updateComputer(Util.parseInt(infoUpdate.get(0)),
							 												infoUpdate.get(1),
																			Util.stringToTimestamp(infoUpdate.get(2)),
																			Util.stringToTimestamp(infoUpdate.get(3)),
																			Util.parseInt(infoUpdate.get(4))));
					 break;
					 
				case DELETE_COMPUTER: DeleteComputerView deleteComputerView = DeleteComputerView.getInstance();
					 Integer idd = Util.parseInt(deleteComputerView.show());
					 deleteComputerView.exec(computerService.deleteComputer(idd));
					 break;
				case EXIT:
					 logger.info("Goodbye :3");
					 run = false;
					 break;
				case ERROR:
					 System.out.println("This options doesn't exist.");
					 break;
				default: logger.error("Somethings bad happened");
					 break;
			}
			
		}
	}
	
}
