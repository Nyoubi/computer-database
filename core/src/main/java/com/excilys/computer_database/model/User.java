package com.excilys.computer_database.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@Basic(optional = false)
	@Column(nullable = false)
	private String username;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private String password;
	
	private Short enabled;

	public User(String username, String password, Short enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	protected User() {}

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

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", enabled=" + enabled + "]";
	}

}
