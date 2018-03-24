package server.essp.tc.common;

import java.util.Date;
import java.util.Calendar;

public class LgTcCommon {
    public static Date resetBeginDate(Date b) {
        Calendar c = Calendar.getInstance();
        c.setTime(b);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date b2 = c.getTime();
        return b2;
    }

    public static Date resetEndDate(Date e) {
        Calendar c = Calendar.getInstance();
        c.setTime(e);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        Date e2 = c.getTime();
        return e2;
    }
    public static Date[] resetBeginDate(Date b, Date e) {
        Calendar c = Calendar.getInstance();
        c.setTime(b);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date b2 = c.getTime();

        c.setTime(e);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        Date e2 = c.getTime();
        return new Date[]{b2,e2};
    }




}
