package com.work.job;

import com.work.AbstractTest;
import org.junit.Test;

public class TestThread extends AbstractTest{
	
	public static void main(String[] args) throws Exception{
		Thread1 t1 = new Thread1();
		Thread1 t2 = new Thread1();
		t1.start();
		t2.start();
		
		Thread2 t3 = new Thread2();
		t3.start();
	}

	public static class ThreadInternal extends Thread{

    @Override
    public void run() {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        System.err.println("interrupt");
      }
      System.out.println("internal thread running...");
    }
  }
	
	public static class Thread1 extends Thread implements Runnable{
		public void start(){
			p("my start, not system");
		}
		public void run(){
			p("thread running");
		}
	}
	
	public static class Thread2 extends Thread{
		@Override
		public void run() {
			//interrupt();//不注释掉会直接进入InterruptedException
			try {
				synchronized (this) {
					wait(2000);//wait 必须包含在synchronized中
				}
				//sleep(1000);
			} catch (InterruptedException e) {
				p("Interrupted");
			}
			p(interrupted());
		}
	}




	@Test
  public void test() throws Exception{
    ThreadInternal thread1 = new ThreadInternal();
    thread1.start();
    //thread1.join();
    thread1.interrupt();
    System.out.println("end");
  }
	
}
