package server.essp.tc.mail.logic;

import java.sql.ResultSet;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.Calendar;
import server.framework.logic.AbstractBusinessLogic;
import itf.hr.LgHrUtilImpl;
import java.util.ArrayList;
import java.util.HashMap;
import server.essp.tc.mail.genmail.contbean.DateDul;
import java.util.Map;
import java.util.Iterator;
import server.framework.common.BusinessException;
import itf.hr.HrFactory;
import c2s.essp.common.calendar.WrokCalendarFactory;
import com.wits.util.comDate;
import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;
import java.util.Date;
import c2s.essp.attendance.Constant;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 *此类用于取得周报的需要催促的数据（可分月和周两种情况）
 * author:Robin.Zhang
 */
public class LgGetWeeklyReportData extends AbstractBusinessLogic {
    HashMap hm = new HashMap();
    ResultSet rs1 = null;
    ResultSet rsFilled = null;

    Calendar calendar = Calendar.getInstance();
    WorkCalendar wc = WrokCalendarFactory.serverCreate();
    String sqla = null;
    String sqlb = null;

    String begin = null;
    String end = null;
    String beginDate = null;
    String endDate = null;
    String type = null;
    int iYearBegin;
    int iMonthBegin;
    int iDayBegin;
    int iYearEnd;
    int iMonthEnd;
    int iDayEnd;


    public LgGetWeeklyReportData() {

    }

    /**
     *此方法对月进行处理
     * 参数：accountId　项目ID
     * 　　　isWeekly　表示是周还是月　true表示为周　false表示为月
     *      offset 月份偏移量，0　表示当月
     * 返回：所有数据用一个HashMap装载
     */
    public HashMap getWeeklyReportData(String accountId, boolean isWeekly,
                                       int offset) {
        if (accountId == null) {
            throw new BusinessException("", "illegal arguments!");
        }
        if (isWeekly) {
            this.getWeeklyReportDataForWeekly(calendar, accountId, offset);
        } else {
            this.getWeeklyReportDataForMonth(accountId, offset);
        }

        return hm;
    }

    /**
     *此方法对月进行处理
     * 参数：accountId　项目ID
     *      offset 月份偏移量，0　表示当月
     */
    public void getWeeklyReportDataForMonth(String accountId, int offset) {

        Calendar[] period = WorkCalendar.getNextBEMonthDay(calendar, offset);

        ArrayList weekList = (ArrayList) WorkCalendar.getBEWeekListMax(period[0],
            period[1]);
        for (int i = 0; i < weekList.size(); i++) {
            Calendar aweekBegin = ((Calendar[]) weekList.get(i))[0];
            Calendar aweekEnd = ((Calendar[]) weekList.get(i))[1];
            System.out.println(comDate.dateToString(aweekBegin.getTime()));
            if (aweekEnd.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
                aweekEnd.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                this.getWeeklyReportDataForWeekly(aweekBegin, accountId, 0);
            }
        }

    }

    /** 此方法只对周进行处理
     * 参数：cal日期为系统运行的当前日期
     * 　　　accountId　项目ID
     *      offset 周偏移量，0　表示本周
     */
    public void getWeeklyReportDataForWeekly(Calendar cal, String accountId,
                                             int offset) {
        //初始化全项目的员工
        HashMap allEmployee = new GetEmployeeList().getAllEmployee(accountId);

        ArrayList alFilled = new ArrayList(); //记录在时间段内填过并通过的人
        Iterator iter;

        Calendar[] beDay = wc.getNextBEWeekDay(cal, offset);
        //全周放假时不做处理， add by lipengxu
        int swh = getStandardWorkHours(beDay[0],beDay[1]);
        if(swh == 0) {
            return;
        }
        iYearBegin = beDay[0].get(Calendar.YEAR);
        iMonthBegin = beDay[0].get(Calendar.MONTH) + 1;
        iDayBegin = beDay[0].get(Calendar.DATE);
        iYearEnd = beDay[1].get(Calendar.YEAR);
        iMonthEnd = beDay[1].get(Calendar.MONTH) + 1;
        iDayEnd = beDay[1].get(Calendar.DATE);

        begin = iYearBegin +
                (iMonthBegin < 10 ? ("0" + iMonthBegin) :
                 Integer.toString(iMonthBegin))
                +
                (iDayBegin < 10 ? ("0" + iDayBegin) : Integer.toString(iDayBegin));
        end = iYearEnd +
              (iMonthEnd < 10 ? ("0" + iMonthEnd) : Integer.toString(iMonthEnd))
              + (iDayEnd < 10 ? ("0" + iDayEnd) : Integer.toString(iDayEnd));
        System.out.println(begin + "~~~~" + end);

        //此句查出在时间段内填写但并未LOCKED或被REJECTED的人
        sqla =
            "select tcsum.user_id,tcsum.begin_period,tcsum.end_period,tcsum.confirm_status "
            + "from tc_weekly_report_sum tcsum where "
            +
            "tcsum.confirm_status<>'Locked' and tcsum.confirm_status<>'Confirmed' "
            + "and to_char(tcsum.begin_period ,'yyyymmdd')='" + begin + "'";
        System.out.println(sqla);

        //此句查出在时间段内已经填过周报并且LOCKED或被CONFIRMED的人
        sqlb = "select tcsum.user_id "
               + " from tc_weekly_report_sum tcsum where "
               + "to_char(tcsum.begin_period ,'yyyymmdd')='" + begin + "'"
               +
            "and (tcsum.confirm_status='Locked' or tcsum.confirm_status='Confirmed')";
        System.out.println(sqlb);

        try {

            LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();

            rs1 = this.getDbAccessor().executeQuery(sqla);
            rsFilled = this.getDbAccessor().executeQuery(sqlb);

            //填过周报且LOCKED或CONFIRMED的员工存入一个LIST
            while (rsFilled.next()) {
                alFilled.add(rsFilled.getString(1));
            }

            while (rs1.next()) {

                String user = rs1.getString("USER_ID");
                //check the user is leave all week
                boolean isleave = checkLeaveAllPeriod(user,beDay[0].getTime(),beDay[1].getTime(),swh);
                if (allEmployee.isEmpty() == false && allEmployee.containsKey(user) && isleave == false) {
                    //如果存在此人，把这个人移掉，然后进行相应的处理，不至于重复
                    allEmployee.remove(user);

                    String email = ihui.getUserEmail(user);
                    beginDate = comDate.dateToString(rs1.getDate("begin_period"));
                    endDate = comDate.dateToString(rs1.getDate("end_period"));
                    type = rs1.getString("confirm_status");
                    //System.out.println(beginDate+"~~~~~~~"+endDate);   //for Test
                    DateDul dd = new DateDul();
                    dd.setBeginDate(beginDate);
                    dd.setEndDate(endDate);
                    dd.setType(type);

                    if (hm.isEmpty() || hm.get(user) == null) {
                        ArrayList al = new ArrayList();
                        ContentBean cb = new ContentBean();
                        String userName = ihui.getChineseName(user);
                        if (userName != null) {
                            cb.setUser(userName);
                        } else {
                            cb.setUser(user);
                        }
                        cb.setEmail(email);
                        al.add(dd);
                        cb.setMailcontent(al);
                        hm.put(user, cb);
                    } else {
                        ArrayList al = new ArrayList();
                        ContentBean cb = (ContentBean) hm.get(user); //.getArrayList().add(dd);
                        al = cb.getMailcontent();
                        al.add(dd);
                        cb.setMailcontent(al);
                        hm.remove(user);
                        hm.put(user, cb);
                    }

                } //非本项目内员工不作处理

            } //while end

            //移去通过周报的员工，剩下的就是未填周报的员工了
            for (int i = 0; i < alFilled.size(); i++) {
                String t = (String) alFilled.get(i);
                if (!allEmployee.isEmpty() && allEmployee.get(t) != null) {
                    allEmployee.remove(t);
                }
            }

            //把未填写周报的员工进行处理
            iter = allEmployee.entrySet().iterator();
            for (; iter.hasNext(); ) {
                Map.Entry item = (Map.Entry) iter.next();
                String t_user = item.getKey().toString();
                String t_email = item.getValue().toString();

                beginDate = iYearBegin + "-" +
                            (iMonthBegin < 10 ? ("0" + iMonthBegin) :
                             Integer.toString(iMonthBegin))
                            + "-" +
                            (iDayBegin < 10 ? ("0" + iDayBegin) :
                             Integer.toString(iDayBegin));
                endDate = iYearEnd + "-" +
                          (iMonthEnd < 10 ? ("0" + iMonthEnd) :
                           Integer.toString(iMonthEnd))
                          + "-" +
                          (iDayEnd < 10 ? ("0" + iDayEnd) :
                           Integer.toString(iDayEnd));

                DateDul dd = new DateDul();
                dd.setBeginDate(beginDate);
                dd.setEndDate(endDate);
                dd.setType("Not Fill");

                if (hm.isEmpty() || hm.get(t_user) == null) {
                    ArrayList al = new ArrayList();
                    ContentBean cb = new ContentBean();
                    String userName = ihui.getChineseName(t_user);
                    if (userName != null) {
                        cb.setUser(userName);
                    } else {
                        cb.setUser(t_user);
                    }
                    cb.setEmail(t_email);
                    al.add(dd);
                    cb.setMailcontent(al);
                    hm.put(t_user, cb);
                } else {
                    ArrayList al = new ArrayList();
                    ContentBean cb = (ContentBean) hm.get(t_user);
                    al = cb.getMailcontent();
                    al.add(dd);
                    cb.setMailcontent(al);
                    hm.remove(t_user);
                    hm.put(t_user, cb);
                }
            } //for end

        } catch (Throwable tx) {
            String msg = "error get all WeeklyReport hasten data!";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }

    }

    private boolean checkLeaveAllPeriod(String loginId, Date begin, Date end, double workHours) {
        String sql = "select sum(t2.hours) as hours " +
                     "from tc_leave_main t1 left join tc_leave_detail t2 " +
                     "on t1.rid = t2.leave_id " +
                     "where t1.login_id='" + loginId + "' " +
                     "and t1.status !='"+Constant.STATUS_ABORTED+"' " +
                     "and to_char(t2.leave_day,'" + comDate.pattenDate + "') >= '" +
                     comDate.dateToString(begin, comDate.pattenDate) + "' "+
                     "and to_char(t2.leave_day,'" + comDate.pattenDate + "') <= '" +
                     comDate.dateToString(end, comDate.pattenDate) + "' ";

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




    //For Test
    public static void main(String[] args) {
        String timeStr = (new SimpleDateFormat("dd MMMM yyyy")).format(new Date());
        System.out.println(timeStr);
        LgGetWeeklyReportData gwrd = new LgGetWeeklyReportData();
        SendHastenMail shm = new SendHastenMail();

        //使用前请先设置Debug Mail !!!
        try {
            shm.sendHastenMail("mail/template/tc/reportMailTemplate.htm",
                               "Test",
                               gwrd.getWeeklyReportData("883", true, -2));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
