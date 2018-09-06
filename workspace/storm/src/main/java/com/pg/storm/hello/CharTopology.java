package com.pg.storm.hello;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class CharTopology {

	public static void main(String[] args) {

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("CharSpout", new CharSpout());
		builder.setBolt("CharBolt", new CharBolt()).shuffleGrouping("CharSpout");

		Config cfg = new Config();
		cfg.setDebug(true);

		LocalCluster clustr = new LocalCluster();
		clustr.submitTopology("CharTopology", cfg, builder.createTopology());
	}

}
