package server.essp.issue.issue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;
import server.essp.issue.common.logic.LgAccount;
import java.util.List;
import server.essp.issue.common.logic.LgFilledBy;
import itf.hr.HrFactory;

public class AcIssuePrincipal extends AbstractISSUEAction {
    public AcIssuePrincipal() {
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
//        /**
//         * 参数:
//         *   1. accountRid　
//         *   2. typeName
//         */
//        //取得account rid
//        String accountRid=request.getParameter("accountRid");
//        //取得type name
//        String typeName=request.getParameter("typeName");
//        String issueId="";
//        //检查传入的两个参数是否合法，不能为空
//        if(accountRid!=null && !accountRid.equals("") &&
//           typeName!=null && !typeName.equals("")) {
//            //调用issue id自动生成器，取得生成的issue id
//            LgIssueBase issueLogicBase=new LgIssueBase();
//            issueId=issueLogicBase.getIssueId(typeName,Long.parseLong(accountRid));
//        }
//        this.getRequest().setAttribute("issueId",issueId);
//    }

        String accountRid = request.getParameter("accountRid");
        LgAccount logic = new LgAccount();
        LgFilledBy filledByLogic=new LgFilledBy();
        String pm = logic.getAccountManager(accountRid);
        String pmName = HrFactory.create().getName(pm);
        List filleByList = filledByLogic.getFilledByListOptions(accountRid,this.getUser());

        this.getRequest().setAttribute("phone",this.getUser().getPhone()==null?"":this.getUser().getPhone());
        this.getRequest().setAttribute("fax",this.getUser().getFax()==null?"":this.getUser().getFax());
        this.getRequest().setAttribute("email",this.getUser().getEmail()==null?"":this.getUser().getEmail());

        this.getRequest().setAttribute("filleBy",this.getUser().getUserLoginId());
        this.getRequest().setAttribute("filleByList", filleByList);
        this.getRequest().setAttribute("webVo", pm);
        this.getRequest().setAttribute("pmName", pmName);
    }
}
