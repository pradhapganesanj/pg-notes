package com.pg.springb.security.jwt.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JwtUser {

	private String userName;
	private String password;
	private List<String> roles = new ArrayList<>();
	
	public JwtUser() {
	}
	
	public JwtUser(String name, String pass, List<String>roleList) {
		this.userName = name;
		this.password = pass;
		this.roles = roleList;
	}
	
	public String getPassword() {
		return password;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String toString() {
		return String.format("name: %s, roles: %s", this.getUserName(), Arrays.toString(this.getRoles().toArray()));
	}
}
