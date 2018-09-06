package com.techprimers.kafka.springbootkafkaconsumerexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages={"com.techprimers.kafka.springbootkafkaconsumerexample"})
public class SpringBootKafkaConsumerExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaConsumerExampleApplication.class, args);
	}
}
