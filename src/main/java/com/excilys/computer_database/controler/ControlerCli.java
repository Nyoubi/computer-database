package com.excilys.computer_database.controler;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.util.Util;
import com.excilys.computer_database.view.DeleteCompanyView;
import com.excilys.computer_database.view.MenuView;
import com.excilys.computer_database.view.MenuViewOptions;

/** Unique controller of the application
 * 	
 * @author Killian Martin
 *
 */
public class ControlerCli {

	/**
	 * The company service
	 */
	private CompanyService companyService;
	
	public ControlerCli(CompanyService companyService) {
		this.companyService = companyService;
	}

	/**
	 * Controller logger, used to trace logs
	 */
	private static Logger logger = LoggerFactory.getLogger(ControlerCli.class);

	/**
	 * Controller constructor, initialize services
	 * @see CompanyService
	 * @see ComputerService
	 * @
	 */
	public ControlerCli(ComputerService computerService, CompanyService companyService) {
		this.companyService = companyService;
	}

	/**
	 * This method is the main method, running until the client choose to close the app.
	 * 
	 * The loop will ask for utilisator input and compared it to menu options, then call the respected view.
	 * @see Util
	 */
	public void run() {
		boolean run = true;
		while (run) {
			String input = MenuView.show();
			switch(MenuViewOptions.getById(Util.parseInt(input).get())) {
			
			case DELETE_COMPANY:
			Optional<Integer> idd = Util.parseInt(DeleteCompanyView.show());
			if (idd.isPresent()) {
				deleteAndShowView(idd.get());
			} else {
				logger.info("Wrong input, try again.");
			}
			break;
			
			case EXIT:
				logger.info("Goodbye :3");
				run = false;
				break;
				
			case ERROR: logger.info("This options doesn't exist.");
				break;
			default: logger.error("Somethings bad happened");
			break;
			}

		}
	}

	private void deleteAndShowView(Integer id) {
		try {
			//companyService.deleteCompany(id);
			DeleteCompanyView.exec(true);
		} catch (Exception /*ExceptionDao*/ e) {
			DeleteCompanyView.exec(false);
		}
	}

}