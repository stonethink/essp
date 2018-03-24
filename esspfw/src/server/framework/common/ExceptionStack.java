package server.framework.common;

import java.io.PrintWriter;
import java.io.StringWriter;


public class ExceptionStack {
    public static String traceStack(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter  pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }
}
