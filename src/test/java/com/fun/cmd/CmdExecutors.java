package com.fun.cmd;

/**
 * @author xulujun
 * @date 2018/06/19
 */
public class CmdExecutors {

  public static CmdExecutor newBashExecutor() {
    return new BashExecutor();
  }

  public static void main(String[] args) throws Exception {

    String userHome = System.getProperty("user.home");
    System.out.println(userHome);

    int ret = CmdExecutors.newBashExecutor().exec("ifconfig", System.out);
    System.out.println("ret ==> " + ret);
    ret = CmdExecutors.newBashExecutor().exec("echo 1", System.out);
    System.out.println("ret ==> " + ret);

    ret = CmdExecutors.newBashExecutor()
        .exec(System.out, "bash", "-c", "grep ac191a2d5b56c8440bf3f87d00000686 " +
            userHome + "/logs/dps_monitor/*.log");
    System.out.println("ret ==> " + ret);


    ret = CmdExecutors.newBashExecutor()
        .exec(System.out, "bash", "-c", "cat " +
            userHome + "/logs/dps_monitor/*.log|grep 'ac191a2d5b56c8440bf3f87d00000686'");
    System.out.println("ret ==> " + ret);


  }


}
