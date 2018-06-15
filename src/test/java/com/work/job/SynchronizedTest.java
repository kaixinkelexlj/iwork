package com.work.job;

import java.util.HashMap;
import java.util.Map;

import com.work.AbstractTest;

public class SynchronizedTest extends AbstractTest{
	private static Long lastModified;
	private static Map<String,String> data;
	static{
		init();
	}
	private static void init(){
		data = new HashMap<String, String>();
		lastModified = 1001L;
		parseConf();
		p("SynchronizedTest init over..");
	}
	private static void parseConf(){
		data.put("ok-1", "xlj");
	}
	public synchronized static void noSync(){
		p("I am ok..");
	}
	public synchronized static String get(String key,Long input){
		try{
			Thread.sleep(5000);
		}catch(Exception ex){}
		p("visit get...");
		if(!lastModified.equals(input)){
			init();
		}
		return data.get(key);
	}
	public static void main(String[] args) throws Exception{
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				p(SynchronizedTest.get("ok-1", 1001L));
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					Thread.sleep(3000);
				}catch(Exception ex){}
				SynchronizedTest.noSync();
			}
		});
		t1.start();
		t2.start();
	}
}
