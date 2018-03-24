package server.essp.issue.issue.logic;

import server.essp.issue.common.logic.AbstractISSUELogic;
import server.essp.issue.issue.viewbean.VbIssue;
import server.essp.issue.issue.resolution.viewbean.VbIssueResolution;
import server.essp.issue.issue.conclusion.viewbean.VbIssueConclusion;
import server.essp.issue.issue.resolution.logic.LgIssueResolution;
import server.essp.issue.issue.logic.LgIssue;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusion;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import db.essp.issue.*;
import server.essp.issue.issue.viewbean.VbAllDetailOfIssue;
import server.essp.issue.common.logic.LgDownload;
import c2s.dto.FileInfo;
import server.essp.issue.common.logic.LgAccount;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusionUrgeList;
import server.essp.issue.issue.viewbean.VbIssueStatusHistory;
import server.essp.issue.issue.viewbean.VbIssueRiskHistory;
import java.util.List;
import java.util.ArrayList;
import server.essp.issue.issue.viewbean.VbIssueRiskHistoryList;
import server.essp.issue.common.constant.Delimiter;
import server.essp.issue.issue.viewbean.VbIssueInfluence;
import com.wits.util.comDate;
import com.wits.util.StringUtil;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;


public class LgallDetailOfIssue extends AbstractISSUELogic {
    /**
     *����ISSUE rid ��ȡ��ISSUE���������������Ϣ
     *@param rid java.lang.Long
     *@return Result java.util.List
     *throws server.framework.common.BusinessException
     */
    public VbAllDetailOfIssue getAllDetailOfIssue(java.lang.Long rid){
        VbAllDetailOfIssue vbAllDetailOfIssue=new VbAllDetailOfIssue();
        VbIssue issue=new VbIssue();//�����洢issue�����Ϣ
        VbIssueResolution issueResolution=new VbIssueResolution();//�����洢Resolution�����Ϣ
//        VbCategory category=new VbCategory();//�����洢Category�����Ϣ
//        VbInfluence influence=new VbInfluence();//�����洢Influence�����Ϣ
        VbIssueConclusion issueConclusion=new VbIssueConclusion();//�����洢Conclusion�����Ϣ
        VbIssueStatusHistory issueStatusHistory=new VbIssueStatusHistory();//�����洢History of issue status�����Ϣ
        VbIssueRiskHistory   issueRiskHistory=new VbIssueRiskHistory();//�����洢History of issue risk �����Ϣ
        LgIssue lgIssue=new LgIssue();
        LgIssueResolution lgIssueResolution=new LgIssueResolution();
        LgIssueConclusion lgIssueConclusion=new LgIssueConclusion();
        try{
            Session session = this.getDbAccessor().getSession();
            //��ȡISSUE�����Ϣ
            Issue _issue=(Issue)session.load(Issue.class, rid);
            issue.setIssueType(_issue.getIssueType());
            issue.setPriority(_issue.getPriority());
            issue.setScope(_issue.getScope());
            issue.setIssueId(_issue.getIssueId());
            issue.setIssueName(_issue.getIssueName());
            issue.setFilleDate(_issue.getFilleDate().toString());
            issue.setPhone(_issue.getPhone());
            issue.setEmail(_issue.getEmail());
            issue.setFax(_issue.getFax());
            issue.setFilleBy(_issue.getFilleBy());
            issue.setFilleByScope(_issue.getFilleByScope());
            issue.setDescription(_issue.getDescription());
           //set Attachment of issue
            LgAccount accountLogic=new LgAccount();
            String accountRid=_issue.getAccountId().toString();
            String accountCode=accountLogic.getAccountId(accountRid);
            if(_issue.getAttachmentId()!=null){
                  LgDownload downloadLogic=new LgDownload();
                  FileInfo fileInfo=new FileInfo();
                  fileInfo.setAccountcode(accountCode);
                  fileInfo.setId(_issue.getAttachmentId());
                  fileInfo.setFilename(_issue.getAttachment());
                  issue.setAttachment(downloadLogic.getDownloadUrl(fileInfo));
               } else {
                   issue.setAttachment("");
               }

            issue.setAttachmentdesc(_issue.getAttachmentdesc());
            issue.setDueDate(_issue.getDueDate().toString());
            issue.setPrincipal(_issue.getPrincipal());
            issue.setPrincipalScope(_issue.getPrincipalScope());
            issue.setIssueStatus(_issue.getIssueStatus());
            vbAllDetailOfIssue.setIssue(issue);
             //��ȡResolution�����Ϣ
             issueResolution=lgIssueResolution.resolutionPrepare(rid.toString());
//             if(issueResolution.getPlanFinishDate().length()==8){
//                 String planFinishDate = (issueResolution.getPlanFinishDate()).substring(0, 3) + "-" +
//                                         (issueResolution.getPlanFinishDate()).substring(4, 5) + "-" +
//                                         (issueResolution.getPlanFinishDate()).substring(6, 7);
//                 issueResolution.setPlanFinishDate(planFinishDate);
//             }
             java.util.Date date=comDate.toDate(issueResolution.getPlanFinishDate());
             issueResolution.setPlanFinishDate(comDate.dateToString(date));
             issueResolution.setProbability(issueResolution.getProbability()+"%");
             String reBy = StringUtil.nvl(issueResolution.getResolutionBy());
             String reCustBy = StringUtil.nvl(issueResolution.getResolutionByCustomer());
             String[] empIds = reBy.split(",");
             String[] custIds = reCustBy.split(",");
             String resolutionByStr = null;
             IHrUtil hrUtil = HrFactory.create();
             for(int iEmp = 0; iEmp < empIds.length; iEmp ++) {
                 String eName = hrUtil.getName(empIds[iEmp]);
                 if(resolutionByStr == null) {
                     resolutionByStr = eName;
                 } else {
                     resolutionByStr = resolutionByStr + "," + eName;
                 }
             }
             for(int iCust = 0; iCust < custIds.length; iCust ++) {
                 String cName = hrUtil.getName(custIds[iCust]);
                 if(resolutionByStr == null) {
                     resolutionByStr = cName;
                 } else {
                     resolutionByStr = resolutionByStr + "," + cName;
                 }
             }
             issueResolution.setResolutionBy(resolutionByStr);
//             if(!"".equalsIgnoreCase(issueResolution.getResolutionByCustomer())
//                &&issueResolution.getResolutionByCustomer()!=null){
//                 if("".equals(issueResolution.getResolutionBy())||issueResolution.getResolutionBy()==null)
//                    issueResolution.setResolutionBy(issueResolution.
//                                                 getResolutionBy() + "  " +
//                                                 issueResolution.
//                                                 getResolutionByCustomer());
//                  else
//                     issueResolution.setResolutionBy(issueResolution.
//                                                 getResolutionBy() + "  ," +
//                                                 issueResolution.
//                                                 getResolutionByCustomer());
//
//             }
             vbAllDetailOfIssue.setIssueResolution(issueResolution);
             //��ȡIssueConclusion�����Ϣ
             //������Ҫ����Ϣ�;���lgIssueConclusion��conclusionPrepare(rid,useid)����
             //���ص�VbIssueConclusion�����Կ�ֱ�ӵ��ô˷���
             String useid = this.getUser().getUserLoginId();
             issueConclusion=lgIssueConclusion.conclusionPrepare(rid,useid);
             LgIssueConclusionUrgeList  lgIssueConclusionUrgeList=new LgIssueConclusionUrgeList();
             issueConclusion.setUrgeList(lgIssueConclusionUrgeList.list(rid));
             /*����lgIssueConclusion��conclusionPrepare(rid,useid)������ConfirmBy��
              *����ʽΪ��
              * ��������ݿ���ȡ�õ�ConfirmByΪ�գ������ô�ֵΪ��Ӧissue��filleBy,���Դ�ֵ
              *ֱ�Ӵ���Ӧ���ݿ���ȡ
              */
             IssueConclusion conclusion=lgIssueConclusion.get(rid);
             if(conclusion.getConfirmBy()!=null){
                 issueConclusion.setConfirmBy(conclusion.getConfirmBy());
                 issueConclusion.setConfirmByScope(conclusion.getConfirmByScope());
             }else{
                 issueConclusion.setConfirmBy("");
             }
             vbAllDetailOfIssue.setIssueConclusion(issueConclusion);
              //��ȡIssueStatusHistory�����Ϣ
              LgIssueStatusHistory lgIssueStatusHistory=new LgIssueStatusHistory();
              vbAllDetailOfIssue.setIssueStatusHistoryList(lgIssueStatusHistory.getIssueStatusHistoryList(rid));
              //��ȡIssueRiskHistory�����Ϣ
              LgIssueRiskHistory lgIssueRiskHistory=new LgIssueRiskHistory();
              List Result=lgIssueRiskHistory.getIssueRiskHistoryList(rid);
              List RiskHistory=new ArrayList();
              for(int i=0;i<Result.size();i++){
                      boolean dataError=false;
                      VbIssueRiskHistory vbIssueRiskHistory = (VbIssueRiskHistory) Result.get(i);
                      VbIssueRiskHistoryList vbIssueRiskHistoryList = new VbIssueRiskHistoryList();
                      vbIssueRiskHistoryList.setTime(vbIssueRiskHistory.getUpdateDate());
                      vbIssueRiskHistoryList.setWho(vbIssueRiskHistory.getUpdateBy());
                      vbIssueRiskHistoryList.setWhoScope(vbIssueRiskHistory.getUpdateByScope());
                      vbIssueRiskHistoryList.setProbability(vbIssueRiskHistory.getProbabilityTo()+"%");
                      vbIssueRiskHistoryList.setRiskLevel(vbIssueRiskHistory.getRiskLevelTo());
                      String influenceTo=vbIssueRiskHistory.getInfluenceTo();
                      String[] influences=influenceTo.split(Delimiter.GROUP);
                      List influenceList=new ArrayList();
                      for(int j=0;j<influences.length;j++){
                          String[] influence=influences[j].split(Delimiter.ITEM);
                          VbIssueInfluence vbIssueInfluence=new VbIssueInfluence();
                          if(influence.length<3||influence.length>4){
                              dataError=true;
                              break;
                          }
                          vbIssueInfluence.setInfluenceName(influence[0]);
                          vbIssueInfluence.setImpactLevel(influence[1]);
                          vbIssueInfluence.setWeight(influence[2]);
                          if(influence.length<4)
                              vbIssueInfluence.setRemark("");
                          else{

                              vbIssueInfluence.setRemark(influence[3]);
                          }
                          influenceList.add(vbIssueInfluence);
                  }
                  if(!dataError){
                      vbIssueRiskHistoryList.setInfluence(influenceList);
                      RiskHistory.add(vbIssueRiskHistoryList);
                  }
              }
              vbAllDetailOfIssue.setIssueRiskHistoryList(RiskHistory);

             return vbAllDetailOfIssue;
             /*
            IssueResolution _issueResolution=(IssueResolution)session.load(IssueResolution.class,rid);
            issueResolution.setProbability(_issueResolution.getProbability().toString());
            issueResolution.setRiskLevel(_issueResolution.getRiskLevel().toString());
            issueResolution.setResolution(_issueResolution.getResolution());
            issueResolution.setPlanFinishDate(_issueResolution.getPlanFinishDate().toString());
            issueResolution.setResolutionBy(_issueResolution.getResolutionBy());
            issueResolution.setAssignedDate(_issueResolution.getAssignedDate().toString());
            issueResolution.setAttachment(_issueResolution.getAttachment());
            issueResolution.setAttachmentDesc(_issueResolution.getAttachmentdesc());
            Result.put("issueResolution",issueResolution);
            //��ȡCategory�����Ϣ
            */
        }catch(Exception ex){
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.DetailOfIssue.exception",
                                        "getAllDetailOfIssue error!");

        }

    }

}
