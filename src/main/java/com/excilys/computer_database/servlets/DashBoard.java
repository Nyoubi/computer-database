package com.excilys.computer_database.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		getIndex(request);
		getSize(request);
		
		Integer index = (Integer) request.getAttribute("index");
		Integer size = (Integer) request.getAttribute("size");
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
	
	private void getIndex(HttpServletRequest request) {
		
		Integer index = (Integer) request.getAttribute("index");
		
		String indexParam = request.getParameter("index");
		
		if(indexParam!=null && !indexParam.equals("")) {
			index = Integer.parseInt(indexParam);
		} else if (index == null){
			index = 1;
		}
		request.setAttribute("index", index);
	}

	private void getSize(HttpServletRequest request) {
		Integer size = (Integer) request.getAttribute("size");

		String sizeParam = request.getParameter("size");
		
		if(sizeParam != null &&  !sizeParam.equals("") && !(sizeParam.equals(""+size))) {
			size = Integer.parseInt(sizeParam);
			request.setAttribute("index", 0);
		} else if (size == null){
			size = 10;
		}
		request.setAttribute("size", size);
	}
}
