package server.essp.common.calendar.logic;

import c2s.dto.*;
import c2s.essp.common.calendar.*;

import com.essp.calendar.*;
import com.wits.util.*;

import essp.tables.*;

import org.apache.log4j.*;

import server.framework.hibernate.*;

import java.util.*;


/**
 *
 * <p>Title: 日历设置逻辑类</p>
 * <p>Description: 用于获取工作日历，可以按时间区间，也可以按年获取</p>
 * 工作日历一但从数据库获取后，将采用静态方式放于内存中，只有重新设置数据或服务器重起时，才从数据库中重新获取。
 * 这样加快公用程序访问速度，并占用少量内存
 * 存放形式利用Hashtable采用按年放置，每年一笔。
 * 如果某年数据没有，将从数据库获取，如果数据库也没有，将获取默认值后，再写回数据库中，从数据库获取后，更新Hashtable
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class CalendarLogic {
    /**
     * 工作日历，以年做为key放置其中，每年一笔
     */
    private static Hashtable workCalendars;
    Category                 log = Category.getInstance("server");
    HBComAccess              hbAccess = new HBComAccess();

    public CalendarLogic() {
        startMemo();
    }

    public Hashtable getWorkCalendars() {
        return workCalendars;
    }

    /**
     * 通过服务传入data，返回信息
     * @param data TransactionData
     */
    public void get(TransactionData data) {
        //CalendarProc cp = new CalendarProc();
        Integer tmpYear = (Integer) data.getInputInfo().getInputObj("WorkYear");
        int     year = tmpYear.intValue();
        String  sRet = null;

        try {
            hbAccess.followTx();
            sRet = getYearWorkDays(year);

            WorkRange wk = WorkRange.getInstance(year, sRet); //加装数据
            data.getReturnInfo().setReturnObj("WorkDayString", wk.toString());
            data.getReturnInfo().setError(false);
            hbAccess.commit();
        } catch (Exception ex) {
            try {
                hbAccess.rollback();
            } catch (Exception ex1) {
                log.error(ex);
                data.getReturnInfo().setError(ex);
            }

            log.error(ex);
            data.getReturnInfo().setError(ex);
        }
    }

    //保存工作日设置数据
    public void save(TransactionData data) {
        DtoTcWt   tw       = (DtoTcWt) data.getInputInfo().getInputObj("DtoTcWt");
        Integer   tmpYear  = tw.getWtsYear();
        int       year     = tmpYear.intValue();
        String    workdays = tw.getWtsDays();
        WorkRange wk       = WorkRange.getInstance(workdays); //解包数据

        try {
            hbAccess.followTx();
            setYearWorkDays(year, wk.getWorkDaysZone());
            data.getReturnInfo().setError(false);
            hbAccess.commit();
        } catch (Exception ex) {
            try {
                hbAccess.rollback();
            } catch (Exception ex1) {
                log.error(ex);
                data.getReturnInfo().setError(ex);
            }

            log.error(ex);
            data.getReturnInfo().setError(ex);
        }

        //data.getData_Node().setFieldValue( "MonthID", new Integer(wk.getRange())) ;
    }

    /**
     * 按年获取工作日字符串，只包含0100001000...字符串
     * @param Year 年份
     * @return 工作日字符串
     */
    public String getYearWorkDays(int year) throws Exception {
        Integer tmpYear = new Integer(year);
        boolean     bRet = false;
        String  sRet = "";

        //判断内存中是否有数据，如果没有，则读取数据库中的数据，如果有直接返回内存中数据
        if (workCalendars.get(tmpYear) == null) {
            //获取数据库中的数据
            TcWt wt = null;
            try {
              wt = getWorkDaysData(tmpYear);
              bRet = true;
            } catch (Exception ex) {
              bRet = false;
            }

            if (!bRet) { //如果没有记录，将获取默认值，并组织成字符串写回数据库

                Calendar start = Calendar.getInstance();
                start.set(year, 0, 1); //设置当前年的1月1号

                Calendar end = Calendar.getInstance();
                end.set(year, 11, 31); //设置当年年的最后一天
                sRet = getDefalut(start, end);

                TcWt wt2 = new TcWt();
                wt2.setWtsYear(tmpYear);
                wt2.setWtsDays(sRet);

                insertWorkDaysData(wt2);
                workCalendars.put(tmpYear, sRet);
            } else { //有记录，将记录的数据返回
                sRet = wt.getWtsDays();
                workCalendars.put(tmpYear, sRet);
            }
        } else {
            sRet = workCalendars.get(tmpYear).toString();
        }

        return sRet;
    }

    /**
     * 依据条件获取工作日数组，采用字符串方式，日期用yyyy-mm-dd组织
     * @param Start 起始日
     * @param End 结束日
     * @param Flag 日期种类标志，与WorkDay中设置一样
     * @return String[]
     */
    public String[] getWorkDays(Calendar start,
                                Calendar end,
                                String   flag) throws Exception {
        WorkRange wkr = getWorkRange(start, end);

        WorkDay[] wks = wkr.getSub(start, end, flag);

        if (wks == null) {
            return null;
        }

        String[] retStrs = new String[wks.length];

        for (int i = 0; i < wks.length; i++) {
            retStrs[i] = StringUtil.Calendar2String(wks[i].getDay());
        }

        return retStrs;
    }

    /**
     * 依据日期范围实例化WorkRange，如果日期范围跨年，将每年都组织在一起
     * @param Start 起日
     * @param End 止日
     * @return WorkRange
     */
    public WorkRange getWorkRange(Calendar start,
                                  Calendar end) throws Exception {
        String strTmp  = "";
        int    iNumber = (int) ((end.getTimeInMillis()
                         - start.getTimeInMillis()) / (24 * 60 * 60 * 1000));

        if (iNumber < 0) {
            return null;
        }

        //获取实际相差年数
        int iYearsCount = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
        int iStartYear = start.get(Calendar.YEAR);

        for (int i = 0; i <= iYearsCount; i++) {
            iStartYear = iStartYear + i;
            strTmp     = strTmp + getYearWorkDays(iStartYear);
        }

        //设置最早日为当年第一天
        Calendar cStart = (Calendar) start.clone();
        cStart.set(Calendar.MONTH, 0);
        cStart.set(Calendar.DATE, 1);

        //设置最晚日为当年最后一天，便 于实例化数据s
        Calendar cEnd = (Calendar) end.clone();
        cEnd.set(Calendar.MONTH, 11);
        cEnd.set(Calendar.DATE, 31);

        WorkRange wkr = WorkRange.getInstance(cStart, cEnd, strTmp);

        return wkr;
    }

    //按给定个数取
    public String[] getWorkDays(Calendar start,
                                int      seq,
                                String   flag) throws Exception {
        String[] retStrs = new String[seq];
        int      iRet = 0;
        int      i    = 0;

        while (iRet < seq) {
            Calendar tmpStart = (Calendar) start.clone();
            tmpStart.add(Calendar.DATE, i);

            String[] tmpStr = getWorkDays(tmpStart, tmpStart, flag);

            if (tmpStr != null) {
                retStrs[iRet] = tmpStr[0];
                iRet++;
            }

            i++;
        }

        return retStrs;
    }

    //按给定范围及星期数组，给出符合条件的数据
    public String[] getWorkDays(Calendar start,
                                Calendar end,
                                String   flag,
                                int[]    weeks) throws Exception {
        WorkRange wkr = getWorkRange(start, end);

        WorkDay[] wks = wkr.getSub(start, end, flag, weeks);

        if (wks == null) {
            return null;
        }

        String[] retStrs = new String[wks.length];

        for (int i = 0; i < wks.length; i++) {
            retStrs[i] = StringUtil.Calendar2String(wks[i].getDay());
        }

        return retStrs;
    }

    //按给定范围及星期数组，获取个数，给出符合条件的数据
    public String[] getWorkDays(Calendar start,
                                int      seq,
                                int[]    weeks) throws Exception {
        String[] retStrs = new String[seq];
        int      iRet = 0;
        int      i    = 0;

        while (iRet < seq) {
            Calendar tmpStart = (Calendar) start.clone();
            tmpStart.add(Calendar.DATE, i);

            for (int j = 0; j < weeks.length; j++) {
                int[] weektmp = new int[1];
                weektmp[0] = weeks[j];

                String[] tmpStr = getWorkDays(tmpStart, tmpStart,
                                              WorkDay.ALLWORKDAY, weektmp);

                if (tmpStr != null) {
                    retStrs[iRet] = tmpStr[0];
                    iRet++;
                }

                if (iRet >= seq) {
                    return retStrs;
                }
            }

            i++;
        }

        return retStrs;
    }

    public void setYearWorkDays(int    year,
                                String workDays) throws Exception {
        Integer tmpYear = new Integer(year);
        workCalendars.put(tmpYear, workDays);

        int iRet;

        //获取数据库中的数据
        TcWt tw = getWorkDaysData(new Integer(year));

        //vddn.setFieldValue("WTS_DAYS", WorkDays);//防止查询后重置数据
        if (tw == null) { //如果没有记录，新增数据

            //将字符串插入到数据库中
            TcWt tw2 = new TcWt();
            tw2.setWtsYear(new Integer(year));
            tw2.setWtsDays(workDays);
            insertWorkDaysData(tw2);
        } else { //有记录，修改数据

            //将字符串更新到数据库中
            tw.setWtsDays(workDays);
            updateWorksData(tw);
        }
    }

    private void startMemo() {
        if (workCalendars == null) {
            workCalendars = new Hashtable();
        }
    }

    //按年获取数据库中的相关数据，如果异常返回-1，否则返回数据库中的值
    private TcWt getWorkDaysData(Integer iYear) throws Exception {
        //DBComAccess dba = new DBComAccess();
        //int iRet = 0;
        TcWt tw = (TcWt) hbAccess.load(TcWt.class, iYear);

        return tw;
    }

    private void insertWorkDaysData(TcWt tw) throws Exception {
        hbAccess.save(tw);
    }

    private void updateWorksData(TcWt tw) throws Exception {
        hbAccess.update(tw);
    }

    private String getDefalut(Calendar start,
                              Calendar end) {
        Date tt = new Date();
        tt.getTime();

        int size = (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        size = size + 1;

        String sRet = "";

        if (size < 0) {
            return "";
        } else {
            for (int i = 0; i < size; i++) {
                Calendar tmpCa = (Calendar) start.clone();
                tmpCa.add(Calendar.DATE, i);

                if ((tmpCa.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                        || (tmpCa.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
                    sRet = sRet + WorkDay.HOLIDAY;
                } else {
                    sRet = sRet + WorkDay.WORKDAY;
                }
            }
        }

        return sRet;
    }

///////////////////////////////////////////////////////////////////////
//                     以下是用于测试的方法                             //
///////////////////////////////////////////////////////////////////////
//    public static void main(String[] args) {
//        int[] ss = new int[1];
//        ss[0] = Calendar.MONDAY;
//
//        //        ss[1] = Calendar.TUESDAY;
//        //        ss[2] = Calendar.THURSDAY;
//        //        ss[3] = Calendar.WEDNESDAY;
//        //        ss[4] = Calendar.FRIDAY;
//        //        ss[5] = Calendar.SATURDAY;
//        //        ss[6] = Calendar.SUNDAY;
//        try {
//            //test("2005-04-21", 5, ss);
//            test("2005-05-26", 10);
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//    }
//
//    private static void test(String start,
//                             String end) throws Exception {
//        System.out.println("-------" + start + "--TO---" + end + "-----");
//
//        Calendar     cStart = StringUtil.String2Calendar(start);
//        Calendar     cEnd = StringUtil.String2Calendar(end);
//        CalendarProc cap  = new CalendarProc();
//        String[]     gg   = cap.getWorkDays(cStart, cEnd, WorkDay.WORKDAY);
//        String[]     mm   = cap.getWorkDays(cStart, cEnd, WorkDay.HOLIDAY);
//        String[]     ff   = cap.getWorkDays(cStart, cEnd, WorkDay.ALLWORKDAY);
//        System.out.println("----------------Work Days-------------------");
//
//        if (gg != null) {
//            for (int i = 0; i < gg.length; i++) {
//                System.out.println(gg[i]);
//            }
//        }
//
//        System.out.println("----------------HoliyDAY Days-------------------");
//
//        if (mm != null) {
//            for (int i = 0; i < mm.length; i++) {
//                System.out.println(mm[i]);
//            }
//        }
//
//        System.out.println("----------------All Days-------------------");
//
//        if (ff != null) {
//            for (int i = 0; i < ff.length; i++) {
//                System.out.println(ff[i]);
//            }
//        }
//
//        System.out.println("----------------End------------------------");
//    }
//
//    private static void test(String start,
//                             int    seq) throws Exception {
//        System.out.println("-------" + start + "--TO---" + seq + "-----");
//
//        Calendar     cStart = StringUtil.String2Calendar(start);
//
//        CalendarProc cap = new CalendarProc();
//        String[]     gg  = cap.getWorkDays(cStart, seq, WorkDay.WORKDAY);
//        String[]     mm  = cap.getWorkDays(cStart, seq, WorkDay.HOLIDAY);
//        String[]     ff  = cap.getWorkDays(cStart, seq, WorkDay.ALLWORKDAY);
//        System.out.println("----------------Work Days-------------------");
//
//        if (gg != null) {
//            for (int i = 0; i < gg.length; i++) {
//                System.out.println(gg[i]);
//            }
//        }
//
//        System.out.println("----------------HoliyDAY Days-------------------");
//
//        if (mm != null) {
//            for (int i = 0; i < mm.length; i++) {
//                System.out.println(mm[i]);
//            }
//        }
//
//        System.out.println("----------------All Days-------------------");
//
//        if (ff != null) {
//            for (int i = 0; i < ff.length; i++) {
//                System.out.println(ff[i]);
//            }
//        }
//
//        System.out.println("----------------End------------------------");
//    }
//
//    private static void test(String start,
//                             String end,
//                             int[]  weeks) throws Exception {
//        System.out.println("-------" + start + "--TO---" + end + "-----");
//
//        Calendar     cStart = StringUtil.String2Calendar(start);
//        Calendar     cEnd = StringUtil.String2Calendar(end);
//        CalendarProc cap  = new CalendarProc();
//        String[]     gg   = cap.getWorkDays(cStart, cEnd, WorkDay.WORKDAY, weeks);
//        String[]     mm   = cap.getWorkDays(cStart, cEnd, WorkDay.HOLIDAY, weeks);
//        String[]     ff   = cap.getWorkDays(cStart, cEnd, WorkDay.ALLWORKDAY,
//                                            weeks);
//        System.out.println("----------------Work Days-------------------");
//
//        if (gg != null) {
//            for (int i = 0; i < gg.length; i++) {
//                System.out.println(gg[i]);
//            }
//        }
//
//        System.out.println("----------------HoliyDAY Days-------------------");
//
//        if (mm != null) {
//            for (int i = 0; i < mm.length; i++) {
//                System.out.println(mm[i]);
//            }
//        }
//
//        System.out.println("----------------All Days-------------------");
//
//        if (ff != null) {
//            for (int i = 0; i < ff.length; i++) {
//                System.out.println(ff[i]);
//            }
//        }
//
//        System.out.println("----------------End------------------------");
//    }
//
//    private static void test(String start,
//                             int    seq,
//                             int[]  weeks) throws Exception {
//        System.out.println("-------" + start + "--TO---" + seq + "-----");
//
//        Calendar     cStart = StringUtil.String2Calendar(start);
//
//        CalendarProc cap = new CalendarProc();
//        String[]     gg  = cap.getWorkDays(cStart, seq, weeks);
//        String[]     mm  = cap.getWorkDays(cStart, seq, weeks);
//        String[]     ff  = cap.getWorkDays(cStart, seq, weeks);
//        System.out.println("----------------Work Days-------------------");
//
//        if (gg != null) {
//            for (int i = 0; i < gg.length; i++) {
//                System.out.println(gg[i]);
//            }
//        }
//
//        System.out.println("----------------HoliyDAY Days-------------------");
//
//        if (mm != null) {
//            for (int i = 0; i < mm.length; i++) {
//                System.out.println(mm[i]);
//            }
//        }
//
//        System.out.println("----------------All Days-------------------");
//
//        if (ff != null) {
//            for (int i = 0; i < ff.length; i++) {
//                System.out.println(ff[i]);
//            }
//        }
//
//        System.out.println("----------------End------------------------");
//    }
}
