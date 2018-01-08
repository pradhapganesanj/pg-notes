package com.pg.java.thread.cooperation;

import java.util.Queue;

public class SupplyConsumeHelper {
	private Queue<String> sharedQ = null;

	public Queue<String> getSharedQ() {
		return sharedQ;
	}

	public void setSharedQ(Queue<String> sharedQ) {
		this.sharedQ = sharedQ;
	}

	int count = 0;

	public SupplyConsumeHelper(Queue<String> sharedQ) {
		this.sharedQ = sharedQ;
	}

	public void consumeTask() {
		if (!sharedQ.isEmpty()) {
			System.out.println("Queue poped :" + sharedQ.remove());
		} else {
			/*
			 * try { Thread.sleep(10); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */
			System.out.println("Polling and checking Queue ");
		}
	}

	public void supplyTask() {
		/*try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		String randChar = getRandChar();
		int cnt = ++count;
		String suppyTxt = randChar + "-" + cnt;
		sharedQ.add(suppyTxt);
		System.err.println("Added Item into Queue "+suppyTxt);
	}

	public String getRandChar() {
		return String.valueOf((char) (65 + (int) (Math.random() * 26.0)));
	}

}
