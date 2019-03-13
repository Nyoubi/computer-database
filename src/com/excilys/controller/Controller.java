package com.excilys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.util.Util;
import com.excilys.view.View;
import com.excilys.view.menu.CompanyListView;
import com.excilys.view.menu.ComputerListView;
import com.excilys.view.menu.ComputerView;
import com.excilys.view.menu.MenuView;


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
			switch(Util.parseInt(input)) {
				case 1: ComputerListView computerListView = new ComputerListView(computerService.listComputers());
						computerListView.show();
						break;
				case 2: CompanyListView companyListView = new CompanyListView(companyService.listCompanies());
						companyListView.show();
						break;
				case 3: ComputerView computerView = new ComputerView();
						Integer id = Util.parseInt(computerView.show());
						computerView.exec(computerService.showDetails(id));
						break;
				default: run = false;
						 break;
			}
			
		}
	}
	
}
