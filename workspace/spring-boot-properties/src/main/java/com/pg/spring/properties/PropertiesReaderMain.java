package com.pg.spring.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.pg.spring.properties.access.PropertiesHandler;
import com.pg.spring.properties.access.PropertiesMapper;

@SpringBootApplication
@EnableConfigurationProperties(PropertiesMapper.class)
public class PropertiesReaderMain {

	public static void main(String[] args) {
		ApplicationContext appCntx = SpringApplication.run(PropertiesReaderMain.class, args);
		PropertiesHandler propHand = appCntx.getBean(PropertiesHandler.class);
		propHand.print();
	}

}
