package server.essp.tc.attcompare;

import server.framework.logic.AbstractBusinessLogic;
import server.framework.common.BusinessException;
import java.sql.ResultSet;
import java.util.ArrayList;


public class LgCompare extends AbstractBusinessLogic {
    public LgCompare() {
    }

    /**
     * 对请假信息和差勤信息进行比较
     * @param comp AfCompare
     * @return ArrayList
     */
    public ArrayList compareAtt(AfCompare comp) throws BusinessException {
        ArrayList al = new ArrayList();
        VbCompareResult webVo = new VbCompareResult();
        String begindate = comp.getBeginDate();
        String enddate = comp.getEndDate();
        String loginname, date, appTime, appHours, appStatus;
        String actualTime = "", actualHours = "", noattRid = "";
        try {
            String leavedetailSQL = "select tcmain.login_id,to_char(tcdetail.leave_day,'yyyy-mm-dd') as day,tcdetail.hours,"
                                    + "to_char(tcmain.actual_datetime_start,'yyyy-mm-dd HH24:MI') as timeB,"
                                    + "to_char(tcmain.actual_datetime_finish,'yyyy-mm-dd HH24:MI') as timeE,"
                                    + "tcmain.total_hours,tcmain.status "
                                    + "from tc_leave_detail tcdetail,tc_leave_main tcmain "
                                    + "where tcdetail.leave_id=tcmain.rid "
                                    + "and tcmain.status='Finished' "
                                    + "and to_char(tcdetail.leave_day,'yyyy/mm/dd')>='" + begindate + "' "
                                    + "and to_char(tcdetail.leave_day,'yyyy/mm/dd')<='" + enddate + "' "
                                    + "order by lower(tcmain.login_id),tcdetail.leave_day";
            //System.out.println(leavedetailSQL);
            String noattSQL;
            ResultSet leavedetailRS = this.getDbAccessor().executeQuery(leavedetailSQL);
            ResultSet noattRS = null;

            while (leavedetailRS.next()) {
                VbCompareResult vbcr = new VbCompareResult();
                loginname = leavedetailRS.getString("login_id");
                date = leavedetailRS.getString("day");
                appTime = leavedetailRS.getString("timeB") + "~" + leavedetailRS.getString("timeE");
                appHours = leavedetailRS.getString("hours");
                appStatus = leavedetailRS.getString("status");

                noattSQL = "select t.rid,t.total_hours,"
                           + "to_char(t.timefrom,'HH24:MI:SS') as tfrom,"
                           + "to_char(t.timeto,'HH24:MI:SS') as tto "
                           + "from tc_nonatten t where t.loginname='" + loginname + "' "
                           + "and to_char(t.timefrom,'yyyy-mm-dd')='" + date + "'";
                //System.out.println(noattSQL);
                noattRS = this.getDbAccessor().executeQuery(noattSQL);
                if (noattRS.next()) {
                    actualTime = noattRS.getString("tfrom") + "~" + noattRS.getString("tto");
                    actualHours = noattRS.getString("total_hours");
                    noattRid = noattRS.getString("rid");
                } else {
                    actualTime = "";
                    actualHours = "";
                    noattRid = "";
                }

                //construct web view Bean
                vbcr.setLoginId(loginname);
                vbcr.setDate(date);
                vbcr.setAppTime(appTime);
                vbcr.setAppHours(appHours);
                vbcr.setAppStatus(appStatus);
                vbcr.setActualTime(actualTime);
                vbcr.setActualHours(actualHours);
                if (!appHours.equals("") && appHours != null && !actualHours.equals("") && actualHours != null) {
                    double douApp = new Double(appHours).doubleValue();
                    double douAct = new Double(actualHours).doubleValue();
                    if (douApp >= douAct) {
                        vbcr.setIsDelete("checked=\"checked\"");
                    }
                } else {
                    vbcr.setIsDelete("");
                }

                vbcr.setNoattRid(noattRid);
                al.add(vbcr);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException(ex);
        }
        return al;
    }


}
