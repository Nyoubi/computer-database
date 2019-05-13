package com.excilys.computer_database.mapper;

import java.util.Optional;

import com.excilys.computer_database.binding_dto.UserDto;
import com.excilys.computer_database.binding_dto.UserDtoBuilder;
import com.excilys.computer_database.model.User;
import com.excilys.computer_database.model.UserBuilder;

public class UserMapper {

	//Private constructor
	private UserMapper () {}
	
	public static Optional<UserDto> userToDtoUser(User user){
		Optional<UserDto> userDto = Optional.empty();
		if (user != null) {
			UserDtoBuilder userDtoBuilder = new UserDtoBuilder();
			userDto = Optional.of(
					userDtoBuilder.setUsername(user.getUsername())
								  .setPassword(user.getPassword())
								  .setEnabled(user.getEnabled())
								  .build());
		}	
		return userDto;
	}

	public static Optional<User> userDtoToUser(UserDto userDto){
		
		if(userDto == null) {
			return Optional.empty();
		} else {
		UserBuilder userBuilder = new UserBuilder();
		User user = userBuilder.setUsername(userDto.getUsername())
							   .setPassword(userDto.getPassword())
							   .setEnabled(userDto.getEnabled() == 0 ? 1 : userDto.getEnabled())
							   .build();
		return Optional.of(user);
		}
	}
}
