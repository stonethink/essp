package server.essp.tc.common;

import java.util.Date;
import java.util.Iterator;

import db.essp.tc.TcWeeklyReportLock;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class LgWeeklyReportLock extends AbstractESSPLogic {

    public Boolean getLock(String userId, Date beginPeriod, Date endPeriod) {
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];

        try {
            String sql = "from TcWeeklyReportLock t where t.userId =:userId"
                         + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod";
            Iterator it = getDbAccessor().getSession().createQuery(sql)
                          .setString("userId", userId)
                          .setDate("beginPeriod", beginPeriod)
                          .setDate("endPeriod", endPeriod)
                          .iterate();
            if (it.hasNext()) {
                TcWeeklyReportLock db = (TcWeeklyReportLock) it.next();
                if ("1".equals(db.getIsLocked())) {
                    return Boolean.TRUE;
                }
            }

            return Boolean.FALSE;
        } catch (Exception ex) {
            throw new BusinessException("E000","Error when get the lock information of worker - " + userId, ex);
        }
    }

    public void setLockOn(String userId, Date beginPeriod, Date endPeriod) {
        setLock( userId, beginPeriod, endPeriod, true );
    }

    public void setLockOff(String userId, Date beginPeriod, Date endPeriod) {
        //set the lock flag
        setLock( userId, beginPeriod, endPeriod, false );
    }

    private void setLock(String userId, Date beginPeriod, Date endPeriod, boolean isLock) {
        try {
            String lock;

            if( isLock ){
                lock = "1";
            }else{
                lock = "0";
            }
            Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
            String sql = "from TcWeeklyReportLock t where t.userId =:userId"
                         + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod";
            Iterator it = getDbAccessor().getSession().createQuery(sql)
                          .setString("userId", userId)
                          .setDate("beginPeriod", ds[0])
                          .setDate("endPeriod", ds[1])
                          .iterate();
            if (it.hasNext()) {
                TcWeeklyReportLock db = (TcWeeklyReportLock) it.next();
                if( lock.equals(db.getIsLocked()) == false ){
                    db.setIsLocked(lock);
                    getDbAccessor().update(db);
                }
            }else{
                TcWeeklyReportLock db = new TcWeeklyReportLock();
                db.setUserId(userId);
                db.setBeginPeriod(beginPeriod);
                db.setEndPeriod(endPeriod);
                db.setIsLocked(lock);
                getDbAccessor().save(db);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000","Error when set the lock of worker - " + userId);
        }
    }


}

