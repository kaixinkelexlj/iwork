package com.fun.oio;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

/**
 * @author xulujun
 * @date 2018/06/19
 */
public class OIOClient implements Closeable {


    public static void main(String[] args) throws Exception {
        try (OIOClient client = OIOClient.connect("localhost", 8719, System.in, System.out)) {
            client.start();
        }
    }

    private static final String CHARSET = "utf8";

    private Socket socket;
    private InputStream input;
    private OutputStream output;

    private OIOClient(String host, int port, InputStream input, OutputStream output) throws Exception {
        try {
            this.socket = new Socket(host, port);
            socket.setSoTimeout(1000);//100ms
        } catch (Exception ex) {
            throw new IOException(String.format("connect to server fail, server info[%s:%s]", host, port));
        }
        this.input = input;
        this.output = output;
    }

    public static OIOClient connect(String host, int port, InputStream input, OutputStream output) throws Exception {
        return new OIOClient(host, port, input, output);
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException ignored) {

        }
    }

    public void start() throws Exception {
        try (Scanner scanner = new Scanner(input);
             PrintWriter clientPrinter = new PrintWriter(new OutputStreamWriter(output, CHARSET));
             PrintWriter socketWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), CHARSET));
             BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET))) {

            outResponse(socketReader, clientPrinter);
            String line;
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                System.out.println("input==>" + line);
                socketWriter.println(line);
                socketWriter.flush();
                /*int response = socketReader.read();
                System.out.println("response==>" + response);*/
                /*if (StringUtils.isNotBlank(response)) {
                    clientPrinter.println(response);
                }*/
                outResponse(socketReader, clientPrinter);

            }

        }
    }

    private void outResponse(BufferedReader socketReader, PrintWriter clientPrinter) throws Exception {
        StringBuilder response = new StringBuilder();
        int val;
        char ch;
        try {
            while ((val = socketReader.read()) != -1) {
                ch = (char)val;
                response.append(ch);
                if (ch == '>') {
                    if(CommandCenter.PROMOTION.equals(response.toString())){
                        break;
                    }
                }
            }
        } catch (SocketTimeoutException ex) {
            System.err.println("so tome out");
        }

        clientPrinter.write(response.toString());
        clientPrinter.flush();
    }



}
