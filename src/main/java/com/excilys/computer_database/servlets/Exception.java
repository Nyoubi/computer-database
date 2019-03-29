package com.excilys.computer_database.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Exception", urlPatterns = { "/exception" })
public class Exception extends HttpServlet{

	private static final long serialVersionUID = 5607418336683896307L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		String message = request.getParameter("Exception");
		
		if (message == null) {
			response.sendRedirect("");
		} else {
			request.setAttribute("exception", message);
			this.getServletContext()
			.getRequestDispatcher("/views/500.html")
			.forward(request, response);
		}
		}
	}
