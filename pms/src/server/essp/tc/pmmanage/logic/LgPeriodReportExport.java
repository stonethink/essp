package server.essp.tc.pmmanage.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import c2s.dto.ITreeNode;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import java.util.ArrayList;
import java.util.Date;
import server.framework.common.BusinessException;
import java.util.List;
import server.essp.tc.common.LgTcCommon;
import server.essp.tc.common.LgWeeklyReportSumExtend;
import c2s.dto.DtoTreeNode;
import c2s.essp.tc.weeklyreport.DtoWeekAllocateHour;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import java.math.BigDecimal;
import com.wits.util.comDate;
import javax.sql.RowSet;
import db.essp.pms.Account;
import java.util.Comparator;
import java.util.Collections;
import server.essp.tc.hrmanage.logic.LgTcLaborReport;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgPeriodReportExport extends AbstractESSPLogic {
    private Date beginDate;
    private Date endDate;
    private int pCount;
    LgWeeklyReportSumExtend lgWeeklyReportSumExtend = new
        LgWeeklyReportSumExtend();
    public LgPeriodReportExport() {
    }

    public int getPersonCount() {
        return this.pCount;
    }

    public ITreeNode getReportTree(String acntRid, Date begin, Date end) {
        beginDate = begin;
        endDate = end;
        List reports = listReports(acntRid, begin, end);
        List users = listUsers(acntRid, begin, end);
        pCount = users.size();
        DtoWeekAllocateHour dtoRoot = new DtoWeekAllocateHour();
        dtoRoot.setUserId("Sum");
        initHours(dtoRoot);
        DtoTreeNode root = new DtoTreeNode(dtoRoot);
        for (int i = 0; i < pCount; i++) {
            DtoWeekAllocateHour userData = (DtoWeekAllocateHour) users.get(i);
            DtoTreeNode userNode = new DtoTreeNode(userData);
            for (int j = 0; j < reports.size(); j++) {
                DtoWeeklyReport reportData = (DtoWeeklyReport) reports.get(j);
                if (reportData.getUserId().equals(userData.getUserId())) {
                    DtoTreeNode reportNode = new DtoTreeNode(reportData);
                    sumDtoData(userData, reportData);
                    userNode.addChild(reportNode);
                    String userName = (reportData.getAcntName());
                    if(userName != null && userName.equals("") == false) {
                        reportData.setUserId(reportData.getAcntName());
                    }
                }
            }
            userData.setActualHour(userData.getSumHour());
            userData.setOvertimeSum(getOverTimeSum(userData.getUserId(),
                acntRid, userData.getBeginPeriod(), userData.getEndPeriod()));
            userData.setLeaveSum(getUserLeaveSum(userData.getUserId(),
                                                 acntRid,
                                                 userData.getBeginPeriod(),
                                                 userData.getEndPeriod()));
            sumDtoData(dtoRoot, userData);
            dtoRoot.setOvertimeSum(userData.getOvertimeSum().add(dtoRoot.
                getOvertimeSum()));
            dtoRoot.setLeaveSum(userData.getLeaveSum().add(dtoRoot.getLeaveSum()));
            root.addChild(userNode);
            if (userNode.children().size() > 0) {
                userData.setUserId(((DtoWeeklyReport) userNode.getChildAt(0).
                                    getDataBean()).getUserId());
            }
        }
        dtoRoot.setActualHour(dtoRoot.getSumHour());
        Collections.sort(root.children(),new UserNameComparator());
        return root;
    }

    private void sumDtoData(DtoHoursOnWeek sumData, DtoHoursOnWeek data) {
        for (int i = 0; i <= 7; i++) {
            sumData.setHour(i, data.getHour(i).add(sumData.getHour(i)));
        }
    }

    /**列出时间区间内userId的所有周报*/
    public List listReports(String acntRid, Date beginPeriod,
                            Date endPeriod) {
        List weeklyReport = new ArrayList();
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];

        String sql =
            "select t.user_id as user_Id, emp.chinese_name as acnt_Name,t.is_activity as is_Activity, a.name as activity_Name,code.value as code_Value_Name,t.begin_period as begin_Period,t.end_period as end_Period,"
            + "t.job_description as job_Description,t.mon_hour as mon_Hour,t.tue_hour as tue_Hour,t.wed_hour as wed_Hour,"
            + "t.thu_hour as thu_Hour,t.fri_hour as fri_Hour,t.sat_hour as sat_Hour,t.sun_hour as sun_Hour,t.comments as comments "
            + "from (((tc_weekly_report t left join tc_weekly_report_sum s "
            + "on t.user_id=s.user_id and t.acnt_rid=s.acnt_rid and t.begin_period=s.begin_period and t.end_period=s.end_period	) "
            + "left join pms_activity a on a.activity_rid = t.activity_rid and a.acnt_rid = t.acnt_rid "
            + ") left join (essp_hr_employee_main_t emp left join upms_loginuser log on emp.code = log.user_id) on log.login_id = t.user_id "
            + ")left join sys_code_value code on code.rid = t.code_value_rid "
            + "where s.confirm_status='Confirmed' and t.acnt_rid='" + acntRid +
            "' " +
            "and to_char(t.begin_period,'yyyy-MM-dd')<='" +
            comDate.dateToString(endPeriod) +
            "' and to_char(t.end_period,'yyyy-MM-dd')>='" +
            comDate.dateToString(beginPeriod) + "' order by t.begin_period desc";
        try {
            List list = getDbAccessor().executeQueryToList(sql, DtoWeeklyReport.class);
            for (int i = 0; i < list.size(); i++) {
                DtoWeeklyReport dto = (DtoWeeklyReport) list.get(i);
                weeklyReport.add(checkData(dto));
            }
        } catch (Exception ex) {
            throw new BusinessException("E000",
                                        "Error when Export weekly report.", ex);
        }

        return weeklyReport;
    }

    private List listUsers(String acntRid, Date beginPeriod, Date endPeriod) {
        List users = new ArrayList();
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];

        String sql =
            "select t.user_id as user_Id from tc_weekly_report t left join tc_weekly_report_sum s "
            + "on t.user_id=s.user_id and t.acnt_rid=s.acnt_rid and t.begin_period=s.begin_period and t.end_period=s.end_period		"
            + "where s.confirm_status='Confirmed' and t.acnt_rid='" + acntRid +
            "' " +
            "and to_char(t.begin_period,'yyyy-MM-dd')<='" +
            comDate.dateToString(endPeriod) +
            "' and to_char(t.end_period,'yyyy-MM-dd')>='" +
            comDate.dateToString(beginPeriod) + "'"
            + " group by t.user_id";

        try {
            List list = getDbAccessor().executeQueryToList(sql,
                DtoWeekAllocateHour.class);
            for (int i = 0; i < list.size(); i++) {
                DtoWeekAllocateHour dto = (DtoWeekAllocateHour) list.get(i);
                users.add(initHours(dto));
            }
        } catch (Exception ex) {
            throw new BusinessException("E000",
                                        "Error when Export weekly report.", ex);
        }
        return users;

    }

    private DtoWeekAllocateHour initHours(DtoWeekAllocateHour dto) {
        for (int i = 0; i <= 7; i++) {
            dto.setHour(i, new BigDecimal(0));
        }
        dto.setOvertimeSum(new BigDecimal(0));
        dto.setLeaveSum(new BigDecimal(0));
        dto.setBeginPeriod(beginDate);
        dto.setEndPeriod(endDate);
        return dto;
    }

    private DtoWeeklyReport checkData(DtoWeeklyReport dto) {

        for (int i = 0; i < 7; i++) {
            if (dto.getHour(i) == null) {
                dto.setHour(i, new BigDecimal(0));
            }
        }

        //时间限制在指定范围内
        Long offset = new Long((beginDate.getTime() -
                                dto.getBeginPeriod().getTime()) /
                               (1000 * 60 * 60 * 24));
        int offValue = offset.intValue();
        while (offValue > 0) {
            dto.setHour(offValue, new BigDecimal(0));
            offValue--;
        }
        offset = new Long((dto.getEndPeriod().getTime() - endDate.getTime()) /
                          (1000 * 60 * 60 * 24));
        offValue = offset.intValue();
        while (offValue > 0) {
            dto.setHour(7 - offValue, new BigDecimal(0));
            offValue--;
        }

        BigDecimal sumHour = new BigDecimal(0);
        for (int i = 0; i < 7; i++) {
            sumHour = sumHour.add(dto.getHour(i));
        }
        dto.setSumHour(sumHour);
        return dto;
    }

    /**
     * 返回某人特定时间区域内加班的统计(已通过审核的).
     * @return VbOverTimeSum
     */
    public BigDecimal getOverTimeSum(String loginId, String acntRid,
                                     Date beginPeriod, Date endPeriod) {
        if (loginId == null) {
            throw new BusinessException("TC_OVER_0011",
                                        "login id can not be null!");
        }
        String sql = "select sum(d.hours) as sumhours from tc_overtime_detail d left join tc_overtime t on d.overtime_id=t.rid "
                     + " where t.status='Finished' and t.login_id='" + loginId +
                     "' and t.acnt_rid='" + acntRid + "' "
                     + " and (to_char(d.overtime_day,'yyyy-MM-dd') >= '" +
                     comDate.dateToString(beginPeriod) + "' "
                     + " and to_char(d.overtime_day,'yyyy-MM-dd') <='" +
                     comDate.dateToString(endPeriod) + "')";
        try {
            RowSet rs = getDbAccessor().executeQuery(sql);
            BigDecimal hours = new BigDecimal(0);
            if (rs.next()) {
                hours = rs.getBigDecimal("sumhours");
            }
            if (hours == null) {
                return new BigDecimal(0);
            }
            return hours;
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0012",
                                        "error summing the overtime of [" +
                                        loginId + "]!", ex);
        }

    }

    public Account getAccount(String acntRid) {
        Account acnt = new Account();
        String sql =
            "select emp.chinese_name as manager, t.acnt_id as id, t.acnt_name as name "
            + "from pms_acnt t left join (essp_hr_employee_main_t emp left join upms_loginuser log on log.user_id = emp.code) "
            + "on log.login_id = t.acnt_manager "
            + "where t.rid = '" + acntRid + "'";
        try {
            List list = getDbAccessor().executeQueryToList(sql, Account.class);
            if (list.size() == 1) {
                acnt = (Account) list.get(0);
            }
        } catch (Exception ex) {
            throw new BusinessException("TC_EXPORT_0012",
                                        "error get Account !", ex);
        }
        return acnt;
    }

    //查找人员特定时间区域内请假的总时间
    private BigDecimal getUserLeaveSum(String loginId, String acntRid,
                                       Date beginPeriod, Date endPeriod) {
        String sql = "select sum(d.hours) as sumhours from tc_leave_detail d left join tc_leave_main t on d.leave_id=t.rid "
                     + " where t.status='Finished' and t.login_id='" + loginId +
                     "' and t.acnt_rid='" + acntRid + "' "
                     + " and (to_char(d.leave_day,'yyyy-MM-dd') >= '" +
                     comDate.dateToString(beginPeriod) + "' "
                     + " and to_char(d.leave_day,'yyyy-MM-dd') <='" +
                     comDate.dateToString(endPeriod) + "')";

        try {
            RowSet rs = getDbAccessor().executeQuery(sql);
            BigDecimal hours = new BigDecimal(0);
            if (rs.next()) {
                hours = rs.getBigDecimal("sumhours");
            }
            if (hours == null) {
                return new BigDecimal(0);
            }
            return hours;

        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }
    private static class UserNameComparator implements Comparator{
            public boolean equals(Object obj) {
                return false;
            }

            public int compare(Object o1, Object o2) {
                if(o1 instanceof ITreeNode && o2 instanceof ITreeNode){
                    ITreeNode node1 = (ITreeNode)o1;
                    ITreeNode node2 = (ITreeNode)o2;
                    DtoWeekAllocateHour dto1 = (DtoWeekAllocateHour)node1.getDataBean();
                    DtoWeekAllocateHour dto2 = (DtoWeekAllocateHour)node2.getDataBean();
                    return dto2.getUserId().compareTo(dto1.getUserId());
                }else{
                    return 0;
                }
            }

        }


    public static void main(String[] args) {
        LgPeriodReportExport lgperiodreportexport = new LgPeriodReportExport();
        Date begin = new Date(2007-1900,0,26);
        Date end   = new Date(2007-1900,1,25);
        lgperiodreportexport.listReports("6022", begin, end);
    }
}
