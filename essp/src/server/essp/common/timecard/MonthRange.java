package server.essp.common.timecard;

import essp.tables.TcPeriod;

import org.apache.log4j.*;

import server.framework.hibernate.HBComAccess;

import java.util.Calendar;


public class MonthRange implements DateRange {
    public static final String PERIODID        = "PER_MONTH";
    public static final String FIELD_NAME_PID  = "PERIOD_PID";
    public static final String FIELD_NAME_RULE = "PERIDO_RULE";
    public static final String SPLITE          = ";";
    private static int       start = 1;
    private static String      flag   = "";
    Category                   log      = Category.getInstance("server");
    HBComAccess                hbAccess = new HBComAccess();

    public MonthRange() {
      if ((flag == null) || flag.equals("")) {
        try {
          hbAccess.followTx();

          TcPeriod tp = (TcPeriod) hbAccess.load(TcPeriod.class, PERIODID);

          if (tp != null) {
            String tmpStr = tp.getPeridoRule();
            String[] tmpStrs = tmpStr.split("[" + SPLITE + "]");

            if (tmpStrs.length != 2) {
              return;
            } else {
              start = Integer.parseInt(tmpStrs[1]);
            }
          }

          hbAccess.commit();
          hbAccess.close();
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

    /**
     * getRange
     *
     * @return int
     */
    public int getRange() {
        return start;
    }

    /**
     * getRange
     *
     * @param paDate Calendar
     * @param paBefore int
     * @param paAfter int
     * @return DateItem[]
     */
    public DateItem[] getRange(Calendar paDate,
                               int      paBefore,
                               int      paAfter) {
        return null;
    }

    /**
     * getRange
     *
     * @param paStart Calendar
     * @param paEnd Calendar
     * @return DateItem[]
     */
    public DateItem[] getRange(Calendar paStart,
                               Calendar paEnd) {
        return null;
    }

    /**
     * getRange
     *
     * @param paDate Calendar
     * @return DateItem
     */
    public DateItem getRange(Calendar paDate) {
        return null;
    }

    /**
     * setRange
     *
     * @param obj Object
     */
    public void setRange(Object obj) throws Exception {
        String   iWeekNumber = obj.toString();
        String   ruleStr = this.getClass().getName() + SPLITE + iWeekNumber;

        TcPeriod tp = new TcPeriod();
        tp.setPeriodPid(PERIODID);
        tp.setPeridoRule(ruleStr);

        hbAccess.followTx();

        //查询是否存在记录
        //        TcPeriod tp2 = (TcPeriod) hbAccess.load(TcPeriod.class, PERIODID);
        //
        //        if (tp2 == null) {
        //            //不存在，新增
        //            hbAccess.save(tp);
        //        } else {
        //
        //            //存在，修改
        //            hbAccess.update(tp);
        //        }
        hbAccess.delete(tp);
        hbAccess.save(tp);

        start = Integer.parseInt(iWeekNumber);

        hbAccess.commit();
        hbAccess.close();
    }
}
