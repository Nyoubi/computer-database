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
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

@Controller
public class ComputerController {
	
	static final String VIEW_EDIT_COMPUTER = "editComputer";
	static final String VIEW_ERROR_500 = "500";
	static final String VIEW_LIST_COMPUTERS = "dashboard";
	static final String VIEW_ADD_COMPUTERS = "addComputer";
	
	private final Logger logger = LoggerFactory.getLogger(ComputerController.class);

	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	
	@GetMapping({ "/", "/dashboard", "/dashBoard" })
	public String getDashboard(Model model,
			@RequestParam(name = "index", required = false, defaultValue = "1") String index,
			@RequestParam(name = "size", required = false, defaultValue = "10") String size,
			@RequestParam(name = "search", required = false, defaultValue = "") String search,
			@RequestParam(name = "order", required = false, defaultValue = "") String order) {
		logger.info("getDashboard has been called");
		Page<ComputerDto> showComputers = null;
		try {
			showComputers = computerService.pageDtoComputer(VIEW_LIST_COMPUTERS, index, size, search, order);

		} catch (DaoException | ModelException e) {
			return VIEW_ERROR_500;
		}

		model.addAttribute("index", index);
		model.addAttribute("size", size);
		model.addAttribute("search", search);
		model.addAttribute("order", order);
		model.addAttribute("computerPage", showComputers);
		model.addAttribute("computerData", showComputers.getPageContent());
		model.addAttribute("numberComputer", showComputers.getContent().size());

		return VIEW_LIST_COMPUTERS;
	}
	
	@PostMapping({ "/", "/dashboard", "/dashBoard" })
	public String postDashboard(Model model,
			@RequestParam(name = "selection", required = false, defaultValue = "") String selection) {
		logger.info("postDashboard has been called");

		if (!"".equals(selection)) {
			String[] computers = selection.split(",");
			for (String id : computers) {
				try {
					computerService.deleteComputer(id);
				} catch (DaoException | InvalidInputException e) {
					return VIEW_ERROR_500;
				}
			}
		}
		return VIEW_LIST_COMPUTERS;
	}
	
	@GetMapping({ "addComputer", "/addcomputer", "/AddComputer", "/Addcomputer" })
	public String getAddComputer(Model model) {
		logger.info("getAddComputer has been called");

		ArrayList<CompanyDto> listCompanies = new ArrayList<>();
		
		try {
			
			listCompanies = companyService.listCompanies();
			model.addAttribute("listCompanies", listCompanies);
			return VIEW_ADD_COMPUTERS;
			
		} catch (DaoException e) {
			return VIEW_ERROR_500;
		}	
	}

	@PostMapping({ "/addComputer", "/addcomputer", "/AddComputer", "/Addcomputer" })
	public String postAddComputer(Model model, @RequestParam(required = false) Map<String, String> param) {
		logger.info("postAddComputer has been called");

		String name, introduced, discontinued, companyId;

		name = param.get("name");
		introduced = param.get("introduced");
		discontinued = param.get("discontinued");
		companyId = param.get("companyId");
		
		try {
			this.computerService.createComputer(null, name, introduced, discontinued, Integer.valueOf(companyId));
			return VIEW_LIST_COMPUTERS;
		} catch (DaoException | ModelException e) {
			return VIEW_ERROR_500;
		}
	}
	
	@PostMapping({ "/editComputer", "/editcomputer", "/Editcomputer", "/EditComputer" })
	protected String postEditcomputer(Model model , @RequestParam(required = false) Map<String, String> param) {
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