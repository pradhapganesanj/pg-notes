package com.pg.springb.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

	@GetMapping("/check")
	@PreAuthorize("hasAuthority('BASIC')")
	//@PreAuthorize("hasRole('BASIC') or hasRole('ADMIN')")
	public String checkSecure() {
		return "Yes, You got it";
	}
	
	
}
