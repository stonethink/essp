package server.essp.tc.hrmanage.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.*;
import server.framework.common.BusinessException;
import server.essp.tc.common.LgTcCommon;
import java.util.Calendar;
import com.wits.util.comDate;

/**
 * TimeCard冻结功能,可以冻结某个时间段,该时间段内不能申请差勤记录(加班,请假)
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class LgTimeCardFreeze extends AbstractESSPLogic {

    /**
     * 判断区间内HR项目的周报是否已被冻结,
     * @param hrAcntRid Long
     * @param beginDate Date
     * @param endDate Date
     * @return boolean
     */
    public boolean isFrozen(Long hrAcntRid,Date beginDate,Date endDate){
        if(hrAcntRid == null || beginDate == null || endDate == null)
            return false;
        String sql = "select is_frozen from tc_freeze " +
                     "where hr_acnt_rid=? " +
                     "and begin_date<=? and end_Date>=?";
        List oParm = new ArrayList();
        oParm.add(hrAcntRid);
        java.sql.Date d1 = new java.sql.Date(LgTcCommon.resetBeginDate(endDate).getTime()) ;
        java.sql.Date d2 = new java.sql.Date(LgTcCommon.resetEndDate(beginDate).getTime()) ;
        oParm.add(d1);
        oParm.add(d2);
        ResultSet rt = this.getDbAccessor().executeQuery(sql,oParm);
        try {
            if (rt.next()) {
                String isFrozen = rt.getString("is_frozen");
                return isFrozen != null && "1".equals(isFrozen);
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new BusinessException(ex);
        }
    }
    /**
     * 判断某个人所在HR Account是否至少有一个被冻结了
     * @param hrAcntRid Long
     * @param beginDate Date
     * @param endDate Date
     * @return boolean
     */
    public boolean isFrozen(String loginId,Date beginDate,Date endDate){
        ////////////////先找这个人归属哪些HR Account,若其中有一个已冻结了该区间,则不能申请加班////////
        String sql = "select account_id from essp_hr_account_scope_t t "+
                     "where t.scope_code=(select user_id from upms_loginuser where login_id='"+loginId+"')";
        try {
            ResultSet rs = this.getDbAccessor().executeQuery(sql);
            while(rs.next()){
                String hrAcntRid =  rs.getString("account_id");
                boolean isFrozen = isFrozen(new Long(hrAcntRid),
                                            beginDate,
                                            endDate);
                if(isFrozen)
                    return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * 冻结HR项目某区间内的周报
     * @param hrAcntRid Long
     * @param beginDate Date
     * @param endDate Date
     */
    public void freeze(Long hrAcntRid,Date beginDate,Date endDate){
        String sql = "select is_frozen from tc_freeze " +
                     "where hr_acnt_rid=? " +
                     "and begin_date<=? and end_Date>=?";
        List oParm = new ArrayList();
        oParm.add(hrAcntRid);
        java.sql.Date d1 = new java.sql.Date(LgTcCommon.resetBeginDate(beginDate).getTime()) ;
        java.sql.Date d2 = new java.sql.Date(LgTcCommon.resetEndDate(endDate).getTime()) ;
        oParm.add(d1);
        oParm.add(d2);
        ResultSet rt = this.getDbAccessor().executeQuery(sql,oParm);
        try {
            if (rt.next()) {
                String updateSql = "update tc_freeze set is_frozen=? "+
                                   "where hr_acnt_rid=? " +
                                   "and begin_date<=? and end_Date>=?";
                oParm.add(0,new Long(1));
                this.getDbAccessor().executeUpdate(updateSql,oParm);
            }else{
                String insertSql = "insert into tc_freeze (is_frozen,hr_acnt_rid,begin_date,end_date) "+
                                   "values(?,?,?,?) " ;
                oParm.add(0,new Long(1));
                this.getDbAccessor().executeUpdate(insertSql,oParm);
            }
        } catch (SQLException ex) {
             throw new BusinessException(ex);
        }
    }

    public void unFreeze(Long hrAcntRid,Date beginDate,Date endDate){
        String updateSql = "update tc_freeze set is_frozen=? "+
                           "where hr_acnt_rid=? " +
                           "and begin_date<=? and end_Date>=?";

        List oParm = new ArrayList();
        oParm.add(0,new Long(0));
        oParm.add(hrAcntRid);
        java.sql.Date d1 = new java.sql.Date(LgTcCommon.resetBeginDate(beginDate).getTime()) ;
        java.sql.Date d2 = new java.sql.Date(LgTcCommon.resetEndDate(endDate).getTime()) ;
        oParm.add(d1);
        oParm.add(d2);
        this.getDbAccessor().executeUpdate(updateSql,oParm);
    }

    public static void main(String[] args){
        LgTimeCardFreeze lg = new LgTimeCardFreeze();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH,7);
        cal.set(Calendar.DAY_OF_MONTH,11);
        Date d1 = cal.getTime();
        Calendar ca2 = Calendar.getInstance();
        ca2.set(Calendar.MONTH,7);
        ca2.set(Calendar.DAY_OF_MONTH,13);
        Date d2 = ca2.getTime();
        System.out.println(comDate.dateToString(d1));
        System.out.println(comDate.dateToString(d2));
        System.out.println(lg.isFrozen("RongXiao",d1,d2));
    }
}
