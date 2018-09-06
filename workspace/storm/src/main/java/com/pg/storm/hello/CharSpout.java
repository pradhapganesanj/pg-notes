package com.pg.storm.hello;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class CharSpout extends BaseRichSpout {

	SpoutOutputCollector spoutOut;

	int inc = 'a';

	public void nextTuple() {
		if (inc  < 120) {
			this.spoutOut.emit(new Values((char) inc));
			inc++;
		}
	}

	public void open(Map cfg, TopologyContext cntx, SpoutOutputCollector spoutOut) {
		this.spoutOut = spoutOut;
	}

	public void declareOutputFields(OutputFieldsDeclarer fieldOut) {
		fieldOut.declare(new Fields("field"));
	}

}
