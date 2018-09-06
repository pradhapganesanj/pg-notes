package com.pg.springb.kafka.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	private final String BOOTSTRAP_SERVERS_CONFIG = "127.0.0.1:9092";
	private final String  GROUP_ID_MSG = "group_id_msg";
	private final String  GROUP_ID_EVENT_JSON = "group_id_event_json";
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> simpleKafkaListenerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> containerFact =  new ConcurrentKafkaListenerContainerFactory<>();
		containerFact.setConsumerFactory(simpleConsumerFactory());
		return containerFact;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, EventDo> eventKafkaListenerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, EventDo> containerFact =  new ConcurrentKafkaListenerContainerFactory<>();
		containerFact.setConsumerFactory(eventConsumerFactory());
		return containerFact;
	}	
	
	private ConsumerFactory<String, String> simpleConsumerFactory() {
		Map<String, Object> cfgMp = new HashMap<>();
		cfgMp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
		cfgMp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		cfgMp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		cfgMp.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_MSG);
		
		return new DefaultKafkaConsumerFactory<>(cfgMp);
	}
	
	private ConsumerFactory<String, EventDo> eventConsumerFactory() {
		Map<String, Object> cfgMp = new HashMap<>();
		cfgMp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
		cfgMp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		cfgMp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		cfgMp.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_EVENT_JSON);
		
		return new DefaultKafkaConsumerFactory<>(cfgMp, new StringDeserializer(),
                new JsonDeserializer<>(EventDo.class));
	}
	
}
