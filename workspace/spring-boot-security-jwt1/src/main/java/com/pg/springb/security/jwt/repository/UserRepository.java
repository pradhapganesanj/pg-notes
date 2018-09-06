package com.pg.springb.security.jwt.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pg.springb.security.jwt.model.JwtUser;

@Component
public class UserRepository {

	static List<JwtUser> userList = new ArrayList<JwtUser>();
	static {
		userList.add(new JwtUser ("pg","pg", Arrays.asList(new String[]{"BASIC"})));
		userList.add(new JwtUser ("gp","gp", Arrays.asList(new String[]{"BASIC"})));
		userList.add(new JwtUser ("admin","admin", Arrays.asList(new String[]{"ADMIN","BASIC"})));
	}
	
	public JwtUser findByUsername(String userName) {
		if(StringUtils.isEmpty(userName)) return null;
		Optional<JwtUser> optUser = userList.stream().filter(user -> userName.equalsIgnoreCase(user.getUserName())).findFirst();
		return optUser.isPresent() ? optUser.get() : null;
	}
}
