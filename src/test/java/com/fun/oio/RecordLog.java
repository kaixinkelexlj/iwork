package com.fun.oio;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

/***
 * 记录关键事件，日志的量级比较小
 *
 * @author youji.zj youji.zj@taobao.com
 * @since 2014-2-20
 *
 */
public class RecordLog {
    private static final String GBK = "GBK";
    private static final String YYYY_MM_DD = "'.'yyyy-MM-dd";
    private static final String M_N = "%d{yyyy-MM-dd HH:m:ss} %m%n";
    private static org.apache.log4j.Logger heliumRecordLog = org.apache.log4j.Logger.getLogger("cspRecordLog");
    private static final String DIR_NAME = "logs" + File.separator + "csp";
    private static final String FILE_NAME = "record.log";
    private static final String USER_HOME = "user.home";

    static {
        PatternLayout layout = new PatternLayout(M_N);
        String userHome = System.getProperty(USER_HOME);
        if (!userHome.endsWith(File.separator)) {
            userHome += File.separator;
        }
        String path = userHome + DIR_NAME + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = path + FILE_NAME;

        FileAppender appender = null;
        try {
            appender = new DailyRollingFileAppender(layout, fileName, YYYY_MM_DD);
            appender.setAppend(true);
            appender.setEncoding(GBK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (appender != null) {
            heliumRecordLog.removeAllAppenders();
            heliumRecordLog.addAppender(appender);
        }
        heliumRecordLog.setLevel(Level.INFO);
        heliumRecordLog.setAdditivity(false);
    }

    public static void info(String detail) {
        heliumRecordLog.info(detail);
    }

    public static void info(String detail, Throwable e) {
        heliumRecordLog.info(detail, e);
    }

    public static org.apache.log4j.Logger getLogger() {
        return heliumRecordLog;
    }
}
