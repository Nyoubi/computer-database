package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.controller.Controler;
import com.excilys.computer_database.dto.DtoComputer;
import com.excilys.computer_database.exception.ExceptionDao;
import com.excilys.computer_database.exception.ExceptionModel;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.persistence.DaoCompany;

@WebServlet(name = "DashBoard", urlPatterns = { "/dashboard" })
public class DashBoard extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controler controler;
	private static Logger logger = LoggerFactory.getLogger(DaoCompany.class);

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controler = Controler.getInstance();

		Integer index = getIndex(request), size = getSize(request);
		Optional<Page<DtoComputer>> showComputers = Optional.empty();
		Optional<Integer> computerCount = Optional.empty();

		try {
			computerCount = controler.getNbComputer();
			showComputers = controler.getPageListDtoComputer(index, size);
		} catch (ExceptionDao e) {
			logger.error(e.errorMessage);
		} catch (ExceptionModel e1) {
			logger.error(e1.errorMessage);
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
	
	private Integer getIndex(HttpServletRequest request) {
		Integer index = 1;
		Object indexAttribute = request.getAttribute("index");
		if (indexAttribute != null) {
			index = (Integer) indexAttribute;
		}
		if(request.getParameter("index")!=null) {
			index = Integer.parseInt(request.getParameter("index"));
		}
		request.setAttribute("index", index);
		return index;
	}

	private Integer getSize(HttpServletRequest request) {
		Integer size = 10;
		Object sizeAttribute = request.getAttribute("size");
		if (sizeAttribute != null) {
			size = (Integer) sizeAttribute;
		}
		if(request.getParameter("size")!=null) {
			size = Integer.parseInt(request.getParameter("size"));
		}
		request.setAttribute("size", size);
		return size;
	}
}
