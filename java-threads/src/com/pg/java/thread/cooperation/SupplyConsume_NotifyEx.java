package com.pg.java.thread.cooperation;

import java.util.LinkedList;

public class SupplyConsume_NotifyEx {

	static SupplyConsumeHelper scHelp = new SupplyConsumeHelper(new LinkedList<String>());
	private Object mutex = new Object();

	public static void main(String[] args) {
		SupplyConsume_NotifyEx thisIns = new SupplyConsume_NotifyEx();
		Thread suppTh = new Thread(() -> thisIns.wait4Supply());
		Thread consTh = new Thread(() -> thisIns.notifyConsume());
		suppTh.start();
		consTh.start();
	}

	private void wait4Supply() {
		while (true) {
			if (null != scHelp.getSharedQ() && !scHelp.getSharedQ().isEmpty()) {
				scHelp.consumeTask();
			} else {
				try {
					synchronized (mutex) {
						System.err.println("************ notify SUPPLY *************** SHARED Q SIZE "
								+ scHelp.getSharedQ().size());						
						mutex.notify();
						mutex.wait();
					}
				} catch (Exception e) {
					System.err.println("Wait4Supply ERR " + e.getMessage());
				}
			}
		}
	}

	private void notifyConsume() {
		while (true) {
			scHelp.supplyTask();
			if (null != scHelp.getSharedQ() && scHelp.getSharedQ().size() > 10) {
				try {
					synchronized (mutex) {
						System.err.println("************ notify CONSUME *************** SHARED Q SIZE "
								+ scHelp.getSharedQ().size());
						mutex.notify();
						mutex.wait();
					}
				} catch (Exception e) {
					System.err.println("NotifyConsume ERR " + e.getMessage());
				}
			}
		}
	}

}
