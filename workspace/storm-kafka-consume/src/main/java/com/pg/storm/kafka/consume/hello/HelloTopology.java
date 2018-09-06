package com.pg.storm.kafka.consume.hello;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
//import org.apache.storm.kafka.spout.KafkaSpoutConfig.ProcessingGuarantee;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff.TimeInterval;
import org.apache.storm.kafka.spout.KafkaSpoutRetryService;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

public class HelloTopology {

	public static void main(String[] args) {

		final String zkHOST = "localhost:2020";
		final String kafkaTopic = "hello-storm-topic";
		final String zkRoot = "/brokers";		
		final String clientId = "storm-consumer";
		
		kafkaSpoutTopology(zkHOST, kafkaTopic, zkRoot, clientId);
		//offsetTopology (zkHOST, kafkaTopic );
	}

	private static void kafkaSpoutTopology(final String zkHOST, final String kafkaTopic, final String zkRoot,
			final String clientId) {
		final BrokerHosts zkrHosts = new ZkHosts(zkHOST);

		final SpoutConfig spoutCfg = new SpoutConfig(zkrHosts, kafkaTopic, zkRoot, clientId);
		spoutCfg.scheme = new SchemeAsMultiScheme(new StringScheme());
		spoutCfg.useStartOffsetTimeIfOffsetOutOfRange = true;
		
		spoutCfg.zkRoot = zkRoot;
		spoutCfg.zkPort = 2020;
		//spoutCfg.
		spoutCfg.id="hellotopology";
		spoutCfg.ignoreZkOffsets = false;
		
		System.out.println(String.format(" CurrentVersion: %d,  LargestTimeString: %s, SmallestTimeString: %s", kafka.api.OffsetRequest.CurrentVersion(), kafka.api.OffsetRequest.LargestTimeString(), kafka.api.OffsetRequest.SmallestTimeString() ));
		
		spoutCfg.startOffsetTime = kafka.api.OffsetRequest.LatestTime(); 
		KafkaSpout kafkaSpout = new KafkaSpout(spoutCfg);

		final TopologyBuilder topologyBuilder = new TopologyBuilder();
		topologyBuilder.setSpout("kafka-spout", kafkaSpout, 1);
		topologyBuilder.setBolt("print-messages", new HelloBolt()).globalGrouping("kafka-spout");

		Map<String, Object> conf = new HashMap();
		conf.put(Config.TOPOLOGY_WORKERS, 4);
		conf.put(Config.TOPOLOGY_DEBUG, true);
		
		final LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("kafka-topology", new HashMap(), topologyBuilder.createTopology());
	}
	

	public static void offsetTopology(final String zkHOST, final String kafkaTopic) {

	    TopologyBuilder builder = new TopologyBuilder();

	    try {       
	        KafkaSpoutConfig<String, String> kafkaSpoutConfig = getKafkaSpoutConfig(zkHOST, kafkaTopic);      
	        
	        org.apache.storm.kafka.spout.KafkaSpout<String, String> kafkaSpout = new org.apache.storm.kafka.spout.KafkaSpout<>(kafkaSpoutConfig);
			
			final TopologyBuilder topologyBuilder = new TopologyBuilder();
			topologyBuilder.setSpout("kafka-spout", kafkaSpout, 1);
			topologyBuilder.setBolt("print-messages",  new HelloBolt()).globalGrouping("kafka-spout");

			final LocalCluster localCluster = new LocalCluster();
			localCluster.submitTopology("kafka-topology", new HashMap(), topologyBuilder.createTopology());	        
	        
	    } catch (Exception ex) {

	    }

		final LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("kafka-topology", new HashMap(), builder.createTopology());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static KafkaSpoutConfig<String, String> getKafkaSpoutConfig(String bootstrapServers ,String topic) {

		KafkaSpoutConfig.Builder<String, String> builder = KafkaSpoutConfig.builder(bootstrapServers, new String[]{topic});

	    return builder
	    	//.setProp(ConsumerConfig.GROUP_ID_CONFIG, topic)
	        //.setProcessingGuarantee(ProcessingGuarantee.AT_MOST_ONCE)
	        .setRetry(getRetryService())
	        //.setRecordTranslator(trans)
	        .setGroupId("kafka-topology_hello-storm-topic_group")
	        .setOffsetCommitPeriodMs(10000)
	        .setFirstPollOffsetStrategy(org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy.UNCOMMITTED_EARLIEST)
	        .setMaxUncommittedOffsets(1000)
	        .build();
	}
	
	protected static KafkaSpoutRetryService getRetryService() {
	    return new KafkaSpoutRetryExponentialBackoff(TimeInterval.microSeconds(500),
	        TimeInterval.milliSeconds(2), Integer.MAX_VALUE, TimeInterval.seconds(10));
	 }
}
