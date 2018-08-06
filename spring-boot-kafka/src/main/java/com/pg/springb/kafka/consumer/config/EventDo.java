package com.pg.springb.kafka.consumer.config;

public class EventDo {
	
	private String eventName;
	private Long eventId;
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public String toString(){
		return this.eventName+" , " + this.eventId;
	}
}
