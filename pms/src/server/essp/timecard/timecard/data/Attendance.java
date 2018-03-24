package server.essp.timecard.timecard.data;

import com.essp.calendar.*;
import com.wits.util.*;

import server.essp.common.calendar.logic.*;
import server.essp.timecard.worktime.data.*;

import java.sql.*;

import java.util.*;


/**
 *
 * <p>Title: Ա�����ڼ�¼</p>
 * <p>Description: Ա�����ڼ�¼�����ڼ�¼���ݿ���һ��Ա���Ĳ��ڼ�¼��������ĳ��ʱ������������ʱ�������ڵģ��ü�¼��Ч��ֹʱ�估����ʱ��</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class Attendance {
    //һ��8��Сʱ
    public static final float DAY_MAX_HOURS  = 8;
    public static final float DAY_MINI_HOURS = 0.5f;

    //����һ��Ŀ�ʼʱ����0:0��һ��Ľ���ʱ����23:59
    public static final int DAY_START_MINUTE  = 0;
    public static final int DAY_START_SECOND  = 0;
    public static final int DAY_FINISH_MINUTE = 23;
    public static final int DAY_FINISH_SECOND = 30;

    /**
     * Ա�����ڻ�������
     */
    /**
     * Ա������
     */
    String empID = "";

    /**
     * ��Ŀ����
     */
    int projID;

    /**
     * ��������
     */
    String attendTYPE = "";

    /**
     * ���ڼ�¼�е�ʱ������
     */
    /**
     * ���ڼ�¼�е���ʱ��
     */
    Timestamp dataStart;

    /**
     * ���ڼ�¼�е�ֹʱ��
     */
    Timestamp dataFinish;

    /**
     * ���ڼ�¼�еĲ���ʱ��
     */
    float dataTime;

    /**
     * �����ڷ�Χ�ڣ�ʵ�ʲ�������
     */
    /**
     * �����ڷ�Χ�ڵ���ʱ��
     */
    Timestamp weeklyStart;

    /**
     * �����ڷ�Χ�ڵ���ʱ��
     */
    Timestamp weeklyFinish;

    /**
     * �����ڷ�Χ�ڵĲ���ʱ��
     */
    float weeklyTime;

    /**
     * ������ֹ
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
     * �������ݵĿ�ʼ����ʱ�䣬��������ֹ����ȡ��¼�ڱ������ڵ���ֹ
     * @param DataStart ������ʱ��
     * @param DataEnd ���ݽ���ʱ��
     * @param RangeStart ������ʱ��
     * @param RaneEnd ���ڽ���ʱ��
     * @return Attendance
     */
    public static Attendance getInstance(Timestamp dataStart,
                                         Timestamp dataEnd,
                                         Calendar  rangeStart,
                                         Calendar  rangeEnd) {
        Attendance atten = new Attendance();

        //1.��ʼ��������ʼ��
        atten.setDataStart(dataStart);
        atten.setDataFinish(dataEnd);

        atten.setRangeStart(rangeStart);
        atten.setRangeFinish(rangeEnd);

        //2.���ݷ�Χ����������ֹ
        atten.setRange(rangeStart, rangeEnd);

        if (atten.getWeeklyStart() == null) {
            return atten;
        }

        //4.��������ֹ�������յıȽϣ���������ֹ
        return atten;
    }

    public void getWeeklyTime(String[][] workItems) throws Exception {
        if (this.getWeeklyStart() == null) {
            return;
        }

        boolean isStart = false;

        //1.���ݵ�ǰ�������ڻ�ȡ��Χ�����й�����
        CalendarLogic cal    = new CalendarLogic();
        Calendar     cStart = GregorianCalendar.getInstance();
        cStart.setTimeInMillis(this.weeklyStart.getTime());

        Calendar cEnd = GregorianCalendar.getInstance();
        cEnd.setTimeInMillis(this.weeklyFinish.getTime());

        WorkRange wkr = cal.getWorkRange(cStart, cEnd);
        WorkDay[] wds = wkr.getSub(cStart, cEnd, WorkDay.HOLIDAY);

        this.weeklyTime = this.getActiveAttendHours(workItems);

        if (wds != null) {
            //����Ϣ�����ظ����������ȥ��
            for (int i = 0; i < wds.length; i++) {
                //�ж���ֹ���Ƿ�����Ϣ����
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

            //������ڵ��վ�����Ϣ�գ��˲����0
            if (isStart && (comDate.getBetweenDays(cStart, cEnd) == 1)) {
                this.weeklyTime = 0;
            }
        }
    }

    /**
     * ����������ֹ�գ����ò����������ڵ���ֹ<br>
     * �������������ֹ�ղ���������ֹ��Χ�ڣ�������������ֹ
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

        //������ʼʱ�䲻�����ڷ�Χ��
        if ((comDate.getBetweenDays(dataEnd, start) >= 0)
                || (comDate.getBetweenDays(dataStart, end) <= 0)) {
            return;
        }

        //����ܿ�ʼ�������ݿ�ʼ��֮ǰ�����ô˲��ڵ����ڿ�ʼΪ���ݿ�ʼ��
        //��������Ϊ���ڿ�ʼ��
        if (comDate.getBetweenDays(start, dataStart) >= 1) {
            this.weeklyStart = this.dataStart;
        } else {
            //����Ϊ���ڿ�ʼ��ʱ����Ҫ��ʼ��Сʱ���룬Ϊ0:0
            this.weeklyStart = new Timestamp(start.getTimeInMillis());
            this.weeklyStart.setHours(DAY_START_MINUTE);
            this.weeklyStart.setHours(DAY_START_SECOND);
        }

        if (comDate.getBetweenDays(dataEnd, end) >= 1) {
            this.weeklyFinish = this.dataFinish;
        } else {
            //����Ϊ���ڿ�ʼ��ʱ����Ҫ��ʼ��Сʱ���룬Ϊ23:59
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
     * ��ʱ��ֽ���빤��ʱ�����ý��бȽ�
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
     * ������ʱ��ֽ����������
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
        //�����жϷ��������ڵ������������ʱ��ȡint�᷵��0��������ǰ����ж���������ĸ�ǰ�ĸ���
        //�˷���ֻ�����ڴ˳�����Ҫ���ж�������û�г�����ʱ���ֹʱ�仹��Ŀ���
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
