package server.essp.issue.issue.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.form.AfIssue;
import server.essp.issue.issue.logic.LgIssue;
import java.util.ArrayList;

public class AcIssueDelete extends AbstractISSUEAction {
        /**
         * 根据主键RID删除Issue对象
         * Call:LgIssue.delete();
         * ForwardId:list
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            AfIssue issueForm = (AfIssue)this.getForm();
            LgIssue logic = new LgIssue();
            logic.delete(issueForm);

            //删除ISSUE之后清空SESSION中存放的新增加的ISSUE　RID列表信息中的那条Issue
            ArrayList addIssueRidList=new ArrayList();
            if(request.getSession().getAttribute("NewAddIssue")!=null){
               addIssueRidList=(ArrayList)request.getSession().getAttribute("NewAddIssue");
            }
            for(int i=0;i<addIssueRidList.size();i++){
                if(issueForm.getRid().equals((String)addIssueRidList.get(i))){
                    addIssueRidList.remove(issueForm.getRid());
                }
            }

            request.getSession().setAttribute("NewAddIssue",addIssueRidList);

            request.setAttribute("forwardFromDelete","true");
    }

    /** @link dependency */
    /*# server.essp.issue.issue.logic.LgIssue lnkLgIssue; */
}
