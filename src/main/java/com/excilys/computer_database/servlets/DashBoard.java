package com.excilys.computer_database.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computer_database.controller.Controler;

@WebServlet(name = "DashBoard", urlPatterns = { "/WebContent/views/DashBoard.jsp" })
public class DashBoard extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controler controler;
	private List<Computer> list;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controler = ControlerComputer.getInstance();
		
		list = controler.getAll();
		request.setAttribute(LIST_COMP_PARAM, list);
		
		extractPageSize(request);
		extractPageIndex(request);
		
		this.getServletContext().getRequestDispatcher(LIST_COMP_VIEW).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
