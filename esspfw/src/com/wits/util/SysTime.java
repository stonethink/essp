package com.wits.util;

import java.util.Calendar;

/** <p> 说明：获取当前时间的字符串。 </p> <p> 日期：2003/03/06 作者：Stone </p> */
public class SysTime {
    private static String sysDate;
    private static String sysTime;
    private static String sysDateSlash;
    private static String sysTimeSemicolon;
    private static String sysDateCN;
    private static String sysTimeCN;

/*
    public SysTime() {
        Calendar cal = Calendar.getInstance();
        int yy = cal.get(Calendar.YEAR);
        String years = String.valueOf(yy);
        int mm = cal.get(Calendar.MONTH) + 1;
        String months = this.fillZero(mm);
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        String days = this.fillZero(dd);
        int hh = cal.get(Calendar.HOUR_OF_DAY);
        String hours = this.fillZero(hh);
        int ms = cal.get(Calendar.MINUTE);
        String minutes = this.fillZero(ms);
        int ss = cal.get(Calendar.SECOND);
        String seconds = this.fillZero(ss);
        sysDate = years + "-" + months + "-" + days;
        sysTime = sysDate + "_" + hours + ":" + minutes + ":" + seconds;
        sysDateSlash = years + "/" + months + "/" + days;
        sysTimeSemicolon = sysDate + " " + hours + ":" + minutes + ":" + seconds;
        sysDateCN = years + "年" + months + "月" + days + "日";
        sysTimeCN = sysDateCN + " " + hours + "时" + minutes + "分" + seconds + "秒";
	}
*/
    static {
        Calendar cal = Calendar.getInstance();
        int yy = cal.get(Calendar.YEAR);
        String years = String.valueOf(yy);
        int mm = cal.get(Calendar.MONTH) + 1;
        String months = fillZero(mm);
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        String days = fillZero(dd);
        int hh = cal.get(Calendar.HOUR_OF_DAY);
        String hours = fillZero(hh);
        int ms = cal.get(Calendar.MINUTE);
        String minutes = fillZero(ms);
        int ss = cal.get(Calendar.SECOND);
        String seconds = fillZero(ss);
        sysDate = years + "-" + months + "-" + days;
        sysTime = sysDate + "_" + hours + ":" + minutes + ":" + seconds;
        sysDateSlash = years + "/" + months + "/" + days;
        sysTimeSemicolon = sysDate + " " + hours + ":" + minutes + ":" + seconds;
        sysDateCN = years + "年" + months + "月" + days + "日";
        sysTimeCN = sysDateCN + " " + hours + "时" + minutes + "分" + seconds + "秒";
	}

    /** 获取时间格式为：YYYY-MM-DD_HH:MS:SS的时间字符串。 */
    public static String getSysTime() {
        return sysTime;
    }

    /** 获取时间格式为：YYYY-MM-DD的时间字符串。 */
    public static String getSysDate() {
        return sysDate;
    }

    /** 获取时间格式为：YYYY/MM/DD的时间字符串。 */
    public static String getSysDateSlash() {
        return sysDateSlash;
    }

    /** 获取时间格式为：YYYY-MM-DD HH:MS:SS的时间字符串。 */
    public static String getSysTimeSemicolon() {
        return sysTimeSemicolon;
    }

    /** 获取时间格式为：YYYY年MM月DD日的时间字符串。 */
    public static String getSysDateCN() {
        return sysDateCN;
    }

    /** 获取时间格式为：YYYY年MM月DD日 HH时MS分SS秒的时间字符串。 */
    public static String getSysTimeCN() {
        return sysTimeCN;
    }

    private static String fillZero(int value) {
        if (value < 10) return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }

    public static void main(String[] args) {
        //SysTime st = new SysTime();
        System.out.println(SysTime.getSysTime());
        System.out.println(SysTime.getSysDate());
        System.out.println(SysTime.getSysDateSlash());
        System.out.println(SysTime.getSysTimeSemicolon());
        System.out.println(SysTime.getSysDateCN());
        System.out.println(SysTime.getSysTimeCN());
    }
}
