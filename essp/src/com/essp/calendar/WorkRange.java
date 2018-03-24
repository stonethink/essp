package com.essp.calendar;

import org.apache.log4j.*;

import java.text.*;

import java.util.*;


/**
 *
 * <p>Title: ���ڽ�����������ͻ��˴�������֮��</p>
 * <p>Description:  ��������2004-01-01~2004-01-12||000010001000...��ʽ��֯�ַ��������ݣ��˷����ô��ǿ���չ������������ֹ��֮���ȡ����������</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class WorkRange {
    static Category log = Category.getInstance("client");

    /**
     * ���ڷָ���
     */
    public static final String DATE_SPLITE = "~";

    /**
     * ���ڷ�Χ�빤�����ַ�����ķַ�
     */
    public static final String HEADER_SPLITE = "+";

    /**
     * ĳһ�����յķָ���
     */
    public static final String DAY_SPLITE = "-";

    /**
     * ����������
     */
    private WorkDay[] workDays;

    /**
     * ���ڷ�Χ����
     */
    private int size;

    /**
     * ��Χ��ʼ����
     */
    private Calendar rangeStart;

    /**
     * ��Χ��������
     */
    private Calendar rangeEnd;

    /**
     * ��Χ�����ַ���������2004-01-01~2004-12-31
     */
    private String rangeZone;

    /**
     * �������ַ���������00010000...
     */
    private String workDaysZone;

    /**
     * ��¼ԭʼ�ַ���
     */
    private String orangeString;

    public WorkRange() {
    }

    /**
     * ����ת���ַ�����ʼ��<br>
     * 1.��2004-01-01~2004-01-12+000010001000...����2004-01-01~2004-01-12��000010001000...�����ַ���<br>
     * 2.��2004-01-01~2004-01-12����RangeStart��RangeEnd<br>
     * 3.��000010001000...����WorkDay����<br>
     * ��������ַ��������ϸ�ʽ��������null<br>
     * ���000010001000...С��ǰ�涨���������������Ĭ��������䣻������ڣ����ȡ<br>
     * 000010001000...����ȫ0��1��ɣ����������һλ���ַ�0��1�������Դ�����Ĭ��ֵ����
     * @param RangeStr String
     * @return ����WorkRangeʵ��
     */
    public static WorkRange getInstance(String rangeStr) {
        String[] rengeStrs = rangeStr.split("[" + HEADER_SPLITE + "]");

        //1.���ַ�����ʽ��飬�Է�ֹ�������ݵĴ���
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

        //2.����ͷ�������ͷ����ʽ���󷵻ؿ�
        WorkRange workrg = new WorkRange();

        if (!workrg.setHeader(rengeStrs[0])) {
            log.debug("Header part error!");

            return null;
        }

        //3.������ϸ�����ͷ����ʽ���󷵻ؿ�
        if (!workrg.setDetail(rengeStrs[1])) {
            log.debug("Header part error!");

            return null;
        }

        workrg.orangeString = rangeStr;

        //RangeStrs[0]
        return workrg;
    }

    /**
     * ������ֹ�ռ��ַ���ʵ����
     * @param Start ��ʼ��
     * @param End ������
     * @param WorkDays �������б�
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
     * �������ȡʵ��
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
     * �������ȡʵ��
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
     * ��������ȡ�������Ӵ�
     * @param Start ����
     * @param End ֹ��
     * @param Flag ��־
     * @return ����������
     */
    public WorkDay[] getSub(Calendar inputStart,
                            Calendar inputEnd,
                            String   flag) {
        long iStart = inputStart.getTimeInMillis()
                      - this.getRangeStart().getTimeInMillis();

        Calendar start = inputStart;
        Calendar end = inputEnd;

        if (iStart < 0) { //����ʼ�ձ���ʼ�ջ��磬������ʼ��
            start = this.getRangeStart();
        }

        long iEnd = end.getTimeInMillis()
                    - this.getRangeEnd().getTimeInMillis();

        if (iEnd > 0) { //�������ձȽ����ջ������ý�����
            end = this.getRangeEnd();
        }

        //��ȡ�������
        int iNumber = (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / (24 * 60 * 60 * 1000));

        if (iNumber < 0) {
            return null;
        }

        iNumber++;

        WorkDay[] workdays   = new WorkDay[iNumber];
        int       iStarIndex = (int) (iStart / (24 * 60 * 60 * 1000)); //�жϴ���һ�쿪ʼ��ȡ
        int       iRet       = 0;

        for (int i = 0; i < iNumber; i++) {
            if (this.getWorkDays()[iStarIndex + i].getDayType().equals(flag)
                    || flag.equals(WorkDay.ALLWORKDAY)) {
                workdays[iRet] = this.getWorkDays()[iStarIndex + i];
                iRet++;
            }
        }

        //û��һ�ʷ���Ҫ������ݣ����ؿ�
        if (iRet == 0) {
            return null;
        }

        //�������ݷ���Ҫ��
        if (iRet == iNumber) {
            return workdays;
        }

        //ֻ�в������ݷ���Ҫ��
        WorkDay[] workdays2 = new WorkDay[iRet];

        for (int i = 0; i < iRet; i++) {
            workdays2[i] = workdays[i];
        }

        return workdays2;
    }

    /**
     * ��������ȡ�ִ��еĹ�����
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

        Hashtable htb  = new Hashtable(); //���ڲ���֪һ�����ж��ٱ����ݣ�����
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
     * ���ݷ�Χ�������ڷ��ع�����
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

        //��ȡ��ʼ���봫�������֮��Ĳ�Ի�ȡ��һ����������
        int days = week - startWeek;

        if (days < 0) {
            days = 7 + days; //���С��0��Ҫ��һ�ܲ����ǵ�һ����������
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
     * �������������ù�����
     * @param Start Calendar
     * @param End Calendar
     * @param Flag String
     * @param Week int[]
     * @return ����������
     */
    public WorkDay[] getSub(Calendar start,
                            Calendar end,
                            String   flag,
                            int[]    week) {
        Hashtable htb = new Hashtable();

        //��ȡ��ʼ���봫�������֮��Ĳ�Ի�ȡ��һ����������
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
     * ����ͷ���ַ�������
     * @param HeaderStr ͷ���ַ���
     * @return �Ƿ�����ɹ�
     */
    private boolean setHeader(String headerStr) {
        rangeZone = headerStr;

        String[] headers = headerStr.split("[" + DATE_SPLITE + "]");

        if (headers.length != 2) {
            log.debug("Data Structure error: " + headerStr);
            System.out.println("Data Structure error: " + headerStr);

            return false;
        }

        //��ȡ��ֹ�գ�����������������������Ϊ���������ش���
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

            size = size + 1; //����������������������0�룬��һ��
        } catch (ParseException ex) {
            log.debug("Data Structure error: " + headerStr);

            return false;
        }

        return true;
    }

    /**
     * ������ϸ�ַ�������
     * @param DetailStr ��ϸ�ַ���
     * @return �Ƿ�����ɹ�
     */
    public boolean setDetail(String detailStr) {
        //WorkDaysZone = DetailStr;
        // String tmpWorkDaysZone = "";
        workDays = new WorkDay[size];

        for (int i = 0; i < size; i++) {
            workDays[i] = new WorkDay();
            workDays[i].setDay((Calendar) rangeStart.clone());
            workDays[i].getDay().add(Calendar.DATE, i); //����ʼ��ʼ��i�죬��һ���Ǽ�0��

            /*1.����Ƕ��ˣ����沿������Ĭ��ֵ
               2.�������0��1������ΪĬ��ֵ
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
     * ��ȡ�������ַ���
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
