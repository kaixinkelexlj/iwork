package com.work.job;

import com.work.AbstractTest;

public class TestDatatype extends AbstractTest{
	public static void main(String[] args) {
		TestDatatype test = new TestDatatype();
		test.testIntByteConvert();
	}
	
	private void testIntByteConvert(){
		int a = 1024;
		byte[] bb = int2byte(a);
		p(byte2int(bb));
	}
	
	
	private byte[] int2byte(int val){
		byte[] targets = new byte[4];
		targets[0] = (byte) (val & 0xff);// 最低位 
		targets[1] = (byte) ((val >> 8) & 0xff);// 次低位 
		targets[2] = (byte) ((val >> 16) & 0xff);// 次高位 
		targets[3] = (byte) (val >>> 24);// 最高位,无符号右移。
		return targets;
	}
	
	private int byte2int(byte[] bytes){
		int val = (bytes[0] & 0xff) 
		| (bytes[1] << 8 & 0xff00)
		| (bytes[2] << 16 & 0xff0000)
		| (bytes[3] << 24 & 0xff000000);
		return val;
	}
}
