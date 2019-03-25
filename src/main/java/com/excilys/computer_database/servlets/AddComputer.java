package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.controller.Controler;
import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.persistence.DaoCompany;

@WebServlet(name = "AddComputer", urlPatterns = { "/addComputer" })
public class AddComputer extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Controler controler;
	private static Logger logger = LoggerFactory.getLogger(DaoCompany.class);

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name, introduced, discontinued, companyId;
		controler = Controler.getInstance();

		name = request.getParameter("name");
		introduced = request.getParameter("introduced");
		discontinued = request.getParameter("discontinued");
		companyId = request.getParameter("companyId");

		try {
			this.controler.createComputer(name, introduced, discontinued, Integer.valueOf(companyId));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ExceptionDao | ExceptionModel e) {
			logger.error(e.getMessage());
		}

		response.sendRedirect("dashboard");
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controler = Controler.getInstance();
		
		ArrayList<DtoCompany> listCompanies = new ArrayList<>();
		try {
			listCompanies = controler.getListCompany();
		} catch (ExceptionDao | ExceptionModel e) {
			logger.error(e.getMessage());
		}
		request.setAttribute("listCompanies", listCompanies);
		
		
		this.getServletContext()
		.getRequestDispatcher("/views/addComputer.jsp")
		.forward(request, response);
		
	}
}
