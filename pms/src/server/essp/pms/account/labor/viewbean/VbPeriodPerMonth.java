package server.essp.pms.account.labor.viewbean;

import java.util.*;

/**
 * 区间划分的每月
 * @author not attributable
 * @version 1.0
 */
public class VbPeriodPerMonth {
        /**
         * 每月的名称
         */
        public static final String MONTH_ARRAY [] ={"0","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        /**
         * 显示页面每周的td宽度
         */
        public static final int PER_WEEK_WIDTH = 20;
        /**
         * 哪一年
         */
        private int year;
        /**
         * 哪一月
         */
        private int month;
        /**
         * 该月包含的星期,VbPeriodPerWeek
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
