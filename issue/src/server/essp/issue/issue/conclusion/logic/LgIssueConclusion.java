package server.essp.issue.issue.conclusion.logic;

import server.framework.logic.AbstractBusinessLogic;
import server.essp.issue.issue.conclusion.viewbean.VbIssueConclusion;
import db.essp.issue.IssueConclusion;
import db.essp.issue.IssueReportStatus;
import server.essp.issue.common.logic.AbstractISSUELogic;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import com.wits.util.comDate;
import java.util.Date;
import java.util.Calendar;
import db.essp.issue.Issue;
import server.essp.issue.common.logic.LgIssueType;
import java.util.List;
import db.essp.issue.IssueStatus;
import server.essp.issue.common.constant.Status;
import db.essp.issue.IssueReportStatus;
import c2s.dto.FileInfo;
import c2s.essp.common.account.IDtoAccount;
import server.essp.issue.common.logic.LgUpload;
import server.essp.issue.common.logic.LgAccount;
import server.essp.issue.common.logic.LgDownload;
import server.essp.issue.common.constant.RelationDate;
import java.util.ArrayList;
import server.framework.taglib.util.SelectOptionImpl;
import server.essp.issue.common.logic.LgFilledBy;
import server.framework.taglib.util.TagUtils;
import server.essp.issue.common.viewbean.VbFilledBy;
import server.essp.issue.issue.logic.LgIssueStatusHistory;

public class LgIssueConclusion extends AbstractISSUELogic {
    LgIssueType issueTypeLogic = new LgIssueType();
    /**
     * 获取Issue Conclusion页面显示所需内容
     * 1.根据userId是否为负责人判断页面内容是否可修改，userId非提交人或处理者，不能修改
     * 2.查找主键对应IssueConclusion对象，若存在显示其属性
     * 3.若IssueConclusion.closureStatus为空，计算该Issue提交后等待的天数：
     * 当前日期－deliveredDate
     * @param rid Long
     * @return VbIssueConclusion
     */
    public VbIssueConclusion conclusionPrepare(java.lang.Long rid,
                                               String userId) {
        String sysDate = comDate.dateToString(new java.util.Date());
        String filledBy = this.getUser().getUserLoginId();
        IssueConclusion issueConclusion = new IssueConclusion();
        Issue issue = new Issue();
        try {
            Session session = this.getDbAccessor().getSession();
            issueConclusion = (IssueConclusion) session.load(IssueConclusion.class,
                rid);
            issue = (Issue) session.load(Issue.class, rid);
            String accountRid = issue.getAccountId().toString();
            VbIssueConclusion vbIssueConclusion = new VbIssueConclusion();

            LgAccount accountLogic = new LgAccount();
            if (issue != null) {

                String pmName = accountLogic.getAccountManager(accountRid);
                String lgName = this.getUser().getUserLoginId();
                if (issue.getPrincipal()!=null) {
                    String Principal[] = issue.getPrincipal().split(",");
                    for(int i=0;i<Principal.length;i++){
                        if(lgName.equals(Principal[i])){
                            vbIssueConclusion.setIsPrincipal("Principal");
                        }
                    }
                }

                if(issue.getFilleBy()!=null && lgName.equalsIgnoreCase(issue.getFilleBy())) {
                    vbIssueConclusion.setIsFilledBy("FilledBy");
                }
                if(issue.getResolution().getResolutionBy()!=null){
                    String ResolutionBy[] = issue.getResolution().getResolutionBy().split(",");
                    for(int i=0;i<ResolutionBy.length;i++){
                        if(lgName.equals(ResolutionBy[i])){
                            vbIssueConclusion.setIsexecutor("Assign To");
                        }
                    }
                }
            }
            /**
             * get download url
             */



            String accountCode = accountLogic.getAccountId(accountRid);
            if (issueConclusion != null) {
                vbIssueConclusion.setRid(issueConclusion.getRid().toString());
                vbIssueConclusion.setActualInfluence(issueConclusion.
                    getActualInfluence());
                vbIssueConclusion.setSolvedDescription(issueConclusion.
                    getSolvedDescription());
                vbIssueConclusion.setFinishedDate(comDate.dateToString(
                    issueConclusion.getFinishedDate()));
                if (issueConclusion.getAttachmentId() != null) {
                    LgDownload downloadLogic = new LgDownload();
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setAccountcode(accountCode);
                    fileInfo.setId(issueConclusion.getAttachmentId());
                    fileInfo.setFilename(issueConclusion.getAttachment());
                    vbIssueConclusion.setAttachment(downloadLogic.
                        getDownloadUrl(fileInfo));
                }

                Date deliverDate = issueConclusion.getDeliveredDate();
                vbIssueConclusion.setDeliveredDate(comDate.dateToString(
                    issueConclusion.getDeliveredDate()));

                if (issueConclusion.getAttachmentDesc() == null) {
                    vbIssueConclusion.setAttachmentDesc("");
                } else {
                    vbIssueConclusion.setAttachmentDesc(issueConclusion.
                        getAttachmentDesc());
                }
                if (issueConclusion.getClosureStatus() == null ||
                    issueConclusion.getClosureStatus().equals("")) {
                    Date currentDate = new Date();
                    if (deliverDate != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(currentDate);
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        currentDate = calendar.getTime();

                        calendar.setTime(deliverDate);
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        deliverDate = calendar.getTime();

                        double d = currentDate.getTime() - deliverDate.getTime();
                        d = d / (1000 * 60 * 60 * 24);
                        if (d < 0) {
                            d = 0;
                        }
                        vbIssueConclusion.setWaiting((long) d + "");
                        vbIssueConclusion.setIsNull("null");
                    }
                } else {
                    vbIssueConclusion.setClosureStatus(issueConclusion.
                        getClosureStatus());
                    vbIssueConclusion.setWaiting("");
                }
                if (issueConclusion.getConfirmDate() == null) {
                    if (RelationDate.CONFIRM_DATE.equals(this.getDateName(rid)) ){
                        vbIssueConclusion.setConfirmDate(sysDate);
                    } else {
                        vbIssueConclusion.setConfirmDate("");
                    }
                } else {
                    vbIssueConclusion.setConfirmDate(comDate.dateToString(
                        issueConclusion.
                        getConfirmDate()));
                }
                if (issueConclusion.getConfirmBy() != null) {
                    vbIssueConclusion.setConfirmBy(issueConclusion.getConfirmBy());
                    vbIssueConclusion.setConfirmByScope(issueConclusion.getConfirmByScope());
                } else {
                    vbIssueConclusion.setConfirmBy(issue.getFilleBy());
                    vbIssueConclusion.setConfirmByScope(issue.getFilleByScope());
                }
                //LgAccount logic = new LgAccount();
                /**
                 * Confirm by栏位下拉框，取出对应的account rid
                 */
                //List confirmByList = logic.getKeypersonOptions(accountRid);
                LgFilledBy filledByLogic=new LgFilledBy();
                VbFilledBy filledByInfo=filledByLogic.getFilledByInfo(issue.getAccountId().toString(),vbIssueConclusion.getConfirmBy());
                List confirmByList = new ArrayList();
                confirmByList.add(new SelectOptionImpl("  ----  Please Select  ----  ", ""));
                TagUtils.addSelectedOption(confirmByList,filledByInfo.getName(),filledByInfo.getLoginid());
                TagUtils.addSelectedOption(confirmByList,this.getUser().getUserName(),this.getUser().getUserLoginId());
                vbIssueConclusion.setConfirmByList(confirmByList);
                vbIssueConclusion.setInstructionClosure(issueConclusion.
                    getInstructionClosure());
            }
            return vbIssueConclusion;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.conclusion.exception",
                                        "Prepare conclusion  error!");
        }

    }

    /**
     * 对Issue做Conclude
     * 1.判断是否已存在Issue对应的IssueConclusion对象
     *      if 不存在 then 新增IssueConclusion
     *      else 更新 IssueConclusion
     * @param param0 AfIssueConclusion
     */
    public void conclude(server.essp.issue.issue.conclusion.form.
                         AfIssueConclusion param0) {
        Long rid = new Long(param0.getRid());
        try {
            Session session = this.getDbAccessor().getSession();
            IssueConclusion issueconlusion = (IssueConclusion) session.get(
                IssueConclusion.class, rid);
            if (issueconlusion == null) {
                this.add(param0);
            } else {
                this.update(param0);
            }
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.conclusion.exception",
                                        "Conclude issue error!");
        }

    }

    /**
     * 根据主键获得IssueConclusion对象
     * @param rid Long
     * @return IssueConclusion
     */
    public IssueConclusion get(java.lang.Long rid) {
        IssueConclusion issueconlusion = null;
        try {
            Session session = this.getDbAccessor().getSession();
            issueconlusion = (IssueConclusion) session.get(IssueConclusion.class,
                rid);
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.conclusion.exception",
                                        "Get issue conclusion error!");
        }
        return issueconlusion;
    }

    /**取Issue Status相关联的日期类型
     * @param rid Long
     * @return dateName
     */
    public String getDateName(java.lang.Long rid) {

        Issue issue = new Issue();
        String typeName = "";
        String status = "";

        try {
            Session session = this.getDbAccessor().getSession();
            issue = (Issue) session.get(Issue.class, rid);
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException(
                "issueConclusion.getDateName.exception",
                "getDateName  error!");
        }

        typeName = issue.getIssueType();
        status = issue.getIssueStatus();

        return issueTypeLogic.getStatusRelationDate(typeName, status);
    }


    /**当closureStatus为正常或非正常结案时将"Status"设置为"Closed"
     * @param rid Long
     * @return statusName
     */

    public String getClosedStatus(java.lang.Long rid) {
        IssueConclusion issueconlusion = get(rid);
        Issue issue = new Issue();
        try {
            if (issueconlusion.getClosureStatus() != null &&
                !issueconlusion.getClosureStatus().equals(Status.NONACCEPTANCE) &&
                !issueconlusion.getClosureStatus().equals("")) {

                Session session = this.getDbAccessor().getSession();
                issue = (Issue) session.get(Issue.class, rid);

                List results = issueTypeLogic.getStatus(issue.getIssueType());
                for (int i = 0; i < results.size(); i++) {
                    IssueStatus issueStatus = (IssueStatus) results.get(i);
                    String statusName = issueStatus.getComp_id().getStatusName();
                    String belongTo = issueTypeLogic.getStatusBelongTo(issue.
                        getIssueType(),
                        statusName);
                    if (belongTo.equals(Status.CLOSED)) {
                        return statusName;
                    }
                }
            }

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException(
                "issueConclusion.getClosedStatus.exception",
                "getClosedStatus   error!");
        }
        return null;
    }

    /**当closureStatus为拒绝时将"Status"设置为"Rejected"
        * @param rid Long
        * @return statusName
     */
    public String getRejectedStatus(java.lang.Long rid) {
       IssueConclusion issueconlusion = get(rid);
       Issue issue = new Issue();
       try {
           if (issueconlusion.getClosureStatus()!=null && issueconlusion.getClosureStatus().length()>0 && issueconlusion.getClosureStatus().equals(Status.NONACCEPTANCE) ) {

               Session session = this.getDbAccessor().getSession();
               issue = (Issue) session.get(Issue.class, rid);

               List results = issueTypeLogic.getStatus(issue.getIssueType());
               for (int i = 0; i < results.size(); i++) {
                   IssueStatus issueStatus = (IssueStatus) results.get(i);
                   String statusName = issueStatus.getComp_id().getStatusName();
                   String belongTo = issueTypeLogic.getStatusBelongTo(issue.
                       getIssueType(),
                       statusName);
                   if (belongTo.equals(Status.REJECTED)) {
                       return statusName;
                   }
               }
           }

       } catch (Exception ex) {
           log.error(ex);
           throw new BusinessException(
               "issueConclusion.getRejectedStatus.exception",
               "getRejectedStatus   error!");
       }
       return null;
   }





    /**
     * 根据AfIssueConclusion保存conclude Issue
     * 1.新建IssueConclusion对象并保存，获得IssueConclusion对应的Issue对象,
     * 及Issue.IssueReportStatus和issueType的对应的所有IssueStatus，按sequence升序排列
     * 2.设置IssueReportStatus的deliverDate为输入值
     * 3.
     *   If ClosureStatus 为空（该问题已提交但未确认） then
     *           遍历IssueStatus，查找关联日期为“DeliverDate Date”的第一个状态，设置Issue.issueStatus为该状态；
     *   else
     *           遍历IssueStatus，查找关联日期为“Confirm Date”的第一个状态，设置Issue.issueStatus为该状态；
     *           设置IssueReportStatus的closedDate属性当前日期
     * @param param0 AfIssueConclusion
     */
    public void add(server.essp.issue.issue.conclusion.form.AfIssueConclusion
                    param0) {
        Long rid = new Long(param0.getRid());
        IssueConclusion issueconlusion = get(rid);
        Issue issue = new Issue();

        IssueReportStatus issueReportStatus = new IssueReportStatus();
        try {
            Session session = this.getDbAccessor().getSession();
            if (issueconlusion == null) {
                issueconlusion = new IssueConclusion();
            }
            //Issue issue=null;

            issueconlusion.setRid(rid);
            issueconlusion.setActualInfluence(param0.getActualInfluence());
            issueconlusion.setSolvedDescription(param0.getSolvedDescription());
            issueconlusion.setFinishedDate(comDate.toDate(param0.
                getFinishedDate()));
            issueconlusion.setDeliveredDate(comDate.toDate(param0.
                getDeliveredDate()));
            issueconlusion.setAttachmentDesc(param0.getAttachmentDesc());
            issueconlusion.setClosureStatus(param0.getClosureStatus());
            issueconlusion.setConfirmDate(comDate.toDate(param0.getConfirmDate()));
            issueconlusion.setConfirmBy(param0.getConfirmBy());
            issueconlusion.setConfirmByScope(param0.getConfirmByScope());
            issueconlusion.setInstructionClosure(param0.getInstructionClosure());
            if (param0.getAttachment() != null &&
                param0.getAttachment().getFileName() != null &&
                param0.getAttachment().getFileSize() > 0) {
                //1. create FileInfo
                FileInfo fileInfo = new FileInfo();
                LgAccount accountLogic = new LgAccount();
                IDtoAccount account = accountLogic.getAccountByRid(param0.
                    getRid());
                fileInfo.setAccountcode(account.getId());
                //2. create LgUpload
                LgUpload uploadLogic = new LgUpload();
                //3. init FileInfo Object
                uploadLogic.initFileInfo(param0.getAttachment(),
                                         fileInfo);

                fileInfo.setFilename(param0.getAttachment().getFileName());
                //4. execute upload
                uploadLogic.upload(param0.getAttachment(), fileInfo);
                issueconlusion.setAttachment(fileInfo.getFilename());
                issueconlusion.setAttachmentId(fileInfo.getId());

            }

            // issueconlusion.setRst(param0.getRst());
            /**当closureStatus为正常或非正常结案时将"Status"设置为"Closed"
             *当closureStatus为拒绝时将"Status"设置为"Rejected"
             */

            issue = (Issue) session.load(IssueConclusion.class,
                                         rid);
            issueReportStatus = (IssueReportStatus) session.load(
                IssueReportStatus.class, rid);
            String status =  issue.getIssueStatus();
            if(this.getClosedStatus(rid)!=null){
                status=this.getClosedStatus(rid);
            }else if(this.getRejectedStatus(rid)!=null){
                status=this.getRejectedStatus(rid);
            }
            issue.setIssueStatus(status);

            issueReportStatus.setIssueStatus(status);
            this.getDbAccessor().saveOrUpdate(issue);
            this.getDbAccessor().saveOrUpdate(issueReportStatus);
            this.getDbAccessor().saveOrUpdate(issueconlusion);
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.conclusion.exception",
                                        "Add issue conclusion error!");
        }

    }

    /**
     * 根据AfIssueConclusion修改IssueConclusion对象
     * 1.获得AfIssueConclusion对应IssueConclusion对象，覆盖其属性值
     * 2.If ClosureStatus不为空 then
     *           遍历IssueStatus，查找第一个关联日期为“Confirm Date”的状态，设置Issue.issueStatus为该状态；
     *           设置IssueReportStatus的closedDate属性当前日期
     * @param param0 AfIssueConclusion
     */
    public void update(server.essp.issue.issue.conclusion.form.
                       AfIssueConclusion param0) {
        Long rid = new Long(param0.getRid());
        IssueConclusion issueconlusion = get(rid);
        Issue issue = new Issue();
        //根据AssignDate是否修改了,要修改Issue的状态为与分派日期相关联的状态
        boolean isFinishedDateChanged = false;
        boolean isDeliveredDateChanged = false;
        IssueReportStatus issueReportStatus = new IssueReportStatus();
        try {
            Session session = this.getDbAccessor().getSession();
            issueconlusion = (IssueConclusion) session.load(IssueConclusion.class,
                rid);
            issueconlusion.setActualInfluence(param0.getActualInfluence());

            if(param0.getFinishedDate()!=null &&
                !param0.getFinishedDate().equals(comDate.dateToString(issueconlusion.getFinishedDate(),"yyyy/MM/dd"))
                 ){
                 isFinishedDateChanged = true;
             }
             if(param0.getDeliveredDate()!=null &&
                !param0.getDeliveredDate().equals(comDate.dateToString(issueconlusion.getDeliveredDate(),"yyyy/MM/dd"))
                 ){
                 isDeliveredDateChanged = true;
             }

            if(param0.getSolvedDescription()!=null && param0.getSolvedDescription().length()>0){
                issueconlusion.setSolvedDescription(param0.getSolvedDescription());
            }
            if(param0.getFinishedDate()!=null && param0.getFinishedDate().length()>0){
                issueconlusion.setFinishedDate(comDate.toDate(param0.
                    getFinishedDate()));
            }

            if(param0.getDeliveredDate()!=null && param0.getDeliveredDate().length()>0){
                issueconlusion.setDeliveredDate(comDate.toDate(param0.
                    getDeliveredDate()));
            }
            if(param0.getAttachmentDesc()!=null && param0.getAttachmentDesc().length()>0){
                issueconlusion.setAttachmentDesc(param0.getAttachmentDesc());
            }
            issueconlusion.setClosureStatus(param0.getClosureStatus());
            issueconlusion.setConfirmDate(comDate.toDate(param0.getConfirmDate()));
            issueconlusion.setConfirmBy(param0.getConfirmBy());
            issueconlusion.setConfirmByScope(param0.getConfirmByScope());
            issueconlusion.setInstructionClosure(param0.getInstructionClosure());
            if (param0.getAttachment() != null &&
                param0.getAttachment().getFileName() != null &&
                param0.getAttachment().getFileSize() > 0) {
                //1. create FileInfo
                FileInfo fileInfo = new FileInfo();
                LgAccount accountLogic = new LgAccount();
                IDtoAccount account = accountLogic.getAccountByIssueRid(param0.
                    getRid());
                fileInfo.setAccountcode(account.getId());
                //2. create LgUpload
                if (issueconlusion.getAttachmentId() != null) {
                    fileInfo.setId(issueconlusion.getAttachmentId());
                }

                LgUpload uploadLogic = new LgUpload();
                //3. init FileInfo Object
                uploadLogic.initFileInfo(param0.getAttachment(),
                                         fileInfo);

                String fileId = issueconlusion.getAttachmentId();
                fileInfo.setId(fileId);

                fileInfo.setFilename(param0.getAttachment().getFileName());
                //4. execute upload
                uploadLogic.upload(param0.getAttachment(), fileInfo);
                issueconlusion.setAttachment(fileInfo.getFilename());
                issueconlusion.setAttachmentId(fileInfo.getId());

            }

            //issueconlusion.setRst(param0.getRst());

            issue = (Issue) session.load(Issue.class,
                                         rid);
            String fStatus = issue.getIssueStatus();
            String tStatus = null;
            LgIssueType lgIssueType = new LgIssueType();
            //若DeliveredDate修改了,Issue状态改为DeliveredDate关联的状态
            if(isDeliveredDateChanged){
                List issueStatuses = issueTypeLogic.getRelationDataStatus(issue.getIssueType(),
                    RelationDate.DELIVERED_DATE);
                if(issueStatuses != null && issueStatuses.size() > 0){
                    IssueStatus issueStatus = (IssueStatus) issueStatuses.get(0);
                    tStatus = issueStatus.getComp_id().getStatusName();
                    issue.setIssueStatus(tStatus);
                    session.update(issue);
                }

                //if need to save
                if (lgIssueType.isSaveStatusHistory(issue.getIssueType())) {
                    if (!fStatus.equalsIgnoreCase(tStatus)) {
                        LgIssueStatusHistory lgIssueStatusHistory =
                            new LgIssueStatusHistory();
                        lgIssueStatusHistory.addIssueStatusHistory(issue.getRid(),
                            fStatus, tStatus);
                    }
                 }

            }
            //若FinishedDate修改且DeliveredDate没修改了,Issue状态改为FinishedDate关联的状态
            else if(isFinishedDateChanged && !isDeliveredDateChanged){
                List issueStatuses = issueTypeLogic.getRelationDataStatus(issue.getIssueType(),
                    RelationDate.FINISHED_DATE);
                if(issueStatuses != null && issueStatuses.size() > 0){
                    IssueStatus issueStatus = (IssueStatus) issueStatuses.get(0);
                    tStatus = issueStatus.getComp_id().getStatusName();
                    issue.setIssueStatus(tStatus);
                    session.update(issue);
                }

                //if need to save
                if (lgIssueType.isSaveStatusHistory(issue.getIssueType())) {
                    if (!fStatus.equalsIgnoreCase(tStatus)) {
                        LgIssueStatusHistory lgIssueStatusHistory =
                            new LgIssueStatusHistory();
                        lgIssueStatusHistory.addIssueStatusHistory(issue.getRid(),
                            fStatus, tStatus);
                    }
                }

            }

//           issue.setIssueStatus(status);


            this.getDbAccessor().saveOrUpdate(issue);
            this.getDbAccessor().saveOrUpdate(issueReportStatus);

            this.getDbAccessor().saveOrUpdate(issueconlusion);

        } catch (Exception ex) {
            log.error("Error: Update Issue Conclusion failed!");
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.conclusion.update.error",
                                        "Issue conclusion update exception!");
        }
    }

    /**
     * 根据主键删除IssueConclusion，级联删除对应IssueConclusionUg
     * @param rid Long
     */
    public void delete(java.lang.Long rid) {
        try {
            IssueConclusion issueConclusion =
                (IssueConclusion)this.getDbAccessor().getSession().get(
                    IssueConclusion.class,
                    rid);
            if (issueConclusion != null) {
                this.getDbAccessor().delete(issueConclusion);
                System.out.println("delete issueConclusion successful!");
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("issueConclusion.delete.exception",
                                        "delete issueConclusion  error!");
        }
    }


}
