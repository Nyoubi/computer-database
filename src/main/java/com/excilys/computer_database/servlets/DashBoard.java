package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.app.App;
import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionInvalidInput;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.service.ComputerService;

@WebServlet(name = "DashBoard", urlPatterns = { "/dashboard" })
public class DashBoard extends HttpServlet{

	private static final long serialVersionUID = 4605140799487702645L;

	private ComputerService computerService;

	private static final String URL = "dashboard";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		computerService = ComputerService.getInstance(App.dataSource);

		getIndex(request);getSize(request);getSearch(request);getOrder(request);
		Integer index = (Integer) request.getAttribute("index");
		Integer size = (Integer) request.getAttribute("size");
		String search = (String) request.getAttribute("search");
		String order = (String) request.getAttribute("order");
				
		Optional<Page<DtoComputer>> showComputers = Optional.empty();
		try {
			showComputers = computerService.pageDtoComputer(URL, index, size, search, order);
		} catch (ExceptionDao | ExceptionModel e) {
			errorRedirect(request,response,e.getMessage());
		}

		if (!showComputers.isPresent()) {
			errorRedirect(request,response,"Can't build a page with those parameters");
		}

		request.setAttribute("computerData", showComputers.get().getPageContent());
		request.setAttribute("computerPage", showComputers.get());
		request.setAttribute("numberComputer", showComputers.get().getContentSize());

		this.getServletContext()
		.getRequestDispatcher("/views/dashboard.jsp")
		.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

		computerService = ComputerService.getInstance(App.dataSource);

		String checked = request.getParameter("selection");
		if (checked != null && !checked.equals("")) {
			String[] computers = checked.split(",");
			for (String id : computers) {
				try {
					computerService.deleteComputer(id);
				} catch (ExceptionDao | ExceptionInvalidInput e) {
					errorRedirect(request,response,e.getMessage());
				}
			}
		}
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

	private void getSearch(HttpServletRequest request) {
		String search = (String) request.getAttribute("search");

		String searchParam = request.getParameter("search");

		if(searchParam != null && !searchParam.equals("")) {
			search = searchParam;
		}

		request.setAttribute("search", search);
	}

	private void getOrder(HttpServletRequest request) {
		String order = (String) request.getAttribute("order");

		String orderParam = request.getParameter("order");

		if(orderParam != null && !orderParam.equals("")) {
			order = orderParam;
		}

		request.setAttribute("order", order);
	}

	public void errorRedirect(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("Exception", message);
		this.getServletContext()
		.getRequestDispatcher("/views/500.jsp")
		.forward(request, response);
	}
}
