package com.baoli.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-30 12:57
 ************************************************************/

public class LogExceptionStackTrace {

    public LogExceptionStackTrace() {
    }

    public static Object errorStackTrace(Exception exception) {
        if (exception == null) {
            return "";
        }

        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            return sw.toString();
        } finally {
            try {
                pw.close();
                sw.close();
            } catch (IOException var14) {
                var14.printStackTrace();
            }
        }
    }
}
