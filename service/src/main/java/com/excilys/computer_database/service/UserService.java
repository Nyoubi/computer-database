package com.excilys.computer_database.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.computer_database.binding_dto.UserDto;
import com.excilys.computer_database.binding_exception.DaoException;
import com.excilys.computer_database.dao.DaoUser;
import com.excilys.computer_database.mapper.UserMapper;
import com.excilys.computer_database.model.User;



@Service
public class UserService {
	
	@Autowired
	private DaoUser daoUser;
	
	public Optional<UserDto> findUserByName(String name) throws DaoException {
		Optional<User> user;
		try {
			user = daoUser.findByUsername(name);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
		return user.isPresent() ? UserMapper.userToDtoUser(user.get()) : Optional.empty();
	}
	
	public User createUser(User user) throws DaoException {
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String coded = encoder.encode(user.getPassword());
			user.setPassword(coded);
			User userCreated = daoUser.save(user);
			
			daoUser.saveRole(userCreated.getUsername());

			return userCreated;
		} catch (DataAccessException e) {
			throw new DaoException("daoException.insertUser");
		}
	}
}
