package com.work.job;

import java.io.IOException;

import com.work.AbstractTest;

public class TestClass extends AbstractTest{
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception{
		/*A a = new A();
		B b = new B();
		a.say();
		b.say();*/
		new TryCatch().test();
		//p(b.getClass().getName());
	}
	
	public static class A{
		public void hello() throws IOException{
			
		}
		public static void say(){
			p("a say");
		}
	}
	public static class B extends A{
		public static void say(){
			p("b say");
		}
		public void hello() throws IOException{
			
		}
	}
	
	
	
	public static class TryCatch{
	    private static int test1(){
	        for(;;) {             
	               try {
	                   return 1;
	               } finally {
	                   break;      
	               }           
	           }           
	           return -1;
	    }
	    private static int test2() throws Exception{
            for(;;) {             
                   try {
                       p("one");
                       String a = null;
                       a.toString();
                   } catch(Exception ex){
                       throw new Exception("xlj test");
                   }finally {
                       return 100;
                       //p(100);
                   }           
               }           
        }
		//@SuppressWarnings("null")
		public void test() throws Exception{
		    System.out.println(test2());
			/*try{
				p("one");
				String a = null;
				a.toString();
			}catch(Exception ex){
				try{
					p("two");
					String a = null;
					a.toString();
				}catch(Exception e){
					p("three");
					String a = null;
					a.toString();
				}finally{
					p("four");
					String a = null;
					a.toString();
				}
			}finally{
				p("five");
			}*/
		}
	}
}
