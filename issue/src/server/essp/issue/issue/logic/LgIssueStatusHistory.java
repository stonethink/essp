package server.essp.issue.issue.logic;

import server.essp.issue.common.logic.AbstractISSUELogic;
import java.util.List;
import java.util.ArrayList;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Query;
import server.essp.issue.issue.viewbean.VbIssueStatusHistory;
import db.essp.issue.IssueStatusHistory;
import com.wits.util.StringUtil;
import com.wits.util.comDate;
import java.util.Date;

public class LgIssueStatusHistory extends AbstractISSUELogic {
    /**
     * 根据ISSUE rid 获取IssueStatusHistory List
     * param rid java.lang.Long
     * return Result java.util.List
     * throws server.framework.common.BusinessException
     */
    public List getIssueStatusHistoryList(java.lang.Long rid){
        List Result=new ArrayList();
        try{
            String querySql=" from IssueStatusHistory   ish where "+
                            "ish.issueRid='"+rid+"' order by ish.updateDate,ish.rid asc";
            Session session = this.getDbAccessor().getSession();
            Query q=session.createQuery(querySql);
            List dbResult=q.list();
            for(int i=0;i<dbResult.size();i++){
                IssueStatusHistory  issueStatusHistory=(IssueStatusHistory)dbResult.get(i);
                VbIssueStatusHistory vbIssueStatusHistory=new VbIssueStatusHistory();
                vbIssueStatusHistory.setIssueRid(StringUtil.nvl(issueStatusHistory.getIssueRid()));
                vbIssueStatusHistory.setUpdateDate(comDate.dateToString(issueStatusHistory.getUpdateDate(),"yyyy-MM-dd HH:mm"));
                vbIssueStatusHistory.setStatusFrom(StringUtil.nvl(issueStatusHistory.getStatusFrom()));
                vbIssueStatusHistory.setStatusTo(StringUtil.nvl(issueStatusHistory.getStatusTo()));
                vbIssueStatusHistory.setMemo(StringUtil.nvl(issueStatusHistory.getMemo()));
                vbIssueStatusHistory.setUpdateBy(StringUtil.nvl(issueStatusHistory.getUpdateBy()));
                vbIssueStatusHistory.setUpdateByScope(StringUtil.nvl(issueStatusHistory.getUpdateByScope()));
                Result.add(vbIssueStatusHistory);

            }
            return Result;

        }catch(Exception ex){
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.issueStatusHistory.list.exception",
                                        "List issueStatusHistory  error!");

        }
    }
    /**
     * add IssueStatusHistory
     * @param rid java.lang.Long
     * @param status java.lang.String
     */
    public void addIssueStatusHistory(java.lang.Long rid,String status1,String status2){
        IssueStatusHistory issueStatusHistory=new IssueStatusHistory();
        String fStatus=status1;
        String tStatus=status2;
        try{
            issueStatusHistory.setIssueRid(rid);
            issueStatusHistory.setStatusFrom(fStatus);
            issueStatusHistory.setStatusTo(tStatus);
            String userName=this.getUser().getUserLoginId();//get current userName
            String userType = this.getUser().getUserType();
            issueStatusHistory.setUpdateBy(userName);
            issueStatusHistory.setUpdateByScope(userType);
            Date date=new Date();
            issueStatusHistory.setUpdateDate(date);
            this.getDbAccessor().save(issueStatusHistory);

        }catch(Exception ex){
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.add.issueStatusHistory.exception",
                                        "Add issueStatusHistory  error!");
        }
    }
    }
