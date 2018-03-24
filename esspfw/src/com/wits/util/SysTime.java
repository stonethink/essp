package com.wits.util;

import java.util.Calendar;

/** <p> ˵������ȡ��ǰʱ����ַ����� </p> <p> ���ڣ�2003/03/06 ���ߣ�Stone </p> */
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
        sysDateCN = years + "��" + months + "��" + days + "��";
        sysTimeCN = sysDateCN + " " + hours + "ʱ" + minutes + "��" + seconds + "��";
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
        sysDateCN = years + "��" + months + "��" + days + "��";
        sysTimeCN = sysDateCN + " " + hours + "ʱ" + minutes + "��" + seconds + "��";
	}

    /** ��ȡʱ���ʽΪ��YYYY-MM-DD_HH:MS:SS��ʱ���ַ����� */
    public static String getSysTime() {
        return sysTime;
    }

    /** ��ȡʱ���ʽΪ��YYYY-MM-DD��ʱ���ַ����� */
    public static String getSysDate() {
        return sysDate;
    }

    /** ��ȡʱ���ʽΪ��YYYY/MM/DD��ʱ���ַ����� */
    public static String getSysDateSlash() {
        return sysDateSlash;
    }

    /** ��ȡʱ���ʽΪ��YYYY-MM-DD HH:MS:SS��ʱ���ַ����� */
    public static String getSysTimeSemicolon() {
        return sysTimeSemicolon;
    }

    /** ��ȡʱ���ʽΪ��YYYY��MM��DD�յ�ʱ���ַ����� */
    public static String getSysDateCN() {
        return sysDateCN;
    }

    /** ��ȡʱ���ʽΪ��YYYY��MM��DD�� HHʱMS��SS���ʱ���ַ����� */
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
