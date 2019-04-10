package com.excilys.computer_database.servlets;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.computer_database.app.AppConfig;

public abstract class ServletData {
	
	static final String URL_LIST_COMPUTERS = "dashboard";
	
	static final String VIEW_EDIT_COMPUTER = "/views/editComputer.jsp";
	static final String VIEW_ERROR_500 = "/views/500.jsp";
	static final String VIEW_LIST_COMPUTERS = "/views/dashboard.jsp";
	static final String VIEW_ADD_COMPUTERS = "/views/addComputer.jsp";
	
	static final GenericApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
}
