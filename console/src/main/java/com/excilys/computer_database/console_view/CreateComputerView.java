package com.excilys.computer_database.console_view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.binding_dto.ComputerDto;
import com.excilys.computer_database.binding_dto.ComputerDtoBuilder;

public class CreateComputerView {
	
	protected static Scanner input;
	protected static Logger logger = LoggerFactory.getLogger(MenuView.class);

	//Private constructor
	private CreateComputerView () {}
	
	public static ComputerDto show() {
		input = new Scanner(System.in);
		logger.info("Choose a computer name:");
		String name = input.nextLine();
		logger.info("Choose a computer introduced date (dd/mm/yyyy):");
		String introduced = input.nextLine();
		logger.info("Choose a computer discontinued date (dd/mm/yyyy):");
		String discontinued = input.nextLine();
		logger.info("Choose a computer company id:");
		String companyId = input.nextLine();
		
		return new ComputerDtoBuilder().setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyId(Integer.parseInt(companyId))
				.build();
	}
}