package server.essp.issue.issue.discuss.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import java.util.Date;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussTitle;
import com.wits.util.comDate;
import server.essp.issue.issue.discuss.logic.LgIssueDiscuss;
import db.essp.issue.IssueDiscussTitle;
import server.essp.issue.common.logic.LgDownload;
import c2s.dto.FileInfo;
import server.essp.issue.common.logic.LgAccount;

public class AcIssueDiscussTitleUpdatePre extends AbstractISSUEAction {
        /**
         * 查找要修改的IssueDiscussTitle
         * Call: LgIssueDiscuss.loadTitle()
         * ForwardId: title
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            System.out.println("in issueDiscussTitleUpdatePre action!");

           VbIssueDiscussTitle vbDiscussTitle=new VbIssueDiscussTitle();
           /*String userName=this.getUser().getUseLoginName();//get current userName
           Date date=new Date();
           String strDate=comDate.dateToString(date,"yyyy-MM-dd");//get current date
           vbDiscussTitle.setFilledBy(userName);
           vbDiscussTitle.setFilledDate(strDate);*/
           String right_flag="true";
           String hasRight="false";
           if(request.getParameter("right_flag")!=null){
                hasRight=request.getParameter("right_flag");
               if("true".equalsIgnoreCase(hasRight))
                   right_flag = "false";
                else right_flag="true";
           }
           String titleRid=(String)request.getParameter("titleRid");  //get issueRid from request
           vbDiscussTitle.setRid(titleRid);

           //get record from table ISSUE_DISCUSS_TITLE with rid
            Long Rid=new Long(titleRid);
            LgIssueDiscuss lgIssueDiscuss=new LgIssueDiscuss();

            VbIssueDiscussTitle vbTitle=lgIssueDiscuss.getTitle(Rid);

            //加入accountRid
            String issueRid=vbTitle.getIssueRid();
            LgAccount lga=new LgAccount();
            vbTitle.setAccountRid(lga.getAccountByIssueRid(issueRid).getRid().toString());

            String modify="true";
            /*if(vbTitle.getReplys().size()==0)
                if("true".equalsIgnoreCase(hasRight))
                                   modify="false";
            if("flase".equalsIgnoreCase(modify)||"false".equalsIgnoreCase(right_flag)){
                vbTitle.setFilledBy(vbDiscussTitle.getFilledBy());
                vbTitle.setFilledDate(vbDiscussTitle.getFilledDate());
            }*/

           request.setAttribute("webVo",vbTitle) ;
           request.setAttribute("right_flag",right_flag);
           request.setAttribute("hasRight",hasRight);
           request.setAttribute("modify",modify);

           data.getReturnInfo() .setForwardID("modifyDiscussTitle");

    }

    /** @link dependency */
    /*# server.essp.issue.issue.discuss.logic.LgIssueDiscuss lnkLgIssueDiscuss; */
}
