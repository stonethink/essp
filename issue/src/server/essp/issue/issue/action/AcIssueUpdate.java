package server.essp.issue.issue.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.issue.form.AfIssue;
import server.essp.issue.issue.logic.LgIssue;
import server.essp.issue.issue.viewbean.VbIssue;

public class AcIssueUpdate extends AbstractBusinessAction {
        /**
         * 根据传入AfIssue修改Issue对象
         * Call:LgIssue.update();
         * ForwardId:success
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            AfIssue issueForm=(AfIssue)this.getForm();
            String isMail =(String)request.getParameter("isMail");
            LgIssue logic=new LgIssue();
            if(isMail==null || isMail.length()==0){
                VbIssue viewBean=logic.update(issueForm,"");
                //viewBean原来放在Session里面
                //modify by:Robin 20060428
                request.setAttribute("webVo",viewBean);
            }else{
                VbIssue viewBean=logic.update(issueForm,isMail);
                request.setAttribute("webVo",viewBean);
            }

    }

    /** @link dependency */
    /*# server.essp.issue.issue.logic.LgIssue lnkLgIssue; */
}
