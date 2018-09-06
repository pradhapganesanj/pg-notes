package com.pg.java.thread.cooperation;

import java.util.LinkedList;

/*
 * A thread keep running and polling a queue; consumes CPU and memory; its not feasible approach for given scenario.
 * 
 */
public class SupplyConsume_PollingEx {

	static SupplyConsumeHelper scHelp = new SupplyConsumeHelper(new LinkedList<String>());
	
	public static void main(String[] args) {

		Thread supplierTh = new Thread(SupplyConsume_PollingEx::pollQ);
		Thread consumerTh = new Thread(SupplyConsume_PollingEx::pushQ);
		supplierTh.start();
		consumerTh.start();
	}

	static void pollQ() {
		while(true) {
			scHelp.consumeTask();
		}
	}

	static void pushQ() {
		while(true) {
			scHelp.supplyTask();
		}
	}

}
