package com.pg.sboot.data.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	@Autowired
	PersonService personSrv;

	@Autowired
	CacheManager manager;
	
	@GetMapping("/persons")
	public List<Person> findAll() {
		return (List<Person>) personSrv.findAll();
	}
	
	@GetMapping("/persons/{lname}")
	public List<Person> findByLName(@PathVariable("lname") String lName) {
		return (List<Person>) personSrv.findByFName(lName);
	}

	@GetMapping("/persons/id/{id}")
	public Person findById(@PathVariable("id") String id) {
		return personSrv.findById(id);
	}
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello....";
	}

}