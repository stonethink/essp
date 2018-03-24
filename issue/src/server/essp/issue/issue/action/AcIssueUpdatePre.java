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
import server.essp.issue.issue.viewbean.VbIssueDetail;
import com.wits.util.comDate;
import java.util.Date;

public class AcIssueUpdatePre extends AbstractISSUEAction {
    public AcIssueUpdatePre() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
         * 根据RID获得修改Issue显示页面所需内容
         * Call：LgIssue.updatePrepare()
         * ForwardId:update
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws server.framework.common.BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            AfIssue issueUpdatePreForm = (AfIssue)this.getForm();

            //预填相关信息
            if(issueUpdatePreForm.getConfirmBy()==null||!issueUpdatePreForm.getConfirmBy().equals("")){
                issueUpdatePreForm.setConfirmBy(this.getUser().getUserLoginId());
                issueUpdatePreForm.setConfirmByScope(this.getUser().getUserType());
                issueUpdatePreForm.setConfirmDate(comDate.dateToString(new Date(),"yyyy/MM/dd"));
            }
            LgIssue logic=new LgIssue();
            VbIssue viewbean=logic.updatePre(issueUpdatePreForm);
            request.setAttribute("webVo",viewbean);

    }

    private void jbInit() throws Exception {
    }

    /** @link dependency */
    /*# server.essp.issue.issue.logic.LgIssue lnkLgIssue; */

    /** @link dependency */
    /*# server.essp.issue.issue.viewbean.VbIssueDetail lnkVbIssueDetail; */
}


