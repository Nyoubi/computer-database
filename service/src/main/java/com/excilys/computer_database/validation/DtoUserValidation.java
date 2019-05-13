package com.excilys.computer_database.validation;

import java.util.Optional;

import com.excilys.computer_database.binding_dto.UserDto;
import com.excilys.computer_database.binding_exception.DaoException;
import com.excilys.computer_database.binding_exception.ValidationException;
import com.excilys.computer_database.mapper.UserMapper;
import com.excilys.computer_database.model.User;
import com.excilys.computer_database.service.UserService;

public abstract class DtoUserValidation {
	
	//Private constructor
	private DtoUserValidation () {}
	
	public static User checkDataUser(UserDto userDto, UserService userService) throws DaoException, ValidationException {

		checkUsername(userDto.getUsername(), userService);
		checkPassword(userDto.getPassword());
		
		Optional<User> user = UserMapper.userDtoToUser(userDto);
		
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new ValidationException("Error with userMapper");
		}
	}
	
	private static void checkUsername(String username, UserService userService) throws ValidationException, DaoException {
		if (userService.findUserByName(username).isPresent()) {
			throw new ValidationException("userValidation.username");
		}
		if ("".equals(username) || "".equals(username.trim())){
			throw new ValidationException("userValidation.empty");
		}
	}
	
	private static void checkPassword(String password) throws ValidationException {
		if (password.length() < 8) {
			throw new ValidationException("userValidation.password");
		}
	}
}
