package com.pg.storm.hello;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class CharBolt extends BaseBasicBolt{

	public void execute(Tuple tuple, BasicOutputCollector collectOut) {
		Object ichar = tuple.getValue(0);
		String str = String.valueOf(ichar);
		collectOut.emit(new Values(str));
	}

	public void declareOutputFields(OutputFieldsDeclarer fieldOut) {
		fieldOut.declare(new Fields("field"));
	}

}
