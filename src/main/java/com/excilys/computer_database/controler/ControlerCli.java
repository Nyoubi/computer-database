package com.excilys.computer_database.controler;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.util.Util;
import com.excilys.computer_database.view.DeleteCompanyView;
import com.excilys.computer_database.view.MenuView;
import com.excilys.computer_database.view.MenuViewOptions;

@Component
public class ControlerCli {
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * Controller logger, used to trace logs
	 */
	private static Logger logger = LoggerFactory.getLogger(ControlerCli.class);


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
			companyService.deleteCompany(id);
			DeleteCompanyView.exec(true);
		} catch (Exception /*ExceptionDao*/ e) {
			DeleteCompanyView.exec(false);
		}
	}

}