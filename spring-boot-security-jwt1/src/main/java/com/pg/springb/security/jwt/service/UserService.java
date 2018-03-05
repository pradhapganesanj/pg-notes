package com.pg.springb.security.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pg.springb.security.jwt.model.JwtUser;
import com.pg.springb.security.jwt.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	UserRepository userRepo;

	public JwtUser signIn(JwtUser user) {
		return userRepo.findByUsername(user.getUserName());
	}
  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		JwtUser user = userRepo.findByUsername(username);
		
	    if (user == null) {
	        throw new UsernameNotFoundException("User '" + username + "' not found"); }

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .createAuthorityList(user.getRoles().stream().toArray(String[]::new));
        
	    return org.springframework.security.core.userdetails.User
	        .withUsername(username)
	        //.password(user.getPassword())
	        .authorities(grantedAuthorities)
	        .accountExpired(false)
	        .accountLocked(false)
	        .credentialsExpired(false)
	        .disabled(false)
	        .build();
	}
	
}
