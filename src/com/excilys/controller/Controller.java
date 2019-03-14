package com.excilys.controller;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.util.Util;
import com.excilys.view.View;
import com.excilys.view.menu.CompanyListView;
import com.excilys.view.menu.ComputerListView;
import com.excilys.view.menu.ComputerView;
import com.excilys.view.menu.CreateComputerView;
import com.excilys.view.menu.DeleteComputerView;
import com.excilys.view.menu.MenuView;
import com.excilys.view.menu.MenuViewOptions;
import com.excilys.view.menu.UpdateComputerView;

public class Controller {
	private ComputerService computerService;
	private CompanyService companyService;
	
	private static Logger logger = LoggerFactory.getLogger(Controller.class);

	public Controller () {
		this.companyService = new CompanyService();
		this.computerService = new ComputerService();
	}
	
	public void run() {
		View menu = new MenuView();
		boolean run = true;
		while (run) {
			String input = menu.show();
			switch(MenuViewOptions.getById(Util.parseInt(input))) {
				case LIST_COMPUTERS: ComputerListView computerListView = new ComputerListView(computerService.listComputers());
					 computerListView.show();
					 break;
					 
				case LIST_COMPANIES: CompanyListView companyListView = new CompanyListView(companyService.listCompanies());
					 companyListView.show();
					 break;
					 
				case COMPUTER_DETAILS: ComputerView computerView = new ComputerView();
					 Integer id = Util.parseInt(computerView.show());
					 ComputerView.exec(computerService.showDetails(id));
					 break;
					 
				case CREATE_COMPUTER: CreateComputerView createComputerView = new CreateComputerView();
					 ArrayList<String> info = createComputerView.show();
					 createComputerView.exec(computerService.createComputer(info.get(0),
							 												Util.stringToTimestamp(info.get(1)),
							 												Util.stringToTimestamp(info.get(2)),
							 												Util.parseInt(info.get(3))));
					 break;
					 
				case UPDATE_COMPUTER: UpdateComputerView updateComputerView = new UpdateComputerView();
					 ArrayList<String> infoUpdate = updateComputerView.show();
					 updateComputerView.exec(computerService.updateComputer(Util.parseInt(infoUpdate.get(0)),
							 												infoUpdate.get(1),
																			Util.stringToTimestamp(infoUpdate.get(2)),
																			Util.stringToTimestamp(infoUpdate.get(3)),
																			Util.parseInt(infoUpdate.get(4))));
					 break;
					 
				case DELETE_COMPUTER: DeleteComputerView deleteComputerView = new DeleteComputerView();
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
