package com.excilys.computer_database.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.InvalidInputException;
import com.excilys.computer_database.exception.ValidationException;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

import validation.DtoComputerValidation;

@Controller
public class EditComputerController {

	static final String VIEW_EDIT_COMPUTER = "editComputer";
	static final String VIEW_ERROR_500 = "500";
	static final String VIEW_LIST_COMPUTERS = "redirect:dashboard";
	
	private final Logger logger = LoggerFactory.getLogger(DashBoardController.class);

	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@PostMapping({ "/editComputer", "/editcomputer", "/Editcomputer", "/EditComputer" })
	protected String postEditcomputer(@Validated @ModelAttribute("computer")ComputerDto dtoComputer, 
		      BindingResult result, Model model) {
		logger.info("postEditcomputer has been called");
		if (setStackTrace(model, result)) {
			return VIEW_EDIT_COMPUTER + "?id=" + dtoComputer.getId();
		}
		try {
			Computer computer = DtoComputerValidation.checkDataComputer(dtoComputer, this.companyService, true);
			computerService.updateComputer(computer);
			return VIEW_LIST_COMPUTERS;
		} catch (DaoException | ValidationException e) {
			model.addAttribute("stackTrace", e.getMessage());
			return VIEW_ERROR_500;
		}
	}
	
	@GetMapping({ "/editComputer", "/editcomputer", "/Editcomputer", "/EditComputer" })
	protected String getEditComputer(Model model , @RequestParam(required = false) Map<String, String> param) {
		logger.info("getEditComputer has been called");

		Optional<ComputerDto> computer = Optional.empty();		
		ArrayList<CompanyDto> listCompanies = new ArrayList<>();
		
		try {
			listCompanies = companyService.listCompanies();
			model.addAttribute("listCompanies", listCompanies);
			computer = computerService.showDetails(param.get("id"));

			if (computer.isPresent()) {
				model.addAttribute("computer", computer.get());
			} else {
				return VIEW_LIST_COMPUTERS;
			}
		} catch (DaoException | InvalidInputException e) {
			model.addAttribute("stackTrace", e.getMessage());
			return VIEW_ERROR_500;
		}
		return VIEW_EDIT_COMPUTER;		
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new DtoComputerValidation());
	}
	
	private boolean setStackTrace(Model model, BindingResult res) {
		if (res.hasErrors()) {
			StringBuilder stackTrace = new StringBuilder();
			List<ObjectError> errors = res.getAllErrors();
			errors.forEach(stackTrace::append);
			model.addAttribute("stackTrace", stackTrace);
			return true;
		}
		return false;
	}
}
