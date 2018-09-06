package com.pg.springb.security.jwtsecurity;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	JwtTokenProcessor tokenPros;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProcessor tokenProcessor) {
		super(authenticationManager);
		this.tokenPros = tokenProcessor;
	}

	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		System.out.println(".. doFilterInternal");
		Authentication auth = this.tokenPros.getAuthentication(req);
		if(null != auth) {
			System.out.println("got Auth " + auth);
	        SecurityContextHolder.getContext().setAuthentication(auth);
		}
		System.out.println(".. doFilterInternal leaving");
		chain.doFilter(req, res);
	}
}
