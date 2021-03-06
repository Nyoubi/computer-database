package com.excilys.computer_database.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computer_database.binding_dto.ComputerDto;
import com.excilys.computer_database.binding_exception.ValidationException;
import com.excilys.computer_database.model.Page;
import com.excilys.computer_database.service.ComputerService;

@Controller
@RequestMapping(value = "/computer")
public class DashBoardController {
	
	static final String VIEW_EDIT_COMPUTER = "editComputer";
	static final String VIEW_ERROR_500 = "500";
	static final String VIEW_LIST_COMPUTERS = "dashboard";
	static final String VIEW_ADD_COMPUTERS = "addComputer";

	private final Logger logger = LoggerFactory.getLogger(DashBoardController.class);

	@Autowired
	private ComputerService computerService;
	
	@GetMapping("/dashboard")
	public String getDashboard(Model model,
			@RequestParam(name = "index", required = false, defaultValue = "1") String index,
			@RequestParam(name = "size", required = false, defaultValue = "10") String size,
			@RequestParam(name = "search", required = false, defaultValue = "") String search,
			@RequestParam(name = "order", required = false, defaultValue = "") String order) {
		logger.info("getDashboard has been called");
		Page<ComputerDto> showComputers = null;
		try {
			showComputers = computerService.pageDtoComputer(VIEW_LIST_COMPUTERS, index, size, search, order);

		} catch ( ValidationException e) {
			model.addAttribute("stackTrace", e.getMessage());
			return VIEW_ERROR_500;
		}
		model.addAttribute("index", index);
		model.addAttribute("size", size);
		model.addAttribute("search", search);
		model.addAttribute("order", order);
		model.addAttribute("computerPage", showComputers);
		model.addAttribute("computerData", showComputers.getContent());
		model.addAttribute("numberComputer", showComputers.getTotalSize());
		return VIEW_LIST_COMPUTERS;
	}
}