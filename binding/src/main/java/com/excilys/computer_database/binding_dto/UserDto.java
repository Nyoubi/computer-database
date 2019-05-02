package com.excilys.computer_database.binding_dto;


public class UserDto {
	
	private String username;
	private String password;
	private short enabled;

	public UserDto(String username, String password, short enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public UserDto() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "UserDto [username=" + username + ", password=" + password + ", enabled=" + enabled + "]";
	}
	

}
