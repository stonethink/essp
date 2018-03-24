package server.essp.tc.auto;

import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import net.sf.hibernate.*;
import server.framework.common.BusinessException;
import db.essp.tc.TcProcess;
import java.math.BigDecimal;
import c2s.essp.attendance.Constant;
import db.essp.attendance.TcOvertimeDetail;
import db.essp.attendance.TcUserLeave;
import db.essp.attendance.TcUserLeavePK;
import db.essp.attendance.TcUserLeaveDetailPK;
import java.util.Set;
import java.util.HashSet;
import db.essp.attendance.TcUserLeaveDetail;
import java.util.Date;
import db.essp.attendance.TcLeaveType;
import java.util.ArrayList;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: wistronits</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AutoProcessTC extends AbstractESSPLogic {
    private static String   TABLE_PROCESS      = "TC_PROCESS";
    private static String   TC_TYPE_OVERTIME   = "overTime";
    private static String   TC_TYPE_ANNUAL     = "annual";
    private static String   PROCESS_TYPE_PAY   = "pay";
    private static String   PROCESS_TYPE_USE   = "use";
    private static String   STATUS_START       = "start";
    private static String   STATUS_FINISHED    = "finished";

    private List processList;
    private String annualLeaveType;

    /**
     * constructor
     */
    public AutoProcessTC() {
    }

    /**
     * load TC_PROCESS data
     */
    private void loadProcessDate() {
        String hql = "from TcProcess as t where t.status = '" + STATUS_START + "'";
        try {
            processList = getDbAccessor().getSession().createQuery(hql).list();
        } catch (Exception ex) {
           throw new BusinessException("E000000", "List TcProcess error.",
                                       ex);
        }
    }

    /**
     * process over time and annual
     */
    public void execute() {
        loadProcessDate();
        annualLeaveType = getAnnualLeaveType();
        for(int i = 0; i < processList.size(); i++) {
            TcProcess tp= (TcProcess)processList.get(i);
            if(TC_TYPE_OVERTIME.equals(tp.getTcType())) {
                processOverTime(tp);
            } else if(TC_TYPE_ANNUAL.equals(tp.getTcType())) {
                processAnnual(tp);
            }
        }
    }

    /**
     * process over time
     * at first subtract over time hours, if not enough subtract annual
     * @param tp TcProcess
     */
    public void processOverTime(TcProcess tp) {
        String loginId = tp.getLoginId();
        double pH = tp.getProcessHours().doubleValue();
        String pT = tp.getProcessType();
        Long yeath = tp.getYearth();
        double pHBak = pH;
        pH = subOverTime(loginId, pH, pT);
        tp.setSubOvertimeHours(new BigDecimal(pHBak - pH));
        if(pH > 0) {
            pHBak = pH;
            pH = subAnnual(loginId, pH, pT, yeath, true);
            tp.setSubAnnualHours(new BigDecimal(pHBak - pH));
        }
        tp.setStatus(STATUS_FINISHED);
        tp.setRut(new Date());
    }

    /**
     * process annual
     * at first subtract annual, if not enough over time hours, if still not enough subtract annual
     * @param tp TcProcess
     */
    public void processAnnual(TcProcess tp) {
        String loginId = tp.getLoginId();
        double pH = tp.getProcessHours().doubleValue();
        String pT = tp.getProcessType();
        Long yeath = tp.getYearth();
         double pHBak = pH;
        pH = subAnnual(loginId, pH, pT, yeath, false);
        tp.setSubAnnualHours(new BigDecimal(pHBak - pH));
        if(pH > 0) {
            pHBak = pH;
            pH = subOverTime(loginId, pH, pT);
            tp.setSubOvertimeHours(new BigDecimal(pHBak - pH));
        }
        if(pH > 0) {
            pHBak = pH;
            pH = subAnnual(loginId, pH, pT, yeath, true);
            tp.setSubAnnualHours(tp.getSubAnnualHours().add(new BigDecimal(pHBak - pH)));
        }
        tp.setStatus(STATUS_FINISHED);
        tp.setRut(new Date());
    }

    /**
     * subtract over time
     * @param loginId String
     * @param subHours double
     * @param processType String
     * @return double
     */
    private double subOverTime(String loginId, double subHours, String processType) {
        List l = getOverTime(loginId);
        if (l == null || l.size() <= 0)
          return subHours;
        for (int i = 0; i < l.size(); i++) {
            TcOvertimeDetail detail = (TcOvertimeDetail) l.get(i);
            double usable = detail.getUsableHours().doubleValue();
            if (usable > 0) {
                if (usable >= subHours) {
                    if(PROCESS_TYPE_USE.equals(processType)) {
                        detail.addShiftHours(subHours);
                    } else if(PROCESS_TYPE_PAY.equals(processType)) {
                        detail.addPayedHours(subHours);
                    }
                    subHours = 0;
                    break;
                } else {
                    subHours -= usable;
                    if(PROCESS_TYPE_USE.equals(processType)) {
                        detail.addShiftHours(usable);
                    } else if(PROCESS_TYPE_PAY.equals(processType)) {
                        detail.addPayedHours(usable);
                    }
                    continue;
                }
            }
        }
        return subHours;
    }

    /**
     * subtract annual (use or pay)
     * @param loginId String
     * @param subHours double
     * @param processType String
     * @param yearth Long
     * @param negative boolean
     * @return double
     */
    private double subAnnual(String loginId, double subHours, String processType, Long yearth, boolean negative) {
        TcUserLeave userLeave = getAnnual(loginId);
        if(userLeave != null) {
            if (PROCESS_TYPE_USE.equals(processType)) {
                subHours = userLeave.addUseLeaveHours(subHours);
            } else if (PROCESS_TYPE_PAY.equals(processType)) {
                subHours = userLeave.addPayedHours(subHours);
            }
        }
        if(negative && subHours > 0) {
            subAnnualNegative(loginId,subHours,userLeave,processType,yearth);
            subHours = 0;
        }
        return subHours;
    }

    /**
     * subtract annual to negative.
     *       if there is no record exist, add one.
     * @param loginId String
     * @param subHours double
     * @param userLeave TcUserLeave
     * @param processType String
     * @param yearth Long
     */
    private void subAnnualNegative(String loginId, double subHours, TcUserLeave userLeave, String processType, Long yearth) {
        TcUserLeavePK pk = new TcUserLeavePK(loginId, annualLeaveType);
        if (userLeave == null) {
            userLeave = new TcUserLeave();
            userLeave.setComp_id(pk);
            this.getDbAccessor().save(userLeave);
        }
        TcUserLeaveDetailPK detailPk = new TcUserLeaveDetailPK(loginId,
            annualLeaveType, new Long(yearth.longValue()-1));
        TcUserLeaveDetail detail;
        Set detailSet = userLeave.getTcUserLeaveDetails();
        if (detailSet == null || detailSet.isEmpty()) {
            detail = new TcUserLeaveDetail();
            detail.setComp_id(detailPk);
            detail.setTcUserLeave(userLeave);
            detail.setCanLeaveHours(new Double(-subHours));

            detailSet = new HashSet();
            detailSet.add(detail);
            userLeave.setTcUserLeaveDetails(detailSet);
        } else {
            detail = (TcUserLeaveDetail)detailSet.toArray()[detailSet.size()-1];
            if (PROCESS_TYPE_USE.equals(processType)) {
                Double useHours = new Double(detail.getUseLeaveHours().doubleValue() + subHours);
                detail.setUseLeaveHours(useHours);
            } else if(PROCESS_TYPE_PAY.equals(processType)) {
                Double payHours = new Double(detail.getPayedHours().doubleValue() + subHours);
                detail.setPayedHours(payHours);
            }
        }
        this.getDbAccessor().save(detail);
        this.getDbAccessor().update(userLeave);
    }

    /**
     * get over time list
     * @param loginId String
     * @return List
     */
    private List getOverTime(String loginId) {
        List overList = null;
        String hql = "from TcOvertimeDetail ot " +
                      "where ot.tcOvertime.loginId=:loginId " +
                      "and ot.tcOvertime.status =:status " +
                      "and ot.hours - ot.shiftHours - ot.payedHours > 0 " +
                      "order by ot.overtimeDay";
        try {
            Session s = this.getDbAccessor().getSession();
            overList = s.createQuery(hql)
                     .setParameter("loginId",loginId)
                     .setParameter("status",Constant.STATUS_FINISHED)
                     .list();
        } catch (Exception ex) {
           throw new BusinessException("E000000", "List over time error.",
                                       ex);
        }
        return overList;
    }

    /**
     * get user leave
     * @param loginId String
     * @return TcUserLeave
     */
    private TcUserLeave getAnnual(String loginId) {
        TcUserLeavePK pk = new TcUserLeavePK(loginId,annualLeaveType);
        try {
            TcUserLeave userLeave = (TcUserLeave)this.getDbAccessor().get(
                TcUserLeave.class, pk);
            return userLeave;
        }catch (Exception ex) {
           throw new BusinessException("E000000", "Get annual error.",
                                       ex);
        }
    }

    private List getNegativeAnnual() {
        List rList = null;
        try {
            rList = this.getDbAccessor().listAll(TcUserLeave.class);
        } catch (Exception ex) {
            throw new BusinessException("E000000", "List annual error.",
                                        ex);
        }
        if (rList == null) {
            return new ArrayList();
        }
        for (int i = 0; i < rList.size(); i++) {
            TcUserLeave userLeave = (TcUserLeave) rList.get(i);
            if (userLeave.getUsableHours().doubleValue() >= 0) {
                rList.remove(i);
            }
        }
        return rList;
    }
    /**
     * get annual leave type
     */
    private String getAnnualLeaveType(){

        List l = null;
        try {
            Session s = this.getDbAccessor().getSession();
            String hql = "from TcLeaveType type where type.relation='"
                         + Constant.LEAVE_RELATION_ANNU_LEAVE
                         + "' and type.status='"
                         + server.framework.common.Constant.RST_NORMAL
                         + "'  order by type.seq";

            l = s.createQuery(hql).list();
        } catch (Exception ex) {
            throw new BusinessException("get annual leave type error!",ex);
        }
        if(l != null && l.size() > 0){
            TcLeaveType leaveType = (TcLeaveType) l.get(0);
            return leaveType.getLeaveName();
        } else {
            throw new BusinessException("Can not find type for annual leave !");
        }
    }

    public static void main(String[] args) {
        AutoProcessTC apt = new AutoProcessTC();
        try {
            apt.getDbAccessor().followTx();
            apt.execute();
            apt.getDbAccessor().endTxCommit();
            log.info("Process Over!");
        } catch (Exception ex) {
            log.error(ex);
            try {
                apt.getDbAccessor().endTxRollback();
            } catch (Exception ex1) {
                log.error("Rollback error!",ex1);
            }
        }
    }

}
