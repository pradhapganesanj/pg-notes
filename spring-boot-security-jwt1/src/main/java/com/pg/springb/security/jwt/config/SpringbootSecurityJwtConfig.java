package com.pg.springb.security.jwt.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pg.springb.security.jwt.filter.JwtFilter;
import com.pg.springb.security.jwt.provider.JwtProvider;

@Configuration
public class SpringbootSecurityJwtConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
    	List<AuthenticationProvider> providers = new ArrayList<>();
    	providers.add(jwtProvider);
    	AuthenticationManager authenticationManager = new ProviderManager(providers);
        return authenticationManager;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests()
			.antMatchers("/signin").permitAll()
			.antMatchers("/signout").permitAll()
			.antMatchers("/api/**").authenticated()
			//.regexMatchers("^/api/(?!signin|signout).*$").authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(this::handleUnauth);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	private void handleUnauth(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		System.out.println("handleUnauth exception auth : ");
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
	}
	
}
