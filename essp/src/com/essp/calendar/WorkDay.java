package com.essp.calendar;

import java.util.*;


/**
 *
 * <p>Title:工作日DataBean</p>
 * <p>Description: 用于记录日期是否为工作日</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class WorkDay {
    /**
     * 常量，默认状态是2
     */
    public static final String DEFAULT_SETTINGS = "2";

    /**
     * 常量，工作日是1
     */
    public static final String WORKDAY = "1";

    /**
     * 常量，非工作日是0
     */
    public static final String HOLIDAY = "0";

    /**
     * 常量，表明获取所有日期
     */
    public static final String ALLWORKDAY = "3";

    /**
     * 日期
     */
    private Calendar day;

    /**
     * 工作日类型
     * 返回常量，如果是工作日，返回WORKDAY，如果是非工作日返回HOLIDAY
     */
    private String dayType;

    public WorkDay() {
    }

    /**
     * 获取日期
     * @return 日期
     */
    public Calendar getDay() {
        return day;
    }

    /**
     * 获取日期类型
     * @return 返回类型常量
     */
    public String getDayType() {
        return dayType;
    }

    /**
     * 设置日期类型
     * @param Day 日期
     */
    public void setDay(Calendar day) {
        this.day = day;
    }

    /**
     * 设置日期类型
     * @param DayType 日期类型
     */
    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    /**
     * 判断实例是否为工作日
     * @return boolean
     */
    public boolean isWorkDay() {
        if (dayType.equals(WORKDAY)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取默认值
     * 默认值规则是当天是双休时，就是休息日；当天是星期一到星期五时，是工作日
     * @return 默认的工作日设置
     */
    public String getDefualt() {
        if ((this.day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                || (this.day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
            return HOLIDAY;
        } else {
            return WORKDAY;
        }
    }

    /**
     * 设置默认值
     */
    public void setDefualt() {
        if ((this.day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                || (this.day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
            this.dayType = HOLIDAY;
        } else {
            this.dayType = WORKDAY;
        }
    }
}
