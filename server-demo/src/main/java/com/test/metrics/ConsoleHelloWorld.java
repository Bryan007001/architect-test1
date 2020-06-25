package com.test.metrics;

public class ConsoleHelloWorld {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Hello, world!");
		synchronized(ConsoleHelloWorld.class) {
			ConsoleHelloWorld.class.wait();
		}
	}
}
