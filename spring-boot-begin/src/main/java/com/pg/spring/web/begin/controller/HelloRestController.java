package com.pg.spring.web.begin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

	@RequestMapping("/")
	public String sayHello() {
		return "Hellooo.....";
	}

}
