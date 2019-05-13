package com.excilys.computer_database.console;

import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.console_controller.CliController;

public class App {
	
	private static Logger logger = LoggerFactory.getLogger(App.class); 

	static int[][] a;
	public static void main( String[] args )
	{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		logger.info("App starting");
		CliController controler = new CliController();
		controler.run();
	}
}
