package com.excilys.computer_database.servlet;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computer_database.binding_dto.CompanyDto;
import com.excilys.computer_database.binding_dto.ComputerDto;
import com.excilys.computer_database.binding_exception.DaoException;
import com.excilys.computer_database.binding_exception.InvalidInputException;
import com.excilys.computer_database.binding_exception.ValidationException;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.validation.DtoComputerValidation;

@Controller
@RequestMapping(value = "/computer")
public class ComputerController {
	
	static final String VIEW_EDIT_COMPUTER = "editComputer";
	static final String VIEW_ERROR_500 = "500";
	static final String VIEW_LIST_COMPUTERS = "dashboard";
	static final String VIEW_ADD_COMPUTERS = "addComputer";
	static final String VIEW_LIST_COMPUTERS_REDIRECT = "redirect:dashboard";

	static final String STACK = "stacktrace";
	
	private final Logger logger = LoggerFactory.getLogger(ComputerController.class);

	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping("/addComputer")
	public String getAddComputer(Model model) {
		logger.info("getAddComputer has been called");

		List<CompanyDto> companies;
		
		companies = companyService.listCompanies();
		model.addAttribute("listCompanies", companies);
		model.addAttribute("computer" , new ComputerDto());
		return VIEW_ADD_COMPUTERS;	
	}

	@PostMapping("/addComputer")
	public String postAddComputer(@Validated @ModelAttribute("computer")ComputerDto dtoComputer, 
		      BindingResult result, Model model) {
		logger.info("postAddComputer has been called");
		if (setStackTrace(model, result)) {
			model.addAttribute(STACK, "dtoComputerValidation.validatorError");
			return VIEW_ADD_COMPUTERS;
		}
		
		try {
			Computer computer = DtoComputerValidation.checkDataComputer(dtoComputer, this.companyService, false);
			computerService.createComputer(computer);

			return VIEW_LIST_COMPUTERS_REDIRECT;
		} catch (DaoException | ValidationException e) {
			model.addAttribute(STACK, e.getMessage());
			return VIEW_ERROR_500;
		}
	}
	
	@PostMapping("/editComputer")
	public String postEditcomputer(@Validated @ModelAttribute("computer")ComputerDto dtoComputer, 
		      BindingResult result, Model model) {
		logger.info("postEditcomputer has been called");
		if (setStackTrace(model, result)) {
			return VIEW_EDIT_COMPUTER + "?id=" + dtoComputer.getId();
		}
		try {
			Computer computer = DtoComputerValidation.checkDataComputer(dtoComputer, this.companyService, true);
			computerService.updateComputer(computer);
			return VIEW_LIST_COMPUTERS_REDIRECT;
		} catch (DaoException | ValidationException e) {
			model.addAttribute(STACK, e.getMessage());
			return VIEW_ERROR_500;
		}
	}
	
	@GetMapping("/editComputer")
	public String getEditComputer(Model model , @RequestParam(required = false) Map<String, String> param) {
		logger.info("getEditComputer has been called");

		Optional<ComputerDto> computer = Optional.empty();		
		List<CompanyDto> companies;
		
		try {
			companies = companyService.listCompanies();
			model.addAttribute("listCompanies", companies);
			computer = computerService.showDetails(param.get("id"));

			if (computer.isPresent()) {
				model.addAttribute("computer", computer.get());
			} else {
				return VIEW_LIST_COMPUTERS_REDIRECT;
			}
		} catch (DaoException | InvalidInputException e) {
			model.addAttribute(STACK, e.getMessage());
			return VIEW_ERROR_500;
		}
		return VIEW_EDIT_COMPUTER;		
	}
	
	@PostMapping("/deleteComputer")
	public String deleteDashboard(Model model,
			@RequestParam(name = "selection", required = false, defaultValue = "") String selection) {
		logger.info("deleteDashboard has been called");

		if (!"".equals(selection)) {
			String[] computers = selection.split(",");
			for (String id : computers) {
				try {
					computerService.deleteComputer(id);
				} catch (InvalidInputException e) {
					model.addAttribute(STACK, e.getMessage());
					return VIEW_ERROR_500;
				}
			}
		} 
		return "redirect:"+VIEW_LIST_COMPUTERS;
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
			model.addAttribute("log", stackTrace);
			return true;
		}
		return false;
	}
}