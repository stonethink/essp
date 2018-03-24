package com.essp.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 * ����Essp��com.enovation.essp.hr.databean.Function�������
 * @author not attributable
 * @version 1.0
 */
public class Function {
        /**
         * ������,�£����ص�ǰ�·��м�������
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
         * ������,�£����ص�ǰ�·ݿ�Խ��������
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
         * �����·�,���ڣ����ظ����ڵ�����1�Ǽ���
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
         * �������ڣ����ظ������ǵڼ�����
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
         * �ж�ĳ�µ����һ���Ƿ����
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
         * �ж�ĳ���Ƿ�����������֮��
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
         * ���ָ�����ڶ�Ӧ����,��
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
