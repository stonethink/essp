package c2s.essp.common.calendar;

import java.util.Calendar;

public final class WorkCalendarConstant {
    public static final int SPLIT_WEEK_BY_MONTH_END = 1; //是否将跨月份的星期分隔开,1表示需要分隔
    public static final int UNSPLIT_WEEK_BY_MONTH_END = 0; //是否将跨月份的星期分隔开,1表示需要分隔

    public static final String WORKDAY = "1"; //常量，工作日是1
    public static final String HOLIDAY = "0"; //常量，非工作日是0
    public static final float DAILY_WORK_HOUR = 8.00f; //每日的工作小时
    public static final float WEEKLY_WORK_DAY = 5.00f; //每周的工作天数
    public static final float WEEKLY_WORK_HOUR = WEEKLY_WORK_DAY *
                                                 DAILY_WORK_HOUR; //每周的工作小时
    public static final float MONTHLY_WORK_DAY = 21.00f; //每月的工作天数
    public static final float MONTHLY_WORK_HOUR = MONTHLY_WORK_DAY *
                                                  DAILY_WORK_HOUR; //每月的工作小时

    public static final int WEEK_BEGIN_DAY = Calendar.SATURDAY; //星期的起始日
    public static final int MONTH_BEGIN_DAY = 26; //月份的起始日
    public static final int YEAR_BEGIN_MONTH = 1; //年份的起始月

    public static final String TIME_HHMM_REG = "[0-2][0-9]:[0-5][0-9]"; //时间HH:MM的正则表达式
    public static final String DEFAULT_TIME_DELIM = ":";//时间分隔符
}
