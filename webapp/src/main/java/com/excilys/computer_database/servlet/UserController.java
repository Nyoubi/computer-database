package com.excilys.computer_database.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.excilys.computer_database.binding_dto.UserDto;
import com.excilys.computer_database.binding_exception.DaoException;
import com.excilys.computer_database.binding_exception.ValidationException;
import com.excilys.computer_database.model.User;
import com.excilys.computer_database.service.UserService;
import com.excilys.computer_database.validation.DtoUserValidation;

@Controller
public class UserController {
	
	static final String LOGIN = "redirect:login?created=true";
	static final String VIEW_CREATE = "create";

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@PostMapping("/createAccount")
	protected String postCreate(@ModelAttribute("user")UserDto userDto, BindingResult result, Model model) {
		logger.info("postCreate has been called");
		try {
			User user = DtoUserValidation.checkDataUser(userDto, userService);
			userService.createUser(user);
			return LOGIN;
		} catch (DaoException | ValidationException e) {
			model.addAttribute("error",true);
			return VIEW_CREATE;
		}
	}
	
	@GetMapping("/create")
	public String getCreateAccount(Model model) {
		logger.info("getCreate has been called");
		model.addAttribute("user" , new UserDto());
		return VIEW_CREATE;	 
	}

}