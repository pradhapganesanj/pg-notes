package com.pg.java.datetime.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CurrentDateTimeFormatted {

	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
		
  	  String[] elements 
		= new String[] {"rec_id"
						 ,"tefra_code"
						 ,"tax_rate"
						 ,"country_code"
						 ,"country_name"
						 ,"src_file_name"
					};
  	  
  	  System.out.println(String.join(",", elements));
  	  
	}

}
