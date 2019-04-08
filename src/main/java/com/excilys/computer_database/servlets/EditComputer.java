package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.app.App;
import com.excilys.computer_database.dto.DtoCompany;
import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionInvalidInput;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

@WebServlet(name = "EditComputer", urlPatterns = { "/editComputer" })
public class EditComputer extends HttpServlet{

	private static final long serialVersionUID = -3250717198410062056L;
	
	private ComputerService computerService;
	private CompanyService companyService;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String id, name, introduced, discontinued, companyId;
		computerService = ComputerService.getInstance(App.dataSource);

		id = request.getParameter("idComputer");
		name = request.getParameter("name");
		introduced = request.getParameter("introduced");
		discontinued = request.getParameter("discontinued");
		companyId = request.getParameter("companyId");
		
		try {
			this.computerService.updateComputer(Integer.valueOf(id), name, introduced, discontinued, Integer.valueOf(companyId));
			response.sendRedirect("dashboard");
		} catch (ExceptionDao | ExceptionModel e) {
			errorRedirect(request,response,e.getMessage());
		}
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		computerService = ComputerService.getInstance(App.dataSource);
		Optional<DtoComputer> computer = Optional.empty();
		companyService = CompanyService.getInstance(App.dataSource);
		
		ArrayList<DtoCompany> listCompanies = new ArrayList<>();
		try {
			listCompanies = companyService.listCompanies();
			request.setAttribute("listCompanies", listCompanies);
			computer = computerService.showDetails(request.getParameter("id"));

			if (computer.isPresent()) {
				request.setAttribute("computer", computer.get());
			} else {
				response.sendRedirect("dashboard");
			}
		} catch (ExceptionDao | ExceptionModel | ExceptionInvalidInput e) {
			errorRedirect(request,response,e.getMessage());
		}
		this.getServletContext()
		.getRequestDispatcher("/views/editComputer.jsp")
		.forward(request, response);
		
	}
		
	public void errorRedirect(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("Exception", message);
		this.getServletContext()
		.getRequestDispatcher("/views/500.jsp")
		.forward(request, response);
	}
}
