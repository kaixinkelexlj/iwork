package com.work.job;

import java.util.Scanner;

import com.work.AbstractTest;

@SuppressWarnings("unused")
public class TestString extends AbstractTest{
	public static void main(String[] args) throws Exception{
		TestString test = new TestString();
		test.test4();
	}
	
	private void test1(){
		String a = "hello";
		char[] b = {'h', 'e', 'l', 'l', 'o'};
		p(a.toCharArray() == b);
		p(a.equals(b));
		p(a.toCharArray().equals(b));
		p(a.equals(new String(b)));
	}
	
	private void test2(){
		char a = '*';
		a += a;
		p(a);
	}
	
	private void test3(){
		String a = "this is \\xlj";
		p(a.replaceAll("\\\\", "/"));
	}
	
	//从控制台输入字符串和字节数,输出子串
	//汉字是两个字节
	//我ABC 4 => 我AB
	//我ABC汉字DEF 6 => 我ABC
	private void test4() throws Exception{
		Scanner in = new Scanner(System.in);
		String str = new String(in.next().getBytes("GBK"), "UTF-8");
		int bLen = in.nextInt();
		in.close();
		int count = 0;
		for(int i = 0;i < str.length(); i++){
			String c = str.substring(i,i + 1);
			count += c.getBytes().length;
			if(count <= bLen){
				p(c + "," + count + "," + c.getBytes().length);
			}else{
				break;
			}
		}
	}
}
