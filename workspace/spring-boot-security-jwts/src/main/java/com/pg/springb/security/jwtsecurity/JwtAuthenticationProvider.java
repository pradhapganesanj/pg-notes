package com.pg.springb.security.jwtsecurity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.pg.springb.security.service.User;
import com.pg.springb.security.service.UserService;

// @Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	UserService userService;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetailsService usServ = (un) -> this.findUser(un);
		UserDetails userDetails = usServ.loadUserByUsername(username);
		return userDetails;
	}

	private UserDetails findUser(String username) {
		User user = userService.findUserByName(username);

		if(null != user) {
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
		return null;
	}

}
