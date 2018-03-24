package server.essp.issue.stat.logic;

import server.framework.common.BusinessException;

import server.essp.issue.stat.form.AfIssueStat;
import server.essp.issue.common.logic.AbstractISSUELogic;
import java.util.*;
import server.essp.issue.common.constant.Status;
import net.sf.hibernate.Session;
import net.sf.hibernate.Query;
import db.essp.issue.Issue;
import server.essp.issue.stat.viewbean.VbIssueTypeStat;
import com.wits.util.comDate;
import c2s.essp.common.account.IDtoAccount;
import server.essp.issue.common.logic.*;
import server.framework.common.Constant;
import db.essp.issue.*;
import server.framework.taglib.util.TagUtils;
import server.essp.issue.stat.viewbean.VbIssueStatPerAccount;
import net.sf.hibernate.*;
import server.essp.issue.stat.viewbean.VbIssueTypeStatList;
import server.essp.issue.stat.viewbean.VbIssueStatAccountByType;

public class LgIssueStat extends AbstractISSUELogic {
    private SortedMap records = new TreeMap(new IssueTypeComparator());
    private LgAccount lgAccount = new LgAccount();
    private LgIssueType issueTypeLogic = new LgIssueType();
    /**
     * ���ݵ�¼ID���û�����ͳ��,����Ա��Ȩ������Account�¸�״̬��Issue����
     * 1  Issue Statistic��
     * 1.1  ��ȡ��ǰ�û���Ȩ������AccountList(�ֱ���UserType���ù��÷���)
     * 1.1 ͳ�ƾ�AccountList������Ŀ��״̬������[N1]
     *}
     * [N1]��
     * 1. ͳ�Ƶļ�¼״̬Ϊ��ɾ��
     * 2. ��UserTypeΪ�ͻ�����ͳ��Vision To Customer��Y�ļ�¼
     * @param loginId String
     * @param userType String
     * @return List
     */
    public List statistic(String loginId,String userType){
        List result = new ArrayList();
        LgAccount lgAccount = new LgAccount();
        List accountList = lgAccount.getAccounts(userType,loginId);
        if(accountList == null  || accountList.size() == 0)
            return result;
        for(int i = 0;i < accountList.size() ;i ++){
            IDtoAccount account = (IDtoAccount) accountList.get(i);
            VbIssueStatPerAccount vb = new VbIssueStatPerAccount();
            vb.setAccountRid(account.getRid().toString());
            vb.setAccountCode(account.getId());
            vb.setAccountName(account.getName());
            List allIssue = listIssueInAccount(account.getRid());
            if(allIssue == null || allIssue.size() == 0)
                continue;
            for(int j = 0;j < allIssue.size() ;j ++){
                Issue issue = (Issue) allIssue.get(j);
                String issueType = issue.getIssueType();
                String status = issue.getIssueStatus();
                String scope = issue.getScope();
                if (!issueTypeLogic.isExistScope(issueType, scope, userType)) {
                    continue;
                }
                String statusBelongto = getStatusBelongTo(issueType,status);
                if(statusBelongto.equalsIgnoreCase(Status.RECEIVED)) {
                    vb.increReceivedCount(1);
                }
                if (statusBelongto.equalsIgnoreCase(Status.REJECTED)) {
                    vb.increRejectedCount( 1);
                }
                if (statusBelongto.equalsIgnoreCase(Status.PROCESSING)) {
                    vb.increProcessingCount(1);
                }
                if (statusBelongto.equalsIgnoreCase(Status.DELIVERED)) {
                    vb.increDeliveredCount( 1);
                }
                if (statusBelongto.equalsIgnoreCase(Status.CLOSED)) {
                    vb.increClosedCount( 1);
                }
                if (statusBelongto.equalsIgnoreCase(Status.DUPLATION)) {
                    vb.increDuplationCount( 1);
                }
                boolean abnoraml = isIssueAbnormal(issue);
                if(abnoraml){
                    vb.increAbnormalCount( 1);
                }
            }
            result.add(vb);
        }
        return result;
    }
    /**
     * ͳ��Account�¸�IssueTypeÿ��״̬��Issue������
     * @param accountRid Long
     * @param userType String
     * @return List
     */
    public List statisticAccountByType(Long accountRid,String userType){
        List result = new ArrayList();
        List allIssue = listIssueInAccount(accountRid);
        if(allIssue == null || allIssue.size() == 0)
            return result;
        SortedMap records = new TreeMap(new IssueTypeComparator());
        for(int j = 0;j < allIssue.size() ;j ++){
            Issue issue = (Issue) allIssue.get(j);
            String issueType = issue.getIssueType();
            String status = issue.getIssueStatus();
            String scope = issue.getScope();

            //���Scope������Customer��Χ�ھ�����
            if (!issueTypeLogic.isExistScope(issueType, scope, userType)) {
                continue;
            }

            VbIssueStatAccountByType vb = (VbIssueStatAccountByType) records.get(issueType);
            if(vb == null){
                vb = new VbIssueStatAccountByType();
//                records.put(issueType,vb);
            }

            vb.setIssueType(issueType);
            String statusBelongto = getStatusBelongTo(issueType,status);
            if(statusBelongto.equalsIgnoreCase(Status.RECEIVED)) {
                vb.increReceivedCount(1);
            }
            if (statusBelongto.equalsIgnoreCase(Status.REJECTED)) {
                vb.increRejectedCount( 1);
            }
            if (statusBelongto.equalsIgnoreCase(Status.PROCESSING)) {
                vb.increProcessingCount(1);
            }
            if (statusBelongto.equalsIgnoreCase(Status.DELIVERED)) {
                vb.increDeliveredCount( 1);
            }
            if (statusBelongto.equalsIgnoreCase(Status.CLOSED)) {
                vb.increClosedCount( 1);
            }
            if (statusBelongto.equalsIgnoreCase(Status.DUPLATION)) {
                vb.increDuplationCount( 1);
            }
            boolean abnoraml = isIssueAbnormal(issue);
            if(abnoraml){
                vb.increAbnormalCount( 1);
            }
            if(vb!=null){
                records.put(issueType,vb);
            }
        }
        result = new ArrayList(records.values());
        records.clear();
        return result;
    }
    /**
     * ����Account�����е�Issue
     * @param rid Long
     * @return List
     */
    private List listIssueInAccount(Long rid){
        String hql = "from Issue as it " +
                     "where it.rst='" + Constant.RST_NORMAL + "'" +
                     "and it.accountId='" + rid + "'" ;
        try {
            return this.getDbAccessor().getSession().createQuery(hql).list();
        } catch (Exception ex) {
            throw new BusinessException("ISSUE_STAT_00001","error list all issue in account ["+rid+"]",ex);
        }
    }

    /**
     * ����userId, userType��ȡIssueͳ����Ϣ
     * Exec:{
     * 1  Issue Statistic��
     * 1.1  ���AccountIdΪ�գ�
     * 1.1.1  ��ȡ��ǰ�û���Ȩ������AccountList(�ֱ���UserType���ù��÷���)
     * 1.1.2  ͳ�ƾ�AccountList������Ŀ��IssueType��״̬������[N1]
     * 2.1 ���AccountId��Ϊ�գ�
     * 2.1.1 ͳ�Ƶ�ǰ��ĿAccountId������Ŀ��IssueType��״̬������[N1]
     *}
     * [N1]��
     * 1. ͳ�Ƶļ�¼״̬Ϊ��ɾ��
     * 2. ��UserTypeΪ�ͻ�����ͳ��Vision To Customer��Y�ļ�¼
     *
     * @param userId String
     * @param userType String
     * @param accountId Long
     * @return VbIssueTypeStatList {List(IssueType, Number(by Issue Status)...)}
     */
    public VbIssueTypeStatList statistic(AfIssueStat issueform) {
        VbIssueTypeStatList statList = null;

        LgIssueType lgIssueType = new LgIssueType();
        List issueTypes = lgIssueType.getIssueTypes();

        for (int i = 0; i < issueTypes.size(); i++) {
            if (issueTypes.get(i) != null) {
                IssueType issueType = (IssueType) issueTypes.get(i);
                VbIssueTypeStat record = new VbIssueTypeStat();
                record.setIssueType(issueType.getTypeName());
                records.put(issueType.getTypeName(), record);
            }
        }

        /**
         * ���form�е�account��Ϊ�գ������form�е�account��ѯ
         */
        if (issueform.getAccountId() != null &&
            !issueform.getAccountId().equalsIgnoreCase("")) {
            String accountId = issueform.getAccountId();

            /**
             * ���ѡ����account���򽫴�account����session��
             */
            IDtoAccount account = lgAccount.getAccountByRid(accountId);
            this.setAccount(account);

            int id = Integer.valueOf(accountId).intValue();
            statList = this.listIssueType(id);
            statList.setAccountId(accountId);
        } else {
            /**
             * ���form�е�AccountΪ�գ����Ե�Session�в����Ƿ����ȱʡ��Account
             * ����ҵ�ȱʡ��Account������ݴ�Account����
             */
//            if (this.getAccount() != null) {
//                String accountId = this.getAccount().getRid().toString();
//                int id = Integer.valueOf(accountId).intValue();
//                statList = this.listIssueType(id);
//                statList.setAccountId(accountId);
//            } else {
                /**
                 * ����Ҳ���ȱʡ��Account������ݵ�ǰ�û��ܷ��ʵ�����Account����
                 */
                String userId = this.getUser().getUserLoginId();
                String userType = this.getUser().getUserType();
                LgAccount accountLogic = new LgAccount();
                List accountList = accountLogic.getAccounts(userType, userId);
                statList = this.listIssueTypeList(accountList);
                statList.setAccountId("");
//            }
        }
        List accountOptionsList = this.getAccountOptions();
        TagUtils.addSelectedOption(0,accountOptionsList,"  ----  Please Select  ----  ", "");
        statList.setAccountList(accountOptionsList);
        return statList;
    }

    /**
     * ��ȡ����Issue���б�
     * @return List
     */

    public VbIssueTypeStatList listIssueType(int accountId) throws
        BusinessException {
        VbIssueTypeStatList statList = new VbIssueTypeStatList();

        List result = null;
        try {
            //��ѯÿһ��accountId ��Ӧ��typeName
            Session session = this.getDbAccessor().getSession();
            String queryStr = "from Issue as it " +
                              "where it.rst='" +
                              Constant.RST_NORMAL +
                              "' and it.accountId=" + accountId+" order by it.issueType, it.rid";
            Query q = session.createQuery(queryStr);
            result = q.list();
            String userType = this.getUser().getUserType();
            for (int i = 0; i < result.size(); i++) {
                Issue issue = (Issue) result.get(i);
                String issueType = issue.getIssueType();
                String scope = issue.getScope();
                if (!issueTypeLogic.isExistScope(issueType, scope, userType)) {
                    continue;
                }
                boolean flag = isIssueAbnormal(issue);
                if(issue.getIssueStatus()==null || issue.getIssueStatus().trim().equals("")) {
                    log.info("Warning: issue status is empty, issue rid=["+issue.getRid()+"]");
                }
                listDetail(issue.getIssueType(), issue.getIssueStatus(),flag);
            }
            while(records.size()>0) {
                Object key=records.firstKey();
                VbIssueTypeStat typeStat = (VbIssueTypeStat) records.get(key);
                records.remove(key);
                statList.getDetail().add(typeStat);
            }

            return statList;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.issue.list.exception",
                                        "List issue define error!");
        }
    }

    public VbIssueTypeStatList listIssueTypeList(List accountList) throws
        BusinessException {
        VbIssueTypeStatList statList = new VbIssueTypeStatList();
        List result = null;
        LgIssueType issueTypeLogic = new LgIssueType();
        String userType = this.getUser().getUserType();
        try {
            //��ѯÿһ��accountId ��Ӧ��typeName
            if (accountList != null && accountList.size() > 0) {
                Session session = this.getDbAccessor().getSession();
                String str = "from Issue as it where it.rst='" +
                             Constant.RST_NORMAL + "' and (";
                Query q;
                String accountSql = getAccountListSql(accountList);
                str = str + accountSql + ") order by it.issueType, it.rid";
                q = session.createQuery(str);
                result = q.list();
                for (int i = 0; i < result.size(); i++) {
                    Issue issue = (Issue) result.get(i);
                    String issueType = issue.getIssueType();
                    String scope = issue.getScope();
                    if (!issueTypeLogic.isExistScope(issueType, scope, userType)) {
                        continue;
                    }
                    Date date = new Date();
                    boolean flag = false;
                    if (issue.getDueDate() != null &&
                        issue.getIssueStatus() != null &&
                        !issue.getIssueStatus().equalsIgnoreCase("")) {
                        if (comDate.compareDate(date, issue.getDueDate()) > 0 &&
                            (issue.getIssueStatus().equalsIgnoreCase(Status.
                            PROCESSING) ||
                             issue.getIssueStatus().equalsIgnoreCase(Status.
                            DELIVERED))) {
                            flag = true;
                        }
                    }
                    if(issue.getIssueStatus()==null || issue.getIssueStatus().trim().equals("")) {
                        log.info("Warning: issue status is empty, issue rid=["+issue.getRid()+"]");
                    }
                    listDetail(issue.getIssueType(), issue.getIssueStatus(),flag);
                }
            }

            while(records.size()>0) {
                Object key=records.firstKey();
                VbIssueTypeStat typeStat = (VbIssueTypeStat) records.get(key);
                records.remove(key);
                statList.getDetail().add(typeStat);
            }

            return statList;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.issue.list.exception",
                                        "List issue define error!");
        }
    }


    public String getAccountListSql(List accountList) throws
        BusinessException {
        String str = "";
        try {
            for (int i = 0; i < accountList.size(); i++) {
                IDtoAccount account = (IDtoAccount) accountList.get(i);
                int id = account.getRid().intValue();
                if (i == 0) {
                    str = str + "it.accountId=" + id;
                } else {
                    str = str + " or it.accountId=" + id;
                }
            }
            return str;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.issue.list.exception",
                                        "List issue define error!");
        }

    }

    /**
     * ��ȡ����Issue���б�
     * @return List
     */

    public void listDetail(String typeName, String status, boolean flag) throws
        BusinessException {
        try {
            VbIssueTypeStat record = (VbIssueTypeStat) records.get(typeName);
            if (record == null) {
                return;
//                record = new VbIssueTypeStat();
//                record.setIssueType(typeName);
//                records.put(typeName, record);
            }
            record.setSum(record.getSum()+1);
            if(status!=null && !status.trim().equals("")) {
                //����typeName��Status��ѯ����Ӧ��Belongto
                String statusBelongto = getStatusBelongTo(typeName, status);

                VbIssueTypeStat typeStat = record;
                if (flag) {
                    typeStat.setAbnormal(typeStat.getAbnormal() + 1);
                }

                if(statusBelongto.equalsIgnoreCase(Status.RECEIVED)) {
                    typeStat.setReceivedCount(typeStat.getReceivedCount()+1);
                }

                if (statusBelongto.equalsIgnoreCase(Status.REJECTED)) {
                    typeStat.setRejectCount(typeStat.getRejectCount() + 1);
                }
                if (statusBelongto.equalsIgnoreCase(Status.
                    PROCESSING)) {
                    typeStat.setProcessingCount(typeStat.getProcessingCount() +
                                                1);
                }
                if (statusBelongto.equalsIgnoreCase(Status.DELIVERED)) {
                    typeStat.setDeliveredCount(typeStat.getDeliveredCount() + 1);
                }
                if (statusBelongto.equalsIgnoreCase(Status.CLOSED)) {
                    typeStat.setClosedCount(typeStat.getClosedCount() + 1);
                }
                if (statusBelongto.equalsIgnoreCase(Status.DUPLATION)) {
                    typeStat.setDuplationCount(typeStat.getDuplationCount() + 1);
                }
            }
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.issue.list.exception",
                                        "List issue define error!");
        }
    }
    //�ж�ĳ��Issue�Ƿ�����:DueDate�ѵ�,�������ڴ�����
    private boolean isIssueAbnormal(Issue issue) {
        Date date = new Date();
        boolean flag = false;
        if (issue.getIssueStatus() != null) {
            if(issue.getDueDate()!=null) {
                String issueStatus = issueTypeLogic.getStatusBelongTo(issue.getIssueType(),issue.getIssueStatus());
                if (comDate.compareDate(date, issue.getDueDate()) > 0 &&
                    (issueStatus.equalsIgnoreCase(Status.PROCESSING) ||
                     issueStatus.equalsIgnoreCase(Status.DELIVERED))) {
                    flag = true;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * ����IssueType�µ�ĳ״̬�������ڲ�������ĸ�Status
     * @param typeName String
     * @param status String
     * @return String
     */
    private String getStatusBelongTo(String typeName, String status) {
        String statusBelongto = issueTypeLogic.getStatusBelongTo(typeName,status);
        return statusBelongto;
    }
    /** @link dependency */
    /*# server.essp.issue.stat.viewbean.VbIssueStatByAccount lnkVbIssueTypeStatList; */
}

class IssueTypeComparator implements Comparator {
    LgIssueType issueTypeLogic = new LgIssueType();
    List allTypes=null;

    public IssueTypeComparator() {
        allTypes=issueTypeLogic.getIssueTypes();
    }

    private int getSeq(String typeName) {
        if(allTypes!=null) {
            for (int i = 0; i < allTypes.size(); i++) {
                IssueType issueType=(IssueType)allTypes.get(i);
                if(issueType.getTypeName().equals(typeName)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int compare(Object o1, Object o2) {
        String type1=(String)o1;
        String type2=(String)o2;
        long seq1=getSeq(type1);
        long seq2=getSeq(type2);
        if(seq1<seq2) {
            return -1;
        } else if(seq1>seq2) {
            return 1;
        } else {
            return type1.compareTo(type2);
        }
    }
}
