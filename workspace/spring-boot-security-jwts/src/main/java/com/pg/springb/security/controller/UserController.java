package com.pg.springb.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.springb.security.jwtsecurity.JwtTokenProcessor;
import com.pg.springb.security.service.User;
import com.pg.springb.security.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userSrv;
	
	@Autowired
	JwtTokenProcessor jwtPros;
	
	@PostMapping("/signin")
	public String signin(@RequestBody User user) {
		User signUser = userSrv.findUser(user);
		if(null != signUser) {
			return jwtPros.generateToken(signUser);
		}
		return null;
	}

}
