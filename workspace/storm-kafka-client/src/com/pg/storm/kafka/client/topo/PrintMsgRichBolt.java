package com.pg.storm.kafka.client.topo;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class PrintMsgRichBolt  extends BaseRichBolt {
	
    private OutputCollector collector;

    /*public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }*/

    /*public void execute(Tuple input) {
        System.err.println("input = [" + input + "]");
        collector.ack(input);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }*/

	@Override
	public void prepare(Map topoConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		
	}

	@Override
	public void execute(Tuple input) {
		System.err.println("input = [" + input + "]");
		//System.err.println("input = [" + input.getStringByField("msg") + "]");
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("msg"));
	}
}