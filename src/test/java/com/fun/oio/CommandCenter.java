package com.fun.oio;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

public class CommandCenter {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static ExecutorService bizExecutor;
    private static final Map<String, ICommandProcessor> controlMap = new HashMap<>();
    private static final String CSP_PORT = "csp.port";
    private static volatile int port = 8719;
    private static volatile boolean useCustomerPort = false;
    private static Set<String> modules = new HashSet<>();
    private static int defaultServerSocketTimeout;

    public CommandCenter() {
    }

    public static void registerCommand(String name, ICommandProcessor command) {
        if (!StringUtils.isEmpty(name)) {
            if (controlMap.containsKey(name)) {
                RecordLog.info("register command duplicated:" + name);
            }

            controlMap.put(name, command);
        }
    }

    public static Set<String> getCommands() {
        return controlMap.keySet();
    }

    public static ICommandProcessor getCommand(String commandName) {
        return (ICommandProcessor)controlMap.get(commandName);
    }

    public static synchronized void registerModule(String appName, String moduleName) {
        if (appName != null && appName.trim().length() != 0 && moduleName != null && moduleName.trim().length() != 0) {
            modules.add(appName + ":" + moduleName);
            //punish();
        }
    }

    /*private static void punish() {
        String publisherName = "courier-node";
        String dataId = "com.taobao.csp.courier.reporter";
        String group = "CSP";
        String appName = "app-name-test";
        String hostIp = null;

        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception var8) {
            RecordLog.info("[CommandCenter] punish error", var8);
            return;
        }

        String content = appName + ":" + hostIp + ":" + port + ":" + modules.toString();
        PublisherRegistration<String> registration = new PublisherRegistration(publisherName, dataId);
        registration.setGroup(group);
        Publisher<String> publisher = PublisherRegistrar.register(registration);
        publisher.publish(content);
        RecordLog.info("[CommandCenter] punish: " + content);
    }*/

    private static void setSocoketSoTimeout(Socket socket) throws SocketException {
        if (socket != null) {
            socket.setSoTimeout(defaultServerSocketTimeout);
        }

    }

    static {
        Thread thread = new Thread() {
            public void run() {
                int pocessNum = Runtime.getRuntime().availableProcessors();
                CommandCenter.bizExecutor = new ThreadPoolExecutor(pocessNum, pocessNum, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100), new DiscardPolicy());
                String specPort = System.getProperty("csp.port");
                if (StringUtils.isNotBlank(specPort)) {
                    try {
                        CommandCenter.port = Integer.parseInt(specPort.trim());
                        CommandCenter.useCustomerPort = true;
                    } catch (Exception var11) {
                        RecordLog.info("[CommandCenter] port format error: " + specPort);
                    }
                }

                long repeat = 0L;

                ServerSocket serverSocket;
                do {
                    serverSocket = null;

                    try {
                        serverSocket = new ServerSocket(CommandCenter.port);
                    } catch (Exception var10) {
                        RecordLog.info("holding CommandCenter failed,repeat:." + repeat, var10);
                        this.adjustPort(repeat);

                        try {
                            TimeUnit.SECONDS.sleep(12L);
                        } catch (InterruptedException var9) {
                            ;
                        }

                        ++repeat;
                    }
                } while (serverSocket == null);

                RecordLog.info("[CommandCenter] begin listening at port " + serverSocket.getLocalPort());
                CommandCenter.executorService.submit(new CommandCenter.ServerThread(serverSocket));
                /*CommandCenter.registerCommand("help", new HelperCommand());
                CommandCenter.registerCommand("modules", new ModuleCommand());
                ReportUtils.startReportThread(CommandCenter.port);*/
                CommandCenter.executorService.shutdown();
            }

            public void adjustPort(long repeat) {
                if (!CommandCenter.useCustomerPort) {
                    int mod = (int)repeat / 10;
                    CommandCenter.port = 8719 + 1 * mod;
                }
            }
        };
        thread.start();
        defaultServerSocketTimeout = 3000;

        try {
            String soTimeoutStr = System.getProperty("csp.server.socketSoTimeout");
            defaultServerSocketTimeout = Integer.valueOf(soTimeoutStr);
        } catch (NumberFormatException var1) {
            ;
        }

    }

    static class ServerThread extends Thread {
        private ServerSocket serverSocket;

        public ServerThread(ServerSocket s) {
            this.serverSocket = s;
        }

        public void run() {
            PrintWriter out;
            while (true) {
                Socket socket;

                try {
                    socket = this.serverSocket.accept();
                    CommandCenter.setSocoketSoTimeout(socket);

                    out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), Charset.forName("utf-8")));
                    out.write(">");
                    out.flush();

                    EventTask eventTask = new EventTask(socket);
                    CommandCenter.bizExecutor.submit(eventTask);
                } catch (Exception ex) {
                    RecordLog.info("server error!", ex);

                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}