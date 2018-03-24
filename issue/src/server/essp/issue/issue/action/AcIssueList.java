package server.essp.issue.issue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.issue.logic.LgIssueList;
import server.essp.issue.issue.viewbean.VbIssueList;
import server.essp.issue.issue.form.AfIssueSearch;
import server.essp.issue.common.action.AbstractISSUEAction;
import c2s.dto.DtoUtil;
import server.essp.issue.issue.viewbean.VbIssueReload;
import java.util.List;
import server.framework.taglib.util.ISelectOption;
import c2s.essp.common.user.DtoUser;
import server.essp.issue.common.constant.Status;
import java.util.ArrayList;

public class AcIssueList extends AbstractISSUEAction {
    /**
     * 根据输入AfIssueSearch查询Issue
     * Call:LgIssueList.search()
     * ForwardId:list
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        VbIssueList listViewBean = new VbIssueList();
        AfIssueSearch issueform = (AfIssueSearch)this.getForm();
        AfIssueSearch issueform2 = new AfIssueSearch();

        ArrayList addIssueRidList = new ArrayList(); //保存新加入的ISSUE的ID

        String myIssueType = null;
        if(request.getParameter("CleanAddListSession")!=null){
            //如果是点击快速搜索的选择框，就把Session中的NewAddIssue的置空
            this.getSession().setAttribute("NewAddIssue",addIssueRidList);
        }
        if (request.getParameter("MyIssue") != null) {
            //记录查询ISSUE　LIST的类型
            myIssueType = request.getParameter("MyIssue");
            this.getSession().setAttribute("MyIssue", myIssueType);
            //如果是点击左边菜单进入的情况，就把Session中的NewAddIssue的置空
            this.getSession().setAttribute("NewAddIssue",addIssueRidList);
        } else { //当程序自己刷新页面的时候进入这里
            myIssueType = "";

            //记录Session中查询的类型
            String oldMyIssueType = null;
            oldMyIssueType = this.getSession().getAttribute("MyIssue").toString();
            String currentUser = this.getUser().getUserLoginId();
            String currentType = this.getUser().getUserType();

            if (oldMyIssueType == null && oldMyIssueType.equals("")) {
                //如果session中没有记录查询的类型，则置类型为 ""
                this.getSession().setAttribute("MyIssue", myIssueType);
            }
            //session中已经存在了类型信息
            else if (oldMyIssueType.equals("Submit")) {
                issueform.setFillBy(currentUser);
//                issueform.setStatus(Status.NOT_CLOSED);
            } else if (oldMyIssueType.equals("Responsible")) {
                issueform.setPrincipal(currentUser);
                issueform.setPrincipalScope(currentType);
//                issueform.setStatus(Status.NOT_CLOSED);
            } else if (oldMyIssueType.equals("Resolve")) {
                issueform.setResolveBy(currentUser);
//                issueform.setStatus(Status.NOT_CLOSED);
            } else if (oldMyIssueType.equals("All")) {
//                issueform.setStatus(Status.NOT_CLOSED);

                try {
                    DtoUtil.copyProperties(issueform2, issueform);
                } catch (Exception ex) {
                    throw new BusinessException(ex);
                }

            } else {
                //其他的MyIssue类型,目前无，便于增加
            }

        }

        if (myIssueType != null && !myIssueType.equals("")) {

            String currentUser = this.getUser().getUserLoginId();
            String currentType = this.getUser().getUserType();
            if (myIssueType.equals("Submit")) {
                issueform.setFillBy(currentUser);
                issueform.setStatus(Status.NOT_CLOSED);
            } else if (myIssueType.equals("Responsible")) {
                issueform.setPrincipal(currentUser);
                issueform.setPrincipalScope(currentType);
                issueform.setStatus(Status.NOT_CLOSED);
            } else if (myIssueType.equals("Resolve")) {
                issueform.setResolveBy(currentUser);
                issueform.setStatus(Status.NOT_CLOSED);
            } else if (myIssueType.equals("All")) {
                issueform.setStatus(Status.NOT_CLOSED);
            } else {
                //其他的MyIssue类型,目前无，便于增加
            }
        }
        try {
            DtoUtil.copyProperties(issueform2, issueform);
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

        if (request.getAttribute("forwardFromDelete") != null &&
            request.getAttribute("forwardFromDelete").equals("true")) {
            issueform = (AfIssueSearch)this.getSession().getAttribute(
                "searchForm");
            try {
                DtoUtil.copyProperties(issueform2, issueform);
            } catch (Exception ex) {
                throw new BusinessException(ex);
            }
        }
        LgIssueList logic = new LgIssueList();

        //执行查询
        //对新加入的ISSUE进行处理
        if (request.getSession().getAttribute("NewAddIssue") != null) {
            addIssueRidList = (ArrayList) request.getSession().getAttribute(
                "NewAddIssue");
        }

        listViewBean = logic.search(issueform,addIssueRidList);
//        System.out.println(issueform.getAddonRidInfo());
//        System.out.println(issueform.getAddonRid());

        request.setAttribute("webVo", listViewBean);

        //获取快速搜索所需要的信息
        VbIssueReload issueLoad = new VbIssueReload();
        issueLoad = logic.searchPrepare();

        List accountList = issueLoad.getAccountList();
        for(int i=0;i<accountList.size();i++){
            ISelectOption accountFirstOpt = (ISelectOption) accountList.get(i);
            if(i==0){
                accountFirstOpt.setLabel("--Please Select Account---");
            }
            accountFirstOpt.setTitle(accountFirstOpt.getLabel());

        }

        List issueTypeList = issueLoad.getIssueTypeList();
        for(int i=0;i<issueTypeList.size();i++){
            ISelectOption issueTypeFirstOpt = (ISelectOption) issueTypeList.get(i);
            if(i==0){
                issueTypeFirstOpt.setLabel("--Please Select Type---");
            }
            issueTypeFirstOpt.setTitle(issueTypeFirstOpt.getLabel());
        }

        List issueStatusList = issueLoad.getStatusList();
        for(int i=0;i<issueStatusList.size();i++){
            ISelectOption issueStatusFirstOpt = (ISelectOption) issueStatusList.get(i);
            if(i==0){
                issueStatusFirstOpt.setLabel("--Please Select Status---");
            }
            issueStatusFirstOpt.setTitle(issueStatusFirstOpt.getLabel());
        }

        issueLoad.setIssueType(issueform2.getIssueType());
        issueLoad.setAccountId(issueform2.getAccountId());
        issueLoad.setStatus(issueform2.getStatus());
//        System.out.println(issueLoad.getStatus());
        request.setAttribute("webVoQuicklySearch", issueLoad);

        this.getSession().setAttribute("searchForm", issueform2);

        // long cur2 = System.currentTimeMillis();
        // System.out.println("Time:" + (cur2 - cur1));
    }

    /** @link dependency */
    /*# server.essp.issue.issue.logic.LgIssueList lnkLgIssueList; */

    /** @link dependency */
    /*# server.essp.issue.issue.viewbean.VbIssueList lnkVbIssueList; */
}
