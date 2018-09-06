package com.pg.storm.kafka.consume.hello;

import java.io.IOException;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.spout.Func;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy;
import org.apache.storm.kafka.spout.trident.KafkaTridentSpoutOpaque;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.tuple.Fields;

public class HelloTridentTopology extends BaseTopologyBuilder { 
	
	Config config;
	
	//overridden "apply" method where from the record only message is extracted out "record.value()"
	private static Func<ConsumerRecord<String, String>, List<Object>> FUNCTION = new CommandValueFunction();
	public void config() throws IOException {
	    config = new Config();
	    config.setDebug(false);
	    config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
	    config.setNumWorkers(1);
	    config.put("topology.spout.max.batch.size", 1);
	}
	protected KafkaSpoutConfig<String, String> spoutConfig(String topic) {
	    return KafkaSpoutConfig
	            .builder("localhost:9092", topic)
	            .setGroupId("kafkaSpoutTestGroup")
	            .setMaxPartitionFectchBytes(2000000000)
	            .setRecordTranslator(FUNCTION, new Fields("message"))
	            .setRetry(newRetryService()).setOffsetCommitPeriodMs(10_000)
	            .setFirstPollOffsetStrategy(FirstPollOffsetStrategy.UNCOMMITTED_LATEST)
	            .setMaxUncommittedOffsets(250)
	            .setProp(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
	            .build();
	}
	//MessagePrinter extends BaseFunction and tuples are printed in execute()
	public void buildTopology(MessagePrinter bolt) {
	    TridentTopology topology = new TridentTopology();
	    Stream stream = topology.newStream("spout", new KafkaTridentSpoutOpaque<>(spoutConfig("sample")));
	    stream.each(new Fields("message"), bolt, new Fields()).parallelismHint(2);
	    try {
	        config.put("zookeeper.ip", "localhost:2020");
	        StormSubmitter.submitTopology("topology", config, topology.build());
	    } catch (Exception exception) { }
	}
	public static void main(String[] args) throws Exception {
		HelloTridentTopology topologyBuilder = new HelloTridentTopology();
	    topologyBuilder.config();
	    topologyBuilder.buildTopology();
	}}