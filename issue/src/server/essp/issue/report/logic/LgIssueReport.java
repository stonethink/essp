package server.essp.issue.report.logic;

import server.framework.logic.AbstractBusinessLogic;
import server.essp.issue.report.viewbean.VbIssueReport;
import server.essp.issue.report.viewbean.VbIssueReportList;
import server.essp.issue.report.form.AfReportForm;
import server.essp.issue.common.logic.AbstractISSUELogic;
import java.util.Date;
import java.util.Calendar;
import server.framework.common.BusinessException;
import com.wits.util.comDate;
import java.util.List;
import java.sql.Connection;
import server.framework.hibernate.HBComAccess;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import net.sf.hibernate.*;
import db.essp.issue.*;
import server.essp.issue.common.logic.LgAccount;

public class LgIssueReport extends AbstractISSUELogic {
    /**
     * 获得进入Report页面所需内容
     * Exec:{
     * 1. 获取当前用户有权限所有AccountList(分别以UserType调用共用方法)
     * 2. 获取所有有效的IssueType
     * 3. 生成IssueStatus选择列表
     * 4. 依据accountId生成Defalut之Begin/End的值：
     * 　 当未选定项目时为本年起始日(1/1)，如果选定指定项目即为项目的起始日；End的缺省值设置为当前系统日期
     *}
     * @return VbIssueReport
     */
    public VbIssueReport reportPrepare() throws
        BusinessException {
        VbIssueReport issueReport = new VbIssueReport();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date firstDayOfYear = calendar.getTime();
        String sfirstDayOfYear = comDate.dateToString(firstDayOfYear);
        Date today = new Date();
        String sToday = comDate.dateToString(today);
        issueReport.setDateBegin(sfirstDayOfYear);
        issueReport.setDateEnd(sToday);
        List accountOptionsList = this.getAccountOptions();
        issueReport.setAccountList(accountOptionsList);
        List typeList = this.typeAll();
        issueReport.setTypeList(typeList);
        return issueReport;
    }


    public List typeAll() throws BusinessException {
        int size = 0;
        HashMap hash = new HashMap();
        List typeList = new ArrayList();
        List temList = this.listAll();
        if (temList != null) {
            size = temList.size();
            for (int i = 0; i < size; i++) {
                Issue issue = (Issue) temList.get(i);
                if (issue.getIssueType() != null) {
                    if (hash.get(issue.getIssueType()) == null) {
                        hash.put(issue.getIssueType(), issue.getIssueType());
                    }
                }
            }
            Iterator it = hash.values().iterator();
            for (; it.hasNext(); ) {
                String typeName = (String) it.next();
                typeList.add(typeName);
            }
        }
        return typeList;
    }


    public List listAll() throws BusinessException {
        List result = new ArrayList();
        try {
            //查询所有的Issue Define
            Session session = this.getDbAccessor().getSession();
            String str = "from Issue";
            Query q = session.createQuery(str);
            result = q.list();
            return result;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.issue.list.exception",
                                        "List issue define error!");
        }
    }

    /**
     * 根据查询条件AfReportForm查询生成报表
     * 查询方法待定？？
     * @param param0 AfReportForm
     * @return VbIssueReportList{
     *  1. List: VbPeriodReport
     * }
     */
    public void processPre(AfReportForm form, List typeParam) {
        java.sql.Connection con = this.getDbAccessor().getConnect();
        String accountId = form.getAccountId();
        int idint = (new Integer(accountId)).intValue();
        String userName = this.getUser().getUserLoginId();
        Date dateBegin = comDate.toDate(form.getDateBegin());
        Date dateEnd = comDate.toDate(form.getDateEnd());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String beginDate = format.format(dateBegin);
        String endDate = format.format(dateEnd);
        String dateBy = "";
        if (form.getDateBy().equalsIgnoreCase("Weekly")) {
            dateBy = "1";
        } else
        if (form.getDateBy().equalsIgnoreCase("Monthly")) {
            dateBy = "2";
        } else
        if (form.getDateBy().equalsIgnoreCase("Seasonly")) {
            dateBy = "3";
        }
        try {
            String sql =
                "delete from ISSUE_REPORTSTATUS_SUM it where it.LOGINNAME = " +
                "'" +
                userName + "'" + " and it.ACNT_ID = " + idint;
            try {
                this.getDbAccessor().executeUpdate(sql);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            CallableStatement proc = con.prepareCall(
                "{call ESSP_ISSUE_REPORT(?,?,?,?,?,?)}");
            proc.setString(1, userName);
            proc.setLong(2, idint);
            proc.setString(3, beginDate);
            proc.setString(4, endDate);
            proc.setString(5, dateBy);

            int size = typeParam.size();
            for (int i = 0; i < size; i++) {
                String typeName = (String) typeParam.get(i);
                proc.setString(6, typeName);
                try {
                    proc.executeQuery();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据查询条件AfReportForm查询生成报表
     * 查询方法待定？？
     * @param param0 AfReportForm
     * @return VbIssueReportList{
     *  1. List: VbPeriodReport
     * }
     */
    public AfReportForm paramPre(AfReportForm form, List typeParam) {
        LgAccount lgAccount = new LgAccount();
        String account = lgAccount.getAccountId(form.getAccountId());

        int size = typeParam.size();
        String typeName = "";
        for (int i = 0; i < size; i++) {
            typeName += (String) typeParam.get(i) + "  ";
        }
        String userName = this.getUser().getUserLoginId();
        form.setUserName(userName);
        form.setIssueType(typeName);
        form.setAccount(account);
        return form;
    }

    /** @link dependency */
    /*# VbIssueReport lnkVbIssueReport; */

    /** @link dependency */
    /*# VbIssueReportList lnkVbIssueReportList; */
}
