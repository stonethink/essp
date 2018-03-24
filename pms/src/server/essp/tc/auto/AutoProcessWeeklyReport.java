package server.essp.tc.auto;

import c2s.essp.common.calendar.WrokCalendarFactory;
import java.util.Calendar;
import c2s.essp.common.calendar.WorkCalendar;
import server.essp.tc.common.LgWeeklyReport;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import com.wits.util.comDate;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import java.util.List;
import javax.sql.RowSet;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Date;
import c2s.dto.IDto;
import java.util.Iterator;
import c2s.essp.attendance.Constant;

/**
 * <p>Title: Fill and confirm weekly report at the end of month.</p>
 *
 * <p>Description: IF there is exist unfilled or unconfirmed weekly report,
 *     when HR need to export timecard report.
 *
 * 1.Fill unfilled weekly report
 *      (1) If an employee is in and only in an account, fill in this account,
 *          else fill in the account with id as his department code.
 *
 *      ? How about unfinished leave and over time
 *
 * 2.Confirm unconfirmed weekly report
 *      (1) confirm weekly report with status of 'Locked'
 *
 *      ?  how about 'UnLock'
 *
 * </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AutoProcessWeeklyReport extends AbstractESSPLogic {

    public static String   PERIOD_TYPE_WEEK  = "week";
    public static String   PERIOD_TYPE_MONTH = "month";
    public static int      OTHER_CODE        = 88;
    public static String   DATE_STYLE        = comDate.pattenDate;
    public static String   JOB_DESCRIPTION   = "<FILLED BY ESSP>";
    public static String   CONFIRM_REMARK    = "<CONFIRMED BY ESSP>";

    private Long           hrAcntRid         = null;
    //current date
    private Calendar       today             = Calendar.getInstance();
    private WorkCalendar   wc                = WrokCalendarFactory.serverCreate();
    //work week period
    private Calendar[]     period            = null;
    private String         beginPeriod       = null;
    private String         endPeriod         = null;
    private LgWeeklyReport lgWeeklyReport    = new LgWeeklyReport();

    /**
     * default constructor
     */
    public AutoProcessWeeklyReport(Long hrAcntRid) {
        this(hrAcntRid, PERIOD_TYPE_WEEK, 0);
    }

    /**
     * constructor
     * @param weekOffset int
     */
    public AutoProcessWeeklyReport(Long hrAcntRid, String type, int pOffset) {
        this.hrAcntRid = hrAcntRid;
        Calendar[] ca;
        //get work week period
        if(PERIOD_TYPE_MONTH.equals(type)) {
            ca = wc.getNextBEMonthDay(today, pOffset);
        } else {
            ca = wc.getNextBEWeekDay(today, pOffset);
        }
        period = new Calendar[2];
        formatPeriod(ca[0], ca[1]);

    }

    /**
     * constructor
     * @param weekBegin Calendar
     * @param weekEnd Calendar
     */
    public AutoProcessWeeklyReport(Long hrAcntRid,
                                   Calendar weekBegin, Calendar weekEnd) {
        this.hrAcntRid = hrAcntRid;
        period = new Calendar[2];
        formatPeriod(weekBegin, weekEnd);
    }

    /**
     * process
     * Fill and confirm weekly report.
     */
    public void execute() {
        execute(true, true);
    }

    /**
     * process
     * @param fill boolean  whether fill weekly report
     * @param confirm boolean whether confirm weekly report
     */
    public void execute(boolean fill, boolean confirm) {
        if(confirm) {
            autoConfirmWeeklyReport();
        }
        if(fill) {
            autoFillWeeklyReport();
        }
    }

    /**
     * Format period begin and end date, for the largest period.
     */
    private void formatPeriod(Calendar weekBegin, Calendar weekEnd) {
        period[0] = weekBegin;
        period[1] = weekEnd;

        period[0].set(Calendar.HOUR_OF_DAY, 0);
        period[0].set(Calendar.MINUTE, 0);
        period[0].set(Calendar.SECOND, 0);
        period[0].set(Calendar.MILLISECOND, 0);
        period[1].set(Calendar.HOUR_OF_DAY, 23);
        period[1].set(Calendar.MINUTE, 59);
        period[1].set(Calendar.SECOND, 59);
        period[1].set(Calendar.MILLISECOND, 999);

        beginPeriod = comDate.dateToString(period[0].getTime(),DATE_STYLE);
        endPeriod = comDate.dateToString(period[1].getTime(), DATE_STYLE);
    }

    /**
     * Confirm weekly report.
     */
    private void autoConfirmWeeklyReport() {

        String confirm = DtoWeeklyReport.STATUS_CONFIRMED;



        String sql = "update tc_weekly_report_sum " +
                     "set confirm_status='" + confirm + "', " +
                     "comments = comments || ' " + CONFIRM_REMARK + "'" +
                     "where to_char(begin_period,'" + DATE_STYLE + "')>='" + beginPeriod + "' " +
                     "and to_char(end_period,'" + DATE_STYLE + "')<='" + endPeriod + "'" +
                     "and confirm_status in (" + getNeedConfirmTypeStr() + ")";
       try {
           log.info("confirm weekly report:" + sql);
           this.getDbAccessor().executeUpdate(sql);
       } catch (Exception ex) {
           throw new BusinessException(ex);
       }
    }

    /**
     * get types of weekly report that need to be confirmed.
     * @return String
     */
    protected String getNeedConfirmTypeStr() {
        String typeStr = "";
        typeStr = "'" + DtoWeeklyReport.STATUS_LOCK + "'";
        typeStr = typeStr + ",'" + DtoWeeklyReport.STATUS_UNLOCK + "'";
        return typeStr;
    }

    /**
     * Fill weekly report.
     */
    private void autoFillWeeklyReport() {
        List lst = wc.getBEWeekListMin(period[0], period[1]);
        for(int i = 0; i < lst.size(); i++) {
            Calendar[] ca = (Calendar[])lst.get(i);
            formatPeriod(ca[0], ca[1]);
            fillOneWeek();
        }
    }

    /**
     * fill all labors' weekly reports of current period.
     */
    private void fillOneWeek() {
        List lst = getNeedFillLabors();
        for(int i = 0; i < lst.size(); i++) {
            fillOneLabor(lst.get(i).toString());
        }
    }

    /**
     * get loginId list
     * 1. in HR account
     * 2. on the job
     * 3. no weekly report filled in current period
     * @return List
     */
    protected List getNeedFillLabors() {

        String sql = "select l.login_id " +
                     "from essp_hr_employee_main_t h,essp_hr_account_scope_t a, upms_loginuser l " +
                     "where h.code=a.scope_code " +
                     "and h.code = l.user_id " +
                     "and a.account_id= " + hrAcntRid +
                     "and h.dimission_flag='0' " +
                     "and l.login_id not in( " +
                     "select t.user_id " +
                     "from tc_weekly_report_sum t " +
                     "where to_char(t.begin_period,'"+DATE_STYLE+"') >= '" + beginPeriod + "' " +
                     "and to_char(t.end_period,'"+DATE_STYLE+"') <= '" + endPeriod + "')";

        try {
            List lst = new ArrayList();
            RowSet rs = this.getDbAccessor().executeQuery(sql);
            while(rs.next()) {
                lst.add(rs.getString("login_id"));
            }
            return lst;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException(ex);
        }
    }

    /**
     * fill one labor's weekly report
     * @param loginId String
     */
    private void fillOneLabor(String loginId) {
        DtoWeeklyReport dto = new DtoWeeklyReport();
        dto.setUserId(loginId);
        Long acntRid = getFillInAcntRid(loginId);
        if(acntRid == null) {
            return;
        }
        dto.setAcntRid(acntRid);
        dto.setCodeValueRid(new Long(OTHER_CODE));
        dto.setBeginPeriod(period[0].getTime());
        dto.setEndPeriod(period[1].getTime());
        dto.setIsActivity("0");
        dto.setOp(IDto.OP_INSERT);
        dto.setJobDescription(JOB_DESCRIPTION);

        //get days need fill
        List days = this.getNeedFillDays(loginId);
        if(days == null || days.size() < 1) {
            return;
        }
        Iterator it = days.iterator();
        while (it.hasNext()) {
            Calendar day = (Calendar) it.next();
            int dayOfWeek = getDayOfWeek(day);
            BigDecimal standardHours = new BigDecimal(DtoWeeklyReport.
                STANDARD_HOUR_ONE_DAY);
            BigDecimal leaveHours = getConfirmedLeaveHoursInDay(loginId, day);
            BigDecimal overTimeHours = getConfirmedOverTimeHoursInDay(loginId,
                day);
            BigDecimal increHoursInDay = standardHours
                                         .subtract(leaveHours)
                                         .add(overTimeHours);
            dto.setHour(dayOfWeek, increHoursInDay);
        }
        saveWeeklyReport(dto);
    }

    /**
     * save, confirm and unlock a weekly report.
     * @param dto DtoWeeklyReport
     */
    private void saveWeeklyReport(DtoWeeklyReport dto) {

        lgWeeklyReport.insert(dto);

        String confirm = DtoWeeklyReport.STATUS_CONFIRMED;
        String loginId = dto.getUserId();
        Long acntRid = dto.getAcntRid();
        String beginPeriod = comDate.dateToString(dto.getBeginPeriod(), DATE_STYLE);
        String endPeriod = comDate.dateToString(dto.getEndPeriod(), DATE_STYLE);
        String sql = "update tc_weekly_report_sum set confirm_status='" + confirm + "' " +
                     "where user_id='" + loginId + "' and acnt_rid = '" + acntRid + "' " +
                     "and to_char(begin_period,'" + DATE_STYLE + "')='" + beginPeriod + "' " +
                     "and to_char(end_period,'" + DATE_STYLE + "')='" + endPeriod + "'";
        try {
            log.info("confirm new weekly reports:" + sql);
            this.getDbAccessor().executeUpdate(sql);

            sql = "update tc_weekly_report_lock set is_locked='0' " +
                  "where user_id='" + loginId + "' " +
                  "and to_char(begin_period,'" + DATE_STYLE + "')='" + beginPeriod + "' " +
                  "and to_char(end_period,'" + DATE_STYLE + "')='" + endPeriod + "'"; ;

            log.info("unlock(hr view) new weekly reports:" + sql);
            this.getDbAccessor().executeUpdate(sql);
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    /**
     * get calendar list weekly report required
     * @param LoginId String
     * @return List
     */
    protected List getNeedFillDays(String LoginId) {
        Date resetBeginPeriod = period[0].getTime();
        Date resetEndPeriod = period[1].getTime();
        Date[] inOutDate = getInOutDate(LoginId);
        Calendar c = Calendar.getInstance();
        if (inOutDate != null) {
            if (inOutDate[0] != null) {
                c.setTime(inOutDate[0]);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                Date inDate = c.getTime();
                if (comDate.compareDate(inDate, resetBeginPeriod) > 0) {
                    resetBeginPeriod = inDate;
                }
            }
            if (inOutDate[1] != null) {
                c.setTime(inOutDate[1]);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                Date outDate = c.getTime();
                if (comDate.compareDate(outDate, resetEndPeriod) < 0) {
                    resetEndPeriod = outDate;
                }
            }
        }
        Calendar begin= Calendar.getInstance();
        Calendar end= Calendar.getInstance();
        begin.setTime(resetBeginPeriod);
        end.setTime(resetEndPeriod);
        return wc.getWorkDays(begin, end);
    }

    /**
     * get account rid of weeekly report
     * @param loginId String
     * @return Long
     */
    protected Long getFillInAcntRid(String loginId) {
        if(isInOnlyOneAccount(loginId)) {
            return getFirstAcntRid(loginId);
        } else {
            return getObsAcntRid(loginId);
        }
    }

    /**
     * assert the labor with loginId as <loginId> in and only in one account.
     * @param loginId String
     * @return boolean
     */
    private boolean isInOnlyOneAccount(String loginId) {
        String sql = "select t.login_id " +
                     "from pms_labor_resources t " +
                     "where t.login_id ='" + loginId +"' " +
                     "group by t.login_id having count(t.acnt_rid) = 1";
        try {
            RowSet rs = this.getDbAccessor().executeQuery(sql);
            if(rs.next()) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException(ex);
        }
    }

    /**
     * get the first account rid, if exist.
     * @param loginId String
     * @return Long
     */
    private Long getFirstAcntRid(String loginId) {
        String sql = "select t.acnt_rid " +
                     "from pms_labor_resources t " +
                     "where t.login_id ='" + loginId +"'";
        try {
            Long acntRid = null;
            RowSet rs = this.getDbAccessor().executeQuery(sql);
            if(rs.next()) {
                acntRid = new Long(rs.getLong("acnt_rid"));
            }
            return acntRid;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException(ex);
        }
    }

    /**
     * get account rid which has a account id like employee's department code.
     * @param loginId String
     * @return Long
     */
    private Long getObsAcntRid(String loginId) {
        String sql = "select a.rid from essp_hr_employee_main_t h, upms_loginuser l, pms_acnt a,essp_upms_unit u " +
                     "where h.code = l.user_id "+
                     "and h.dept = u.unit_id " +
                     "and u.unit_code = a.acnt_id " +
                     "and l.login_id = '" + loginId + "'";
        try {
            Long acntRid = null;
            RowSet rs = this.getDbAccessor().executeQuery(sql);
            if(rs.next()) {
                acntRid = new Long(rs.getLong("rid"));
            }
            return acntRid;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException(ex);
        }
    }

    /**
     * get leave hours by loginId and leave day.
     * @param loginId String
     * @param day Calendar
     * @return BigDecimal
     */
    private BigDecimal getConfirmedLeaveHoursInDay(String loginId, Calendar day) {
        String sql = "select sum(t2.hours) as hours " +
                     "from tc_leave_main t1 left join tc_leave_detail t2 " +
                     "on t1.rid = t2.leave_id " +
                     "where t1.login_id='" + loginId + "' " +
                     "and t1.status !='"+Constant.STATUS_ABORTED+"' " +
                     "and to_char(t2.leave_day,'" + DATE_STYLE + "') = '" +
                     comDate.dateToString(day.getTime(), DATE_STYLE) + "'";

        log.info("sum leave hours:" + sql);
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            while (rt.next()) {
                double hours = rt.getDouble("hours");
                return new BigDecimal(hours);
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return DtoWeeklyReport.BIG_DECIMAL_0;
    }

    /**
     * get over time hours by loginId and over time day.
     * @param loginId String
     * @param day Calendar
     * @return BigDecimal
     */
    private BigDecimal getConfirmedOverTimeHoursInDay(String loginId, Calendar day) {
        String sql = "select sum(t2.hours) as hours " +
                     "from tc_overtime t1 left join tc_overtime_detail t2 " +
                     "on t1.rid = t2.overtime_id " +
                     "where t1.login_id='" + loginId + "' " +
                     "and t1.status !='"+Constant.STATUS_ABORTED+"' " +
                     "and to_char(t2.overtime_day,'" + DATE_STYLE + "') = '" +
                     comDate.dateToString(day.getTime(), DATE_STYLE) + "'";

        log.info("sum overtime hours:" + sql);
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            while (rt.next()) {
                double hours = rt.getDouble("hours");
                return new BigDecimal(hours);
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return DtoWeeklyReport.BIG_DECIMAL_0;
    }

    /**
     * get user's in date and out date.
     * @param userId String
     * @return Date[]
     */
    private Date[] getInOutDate(String userId){
        String sql = "select hr.INDATE as inDate, hr.OUTDATE as outDate from essp_hr_employee_main_t hr, upms_loginuser login " +
                     "where hr.code=login.user_id and login.login_id='"+userId+"' ";
        try {
            RowSet rset = getDbAccessor().executeQuery(sql);
            if (rset.next()) {
                Date inDate = rset.getDate("inDate");
                Date outDate = rset.getDate("outDate");
                return new Date[] {
                        inDate, outDate};
            }else{
                return null;
            }
        } catch (Exception ex) {
            throw new BusinessException("E00000","Error when get the inDate and outDate of user - " + userId,ex);
        }
    }

    /**
     * exchange calendar date to weekly report date.
     * @param day Calendar
     * @return int
     */
    protected int getDayOfWeek(Calendar day) {
        int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
        case Calendar.SATURDAY:
            dayOfWeek = DtoWeeklyReport.SATURDAY;
            break;
        case Calendar.SUNDAY:
            dayOfWeek = DtoWeeklyReport.SUNDAY;
            break;
        case Calendar.MONDAY:
            dayOfWeek = DtoWeeklyReport.MONDAY;
            break;
        case Calendar.TUESDAY:
            dayOfWeek = DtoWeeklyReport.TUESDAY;
            break;
        case Calendar.WEDNESDAY:
            dayOfWeek = DtoWeeklyReport.WEDNESDAY;
            break;
        case Calendar.THURSDAY:
            dayOfWeek = DtoWeeklyReport.THURSDAY;
            break;
        case Calendar.FRIDAY:
            dayOfWeek = DtoWeeklyReport.FRIDAY;
            break;
        }
        return dayOfWeek;
    }

    public static void main(String[] args) {
        String fillFalseStr = "fillfalse";
        String confirmFalseStr = "confirmfalse";
        String helpStr = "1 parameter is required:\r\n\t" +
                         "HR account rid (Long)\r\n" +
                         "4 parameters are optional:\r\n\t" +
                         "1. process period offset (Integer, default:0)\r\n\t" +
                         "2. period type (week/month, default:week)\r\n\t" +
                         "3. do not fill weekly report (fillfalse)\r\n\t" +
                         "4. do not confirm weekly report (confirmfalse)\r\n";

        Long hrAcntRid = new Long(883);
        String periodType = PERIOD_TYPE_WEEK;
        int offset = 0;
        boolean fillFlage = true;
        boolean confirmFlage = true;

        if(args.length < 1) {
            helpStr += "\r\n Please input HR account rid (Long)!";
            System.out.println(helpStr);
            return;
        } else {
            try {
                hrAcntRid = Long.valueOf(args[0]);
            } catch(NumberFormatException ex) {
                helpStr += "\r\n Please input HR account rid (Long) correctly!";
                System.out.println(helpStr);
            }
        }
        for(int i = 1; i < args.length; i++) {
            try {
                int off = Integer.parseInt(args[i]);
                offset = off;
            } catch(NumberFormatException ex) {
            }
            if(fillFalseStr.equalsIgnoreCase(args[i])) {
                fillFlage = false;
            } else if(confirmFalseStr.equalsIgnoreCase(args[i])) {
                confirmFlage = false;
            } else if(PERIOD_TYPE_MONTH.equalsIgnoreCase(args[i])) {
                periodType = PERIOD_TYPE_MONTH;
            }

        }
        AutoProcessWeeklyReport ap = new AutoProcessWeeklyReport(hrAcntRid, periodType, offset);
        try {
            ap.getDbAccessor().followTx();
            ap.execute(fillFlage, confirmFlage);
            ap.getDbAccessor().endTxCommit();
            System.out.println("Fill and confirm over!");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ap.getDbAccessor().endTxRollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

}
