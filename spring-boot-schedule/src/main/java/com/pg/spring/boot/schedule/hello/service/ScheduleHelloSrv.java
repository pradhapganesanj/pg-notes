package com.pg.spring.boot.schedule.hello.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleHelloSrv {

	private Queue<ClientInterval> ciList = new LinkedList<ClientInterval>();
	
	public ScheduleHelloSrv() {
		processClientInterval();
	}

	@Scheduled(fixedRate = 1000)
	public void logTime() {
		if (!ciList.isEmpty()) {
			System.out.println("ClientIntrv process ");
			ClientInterval ci = ciList.peek();
			System.out.println(currTime()+" CI: " + ci.toString());
			boolean bool = isRightTimeForInterval(ci.getIntervalMinuteStr(), ci.getEmailSentTs());
			if (bool) {
				System.err.println(ci.toString()+" "+currTime()+" timeForInterval MET");
				ciList.remove();
			}else{
				System.out.println(currTime()+" timeForInterval NOT met");
			}

		}
	}

	private String currTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
	}

	public void processClientInterval() {

		LocalDateTime dtTime = LocalDateTime.now();
		LocalDateTime dtTime1 = dtTime.plusSeconds(5);
		LocalDateTime dtTime2 = dtTime.plusSeconds(15);
		LocalDateTime dtTime3 = dtTime.plusSeconds(20);
		LocalDateTime dtTime4 = dtTime.plusSeconds(40);

		ClientInterval cIntrv = new ClientInterval("5", dtTime1);
		ClientInterval cIntrv2 = new ClientInterval("5", dtTime2);
		ClientInterval cIntrv3 = new ClientInterval("5", dtTime3);
		ClientInterval cIntrv4 = new ClientInterval("5", dtTime4);

		ciList.add(cIntrv);
		ciList.add(cIntrv2);
		ciList.add(cIntrv3);
		ciList.add(cIntrv4);
	}

	private boolean isRightTimeForInterval(String intervalMinuteStr, LocalDateTime emailSentTs) {

		if (emailSentTs == null)
			return true;
		try {
			double d = Double.parseDouble(intervalMinuteStr);
			emailSentTs = emailSentTs.plusSeconds((int) d);
			LocalDateTime ldtNow = LocalDateTime.now();
			long emlPlsIntrvlTime = convertLocalDateTimeToEpoch(emailSentTs);
			long nowTime = convertLocalDateTimeToEpoch(ldtNow);
			System.out.println("nowTime:"+ldtNow.format(DateTimeFormatter.ISO_LOCAL_TIME)+" emlPlsIntrvlTime:"+emailSentTs.format(DateTimeFormatter.ISO_LOCAL_TIME));
			System.out.println("(nowTime - emlPlsIntrvlTime): " + (nowTime - emlPlsIntrvlTime));
			return nowTime > emlPlsIntrvlTime;
		} catch (Exception e) {
			System.out.println("Exception in isRightTimeForInterval():" + e.getMessage());
		}

		return false;
	}

	private Long convertLocalDateTimeToEpoch(LocalDateTime currentTs) {
		long epochSec = currentTs.atZone(Clock.systemUTC().getZone()).toInstant().getEpochSecond();
		Integer nanos = currentTs.getNano();
		Long nanoTime = (epochSec * 1000000000) + nanos;
		return nanoTime;
	}

}

class ClientInterval {
	private String intervalMinuteStr;
	private LocalDateTime emailSentTs;

	public ClientInterval(String intervalMinuteStr, LocalDateTime emailSentTs) {
		this.intervalMinuteStr = intervalMinuteStr;
		this.emailSentTs = emailSentTs;
	}

	public String getIntervalMinuteStr() {
		return intervalMinuteStr;
	}

	public void setIntervalMinuteStr(String intervalMinuteStr) {
		this.intervalMinuteStr = intervalMinuteStr;
	}

	public LocalDateTime getEmailSentTs() {
		return emailSentTs;
	}

	public void setEmailSentTs(LocalDateTime emailSentTs) {
		this.emailSentTs = emailSentTs;
	}

	public String toString() {
		return "interv: " + this.intervalMinuteStr + " lastSent: " + this.emailSentTs.format(DateTimeFormatter.ISO_LOCAL_TIME);
	}

}