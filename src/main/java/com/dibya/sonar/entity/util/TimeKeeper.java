package com.dibya.sonar.entity.util;

public class TimeKeeper {
	private long startTime;
	private long endTime;

	public void start() {
		startTime = System.currentTimeMillis();
	}

	public void stop() {
		endTime = System.currentTimeMillis();
	}

	public float getTimeTaken() {
		return (endTime - startTime) / 1000f;
	}
}
