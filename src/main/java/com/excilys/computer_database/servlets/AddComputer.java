package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

@WebServlet(name = "AddComputer", urlPatterns = { "/addComputer" })
public class AddComputer extends HttpServlet{
	
	private static final long serialVersionUID = 154395876719316343L;
	
	private ComputerService computerService;
	private CompanyService companyService;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name, introduced, discontinued, companyId;
		computerService = ComputerService.getInstance();

		name = request.getParameter("name");
		introduced = request.getParameter("introduced");
		discontinued = request.getParameter("discontinued");
		companyId = request.getParameter("companyId");
		
		try {
			this.computerService.createComputer(name, introduced, discontinued, Integer.valueOf(companyId));
			response.sendRedirect("dashboard");
		} catch (ExceptionDao | ExceptionModel e) {
			response.sendRedirect("/500.html");
		}
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		companyService = CompanyService.getInstance();
		
		ArrayList<DtoCompany> listCompanies = new ArrayList<>();
		try {
			listCompanies = companyService.listCompanies();
			request.setAttribute("listCompanies", listCompanies);
			this.getServletContext()
			.getRequestDispatcher("/views/addComputer.jsp")
			.forward(request, response);
		} catch (ExceptionDao e) {
			System.out.println(e.errorMessage);
			response.sendRedirect("/500.html");
		}		
	}
}
