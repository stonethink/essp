package server.essp.issue.issue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;
import server.essp.issue.common.logic.LgIssueBase;
import server.essp.issue.issue.form.AfIssueId;

public class AcIssueIdCreate extends AbstractISSUEAction {
    public AcIssueIdCreate() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        AfIssueId form=(AfIssueId)this.getForm();
        /**
         * 参数:
         *   1. accountRid　
         *   2. typeName
         */
        //取得account rid
        String accountRid=form.getAccountRid();
        //取得type name
        String typeName=form.getTypeName();
        String issueId="";
        //检查传入的两个参数是否合法，不能为空
        if(accountRid!=null && !accountRid.equals("") &&
           typeName!=null && !typeName.equals("")) {
            //调用issue id自动生成器，取得生成的issue id
            LgIssueBase issueLogicBase=new LgIssueBase();
            issueId=issueLogicBase.getIssueId(typeName,Long.parseLong(accountRid));
        }
        this.getRequest().setAttribute("issueId",issueId);
    }
}
