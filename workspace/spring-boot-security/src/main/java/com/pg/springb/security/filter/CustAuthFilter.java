package com.pg.springb.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class CustAuthFilter implements Filter{

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("init filter");		
	}


	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("doFilter filter");
		HttpServletRequest hreq = (HttpServletRequest) req;
		
		System.out.println("doFilter filter "+hreq);
	}
	
	@Override
	public void destroy() {
		System.out.println("destroy...");		
	}

}
