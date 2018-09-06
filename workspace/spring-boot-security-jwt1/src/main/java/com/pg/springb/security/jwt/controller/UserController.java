package com.pg.springb.security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.springb.security.jwt.model.JwtUser;
import com.pg.springb.security.jwt.provider.JwtProvider;
import com.pg.springb.security.jwt.service.UserService;

@RestController
//@RequestMapping("/")
public class UserController {
	
	@Autowired
	UserService userSrv;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping("/signin")
	public String signin(@RequestBody JwtUser user) {
		JwtUser signUser = userSrv.signIn(user);
		if(null != signUser) {
			return jwtProvider.generateToken(signUser);
		}
		return null;
	}

}
