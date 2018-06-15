package com.fun.oio;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author xulujun
 * @date 2018/06/14
 */
public class CommandCenterTest {

    public static void main(String[] args) throws Exception{
        System.setProperty("csp.server.socketSoTimeout", "60000");
        CommandCenter.registerCommand("fun", new ICommandProcessor() {
            @Override
            public void execute(OutputStream out, Request request) throws Exception {
                PrintWriter printOut = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
                printOut.write("hello, this is a fun!!!\n");
                printOut.write("你好，这是一个测试\n");
                printOut.flush();
            }
        });
        Thread.currentThread().join();
    }

}