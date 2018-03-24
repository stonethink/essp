package server.essp.issue.issue.logic;

import server.essp.issue.common.logic.AbstractISSUELogic;
import server.framework.common.BusinessException;
import java.util.List;
import java.util.ArrayList;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import db.essp.issue.IssueRiskHistory;
import server.essp.issue.issue.viewbean.VbIssueRiskHistory;
import com.wits.util.StringUtil;
import java.util.Date;
import com.wits.util.comDate;

public class LgIssueRiskHistory extends AbstractISSUELogic {
    /**
     * 根据ISSUE rid 获取IssueStatusHistory List
     * param rid java.lang.Long
     * return Result java.util.List
     * throws server.framework.common.BusinessException
     */
    public List getIssueRiskHistoryList(java.lang.Long rid){
        List Result=new ArrayList();
        try{
            String querySql=" from IssueRiskHistory  irh where "+
                            "irh.issueRid='"+rid.toString()+"' order by irh.updateDate ,irh.rid asc";
            Session session = this.getDbAccessor().getSession();
            Query q=session.createQuery(querySql);
            List dbResult=q.list();
            for(int i=0;i<dbResult.size();i++){
                IssueRiskHistory issueRiskHistory=(IssueRiskHistory)dbResult.get(i);
                VbIssueRiskHistory vbIssueRiskHistory=new VbIssueRiskHistory();
                vbIssueRiskHistory.setIssueRid(StringUtil.nvl(issueRiskHistory.getIssueRid()));
                vbIssueRiskHistory.setUpdateDate(comDate.dateToString(issueRiskHistory.getUpdateDate(),"yyyy-MM-dd HH:mm"));
                vbIssueRiskHistory.setUpdateBy(StringUtil.nvl(issueRiskHistory.getUpdateBy()));
                vbIssueRiskHistory.setUpdateByScope(StringUtil.nvl(issueRiskHistory.getUpdateByScope()));
//                vbIssueRiskHistory.setProbabilityFrom(StringUtil.nvl(issueRiskHistory.getProbabilityFrom()));
                vbIssueRiskHistory.setProbabilityTo(String.valueOf(issueRiskHistory.getProbabilityTo()));
//                vbIssueRiskHistory.setRiskLevelFrom(StringUtil.nvl(issueRiskHistory.getRisklevelFrom()));
                vbIssueRiskHistory.setRiskLevelTo(String.valueOf(issueRiskHistory.getRisklevelTo()));
//                vbIssueRiskHistory.setInfluenceFrom(StringUtil.nvl(issueRiskHistory.getInfluenceFrom()));
                vbIssueRiskHistory.setInfluenceTo(StringUtil.nvl(issueRiskHistory.getInfluenceTo()));
//                vbIssueRiskHistory.setCategoryFrom(StringUtil.nvl(issueRiskHistory.getCategoryFrom()));
//                vbIssueRiskHistory.setCategoryTo(StringUtil.nvl(issueRiskHistory.getCategoryTo()));
                Result.add(vbIssueRiskHistory);
            }
            return Result;
        }catch(Exception ex){
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.issueRiskHistory.list.exception",
                                        "List issueRiskHistory  error!");

        }
    }
    /**
     * add IssueStatusHistory
     * @param vbIssueRiskHistory VbIssueRiskHistory
     */
    public void addIssueRiskHistory(VbIssueRiskHistory vbIssueRiskHistory){
        IssueRiskHistory issueRiskHistory=new IssueRiskHistory();
        try{
            issueRiskHistory.setIssueRid(new Long(vbIssueRiskHistory.getIssueRid()));
            Date date =new Date();
            issueRiskHistory.setUpdateDate(date);
            String userName=this.getUser().getUserLoginId();//get current userName
            String userType = this.getUser().getUserType();
            issueRiskHistory.setUpdateBy(StringUtil.nvl(userName));
            issueRiskHistory.setUpdateByScope(userType);
//            issueRiskHistory.setProbabilityFrom(StringUtil.nvl(vbIssueRiskHistory.getProbabilityFrom()));
            String probability=StringUtil.nvl(vbIssueRiskHistory.getProbabilityTo());
            if(!"".equals(probability)){
                double d_probability=Double.parseDouble(probability);
                issueRiskHistory.setProbabilityTo(d_probability);
            }
//            issueRiskHistory.setRisklevelFrom(StringUtil.nvl(vbIssueRiskHistory.getRiskLevelFrom()));
            String riskLevel=StringUtil.nvl(vbIssueRiskHistory.getRiskLevelTo());
            if(!"".equals(riskLevel)){
                double d_riskLevel=Double.parseDouble(riskLevel);
                issueRiskHistory.setRisklevelTo(d_riskLevel);
            }
//            issueRiskHistory.setInfluenceFrom(StringUtil.nvl(vbIssueRiskHistory.getInfluenceFrom()));
            issueRiskHistory.setInfluenceTo(StringUtil.nvl(vbIssueRiskHistory.getInfluenceTo()));
//            issueRiskHistory.setCategoryFrom(StringUtil.nvl(vbIssueRiskHistory.getCategoryFrom()));
//            issueRiskHistory.setCategoryTo(StringUtil.nvl(vbIssueRiskHistory.getCategoryTo()));
            issueRiskHistory.setMemo(StringUtil.nvl(vbIssueRiskHistory.getMemo()));
            this.getDbAccessor().save(issueRiskHistory);
        }catch(Exception ex){
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.add.issueRiskHistory.exception",
                                        "Add issueRiskHistory  error!");
        }
    }
}
