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
 * <p>Title: ���������߼���</p>
 * <p>Description: ���ڻ�ȡ�������������԰�ʱ�����䣬Ҳ���԰����ȡ</p>
 * ��������һ�������ݿ��ȡ�󣬽����þ�̬��ʽ�����ڴ��У�ֻ�������������ݻ����������ʱ���Ŵ����ݿ������»�ȡ��
 * �����ӿ칫�ó�������ٶȣ���ռ�������ڴ�
 * �����ʽ����Hashtable���ð�����ã�ÿ��һ�ʡ�
 * ���ĳ������û�У��������ݿ��ȡ��������ݿ�Ҳû�У�����ȡĬ��ֵ����д�����ݿ��У������ݿ��ȡ�󣬸���Hashtable
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class CalendarLogic {
    /**
     * ����������������Ϊkey�������У�ÿ��һ��
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
     * ͨ��������data��������Ϣ
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

            WorkRange wk = WorkRange.getInstance(year, sRet); //��װ����
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

    //���湤������������
    public void save(TransactionData data) {
        DtoTcWt   tw       = (DtoTcWt) data.getInputInfo().getInputObj("DtoTcWt");
        Integer   tmpYear  = tw.getWtsYear();
        int       year     = tmpYear.intValue();
        String    workdays = tw.getWtsDays();
        WorkRange wk       = WorkRange.getInstance(workdays); //�������

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
     * �����ȡ�������ַ�����ֻ����0100001000...�ַ���
     * @param Year ���
     * @return �������ַ���
     */
    public String getYearWorkDays(int year) throws Exception {
        Integer tmpYear = new Integer(year);
        boolean     bRet = false;
        String  sRet = "";

        //�ж��ڴ����Ƿ������ݣ����û�У����ȡ���ݿ��е����ݣ������ֱ�ӷ����ڴ�������
        if (workCalendars.get(tmpYear) == null) {
            //��ȡ���ݿ��е�����
            TcWt wt = null;
            try {
              wt = getWorkDaysData(tmpYear);
              bRet = true;
            } catch (Exception ex) {
              bRet = false;
            }

            if (!bRet) { //���û�м�¼������ȡĬ��ֵ������֯���ַ���д�����ݿ�

                Calendar start = Calendar.getInstance();
                start.set(year, 0, 1); //���õ�ǰ���1��1��

                Calendar end = Calendar.getInstance();
                end.set(year, 11, 31); //���õ���������һ��
                sRet = getDefalut(start, end);

                TcWt wt2 = new TcWt();
                wt2.setWtsYear(tmpYear);
                wt2.setWtsDays(sRet);

                insertWorkDaysData(wt2);
                workCalendars.put(tmpYear, sRet);
            } else { //�м�¼������¼�����ݷ���
                sRet = wt.getWtsDays();
                workCalendars.put(tmpYear, sRet);
            }
        } else {
            sRet = workCalendars.get(tmpYear).toString();
        }

        return sRet;
    }

    /**
     * ����������ȡ���������飬�����ַ�����ʽ��������yyyy-mm-dd��֯
     * @param Start ��ʼ��
     * @param End ������
     * @param Flag ���������־����WorkDay������һ��
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
     * �������ڷ�Χʵ����WorkRange��������ڷ�Χ���꣬��ÿ�궼��֯��һ��
     * @param Start ����
     * @param End ֹ��
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

        //��ȡʵ���������
        int iYearsCount = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
        int iStartYear = start.get(Calendar.YEAR);

        for (int i = 0; i <= iYearsCount; i++) {
            iStartYear = iStartYear + i;
            strTmp     = strTmp + getYearWorkDays(iStartYear);
        }

        //����������Ϊ�����һ��
        Calendar cStart = (Calendar) start.clone();
        cStart.set(Calendar.MONTH, 0);
        cStart.set(Calendar.DATE, 1);

        //����������Ϊ�������һ�죬�� ��ʵ��������s
        Calendar cEnd = (Calendar) end.clone();
        cEnd.set(Calendar.MONTH, 11);
        cEnd.set(Calendar.DATE, 31);

        WorkRange wkr = WorkRange.getInstance(cStart, cEnd, strTmp);

        return wkr;
    }

    //����������ȡ
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

    //��������Χ���������飬������������������
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

    //��������Χ���������飬��ȡ������������������������
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

        //��ȡ���ݿ��е�����
        TcWt tw = getWorkDaysData(new Integer(year));

        //vddn.setFieldValue("WTS_DAYS", WorkDays);//��ֹ��ѯ����������
        if (tw == null) { //���û�м�¼����������

            //���ַ������뵽���ݿ���
            TcWt tw2 = new TcWt();
            tw2.setWtsYear(new Integer(year));
            tw2.setWtsDays(workDays);
            insertWorkDaysData(tw2);
        } else { //�м�¼���޸�����

            //���ַ������µ����ݿ���
            tw.setWtsDays(workDays);
            updateWorksData(tw);
        }
    }

    private void startMemo() {
        if (workCalendars == null) {
            workCalendars = new Hashtable();
        }
    }

    //�����ȡ���ݿ��е�������ݣ�����쳣����-1�����򷵻����ݿ��е�ֵ
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
//                     ���������ڲ��Եķ���                             //
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
