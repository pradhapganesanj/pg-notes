package com.pg.springb.security.jwtsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter{
	
	/*@Autowired
	JwtAuthenticationProvider jwtAuthProvider;*/
	
	/*@Autowired
	JwtUserServices jwtUserServices;*/
	
	@Autowired
	JwtTokenProcessor tokenProcessor;
	
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.authenticationProvider(jwtAuthProvider);
		//UserDetailsService userDetailsService = (uname) -> this.retrieveUserDetails(uname);
		auth.userDetailsService(jwtUserServices);
	}*/

	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	    		.antMatchers("/api/signin", "/api/signout");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests()
				.antMatchers("/api/**").authenticated()
				.and()
        //.addFilter(authFilter());
		.addFilterBefore(authGenFilter(), UsernamePasswordAuthenticationFilter.class);
	
	}

	private JwtAuthorizationFilter authFilter() throws Exception {
		AuthenticationManager authMgr = authenticationManager();
		JwtAuthorizationFilter jwtFilter =  new JwtAuthorizationFilter(authenticationManager(), tokenProcessor);
		return jwtFilter;
	}
	
	private JwtAuthorizationGenFilter authGenFilter() throws Exception {
		//AuthenticationManager authMgr = authenticationManager();
		JwtAuthorizationGenFilter jwtGenFilter =  new JwtAuthorizationGenFilter(tokenProcessor);
		return jwtGenFilter;
	}
	/*
	private UserDetails retrieveUserDetails(String userName) {
		User user = userService.findUserByName(userName);

		if(null != user) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .createAuthorityList(user.getRoles().stream().toArray(String[]::new));
        
	    return org.springframework.security.core.userdetails.User
	        .withUsername(userName)
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
	*/
}
