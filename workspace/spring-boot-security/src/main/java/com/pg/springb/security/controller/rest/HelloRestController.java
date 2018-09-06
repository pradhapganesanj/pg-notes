package com.pg.springb.security.controller.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloRestController {
	
	@GetMapping
	@RequestMapping("/hello")
	public String sayHello(@AuthenticationPrincipal final UserDetails user) {
		return "Hello...."+user.getUsername();
	}

	@GetMapping
	@RequestMapping("/home")
	public String welcome(@AuthenticationPrincipal final UserDetails user) {
		return "Welcome to Security Home...."+user.getUsername();
	}
	
	@GetMapping
	@RequestMapping("/restrict")
	public String restrict(@AuthenticationPrincipal final UserDetails user) {
		return "Restricted to only Admin User: "+user.getUsername();
	}
}
