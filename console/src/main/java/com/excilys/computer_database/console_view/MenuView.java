
package com.excilys.computer_database.console_view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuView {
	protected static Scanner input;
	protected static Logger logger = LoggerFactory.getLogger(MenuView.class);
	
	//Private constructor
	private MenuView () {}
	
	public static String show() {
		String value;
		input = new Scanner(System.in);
		logger.info("\n========================");
		logger.info("Choose an option");
		logger.info("{} - Show list computer", MenuOptionsView.LIST_COMPUTER.getId());
		logger.info("{} - Show details of a computer", MenuOptionsView.SHOW_COMPUTER.getId());
		logger.info("{} - Create a computer", MenuOptionsView.CREATE_COMPUTER.getId());
		logger.info("{} - Update a computer", MenuOptionsView.UPDATE_COMPUTER.getId());
		logger.info("{} - Delete a company", MenuOptionsView.DELETE_COMPANY.getId());
		logger.info("{} - Exit", MenuOptionsView.EXIT.getId());
		logger.info("==========================\n");

		value = input.nextLine();
		
		return value;
	}
}