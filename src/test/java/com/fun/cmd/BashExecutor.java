package com.fun.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author xulujun
 * @date 2018/06/19
 */
public class BashExecutor implements CmdExecutor {

    @Override
    public int exec(String cmd, OutputStream outputStream) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(cmd.split(" "));
        pb.redirectErrorStream(true);
        Process process = pb.start();
        output(process.getInputStream(), outputStream);
        return process.waitFor();

    }

    private void output(InputStream inputStream, OutputStream outputStream) throws IOException {
        org.apache.commons.io.IOUtils.copy(inputStream, outputStream);
    }

}
