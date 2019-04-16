package com.excilys.computer_database.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.controler.ControlerCli;

public class App {
	
	private static Logger logger = LoggerFactory.getLogger(App.class); 

	public static void main( String[] args )
	{
		logger.info("App starting");
		GenericApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ControlerCli controler = context.getBean(ControlerCli.class);
		controler.run();
		context.close();
	}
}