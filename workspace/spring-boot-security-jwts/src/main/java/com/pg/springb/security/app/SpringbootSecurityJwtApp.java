package com.pg.springb.security.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages= {"com.pg.springb.security"})
public class SpringbootSecurityJwtApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityJwtApp.class, args);
	}

}
