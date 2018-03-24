package server.essp.issue.issue.logic;

import server.framework.common.BusinessException;
import server.essp.issue.issue.viewbean.*;
import java.util.*;
import java.util.Calendar;
import java.util.Date;
import server.framework.hibernate.HBComAccess;
import net.sf.hibernate.Session;
import db.essp.issue.Issue;
import db.essp.issue.IssueType;
import java.util.Date;
import server.essp.issue.issue.form.AfIssueSearch;
import server.essp.issue.issue.viewbean.VbIssueList;
import com.wits.util.comDate;
import net.sf.hibernate.Query;
import server.essp.issue.common.constant.Status;
import server.essp.issue.common.logic.AbstractISSUELogic;
import server.essp.issue.common.logic.LgAccount;
import server.essp.issue.common.logic.LgIssueType;
import db.essp.issue.IssueScope;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.account.*;
import server.framework.common.Constant;
import server.essp.issue.common.logic.LgSort;
import server.essp.issue.common.logic.LgPage;
import server.essp.issue.common.constant.PageAction;
import server.framework.taglib.util.TagUtils;
import db.essp.issue.IssueStatus;
import net.sf.hibernate.*;
import server.framework.taglib.util.SelectOptionImpl;
import server.framework.taglib.util.ISelectOption;

public class LgIssueList extends AbstractISSUELogic {
    private LgIssueType issueTypeLogic = new LgIssueType();
    /**
     * ����Issue��ѯҳ����������
     * 1.����IssueType�б���������Priority��Scope��IssueStatus��
     * ��LgTypeList��LgPriorityList��LgStatusList���(״̬Ϊ����)
     * 2.ʹ�����з���Ȩ�޵���ĿId�б�
     * 3.Due Date�Ŀ�ʼ�ͽ�������Ĭ��ֵ����ʼĬ��Ϊ������ʼ�գ���������������Ĭ��Ϊ����
     * @return VbIssueReload
     * @throws BusinessException
     */
    public VbIssueReload searchPrepare() throws
        BusinessException {
        VbIssueReload issueReload = new VbIssueReload();
        issueReload.setFillBy("");
        issueReload.setIssueId("");
        issueReload.setIssueName("");
        issueReload.setPrincipal("");
        //Account options
        List accountList = this.getAccountOptions();
        TagUtils.addSelectedOption(0, accountList,
                                   "  ----  Please Select  ----  ", "");
        ISelectOption accountFirstOpt = (ISelectOption) accountList.get(0);
        accountFirstOpt.setTitle(accountFirstOpt.getLabel());
        issueReload.setAccountList(accountList);
        //IssueType options

        List temListType = issueTypeLogic.getIssueTypeOptions();
        TagUtils.addSelectedOption(0, temListType,
                                   "  ----  Please Select  ----  ", "");
        issueReload.setIssueTypeList(temListType);
        //priority options
        List priorityList = issueTypeLogic.getPriorityOptions(null);
        TagUtils.addSelectedOption(0, priorityList,
                                   "  ----  Please Select  ----  ", "");
        issueReload.setPriorityList(priorityList);
        //scope options
        List scopeList = issueTypeLogic.getScopeOptions(null, null);
        TagUtils.addSelectedOption(0, scopeList,
                                   "  ----  Please Select  ----  ", "");
        issueReload.setScopeList(scopeList);

        //status options (����������״̬)
        List statusList = Status.SEARCH_STATUS_OPTIONS;

        issueReload.setStatusList(statusList);
        return issueReload;
    }

    /**
     * ��������������ѯ��ӦIssue����ѯ����ͨ��QBE��QBC��
     * 1.����Issue�ַ������Դ���Example
     * 2.typeName������Expression.ilike()ƥ��
     * 3.filleDate��dueDate������Expression.between()ƥ��
     * 4.��fillDate��������
     * @param form AfIssueSearch
     * @return java.util.List
     * @throws server.framework.common.BusinessException
     */
    public VbIssueList search(AfIssueSearch form, ArrayList newAddIssue) throws
        BusinessException {

        VbIssueList listViewBean = new VbIssueList();
        if (form.getIssueType() == null || form.getIssueType().trim().equals("")) {
            List typeList = issueTypeLogic.getIssueTypes();
            String typeInfo = "";
            for (int i = 0; i < typeList.size(); i++) {
                IssueType dbIssueType = (IssueType) typeList.get(i);
                typeInfo = typeInfo + dbIssueType.getTypeName();
                if (i < typeList.size() - 1) {
                    typeInfo = typeInfo + "/";
                }
            }
            listViewBean.setTypeName(typeInfo);
        } else {
            listViewBean.setTypeName(form.getIssueType());
        }
        List temList = this.listAll(form);

        listViewBean.setSortInfo(form.getSortInfo());
        listViewBean.setDetail(temList);
        /**
         * ��ִ������ͷ�ҳ��,�ٲ���ÿ��Issue����ϸ��Ϣ
         */
        /**
         * ִ������
         */
        sortIssue(listViewBean, form);

        //�������֮������¼ӵ�ISSUE
        //��ʵ����������ISSUE���ұ�֤����ǰ��
        //Modified by:Robin.zhang
        List detail = listViewBean.getDetail();
        for (int i = 0; i < newAddIssue.size(); i++) {
            String rid = (String) newAddIssue.get(i);
            int index = this.hasExistResult(detail, rid);
            if (index > 0) { //�˴���ӦΪ���ڵ���0���������0�Ļ��Ͳ�����ǰ��
                detail.add(0, temList.remove(index));
            } else if (index == -1) { //û�ҵ������
                LgIssue lgIssue = new LgIssue();
                detail.add(0, lgIssue.load(new Long(rid)));
            }

        }

        /**
         * ִ�з�ҳ
         */
        //1.׼����ҳ����
        String sPageAction = form.getPageAction();
        String sPageNo = form.getPageNo();
        if (sPageNo == null ||
            sPageNo.trim().equals("")) {
            sPageNo = "1";
        }

        if (sPageAction == null ||
            sPageAction.trim().equals("")) {
            sPageAction = PageAction.ACTION_NOTHING + "";
        }
        int pageStandard = 8;
        if (form.getPageStandard() != null) {
            try {
                pageStandard = Integer.valueOf(form.getPageStandard()).
                               intValue();
            } catch (Exception e) {

            }
        }
        listViewBean.setPageStandard(pageStandard);
        listViewBean.setPageNo(Integer.valueOf(sPageNo).intValue());
        listViewBean.setPageAction(Integer.valueOf(sPageAction).intValue());
        LgPage pageLogic = new LgPage();
        pageLogic.splitPages(listViewBean);
        /**
         * ����ÿ��Issue����ϸ��Ϣ
         */
        temList = listViewBean.getDetail();
        if (temList != null) {
            renderViewbean(listViewBean, temList);
        }
        return listViewBean;
    }

    private void renderViewbean(VbIssueList listViewBean, List issueDBList) {
        List detailList = new ArrayList();
        int size = issueDBList.size();
        for (int i = 0; i < size; i++) {
            Issue issue = (Issue) issueDBList.get(i);
            VbIssue vbIssue = new VbIssue();
            vbIssue.setRid(issue.getRid().toString());
            Date date = new Date();
            vbIssue.setAbnormal("");
            if (issue.getIssueStatus() != null && issue.getDueDate() != null) {
                String issueStatus = issueTypeLogic.getStatusBelongTo(issue.
                    getIssueType(), issue.getIssueStatus());
                if (comDate.compareDate(date, issue.getDueDate()) > 0 &&
                    (issueStatus.equalsIgnoreCase(Status.PROCESSING) ||
                     issueStatus.equalsIgnoreCase(Status.DELIVERED))) {
                    vbIssue.setAbnormal("!");
                }
            }

            LgAccount lgAccount = new LgAccount();
            if (issue.getAccountId() != null) {
                vbIssue.setAccountId(lgAccount.getAccountId(issue.
                    getAccountId().toString()));
            } else {
                vbIssue.setAccountId(" ");
            }
            if (issue.getAccountId() != null) {
                // LgAccount lgAccount = new LgAccount();

                vbIssue.setAccountName(lgAccount.getAccountName(issue.
                    getAccountId().toString()));
            } else {
                vbIssue.setAccountName("");
            }
            if (issue.getIssueId() != null) {
                vbIssue.setIssueId(issue.getIssueId());
            } else {
                vbIssue.setIssueId(" ");
            }
            if (issue.getIssueName() != null) {
                vbIssue.setIssueName(issue.getIssueName());
            } else {
                vbIssue.setIssueName(" ");
            }
            if (issue.
                getFilleDate() != null) {
                vbIssue.setFilleDate(comDate.dateToString(issue.
                    getFilleDate()));
            } else {
                vbIssue.setFilleDate(" ");
            }
            if (issue.getPriority() != null) {
                vbIssue.setPriority(issue.getPriority());
            } else {
                vbIssue.setPriority(" ");
            }
            if (issue.getDueDate() != null) {
                vbIssue.setDueDate(comDate.dateToString(issue.getDueDate()));
            } else {
                vbIssue.setDueDate(" ");
            }
            if (issue.getPrincipal() != null) {
                vbIssue.setPrincipal(issue.getPrincipal());
                vbIssue.setPrincipalScope(issue.getPrincipalScope());
            } else {
                vbIssue.setPrincipal(" ");
            }
            if (issue.getIssueStatus() != null) {
                vbIssue.setIssueStatus(issue.getIssueStatus());
                /**
                 * ��List����������Status BelongTo��Ϣ�������ж�ɾ������
                 */
                String typeName = issue.getIssueType();
                String statusBelongto = issueTypeLogic.getStatusBelongTo(
                    typeName, issue.getIssueStatus());
                vbIssue.setIssueStatusBelongto(statusBelongto);
                vbIssue.setIssueType(typeName);
            } else {
                vbIssue.setIssueStatus(" ");
                vbIssue.setIssueStatusBelongto("");
                vbIssue.setIssueType("");
            }

            /**
             * ��List����������isPrincipal��Ϣ�������ж��Ƿ�Ϊ���⸺����
             */
            String userName = this.getUser().getUserLoginId();
            if (vbIssue.getPrincipal().equals(userName)) {
                vbIssue.setPrincipalFlag("true");
            } else {
                vbIssue.setPrincipalFlag("false");
            }

            /**
             * ��List����������isPm��Ϣ�������ж��Ƿ�Ϊ��Ŀ����
             */
            String accountRid = issue.getAccountId().toString();
            String pm = lgAccount.getAccountManager(accountRid);
            if (pm.equals(userName)) {
                vbIssue.setPmFlag("true");
            } else {
                vbIssue.setPmFlag("false");
            }

            detailList.add(vbIssue);
        }
        listViewBean.setDetail(detailList);
    }

    private void sortIssue(VbIssueList listViewBean, AfIssueSearch form) {
        LgSort sortLogic = new LgSort();
        try {
            String sortInfo = sortLogic.updateSortInfo(form.getSortInfo(),
                form.getSortItem());
            sortLogic.sort(sortInfo, listViewBean.getDetail());
            listViewBean.setSortInfo(sortInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ��ȡ����Issue���б�
     * @return List
     */

    private List listAll(AfIssueSearch form) throws BusinessException {
        List result = new ArrayList();
        try {
            List allTypes = issueTypeLogic.getIssueTypes();

            LgAccount lgAccount = new LgAccount();
            String typeName = form.getIssueType();

            boolean hasSetFillDateBegin = false;
            boolean hasSetFillDateEnd = false;
            boolean hasSetDueDateBegin = false;
            boolean hasSetDueDateEnd = false;
            boolean hasSetAbnormal = false;
            //��ѯ���е�Issue Define
            Session session = this.getDbAccessor().getSession();

            String str = "from Issue as it where it.rst='" +
                         Constant.RST_NORMAL + "' and it.issueType=" + "'" +
                         typeName + "'";

            /**
             * ���û��ָ��IssueType�����ǵ�һ�ν��뻭�棬�����������͵�Issue
             */
            if (typeName == null || typeName.trim().equals("")) {
                str = "from Issue as it where it.rst='" + Constant.RST_NORMAL +
                      "' ";
            }
            if (form.getAccountId() != null &&
                !form.getAccountId().equalsIgnoreCase("")) {
                IDtoAccount account = lgAccount.getAccountByRid(form.
                    getAccountId());
                this.setAccount(account);
                Integer id = new Integer(form.getAccountId());
                int idint = id.intValue();
                str = str + " and it.accountId =" + idint;
            } else {
                if (this.getUser().getUserLoginId() != null) {
                    String userId = this.getUser().getUserLoginId();
                    String userType = this.getUser().getUserType();
                    List accountList = lgAccount.getAccounts(userType,
                        userId);
                    if (accountList != null && accountList.size() != 0) {
                        str = str + " and (";
                        for (int j = 0; j < accountList.size(); j++) {
                            IDtoAccount tem = (IDtoAccount) accountList.get(
                                j);
                            if (j < accountList.size() - 1) {
                                str = str + " it.accountId='" +
                                      tem.getRid() +
                                      "' or ";
                            } else {
                                str = str + " it.accountId='" +
                                      tem.getRid() +
                                      "') ";
                            }

                        }
                    } else {
                        return result;
                    }
                }

            }
            if (form.getPriority() != null &&
                !form.getPriority().equalsIgnoreCase("")) {
                String priority = form.getPriority();
                str = str + " and it.priority =" + "'" + priority + "'";
            }
            if (form.getFillBy() != null &&
                !form.getFillBy().equalsIgnoreCase("")) {
                String fillBy = form.getFillBy();
                str = str + " and ( it.filleBy like '%" + fillBy +
                      "%' or it.actualFilledBy like '" + form.getFillBy() +
                      "' )";
            }
            if (form.getResolveBy() != null &&
                !form.getResolveBy().equalsIgnoreCase("")) {
                str = str + " and it.resolution.resolutionBy like '%" +
                      form.getResolveBy() + "%'";
            }

            if (form.getScope() != null &&
                !form.getScope().equalsIgnoreCase("")) {
                String scope = form.getScope();
                str = str + " and it.scope =" + "'" + scope + "'";
            } else {
                if (this.getUser().getUserType() != null) {
                    String userType = this.getUser().getUserType();
                    if (userType.equalsIgnoreCase(DtoUser.USER_TYPE_CUST)) {
                        if (form.getIssueType() != null &&
                            !form.getIssueType().equalsIgnoreCase("")) {
                            List scopes = (List) issueTypeLogic.getScope(form.
                                getIssueType(), userType);
                            if (scopes != null && scopes.size() != 0) {
                                str = str + " and (";
                                for (int i = 0; i < scopes.size(); i++) {
                                    IssueScope issueScope = (IssueScope) scopes.
                                        get(
                                            i);
                                    if (i < scopes.size() - 1) {
                                        str = str + " it.scope='" +
                                              issueScope.getComp_id().getScope() +
                                              "' or ";
                                    } else {
                                        str = str + " it.scope='" +
                                              issueScope.getComp_id().getScope() +
                                              "')";
                                    }
                                }
                            } else {
                                return result;
                            }
                        } else {
                            HashMap typeCondition = new HashMap();
                            if (allTypes != null && allTypes.size() != 0) {
                                for (int i = 0; i < allTypes.size(); i++) {
                                    IssueType issueType = (IssueType) allTypes.
                                        get(i);
                                    List scopes = (List) issueTypeLogic.
                                                  getScope(
                                        issueType.getTypeName(), userType);
                                    if (scopes != null && scopes.size() != 0) {
                                        typeCondition.put(issueType.getTypeName(),
                                            scopes);
                                    }
                                }
                            }
                            if (typeCondition.size() > 0) {
                                str = str + " and ( ";
                                int conditCount = 0;
                                for (Iterator it = typeCondition.keySet().
                                    iterator(); it.hasNext(); ) {
                                    String typeNameStr = (String) it.next();
                                    str = str + " ( it.issueType='" +
                                          typeNameStr + "' and ( ";
                                    List scopes = (List) typeCondition.get(
                                        typeNameStr);
                                    for (int i = 0; i < scopes.size(); i++) {
                                        IssueScope scope = (IssueScope) scopes.
                                            get(i);
                                        str = str + " it.scope='" +
                                              scope.getComp_id().getScope() +
                                              "' ";
                                        if (i < scopes.size() - 1) {
                                            str = str + " or ";
                                        }
                                    }
                                    str = str + " ))";
                                    if (conditCount < typeCondition.size() - 1) {
                                        str = str + " or ";
                                    }
                                    conditCount++;
                                }
                                str = str + " ) ";
                            } else {
                                return result;
                            }
                        }
                    }
                } else {
                    return result;
                }
            }
            if (form.getIssueId() != null &&
                !form.getIssueId().equalsIgnoreCase("")) {
                String issueId = form.getIssueId();
                str = str + " and it.issueId like '%" + issueId + "%'";
            }
            if (form.getIssueName() != null &&
                !form.getIssueName().equalsIgnoreCase("")) {
                String issueName = form.getIssueName();
                str = str + " and it.issueName like '%" + issueName + "%'";
            }
            if (form.getPrincipal() != null &&
                !form.getPrincipal().equalsIgnoreCase("")) {
                String principal = form.getPrincipal();
                str = str + " and it.principal like '%" + principal + "%'";
            }
            //�����ڲ�״̬���ɵ�IssueType��Status�Ĳ�ѯ����
            //modified by xr
            if (form.getStatus() != null &&
                !form.getStatus().equalsIgnoreCase("")) {
                if (form.getStatus().equals(Status.NOT_CLOSED)) {
                    //Ĭ�����ҲΪ��
                    str = str + " and it.issueStatus<>'" + Status.CLOSED + "'";
                } else {
                    String innerStatus = form.getStatus();
                    String statusCondition = null;
                    List statuses = null; //��ĳһ�ڲ�״̬��Ӧ���ⲿ״̬
                    //��ѯ����״̬����
                    if (!Status.ABNORMAL.equals(innerStatus)) { //���ڲ�״̬
                        //�����ڲ�״̬���ҳ����ж�Ӧ���ⲿ״̬
                        statuses = issueTypeLogic.listIssueTypeStatus(
                            innerStatus);
                    } else { //��ѯ������״̬,��ѯ����Left,�ж�����(��ǰ���ڴ���DueDate��Issue)�ڲ�ѯ����и��ݳ����ų�
                        hasSetAbnormal = true;
                        statuses = issueTypeLogic.listIssueTypeStatus(Status.
                            LEFT);
                    }
                    if (statuses != null && statuses.size() != 0) {
                        statusCondition = getStatusCondition(statuses);
                    }
                    str = str + " and ( " + statusCondition + ")";
                }
            }
            if (form.getFillDateBegin() != null &&
                !form.getFillDateBegin().equalsIgnoreCase("")) {
                str = str + " and it.filleDate >=:fillDataBegin";
                hasSetFillDateBegin = true;
            }
            if (form.getFillDateEnd() != null &&
                !form.getFillDateEnd().equalsIgnoreCase("")) {
                str = str + " and it.filleDate<=:fillDateEnd";
                hasSetFillDateEnd = true;
            }
            if (form.getDueDateBegin() != null &&
                !form.getDueDateBegin().equalsIgnoreCase("")) {
                str = str + " and it.dueDate >=:dueDateBegin";
                hasSetDueDateBegin = true;
            }
            if (form.getDueDateEnd() != null &&
                !form.getDueDateEnd().equalsIgnoreCase("")) {
                str = str + " and it.dueDate <=:dueDateEnd";
                hasSetDueDateEnd = true;
            }

            str = str + "  order by it.filleDate desc,it.rid desc";
            System.out.println(str);
            Query q = session.createQuery(str);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.WEEK_OF_YEAR,
                         calendar.get(Calendar.WEEK_OF_YEAR) - 1);
            calendar.set(Calendar.DAY_OF_WEEK, 0);
            Date satDay = calendar.getTime();
            String sSatday = comDate.dateToString(satDay);

            Date today = new Date();
            if (hasSetFillDateBegin) {
                if (form.getFillDateBegin() != null &&
                    !form.getFillDateBegin().equalsIgnoreCase("")) {
                    Date fillDataBegin = comDate.toDate(form.getFillDateBegin());
                    q.setDate("fillDataBegin", fillDataBegin);
                } else {
                    q.setDate("fillDataBegin", satDay);
                }
            }
            if (hasSetFillDateEnd) {
                if (form.getFillDateEnd() != null &&
                    !form.getFillDateEnd().equalsIgnoreCase("")) {
                    Date fillDateEnd = comDate.toDate(form.getFillDateEnd());
                    q.setDate("fillDateEnd", fillDateEnd);
                } else {
                    q.setDate("fillDateEnd", today);
                }
            }
            if (hasSetDueDateBegin) {
                if (form.getDueDateBegin() != null &&
                    !form.getDueDateBegin().equalsIgnoreCase("")) {
                    Date dueDateBegin = comDate.toDate(form.getDueDateBegin());
                    q.setDate("dueDateBegin", dueDateBegin);
                } else {
                    q.setDate("dueDateBegin", satDay);
                }
            }
            if (hasSetDueDateEnd) {
                if (form.getDueDateEnd() != null &&
                    !form.getDueDateEnd().equalsIgnoreCase("")) {
                    Date dueDateEnd = comDate.toDate(form.getDueDateEnd());
                    q.setDate("dueDateEnd", dueDateEnd);
                } else {
                    q.setDate("dueDateEnd", today);
                }
            }

            result = q.list();

            if (hasSetAbnormal) {
                if (result != null && result.size() > 0) {
                    Iterator it = result.iterator();
                    while (it.hasNext()) {
                        Issue issue = (Issue) it.next();
                        Date dueDate = issue.getDueDate();
                        //��ǰ���ڴ���DueDate,����Abnormal��
                        if (!(comDate.compareDate(today, dueDate) > 0)) {
                            it.remove();
                        }
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.issue.list.exception",
                                        "List issue define error!");
        }
    }


    private String getStatusCondition(List statuses) {
        String statusCondition = null;
        for (int i = 0; i < statuses.size(); i++) {
            IssueStatus status = (IssueStatus) statuses.get(i);
            String con = " (it.issueType='" + status.getComp_id().getTypeName() +
                         "' and it.issueStatus='" +
                         status.getComp_id().getStatusName() + "') ";
            if (statusCondition == null) {
                statusCondition = con;
            } else {
                statusCondition += " or " + con + "";
            }
        }
        return statusCondition;
    }

    /**
     * ��ȡ����Issue���б�רΪExport
     * @return List
     */

    public List listAllForExport(AfIssueSearch form) throws BusinessException {
        List result = new ArrayList();
        try {
            List allTypes = issueTypeLogic.getIssueTypes();

            LgAccount lgAccount = new LgAccount();
            String typeName = form.getIssueType();
            boolean hasSetFillDateBegin = false;
            boolean hasSetFillDateEnd = false;
            boolean hasSetDueDateBegin = false;
            boolean hasSetDueDateEnd = false;
            boolean hasSetAbnormal = false;
            //��ѯ���е�Issue Define
            Session session = this.getDbAccessor().getSession();
            String str = "from Issue as it where it.rst='" +
                         Constant.RST_NORMAL + "' and it.issueType=" + "'" +
                         typeName + "'";
            /**
             * ���û��ָ��IssueType�����ǵ�һ�ν��뻭�棬�����������͵�Issue
             */
            if (typeName == null || typeName.trim().equals("")) {
                str = "from Issue as it where it.rst='" + Constant.RST_NORMAL +
                      "' ";
            }
            if (form.getAccountId() != null &&
                !form.getAccountId().equalsIgnoreCase("")) {
                IDtoAccount account = lgAccount.getAccountByRid(form.
                    getAccountId());
                this.setAccount(account);
                Integer id = new Integer(form.getAccountId());
                int idint = id.intValue();
                str = str + " and it.accountId =" + idint;
            } else {
                if (this.getUser().getUserLoginId() != null) {
                    String userId = this.getUser().getUserLoginId();
                    String userType = this.getUser().getUserType();
                    List accountList = lgAccount.getAccounts(userType,
                        userId);
                    if (accountList != null && accountList.size() != 0) {
                        str = str + " and (";
                        for (int j = 0; j < accountList.size(); j++) {
                            IDtoAccount tem = (IDtoAccount) accountList.get(
                                j);
                            if (j < accountList.size() - 1) {
                                str = str + " it.accountId='" +
                                      tem.getRid() +
                                      "' or ";
                            } else {
                                str = str + " it.accountId='" +
                                      tem.getRid() +
                                      "') ";
                            }

                        }
                    } else {
                        return result;
                    }
                }
//                }
            }
            if (form.getPriority() != null &&
                !form.getPriority().equalsIgnoreCase("")) {
                String priority = form.getPriority();
                str = str + " and it.priority =" + "'" + priority + "'";
            }
            if (form.getStatus() != null &&
                !form.getStatus().equalsIgnoreCase("")) {
                if (form.getStatus().equals(Status.NOT_CLOSED)) {
                    //Ĭ�����ҲΪ��
                    str = str + " and it.issueStatus<>'" + Status.CLOSED + "'";
                } else {
                    String innerStatus = form.getStatus();
                    String statusCondition = null;
                    List statuses = null; //��ĳһ�ڲ�״̬��Ӧ���ⲿ״̬
                    //��ѯ����״̬����
                    if (!Status.ABNORMAL.equals(innerStatus)) { //���ڲ�״̬
                        //�����ڲ�״̬���ҳ����ж�Ӧ���ⲿ״̬
                        statuses = issueTypeLogic.listIssueTypeStatus(
                            innerStatus);
                    } else { //��ѯ������״̬,��ѯ����Left,�ж�����(��ǰ���ڴ���DueDate��Issue)�ڲ�ѯ����и��ݳ����ų�
                        hasSetAbnormal = true;
                        statuses = issueTypeLogic.listIssueTypeStatus(Status.
                            LEFT);
                    }
                    if (statuses != null && statuses.size() != 0) {
                        statusCondition = getStatusCondition(statuses);
                    }
                    str = str + " and ( " + statusCondition + ")";
                }
            }

            if (form.getFillBy() != null &&
                !form.getFillBy().equalsIgnoreCase("")) {
                String fillBy = form.getFillBy();
                str = str + " and ( it.filleBy like '%" + fillBy +
                      "%' or it.actualFilledBy like '" + form.getFillBy() +
                      "' )";

            }
            if (form.getResolveBy() != null &&
                !form.getResolveBy().equalsIgnoreCase("")) {
                str = str + " and it.resolution.resolutionBy like '%" +
                      form.getResolveBy() + "%'";
            }

            if (form.getScope() != null &&
                !form.getScope().equalsIgnoreCase("")) {
                String scope = form.getScope();
                str = str + " and it.scope =" + "'" + scope + "'";
            } else {
                if (this.getUser().getUserType() != null) {
                    String userType = this.getUser().getUserType();
                    if (userType.equalsIgnoreCase(DtoUser.USER_TYPE_CUST)) {
                        if (form.getIssueType() != null &&
                            !form.getIssueType().equalsIgnoreCase("")) {
                            List scopes = (List) issueTypeLogic.getScope(form.
                                getIssueType(), userType);
                            if (scopes != null && scopes.size() != 0) {
                                str = str + " and (";
                                for (int i = 0; i < scopes.size(); i++) {
                                    IssueScope issueScope = (IssueScope) scopes.
                                        get(
                                            i);
                                    if (i < scopes.size() - 1) {
                                        str = str + " it.scope='" +
                                              issueScope.getComp_id().getScope() +
                                              "' or ";
                                    } else {
                                        str = str + " it.scope='" +
                                              issueScope.getComp_id().getScope() +
                                              "')";
                                    }
                                }
                            } else {
                                return result;
                            }
                        } else {
                            HashMap typeCondition = new HashMap();
                            if (allTypes != null && allTypes.size() != 0) {
                                for (int i = 0; i < allTypes.size(); i++) {
                                    IssueType issueType = (IssueType) allTypes.
                                        get(i);
                                    List scopes = (List) issueTypeLogic.
                                                  getScope(
                                        issueType.getTypeName(), userType);
                                    if (scopes != null && scopes.size() != 0) {
                                        typeCondition.put(issueType.getTypeName(),
                                            scopes);
                                    }
                                }
                            }
                            if (typeCondition.size() > 0) {
                                str = str + " and ( ";
                                int conditCount = 0;
                                for (Iterator it = typeCondition.keySet().
                                    iterator(); it.hasNext(); ) {
                                    String typeNameStr = (String) it.next();
                                    str = str + " ( it.issueType='" +
                                          typeNameStr + "' and ( ";
                                    List scopes = (List) typeCondition.get(
                                        typeNameStr);
                                    for (int i = 0; i < scopes.size(); i++) {
                                        IssueScope scope = (IssueScope) scopes.
                                            get(i);
                                        str = str + " it.scope='" +
                                              scope.getComp_id().getScope() +
                                              "' ";
                                        if (i < scopes.size() - 1) {
                                            str = str + " or ";
                                        }
                                    }
                                    str = str + " ))";
                                    if (conditCount < typeCondition.size() - 1) {
                                        str = str + " or ";
                                    }
                                    conditCount++;
                                }
                                str = str + " ) ";
                            } else {
                                return result;
                            }
                        }
                    }
                } else {
                    return result;
                }
            }
            if (form.getIssueId() != null &&
                !form.getIssueId().equalsIgnoreCase("")) {
                String issueId = form.getIssueId();
                str = str + " and it.issueId like '%" + issueId + "%'";
            }
            if (form.getIssueName() != null &&
                !form.getIssueName().equalsIgnoreCase("")) {
                String issueName = form.getIssueName();
                str = str + " and it.issueName like '%" + issueName + "%'";
            }
            if (form.getPrincipal() != null &&
                !form.getPrincipal().equalsIgnoreCase("")) {
                String principal = form.getPrincipal();
                str = str + " and it.principal like '%" + principal + "%'";
            }
//            if (form.getStatus() != null &&
//                !form.getStatus().equalsIgnoreCase("")) {
//                if (form.getStatus().equals("Not Closed")) {
//                    //Ĭ�����ҲΪ��
//                    str = str + " and it.issueStatus<>'Closed'";
//                } else {
//                    str = str + " and it.issueStatus<>'" + form.getStatus() +
//                          "'";
//                }
//            }
            if (form.getFillDateBegin() != null &&
                !form.getFillDateBegin().equalsIgnoreCase("")) {
                str = str + " and it.filleDate >=:fillDataBegin";
                hasSetFillDateBegin = true;
            }
            if (form.getFillDateEnd() != null &&
                !form.getFillDateEnd().equalsIgnoreCase("")) {
                str = str + " and it.filleDate<=:fillDateEnd";
                hasSetFillDateEnd = true;
            }
            if (form.getDueDateBegin() != null &&
                !form.getDueDateBegin().equalsIgnoreCase("")) {
                str = str + " and it.dueDate >=:dueDateBegin";
                hasSetDueDateBegin = true;
            }
            if (form.getDueDateEnd() != null &&
                !form.getDueDateEnd().equalsIgnoreCase("")) {
                str = str + " and it.dueDate <=:dueDateEnd";
                hasSetDueDateEnd = true;
            }

            str = str + "  order by it.accountId,it.filleDate desc,it.rid desc";
            System.out.println(str);
            Query q = session.createQuery(str);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.WEEK_OF_YEAR,
                         calendar.get(Calendar.WEEK_OF_YEAR) - 1);
            calendar.set(Calendar.DAY_OF_WEEK, 0);
            Date satDay = calendar.getTime();
            String sSatday = comDate.dateToString(satDay);

            Date today = new Date();
            if (hasSetFillDateBegin) {
                if (form.getFillDateBegin() != null &&
                    !form.getFillDateBegin().equalsIgnoreCase("")) {
                    Date fillDataBegin = comDate.toDate(form.
                        getFillDateBegin());
                    q.setDate("fillDataBegin", fillDataBegin);
                } else {
                    q.setDate("fillDataBegin", satDay);
                }
            }
            if (hasSetFillDateEnd) {
                if (form.getFillDateEnd() != null &&
                    !form.getFillDateEnd().equalsIgnoreCase("")) {
                    Date fillDateEnd = comDate.toDate(form.getFillDateEnd());
                    q.setDate("fillDateEnd", fillDateEnd);
                } else {
                    q.setDate("fillDateEnd", today);
                }
            }
            if (hasSetDueDateBegin) {
                if (form.getDueDateBegin() != null &&
                    !form.getDueDateBegin().equalsIgnoreCase("")) {
                    Date dueDateBegin = comDate.toDate(form.getDueDateBegin());
                    q.setDate("dueDateBegin", dueDateBegin);
                } else {
                    q.setDate("dueDateBegin", satDay);
                }
            }
            if (hasSetDueDateEnd) {
                if (form.getDueDateEnd() != null &&
                    !form.getDueDateEnd().equalsIgnoreCase("")) {
                    Date dueDateEnd = comDate.toDate(form.getDueDateEnd());
                    q.setDate("dueDateEnd", dueDateEnd);
                } else {
                    q.setDate("dueDateEnd", today);
                }
            }

            result = q.list();

            if (hasSetAbnormal) {
                if (result != null && result.size() > 0) {
                    int length = result.size();
                    for (int i = 0; i < length; i++) {
                        Issue issue = (Issue) result.get(i);
                        Date dueDate = issue.getDueDate();
                        //��ǰ���ڴ���DueDate,����Abnormal��
                        if (!(comDate.compareDate(today, dueDate) > 0)) {
                            result.remove(i);
                        }
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException(
                "issue.issue.list.exception for export",
                "List issue define error!");
        }
    }

    //����RID�ж�����Ŀ��LIST���Ƿ����
    //��������򷵻�����Ŀ��LIST�е�indexֵ
    //���򷵻ء���1
    private int hasExistResult(List destList, String rid) {
        for (int i = 0; i < destList.size(); i++) {
            Issue issue = (Issue) destList.get(i);
            if (issue.getRid().toString().equals(rid)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        server.framework.hibernate.HBComAccess access = new HBComAccess();
        try {
            Session s = access.getSession();
            Issue issue = new Issue();
            /*
                            issue.setRid(new BigDecimal("2"));
                            issue.setProjId(new BigDecimal("123"));
                            issue.setPriority("priority");
                            issue.setPrincipal("pppp");
                            issue.setFilleDate(new Date());
             IssueReportStatus reportStatus = new IssueReportStatus(new BigDecimal("1"));
                            reportStatus.setProcessingDate(new Date());
                            issue.setIssuerReportStatus(reportStatus);
                            Transaction tx = s.beginTransaction();
             IssueType type = (IssueType) s.load(IssueType.class,"TEST1");
                            issue.setIssueType(type);
                            s.save(issue);
                            tx.commit();
             /*
                                                                                                                                                   Example exampleIssue = Example.create(issue).ignoreCase().enableLike(MatchMode.ANYWHERE);
                           Criteria crit  = s.createCriteria(Issue.class)
                        .add(exampleIssue)
                      .add(Expression.ilike("issueType.typeName","typeName"))
               .add(Expression.between("filleDate",new Date(),new Date()))
                                  ;
                                                         List l = crit.list();
                                    System.out.println(l.size());
              */
             s.close();
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
        }
    }
}
