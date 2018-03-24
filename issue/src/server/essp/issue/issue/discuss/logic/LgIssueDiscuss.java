package server.essp.issue.issue.discuss.logic;

import db.essp.issue.IssueDiscussTitle;
import db.essp.issue.IssueDiscussReply;
import server.essp.issue.issue.discuss.form.*;
import net.sf.hibernate.*;
import server.framework.common.*;
import com.wits.util.comDate;
import server.essp.issue.common.logic.AbstractISSUELogic;
import db.essp.issue.Issue;
import c2s.essp.common.account.IDtoAccount;
import server.essp.issue.common.logic.LgAccount;
import server.essp.issue.common.logic.LgUpload;
import c2s.dto.FileInfo;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussTitle;
import server.essp.issue.common.logic.LgDownload;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussReply;
import java.util.List;
import java.util.ArrayList;
import server.essp.common.mail.SendHastenMail;
import itf.hr.HrFactory;
import javax.activation.FileDataSource;
import java.util.HashMap;
import server.essp.common.mail.ContentBean;
import server.essp.issue.issue.mail.contbean.IssueConclusionGeneralMail;
import java.io.File;
import itf.hr.LgHrUtilImpl;
import server.essp.issue.issue.mail.contbean.IssueConclusionDiscussMail;
import c2s.essp.common.user.DtoCustomer;
import db.essp.issue.IssueDiscussMailBak;


public class LgIssueDiscuss extends AbstractISSUELogic {
    /**
     * 根据AfIssueDiscussTitle新增IssueDiscussTitle对象
     * @param afTitle AfIssueDiscussTitle
     */
    public static final String vmFile11 = "mail/template/issue/DiscussMailTempl.html";
    public static final String vmFile12 = "mail/template/issue/DiscussMailTemplByRemark.html";
    public static final String title = "please look through it!";
    public IssueDiscussTitle addTitle(AfIssueDiscussTitle afTitle,String isMail) {
        IssueDiscussTitle discussTitle = new IssueDiscussTitle();
        String fileUri=null;
        try {

            if (afTitle.getAttachment() != null &&
                !afTitle.getAttachment().getFileName().equals("") &&
                afTitle.getAttachment().getFileSize() != 0) {
                //1. create FileInfo
                FileInfo fileInfo = new FileInfo();
                LgAccount accountLogic = new LgAccount();
                IDtoAccount account = accountLogic.getAccountByIssueRid(afTitle.
                    getIssueRid());
                fileInfo.setAccountcode(account.getId());

                //2. create LgUpload
                LgUpload uploadLogic = new LgUpload();
                //3. init FileInfo Object
                uploadLogic.initFileInfo(afTitle.getAttachment(), fileInfo);
                fileInfo.setFilename(afTitle.getAttachment().getFileName());
                //4. execute upload
                uploadLogic.upload(afTitle.getAttachment(), fileInfo);

                // 获取fileInfo的路径
                fileUri = fileInfo.getServerFullPath(this.getServerRoot());

                discussTitle.setAttachment(fileInfo.getFilename());
                discussTitle.setAttachmentId(fileInfo.getId());
                discussTitle.setAttachmentDesc(afTitle.getAttachmentDesc());
            }

            discussTitle.setTitle(afTitle.getTitle());
            discussTitle.setDescription(afTitle.getDescription());
            discussTitle.setFilledBy(afTitle.getFilledBy());
            discussTitle.setFilledByScope(afTitle.getFilledByScope());
            discussTitle.setFilledDate(comDate.toDate(afTitle.getFilledDate()));
            discussTitle.setTo(afTitle.getTo());
            discussTitle.setCc(afTitle.getCc());
            discussTitle.setSendremark(afTitle.getSendremark());
            discussTitle.setRemark(afTitle.getRemark());

            /**
             *  load IssueDiscussTitle
             */
            Long issueRid = new Long(afTitle.getIssueRid());
            Issue issue =
                (Issue)this.getDbAccessor().load(Issue.class, issueRid);
            String issueid = (String)issue.getIssueId();
            String issuename = (String)issue.getIssueName();

            String Accountid =issue.getAccountId().toString();
            LgAccount accountLogic = new LgAccount();
            IDtoAccount account = accountLogic.getAccountByRid(Accountid);
            String accountid = account.getId();
            String accountname = account.getName();

            discussTitle.setIssue(issue);
            this.getDbAccessor().save(discussTitle);
            String accountrid = account.getRid().toString();
            LgIssueDiscuss lg = new LgIssueDiscuss();
            IssueDiscussMailBak idmb = lg.getIssueDiscussMailBak(accountrid,issue.getIssueType(),"Topic");
            if(idmb!=null){
                if(afTitle.getTo()!=null && afTitle.getTo().length()>0  &&  afTitle.getCc()!=null && afTitle.getCc().length()>0){
                    idmb.setMailto(afTitle.getTo());
                    idmb.setMailcc(afTitle.getCc());
                    this.getDbAccessor().update(idmb);
                }else if(afTitle.getTo()!=null && afTitle.getTo().length()>0){
                    idmb.setMailto(afTitle.getTo());
                    this.getDbAccessor().update(idmb);
                }else if(afTitle.getCc()!=null && afTitle.getCc().length()>0){
                    idmb.setMailcc(afTitle.getCc());
                    this.getDbAccessor().update(idmb);
                }
            }else{
                IssueDiscussMailBak id = new IssueDiscussMailBak();
                id.setIssuetype(issue.getIssueType());
                id.setAcntrid(accountrid);
                id.setDiscusstype("Topic");
                id.setMailto(afTitle.getTo());
                id.setMailcc(afTitle.getCc());
                this.getDbAccessor().save(id);
            }

            if(isMail.equals("true")){
                IssueConclusionDiscussMail icdm = new IssueConclusionDiscussMail();
                icdm.setMailto(afTitle.getTo());
                icdm.setCc(afTitle.getCc());
                icdm.setIssue(issueid+"--"+issuename);
                icdm.setAccount(accountid+"--"+accountname);
                if(afTitle.getTitle()!=null && afTitle.getTitle().length()>0){
                    icdm.setTitle(afTitle.getTitle());
                }
                icdm.setFilldate(afTitle.getFilledDate());
                icdm.setFillby(afTitle.getFilledBy());
                icdm.setFilledByScope(afTitle.getFilledByScope());
                if(afTitle.getAttachment().getFileName()!=null && afTitle.getAttachment().getFileSize()>0){
                    icdm.setAttachment(afTitle.getAttachment());
                }
                if(afTitle.getAttachmentDesc()!=null && afTitle.getAttachmentDesc().length()>0){
                    icdm.setAttachmentdesc(afTitle.getAttachmentDesc());
                }
                if(afTitle.getDescription()!=null && afTitle.getDescription().length()>0){
                    icdm.setDescription(afTitle.getDescription());
                }
                //发送邮件
                SendHastenMail shm = new SendHastenMail();
                if(afTitle.getSendremark()!=null && (afTitle.getSendremark()).equals("true")){
                    if(afTitle.getRemark()!=null && afTitle.getRemark().length()>0){
                        icdm.setRemark(afTitle.getRemark());
                    }
                    shm.sendHastenMail(vmFile12, title, getEmailDate(icdm,fileUri));
                }else{
                    shm.sendHastenMail(vmFile11, title, getEmailDate(icdm,fileUri));
                }


            }
        }catch(BusinessException ex){
            throw ex;
        }
        catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("discuss.title.add.exception",
                                        "Add title error!");
        }
        return discussTitle;
    }

    /**
     * 依据传入的typeName获取该IssueType对象
     * @param typeName String
     * @return IssueType
     */
    public IssueDiscussTitle load(String title) throws BusinessException {
        IssueDiscussTitle discussTitle = new IssueDiscussTitle();
        try {
            Session session = this.getDbAccessor().getSession();
            discussTitle = (IssueDiscussTitle) session.load(IssueDiscussTitle.class,
                title);
            return discussTitle;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("discuss.title.load.exception",
                                        "Load title  error!");
        }
    }

    /**
     * 根据主键查找IssueDiscussTitle
     * @param rid Long
     * @return IssueDiscussTitle
     */
    public IssueDiscussTitle loadTitle(java.lang.Long rid) {
        try {
            IssueDiscussTitle issueDiscussTitle =
                (IssueDiscussTitle)this.getDbAccessor().load(IssueDiscussTitle.class,
                rid);

            return issueDiscussTitle;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("load.title.exception",
                                        "load title  error!");

        }

    }

    /**
     * 根据ISSUE rid查找AccountManager
     * @param rid Long
     * @return String
     */
    public String getPM(java.lang.Long rid) {
        String pmName = "";
        try {

            Issue issue =
                (Issue)this.getDbAccessor().load(Issue.class, rid);
            LgAccount accountLogic = new LgAccount();
            if (issue != null) {
                Long accountRid = issue.getAccountId();
                pmName = accountLogic.getAccountManager(accountRid.
                    toString());
                return pmName;
            }
            return pmName;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("load.title.exception",
                                        "load title  error!");
        }

    }

    /**
     * 根据主键查找IssueDiscussTitle
     * @param rid Long
     * @return IssueDiscussTitle
     */
    public VbIssueDiscussTitle getTitle(java.lang.Long rid) {
        VbIssueDiscussTitle vbDiscussTitle = new VbIssueDiscussTitle();
        try {
            IssueDiscussTitle issueDiscussTitle = this.loadTitle(rid);
            //set viewBeam with result record
            vbDiscussTitle.setRid(issueDiscussTitle.getRid().toString());
            vbDiscussTitle.setTitle(issueDiscussTitle.getTitle());
            vbDiscussTitle.setFilledBy(issueDiscussTitle.getFilledBy());
            vbDiscussTitle.setFilledByScope(issueDiscussTitle.getFilledByScope());
            vbDiscussTitle.setFilledDate(issueDiscussTitle.getFilledDate().
                                         toString());
            if(issueDiscussTitle.getTo()!=null && issueDiscussTitle.getTo().length()>0){
                vbDiscussTitle.setTo(issueDiscussTitle.getTo());
            }
            if(issueDiscussTitle.getCc()!=null && issueDiscussTitle.getCc().length()>0){
                vbDiscussTitle.setCc(issueDiscussTitle.getCc());
            }
            if(issueDiscussTitle.getRemark()!=null && issueDiscussTitle.getRemark().length()>0){
                vbDiscussTitle.setRemark(issueDiscussTitle.getRemark());
            }
            if(issueDiscussTitle.getSendremark()!=null && issueDiscussTitle.getSendremark().length()>0){
                vbDiscussTitle.setSendremark(issueDiscussTitle.getSendremark());
            }
            vbDiscussTitle.setReplys(new ArrayList(issueDiscussTitle.
                getIssueDiscussReplies()));

            /**
             * get download url
             */
            String accountRid = issueDiscussTitle.getIssue().getAccountId().
                                toString();
            LgAccount accountLogic = new LgAccount();
            String accountCode = accountLogic.getAccountId(accountRid);
            if (issueDiscussTitle.getAttachmentId() != null) {
                LgDownload downloadLogic = new LgDownload();
                FileInfo fileInfo = new FileInfo();
                fileInfo.setAccountcode(accountCode);
                fileInfo.setId(issueDiscussTitle.getAttachmentId());
                fileInfo.setFilename(issueDiscussTitle.getAttachment());
                vbDiscussTitle.setAttachment(downloadLogic.getDownloadUrl(
                    fileInfo));
            }
            if (issueDiscussTitle.getAttachmentDesc() == null ||
                issueDiscussTitle.getAttachmentDesc().equalsIgnoreCase("null")) {
                vbDiscussTitle.setAttachmentDesc("");
            } else {
                vbDiscussTitle.setAttachmentDesc(issueDiscussTitle.
                                                 getAttachmentDesc());
            }
            vbDiscussTitle.setIssueRid(issueDiscussTitle.getIssue().getRid().
                                       toString());
            if (issueDiscussTitle.getDescription() == null ||
                issueDiscussTitle.getDescription().equalsIgnoreCase("null")) {
                vbDiscussTitle.setDescription("");
            } else {
                vbDiscussTitle.setDescription(issueDiscussTitle.getDescription());
            }
            return vbDiscussTitle;

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("load.title.exception",
                                        "get title  error!");

        }
    }


    /**
     * 根据AfIssueDiscussTitle更新IssueDiscussTitle对象
     * @param afTitle AfIssueDiscussTitle
     */
    public void updateTitle(AfIssueDiscussTitle afTitle ,String isMail) {
        Long Rid = new Long(afTitle.getRid());
        String attachmentUpdateFlag = "false";
        String fileUri=null;
        String attachmentname=null;
        try {
            IssueDiscussTitle discussTitle = this.loadTitle(Rid);
            String issueid = (String)discussTitle.getIssue().getIssueId();
            String issuename = (String)discussTitle.getIssue().getIssueName();
            LgAccount accountLogic = new LgAccount();
                IDtoAccount account = accountLogic.getAccountByIssueRid(
                    afTitle.getIssueRid());
            String accountid = (String) account.getId();
            String accountname = (String) account.getName();
            if (discussTitle.getAttachment() != null &&
                discussTitle.getAttachment().length() > 0) {
                String realpath = "/ISSUE/"+accountid+"/";
                String fileformat = discussTitle.getAttachment().substring( discussTitle.getAttachment().indexOf("."),discussTitle.getAttachment().length());
                fileUri = this.getServerRoot() + realpath + discussTitle.getAttachmentId()+fileformat;
            }

            //if modify Attachment then update attachment
            if (afTitle.getAttachment() != null &&
                !afTitle.getAttachment().getFileName().equals("") &&
                afTitle.getAttachment().getFileSize() != 0) {
                FileInfo fileInfo = new FileInfo();

                fileInfo.setAccountcode(account.getId());

                if (discussTitle.getAttachmentId() != null) {
                    fileInfo.setId(discussTitle.getAttachmentId());
                }

                LgUpload uploadLogic = new LgUpload();
                uploadLogic.initFileInfo(afTitle.getAttachment(), fileInfo);
                fileInfo.setFilename(afTitle.getAttachment().getFileName());
                uploadLogic.upload(afTitle.getAttachment(), fileInfo);

                // 获取fileInfo的路径
                fileUri = fileInfo.getServerFullPath(this.getServerRoot());

                discussTitle.setAttachment(fileInfo.getFilename());
                discussTitle.setAttachmentId(fileInfo.getId());
                discussTitle.setAttachmentDesc(afTitle.getAttachmentDesc());
                attachmentUpdateFlag = "true";
            }
            if (attachmentUpdateFlag.equals("false") &&
                discussTitle.getAttachment() != null) {

                discussTitle.setAttachmentDesc(afTitle.getAttachmentDesc());

            }
            discussTitle.setDescription(afTitle.getDescription());
            discussTitle.setRst(afTitle.getRst());
            discussTitle.setFilledBy(afTitle.getFilledBy());
            discussTitle.setFilledByScope(afTitle.getFilledByScope());
            discussTitle.setFilledDate(comDate.toDate(afTitle.getFilledDate()));
            discussTitle.setTitle(afTitle.getTitle());
            discussTitle.setTo(afTitle.getTo());
            discussTitle.setCc(afTitle.getCc());
            discussTitle.setSendremark(afTitle.getSendremark());
            discussTitle.setRemark(afTitle.getRemark());
            this.getDbAccessor().update(discussTitle);

            Long issueRid = new Long(afTitle.getIssueRid());
            Issue issue =
                (Issue)this.getDbAccessor().load(Issue.class, issueRid);

            String accountrid = account.getRid().toString();
            LgIssueDiscuss lg = new LgIssueDiscuss();
            IssueDiscussMailBak idmb = lg.getIssueDiscussMailBak(accountrid,issue.getIssueType(),"Topic");
            if (idmb != null) {
                if(afTitle.getTo()!=null && afTitle.getTo().length()>0 && afTitle.getCc()!=null && afTitle.getCc().length()>0){
                        idmb.setMailto(afTitle.getTo());
                        idmb.setMailcc(afTitle.getCc());
                        this.getDbAccessor().update(idmb);
                }else if (afTitle.getTo()!=null && afTitle.getTo().length()>0){
                        idmb.setMailto(afTitle.getTo());
                        this.getDbAccessor().update(idmb);

                }else if(afTitle.getCc()!=null && afTitle.getCc().length()>0){
                        idmb.setMailcc(afTitle.getCc());
                        this.getDbAccessor().update(idmb);

                }
            } else {
                IssueDiscussMailBak id = new IssueDiscussMailBak();
                id.setAcntrid(accountrid);
                id.setIssuetype(issue.getIssueType());
                id.setDiscusstype("Topic");
                id.setMailto(afTitle.getTo());
                id.setMailcc(afTitle.getCc());
                this.getDbAccessor().save(id);
            }

            if(isMail.equals("true")){
                IssueConclusionDiscussMail icdm = new IssueConclusionDiscussMail();
                icdm.setMailto(afTitle.getTo());
                icdm.setCc(afTitle.getCc());
                icdm.setIssue(issueid+"--"+issuename);
                icdm.setAccount(accountid+"--"+accountname);
                if(afTitle.getTitle()!=null && afTitle.getTitle().length()>0){
                    icdm.setTitle(afTitle.getTitle());
                }
                icdm.setFilldate(afTitle.getFilledDate());
                icdm.setFillby(afTitle.getFilledBy());
                icdm.setFilledByScope(afTitle.getFilledByScope());
                if(afTitle.getAttachment().getFileName()!=null && afTitle.getAttachment().getFileSize()>0){
                    icdm.setAttachment(afTitle.getAttachment());
                }else if(discussTitle.getAttachment()!=null &&
                         discussTitle.getAttachment().length()>0){
                    attachmentname = (String)discussTitle.getAttachment();
                }

                if(afTitle.getAttachmentDesc()!=null && afTitle.getAttachmentDesc().length()>0){
                    icdm.setAttachmentdesc(afTitle.getAttachmentDesc());
                }
                if(afTitle.getDescription()!=null && afTitle.getDescription().length()>0){
                    icdm.setDescription(afTitle.getDescription());
                }

                //发送邮件
                SendHastenMail shm = new SendHastenMail();
                if(afTitle.getSendremark()!=null && afTitle.getSendremark().equals("true")){
                    if(afTitle.getRemark()!=null && afTitle.getRemark().length()>0){
                        icdm.setRemark(afTitle.getRemark());
                    }
                    if (fileUri == null || fileUri.length() == 0) {
                        shm.sendHastenMail(vmFile12, title,
                                           getEmailDate(icdm, "", ""));
                    } else {
                        shm.sendHastenMail(vmFile12, title,
                                           getEmailDate(icdm, fileUri,
                            attachmentname));
                    }
                }else{
                    if (fileUri == null || fileUri.length() == 0) {
                        shm.sendHastenMail(vmFile11, title,
                                           getEmailDate(icdm, "", ""));
                    } else {
                        shm.sendHastenMail(vmFile11, title,
                                           getEmailDate(icdm, fileUri,
                            attachmentname));
                    }

                }

            }

        }catch(BusinessException ex){
            throw ex;
        }catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("discuss.title.update.exception",
                                        "update title  error!");
        }

    }

    /**
     *  根据AfIssueDiscussReply新增IssueDiscussReply对象
     * @param reply AfIssueDiscussReply
     */
    public IssueDiscussReply addReply(AfIssueDiscussReply reply,String isMail) {
        IssueDiscussReply discussReply = new IssueDiscussReply();
        String fileUri=null;
        try {
            Long titleRid = new Long(reply.getTitleId());
            IssueDiscussTitle discussTitle =
                (IssueDiscussTitle)this.getDbAccessor().load(IssueDiscussTitle.class,
                titleRid);
            String issueid = (String)discussTitle.getIssue().getIssueId();
            String issuename = (String)discussTitle.getIssue().getIssueName();
            LgAccount accountLogic = new LgAccount();
            IDtoAccount account = accountLogic.getAccountByIssueRid(
                discussTitle.getIssue().getRid().toString());
            String accountid = (String) account.getId();
            String accountname = (String) account.getName();
            if (reply.getAttachment() != null &&
                !reply.getAttachment().getFileName().equals("") &&
                reply.getAttachment().getFileSize() != 0) {

                FileInfo fileInfo = new FileInfo();
                /**
                 * load IssueDiscussTitle
                 */


                fileInfo.setAccountcode(account.getId());

                LgUpload uploadLogic = new LgUpload();
                uploadLogic.initFileInfo(reply.getAttachment(), fileInfo);
                fileInfo.setFilename(reply.getAttachment().getFileName());
                uploadLogic.upload(reply.getAttachment(), fileInfo);

                // 获取fileInfo的路径
                fileUri = fileInfo.getServerFullPath(this.getServerRoot());
                discussReply.setAttachment(fileInfo.getFilename());
                discussReply.setAttachmentId(fileInfo.getId());
                discussReply.setAttachmentDesc(reply.getAttachmentDesc());
            }
            discussReply.setFilledDate(comDate.toDate(reply.getFilledDate()));
            discussReply.setFilledBy(reply.getFilledBy());
            discussReply.setFilledByScope(reply.getFilledByScope());
            discussReply.setDescription(reply.getDescription());
            discussReply.setRst(reply.getRst());
            discussReply.setTitle(reply.getTitle());
            discussReply.setIssueDiscussTitle(discussTitle);
            discussReply.setTo(reply.getTo());
            discussReply.setCc(reply.getCc());
            discussReply.setSendremark(reply.getSendremark());
            discussReply.setRemark(reply.getRemark());
            this.getDbAccessor().save(discussReply);


            Issue issue =
                (Issue)this.getDbAccessor().load(Issue.class, discussTitle.getIssue().getRid());

            String accountrid = account.getRid().toString();
            LgIssueDiscuss lg = new LgIssueDiscuss();
            IssueDiscussMailBak idmb = lg.getIssueDiscussMailBak(accountrid,issue.getIssueType(),"Reply");
            if(idmb!=null){
                if(reply.getTo()!=null && reply.getTo().length()>0 && reply.getCc()!=null && reply.getCc().length()>0){
                    idmb.setMailto(reply.getTo());
                    idmb.setMailcc(reply.getCc());
                    this.getDbAccessor().update(idmb);
                }else if(reply.getTo()!=null && reply.getTo().length()>0){
                    idmb.setMailto(reply.getTo());
                    this.getDbAccessor().update(idmb);
                }else if(reply.getCc()!=null && reply.getCc().length()>0){
                    idmb.setMailcc(reply.getCc());
                    this.getDbAccessor().update(idmb);
                }
            }else{
                IssueDiscussMailBak id = new IssueDiscussMailBak();
                id.setAcntrid(accountrid);
                id.setIssuetype(issue.getIssueType());
                id.setDiscusstype("Reply");
                id.setMailto(reply.getTo());
                id.setMailcc(reply.getCc());
                this.getDbAccessor().save(id);
            }

            if(isMail.equals("true")){
                IssueConclusionDiscussMail icdm = new IssueConclusionDiscussMail();
                icdm.setMailto(reply.getTo());
                icdm.setCc(reply.getCc());
                icdm.setIssue(issueid+"--"+issuename);
                icdm.setAccount(accountid+"--"+accountname);
                if(reply.getTitle()!=null && reply.getTitle().length()>0){
                    icdm.setTitle(reply.getTitle());
                }
                icdm.setFilldate(reply.getFilledDate());
                icdm.setFillby(reply.getFilledBy());
                icdm.setFilledByScope(reply.getFilledByScope());
                if(reply.getAttachment().getFileName()!=null && reply.getAttachment().getFileSize()>0){
                    icdm.setAttachment(reply.getAttachment());
                }
                if(reply.getAttachmentDesc()!=null && reply.getAttachmentDesc().length()>0){
                    icdm.setAttachmentdesc(reply.getAttachmentDesc());
                }
                if(reply.getDescription()!=null && reply.getDescription().length()>0){
                    icdm.setDescription(reply.getDescription());
                }

                //发送邮件
                SendHastenMail shm = new SendHastenMail();
                if(reply.getSendremark()!=null && (reply.getSendremark()).equals("true")){
                    if(reply.getRemark()!=null && reply.getRemark().length()>0){
                        icdm.setRemark(reply.getRemark());
                    }
                    shm.sendHastenMail(vmFile12, title, getEmailDate(icdm, fileUri));
                }else{
                    shm.sendHastenMail(vmFile11, title, getEmailDate(icdm, fileUri));
                }
            }

        } catch(BusinessException ex){
            throw ex;
        }catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("discuss.apply.add.exception",
                                        "add apply  error!");
        }
        return discussReply;
    }

    /**
     * 根据主键查找IssueDiscussReply
     * @param rid Long
     * @return IssueDiscussReply
     */
    public IssueDiscussReply loadReply(java.lang.Long rid) {
        try {
            IssueDiscussReply issueDiscussReply =
                (IssueDiscussReply)this.getDbAccessor().load(IssueDiscussReply.class,
                rid);

            return issueDiscussReply;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("load.title.reply.exception",
                                        "load reply  error!");

        }

    }

    /**
     * 根据主键查找IssueDiscussReply
     * @param rid Long
     * @return VbIssueDiscussReply
     */
    public VbIssueDiscussReply getReply(java.lang.Long rid) {
        VbIssueDiscussReply vbDiscussReply = new VbIssueDiscussReply();
        try {
            IssueDiscussReply issueDiscussReply = this.loadReply(rid);
            IssueDiscussTitle issueDiscussTitle = issueDiscussReply.
                                                  getIssueDiscussTitle();
            String accountRid = issueDiscussTitle.getIssue().getAccountId().
                                toString();
            LgAccount accountLogic = new LgAccount();
            String accountCode = accountLogic.getAccountId(accountRid);
            //set viewBeam with result record
            if (issueDiscussReply.getAttachmentId() != null) {
                LgDownload downloadLogic = new LgDownload();
                FileInfo fileInfo = new FileInfo();
                fileInfo.setId(issueDiscussReply.getAttachmentId());
                fileInfo.setAccountcode(accountCode);
                fileInfo.setFilename(issueDiscussReply.getAttachment());
                vbDiscussReply.setAttachment(downloadLogic.getDownloadUrl(
                    fileInfo));
            }
            vbDiscussReply.setRid(issueDiscussReply.getRid().toString());
            vbDiscussReply.setTitleId(issueDiscussTitle.getRid().toString());
            vbDiscussReply.setTitle(issueDiscussReply.getTitle());
            vbDiscussReply.setFilledBy(issueDiscussReply.getFilledBy());
            vbDiscussReply.setFilledByScope(issueDiscussReply.getFilledByScope());
            vbDiscussReply.setFilledDate(issueDiscussReply.getFilledDate().
                                         toString());
            if(issueDiscussReply.getTo()!=null && issueDiscussReply.getTo().length()>0){
                vbDiscussReply.setTo(issueDiscussReply.getTo());
            }
            if(issueDiscussReply.getCc()!=null && issueDiscussReply.getCc().length()>0){
                vbDiscussReply.setCc(issueDiscussReply.getCc());
            }
            if(issueDiscussReply.getSendremark()!=null && issueDiscussReply.getSendremark().length()>0){
                vbDiscussReply.setSendremark(issueDiscussReply.getSendremark());
            }
            if(issueDiscussReply.getRemark()!=null && issueDiscussReply.getRemark().length()>0){
                vbDiscussReply.setRemark(issueDiscussReply.getRemark());
            }
            //check AttachmentDesc
            if (issueDiscussReply.getAttachmentDesc() == null ||
                issueDiscussReply.getAttachmentDesc().equalsIgnoreCase("null")) {
                vbDiscussReply.setAttachmentDesc("");
            } else {
                vbDiscussReply.setAttachmentDesc(issueDiscussReply.
                                                 getAttachmentDesc());
            }
            vbDiscussReply.setTitleId(issueDiscussReply.getIssueDiscussTitle().
                                      getRid().toString());
            if (issueDiscussReply.getDescription() == null ||
                issueDiscussReply.getDescription().equalsIgnoreCase("null")) {
                vbDiscussReply.setDescription("");
            } else {
                vbDiscussReply.setDescription(issueDiscussReply.getDescription());
            }
            return vbDiscussReply;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("load.title.reply.exception",
                                        "get reply  error!");

        }
    }


    /**
     * 根据AfIssueDiscussReply更新IssueDiscussReply对象
     * @param afIssueDiscussReply AfIssueDiscussReply
     */
    public void updateReply(server.essp.issue.issue.discuss.form.
                            AfIssueDiscussReply afIssueDiscussReply,String isMail) {
        Long rid = new Long(afIssueDiscussReply.getRid());
        String AttachmentUpdateFlag = "false";
        String fileUri=null;
        String attachmentname=null;
        try {
            IssueDiscussReply issueDiscussReply = this.loadReply(rid);
            String issueid = (String)issueDiscussReply.getIssueDiscussTitle().getIssue().getIssueId();
            String issuename = (String)issueDiscussReply.getIssueDiscussTitle().getIssue().getIssueName();
            LgAccount accountLogic = new LgAccount();

            IDtoAccount account = accountLogic.getAccountByIssueRid(
                    issueDiscussReply.getIssueDiscussTitle().getIssue().
                    getRid().
                    toString());

            String accountid = (String) account.getId();
            String accountname = (String) account.getName();

            if (issueDiscussReply.getAttachment() != null &&
                issueDiscussReply.getAttachment().length() > 0) {
                String realpath = "/ISSUE/"+accountid+"/";
                String fileformat = issueDiscussReply.getAttachment().substring( issueDiscussReply.getAttachment().indexOf("."),issueDiscussReply.getAttachment().length());
                fileUri = this.getServerRoot() + realpath + issueDiscussReply.getAttachmentId()+fileformat;
                attachmentname = (String)issueDiscussReply.getAttachment();
            }

            //if modify Attachment then update attachment
            if (afIssueDiscussReply.getAttachment() != null &&
                !afIssueDiscussReply.getAttachment().getFileName().equals(
                    "") &&
                afIssueDiscussReply.getAttachment().getFileSize() != 0) {
                FileInfo fileInfo = new FileInfo();

                fileInfo.setAccountcode(account.getId());
                LgUpload uploadLogic = new LgUpload();
                uploadLogic.initFileInfo(afIssueDiscussReply.getAttachment(),
                                         fileInfo);
                fileInfo.setFilename(afIssueDiscussReply.getAttachment().
                                     getFileName());
                uploadLogic.upload(afIssueDiscussReply.getAttachment(),
                                   fileInfo);
                // 获取fileInfo的路径
                fileUri = fileInfo.getServerFullPath(this.getServerRoot());

                issueDiscussReply.setAttachment(fileInfo.getFilename());
                issueDiscussReply.setAttachmentId(fileInfo.getId());
                issueDiscussReply.setAttachmentDesc(afIssueDiscussReply.
                    getAttachmentDesc());
                AttachmentUpdateFlag = "true";
            }
            if (AttachmentUpdateFlag.equals("false") &&
                issueDiscussReply.getAttachment() != null) {

                issueDiscussReply.setAttachmentDesc(afIssueDiscussReply.
                    getAttachmentDesc());
            }

            issueDiscussReply.setTitle(afIssueDiscussReply.getTitle());
            issueDiscussReply.setDescription(afIssueDiscussReply.
                                             getDescription());
            issueDiscussReply.setRst(afIssueDiscussReply.getRst());
            issueDiscussReply.setFilledBy(afIssueDiscussReply.getFilledBy());
            issueDiscussReply.setFilledByScope(afIssueDiscussReply.getFilledByScope());
            issueDiscussReply.setFilledDate(comDate.toDate(
                afIssueDiscussReply.
                getFilledDate()));
            issueDiscussReply.setTo(afIssueDiscussReply.getTo());
            issueDiscussReply.setCc(afIssueDiscussReply.getCc());
            issueDiscussReply.setSendremark(afIssueDiscussReply.getSendremark());
            issueDiscussReply.setRemark(afIssueDiscussReply.getRemark());
            this.getDbAccessor().update(issueDiscussReply);

            Issue issue =
                (Issue)this.getDbAccessor().load(Issue.class, issueDiscussReply.getIssueDiscussTitle().getIssue().
                    getRid());

            String accountrid = account.getRid().toString();
            LgIssueDiscuss lg = new LgIssueDiscuss();
            IssueDiscussMailBak idmb = lg.getIssueDiscussMailBak(accountrid,issue.getIssueType(),"Reply");
            if(idmb!=null){
                if(afIssueDiscussReply.getTo()!=null && afIssueDiscussReply.getTo().length()>0 &&  afIssueDiscussReply.getCc()!=null && afIssueDiscussReply.getCc().length()>0){
                    idmb.setMailto(afIssueDiscussReply.getTo());
                    idmb.setMailcc(afIssueDiscussReply.getCc());
                    this.getDbAccessor().update(idmb);
                }else if(afIssueDiscussReply.getTo()!=null && afIssueDiscussReply.getTo().length()>0){
                    idmb.setMailto(afIssueDiscussReply.getTo());
                    this.getDbAccessor().update(idmb);
                }else if(afIssueDiscussReply.getCc()!=null && afIssueDiscussReply.getCc().length()>0){
                    idmb.setMailcc(afIssueDiscussReply.getCc());
                    this.getDbAccessor().update(idmb);
                }
            }else{
                IssueDiscussMailBak id = new IssueDiscussMailBak();
                id.setAcntrid(accountrid);
                id.setIssuetype(issue.getIssueType());
                id.setDiscusstype("Reply");
                id.setMailto(afIssueDiscussReply.getTo());
                id.setMailcc(afIssueDiscussReply.getCc());
                this.getDbAccessor().save(id);
            }


            if(isMail.equals("true")){
                IssueConclusionDiscussMail icdm = new IssueConclusionDiscussMail();
                icdm.setMailto(afIssueDiscussReply.getTo());
                icdm.setCc(afIssueDiscussReply.getCc());
                icdm.setIssue(issueid+"--"+issuename);
                icdm.setAccount(accountid+"--"+accountname);
                if(afIssueDiscussReply.getTitle()!=null && afIssueDiscussReply.getTitle().length()>0){
                    icdm.setTitle(afIssueDiscussReply.getTitle());
                }
                icdm.setFilldate(afIssueDiscussReply.getFilledDate());
                icdm.setFillby(afIssueDiscussReply.getFilledBy());
                icdm.setFilledByScope(afIssueDiscussReply.getFilledByScope());
                if(afIssueDiscussReply.getAttachment().getFileName()!=null && afIssueDiscussReply.getAttachment().getFileSize()>0){
                    icdm.setAttachment(afIssueDiscussReply.getAttachment());
                }
                if(afIssueDiscussReply.getAttachmentDesc()!=null && afIssueDiscussReply.getAttachmentDesc().length()>0){
                    icdm.setAttachmentdesc(afIssueDiscussReply.getAttachmentDesc());
                }
                if(afIssueDiscussReply.getDescription()!=null && afIssueDiscussReply.getDescription().length()>0){
                    icdm.setDescription(afIssueDiscussReply.getDescription());
                }

                //发送邮件
                SendHastenMail shm = new SendHastenMail();
                if(afIssueDiscussReply.getSendremark()!=null && afIssueDiscussReply.getSendremark().equals("true")){
                    if(afIssueDiscussReply.getRemark()!=null && afIssueDiscussReply.getRemark().length()>0){
                         icdm.setRemark(afIssueDiscussReply.getRemark());
                     }
                    if (fileUri == null || fileUri.length() == 0) {
                        shm.sendHastenMail(vmFile12, title,
                                           getEmailDate(icdm, "", ""));
                    } else {
                        shm.sendHastenMail(vmFile12, title,
                                           getEmailDate(icdm, fileUri,
                            attachmentname));
                    }
                }else{
                    if (fileUri == null || fileUri.length() == 0) {
                        shm.sendHastenMail(vmFile11, title,
                                           getEmailDate(icdm, "", ""));
                    } else {
                        shm.sendHastenMail(vmFile11, title,
                                           getEmailDate(icdm, fileUri,
                            attachmentname));
                    }
                }
            }


        }catch(BusinessException ex){
            throw ex;
        }catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("discuss.title.reply.exception",
                                        "update reply error!");

        }

    }

    /**
     * 依据主键删除IssueDiscussTitle，级联删除IssueDiscussReply对象集合
     * @param rid Long
     */
    public void deleteTitle(java.lang.Long rid) {
        try {
            IssueDiscussTitle issueDiscussTitle =
                (IssueDiscussTitle)this.getDbAccessor().getSession().get(
                    IssueDiscussTitle.class,
                    rid);
            if (issueDiscussTitle != null) {
                this.getDbAccessor().delete(issueDiscussTitle);
            }

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("discuss.title.delete.exception",
                                        "delete title  error!");

        }
    }

    /**
     * 依据主键删除IssueDiscussReply
     */
    public void deleteReply(java.lang.Long rid) {
        try {
            IssueDiscussReply issueDiscussReply =
                (IssueDiscussReply)this.getDbAccessor().getSession().get(
                    IssueDiscussReply.class,
                    rid);
            if (issueDiscussReply != null) {
                IssueDiscussTitle issueDiscussTitle = issueDiscussReply.
                    getIssueDiscussTitle();
                issueDiscussTitle.getIssueDiscussReplies().remove(
                    issueDiscussReply);
                this.getDbAccessor().delete(issueDiscussReply);
                System.out.println("delete reply successful!");

            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("discuss.title.reply.delete.exception",
                                        "delete reply  error!");
        }
    }

    /**
     * 根据传入的IssueConclusionUrgeMail对象,获取邮件信息
     * @param mailbean IssueConclusionUrgeMail
     * @param Uri String
     * @return HashMap
     */

public HashMap getEmailDate(IssueConclusionDiscussMail icdm, String Uri) {

        HashMap hm = new HashMap();
        LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();

        String sendEmail="", user, toEmail="";
        user = icdm.getMailto();
        try {

            ArrayList al = new ArrayList();
            ContentBean cb = new ContentBean();

            al.add(icdm);
            cb.setUser(user);
            String mail[] = user.split(",");
            for (int i = 0; i < mail.length; i++) {
                if(sendEmail.length()>0){
                    if(ihui.getUserEmail(mail[i])==null || ihui.getUserEmail(mail[i]).length()==0){
                        if(ihui.findCustomer(mail[i]).getEmail()==null || ihui.findCustomer(mail[i]).getEmail().length()==0){
                            throw new BusinessException("","The Customer: ["+mail[i]+"] has not mail address!");
                        }else{
                            sendEmail = sendEmail + "," + ihui.findCustomer(mail[i]).getEmail();
                        }
                    }else{
                        sendEmail = sendEmail + "," + ihui.getUserEmail(mail[i]);
                    }
                }else{
                    if(ihui.getUserEmail(mail[i])==null || ihui.getUserEmail(mail[i]).length()==0){
                       if(ihui.findCustomer(mail[i]).getEmail()==null || ihui.findCustomer(mail[i]).getEmail().length()==0){
                           throw new BusinessException("","The Customer: ["+mail[i]+"] has not mail address!");
                       }else{
                           sendEmail = ihui.findCustomer(mail[i]).getEmail();
                       }
                    }else{
                       sendEmail = ihui.getUserEmail(mail[i]);
                    }
                }
            }
            if(icdm.getCc()!=null && icdm.getCc().length()>0){
                String cc[] = icdm.getCc().split(",");
                for (int i = 0; i < cc.length; i++) {
                    if (toEmail.length() > 0) {
                        if (ihui.getUserEmail(cc[i]) == null ||
                            ihui.getUserEmail(cc[i]).length() == 0) {
                            if (ihui.findCustomer(cc[i]).getEmail() == null ||
                                ihui.findCustomer(cc[i]).getEmail().length() ==
                                0) {
                                throw new BusinessException("",
                                    "The Customer: [" + mail[i] +
                                    "] has not mail address!");
                            } else {
                                toEmail = toEmail + "," +
                                          ihui.findCustomer(cc[i]).getEmail();
                            }
                        } else {
                            toEmail = toEmail + "," + ihui.getUserEmail(cc[i]);
                        }
                    } else {
                        if (ihui.getUserEmail(cc[i]) == null ||
                            ihui.getUserEmail(cc[i]).length() == 0) {
                            if (ihui.findCustomer(cc[i]).getEmail() == null ||
                                ihui.findCustomer(cc[i]).getEmail().length() ==
                                0) {
                                throw new BusinessException("",
                                    "The Customer: [" + mail[i] +
                                    "] has not mail address!");
                            } else {
                                toEmail = ihui.findCustomer(cc[i]).getEmail();
                            }
                        } else {
                            toEmail = ihui.getUserEmail(cc[i]);
                        }
                    }

                }
            }
            String fillby = ihui.getUserEmail(icdm.getFillby());
            if(toEmail.indexOf(fillby)==-1){
                if(toEmail==null || toEmail.length()==0){
                    toEmail = fillby;
                }else{
                    toEmail = toEmail + "," + fillby;
                }
            }
            cb.setEmail(sendEmail);
            cb.setMailcontent(al);

            if (icdm.getAttachment()!= null &&
                    icdm.getAttachment().getFileSize() > 0) {
                FileDataSource fds;
                fds = new FileDataSource(Uri);
                File f = fds.getFile();
                HashMap atts = new HashMap();
                atts.put(icdm.getAttachment().getFileName(), f);
                cb.setAttachments(atts);
            }
            cb.setCcAddress(toEmail);
            hm.put(user, cb);

        }catch(BusinessException ex){
            throw ex;
        }
        catch (Exception tx) {
            String msg = "error get all  Eamil data!";
            log.error(msg, tx);
            throw new BusinessException("", msg, tx);
        }

        return hm;
}

    /**
     * 根据传入的IssueConclusionUrgeMail对象,获取邮件信息
     * @param mailbean IssueConclusionUrgeMail
     * @param Uri String
     * @param attachmentname String
     * @return HashMap
     */

public HashMap getEmailDate(IssueConclusionDiscussMail icdm, String Uri,String attachmentname) {

    HashMap hm = new HashMap();
    LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
    String sendEmail="", user,toEmail="";
    user = icdm.getMailto();
    try {

        ArrayList al = new ArrayList();
        ContentBean cb = new ContentBean();

        al.add(icdm);
        cb.setUser(user);
            String mail[] = user.split(",");
            for (int i = 0; i < mail.length; i++) {
                if(sendEmail.length()>0){
                    if(ihui.getUserEmail(mail[i])==null || ihui.getUserEmail(mail[i]).length()==0){
                        if(ihui.findCustomer(mail[i]).getEmail()==null || ihui.findCustomer(mail[i]).getEmail().length()==0){
                            throw new BusinessException("","The Customer: ["+mail[i]+"] has not mail address!");
                        }else{
                            sendEmail = sendEmail + "," + ihui.findCustomer(mail[i]).getEmail();
                        }
                    }else{
                        sendEmail = sendEmail + "," + ihui.getUserEmail(mail[i]);
                    }
                }else{
                    if(ihui.getUserEmail(mail[i])==null || ihui.getUserEmail(mail[i]).length()==0){
                        if(ihui.findCustomer(mail[i]).getEmail()==null || ihui.findCustomer(mail[i]).getEmail().length()==0){
                            throw new BusinessException("","The Customer: [" + mail[i] +"] has not mail address!");
                        }else{
                            sendEmail = ihui.findCustomer(mail[i]).getEmail();
                        }
                    }else{
                       sendEmail = ihui.getUserEmail(mail[i]);
                    }
                }
            }
            if(icdm.getCc()!=null && icdm.getCc().length()>0){
                String cc[] = icdm.getCc().split(",");
                for (int i = 0; i < cc.length; i++) {
                    if (toEmail.length() > 0) {
                        if (ihui.getUserEmail(cc[i]) == null ||
                            ihui.getUserEmail(cc[i]).length() == 0) {
                            if (ihui.findCustomer(cc[i]).getEmail() == null ||
                                ihui.findCustomer(cc[i]).getEmail().length() ==
                                0) {
                                throw new BusinessException("",
                                    "The Customer: [" + mail[i] +
                                    "] has not mail address!");
                            } else {
                                toEmail = toEmail + "," +
                                          ihui.findCustomer(cc[i]).getEmail();
                            }
                        } else {
                            toEmail = toEmail + "," + ihui.getUserEmail(cc[i]);
                        }
                    } else {
                        if (ihui.getUserEmail(cc[i]) == null ||
                            ihui.getUserEmail(cc[i]).length() == 0) {
                            if (ihui.findCustomer(cc[i]).getEmail() == null ||
                                ihui.findCustomer(cc[i]).getEmail().length() ==
                                0) {
                                throw new BusinessException("",
                                    "The Customer: [" + mail[i] +
                                    "] has not mail address!");
                            } else {
                                toEmail = ihui.findCustomer(cc[i]).getEmail();
                            }
                        } else {
                            toEmail = ihui.getUserEmail(cc[i]);
                        }
                    }
                }
            }
        String fillby = ihui.getUserEmail(icdm.getFillby());
        if(toEmail.indexOf(fillby)==-1){
            if(toEmail==null || toEmail.length()==0){
                toEmail = fillby;
            }else{
                toEmail = toEmail + "," + fillby;
            }
        }

        cb.setEmail(sendEmail);
        cb.setMailcontent(al);

        if(Uri!=null && Uri.length()>0){

            FileDataSource fds;
            fds = new FileDataSource(Uri);
            File f = fds.getFile();
            HashMap atts = new HashMap();

            if(icdm.getAttachment()!= null &&
                    icdm.getAttachment().getFileSize() > 0){
                atts.put(icdm.getAttachment().getFileName(), f);
            }else{
                atts.put(attachmentname, f);
            }
            cb.setAttachments(atts);


        }
        cb.setCcAddress(toEmail);
        hm.put(user, cb);

    } catch(BusinessException ex){
        throw ex;
    }catch (Throwable tx) {
        String msg = "error get all  Eamil data!";
        log.error(msg, tx);
        throw new BusinessException("", msg, tx);
    }

    return hm;
}

/**
 * 设置SERVER_ROOT的路径
 * @return String
 */
     public String getServerRoot (){
         String root = null;
         try{
             root = server.essp.issue.common.logic.LgUpload.class.
             getResource("/").toString();
             root = root.replace('\\', '/');
             int index = root.lastIndexOf("/");
             root = root.substring(0, index);
             index = root.lastIndexOf("/");
             root = root.substring(0, index);
             index = root.lastIndexOf("/");
             root = root.substring(0, index);
             root = root.substring(6);

         }catch(Throwable tx){
             String msg = "error get all ServerRootPath!";
             log.error(msg, tx);
             throw new BusinessException("", msg, tx);

         }
         return root + "/../attachmentFiles";
 }

 /**
  * 根据 acntrid,issuetype,discusstype来获取收件人，抄送人
  * @param acntrid String
  * @param issuetype String
  * @param discusstype String
  * @return IssueDiscussMailBak
  */
 public IssueDiscussMailBak  getIssueDiscussMailBak(String acntrid,String issuetype,String discusstype){
     IssueDiscussMailBak idmb=null;
     try{
         String querySql = " from IssueDiscussMailBak  idmb  where idmb.acntrid='" + acntrid +
                           "' and idmb.issuetype='"+issuetype+"' and idmb.discusstype='"+discusstype+"'";
         Session session = this.getDbAccessor().getSession();
         Query q = session.createQuery(querySql);
         List dbResult = q.list();
         if(dbResult.size()>0){
             idmb = (IssueDiscussMailBak) dbResult.get(0);
         }
     }catch(Exception ex){
         log.error(ex);
         ex.printStackTrace();
     }
     return idmb;
 }

/*public static void main(String[] args)throws Exception{

    IssueDiscussMailBak idmb = new IssueDiscussMailBak();
    idmb.setAcntrid("6022");
    idmb.setIssuetype("psa");
    idmb.setDiscusstype("Topic");
    idmb.setMailcc("wenjun.yang");
    idmb.setMailto("wenjun.yang,stone.shi");

}*/
}
