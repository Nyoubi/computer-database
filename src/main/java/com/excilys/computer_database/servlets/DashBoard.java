package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.service.ComputerService;

@WebServlet(name = "DashBoard", urlPatterns = { "/dashboard" })
public class DashBoard extends HttpServlet{

	private static final long serialVersionUID = 4605140799487702645L;
	
	private ComputerService computerService;
	private static final String URL = "dashboard";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		computerService = ComputerService.getInstance();
		
		getIndex(request);getSize(request);
		
		Integer index = (Integer) request.getAttribute("index");
		Integer size = (Integer) request.getAttribute("size");
		
		Optional<Page<DtoComputer>> showComputers = Optional.empty();
		Optional<Integer> computerCount = Optional.empty();

		try {
			computerCount = computerService.getNbComputer();
			showComputers = computerService.pageDtoComputer(URL, index, size);
		} catch (ExceptionDao | ExceptionModel e) {
			response.sendRedirect("/500.html");
		}
		
		if (!showComputers.isPresent()) {
			response.sendRedirect("/500.html");
		}
		
		request.setAttribute("computerData", showComputers.get().getPageContent());
		request.setAttribute("computerPage", showComputers.get());
		if (computerCount.isPresent()) {
			request.setAttribute("numberComputer", computerCount.get());
		} else {
			request.setAttribute("numberComputer", 0);
		}
		
		this.getServletContext()
		.getRequestDispatcher("/views/dashboard.jsp")
		.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void getIndex(HttpServletRequest request) {
		
		Integer index = 1;
		
		String indexParam = request.getParameter("index");
		
		if(indexParam!=null && !indexParam.equals("")) {
			index = Integer.parseInt(indexParam);
		}
		
		request.setAttribute("index", index);
	}

	private void getSize(HttpServletRequest request) {
		Integer size = (Integer) request.getAttribute("size");

		String sizeParam = request.getParameter("size");

		if(sizeParam != null && !sizeParam.equals("") && Page.getSizeList().indexOf(Integer.parseInt(sizeParam)) != -1) {
			size = Integer.parseInt(sizeParam);
		}
		
		request.setAttribute("size", size);
	}
}
