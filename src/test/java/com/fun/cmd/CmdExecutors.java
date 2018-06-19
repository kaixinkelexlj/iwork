package com.fun.cmd;

/**
 * @author xulujun
 * @date 2018/06/19
 */
public class CmdExecutors {

    public static CmdExecutor newBashExecutor(){
        return new BashExecutor();
    }

    public static void main(String[] args) throws Exception {
        int ret = CmdExecutors.newBashExecutor().exec("ifconfig", System.out);
        System.out.println("ret ==> " + ret);
        ret = CmdExecutors.newBashExecutor().exec("echo 1", System.out);
        System.out.println("ret ==> " + ret);
    }


}
