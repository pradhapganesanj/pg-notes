package com.pg.springb.kafka.producer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("produce")
public class KafkaProducerController {

	private final String TOPIC_SPRING_EVENT_JSON = "spring_event_json";
	private final String TOPIC_SPRING_EVENT_STR = "spring_event_str";
	
	@Autowired
	KafkaTemplate<String, EventDo> eventKafkaTemplate;

	@Autowired
	KafkaTemplate<String, String> simpleKafkaTemplate;
	
	@PostMapping("/event")
	public String send(@RequestBody final EventDo event){
		
		eventKafkaTemplate.send(TOPIC_SPRING_EVENT_JSON, event);
		
		return "Event posted successfully";
	}
	
	@GetMapping("/msg/{msg}")
	public String send(@PathVariable("msg") final String msg){
		
		simpleKafkaTemplate.send(TOPIC_SPRING_EVENT_STR, msg);
		
		return "Msg posted successfully";
	}
	

}
