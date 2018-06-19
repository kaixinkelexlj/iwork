package com.fun.oio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import com.fun.cmd.CmdExecutor;
import com.fun.cmd.CmdExecutors;
import org.apache.commons.lang.StringUtils;

/***
 *
 * @Description: comandcenter command task
 * @author youji.zj youji.zj@taobao.com
 * @since 2014-5-21
 *
 */
public class EventTask implements Runnable {

    Socket socket;

    public EventTask(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        if (socket == null) {
            return;
        }

        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket
                .getInputStream(), "GBK"));
            OutputStream outputStream = socket.getOutputStream();

            out = new PrintWriter(new OutputStreamWriter(
                outputStream, Charset.forName("GBK")));

            for (; ; ) {

                try{
                    String line = in.readLine();
                    if ("exit".equalsIgnoreCase(line)) {
                        break;
                    }

                    // 通过输入流接收客户端信息

                    System.out.println("[CommandCenter] receive a socket task:" + line + "," + socket.getInetAddress());


                    String[] arr = line.split(" ");
                    if(arr.length > 0 && "exec".equalsIgnoreCase(arr[0])){
                        if(arr.length > 1){
                            int val = execBash(line.replaceAll(arr[0], "").trim(), outputStream);
                            if(val != 0){
                                println(out, "error code[" + val + "]");
                            }
                        }else{
                            println(out, "");
                        }
                        continue;
                    }

                    Request request = parseRequest(line);

                /*println.write("fun>");
                //println.print("HTTP/1.1 200 OK\r\n\r\n");
                println.flush();*/
                    if (StringUtils.isBlank(request.getTarget())) {
                        //println(out, "query parameter is blank");
                        println(out, null);
                        continue;
                    }

                    ICommandProcessor command = CommandCenter.getCommand(request.getTarget());
                    if (null != command) {
                        command.execute(outputStream, request);
                        println(out);
                    } else {
                        println(out, "query parameter is incorrect");
                    }
                    RecordLog.info("[CommandCenter] deal a socket task:" + line + "," + socket.getInetAddress());
                }catch (Exception ex){
                    println(out, "CommandCenter failed, message is " + ex.getMessage());
                    RecordLog.info("CommandCenter close serverSocket failed", ex);
                }


            }

        } catch (Exception e) {
            RecordLog.info("CommandCenter error", e);

            try {
                println(out, "CommandCenter failed, message is " + e.getMessage());
            } catch (Exception e1) {
                RecordLog.info("CommandCenter close serverSocket failed", e);
            }
        } finally {
            try {
                out.close();
                in.close();
                //测试一下是不是可以不close
                socket.close();
            } catch (Exception e) {
                RecordLog.info("CommandCenter close resource failed", e);
            }
        }

    }

    private int execBash(String s, OutputStream outputStream) throws Exception{
        CmdExecutor executor = CmdExecutors.newBashExecutor();
        return executor.exec(s, outputStream);
    }

    private void println(PrintWriter out) {
        println(out, null);
    }

    private void println(PrintWriter out, String message) {
        if (message != null) {
            out.write(message);
            out.println();
            out.flush();
        }
        out.write(">");
        out.flush();
    }

    /**
     * GET /forceUpdateEvent?bizIndentify=123456 HTTP/1.1
     *
     * @param line
     * @return
     */
    private Request parseRequest(String line) {
        Request request = new Request();
        if (StringUtils.isBlank(line)) {
            return request;
        }
        int start = line.indexOf('/');
        int ask = line.indexOf('?') == -1 ? line.lastIndexOf(' ') : line.indexOf('?');
        int space = line.lastIndexOf(' ');
        String target = line.substring(start != -1 ? start + 1 : 0, ask != -1 ? ask : line.length());
        request.setTarget(target);
        if (ask == -1 || ask == space) {
            return request;
        }
        String parameterStr = line.substring(ask != -1 ? ask + 1 : 0, space != -1 ? space : line.length());
        for (String parameter : parameterStr.split("&")) {
            if (StringUtils.isBlank(parameter)) {
                continue;
            }

            String[] keyValue = parameter.split("=");
            if (keyValue.length != 2) {
                continue;
            }

            String value = StringUtils.trim(keyValue[1]);
            try {
                value = URLDecoder.decode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
            }

            request.addParameter(StringUtils.trim(keyValue[0]), value);
        }
        return request;
    }

}
