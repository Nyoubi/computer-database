package com.excilys.computer_database.app;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer{
	public static AnnotationConfigWebApplicationContext ctx;

	@Override
	public void onStartup(ServletContext servletCtx) throws ServletException  {
		ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);
		ctx.setServletContext(servletCtx);
		ctx.refresh();

		servletCtx.addListener(new ContextLoaderListener(ctx));
		DispatcherServlet servlet = new DispatcherServlet(ctx);

		ServletRegistration.Dynamic registration = servletCtx.addServlet("app", servlet);
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
	}
}
