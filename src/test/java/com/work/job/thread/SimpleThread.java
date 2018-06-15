package com.work.job.thread;

import java.util.List;

public class SimpleThread extends Thread{
	SimpleObj obj;
	public SimpleThread(SimpleObj obj){
		this.obj = obj;
	}
	public void run() {
		obj.addName("xlj");
	}
	public String getObjName(){
		return obj.getLastName();
	}
	public List<String> getNameList(){
		return obj.getNameList();
	}
	public static void main(String[] args) throws Exception{
		SimpleObj obj = new SimpleObj();
		SimpleThread t1 = new SimpleThread(obj);
		SimpleThread t2 = new SimpleThread(obj);
		t1.start();
		t2.start();
		Thread.sleep(500);
		System.out.println("%%" + t2.getObjName());
		System.out.println("%%" + t2.getNameList().size());
	}
}
