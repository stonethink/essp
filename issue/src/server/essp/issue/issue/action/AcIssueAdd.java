package server.essp.issue.issue.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.form.AfIssue;
import server.essp.issue.issue.logic.LgIssue;
import server.essp.issue.issue.viewbean.VbIssue;
import java.util.ArrayList;

public class AcIssueAdd extends AbstractISSUEAction {
        /**
         * 根据传入AfIssue新增Issue对象，取得Form
         * 创建logic，并调用logic里的add方法，Call:LgIssue.add()
         * ForwardId:success
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            ArrayList addIssueRidList=new ArrayList();
            if(request.getSession().getAttribute("NewAddIssue")!=null){
                addIssueRidList=(ArrayList)request.getSession().getAttribute("NewAddIssue");
            }

            AfIssue issueAddForm=(AfIssue)this.getForm();

            //记录真正填写者的用户名，解决帮客户填写的问题。
            issueAddForm.setActualFilledBy(this.getUser().getUserLoginId());
            String isMail = request.getParameter("isMail");
            LgIssue logic=new LgIssue();

            String addIssueRid;//记录新加Issue的Rid
            if(isMail==null || isMail.length()==0){
                addIssueRid=logic.add(issueAddForm,"");
            }else{
                addIssueRid=logic.add(issueAddForm,isMail);
            }
            addIssueRidList.add(addIssueRid);
            request.getSession().setAttribute("NewAddIssue",addIssueRidList);

    }

    /** @link dependency */
    /*# server.essp.issue.issue.logic.LgIssue lnkLgIssue; */
}
