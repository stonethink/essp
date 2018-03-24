package server.essp.common.timecard;

import essp.tables.*;

import org.apache.log4j.*;

import server.framework.hibernate.*;

import java.util.*;


/**
 * <p>Title:����/��ȡ���ڵ���ʼ</p>
 * <p>Description:ʵ��DataRange�ӿڣ��ṩ�ܵ���ʼ�б� </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class WeekRange implements DateRange {
    public static final String PERIODID        = "PER_WEEK";
    public static final String FIELD_NAME_PID  = "PERIOD_PID";
    public static final String FIELD_NAME_RULE = "PERIDO_RULE";
    public static final String SPLITE          = ";";
    private static int         start = Calendar.MONDAY;
    private static String      flag   = "";
    Category                   log      = Category.getInstance("server");
    HBComAccess                hbAccess = new HBComAccess();

    public WeekRange() {
        if ((flag == null) || flag.equals("")) {
            try {
                hbAccess.followTx();

                TcPeriod tp = (TcPeriod) hbAccess.load(TcPeriod.class, PERIODID);

                if (tp != null) {
                    String   tmpStr  = tp.getPeridoRule();
                    String[] tmpStrs = tmpStr.split("[" + SPLITE + "]");

                    if (tmpStrs.length != 2) {
                        return;
                    } else {
                        start = Integer.parseInt(tmpStrs[1]);
                    }
                }

                hbAccess.commit();
                hbAccess.close();
                flag = "1";
            } catch (Exception ex) {
                Exception tmpex = ex;

                try {
                    hbAccess.rollback();
                    hbAccess.close();
                } catch (Exception ex1) {
                    tmpex = ex1;
                } finally {
                    log.debug(ex.toString());
                    log.debug(tmpex.toString());
                }
            }
        }
    }

    public WeekRange(int iStart) {
        start = iStart;
    }

    public static void main(String[] args) {
        WeekRange weekRange1 = new WeekRange(Calendar.THURSDAY);
        Calendar  ca = java.util.GregorianCalendar.getInstance();
        ca.set(2005, 5, 14);
        weekRange1.getRange(ca);

        //weekRange1.setRange(2);
    }

    /**
     * setRange
     * �����ܵ���ʼ����
     * @author BoXiao
     * @version 1.0
     * @param obj Object
     */
    public void setRange(Object obj) throws Exception {
        String   iWeekNumber = obj.toString();
        String   ruleStr = this.getClass().getName() + SPLITE + iWeekNumber;

        TcPeriod tp = new TcPeriod();
        tp.setPeriodPid(PERIODID);
        tp.setPeridoRule(ruleStr);

        hbAccess.followTx();

        //��ѯ�Ƿ���ڼ�¼
        //        TcPeriod tp2 = (TcPeriod) hbAccess.load(TcPeriod.class, PERIODID);
        //
        //        if (tp2 == null) {
        //            //�����ڣ�����
        //            hbAccess.save(tp);
        //        } else {
        //
        //            //���ڣ��޸�
        //            hbAccess.update(tp);
        //        }
        hbAccess.delete(tp);
        hbAccess.save(tp);

        hbAccess.commit();
        start = Integer.parseInt(iWeekNumber);
        hbAccess.close();
    }

    /**
     * ����һ���������ڷ��ش�������������
     *
     * @param paDate ��������
     * @return ������ʼ��Χ
     */
    public DateItem getRange(Calendar paDate) {
        resetCalendar(paDate);

        int iWeek   = paDate.get(Calendar.DAY_OF_WEEK);
        int iNumber = start - iWeek;

        int iOffset = 0;

        //�жϵ�ǰ�������������Ƿ����ʼ����С
        if (iNumber > 0) {
            iOffset = iNumber - 7; //��ǰ��һ�ܣ��԰�����ǰ����
        } else {
            iOffset = iNumber;
        }

        Calendar cStart = (Calendar) paDate.clone();
        cStart.add(Calendar.DATE, iOffset);

        Calendar cEnd = (Calendar) cStart.clone();
        cEnd.add(Calendar.DATE, 6);

        DateItem di = new DateItem();
        di.setStart(cStart);
        di.setEnd(cEnd);

        return di;
    }

    /**
     * getRange
     *
     * @param paStart Calendar
     * @param paEnd Calendar
     * @return DataItem[]
     */
    public DateItem[] getRange(Calendar paStart,
                               Calendar paEnd) {
        return null;
    }

    /**
     * getRange
     *
     * @param paDate Calendar
     * @param paBefore int
     * @param paAfter int
     * @return DataItem[]
     */
    public DateItem[] getRange(Calendar paDate,
                               int      paBefore,
                               int      paAfter) {
        return null;
    }

    /**
     * getRange
     *
     * @return int
     */
    public int getRange() {
        return start;
    }

    //�������������ڿ�ʼ������һ�����ڵ���������
    private void resetCalendar(Calendar paDate) {
        paDate.setFirstDayOfWeek(start);
        paDate.setMinimalDaysInFirstWeek(7);

        // paDate.setl
    }
}
