package com.pg.springb.security.jwtsecurity;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthorizationGenFilter extends GenericFilterBean {

	JwtTokenProcessor tokenPros;
	
	public JwtAuthorizationGenFilter(JwtTokenProcessor tokenProcessor) {
		this.tokenPros = tokenProcessor;
	}

	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		System.out.println(".. doFilterInternal");
		Authentication auth = this.tokenPros.getAuthentication((HttpServletRequest)req);
		if(null != auth) {
			System.out.println("got Auth " + auth);
	        SecurityContextHolder.getContext().setAuthentication(auth);
		}
		System.out.println(".. doFilterInternal leaving");
		chain.doFilter(req, res);
	}
}
