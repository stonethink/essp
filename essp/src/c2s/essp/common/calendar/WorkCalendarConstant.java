package c2s.essp.common.calendar;

import java.util.Calendar;

public final class WorkCalendarConstant {
    public static final int SPLIT_WEEK_BY_MONTH_END = 1; //�Ƿ񽫿��·ݵ����ڷָ���,1��ʾ��Ҫ�ָ�
    public static final int UNSPLIT_WEEK_BY_MONTH_END = 0; //�Ƿ񽫿��·ݵ����ڷָ���,1��ʾ��Ҫ�ָ�

    public static final String WORKDAY = "1"; //��������������1
    public static final String HOLIDAY = "0"; //�������ǹ�������0
    public static final float DAILY_WORK_HOUR = 8.00f; //ÿ�յĹ���Сʱ
    public static final float WEEKLY_WORK_DAY = 5.00f; //ÿ�ܵĹ�������
    public static final float WEEKLY_WORK_HOUR = WEEKLY_WORK_DAY *
                                                 DAILY_WORK_HOUR; //ÿ�ܵĹ���Сʱ
    public static final float MONTHLY_WORK_DAY = 21.00f; //ÿ�µĹ�������
    public static final float MONTHLY_WORK_HOUR = MONTHLY_WORK_DAY *
                                                  DAILY_WORK_HOUR; //ÿ�µĹ���Сʱ

    public static final int WEEK_BEGIN_DAY = Calendar.SATURDAY; //���ڵ���ʼ��
    public static final int MONTH_BEGIN_DAY = 26; //�·ݵ���ʼ��
    public static final int YEAR_BEGIN_MONTH = 1; //��ݵ���ʼ��

    public static final String TIME_HHMM_REG = "[0-2][0-9]:[0-5][0-9]"; //ʱ��HH:MM��������ʽ
    public static final String DEFAULT_TIME_DELIM = ":";//ʱ��ָ���
}
