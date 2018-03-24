package server.essp.tc.weeklyreport.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import c2s.essp.common.user.DtoUser;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByWorker;
import db.essp.tc.TcByWorkerAccount;
import server.essp.tc.common.LgWeeklyReportSum;
import server.framework.hibernate.HBComAccess;
import java.util.HashSet;
import java.util.Set;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import server.framework.common.BusinessException;

public class LgWeeklyReportSumByWorker extends LgWeeklyReportSum{
    /**
     * 返回登录者在beginPeriod到endPeriod时间区间内的weeklyReportSum信息
     * 对weeklyReportSum的时间数据进行汇总，返回结果中不含每天的时间数据
     * 适用：按“周”列出登陆者在每个项目的总工作时间
     */
    public List listByLoginIdOnWeek(String loginId,Date beginPeriod, Date endPeriod){
        List dtoList = new ArrayList();
        List tcList = getByUser(loginId, beginPeriod, endPeriod);

        for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
            TcByWorkerAccount tc = (TcByWorkerAccount) iter.next();

            DtoWeeklyReportSumByWorker dto = new DtoWeeklyReportSumByWorker();
            dto.setAcntName( tc.getAccount().getId() + " -- " + tc.getAccount().getName() );
            dto.setUserId(tc.getUserId());
            dto.setConfirmStatus(tc.getConfirmStatus());
            dto.setComments(tc.getComments());

            dto.setActualHour(getSumHour(tc));

            dtoList.add(dto);
        }

        return dtoList;
    }

    /**
     * worker lock the weeklyreport
     * change "UnLocked" --> "Locked"
     * change "Rejected" --> "Locked"
     */
    public void updateConfirmStatusByWorker(List weeklyReport, String userId, Date beginPeriod, Date endPeriod) {
        Set acnts = new HashSet();
        for (Iterator iter = weeklyReport.iterator(); iter.hasNext(); ) {
            DtoWeeklyReport dto = (DtoWeeklyReport) iter.next();
            Long acntRid = dto.getAcntRid();
            if (acnts.contains(acntRid) == false) {
                String confirmStatus = DtoWeeklyReport.STATUS_LOCK;

                List weekrptSum = getByUserAcnt(userId, acntRid, beginPeriod, endPeriod);
                if (weekrptSum.iterator().hasNext()) {
                    TcByWorkerAccount tc = (TcByWorkerAccount) weekrptSum.iterator().next();

                    if (DtoWeeklyReport.STATUS_UNLOCK.equals(tc.getConfirmStatus()) == true ||
                        DtoWeeklyReport.STATUS_REJECTED.equals(tc.getConfirmStatus()) == true) {
                        tc.setConfirmStatus(confirmStatus);
                        try {
                            getDbAccessor().update(tc);
                        } catch (Exception ex) {
                            throw new BusinessException("E0000", "Error when update confirm status of weekly report summary.", ex);
                        }
                    }
                }

                acnts.add(dto.getAcntRid());
            }
        }
    }

    public static void main(String args[]) {
        try {
            LgWeeklyReportSumByWorker logic = new LgWeeklyReportSumByWorker();
            HBComAccess hbComAccess = new HBComAccess();
            logic.setDbAccessor(hbComAccess);

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            //List list = logic.getByAcnt(new Long(1), c.getTime(), c.getTime());
            //System.out.println(list.size());

            c.set(2000,1,9);
            Date b = c.getTime();
            c.set(2009,12,9);
            Date e = c.getTime();
//            logic.listByLoginIdOnWeek(b, e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
