package com.excilys.computer_database.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.controller.Controller;


/**
 * 
 * @author Killian Martin
 *
 */
public class App {
	
	private static Logger logger = LoggerFactory.getLogger(App.class);
	public static void main(String[] args) {
			logger.trace("Main started");
			Controller controller = Controller.getInstance();
			controller.run();
	}

	
}