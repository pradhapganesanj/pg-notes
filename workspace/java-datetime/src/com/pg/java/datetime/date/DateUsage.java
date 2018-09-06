package com.pg.java.datetime.date;

import java.util.Date;

public class DateUsage {

	public static void main(String[] args) {

		System.out.println("new Date() " + new Date());
		System.out.println("new Date(\"11/11/2011\") Deprecated " + new Date("11/11/2011"));
		System.out.println("new Date(111,11,11) Deprecated "+ new Date(111,11,11));

	}

}
