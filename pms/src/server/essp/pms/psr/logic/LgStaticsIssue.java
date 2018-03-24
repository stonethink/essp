package server.essp.pms.psr.logic;

import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import c2s.dto.DtoUtil;
import com.wits.util.comDate;
import db.essp.pms.PmsStatusReportIssue;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;


public class LgStaticsIssue extends AbstractESSPLogic {
    /**
     * ��ͳ��ֵ��Ӧ������
     */
    private static final String PRPERTY_CLOSECOUNT = "closecount";
    private static final String PRPERTY_ADDCOUNT = "addcount";
    private static final String PRPERTY_HANDLECOUNT = "handlecount";
    private static final String PRPERTY_ABNORMALCOUNT = "abnomalcount";
    private static final String PRPERTY_TOTALCOUNT = "totalcount";
    //ͳ������
    private Date start;
    private Date finish;
    private Long acntRid;

    public void staticIssueData(Long acntRid, Date start, Date finish) throws
        Exception {
        this.start = start;
        this.finish = finish;
        this.acntRid = acntRid;

        statNewInWeek();

        statClosedInWeek();

        statHandleInWeek();

        statAbnormalInWeek();

        statTotalInWeek();
    }

    /**
     * �����������������͵�����
     */
    private void statNewInWeek() {
        String sql1 =
            "select t.type_name as issuetype,count(*) from issue t where to_char(t.filledate,'yyyy-mm-dd')>='" +
            comDate.dateToString(start) +
            "' and to_char(t.filledate,'yyyy-mm-dd')<='" +
            comDate.dateToString(finish) +
            "' and t.account_id='" + acntRid + "'"
            + " group by t.type_name";
        addDataToBean(sql1, PRPERTY_ADDCOUNT);
    }

    private void statClosedInWeek() {
        /*
                 ���ܽ᰸
         */
        String sql2 = "select i.type_name as issuetype,count(*) from issue_conclusion t,issue i where to_char(t.confirm_date,'yyyy-mm-dd')>='" +
                      comDate.dateToString(start) +
                      "' and to_char(t.confirm_date,'yyyy-mm-dd')<='" +
                      comDate.dateToString(finish) +
                      "'" +
                      "and i.account_id='" + acntRid +
                      "' and i.rid=t.rid group by i.type_name";
        addDataToBean(sql2, PRPERTY_CLOSECOUNT);

    }

    private void statHandleInWeek() {
        /*
         �����е�
         */
        String sql3 =
            "select t.type_name as issuetype,count(*) from issue t where t.issue_status<>'Closed'and t.account_id='" +
            acntRid + "' group by t.type_name";
        addDataToBean(sql3, PRPERTY_HANDLECOUNT);
    }

    private void statAbnormalInWeek() {
        /*
           �ڵ�ǰʱ���쳣��ISSUE����
         */
        String sql4 =
            "select t.type_name as issuetype,count(*) from issue t where to_char(t.duedate,'yyyy-mm-dd')<='" +
            comDate.dateToString(finish) + "' and t.issue_status<>'Closed'"
            + "and t.account_id = '" + acntRid + "' group by t.type_name";
        addDataToBean(sql4, PRPERTY_ABNORMALCOUNT);
    }

    private void statTotalInWeek() {
        /*
          �ۼ�����
         */
        String sql5 =
            "select t.type_name as issuetype,count(*) from issue t where t.account_id='" +
            acntRid + "' group by t.type_name";
        addDataToBean(sql5, PRPERTY_TOTALCOUNT);
    }

    /**
     * �����ѯ��ÿ�����͵�ISSUE�Ĳ�ͬͳ��ֵ
     */
    private void addDataToBean(String sql, String statProperty) {
        ResultSet rs = this.getDbAccessor().executeQuery(sql);
        try {
            while (rs.next()) {
                Long count = rs.getLong("count(*)");
                String issueType = rs.getString("issuetype");
                PmsStatusReportIssue data = getStatDataInWeek(issueType);
                DtoUtil.setProperty(data, statProperty, count);
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }
    /**
     * �ȸ���IssueType,acntRid,��start,finish��DataBase�в��Ҷ�Ӧ��PmsStatusReportIssue
     * ��û�ҵ�����DataBase������һ��
     */
    private PmsStatusReportIssue getStatDataInWeek(String issueType) throws
         Exception {
        PmsStatusReportIssue data = (PmsStatusReportIssue)
                                    this.getDbAccessor().getSession().createQuery("from PmsStatusReportIssue t " +
            "where t.acntRid=:acntRid and t.type=:type " +
            "and t.startdate=:startdate and t.finishdate=:finishdate ")
            .setParameter("acntRid",acntRid)
            .setParameter("type",issueType)
            .setDate("startdate",start)
            .setDate("finishdate",finish)
            .uniqueResult();
        if(data == null){
            data = new PmsStatusReportIssue();
            data.setAcntRid(acntRid);
            data.setType(issueType);
            data.setStartdate(start);
            data.setFinishdate(finish);
            this.getDbAccessor().save(data);
        }
        return data;
    }
}
