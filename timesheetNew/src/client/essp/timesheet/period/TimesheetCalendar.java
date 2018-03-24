package client.essp.timesheet.period;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.preference.DtoPreference;
import client.net.ConnectFactory;
import client.net.NetConnector;

/**
 * <p>Title: TimesheetCalendar</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TimesheetCalendar {

//    private final static String actionId_Load = "FTSLoadPreference";

    private static Integer monthStartDay = 1;

    private TimesheetCalendar() {
    }

    /**
     * ��ȡ�������ڵ����(iStep)�µ���ʼ����(�����µ���ʼ��)
     * @param day Date
     * @param iStep int
     * @return Date[]
     */
    public static Date[] getNextBEMonthDay(Date date, int iStep) {
        GregorianCalendar prmDay = new GregorianCalendar();
        Calendar day = Date2Calendar(date);
        int iYear = day.get(Calendar.YEAR);
        int iMonth = day.get(Calendar.MONTH);
        int iDay = day.get(Calendar.DATE);
        prmDay.set(iYear, iMonth, iDay);

        int iNMonth = iMonth + iStep;
        prmDay.set(Calendar.MONTH, iNMonth);

        return getBEMonthDay(prmDay.getTime());
    }

    /**
     * ��ȡ�������ڵ����(iStep)�µ���ʼ����(�����µ���ʼ��)
     * @param day Calendar
     * @param iStep int
     * @return Calendar[]
     */
    public static Calendar[] getNextBEMonthDay(Calendar day, int iStep) {
        GregorianCalendar prmDay = new GregorianCalendar();
        int iYear = day.get(Calendar.YEAR);
        int iMonth = day.get(Calendar.MONTH);
        int iDay = day.get(Calendar.DATE);
        prmDay.set(iYear, iMonth, iDay);
        int iNMonth = iMonth + iStep;
        prmDay.set(Calendar.MONTH, iNMonth);

        return getBEMonthDay(prmDay);
    }

    /**
     * ��ȡ�������ڵ���ʼ��(�����µ���ʼ��)
     * @param day Date
     * @return Date[]
     */
    public static Date[] getBEMonthDay(Date day) {
        Date[] beDay = new Date[2];
        beDay[0] = beginDayOfMonth(day);
        beDay[1] = endDayOfMonth(day);
        return beDay;
    }

    /**
     * ��ȡ�������ڵ���ʼ��(�����µ���ʼ��)
     * @param day Calendar
     * @return Calendar[]
     */
    public static Calendar[] getBEMonthDay(Calendar day) {
        Calendar[] beDay = new Calendar[2];
        beDay[0] = beginDayOfMonth(day);
        beDay[1] = endDayOfMonth(day);
        return beDay;
    }

    /**
     * ��ȡ�µ���ʼ��(�����µ���ʼ��)
     * @param date Date
     * @return Date
     */
    public static Date beginDayOfMonth(Date date) {
        return beginDayOfMonth(Date2Calendar(date)).getTime();
    }

    /**
     * ��ȡ�µ���ʼ��(�����µ���ʼ��)
     * @param day Calendar
     * @return Calendar
     */
    public static Calendar beginDayOfMonth(Calendar day) {
        Calendar bDay = Calendar.getInstance();
        resetBeginCalendar(bDay);
        int iYear = day.get(Calendar.YEAR);
        int iMonth = day.get(Calendar.MONTH);
        int iDay = day.get(Calendar.DATE);

        if (iDay >= getMonthBeginDay()) {
            bDay.set(iYear, iMonth, getMonthBeginDay());
        } else {
            int iBMonth = iMonth - 1;
            int iBYear = iYear;
            if (iBMonth < 0) {
                iBMonth = 11;
                iBYear = iYear - 1;
            }
            bDay.set(iBYear, iBMonth, getMonthBeginDay());
        }
        return bDay;
    }

    /**
     * ��ȡ�µĽ�����(�����µ���ʼ��)
     * @param date Date
     * @return Date
     */
    public static Date endDayOfMonth(Date date) {
        return endDayOfMonth(Date2Calendar(date)).getTime();
    }

    /**
     * ��ȡ�µĽ�����(�����µ���ʼ��)
     * @param day Calendar
     * @return Calendar
     */
    public static Calendar endDayOfMonth(Calendar day) {
        Calendar bDay = Calendar.getInstance();
        resetEndCalendar(bDay);
        int iYear = day.get(Calendar.YEAR);
        int iMonth = day.get(Calendar.MONTH);
        int iDay = day.get(Calendar.DATE);

        if (getMonthBeginDay() == 1) {
            int iEDay = day.getActualMaximum(Calendar.DATE);
            bDay.set(iYear, iMonth, iEDay);
        } else {
            if (iDay >= getMonthBeginDay()) {
                bDay.set(iYear, iMonth + 1, getMonthBeginDay() - 1);
            } else {
                bDay.set(iYear, iMonth, getMonthBeginDay() - 1);
            }
        }
        return bDay;
    }

    /**
     * ����DateΪ����ĵ�һ��
     * @param date Date
     */
    public static void resetBeginDate(Date date) {
    	if(date == null) return;
        Calendar c = Date2Calendar(date);
        resetBeginCalendar(c);
        date.setTime(c.getTimeInMillis());
    }

    /**
     * ����CalendarΪ����ĵ�һ��
     * @param bDay Calendar
     */
    public static void resetBeginCalendar(Calendar bDay) {
        bDay.set(Calendar.HOUR_OF_DAY, 0);
        bDay.set(Calendar.MINUTE, 0);
        bDay.set(Calendar.SECOND, 0);
        bDay.set(Calendar.MILLISECOND, 0);
    }

    /**
     * ����DateΪ��������һ��
     * @param date Date
     */
    public static void resetEndDate(Date date) {
    	if(date == null) return;
        Calendar c = Date2Calendar(date);
        resetEndCalendar(c);
        date.setTime(c.getTimeInMillis());
    }

    /**
     * ����CalendarΪ��������һ��
     * @param eDay Calendar
     */
    public static void resetEndCalendar(Calendar eDay) {
        eDay.set(Calendar.HOUR_OF_DAY, 23);
        eDay.set(Calendar.MINUTE, 59);
        eDay.set(Calendar.SECOND, 59);
        eDay.set(Calendar.MILLISECOND, 999);
    }

    /**
     * ��Dateת������ͬʱ���Calendar
     * @param date Date
     * @return Calendar
     */
    public static Calendar Date2Calendar(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c;
    }

    /**
     * ��ȡ�¿�ʼ����
     *     ��һ�δ�Server�˻�ȡ��
     * @return int
     */
    protected static int getMonthBeginDay() {
//        if (monthStartDay != null) {
//            return monthStartDay;
//        }
//        //��Server��ȡ
//        InputInfo inputInfo = new InputInfo();
//        inputInfo.setActionId(actionId_Load);
//        ReturnInfo returnInfo = accessData(inputInfo);
//        if (!returnInfo.isError()) {
//            DtoPreference dtoPreference = (DtoPreference)
//                                          returnInfo.getReturnObj(DtoPreference.
//                    DTO);
//            monthStartDay = dtoPreference.getMonthStartDay().intValue();
//        }
        return monthStartDay;
    }

//    /**
//     * ����Server��
//     * @param inputInfo InputInfo
//     * @return ReturnInfo
//     */
//    private static ReturnInfo accessData(InputInfo inputInfo) {
//        TransactionData transData = new TransactionData();
//        transData.setInputInfo(inputInfo);
//
//        NetConnector connector = ConnectFactory.createConnector();
//        connector.accessData(transData);
//
//        return transData.getReturnInfo();
//    }

}
