package com.fun.cmd;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author xulujun
 * @date 2018/06/19
 */
public interface CmdExecutor {

    int exec(String cmd, OutputStream outputStream) throws IOException, Exception;

}
