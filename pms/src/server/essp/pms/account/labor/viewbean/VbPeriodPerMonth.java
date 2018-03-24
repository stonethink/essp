package server.essp.pms.account.labor.viewbean;

import java.util.*;

/**
 * ���仮�ֵ�ÿ��
 * @author not attributable
 * @version 1.0
 */
public class VbPeriodPerMonth {
        /**
         * ÿ�µ�����
         */
        public static final String MONTH_ARRAY [] ={"0","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        /**
         * ��ʾҳ��ÿ�ܵ�td���
         */
        public static final int PER_WEEK_WIDTH = 20;
        /**
         * ��һ��
         */
        private int year;
        /**
         * ��һ��
         */
        private int month;
        /**
         * ���°���������,VbPeriodPerWeek
         */
        private List weeks = new LinkedList();


    public int getMonth() {
        return month;
    }

    public List getWeeks() {
        return weeks;
    }

    public int getYear() {
        return year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setWeeks(List weeks) {
        this.weeks = weeks;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeeksNum(){
        return weeks.size();
    }

    public String getMonthName(){
        return MONTH_ARRAY[month];
    }

    public int getWidth(){
        return weeks.size() * PER_WEEK_WIDTH;
    }
}
