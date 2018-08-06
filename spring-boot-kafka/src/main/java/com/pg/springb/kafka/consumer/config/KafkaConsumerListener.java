package com.pg.springb.kafka.consumer.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerListener {
	
	private final String TOPIC_SPRING_EVENT_JSON = "spring_event_json";
	private final String TOPIC_SPRING_EVENT_MSG = "spring_event_str";
	
	private final String  GROUP_ID_MSG = "group_id_msg";
	private final String  GROUP_ID_EVENT_JSON = "group_id_event_json";
	
	
	@KafkaListener(topics=TOPIC_SPRING_EVENT_MSG, group=GROUP_ID_MSG)
	public void msgConsumer(String msg){
		System.out.println("> "+msg);
	}
	
	@KafkaListener(topics=GROUP_ID_EVENT_JSON, group=GROUP_ID_EVENT_JSON, containerFactory="eventKafkaListenerFactory")
	public void eventConsumer(EventDo event){
		System.out.println("> "+event.toString());
	}
}
