package com.work.job;

public class StaticTest {
	//所有static部分按照static代码先后顺序执行
	//试试交换i声明位置，输出不同
	public static int i = 10;
	static{
		i = 20;
	}
	public static void main(String[] args) {
		System.out.println(StaticTest.i);
	}
}
