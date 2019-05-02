package com.excilys.computer_database.validation;

import com.excilys.computer_database.binding_dto.UserDto;
import com.excilys.computer_database.binding_exception.DaoException;
import com.excilys.computer_database.binding_exception.ValidationException;
import com.excilys.computer_database.mapper.UserMapper;
import com.excilys.computer_database.model.User;
import com.excilys.computer_database.service.UserService;

public class DtoUserValidation {
	
	public static User checkDataUser(UserDto userDto, UserService userService) throws DaoException, ValidationException {

		checkUsername(userDto.getUsername(), userService);
		checkPassword(userDto.getPassword());
		
		return UserMapper.userDtoToUser(userDto).get();
	}
	
	private static void checkUsername(String username, UserService userService) throws ValidationException, DaoException {
		if (userService.findUserByName(username).isPresent()) {
			throw new ValidationException("Username already used");
		}
	}
	
	private static void checkPassword(String password) throws ValidationException {
		if (password.length() < 8) {
			throw new ValidationException("Password must be at least 8 characters long");
		}
	}
}
