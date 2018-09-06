package com.pg.springb.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {

	private final String BOOTSTRAP_SERVERS_CONFIG = "127.0.0.1:9092";

	@Bean
	@Qualifier("eventKafkaTemplate")
	public KafkaTemplate<String, EventDo> eventKafkaTemplate() {
		return new KafkaTemplate<>(eventProducerFactory());
	}

	@Bean
	@Qualifier("simpleKafkaTemplate")
	public KafkaTemplate<String, String> simpleKafkaTemplate() {
		return new KafkaTemplate<>(simpleProducerFactory());
	}

	private ProducerFactory<String, EventDo> eventProducerFactory() {
		Map<String, Object> cfgMp = new HashMap<>();
		cfgMp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
		cfgMp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		cfgMp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(cfgMp);
	}

	private ProducerFactory<String, String> simpleProducerFactory() {
		Map<String, Object> cfgMp = new HashMap<>();
		cfgMp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
		cfgMp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		cfgMp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<>(cfgMp);
	}
}
