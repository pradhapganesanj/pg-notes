package com.pg.sboot.integ.sftp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication(scanBasePackages={"com.pg.sboot.integ.sftp"})
@EnableIntegration
public class SpringIntegerationSftpApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegerationSftpApp.class , args);
	}

}
