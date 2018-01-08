package com.pg.sboot.mvc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.pg.sboot.mvc"})
public class MyWebApp {

	public static void main(String[] args) {
		SpringApplication.run(MyWebApp.class, args);
	}

}
