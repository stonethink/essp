package com.essp.calendar;

import org.apache.log4j.*;

import java.text.*;

import java.util.*;


/**
 *
 * <p>Title: 用于将服务器端与客户端传输数据之类</p>
 * <p>Description:  采用形如2004-01-01~2004-01-12||000010001000...方式组织字符串传数据，此方法好处是可扩展成任意两个起止日之间获取工作日设置</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class WorkRange {
    static Category log = Category.getInstance("client");

    /**
     * 日期分隔符
     */
    public static final String DATE_SPLITE = "~";

    /**
     * 日期范围与工作日字符串间的分符
     */
    public static final String HEADER_SPLITE = "+";

    /**
     * 某一具体日的分隔符
     */
    public static final String DAY_SPLITE = "-";

    /**
     * 工作日数组
     */
    private WorkDay[] workDays;

    /**
     * 日期范围长度
     */
    private int size;

    /**
     * 范围起始日期
     */
    private Calendar rangeStart;

    /**
     * 范围结束日期
     */
    private Calendar rangeEnd;

    /**
     * 范围区的字符串，形如2004-01-01~2004-12-31
     */
    private String rangeZone;

    /**
     * 工作日字符串，形如00010000...
     */
    private String workDaysZone;

    /**
     * 记录原始字符串
     */
    private String orangeString;

    public WorkRange() {
    }

    /**
     * 依据转输字符串初始化<br>
     * 1.将2004-01-01~2004-01-12+000010001000...换成2004-01-01~2004-01-12和000010001000...两个字符串<br>
     * 2.将2004-01-01~2004-01-12换成RangeStart和RangeEnd<br>
     * 3.将000010001000...换成WorkDay数组<br>
     * 如果传入字符串非以上格式，将返回null<br>
     * 如果000010001000...小于前面定义天数，所差部分用默认设置填充；如果大于，则截取<br>
     * 000010001000...是由全0或1组成，如果其中有一位出现非0或1，将忽略错误，用默认值代替
     * @param RangeStr String
     * @return 返回WorkRange实例
     */
    public static WorkRange getInstance(String rangeStr) {
        String[] rengeStrs = rangeStr.split("[" + HEADER_SPLITE + "]");

        //1.做字符串格式检查，以防止错误数据的传入
        if (rengeStrs.length != 2) {
            log.debug("Data Stracture error: " + rangeStr);
            System.out.println("Data Stracture error: " + rangeStr);

            return null;
        } else {
            String[] rangeHeaders = rengeStrs[0].split("[" + DATE_SPLITE + "]");

            if (rangeHeaders.length != 2) {
                log.debug("Data Structure error: " + rangeStr);
                System.out.println("Data Stracture error: " + rangeStr);

                return null;
            }
        }

        //2.设置头部，如果头部格式错误返回空
        WorkRange workrg = new WorkRange();

        if (!workrg.setHeader(rengeStrs[0])) {
            log.debug("Header part error!");

            return null;
        }

        //3.设置明细，如果头部格式错误返回空
        if (!workrg.setDetail(rengeStrs[1])) {
            log.debug("Header part error!");

            return null;
        }

        workrg.orangeString = rangeStr;

        //RangeStrs[0]
        return workrg;
    }

    /**
     * 依据起止日及字符串实例化
     * @param Start 起始日
     * @param End 结束日
     * @param WorkDays 工作日列表
     * @return WorkRange
     */
    public static WorkRange getInstance(Calendar start,
                                        Calendar end,
                                        String   workDays) {
        String sStart = conver(start);
        String sEnd = conver(end);

        String sRet = sStart + DATE_SPLITE + sEnd;

        sRet = sRet + HEADER_SPLITE + workDays;

        WorkRange wk = getInstance(sRet);

        return wk;
    }

    /**
     * 依据年获取实例
     * @param Year int
     * @param WorkDays String
     * @return WorkRange
     */
    public static WorkRange getInstance(int    year,
                                        String workDays) {
        Calendar cStart = Calendar.getInstance();
        cStart.set(year, 0, 1);

        Calendar cEnd = Calendar.getInstance();
        cEnd.set(year, 11, 31);

        WorkRange wk = getInstance(cStart, cEnd, workDays);

        return wk;
    }

    /**
     * 依据年获取实例
     *
     * @param works int
     * @return WorkRange
     */
    public static WorkRange getInstance(WorkDay[] works) {
        String sRet = "";

        for (int i = 0; i < works.length; i++) {
            sRet = sRet + works[i].getDayType();
        }

        WorkRange wk = getInstance(works[0].getDay(),
                                   works[works.length - 1].getDay(), sRet);

        return wk;
    }

    /**
     * 依条件获取工作日子串
     * @param Start 起日
     * @param End 止日
     * @param Flag 标志
     * @return 工作日数组
     */
    public WorkDay[] getSub(Calendar inputStart,
                            Calendar inputEnd,
                            String   flag) {
        long iStart = inputStart.getTimeInMillis()
                      - this.getRangeStart().getTimeInMillis();

        Calendar start = inputStart;
        Calendar end = inputEnd;

        if (iStart < 0) { //当开始日比起始日还早，就用起始日
            start = this.getRangeStart();
        }

        long iEnd = end.getTimeInMillis()
                    - this.getRangeEnd().getTimeInMillis();

        if (iEnd > 0) { //当结束日比结束日还晚，就用结束日
            end = this.getRangeEnd();
        }

        //获取相差天数
        int iNumber = (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / (24 * 60 * 60 * 1000));

        if (iNumber < 0) {
            return null;
        }

        iNumber++;

        WorkDay[] workdays   = new WorkDay[iNumber];
        int       iStarIndex = (int) (iStart / (24 * 60 * 60 * 1000)); //判断从哪一天开始获取
        int       iRet       = 0;

        for (int i = 0; i < iNumber; i++) {
            if (this.getWorkDays()[iStarIndex + i].getDayType().equals(flag)
                    || flag.equals(WorkDay.ALLWORKDAY)) {
                workdays[iRet] = this.getWorkDays()[iStarIndex + i];
                iRet++;
            }
        }

        //没有一笔符合要求的数据，返回空
        if (iRet == 0) {
            return null;
        }

        //所有数据符合要求
        if (iRet == iNumber) {
            return workdays;
        }

        //只有部分数据符合要求
        WorkDay[] workdays2 = new WorkDay[iRet];

        for (int i = 0; i < iRet; i++) {
            workdays2[i] = workdays[i];
        }

        return workdays2;
    }

    /**
     * 按步长获取字串中的工作日
     * @param Start Calendar
     * @param End Calendar
     * @param Flag String
     * @param Steps int
     * @return WorkDay[]
     */
    public WorkDay[] getSub(Calendar start,
                            Calendar end,
                            String   flag,
                            int      steps) {
        if (steps < 0) {
            log.debug("Steps <= 0 : " + steps);

            return null;
        }

        Calendar cStart = (Calendar) start.clone();

        long     iNumber = end.getTimeInMillis() - cStart.getTimeInMillis();

        if (iNumber < 0) {
            log.debug("End Date < Start Date");

            return null;
        }

        Hashtable htb  = new Hashtable(); //由于不可知一定会有多少笔数据，采用
        int       iRet = 0;

        while (iNumber >= 0) {
            WorkDay[] tmpStrs = getSub(start, cStart, flag);

            if (tmpStrs == null) {
                htb.put(new Integer(iRet), tmpStrs[0]);
            }

            cStart.add(Calendar.DATE, steps);
            iNumber = end.getTimeInMillis() - cStart.getTimeInMillis();
            iRet++;
        }

        WorkDay[] wrks = new WorkDay[htb.size()];

        for (int i = 0; i < htb.size(); i++) {
            wrks[i] = new WorkDay();
            wrks[i] = (WorkDay) htb.get(new Integer(i));
        }

        return wrks;
    }

    /**
     * 依据范围，及星期返回工作日
     * @param Start Calendar
     * @param End Calendar
     * @param Flag String
     * @param Week int
     * @param retHtb Hashtable
     */
    public void getSub(Calendar  start,
                       Calendar  end,
                       String    flag,
                       int       week,
                       Hashtable retHtb) {
        if ((week < Calendar.SUNDAY) || (week > Calendar.SATURDAY)) {
            log.debug("Error: Week paramer over range : Week is " + week);

            return;
        }

        Calendar cStart    = (Calendar) start.clone();
        int      startWeek = cStart.get(Calendar.DAY_OF_WEEK);

        //获取起始日与传入的星期之间的差，以获取第一个可用日期
        int days = week - startWeek;

        if (days < 0) {
            days = 7 + days; //如果小于0，要加一周才能是第一个可用日期
        }

        cStart.add(Calendar.DATE, days);

        long iNumber = end.getTimeInMillis() - cStart.getTimeInMillis();

        while (iNumber >= 0) {
            WorkDay[] works = getSub(cStart, cStart, flag);

            if (works != null) {
                String sStart = conver(cStart);
                retHtb.put(sStart, works[0]);
            }

            cStart.add(Calendar.DATE, 7);
            iNumber = end.getTimeInMillis() - cStart.getTimeInMillis();
        }
    }

    /**
     * 依据星期数组获得工作日
     * @param Start Calendar
     * @param End Calendar
     * @param Flag String
     * @param Week int[]
     * @return 工作日数组
     */
    public WorkDay[] getSub(Calendar start,
                            Calendar end,
                            String   flag,
                            int[]    week) {
        Hashtable htb = new Hashtable();

        //获取起始日与传入的星期之间的差，以获取第一个可用日期
        for (int i = 0; i < week.length; i++) {
            getSub(start, end, flag, week[i], htb);
        }

        if (htb.size() == 0) {
            return null;
        }

        WorkDay[] wrks = new WorkDay[htb.size()];
        htb.values().toArray(wrks);

        return wrks;
    }

    private static String conver(Calendar ca) {
        int    iYear  = ca.get(Calendar.YEAR);
        int    iMonth = ca.get(Calendar.MONTH) + 1;
        int    iDay   = ca.get(Calendar.DATE);
        String sRet   = String.valueOf(iYear);

        if (iMonth < 10) {
            sRet = sRet + DAY_SPLITE + "0" + iMonth;
        } else {
            sRet = sRet + DAY_SPLITE + iMonth;
        }

        if (iDay < 10) {
            sRet = sRet + DAY_SPLITE + "0" + iDay;
        } else {
            sRet = sRet + DAY_SPLITE + iDay;
        }

        return sRet;
    }

    /**
     * 依据头部字符串解析
     * @param HeaderStr 头部字符串
     * @return 是否解析成功
     */
    private boolean setHeader(String headerStr) {
        rangeZone = headerStr;

        String[] headers = headerStr.split("[" + DATE_SPLITE + "]");

        if (headers.length != 2) {
            log.debug("Data Structure error: " + headerStr);
            System.out.println("Data Structure error: " + headerStr);

            return false;
        }

        //获取起止日，并计算两者相差日数，如果为负数，返回错误
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date da1 = smf.parse(headers[0]);
            Date da2 = smf.parse(headers[1]);

            rangeStart = Calendar.getInstance();
            rangeEnd   = Calendar.getInstance();
            rangeStart.set(da1.getYear() + 1900, da1.getMonth(), da1.getDate(),
                           0, 0, 0);
            rangeStart.set(Calendar.MILLISECOND, 0);
            System.out.println("Start " + rangeStart.toString());
            rangeEnd.set(da2.getYear() + 1900, da2.getMonth(), da2.getDate(),
                         0, 0, 0);
            rangeEnd.set(Calendar.MILLISECOND, 0);
            System.out.println("End " + rangeEnd.toString());
            size = (int) ((rangeEnd.getTimeInMillis()
                   - rangeStart.getTimeInMillis()) / (24 * 60 * 60 * 1000));

            if (size < 0) {
                log.debug("Start > Finish :" + headerStr);
                System.out.println("Start > Finish :" + headerStr);

                return false;
            }

            size = size + 1; //将毫米数换算成天数，相差0秒，算一天
        } catch (ParseException ex) {
            log.debug("Data Structure error: " + headerStr);

            return false;
        }

        return true;
    }

    /**
     * 依据明细字符串解析
     * @param DetailStr 明细字符串
     * @return 是否解析成功
     */
    public boolean setDetail(String detailStr) {
        //WorkDaysZone = DetailStr;
        // String tmpWorkDaysZone = "";
        workDays = new WorkDay[size];

        for (int i = 0; i < size; i++) {
            workDays[i] = new WorkDay();
            workDays[i].setDay((Calendar) rangeStart.clone());
            workDays[i].getDay().add(Calendar.DATE, i); //从起始开始加i天，第一天是加0天

            /*1.如果是短了，后面部分设置默认值
               2.如果不是0或1，设置为默认值
             */
            if (detailStr.length() < (i + 1)) {
                workDays[i].setDefualt();
            } else if (detailStr.substring(i, i + 1).equals(WorkDay.WORKDAY)
                           || detailStr.substring(i, i + 1).equals(WorkDay.HOLIDAY)) {
                workDays[i].setDayType(detailStr.substring(i, i + 1));
            } else {
                workDays[i].setDefualt();
            }

            //tmpWorkDaysZone = tmpWorkDaysZone + WorkDays[i].getDayType();
        }

        // WorkDaysZone = tmpWorkDaysZone;
        return true;
    }

    public Calendar getRangeEnd() {
        return rangeEnd;
    }

    public Calendar getRangeStart() {
        return rangeStart;
    }

    public String getRangeZone() {
        return rangeZone;
    }

    public WorkDay[] getWorkDays() {
        return workDays;
    }

    /**
     * 获取工作日字符串
     * @return String
     */
    public String getWorkDaysZone() {
        workDaysZone = "";

        if (workDays == null) {
            workDaysZone = orangeString;
        } else {
            for (int i = 0; i < workDays.length; i++) {
                workDaysZone = workDaysZone + workDays[i].getDayType();
            }
        }

        return workDaysZone;
    }

    public void setWorkDaysZone(String workDaysZone) {
        this.workDaysZone = workDaysZone;
    }

    public void setWorkDays(WorkDay[] workDays) {
        this.workDays = workDays;
    }

    public void setRangeZone(String rangeZone) {
        this.rangeZone = rangeZone;
    }

    public void setRangeStart(Calendar rangeStart) {
        this.rangeStart = rangeStart;
    }

    public void setRangeEnd(Calendar rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public int getSize() {
        return size;
    }

    public String getOrangeString() {
        return orangeString;
    }

    public String toString() {
        String retStr = this.getRangeZone() + HEADER_SPLITE
                        + this.getWorkDaysZone();

        return retStr;
    }

    public static void main(String[] args) {
        String str = "2005-01-01~2005-12-31+0";

        //        test(str);
        //        str = "2005-01-01~2005-12-31+00111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110";
        //        test(str);
        //        str = "2005-01-01~2005-01-31+11111111111111001111111111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110011111001111100111110";
        //        test(str);
        str = "2000-01-01~2000-12-31+0";
        test(str);
//        str = "2005-12-01~2005-01-31==0";
//        test(str);
//        str = "2005-12-01~2005-12-31+0";
//        test(str);
//        str = "2005-12--01~2005-12--31+0";
//        test(str);
//        str = "2005-12--01~2005-12--31==0";
//        test(str);
    }

    private static void test(String inStr) {
        WorkRange work = WorkRange.getInstance(inStr);
        System.out.println("--------Test Start-------");

        if (work != null) {
            System.out.println("WorkRange is " + work.getWorkDaysZone());
            System.out.println(" Year " + work.getRangeStart().toString());
            System.out.println("Size is " + work.getSize());
            System.out.println("String Lenth is "
                               + work.getWorkDaysZone().length());

            for (int i = 0; i < work.getWorkDays().length; i++) {
                WorkDay  tmpDay = work.getWorkDays()[i];
                Calendar ca     = tmpDay.getDay();
                String   tmpStr = String.valueOf(ca.get(Calendar.YEAR))
                                  + String.valueOf(ca.get(Calendar.MONTH))
                                  + String.valueOf(ca.get(Calendar.DATE));
                System.out.println(" The " + i + " is " + tmpStr
                                   + " and  WorkDay is " + tmpDay.getDayType());
            }

            System.out.println("The WorkRange String " + work.toString());
        }

        System.out.println("--------Test End-------");
    }
}
