package com.work.job;

public class ClassTest {
	public static void main(String[] args) {
		Xlj x = new Hhq();
		x.printName();
		ClassTest c = new ClassTest();
		Yuanbao y = c.new Yuanbao();
	}
	
	public static abstract class Xlj{
		public void printName(){
			System.out.println(this.getClass().getSimpleName());
		}
	}
	public static class Hhq extends Xlj{
		
	}
	
	public class Yuanbao{
	    
	}
}
