package com.excilys.computer_database.console_view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.console.App;

public class DeleteCompanyView {
	
	//Private constructor
	private DeleteCompanyView() {}
	
	protected static Scanner input;
	private static Logger logger = LoggerFactory.getLogger(App.class); 

	public static String show() {
		input = new Scanner(System.in);
		logger.info("Choose a company id you want to delete:");
		return input.nextLine();
		
	}
	
	public static void exec(Boolean deleted) {
		if (deleted)
			logger.info("Company has been deleted.");
		else
			logger.info("This company doesn't exist in the database.");
	}
}