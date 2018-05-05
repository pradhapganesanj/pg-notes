package com.pg.sboot.batch.csvora.app;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages={"com.pg.sboot.batch.csvora"})
@EnableBatchProcessing
public class CsvOraApp {

	public static void main(String[] args) {
		SpringApplication.run(CsvOraApp.class, args);
	}

}
