package com.excilys.computer_database.console_controller;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.computer_database.binding_dto.ComputerDto;
import com.excilys.computer_database.console.RestClient;
import com.excilys.computer_database.console_view.CreateComputerView;
import com.excilys.computer_database.console_view.DeleteCompanyView;
import com.excilys.computer_database.console_view.MenuOptionsView;
import com.excilys.computer_database.console_view.MenuView;
import com.excilys.computer_database.console_view.ShowComputerView;
import com.excilys.computer_database.console_view.UpdateComputerView;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.utils.Util;

@Controller
public class CliController {

	@Autowired
	private CompanyService companyService;

	/**
	 * Controller logger, used to trace logs
	 */
	private static Logger logger = LoggerFactory.getLogger(CliController.class);


	/**
	 * This method is the main method, running until the client choose to close the app.
	 * 
	 * The loop will ask for utilisator input and compared it to menu options, then call the respected view.
	 * @see Util
	 */
	public void run() {
		boolean run = true;
		RestClient client = new RestClient();

		while (run) {
			String input = MenuView.show();
			Optional<Integer> toSwitch = Util.parseInt(input);
			switch(toSwitch.isPresent() ? MenuOptionsView.getById(toSwitch.get()) : MenuOptionsView.EXIT) {


			case LIST_COMPUTER: 
				List<ComputerDto> computers = client.findAll();
				computers.forEach(c -> logger.info(c.toString()));
				break;

			case SHOW_COMPUTER: 
				show(client);
				break;
			case DELETE_COMPANY:
				Optional<Integer> id = Util.parseInt(DeleteCompanyView.show());
				if (id.isPresent()) {
					deleteAndShowView(id.get());
				} else {
					logger.info("Wrong input, try again.");
				}
				break;

			case CREATE_COMPUTER:
				create(client);
				break;
				
			case UPDATE_COMPUTER:
				update(client);
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
		companyService.deleteCompany(id);
		DeleteCompanyView.exec(true);
	}
	
	private void show(RestClient client) {
		Optional<Integer> id = Util.parseInt(ShowComputerView.show());
		if (id.isPresent()) {
			String computer = client.showDetails(id.get()).toString();
			logger.info(computer);
		} else {
			logger.info("Wrong input, try again.");
		}
	}
	
	private void create (RestClient client) {
		ComputerDto data = CreateComputerView.show();
		if (client.create(data) != 200){
			logger.info("Wrong input");
		}
		else {
			logger.info("Computer created");
		}
	}

	private void update (RestClient client) {
		Optional<Integer> id = UpdateComputerView.showId();
		if (id.isPresent()){
			ComputerDto data = UpdateComputerView.show();
			if (client.update(data) != 200){
				logger.info("Error update");
			}
			else {
				logger.info("Computer updated");
			}
		}
		else {
			logger.info("Wrong input");
		}
	}
}