package com.pg.storm.kafka.client.topo;

import java.util.HashMap;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.mapper.TupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.kafka.spout.ByTopicRecordTranslator;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class KafkaSpoutTopology {

    public static final long DEFAULT_OFFSET_COMMIT_PERIOD_MS = 10000;
    public static final long DEFAULT_INITAIL_DELAY_MS = 500;
    public static final long DEFAULT_DELAY_PERIOD_MS = 2;
    public static final long DEFAULT_MAX_DELAY_PERIOD_MS = 10;
    public static final int DEFAULT_MAX_UNCOMMITTED_OFFSETS = 1000;
    public static final String DEFAULT_AUTO_OFFSET_REST = "UNCOMMITTED_EARLIEST";

    public static final String KAFKA_SPOUT_OUTFIELDS = "kafka.spout.outfields";
    public static final String KAFKA_SPOUT_INITIAL_DELAY_MICROSECONDS = "kafka.spout.initial.delay.microseconds";
    public static final String KAFKA_SPOUT_DELAY_PERIOD_MILLISECONDS = "kafka.spout.delay.period.milliseconds";
    public static final String KAFKA_SPOUT_MAX_DELAY_SECONDS = "kafka.spout.max.delay.seconds";
    public static final String KAFKA_SPOUT_MAX_RETRIES_COUNT = "kafka.spout.max.retries.count";

    public static final String KAFKA_SPOUT_MAX_UNCOMMITTED_OFFSETS = "kafka.spout.max.committed.offsets";
    public static final String KAFKA_SPOUT_OFFSET_COMMIT_PERIOD_MS = "kafka.spout.offset.commit.period.ms";
    public static final String KAFKA_SPOUT_AUTO_OFFSET_RESET = "kafka.spout.auto.offset.reset";
    public static final String KAFKA_SPOUT_CONSUMER_GROUP_ID = "kafka.spout.consumer.group.id";
    
    public static final String BOOTSTRAP_SERVERS_CONFIG = "localhost:9092"; //"localhost:9092" 2020
    public static String groupId; 
    public static final String topicName  = "file_export_topic"; //"hello-storm-topic";

	final String zkRoot = "/brokers";		
	final String clientId = "storm-consumer";

	public static void main(String...str){
		
		KafkaSpoutConfig<String, String> kafkaSpoutCfg =  getKafkaSpoutConfig();
		
		KafkaSpout<String, String> kafkaSpout =  new KafkaSpout<>(kafkaSpoutCfg);
		
		final TopologyBuilder topologyBuilder = new TopologyBuilder();
		topologyBuilder.setSpout("kafka-spout", kafkaSpout, 1);
		topologyBuilder.setBolt("print-messages", new PrintMsgRichBolt()).globalGrouping("kafka-spout");

		final LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("kafka-topology", new HashMap(), topologyBuilder.createTopology());
	}
	
	private static KafkaSpoutConfig<String, String> getKafkaSpoutConfig() {
		int maxUncommittedOffsets =  DEFAULT_MAX_UNCOMMITTED_OFFSETS;
		long offsetCommitPeriod = DEFAULT_OFFSET_COMMIT_PERIOD_MS;
		String autoOffsetReset = DEFAULT_AUTO_OFFSET_REST;
		FirstPollOffsetStrategy firstPollOffsetStrategy = FirstPollOffsetStrategy.valueOf(autoOffsetReset.toUpperCase());
		
		String bootstrapServers = BOOTSTRAP_SERVERS_CONFIG;

		groupId = "KafkaSpoutTopology"+ "_" + topicName+ "_consumer";	
		
		
        ByTopicRecordTranslator<String, String> trans = new ByTopicRecordTranslator<>(
                (r) -> new Values(r.topic(), r.partition(), r.offset(), r.key(), r.value()),
                new Fields("topic", "partition", "offset", "key", "value"), topicName);
           
		
		return KafkaSpoutConfig.builder(BOOTSTRAP_SERVERS_CONFIG, topicName)
				.setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
				.setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG , "org.apache.kafka.common.serialization.StringDeserializer")
				//.setKey(this.keyDesClazz)
				//.setValue(this.valueDesClazz)
				.setGroupId(groupId)
				//.setRetry(getRetryService())
				//.setRecordTranslator(trans)
				.setOffsetCommitPeriodMs(offsetCommitPeriod)
				.setFirstPollOffsetStrategy(firstPollOffsetStrategy)
				.setMaxUncommittedOffsets(maxUncommittedOffsets)
				.build();
	}
	
	public static final TupleToKafkaMapper<Object, Object> defaultMapper = new FieldNameBasedTupleToKafkaMapper<>();
	private static KafkaBolt<Object, Object> getBolt() {
		
		Properties producerProps = new Properties();
		producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:2020");
		// producerProps.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ERROR_MESSAGE);
		// producerProps.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ERROR_MESSAGE);
		
		return new KafkaBolt<>()
			.withProducerProperties(producerProps)
			.withTopicSelector(new DefaultTopicSelector(topicName))
			.withTupleToKafkaMapper(defaultMapper);
		
	}
}
