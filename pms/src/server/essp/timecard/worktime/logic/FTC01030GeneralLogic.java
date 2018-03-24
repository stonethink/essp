package server.essp.timecard.worktime.logic;

import c2s.dto.*;
import c2s.essp.timecard.worktime.*;
import c2s.essp.timecard.worktime.DtoTcPeriod;

import org.apache.log4j.*;

import server.essp.common.timecard.*;
import server.essp.common.timecard.WeekRange;
import server.essp.timecard.worktime.data.*;
import server.framework.hibernate.*;

import java.util.*;

import javax.sql.RowSet;
import server.framework.logic.AbstractBusinessLogic;
import com.wits.util.Parameter;
import server.framework.common.BusinessException;

/**
 *
 * <p>Title: 设置工作日</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class FTC01030GeneralLogic extends AbstractBusinessLogic {
    static Category log      = Category.getInstance("server");
    HBComAccess     hbAccess = new HBComAccess();

    public FTC01030GeneralLogic() {
    }

    public void get(TransactionData data) {
        DtoTcPeriod dtp = new DtoTcPeriod();
        WeekRange   wk = new WeekRange();
        MonthRange  mr = new MonthRange();
        dtp.setWeekID(new Integer(wk.getRange()));
        dtp.setMonthID(new Integer(mr.getRange()));

        try {
            ArrayList arrs = getWorkTime();
            data.getReturnInfo().setReturnObj("DtoTcPeriod", dtp);
            data.getReturnInfo().setReturnObj("DtoTcWorktimeList", arrs);
        } catch (Exception ex) {
            log.error(ex);
            data.getReturnInfo().setError(ex);
        }
    }

    public void save(TransactionData data) {
        DtoTcPeriod dtp = (DtoTcPeriod) data.getInputInfo().getInputObj("DtoTcPeriod");

        try {
            WeekRange wk = new WeekRange();
            wk.setRange(dtp.getWeekID());

            MonthRange mr = new MonthRange();
            mr.setRange(dtp.getMonthID());

            ArrayList arrs = (ArrayList) data.getInputInfo().getInputObj("DtoTcWorktimeList");

            if (arrs == null) {
                data.getReturnInfo().setError(true);
                data.getReturnInfo().setReturnCode("0001");
                data.getReturnInfo().setReturnCode("Woke Times is null");

                return;
            }

            FTC01030GeneralLogic ftc = new FTC01030GeneralLogic();
            FTC01030GeneralLogic.setWorkTime(arrs);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    public static ArrayList getWorkTime() throws Exception {
        HBComAccess dba = new HBComAccess();
        ArrayList   arr = new ArrayList();
        dba.followTx();

        //int iRet = 0;
        String sql = "SELECT RID,WT_STARTTIME,WT_ENDTIME FROM TC_WORKTIME ORDER BY WT_STARTTIME";
        RowSet rs = dba.executeQuery(sql);

        while (rs.next()) {
            DtoTcWorktime dtw = new DtoTcWorktime();
            dtw.setWtStarttime(rs.getString("WT_STARTTIME"));
            dtw.setWtEndtime(rs.getString("WT_ENDTIME"));
            arr.add(dtw);
        }

        dba.endTxCommit();
        dba.close();

        return arr;
    }

    public static void setWorkTime(ArrayList data) throws Exception {
        HBComAccess dba = new HBComAccess();
        String      sql = "DELETE FROM TC_WORKTIME";
        dba.followTx();
        dba.executeUpdate(sql);

        for (int i = 0; i < data.size(); i++) {
            sql = "INSERT INTO TC_WORKTIME ( RID , WT_STARTTIME,WT_ENDTIME) values (";
            sql = sql + i + ", ";
            sql = sql + "'" + ((DtoTcWorktime) data.get(i)).getWtStarttime()
                  + "' , ";
            sql = sql + "'" + ((DtoTcWorktime) data.get(i)).getWtEndtime()
                  + "' ) ";
            dba.executeUpdate(sql);
        }

        WorkTime.flush(data);

        dba.endTxCommit();
        dba.close();
    }

    public void executeLogic(Parameter param) throws BusinessException {
      TransactionData data = (TransactionData) param.get("data");
      String sAction = data.getInputInfo().getFunId();
      hbAccess = getDbAccessor();

      if (sAction.equals("get")) {
          get(data);
      } else if (sAction.equals("save")) {
          save(data);
        }

    }
}
