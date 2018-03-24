package server.essp.timecard.timecard.data;

import com.essp.calendar.*;
import com.wits.util.*;

import server.essp.common.calendar.logic.*;
import server.essp.timecard.worktime.data.*;

import java.sql.*;

import java.util.*;


/**
 *
 * <p>Title: 员工差勤记录</p>
 * <p>Description: 员工差勤记录，由于记录数据库中一笔员工的差勤记录，并依据某个时间区间记算出此时间区间内的，该记录有效起止时间及差勤时间</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class Attendance {
    //一天8个小时
    public static final float DAY_MAX_HOURS  = 8;
    public static final float DAY_MINI_HOURS = 0.5f;

    //设置一天的开始时间是0:0，一天的结束时间是23:59
    public static final int DAY_START_MINUTE  = 0;
    public static final int DAY_START_SECOND  = 0;
    public static final int DAY_FINISH_MINUTE = 23;
    public static final int DAY_FINISH_SECOND = 30;

    /**
     * 员工猜勤基本数据
     */
    /**
     * 员工代号
     */
    String empID = "";

    /**
     * 项目代号
     */
    int projID;

    /**
     * 差勤类型
     */
    String attendTYPE = "";

    /**
     * 差勤记录中的时差数据
     */
    /**
     * 差勤记录中的起时间
     */
    Timestamp dataStart;

    /**
     * 差勤记录中的止时间
     */
    Timestamp dataFinish;

    /**
     * 差勤记录中的差勤时间
     */
    float dataTime;

    /**
     * 在周期范围内，实际差勤数据
     */
    /**
     * 在周期范围内的起时间
     */
    Timestamp weeklyStart;

    /**
     * 在周期范围内的起时间
     */
    Timestamp weeklyFinish;

    /**
     * 在周期范围内的差勤时间
     */
    float weeklyTime;

    /**
     * 周期起止
     */
    Calendar rangeStart;
    Calendar rangeFinish;

    public Attendance() {
    }

    public void setRangeStart(Calendar rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Calendar getRangeStart() {
        return rangeStart;
    }

    public void setRangeFinish(Calendar rangeFinish) {
        this.rangeFinish = rangeFinish;
    }

    public Calendar getRangeFinish() {
        return rangeFinish;
    }

    public void setDataStart(Timestamp dataStart) {
        this.dataStart = dataStart;
    }

    public Timestamp getDataStart() {
        return dataStart;
    }

    public void setWeeklyFinish(Timestamp weeklyFinish) {
        this.weeklyFinish = weeklyFinish;
    }

    public Timestamp getWeeklyFinish() {
        return weeklyFinish;
    }

    public void setWeeklyStart(Timestamp weelyStart) {
        this.weeklyStart = weelyStart;
    }

    public Timestamp getWeeklyStart() {
        return weeklyStart;
    }

    public void setDataFinish(Timestamp dataFinish) {
        this.dataFinish = dataFinish;
    }

    public Timestamp getDataFinish() {
        return dataFinish;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public void setProjID(int projID) {
        this.projID = projID;
    }

    public float getWeeklyTime() {
        return weeklyTime;
    }

    public void setWeeklyTime(float weeklyTime) {
        this.weeklyTime = weeklyTime;
    }

    public float getDataTime() {
        return dataTime;
    }

    public void setDataTime(float dataTime) {
        this.dataTime = dataTime;
    }

    public String getAttendType() {
        return attendTYPE;
    }

    public void setAttendType(String attendType) {
        this.attendTYPE = attendType;
    }

    public int getProjID() {
        return projID;
    }

    /**
     * 依据数据的开始结束时间，及周期起止，获取记录在本周期内的起止
     * @param DataStart 数据起时间
     * @param DataEnd 数据结束时间
     * @param RangeStart 周期起时间
     * @param RaneEnd 周期结束时间
     * @return Attendance
     */
    public static Attendance getInstance(Timestamp dataStart,
                                         Timestamp dataEnd,
                                         Calendar  rangeStart,
                                         Calendar  rangeEnd) {
        Attendance atten = new Attendance();

        //1.初始化数据起始日
        atten.setDataStart(dataStart);
        atten.setDataFinish(dataEnd);

        atten.setRangeStart(rangeStart);
        atten.setRangeFinish(rangeEnd);

        //2.依据范围设置周期起止
        atten.setRange(rangeStart, rangeEnd);

        if (atten.getWeeklyStart() == null) {
            return atten;
        }

        //4.依据周起止及工作日的比较，重置周起止
        return atten;
    }

    public void getWeeklyTime(String[][] workItems) throws Exception {
        if (this.getWeeklyStart() == null) {
            return;
        }

        boolean isStart = false;

        //1.依据当前差勤周期获取范围内所有工作日
        CalendarLogic cal    = new CalendarLogic();
        Calendar     cStart = GregorianCalendar.getInstance();
        cStart.setTimeInMillis(this.weeklyStart.getTime());

        Calendar cEnd = GregorianCalendar.getInstance();
        cEnd.setTimeInMillis(this.weeklyFinish.getTime());

        WorkRange wkr = cal.getWorkRange(cStart, cEnd);
        WorkDay[] wds = wkr.getSub(cStart, cEnd, WorkDay.HOLIDAY);

        this.weeklyTime = this.getActiveAttendHours(workItems);

        if (wds != null) {
            //将休息日中重复记算的天数去掉
            for (int i = 0; i < wds.length; i++) {
                //判断起止日是否在休息日中
                if (comDate.getBetweenDays(wds[i].getDay(), cStart) == 1) {
                    this.weeklyTime = this.weeklyTime
                                       - getDayHours(this.weeklyStart,
                                                     workItems);
                    isStart          = true;
                } else if (comDate.getBetweenDays(wds[i].getDay(), cEnd) == 1) {
                    this.weeklyTime = this.weeklyTime
                                       - (DAY_MAX_HOURS
                                       - getDayHours(this.weeklyFinish,
                                                     workItems));
                    isStart          = true;
                } else {
                    this.weeklyTime = this.weeklyTime - DAY_MAX_HOURS;
                }
            }

            //如果差勤当日就是休息日，此差就是0
            if (isStart && (comDate.getBetweenDays(cStart, cEnd) == 1)) {
                this.weeklyTime = 0;
            }
        }
    }

    /**
     * 依据周期起止日，设置差勤在周期内的起止<br>
     * 如果差勤数据起止日不在周期起止范围内，不设置周期起止
     * @param Start Calendar
     * @param End Calendar
     */
    public void setRange(Calendar start,
                         Calendar end) {
        Timestamp tms       = (Timestamp) this.dataStart.clone();
        Timestamp tme       = (Timestamp) this.dataFinish.clone();
        Calendar  dataStart = GregorianCalendar.getInstance();
        dataStart.setTimeInMillis(tms.getTime());

        Calendar dataEnd = GregorianCalendar.getInstance();
        dataEnd.setTimeInMillis(tme.getTime());

        //数据起始时间不在周期范围内
        if ((comDate.getBetweenDays(dataEnd, start) >= 0)
                || (comDate.getBetweenDays(dataStart, end) <= 0)) {
            return;
        }

        //如果周开始日在数据开始日之前，设置此差勤的周期开始为数据开始日
        //否则设置为周期开始日
        if (comDate.getBetweenDays(start, dataStart) >= 1) {
            this.weeklyStart = this.dataStart;
        } else {
            //设置为周期开始日时，需要初始化小时和秒，为0:0
            this.weeklyStart = new Timestamp(start.getTimeInMillis());
            this.weeklyStart.setHours(DAY_START_MINUTE);
            this.weeklyStart.setHours(DAY_START_SECOND);
        }

        if (comDate.getBetweenDays(dataEnd, end) >= 1) {
            this.weeklyFinish = this.dataFinish;
        } else {
            //设置为周期开始日时，需要初始化小时和秒，为23:59
            this.weeklyFinish = new Timestamp(end.getTimeInMillis());
            this.weeklyFinish.setHours(DAY_FINISH_MINUTE);
            this.weeklyFinish.setHours(DAY_FINISH_SECOND);
        }
    }

    public float getActiveAttendHours(String[][] vDD) {
        float fStart  = getDayHours(this.weeklyStart, vDD);
        float fend    = getDayHours(this.weeklyFinish, vDD);
        int   iCase   = getDaysNumber(this.weeklyStart, this.weeklyFinish);
        float iNumber;

        if (getDayBetweenWorks() >= 0) {
            iNumber = (fStart + (iCase * DAY_MAX_HOURS)) - fend;
        } else if (iCase >= 0) {
            iNumber = fStart + (iCase * DAY_MAX_HOURS) + (DAY_MAX_HOURS - fend);
        } else {
            iNumber = -1;
        }

        return iNumber;
    }

    /**
     * 将时间分解后，与工作时间设置进行比较
     * @param tm Timestamp
     * @param vDD String[][]
     * @return float
     */
    public static float getDayHours(java.sql.Timestamp tm,
                                    String[][]         vDD) {
        int[]    inTimes = timeStempToInts(tm);
        WorkTime wkt = new WorkTime(vDD);

        return wkt.getTimeHours(inTimes);
    }

    /**
     * 将传入时间分解成整形数组
     * @param tm Timestamp
     * @return int[]
     */
    public static int[] timeStempToInts(java.sql.Timestamp tm) {
        int[] inTimes = new int[2];
        inTimes[0] = tm.getHours();
        inTimes[1] = tm.getMinutes();

        return inTimes;
    }

    public static int getDaysNumber(java.sql.Timestamp start,
                                    java.sql.Timestamp end) {
        //日期判断方法，由于当两个数很相近时，取int会返回0，所以在前面就判断这个数是哪个前哪个后。
        //此方法只能用于此程序，主要是判断日期有没有出现起时间比止时间还晚的可能
        long lRet = end.getTime() - start.getTime();

        if (lRet < 0) {
            return -1;
        }

        int iSRet = (int) (lRet / (24 * 60 * 60 * 1000));

        return iSRet;
    }

    public float getDayBetweenWorks() {
        return WorkTimeItem.getTimeHours(timeStempToInts(this.weeklyStart),
                                         timeStempToInts(this.weeklyFinish));
    }

//    public static void main(String[] args) {
//        Timestamp                             tmfirst = new Timestamp(105, 3,
//                                                                      10, 13,
//                                                                      0, 0, 0);
//        Timestamp                             tmend = new Timestamp(105, 4, 1,
//                                                                    13, 30, 0, 0);
//
//        Calendar                              cStart = GregorianCalendar
//                                                       .getInstance();
//        Calendar                              cEnd   = GregorianCalendar
//                                                       .getInstance();
//
//        server.essp.common.timecard.WeekRange wrg = new server.essp.common.timecard.WeekRange();
//        server.essp.common.timecard.DateItem  dit = wrg.getRange(cStart);
//
//        String[][]                            WorkTimes = {
//                                                              { "9:00", "11:00" },
//                                                              { "12:00", "15:00" },
//                                                              { "16:00", "19:00" }
//                                                          };
//
//        Attendance                            atten = Attendance.getInstance(tmfirst,
//                                                                             tmend,
//                                                                             dit
//                                                                             .getStart(),
//                                                                             dit
//                                                                             .getEnd());
//
//        try {
//            atten.getWEEKLY_TIME(WorkTimes);
//        } catch (Exception ex) {
//        }
//
//        //        atten.setWEEKLY_START(tmfirst);
//        //        atten.setWEEKLY_FINISH(tmend);
//        float fRet = atten.getWEEKLY_TIME();
//        System.out.println("-------------Test Start--------");
//        System.out.println("This is :" + fRet);
//        System.out.println("-------------Test End----------");
//
//        //        Timestemp
//    }
}
