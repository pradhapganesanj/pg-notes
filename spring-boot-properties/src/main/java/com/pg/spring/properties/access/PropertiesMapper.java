package com.pg.spring.properties.access;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class PropertiesMapper {
	private String email_from;
	private String[] email_to;
	private String email_sub;
	
	public String getEmail_from() {
		return email_from;
	}
	public String[] getEmail_to() {
		return email_to;
	}
	public String getEmail_sub() {
		return email_sub;
	}
	public void setEmail_from(String email_from) {
		this.email_from = email_from;
	}
	public void setEmail_to(String[] email_to) {
		this.email_to = email_to;
	}
	public void setEmail_sub(String email_sub) {
		this.email_sub = email_sub;
	}
}
