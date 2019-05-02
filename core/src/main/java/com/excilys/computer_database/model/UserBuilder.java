package com.excilys.computer_database.model;

public class UserBuilder {
	private String username;
	private String password;
	private Short enabled;

	public User build() {
		User user = new User();
		user.setUsername(this.username);
		user.setPassword(this.password);
		user.setEnabled(this.enabled);
		return user;
	}
	
	public UserBuilder setUsername(String username) {
		this.username = username;
		return this;
	}	
	
	public UserBuilder setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public UserBuilder setEnabled(Short enabled) {
		this.enabled = enabled;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Short getEnabled() {
		return enabled;
	}

}
