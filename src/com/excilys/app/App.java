package com.excilys.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.controller.Controller;

public class App {
	private static Logger logger = LoggerFactory.getLogger(Controller.class);
	public static void main(String[] args) {
			logger.trace("Main started");
			Controller controller = new Controller();
			controller.run();
	}
}
