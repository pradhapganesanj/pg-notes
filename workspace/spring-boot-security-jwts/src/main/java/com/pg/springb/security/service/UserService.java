package com.pg.springb.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pg.springb.security.repository.UserRepository;

@Service
public class UserService{

	@Autowired
	UserRepository userRepo;

	public User findUser(User user) {
		return userRepo.findUserByCred(user.getUserName(), user.getPassword());
	}
	
	public User findUserByName(String userName) {
		System.out.println(" findUserByName  " + userName);
		return userRepo.findByUsername(userName);
	}
 }
