package com.essp.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 * 从老Essp的com.enovation.essp.hr.databean.Function改造过来
 * @author not attributable
 * @version 1.0
 */
public class Function {
        /**
         * 输入年,月，返回当前月份有几个星期
         * @param year int
         * @param month int
         * @return int
         */
        public static int weekNumOfMONTH_min(int year,int month) {
            GregorianCalendar prmDay = new GregorianCalendar();
            int iweeknum;
            prmDay.setFirstDayOfWeek(prmDay.MONDAY);
            prmDay.setMinimalDaysInFirstWeek(7);
            prmDay.set(Calendar.YEAR,year);
            prmDay.set(Calendar.MONTH,month-1);

            prmDay.set(Calendar.DAY_OF_MONTH,prmDay.getActualMaximum(prmDay.DAY_OF_MONTH));
            iweeknum = prmDay.getActualMaximum(Calendar.WEEK_OF_MONTH);
            return iweeknum;
        }

        /**
         * 输入年,月，返回当前月份跨越几个星期
         * @param year int
         * @param month int
         * @return int
         */
        public static int weekNumOfMONTH_max(int year,int month) {
            GregorianCalendar prmDay = new GregorianCalendar();
            int iweeknum;
            prmDay.setFirstDayOfWeek(prmDay.MONDAY);
            prmDay.setMinimalDaysInFirstWeek(7);
            prmDay.set(Calendar.YEAR,year);
            prmDay.set(Calendar.MONTH,month-1);
            iweeknum = prmDay.getActualMaximum(Calendar.WEEK_OF_MONTH);
            return iweeknum;
        }
        /**
         * 输入月份,星期，返回该星期的星期1是几号
         * @param year int
         * @param month int
         * @param week int
         * @return int
         */
        public static int dateOfWeek(int year,int month,int week) {
            GregorianCalendar prmDay = new GregorianCalendar();
            int idate;

            prmDay.setFirstDayOfWeek(prmDay.MONDAY);
            prmDay.setMinimalDaysInFirstWeek(7);
            prmDay.set(Calendar.YEAR,year);
            prmDay.set(Calendar.MONTH,month-1);
            prmDay.set(Calendar.WEEK_OF_MONTH,week);
            prmDay.set(Calendar.DAY_OF_WEEK,prmDay.MONDAY);
            idate=prmDay.get(Calendar.DAY_OF_MONTH);
            return idate;

        }
        /**
         * 输入日期，返回该日期是第几星期
         * @param year int
         * @param month int
         * @param date int
         * @return int
         */
        public static int WeekOfdate(int year,int month,int date) {
            GregorianCalendar prmDay = new GregorianCalendar();
            int idate;
            prmDay.set(Calendar.YEAR,year);
            prmDay.set(Calendar.MONTH,month-1);
            prmDay.set(Calendar.DATE,date);
            idate=prmDay.get(Calendar.WEEK_OF_YEAR);
            return idate;

        }
        /**
         * 判断某月的最后一周是否跨月
         * @param year int
         * @param month int
         * @return boolean
         */
        public static boolean isBrige(int year,int month){
            GregorianCalendar prmDay = new GregorianCalendar();
            prmDay.setFirstDayOfWeek(prmDay.MONDAY);
            prmDay.setMinimalDaysInFirstWeek(7);
            prmDay.set(Calendar.MONTH, month - 1);
            int iendday = prmDay.getActualMaximum(Calendar.DAY_OF_MONTH);
            prmDay.set(Calendar.DAY_OF_MONTH, iendday);
            if (prmDay.get(Calendar.DAY_OF_WEEK) == 1)
                return false;
            else
                return true;
        }
        /**
         * 判断某天是否在两个日期之间
         * @param some Calendar
         * @param start Calendar
         * @param end Calendar
         * @return boolean
         */
        public static boolean isBetween(Calendar some,Calendar start,Calendar end){
            if(some.before(start) || some.after(end))
                return false;
            else
                return true;
        }

        /**
         * 获得指定日期对应的年,月
         * @param date Date
         * @return int
         */
        public static int getYear(Calendar cl){
            return cl.get(Calendar.YEAR);
        }
        public static int getMonth(Calendar cl){
            return cl.get(Calendar.MONTH) + 1;
        }
}
