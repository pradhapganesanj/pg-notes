package com.pg.springb.security.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pg.springb.security.service.User;


@Component
public class UserRepository {

	static List<User> userList = new ArrayList<User>();
	static {
		userList.add(new User ("pg","pg", Arrays.asList(new String[]{"BASIC"})));
		userList.add(new User ("gp","gp", Arrays.asList(new String[]{"BASIC"})));
		userList.add(new User ("admin","admin", Arrays.asList(new String[]{"ADMIN","BASIC"})));
	}
	public User findUserByCred(String userName, String password) {
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) return null;
		Optional<User> optUser = userList.stream()
											.filter(user -> userName.equalsIgnoreCase(user.getUserName()) && password.equals(user.getPassword()))
											.findFirst();
		return optUser.isPresent() ? optUser.get() : null;
	}
	
	public User findByUsername(String userName) {
		if(StringUtils.isEmpty(userName)) return null;
		Optional<User> optUser = userList.stream().filter(user -> userName.equalsIgnoreCase(user.getUserName())).findFirst();
		return optUser.isPresent() ? optUser.get() : null;
	}
}
