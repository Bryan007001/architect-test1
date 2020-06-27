package com.cxspaces.test.metrics;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GCTest {
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long addressOf(Object o) throws Exception {
        Object[] array = new Object[] { o };
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
        case 4:
            objectAddress = unsafe.getInt(array, baseOffset);
            break;
        case 8:
            objectAddress = unsafe.getLong(array, baseOffset);
            break;
        default:
            throw new Error("unsupported address size: " + addressSize);
        }
        return (objectAddress);
    }

    private static final int _1MB = 1024 * 1024;

    public static void test2() throws Exception {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
        long a1 = addressOf(allocation1);
        long a2 = addressOf(allocation2);
        long a3 = addressOf(allocation3);
        long a4 = addressOf(allocation4);
        // 打印对象所在的起始内存地址
        System.out.println("allocation1:0x00000000" + Long.toHexString(addressOf(allocation1)) + "\t" + (a2-a1)/1024 + "K");
        System.out.println("allocation2:0x00000000" + Long.toHexString(addressOf(allocation2)) + "\t" + (a3-a2)/1024 + "K");
        System.out.println("allocation3:0x00000000" + Long.toHexString(addressOf(allocation3)) + "\t" + (a3-a4)/1024 + "K");
        System.out.println("allocation4:0x00000000" + Long.toHexString(addressOf(allocation4)) + "\t" + a4);
        synchronized(System.out) {
        	System.out.wait();
        }
    }
	static int t = 0;
	
	public static void main(String[] args) throws Exception {
		//System.out.println(sum(30));
		test2();
        //long t3 = test3();
        //long t4 = test4();
        //System.out.println("\nTime elapsed:" + t3 + " vs " + t4);
	}

	private static long test3() {
		long t0 = new Date().getTime();
		int t = 1;
		for (int i = 1; i < 1000000; i ++) {
			t = (t*i) % 10001 + i;
		}
        long t1 = new Date().getTime();
        System.out.println("\nTime elapsed:" + (t1-t0));
		System.out.println(t);
		return t1-t0;
	}

	private static long test4() {
		long t0 = new Date().getTime();
		int t = 1;
		for (int i = 1; i < 1000000; i ++) {
			synchronized(System.out) {
				t = (t*i) % 10001 + i;
			}
		}
        long t1 = new Date().getTime();
        System.out.println("\nTime elapsed:" + (t1-t0));
		System.out.println(t);
		return t1-t0;
	}

	private static void test() {
		for (int i = 0; i < 100000; i ++) {
			build(10000);
		}
	}

	public static ArrayList<Object> build(int c) {
		ArrayList<Object> list = new ArrayList<Object>();
		
		for (int i = 0; i < c; i ++) {
			list.add(new HashMap<Integer, Integer>());
		}
		return list;
	}
	
	public static int sum(int c) {
		t ++;
		if (t % 100000 == 0) {
			System.out.println(t);
		}
		HashMap<Integer, Integer> h = new HashMap<Integer, Integer>();
		if (c == 0 ) {
			h.put(c, c);
			return 0;
		}
		return (h.size() + c + sum(c-1) + sum(c-2)) % 10001;
	}
}

