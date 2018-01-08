package com.pg.spring.properties.access;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertiesHandler {
	@Autowired
	PropertiesMapper propMap;

	public void print() {
		System.err.println("email from : " + propMap.getEmail_from());
		System.err.println("email to : " + Arrays.toString(propMap.getEmail_to()));
		System.err.println("email subject : " + propMap.getEmail_sub());
	}

}
