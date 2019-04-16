package com.excilys.computer_database.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computer_database.dto.CompanyDto;
import com.excilys.computer_database.dto.ComputerDto;
import com.excilys.computer_database.exception.DaoException;
import com.excilys.computer_database.exception.InvalidInputException;
import com.excilys.computer_database.exception.ModelException;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

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
	protected String postEditcomputer(Model model , @RequestParam(required = false) Map<String, String> param) {
		logger.info("postEditcomputer has been called");

		String id, name, introduced, discontinued, companyId;

		id = param.get("idComputer");
		name = param.get("name");
		introduced = param.get("introduced");
		discontinued = param.get("discontinued");
		companyId = param.get("companyId");
		
		try {
			computerService.updateComputer(Integer.valueOf(id), name, introduced, discontinued, Integer.valueOf(companyId));
			return VIEW_LIST_COMPUTERS;
		} catch (DaoException | ModelException e) {
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
			return VIEW_ERROR_500;
		}
		return VIEW_EDIT_COMPUTER;		
	}
}
