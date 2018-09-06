package com.pg.springb.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.pg.springb.security.jwt.provider.JwtProvider;

@Component
public class JwtFilter extends AbstractAuthenticationProcessingFilter{

	@Autowired
	private JwtProvider jwtProvider;

	public JwtFilter() {
		//super("^/api/(?!signin|signout).*$");
		super("/api/**");
		/*super(
			new RequestMatcher() {
				@Override
				public boolean matches(HttpServletRequest request) {
					//boolean match = request.getRequestURI().matches("^/api/(?!signin|signout).*$");
					boolean match = ! (request.getRequestURI().equals("/api/signin") || request.getRequestURI().equals("/api/signout"));
					return match;
				}
			});*/
	}
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
			System.out.println(" attemptAuthentication ");
		 return getAuthenticationManager().authenticate(jwtProvider.getAuthentication(req));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println(" successfulAuthentication ");
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("filter unsucessful ");
		// response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
		super.unsuccessfulAuthentication(request, response, failed);
	}
	
	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
	    super.setAuthenticationManager(authenticationManager);
	}

}
