package server.essp.attendance.common;


import java.util.Date;
import java.sql.ResultSet;
import java.util.List;
import server.framework.common.BusinessException;
import java.util.ArrayList;
import java.sql.SQLException;
import server.essp.framework.logic.AbstractESSPLogic;
import java.util.Calendar;

public class LgTimeCardFreeze extends AbstractESSPLogic {
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
     * 判断区间内HR项目的周报是否已被冻结,
     * @param hrAcntRid Long
     * @param beginDate Date
     * @param endDate Date
     * @return boolean
     */
    private boolean isFrozen(Long hrAcntRid,Date beginDate,Date endDate){
        if(hrAcntRid == null || beginDate == null || endDate == null)
            return false;
        String sql = "select is_frozen from tc_freeze " +
                     "where hr_acnt_rid=? " +
                     "and begin_date<=? and end_Date>=?";
        List oParm = new ArrayList();
        oParm.add(hrAcntRid);
        java.sql.Date d1 = new java.sql.Date(resetBeginDate(endDate).getTime()) ;
        java.sql.Date d2 = new java.sql.Date(resetEndDate(beginDate).getTime()) ;
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

    private static Date resetBeginDate(Date b) {
        Calendar c = Calendar.getInstance();
        c.setTime(b);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date b2 = c.getTime();
        return b2;
    }
    public static Date resetEndDate(Date e) {
        Calendar c = Calendar.getInstance();
        c.setTime(e);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        Date e2 = c.getTime();
        return e2;
    }
}
