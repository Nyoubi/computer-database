
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
		logger.info("\n==========================");
		logger.info("Choose an option");
		logger.info(MenuOptionsView.SHOW_COMPUTER.getId() + " - Show details of a computer");
		logger.info(MenuOptionsView.DELETE_COMPANY.getId() + " - Delete a company");
		logger.info(MenuOptionsView.EXIT.getId() + " - Exit");
		logger.info("==========================\n");

		value = input.nextLine();
		
		return value;
	}
}