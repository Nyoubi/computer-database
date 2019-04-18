package com.excilys.computer_database.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.ModelException;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

import validation.DtoComputerValidation;

@Controller
public class AddComputerController {

	static final String VIEW_ERROR_500 = "500";
	static final String VIEW_LIST_COMPUTERS = "redirect:dashboard";
	static final String VIEW_ADD_COMPUTERS = "addComputer";
	
	private final Logger logger = LoggerFactory.getLogger(DashBoardController.class);

	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	
	@GetMapping({ "addComputer", "/addcomputer", "/AddComputer", "/Addcomputer" })
	public String getAddComputer(Model model) {
		logger.info("getAddComputer has been called");

		ArrayList<CompanyDto> listCompanies = new ArrayList<>();
		
		try {
			
			listCompanies = companyService.listCompanies();
			model.addAttribute("listCompanies", listCompanies);
			model.addAttribute("computer" , new ComputerDto());
			return VIEW_ADD_COMPUTERS;
			
		} catch (DaoException e) {
			return VIEW_ERROR_500;
		}	
	}

	@PostMapping({ "/addComputer", "/addcomputer", "/AddComputer", "/Addcomputer" })
	public String postAddComputer(@Validated @ModelAttribute("computer")ComputerDto dtoComputer, 
		      BindingResult result, Model model) {
		logger.info("postAddComputer has been called");

		if (result.hasErrors()) {
            return VIEW_ERROR_500;
        }
		
		try {
			Computer computer = DtoComputerValidation.checkDataComputer(dtoComputer, this.companyService, false);
			this.computerService.createComputer(computer);
			return VIEW_LIST_COMPUTERS;
		} catch (DaoException | ModelException e) {
			return VIEW_ERROR_500;
		}
	}
}
