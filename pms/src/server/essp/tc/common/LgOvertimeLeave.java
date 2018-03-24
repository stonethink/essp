package server.essp.tc.common;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.RowSet;

import c2s.essp.attendance.Constant;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoOvertimeLeave;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import db.essp.attendance.TcLeaveMain;
import db.essp.attendance.TcOvertime;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;

public class LgOvertimeLeave extends AbstractBusinessLogic {
    IOrgnizationUtil orgUtil = OrgnizationFactory.create();
    IAccountUtil acntUtil = AccountFactory.create();
//    LgLeave lgLeave = new LgLeave();
//    LgOverTime lgOverTime = new LgOverTime();
//    LgLeaveType lgLeaveType = new LgLeaveType();

    //汇总数据
    //将这几个变量作为成员函数是为了一起计算它们
    DtoHoursOnWeek overtimeOnWeek = null;
    DtoHoursOnWeek overtimeOnWeekConfirmed = null;

    DtoHoursOnWeek leaveOnWeek = null;
    DtoHoursOnWeek leaveOnWeekConfirmed = null;

    List overtimeLeaveList = null;

    public List list(String userId, Date beginPeriod, Date endPeriod) {
        if (overtimeLeaveList == null) {
            Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
            beginPeriod = ds[0];
            endPeriod = ds[1];

            List leaveList = listLeave(userId, beginPeriod, endPeriod);
            List overtimeList = listOvertime(userId, beginPeriod, endPeriod);

            leaveList.addAll(overtimeList);
            overtimeLeaveList = leaveList;
        }

        caculateTimes();
        return overtimeLeaveList;
    }

    /**
     * 查出一周内userId的休假信息
     * @param userId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    private List listLeave(String userId, Date beginPeriod, Date endPeriod) {
        List dtoList = new ArrayList();

        try {
            List param = new ArrayList();
            List paramType = new ArrayList();
            param.add(new Timestamp(beginPeriod.getTime()));
            paramType.add("date");
            param.add(new Timestamp(endPeriod.getTime()));
            paramType.add("date");

            List leaveList = listPeriodStatusLeave(userId, beginPeriod, endPeriod);
            for (Iterator iterLeave = leaveList.iterator(); iterLeave.hasNext(); ) {
                TcLeaveMain leave = (TcLeaveMain) iterLeave.next();

                DtoOvertimeLeave dto = new DtoOvertimeLeave();
                dto.setUserId(userId);
                dto.setIsOvertime(false);

                //common information
                dto.setCause(leave.getCause());
                dto.setComments(leave.getComments());
                dto.setType(leave.getLeaveName());
                dto.setStatus(leave.getStatus());

                String orgId = leave.getOrgId();
                if (orgId != null) {
                    dto.setBelongTo(orgUtil.getOrgName(orgId));
                }

                DtoOvertimeLeave leaveDetail = getLeaveDetail(leave.getRid(), beginPeriod, endPeriod);
                if (leaveDetail != null) {
                    for (int i = DtoWeeklyReport.SATURDAY; i <= DtoWeeklyReport.SUMMARY; i++) {
                        dto.setHour(i, leaveDetail.getHour(i));
                    }
                    dtoList.add(dto);
                }
            }

            return dtoList;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when get the leave information of user - " + userId, ex);
        }
    }


    /**
     * 查出一周内userId的调休信息
     * @param userId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    private List listOvertime(String userId, Date beginPeriod, Date endPeriod) {
        List dtoList = new ArrayList();

        try {
            List param = new ArrayList();
            List paramType = new ArrayList();
            param.add(new Timestamp(beginPeriod.getTime()));
            paramType.add("date");
            param.add(new Timestamp(endPeriod.getTime()));
            paramType.add("date");

            List ovetimeList = listPeriodStatusOverTime(userId, beginPeriod, endPeriod);
            for (Iterator iterOvertime = ovetimeList.iterator(); iterOvertime.hasNext(); ) {
                TcOvertime overtime = (TcOvertime) iterOvertime.next();

                DtoOvertimeLeave dto = new DtoOvertimeLeave();
                dto.setUserId(userId);
                dto.setIsOvertime(true);

                //common information
                dto.setCause(overtime.getCause());
                dto.setComments(overtime.getComments());
                dto.setType("Overtime");
                dto.setStatus(overtime.getStatus());

                Long acntRid = overtime.getAcntRid();
                if (acntRid != null) {
                    IDtoAccount acnt = acntUtil.getAccountByRID(acntRid);
                    dto.setBelongTo(acnt.getName());
                }

                //get overtime time detail information
                DtoOvertimeLeave leaveDetail = getOvertimeDetail(overtime.getRid(), beginPeriod, endPeriod);
                if (leaveDetail != null) {
                    for (int i = DtoWeeklyReport.SATURDAY; i <= DtoWeeklyReport.SUMMARY; i++) {
                        dto.setHour(i, leaveDetail.getHour(i));
                    }
                    dtoList.add(dto);
                }
            }

            return dtoList;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when get the overtime information of user - " + userId, ex);
        }
    }

    private int getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    private void caculateTimes() {
        overtimeOnWeek = new DtoHoursOnWeek();
        overtimeOnWeekConfirmed = new DtoHoursOnWeek();

        leaveOnWeek = new DtoHoursOnWeek();
        leaveOnWeekConfirmed = new DtoHoursOnWeek();

        for (int i = DtoHoursOnWeek.SATURDAY; i < DtoHoursOnWeek.SUMMARY; i++) {
            overtimeOnWeek.setHour(i, new BigDecimal(0));
            overtimeOnWeekConfirmed.setHour(i, new BigDecimal(0));
            leaveOnWeek.setHour(i, new BigDecimal(0));
            leaveOnWeekConfirmed.setHour(i, new BigDecimal(0));
        }

        for (Iterator iter = this.overtimeLeaveList.iterator(); iter.hasNext(); ) {
            DtoOvertimeLeave dto = (DtoOvertimeLeave) iter.next();
            if (dto.isIsOvertime()) {

                //overtime
                if (Constant.STATUS_DISAGREED.equals(dto.getStatus()) == false) {
                    addToArray(overtimeOnWeek, dto);
                }

                if (Constant.STATUS_FINISHED.equals(dto.getStatus())) {
                    addToArray(overtimeOnWeekConfirmed, dto);
                }
            } else {
                //leave
                if (Constant.STATUS_DISAGREED.equals(dto.getStatus()) == false) {
                    addToArray(leaveOnWeek, dto);
                }

                if (Constant.STATUS_FINISHED.equals(dto.getStatus())) {
                    addToArray(leaveOnWeekConfirmed, dto);
                }
            }
        }
    }

    private void addToArray(DtoHoursOnWeek dto, DtoOvertimeLeave overtimeLeave) {
        for (int i = DtoHoursOnWeek.SATURDAY; i <= DtoHoursOnWeek.FRIDAY; i++) {
            if (overtimeLeave.getHour(i) != null) {
                dto.add(i, overtimeLeave.getHour(i));
                dto.add(DtoHoursOnWeek.SUMMARY, overtimeLeave.getHour(i));
            }
        }
    }

    public List listPeriodStatusLeave(String loginId, Date datetimeStart, Date datetimeFinish) {
        try {
            String[] status = new String[] {Constant.STATUS_APPLYING,
                              Constant.STATUS_DISAGREED,
                              Constant.STATUS_FINISHED,
                              Constant.STATUS_REVIEWING};

            StringBuffer statusQuery = new StringBuffer();
            for (int i = 0; i < status.length; i++) {
                statusQuery.append(" t.status='" + status[i] + "' or ");
            } //删除最后一个or
            statusQuery.delete(statusQuery.length() - 3, statusQuery.length() - 1);

            Date[] ds = LgTcCommon.resetBeginDate(datetimeStart, datetimeFinish);
            datetimeStart = ds[0];
            datetimeFinish = ds[1];

            String timeQuery = "(t.actualDatetimeStart <= :finish and t.actualDatetimeFinish >= :start)";

            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from TcLeaveMain t " +
                                   "where t.loginId=:loginId " +
                                   "and( " + timeQuery + " )" +
                                   "and ( " + statusQuery + " )" +
                                   "order by t.datetimeStart"
                     )
                     .setParameter("loginId", loginId)
                     .setParameter("start", datetimeStart)
                     .setParameter("finish", datetimeFinish)
                     .list();
            return l;
        } catch (Exception ex) {
            throw new BusinessException("TC_LV_0006", "error list leave of period!", ex);
        }
    }

    /**
     * 列出人员某区间内某状态的所有加班记录
     */
    public List listPeriodStatusOverTime(String loginId, Date startDate, Date finishDate) {
        String[] status = new String[] {Constant.STATUS_APPLYING,
                          Constant.STATUS_FINISHED,
                          Constant.STATUS_REVIEWING};

        try {
            StringBuffer statusQuery = new StringBuffer();
            for (int i = 0; i < status.length; i++) {
                statusQuery.append(" t.status='" + status[i] + "' or ");
            }
            //删除最后一个or
            statusQuery.delete(statusQuery.length() - 3, statusQuery.length() - 1);

            Date[] ds = LgTcCommon.resetBeginDate(startDate, finishDate);
            startDate = ds[0];
            finishDate = ds[1];

            String timeQuery = "(t.actualDatetimeStart <= :finish and t.actualDatetimeFinish >= :start)";

            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from TcOvertime t " +
                                   "where t.loginId=:loginId " +
                                   "and( " + timeQuery + " )" +
                                   "and (" + statusQuery + ") " +
                                   "order by t.datetimeStart"
                     )
                     .setParameter("loginId", loginId)
                     .setParameter("start", startDate)
                     .setParameter("finish", finishDate)
//                     .setParameter("status", Constant.STATUS_DISAGREED)
                     .list();
            return l;
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0013", "error list over time of period!", ex);
        }
    }

    /**
     * 列出项目某区间内某状态的所有加班记录
     */
    public List listPeriodStatusOverTime(Long acntRid, Date startDate, Date finishDate) {
        String[] status = new String[] {Constant.STATUS_APPLYING,
                          Constant.STATUS_FINISHED,
                          Constant.STATUS_REVIEWING};

        try {
            StringBuffer statusQuery = new StringBuffer();
            for (int i = 0; i < status.length; i++) {
                statusQuery.append(" t.status='" + status[i] + "' or ");
            }
            //删除最后一个or
            statusQuery.delete(statusQuery.length() - 3, statusQuery.length() - 1);

            Date[] ds = LgTcCommon.resetBeginDate(startDate, finishDate);
            startDate = ds[0];
            finishDate = ds[1];

            String timeQuery = "(t.actualDatetimeStart <= :finish and t.actualDatetimeFinish >= :start)";

            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from TcOvertime t " +
                                   "where t.acntRid=:acntRid " +
                                   "and( " + timeQuery + " )" +
                                   "and (" + statusQuery + ") " +
                                   "order by t.datetimeStart"
                     )
                     .setParameter("acntRid", acntRid)
                     .setParameter("start", startDate)
                     .setParameter("finish", finishDate)
//                     .setParameter("status", Constant.STATUS_DISAGREED)
                     .list();
            return l;
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0013", "error list over time of period!", ex);
        }
    }


    private DtoOvertimeLeave getLeaveDetail(Long leaveRid, Date beginPeriod, Date endPeriod) throws Exception {
        DtoOvertimeLeave dto = new DtoOvertimeLeave();

        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];
        //get leave time detail information
        List param = new ArrayList();
        List paramType = new ArrayList();
        param.add(new Timestamp(beginPeriod.getTime()));
        paramType.add("date");
        param.add(new Timestamp(endPeriod.getTime()));
        paramType.add("date");
        String sql = "select t.LEAVE_DAY as day, t.HOURS as hours from tc_leave_detail t "
                     + " where t.LEAVE_ID = " + leaveRid + " and t.LEAVE_DAY >= ? and t.LEAVE_DAY <= ?";

        RowSet rset = getDbAccessor().executeQuery(sql, param, paramType);
        double totalHours = 0;
        while (rset.next()) {
            Date day = rset.getDate("day");
            double hours = rset.getDouble("hours");
            int dayOfWeek = getDayOfWeek(day);
            dayOfWeek = dayOfWeek % 7;

            dto.setHour(dayOfWeek, new BigDecimal(hours));
            totalHours += hours;
        }

        if (totalHours > 0) {
            dto.setSumHour(new BigDecimal(totalHours));
            return dto;
        } else {
            return null;
        }
    }

    private DtoOvertimeLeave getOvertimeDetail(Long overtimeRid, Date beginPeriod, Date endPeriod) throws Exception {
        DtoOvertimeLeave dto = new DtoOvertimeLeave();

        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];
        //get overtime time detail information
        List param = new ArrayList();
        List paramType = new ArrayList();
        param.add(new Timestamp(beginPeriod.getTime()));
        paramType.add("date");
        param.add(new Timestamp(endPeriod.getTime()));
        paramType.add("date");
        String sql = "select t.overtime_day as day, t.HOURS as hours from tc_overtime_detail t "
                     + " where t.overtime_id = " + overtimeRid + " and t.overtime_day >= ? and t.overtime_day <= ?";

        RowSet rset = getDbAccessor().executeQuery(sql, param, paramType);
        double totalHours = 0;
        while (rset.next()) {
            Date day = rset.getDate("day");
            double hours = rset.getDouble("hours");
            int dayOfWeek = getDayOfWeek(day);
            dayOfWeek = dayOfWeek % 7;

            dto.setHour(dayOfWeek, new BigDecimal(hours));
            totalHours += hours;
        }

        if (totalHours > 0) {
            dto.setSumHour(new BigDecimal(totalHours));
            return dto;
        } else {
            return null;
        }
    }

    protected BigDecimal getLeaveSum(Long leaveRid, Date beginPeriod, Date endPeriod) throws Exception {
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];

        //get leave time detail information
        List param = new ArrayList();
        List paramType = new ArrayList();
        param.add(new Timestamp(beginPeriod.getTime()));
        paramType.add("date");
        param.add(new Timestamp(endPeriod.getTime()));
        paramType.add("date");
        String sql = "select sum(t.HOURS) as totalHours from tc_leave_detail t "
                     + " where t.LEAVE_ID = " + leaveRid + " and t.LEAVE_DAY >= ? and t.LEAVE_DAY <= ?";

        RowSet rset = getDbAccessor().executeQuery(sql, param, paramType);
        double totalHours = 0;
        if (rset.next()) {
            totalHours = rset.getDouble("totalHours");
        }
        return new BigDecimal(totalHours);
    }

    protected BigDecimal getOvertimeSum(Long overtimeRid, Date beginPeriod, Date endPeriod) throws Exception {
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];
        //get overtime detail information
        List param = new ArrayList();
        List paramType = new ArrayList();
        param.add(new Timestamp(beginPeriod.getTime()));
        paramType.add("date");
        param.add(new Timestamp(endPeriod.getTime()));
        paramType.add("date");
        String sql = "select sum(t.HOURS) as totalHours from tc_overtime_detail t "
                     + " where t.overtime_id = " + overtimeRid + " and t.overtime_day >= ? and t.overtime_day <= ?";

        RowSet rset = getDbAccessor().executeQuery(sql, param, paramType);
        double totalHours = 0;
        if (rset.next()) {
            totalHours = rset.getDouble("totalHours");
        }
        return new BigDecimal(totalHours);
    }


    public static void main(String args[]) {
        try {
            HBComAccess hBComAccess = new HBComAccess();
            hBComAccess.newTx();
            LgOvertimeLeave logic = new LgOvertimeLeave();
            logic.setDbAccessor(hBComAccess);

            List overtimeLeave = logic.list("stone.shi", new Date(106, 0, 1), new Date(106, 0, 6));
            System.out.println(overtimeLeave.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DtoHoursOnWeek getLeaveOnWeek() {
        return leaveOnWeek;
    }

    public DtoHoursOnWeek getLeaveOnWeekConfirmed() {
        return leaveOnWeekConfirmed;
    }

    public DtoHoursOnWeek getOvertimeOnWeek() {
        return overtimeOnWeek;
    }

    public DtoHoursOnWeek getOvertimeOnWeekConfirmed() {
        return overtimeOnWeekConfirmed;
    }
}
