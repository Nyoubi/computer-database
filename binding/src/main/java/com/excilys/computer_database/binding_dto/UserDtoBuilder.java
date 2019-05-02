package com.excilys.computer_database.binding_dto;

public class UserDtoBuilder {
	private String username;
	private String password;
	private short enabled;

	public UserDto build() {
		UserDto user = new UserDto();
		user.setUsername(this.username);
		user.setPassword(this.password);
		
		if (this.enabled == 0) {
			user.setEnabled((short) 1);
		}
		else {
			user.setEnabled(enabled);
		}
		return user;
	}
	
	public UserDtoBuilder setUsername(String username) {
		this.username = username;
		return this;
	}	
	
	public UserDtoBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public UserDtoBuilder setEnabled(short enabled) {
		this.enabled = enabled;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public short getEnabled() {
		return enabled;
	}
}
