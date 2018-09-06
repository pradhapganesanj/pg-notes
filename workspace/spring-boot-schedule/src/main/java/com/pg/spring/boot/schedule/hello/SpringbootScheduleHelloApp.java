package com.pg.spring.boot.schedule.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootScheduleHelloApp {

	public static void main(String[] args) {
		SpringApplication sApp = new SpringApplication(SpringbootScheduleHelloApp.class);
		sApp.run(args);
	}

}
