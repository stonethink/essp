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
     * ��������AfIssueSearch��ѯIssue
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

        ArrayList addIssueRidList = new ArrayList(); //�����¼����ISSUE��ID

        String myIssueType = null;
        if(request.getParameter("CleanAddListSession")!=null){
            //����ǵ������������ѡ��򣬾Ͱ�Session�е�NewAddIssue���ÿ�
            this.getSession().setAttribute("NewAddIssue",addIssueRidList);
        }
        if (request.getParameter("MyIssue") != null) {
            //��¼��ѯISSUE��LIST������
            myIssueType = request.getParameter("MyIssue");
            this.getSession().setAttribute("MyIssue", myIssueType);
            //����ǵ����߲˵������������Ͱ�Session�е�NewAddIssue���ÿ�
            this.getSession().setAttribute("NewAddIssue",addIssueRidList);
        } else { //�������Լ�ˢ��ҳ���ʱ���������
            myIssueType = "";

            //��¼Session�в�ѯ������
            String oldMyIssueType = null;
            oldMyIssueType = this.getSession().getAttribute("MyIssue").toString();
            String currentUser = this.getUser().getUserLoginId();
            String currentType = this.getUser().getUserType();

            if (oldMyIssueType == null && oldMyIssueType.equals("")) {
                //���session��û�м�¼��ѯ�����ͣ���������Ϊ ""
                this.getSession().setAttribute("MyIssue", myIssueType);
            }
            //session���Ѿ�������������Ϣ
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
                //������MyIssue����,Ŀǰ�ޣ���������
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
                //������MyIssue����,Ŀǰ�ޣ���������
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

        //ִ�в�ѯ
        //���¼����ISSUE���д���
        if (request.getSession().getAttribute("NewAddIssue") != null) {
            addIssueRidList = (ArrayList) request.getSession().getAttribute(
                "NewAddIssue");
        }

        listViewBean = logic.search(issueform,addIssueRidList);
//        System.out.println(issueform.getAddonRidInfo());
//        System.out.println(issueform.getAddonRid());

        request.setAttribute("webVo", listViewBean);

        //��ȡ������������Ҫ����Ϣ
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
