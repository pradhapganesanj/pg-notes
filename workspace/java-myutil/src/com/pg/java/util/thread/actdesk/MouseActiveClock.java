package com.pg.java.util.thread.actdesk;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.Date;

public class MouseActiveClock {

	public static void main(String[] args) {
		long startT = System.currentTimeMillis();
		int duration = (null == args || 0 == args.length || null == args[0] || "".equals(args[0])) ? 0 : Integer.parseInt(args[0]); //mins;
		System.err.println("duration : "+duration+" Mins");
		Runnable run = () -> runner(280, startT, duration);
		Thread th = new Thread(run);
		th.start();
	}

	static void runner(int delaySec, long startT, int duration) {
		try {
			while (shallRun(startT, duration)) {
				mouseMove();
				System.out.println("sleep for "+delaySec + " seconds");
				Thread.sleep((long) (delaySec * 1000));
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.format("TimerStarted : %s TimerEnd : %s",new Date(startT).toString(), new Date(System.currentTimeMillis()).toString());
	}

	private static boolean shallRun(long startT, int duration) {
		
		if(0 == duration || 0 == startT) return true;
		
		long nowT = System.currentTimeMillis();
		long durT = duration * 60 * 1000;
		System.out.format("startT: %s \nnowT: %s\ndurationLng: %d \n", new Date(startT).toString(), new Date(nowT).toString(), duration);
		System.out.format("nowT - startT: %d (nowT - startT > durT): %b \n", (nowT - startT), (nowT - startT > durT));
		return !(nowT - startT > durT) ;
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
