package com.excilys.computer_database.servlets;

import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.excilys.computer_database.app.AppConfig;

public class ServletData extends AbstractContextLoaderInitializer{
	
	static final String URL_LIST_COMPUTERS = "dashboard";
	
	static final String VIEW_EDIT_COMPUTER = "/views/editComputer.jsp";
	static final String VIEW_ERROR_500 = "/views/500.jsp";
	static final String VIEW_LIST_COMPUTERS = "/views/dashboard.jsp";
	static final String VIEW_ADD_COMPUTERS = "/views/addComputer.jsp";
	
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(AppConfig.class);
		return context;
	}
	
	
}
