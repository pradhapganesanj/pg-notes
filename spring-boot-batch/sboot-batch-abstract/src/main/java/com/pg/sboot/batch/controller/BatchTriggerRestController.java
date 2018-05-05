package com.pg.sboot.batch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchTriggerRestController {

	@GetMapping(path="/hello")
	public String hello(@RequestParam(value="n", required=false) String name){
		return "Hellooo..."+null!=name?name:"";
	}
	
}
