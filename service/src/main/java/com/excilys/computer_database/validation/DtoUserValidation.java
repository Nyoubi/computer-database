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
		
		return UserMapper.userDtoToUser(userDto).get();
	}
	
	private static void checkUsername(String username, UserService userService) throws ValidationException, DaoException {
		if (userService.findUserByName(username).isPresent()) {
			throw new ValidationException("Username already taken");
		}
	}

}
