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
     * 返回Issue查询页面所需内容
     * 1.所有IssueType列表，包含关联Priority，Scope和IssueStatus，
     * 从LgTypeList，LgPriorityList和LgStatusList获得(状态为可用)
     * 2.使用者有访问权限的项目Id列表
     * 3.Due Date的开始和结束日期默认值，开始默认为本周起始日（星期六），结束默认为当天
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

        //status options (用于搜索的状态)
        List statusList = Status.SEARCH_STATUS_OPTIONS;

        issueReload.setStatusList(statusList);
        return issueReload;
    }

    /**
     * 根据输入条件查询对应Issue，查询方法通过QBE和QBC：
     * 1.所有Issue字符串属性创建Example
     * 2.typeName属性用Expression.ilike()匹配
     * 3.filleDate和dueDate属性用Expression.between()匹配
     * 4.按fillDate倒序排列
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
         * 先执行排序和分页后,再查找每条Issue的详细信息
         */
        /**
         * 执行排序
         */
        sortIssue(listViewBean, form);

        //排序完成之后加入新加的ISSUE
        //可实现新增多条ISSUE，且保证在最前面
        //Modified by:Robin.zhang
        List detail = listViewBean.getDetail();
        for (int i = 0; i < newAddIssue.size(); i++) {
            String rid = (String) newAddIssue.get(i);
            int index = this.hasExistResult(detail, rid);
            if (index > 0) { //此处本应为大于等于0，但如果是0的话就不用提前了
                detail.add(0, temList.remove(index));
            } else if (index == -1) { //没找到的情况
                LgIssue lgIssue = new LgIssue();
                detail.add(0, lgIssue.load(new Long(rid)));
            }

        }

        /**
         * 执行分页
         */
        //1.准备分页条件
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
         * 查找每条Issue的详细信息
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
                 * 在List画面上增加Status BelongTo信息，便于判断删除操作
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
             * 在List画面上增加isPrincipal信息，便于判断是否为问题负责人
             */
            String userName = this.getUser().getUserLoginId();
            if (vbIssue.getPrincipal().equals(userName)) {
                vbIssue.setPrincipalFlag("true");
            } else {
                vbIssue.setPrincipalFlag("false");
            }

            /**
             * 在List画面上增加isPm信息，便于判断是否为项目经理
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
     * 获取所有Issue的列表
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
            //查询所有的Issue Define
            Session session = this.getDbAccessor().getSession();

            String str = "from Issue as it where it.rst='" +
                         Constant.RST_NORMAL + "' and it.issueType=" + "'" +
                         typeName + "'";

            /**
             * 如果没有指定IssueType，即是第一次进入画面，则查出所有类型的Issue
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
            //根据内部状态生成的IssueType和Status的查询条件
            //modified by xr
            if (form.getStatus() != null &&
                !form.getStatus().equalsIgnoreCase("")) {
                if (form.getStatus().equals(Status.NOT_CLOSED)) {
                    //默认情况也为此
                    str = str + " and it.issueStatus<>'" + Status.CLOSED + "'";
                } else {
                    String innerStatus = form.getStatus();
                    String statusCondition = null;
                    List statuses = null; //存某一内部状态对应的外部状态
                    //查询正常状态条件
                    if (!Status.ABNORMAL.equals(innerStatus)) { //是内部状态
                        //根据内部状态查找出所有对应的外部状态
                        statuses = issueTypeLogic.listIssueTypeStatus(
                            innerStatus);
                    } else { //查询非正常状态,查询所有Left,判断日期(当前日期大于DueDate的Issue)在查询结果中根据程序排除
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
                        //当前日期大于DueDate,则是Abnormal的
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
     * 获取所有Issue的列表专为Export
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
            //查询所有的Issue Define
            Session session = this.getDbAccessor().getSession();
            String str = "from Issue as it where it.rst='" +
                         Constant.RST_NORMAL + "' and it.issueType=" + "'" +
                         typeName + "'";
            /**
             * 如果没有指定IssueType，即是第一次进入画面，则查出所有类型的Issue
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
                    //默认情况也为此
                    str = str + " and it.issueStatus<>'" + Status.CLOSED + "'";
                } else {
                    String innerStatus = form.getStatus();
                    String statusCondition = null;
                    List statuses = null; //存某一内部状态对应的外部状态
                    //查询正常状态条件
                    if (!Status.ABNORMAL.equals(innerStatus)) { //是内部状态
                        //根据内部状态查找出所有对应的外部状态
                        statuses = issueTypeLogic.listIssueTypeStatus(
                            innerStatus);
                    } else { //查询非正常状态,查询所有Left,判断日期(当前日期大于DueDate的Issue)在查询结果中根据程序排除
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
//                    //默认情况也为此
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
                        //当前日期大于DueDate,则是Abnormal的
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

    //根据RID判断其在目标LIST中是否存在
    //如果存在则返回其在目标LIST中的index值
    //否则返回　－1
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
