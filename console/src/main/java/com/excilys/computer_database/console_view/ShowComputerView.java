package com.excilys.computer_database.console_view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowComputerView {
	
	protected static Scanner input;
	protected static Logger logger = LoggerFactory.getLogger(MenuView.class);

	//Private constructor
	private ShowComputerView () {}
	
	public static String show() {
		input = new Scanner(System.in);
		logger.info("Choose a computer id you want to see:");
		return input.nextLine();
		
	}
}