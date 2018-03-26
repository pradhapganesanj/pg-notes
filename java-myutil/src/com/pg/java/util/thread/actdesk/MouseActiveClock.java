package com.pg.java.util.thread.actdesk;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;

public class MouseActiveClock {

	public static void main(String[] args) {

		Runnable run = () -> runner(250);
		Thread th = new Thread(run);
		th.start();
	}

	static void runner(int delaySec) {
		try {
			while (true) {
				mouseMove();
				System.out.println("sleep for "+delaySec + " seconds");
				Thread.sleep((long) (delaySec * 1000));
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	static void mouseMove() {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX();
		int y = (int) b.getY();
		System.out.println("x: " + x + ", y: " + y);

		int newX = (int) (Math.random() * Math.random() * 100);
		int newY = (int) (Math.random() * Math.random() * 100);

		System.out.println("newX: " + newX + ", newY: " + newY);

		Robot r;
		try {
			r = new Robot();
			r.mouseMove(newX, newY);
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}
}
