package server.essp.timecard.worktime.data;

import c2s.essp.timecard.worktime.*;

import org.apache.log4j.*;

import server.framework.hibernate.*;

import java.util.*;

import javax.sql.*;


/**
 *
 * <p>Title: 工作时间的DataBean</p>
 * <p>Description: 用于存放工作时间，采用数组放置数据，及解析后的数字</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class WorkTime {
    static WorkTimeItem[] items;
    static Category       log = Category.getInstance("server");

    public WorkTime() {
        if (items == null) {
            //VDData_Node vdd = new VDData_Node();
            HBComAccess dba = new HBComAccess();
            ArrayList   arr = new ArrayList();

            try {
                dba.followTx();

                String sql = "SELECT RID,WT_STARTTIME,WT_ENDTIME FROM TC_WORKTIME ORDER BY WT_STARTTIME";
                RowSet rs = dba.executeQuery(sql);

                while (rs.next()) {
                    DtoTcWorktime dtw = new DtoTcWorktime();
                    dtw.setWtStarttime(rs.getString("WT_STARTTIME"));
                    dtw.setWtEndtime(rs.getString("WT_ENDTIME"));
                    arr.add(dtw);
                }

                dba.commit();
                dba.close();
            } catch (Exception ex) {
                try {
                    dba.rollback();
                    dba.close();
                } catch (Exception ex1) {
                    ex.printStackTrace();
                }

                log.error(ex);

                return;

                //ex.printStackTrace();
                //log.debug(ex.toString());
            }

            String[][] tmpStrs = new String[arr.size()][2];
            items = new WorkTimeItem[arr.size()];

            for (int i = 0; i < arr.size(); i++) {
                tmpStrs[i][0] = ((DtoTcWorktime) arr.get(i)).getWtStarttime();
                tmpStrs[i][1] = ((DtoTcWorktime) arr.get(i)).getWtEndtime();
                items[i]      = new WorkTimeItem(tmpStrs[i][0], tmpStrs[i][1]);
            }
        }
    }

    public WorkTime(String[][] inStr) {
        items = new WorkTimeItem[inStr.length];

        for (int i = 0; i < items.length; i++) {
            items[i] = new WorkTimeItem(inStr[i][0], inStr[i][1]);
        }
    }

    public WorkTimeItem[] getItems() {
        return items;
    }

    public void setItems(WorkTimeItem[] items) {
        WorkTime.items = items;
    }

    /**
     * 依据输入的工时开始时间，获取其在一天的工作时间
     * @param inPutNumber int[]
     * @return float
     */
    public float getTimeHours(int[] inPutNumber) {
        float fRet = 0;

        //从第一个包含区间（或第一个在此时间之后的区间）开始算起
        for (int i = 0; i < items.length; i++) {
            int[] itemp = items[i].getNextTimeHours(inPutNumber);

            if (itemp.length != 1) {
                fRet = fRet + items[i].getTimeHours(itemp);
            }
        }

        return fRet;
    }

    public float getAllHours() {
        float fRet = 0;

        for (int i = 0; i < items.length; i++) {
            fRet = fRet + items[i].getTimeHours();
        }

        return fRet;
    }

    public static void flush(String[][] inStr) {
        items = new WorkTimeItem[inStr.length];

        for (int i = 0; i < items.length; i++) {
            items[i] = new WorkTimeItem(inStr[i][0], inStr[i][1]);
        }
    }

    public static void flush(ArrayList arr) {
        items = new WorkTimeItem[arr.size()];

        for (int i = 0; i < items.length; i++) {
            DtoTcWorktime dtw = (DtoTcWorktime) arr.get(i);
            items[i] = new WorkTimeItem(dtw.getWtStarttime(), dtw.getWtEndtime());
        }
    }

    public String[][] getItemstoStrings() {
        String[][] rStr = new String[this.getItems().length][2];

        for (int i = 0; i < this.getItems().length; i++) {
            rStr[i][0] = this.getItems()[i].getTimeStart();
            rStr[i][1] = this.getItems()[i].getTimeEnd();
        }

        return rStr;
    }
}
