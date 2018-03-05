package com.pg.springb.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.pg.springb.security.filter.CustAuthFilter;

@EnableWebSecurity
@Configuration
public class SpringbootSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication()
		.withUser("pg").password("pg").roles("ADMIN","BASIC")
		.and()
		.withUser("gp").password("gp").roles("BASIC");
		
		//super.configure(auth);
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/api/hello").hasAnyRole("ADMIN","BASIC")
			.antMatchers("/api/home").hasRole("BASIC")
			.antMatchers("/api/restrict").hasRole("ADMIN")
			//.anyRequest()
			//.fullyAuthenticated()
			//.permitAll()
			.and()
			.addFilterBefore(cAuthFilter(), BasicAuthenticationFilter.class)
			.httpBasic();
		http.csrf().disable();		    
		// super.configure(http);
	}
	
	@Bean
	public CustAuthFilter cAuthFilter() {
		return new CustAuthFilter();
	}

}
