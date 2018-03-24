package server.essp.tc.weeklyreport.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import java.util.Date;
import com.wits.util.comDate;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import server.framework.common.BusinessException;
import java.util.ArrayList;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportStatus;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.Calendar;
import c2s.essp.attendance.Constant;
import java.sql.ResultSet;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class LgWeeklyReportStatus extends AbstractESSPLogic {
    //date format style 'yyyy-MM-dd'
    public static String   DATE_STYLE   = comDate.pattenDate;
    private Long           hrAcntRid;

    private String         beginPeriod;
    private String         endPeriod;
    private WorkCalendar   wc           = WrokCalendarFactory.serverCreate();
    private Calendar       cBegin       = Calendar.getInstance();
    private Calendar       cEndDate     = Calendar.getInstance();

    /**
     * default constructor
     */
    public LgWeeklyReportStatus(Long hrAcntRid, Date begin, Date end) {
        this.hrAcntRid   = hrAcntRid;
        this.beginPeriod = Date2String(begin);
        this.endPeriod   = Date2String(end);
        this.cBegin.setTime(begin);
        this.cEndDate.setTime(end);
        this.cBegin.set(Calendar.HOUR_OF_DAY, 0);
        this.cBegin.set(Calendar.MINUTE, 0);
        this.cBegin.set(Calendar.SECOND, 0);
        this.cBegin.set(Calendar.MILLISECOND, 0);
        this.cEndDate.set(Calendar.HOUR_OF_DAY, 0);
        this.cEndDate.set(Calendar.MINUTE, 0);
        this.cEndDate.set(Calendar.SECOND, 0);
        this.cEndDate.set(Calendar.MILLISECOND, 0);

    }

    /**
     * get labors who didn't fill weekly report
     * @return List
     */
    public List getUnfilled() {
        List wList = wc.getBEWeekListMax(cBegin, cEndDate);
        List rList = new ArrayList();
        for(int i = 0; i < wList.size(); i++) {
            Calendar[] week = (Calendar[]) wList.get(i);
            int workHours = getStandardWorkHours(week[0], week[1]);
            if(workHours > 0) {
                Date wBegin = week[0].getTime();
                Date wEnd = week[1].getTime();
                addToList(rList, getUnfilled(wBegin, wEnd), wBegin, wEnd, workHours);
            }
        }
        return rList;
    }

    /**
     * check there is work day exist in this period
     * @param week Calendar[]
     * @return boolean
     */
    private int getStandardWorkHours(Calendar begin, Calendar end) {
        List wkList = wc.getWorkDays(begin, end);
        if(wkList != null) {
            return wkList.size()*8;
        } else {
            return 0;
        }
    }

    /**
     * add week begin and end date into dto
     * @param rList List
     * @param sList List
     * @param begin Date
     * @param end Date
     */
    private void addToList(List rList, List sList, Date begin, Date end, double staWorkHours) {
        for(int i = 0; i < sList.size(); i++) {
            DtoWeeklyReportStatus dto = (DtoWeeklyReportStatus) sList.get(i);
            if (checkLeaveAllPeriod(dto.getLoginId(), begin, end, staWorkHours)) {
                dto.setRemark("Leave all the period");
            }
            dto.setBeginPeriod(begin);
            dto.setEndPeriod(end);
            rList.add(dto);
        }
    }

    private boolean checkLeaveAllPeriod(String loginId, Date begin, Date end, double workHours) {
        String sql = "select sum(t2.hours) as hours " +
                     "from tc_leave_main t1 left join tc_leave_detail t2 " +
                     "on t1.rid = t2.leave_id " +
                     "where t1.login_id='" + loginId + "' " +
                     "and t1.status !='"+Constant.STATUS_ABORTED+"' " +
                     "and to_char(t2.leave_day,'" + DATE_STYLE + "') >= '" +
                     comDate.dateToString(begin, DATE_STYLE) + "' "+
                     "and to_char(t2.leave_day,'" + DATE_STYLE + "') <= '" +
                     comDate.dateToString(end, DATE_STYLE) + "' ";

        log.info("sum leave hours:" + sql);
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            if(rt.next()) {
                double leaveHours = rt.getDouble("hours");
                if(leaveHours >= workHours) {
                    return true;
                }
                return false;
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return false;
    }


    /**
     * get labors who didn't fill weekly report in one week
     * @param hrAcntRid Long
     * @param begin Date
     * @param end Date
     * @return List
     */
    private List getUnfilled(Date weekBegin, Date weekEnd) {
        String sql = "select l.login_id, h.chinese_name,u.name as dept " +
                     "from essp_hr_employee_main_t h,essp_hr_account_scope_t a, upms_loginuser l, essp_upms_unit u " +
                     "where h.code=a.scope_code " +
                     "and h.code = l.user_id " +
                     "and h.dept = u.unit_id " +
                     "and a.account_id= " + hrAcntRid +" " +
                     "and h.dimission_flag='0' " +
                     "and (h.outdate is null or to_char(h.outdate,'"+DATE_STYLE+"') > '" +
                     Date2String(weekBegin) + "') " +
                     "and l.login_id not in( " +
                     "select t.user_id " +
                     "from tc_weekly_report_sum t " +
                     "where to_char(t.begin_period,'"+DATE_STYLE+"') >= '" +
                     Date2String(weekBegin) + "' " +
                     "and to_char(t.end_period,'"+DATE_STYLE+"') <= '" +
                     Date2String(weekEnd) + "')";
        return getByWeeklyReportBySql(sql);
    }

    /**
     * get information of confirmed weekly report
     * @param hrAcntRid Long
     * @param begin Date
     * @param end Date
     * @return List
     */
    public List getConfirmed() {
        return getWeeklyReportByStatus(DtoWeeklyReport.STATUS_CONFIRMED);
    }

    /**
     * get information of locked (unconfirmed) weekly report
     * @param hrAcntRid Long
     * @param begin Date
     * @param end Date
     * @return List
     */
    public List getLocked() {
        return getWeeklyReportByStatus(DtoWeeklyReport.STATUS_LOCK);
    }

    /**
     * get information of unlocked weekly report
     * @param hrAcntRid Long
     * @param begin Date
     * @param end Date
     * @return List
     */
    public List getUnLock() {
        return getWeeklyReportByStatus(DtoWeeklyReport.STATUS_UNLOCK);
    }

    /**
     * get information of rejected weekly report
     * @param hrAcntRid Long
     * @param begin Date
     * @param end Date
     * @return List
     */
    public List getRejected() {
        return getWeeklyReportByStatus(DtoWeeklyReport.STATUS_REJECTED);
    }

    /**
     * get information of weekly report by status
     * @param hrAcntRid Long
     * @param begin Date
     * @param end Date
     * @return List
     */
    private List getWeeklyReportByStatus(String status) {
        String sql = "select pm.acnt_name,pm.acnt_manager,hr1.chinese_name as acnt_manager_name,t.user_id as login_id,hr2.chinese_name,t.begin_period,t.end_period " +
                     "from tc_weekly_report_sum t,pms_acnt pm, essp_hr_employee_main_t hr1,essp_hr_employee_main_t hr2,upms_loginuser up1,upms_loginuser up2 " +
                     "where to_char(t.begin_period,'"+DATE_STYLE+"')>='" + beginPeriod + "' " +
                     "and to_char(t.end_period,'"+DATE_STYLE+"')<='" + endPeriod + "' " +
                     "and t.confirm_status='" + status + "' " +
                     "and pm.rid=t.acnt_rid " +
                     "and hr1.code=up1.user_id " +
                     "and hr2.code=up2.user_id " +
                     "and up1.login_id = acnt_manager " +
                     "and up2.login_id = t.user_id " +
                     "and t.user_id in ( " +
                     "select tm.login_id from upms_loginuser tm,essp_hr_account_scope_t hr " +
                     "where tm.user_id=hr.scope_code and hr.account_id=" + hrAcntRid + ") " +
                     "order by lower(pm.acnt_manager)";

        return getByWeeklyReportBySql(sql);
    }

    /**
     * get information of weekly report by sql
     * @param hrAcntRid Long
     * @param begin Date
     * @param end Date
     * @return List
     */
    private List getByWeeklyReportBySql(String sql) {
        try {
            List lst =  this.getDbAccessor().executeQueryToList(sql, DtoWeeklyReportStatus.class);
            return lst;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException(ex);
        }
    }

    /**
     * format date string 'yyyy-MM-dd'
     * @param d Date
     * @return String
     */
    private String Date2String(Date d) {
        return comDate.dateToString(d, DATE_STYLE);
    }
}
