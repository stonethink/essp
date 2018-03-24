package com.essp.calendar;

import java.util.*;


/**
 *
 * <p>Title:������DataBean</p>
 * <p>Description: ���ڼ�¼�����Ƿ�Ϊ������</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class WorkDay {
    /**
     * ������Ĭ��״̬��2
     */
    public static final String DEFAULT_SETTINGS = "2";

    /**
     * ��������������1
     */
    public static final String WORKDAY = "1";

    /**
     * �������ǹ�������0
     */
    public static final String HOLIDAY = "0";

    /**
     * ������������ȡ��������
     */
    public static final String ALLWORKDAY = "3";

    /**
     * ����
     */
    private Calendar day;

    /**
     * ����������
     * ���س���������ǹ����գ�����WORKDAY������Ƿǹ����շ���HOLIDAY
     */
    private String dayType;

    public WorkDay() {
    }

    /**
     * ��ȡ����
     * @return ����
     */
    public Calendar getDay() {
        return day;
    }

    /**
     * ��ȡ��������
     * @return �������ͳ���
     */
    public String getDayType() {
        return dayType;
    }

    /**
     * ������������
     * @param Day ����
     */
    public void setDay(Calendar day) {
        this.day = day;
    }

    /**
     * ������������
     * @param DayType ��������
     */
    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    /**
     * �ж�ʵ���Ƿ�Ϊ������
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
     * ��ȡĬ��ֵ
     * Ĭ��ֵ�����ǵ�����˫��ʱ��������Ϣ�գ�����������һ��������ʱ���ǹ�����
     * @return Ĭ�ϵĹ���������
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
     * ����Ĭ��ֵ
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
