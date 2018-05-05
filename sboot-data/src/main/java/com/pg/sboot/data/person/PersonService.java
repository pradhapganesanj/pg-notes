package com.pg.sboot.data.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepo;
	
	@Cacheable(value="personCache")
	public List<Person> findAll(){
		List<Person> persList = new ArrayList<Person>();
		for(Person p : personRepo.findAll() ){
			System.out.println("P " + p.toString());
			persList.add(p);
		}
		return persList;
	}

	@Cacheable
	public List<Person> findByFName(String lName){
		List<Person> persList = new ArrayList<Person>();
		for(Person p : personRepo.findByLName(lName)){
			System.out.println("P " + p.toString());
			persList.add(p);
		}
		return persList;
	}
	
	@CachePut(cacheNames="personCache", key="#id")
	public Person findById(String id) {
		
		
		
		Optional<Person> persOpt = personRepo.findById(new Long(id));
		System.out.println("CAches not present. So going to db");
		if(persOpt.isPresent()) return persOpt.get();
		return null;
	}
}
