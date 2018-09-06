package com.pg.spring.boot.schedule.hello.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SayHelloEvery10Secs {

	@Scheduled(fixedDelay=10000)
	public void sayHello(){
		String ts = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		System.out.println(ts+ " : Hellooooo...");
	}
	
}
