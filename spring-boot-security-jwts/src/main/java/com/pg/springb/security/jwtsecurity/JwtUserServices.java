package com.pg.springb.security.jwtsecurity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pg.springb.security.service.User;
import com.pg.springb.security.service.UserService;

@Service
public class JwtUserServices implements UserDetailsService{
  
	@Autowired
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userService.findUserByName(username);

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
