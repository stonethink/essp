package server.essp.issue.issue.conclusion.logic;

import java.util.List;
import db.essp.issue.IssueConclusionUg;
import server.essp.issue.common.logic.AbstractISSUELogic;
import net.sf.hibernate.Session;
import server.framework.common.Constant;
import server.framework.common.BusinessException;
import net.sf.hibernate.Query;
import java.util.ArrayList;
import server.essp.issue.issue.conclusion.viewbean.VbIssueConclusionUrge;
import db.essp.issue.Issue;
import server.essp.issue.common.logic.LgAccount;
import server.essp.issue.common.logic.LgDownload;
import c2s.dto.FileInfo;

public class LgIssueConclusionUrgeList extends AbstractISSUELogic {
    public String getIsPrincipal(java.lang.Long issueRid) {

    Issue issue = new Issue();
        try {
            Session session = this.getDbAccessor().getSession();
            issue = (Issue) session.load(Issue.class, issueRid);
            String lgName = this.getUser().getUserLoginId();
              if (issue.getPrincipal()!=null){
                  String Principal[] = issue.getPrincipal().split(",");
                    for(int i=0;i<Principal.length;i++){
                        if(lgName.equals(Principal[i])){
                            return"Principal";
                        }
                    }
               }
           }catch (Exception ex) {
                   log.error(ex);
                   ex.printStackTrace();
                   throw new BusinessException("issue.conclusion.urge.getIsPrincipal. exception",
                                               "List urge error");
               }
          return "";
    }

    public String getIsAssignto(java.lang.Long issueRid) {

        Issue issue = new Issue();
        try {
            Session session = this.getDbAccessor().getSession();
            issue = (Issue) session.load(Issue.class, issueRid);
            String lgName = this.getUser().getUserLoginId();
            if (issue.getResolution().getResolutionBy() != null) {
                String ResolutionBy[] = issue.getResolution().getResolutionBy().split(",");
                for (int i = 0; i < ResolutionBy.length; i++) {
                    if (lgName.equals(ResolutionBy[i])) {
                        return "Assign TO";
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException(
                "issue.conclusion.urge.getIsAssignto. exception",
                "List urge error");
        }
        return "";
    }



    /**
     * 查找Issue对应的IssueConclusionUrge
     * from IssueConclusionUg as ug
     * where ug.issueConclusion.rid=:issueRid order by ug.urgDate
     * @param issueCode Long
     * @return List
     */
    public List list(java.lang.Long issueRid) {
        try {
            Session session = this.getDbAccessor().getSession();
            Query q = session.createQuery("from IssueConclusionUg as ug " +
                                          "where ug.issueConclusion.rid=:issueRid " +
                                          "  and ug.rst=:rst "+
                                          "order by ug.urgDate asc");
            q.setLong("issueRid",issueRid.longValue());
            q.setString("rst", Constant.RST_NORMAL);
            List result = q.list();
            List webVo=new ArrayList();
            LgAccount accountLogic=new LgAccount();
            if(result!=null && result.size()>0) {
                for(int i=0;i<result.size();i++) {
                     IssueConclusionUg it=(IssueConclusionUg)result.get(i);
                     Issue issue=(Issue)session.get(Issue.class,issueRid);
                     String accountRid=issue.getAccountId().toString();
                String accountCode=accountLogic.getAccountId(accountRid);

                    VbIssueConclusionUrge oneBean = new VbIssueConclusionUrge();
                    if(it.getAttachmentId()!=null){
                  LgDownload downloadLogic=new LgDownload();
                  FileInfo fileInfo=new FileInfo();
                  fileInfo.setAccountcode(accountCode);
                  fileInfo.setId(it.getAttachmentId());
                  fileInfo.setFilename(it.getAttachment());
                  oneBean.setAttachment(downloadLogic.getDownloadUrl(fileInfo));
               } else {
                   oneBean.setAttachment("");
               }

                    oneBean.setRid(String.valueOf(it.getRid()));
                    oneBean.setIssueRid(String.valueOf(it.getIssueConclusion().getRid()));
                    oneBean.setUrgedBy(it.getUrgedBy());
                    oneBean.setUrgeTo(it.getUrgeTo());
                    oneBean.setUrgedByScope(it.getUrgedByScope());
                    oneBean.setUrgeToScope(it.getUrgeToScope());
                    oneBean.setUrgDate(String.valueOf(it.getUrgDate()));
                    oneBean.setDescription(it.getDescription());

                    oneBean.setAttachmentdesc(it.getAttachmentdesc());

                    if(oneBean.getRid()==null) {
                       oneBean.setRid("");
                   }
                   if(oneBean.getIssueRid()==null) {
                       oneBean.setIssueRid("");
                   }
                   if(oneBean.getUrgedBy()==null) {
                       oneBean.setUrgedBy("");
                   }
                   if(oneBean.getUrgeTo()==null) {
                    oneBean.setUrgeTo("");
                   }
                   if(oneBean.getUrgDate()==null) {
                    oneBean.setUrgDate("");
                   }
                   if(oneBean.getDescription()==null) {
                   oneBean.setDescription("");
                  }
                  if(oneBean.getAttachmentdesc()==null) {
                   oneBean.setAttachmentdesc("");
                  }

                    webVo.add(oneBean);
                }
            }
            return webVo;

        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.conclusion.urge exception",
                                        "List urge error");
        }

    }
}
