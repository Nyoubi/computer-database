package com.excilys.computer_database.console_view;

import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.binding_dto.ComputerDto;
import com.excilys.computer_database.binding_dto.ComputerDtoBuilder;
import com.excilys.computer_database.utils.Util;

public class UpdateComputerView {
	
	protected static Scanner input;
	protected static Logger logger = LoggerFactory.getLogger(MenuView.class);

	//Private constructor
	private UpdateComputerView () {}
	private static Optional<Integer> id;
	
	public static Optional<Integer> showId () {
		input = new Scanner(System.in);
		logger.info("Choose a computer id you want to update:");
		id = Util.parseInt(input.nextLine());
		return id;
	}
	
	public static ComputerDto show() {
		input = new Scanner(System.in);
		logger.info("Choose new a computer name (can't be empty):");
		String name = input.nextLine();
		logger.info("Choose a introduced date (dd/mm/yyyy):");
		String introduced = input.nextLine();
		logger.info("Choose a discontinued date (dd/mm/yyyy):");
		String discontinued = input.nextLine();
		logger.info("Choose a computer company id:");
		String companyId = input.nextLine();
		
		return new ComputerDtoBuilder()
				.setId(id.isPresent() ? id.get() : 0)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompanyId(Integer.parseInt(companyId))
				.build();
	}
}