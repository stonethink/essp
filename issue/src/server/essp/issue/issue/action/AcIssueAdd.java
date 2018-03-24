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
         * ���ݴ���AfIssue����Issue����ȡ��Form
         * ����logic��������logic���add������Call:LgIssue.add()
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

            //��¼������д�ߵ��û����������ͻ���д�����⡣
            issueAddForm.setActualFilledBy(this.getUser().getUserLoginId());
            String isMail = request.getParameter("isMail");
            LgIssue logic=new LgIssue();

            String addIssueRid;//��¼�¼�Issue��Rid
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
