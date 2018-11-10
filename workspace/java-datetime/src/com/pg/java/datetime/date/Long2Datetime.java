package com.pg.java.datetime.date;

import java.util.Date;

public class Long2Datetime {

	public static void main(String[] args) {

		Date dt = new Date(1541440538884L);
		System.out.println(dt);
				//.withZone( DateTimeZone.forID( "Europe/Paris" ) );
		
	}

}
