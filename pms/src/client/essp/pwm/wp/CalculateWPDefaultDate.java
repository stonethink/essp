package client.essp.pwm.wp;

import java.util.Date;
import c2s.essp.common.calendar.WorkCalendarConstant;
import java.util.Calendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import com.wits.util.comDate;

/**
 * <p>Title:根据WBS或Activity的时间计算Activity或WP的默认时间 </p>
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
     * 加一个WP时，计算其默认的计划开始和结束时间
     * 若Activity还没开始，取Activity的前offset个工作周的起止做为WP的计划起止
     * 若Activity已结束，取Activity的后offset个工作周的起止做为WP的计划起止
     * 若Activity正在进行，取当前周做为WP的计划起止
     * @param actStart Date Activity的开始
     * @param actFinish Date Activity的结束时间
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
        if (actStart != null && today.compareTo(actStart) < 0) { //Activity之前默认第一周
            cStart.setTime(actStart);
            cStart = wc.getNextWorkDay(cStart, 0);
            cOffSet = wc.getNextDay(cStart, (offset - 1) * 7);
            cFinish = wc.endDayOfWeek(cOffSet,
                                      WorkCalendarConstant.
                                      SPLIT_WEEK_BY_MONTH_END);

        } else if (actFinish != null && today.compareTo(actFinish) > 0) { //Activity之后默认最后一周
            cFinish.setTime(actFinish);
            cOffSet = wc.getNextDay(cFinish, -(offset - 1) * 7);
            cStart = wc.beginDayOfWeek(cOffSet, 0);
        } else { //Activity期间默认本周
            cStart.setTime(today);
            cStart = wc.getNextWorkDay(cStart, 0);
            cOffSet = wc.getNextDay(cStart, (offset - 1) * 7);
            cFinish = wc.endDayOfWeek(cOffSet,
                                      WorkCalendarConstant.
                                      SPLIT_WEEK_BY_MONTH_END);
        }
        //确保WP的起止时间在Activity之内
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
