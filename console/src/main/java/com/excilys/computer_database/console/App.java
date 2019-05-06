package com.excilys.computer_database.console;

import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.console_config.SpringConsoleConfiguration;
import com.excilys.computer_database.console_controller.CliController;

public class App {
	
	private static Logger logger = LoggerFactory.getLogger(App.class); 

	static int a [][];
	public static void main( String[] args )
	{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		logger.info("App starting");
		GenericApplicationContext context = new AnnotationConfigApplicationContext(SpringConsoleConfiguration.class);
		CliController controler = context.getBean(CliController.class);
		controler.run();
		context.close();
	}
}
