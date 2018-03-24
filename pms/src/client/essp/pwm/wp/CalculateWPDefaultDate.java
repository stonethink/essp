package client.essp.pwm.wp;

import java.util.Date;
import c2s.essp.common.calendar.WorkCalendarConstant;
import java.util.Calendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import com.wits.util.comDate;

/**
 * <p>Title:����WBS��Activity��ʱ�����Activity��WP��Ĭ��ʱ�� </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Robin
 * @version 1.0
 */
public class CalculateWPDefaultDate {
    /**
     *
     * @param actStart Date
     * @param actFinish Date
     * @return Date[]
     */
    public static Date[] calculateDefaultPlanDate(Date actStart, Date actFinish) {
        return calculateDefaultPlanDate(actStart, actFinish, 1);
    }
    /**
     * ��һ��WPʱ��������Ĭ�ϵļƻ���ʼ�ͽ���ʱ��
     * ��Activity��û��ʼ��ȡActivity��ǰoffset�������ܵ���ֹ��ΪWP�ļƻ���ֹ
     * ��Activity�ѽ�����ȡActivity�ĺ�offset�������ܵ���ֹ��ΪWP�ļƻ���ֹ
     * ��Activity���ڽ��У�ȡ��ǰ����ΪWP�ļƻ���ֹ
     * @param actStart Date Activity�Ŀ�ʼ
     * @param actFinish Date Activity�Ľ���ʱ��
     * @param offset int
     * @return Date[]
     */
    public static Date[] calculateDefaultPlanDate(Date actStart, Date actFinish,
                                                  int offset) {
        Date[] result = new Date[2];
        Date today = new Date();
        Calendar cStart = Calendar.getInstance();
        Calendar cFinish = Calendar.getInstance();
        Calendar cOffSet = Calendar.getInstance();
        WorkCalendar wc = WrokCalendarFactory.clientCreate();
        if (actStart != null && today.compareTo(actStart) < 0) { //Activity֮ǰĬ�ϵ�һ��
            cStart.setTime(actStart);
            cStart = wc.getNextWorkDay(cStart, 0);
            cOffSet = wc.getNextDay(cStart, (offset - 1) * 7);
            cFinish = wc.endDayOfWeek(cOffSet,
                                      WorkCalendarConstant.
                                      SPLIT_WEEK_BY_MONTH_END);

        } else if (actFinish != null && today.compareTo(actFinish) > 0) { //Activity֮��Ĭ�����һ��
            cFinish.setTime(actFinish);
            cOffSet = wc.getNextDay(cFinish, -(offset - 1) * 7);
            cStart = wc.beginDayOfWeek(cOffSet, 0);
        } else { //Activity�ڼ�Ĭ�ϱ���
            cStart.setTime(today);
            cStart = wc.getNextWorkDay(cStart, 0);
            cOffSet = wc.getNextDay(cStart, (offset - 1) * 7);
            cFinish = wc.endDayOfWeek(cOffSet,
                                      WorkCalendarConstant.
                                      SPLIT_WEEK_BY_MONTH_END);
        }
        //ȷ��WP����ֹʱ����Activity֮��
        if (actStart != null) {
            result[0] = actStart.compareTo(cStart.getTime()) > 0 ? actStart :
                        cStart.getTime();
        }
        if (actFinish != null) {
            result[1] = actFinish.compareTo(cFinish.getTime()) < 0 ? actFinish :
                        cFinish.getTime();
        }

        if (result[0] == null && result[1] != null) {
            result[0] = result[1];
        }
        if (result[1] == null && result[0] != null) {
            result[1] = result[0];
        }
        if (result[1] == null && result[0] == null) {
            String todayStr = comDate.dateToString(today, "yyyyMMdd");
            today = comDate.toDate(todayStr);
            result[1] = today;
            result[0] = today;
        }

        return result;
    }
}
