package com.pg.springb.security.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.pg.springb.security" })
public class SpringbootSecurityApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityApp.class, args);
	}

}
