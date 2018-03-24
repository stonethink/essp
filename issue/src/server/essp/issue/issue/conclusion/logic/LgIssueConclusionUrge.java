package server.essp.issue.issue.conclusion.logic;

import db.essp.issue.IssueConclusionUg;
import db.essp.issue.IssueConclusion;
import com.wits.util.comDate;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import server.essp.issue.issue.conclusion.viewbean.VbIssueConclusionUrge;
import db.essp.issue.Issue;
import server.essp.issue.common.logic.AbstractISSUELogic;
import server.essp.issue.common.logic.LgAccount;
import server.essp.issue.common.logic.LgUpload;
import c2s.dto.FileInfo;
import c2s.essp.common.account.IDtoAccount;
import server.essp.issue.common.logic.LgDownload;
import server.essp.common.mail.SendHastenMail;
import server.essp.issue.issue.mail.contbean.IssueConclusionUrgeMail;
import java.util.HashMap;
import itf.hr.LgHrUtilImpl;
import itf.hr.HrFactory;
import java.util.ArrayList;
import server.essp.common.mail.ContentBean;
import javax.activation.FileDataSource;
import java.io.File;
import server.essp.issue.issue.logic.LgIssue;

public class LgIssueConclusionUrge extends AbstractISSUELogic {
        /**
         *
         * 取到Hibernate的session对象
         * 取出Issue
         * 把取出的account放到viewbean中
         * 取出viewbean中的值
         * 注意除了取出上一次的account以外，还需要取出下拉框中的所有可以显示出来的选项
         * 取出IssueType下拉框的数据
         * 取出当前系统时间，与登陆的当前用户
         */
      public static final String vmFile1 = "mail/template/issue/UrgencyMailTemplate.htm";
      public static final String title = "please close the Issue!";
      public VbIssueConclusionUrge addPre(java.lang.Long issueRid){
           String sysDate = comDate.dateToString(new java.util.Date());
           String user=this.getUser().getUserLoginId();
        try{  Session session = this.getDbAccessor().getSession();
            Issue issue = (Issue) session.load(Issue.class, issueRid);
            VbIssueConclusionUrge  webVo = new VbIssueConclusionUrge();
            webVo.setAccountId(issue.getAccountId().toString());
            webVo.setIssueRid(issueRid.toString());
            webVo.setUrgedBy(user);
            webVo.setUrgedByScope(getUser().getUserType());
            webVo.setUrgeTo(issue.getFilleBy());
            webVo.setUrgeToScope(issue.getFilleByScope());
            webVo.setUrgDate(sysDate);
            return webVo;
        }
        catch (Exception ex) {
                ex.printStackTrace();
                throw new BusinessException(ex);
            }

      }
    /**
     * 根据传入AfIssueConclusionUrge新增IssueConclusionUg对象
     * @param param0 AfIssueConclusionUrge
     */
    public void add(server.essp.issue.issue.conclusion.form.
                    AfIssueConclusionUrge param0,String isMail) {
         IssueConclusionUg issueConclusionUg = new IssueConclusionUg();
         IssueConclusionUrgeMail mailbean = new  IssueConclusionUrgeMail();
         IssueConclusion issueConclusion = null;
         Long pk = new Long(param0.getIssueRid());
         LgIssue lgissue = new LgIssue();
         Issue issue = lgissue.load(pk);
         String fileUri = null;
         LgAccount accountLogic = new LgAccount();
         IDtoAccount account = accountLogic.getAccountByIssueRid(param0.
                    getIssueRid());
         String accountid = account.getId();
         String accountname = account.getName();
         try {
            mailbean.setIssue(issue.getIssueId()+"--"+issue.getIssueName());
            mailbean.setAccount(accountid+"--"+accountname);
            mailbean.setUrgedBy(param0.getUrgedBy());
            mailbean.setUrgedByScope(param0.getUrgedByScope());
            if(param0.getAttachment()!=null && param0.getAttachment().getFileSize()>0){
                 mailbean.setAttachment(param0.getAttachment());
            }
            mailbean.setAttachmentdesc(param0.getAttachmentdesc());
            mailbean.setDescription(param0.getDescription());
            mailbean.setUrgeTo(param0.getUrgeTo());
            mailbean.setUrgeToScope(param0.getUrgeToScope());
            mailbean.setIssueRid(param0.getIssueRid());
            Session s = this.getDbAccessor().getSession();
            issueConclusion = (IssueConclusion) s.get(IssueConclusion.class, pk);
            issueConclusionUg.setIssueConclusion(issueConclusion);
            issueConclusionUg.setUrgedBy(param0.getUrgedBy());
            issueConclusionUg.setUrgedByScope(param0.getUrgedByScope());
            issueConclusionUg.setUrgeTo(param0.getUrgeTo());
            issueConclusionUg.setUrgeToScope(param0.getUrgeToScope());
            issueConclusionUg.setUrgDate(comDate.toDate(param0.getUrgDate()));
            issueConclusionUg.setDescription(param0.getDescription());
            if (param0.getAttachment() != null &&
                param0.getAttachment().getFileName() != null &&
                param0.getAttachment().getFileSize() > 0) {
                //1. create FileInfo
                FileInfo fileInfo = new FileInfo();

                fileInfo.setAccountcode(account.getId());
                //2. create LgUpload
                LgUpload uploadLogic = new LgUpload();
                //3. init FileInfo Object
                uploadLogic.initFileInfo(param0.getAttachment(),
                                         fileInfo);

                fileInfo.setFilename(param0.getAttachment().getFileName());

                //4. execute upload
                uploadLogic.upload(param0.getAttachment(), fileInfo);
                issueConclusionUg.setAttachment(fileInfo.getFilename());
                issueConclusionUg.setAttachmentId(fileInfo.getId());

                // 获取fileInfo的路径
                fileUri = fileInfo.getServerFullPath(this.getServerRoot());
            }
            issueConclusionUg.setAttachmentdesc(param0.getAttachmentdesc());
            this.getDbAccessor().save(issueConclusionUg);

            //send mail
            if(isMail.equals("true")){
                SendHastenMail shm = new SendHastenMail();
                shm.sendHastenMail(vmFile1, title, getEmailDate(mailbean, fileUri));
            }
         } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("Conclusion.urge.add.exception",
                                        "add urge error!");
         }

      }


    /**
     * 根据主键查找IssueConclusionUg对象
     * @param rid Long
     * @return IssueConclusionUg
     */
    public IssueConclusionUg load(java.lang.Long rid) {
        IssueConclusionUg issueconlusionUg = null;
        try {
            Session session = this.getDbAccessor().getSession();
            issueconlusionUg = (IssueConclusionUg) session.get(
                IssueConclusionUg.class,
                rid);
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.conclusionUg.exception",
                                        "Get issue conclusionUg error!");
        }
        return issueconlusionUg;

    }
    /**
     * 获取Issue ConclusionUrge页面显示所需内容
     * @param rid Long
     * @return VbIssueConclusionUrge
     */

     public VbIssueConclusionUrge updatePre(java.lang.Long rid){
         VbIssueConclusionUrge vBean = new VbIssueConclusionUrge();
           vBean.setRid(String.valueOf(rid));
           String[] noCopyProperties={"attachment"};
         IssueConclusionUg issueConclusionUg = this.load(rid);
          Long  issueRid=issueConclusionUg.getIssueConclusion().getRid();
           vBean.setUrgDate(String.valueOf(issueConclusionUg.getUrgDate()));

           try { Session session = this.getDbAccessor().getSession();
            Issue issue = (Issue) session.load(Issue.class, issueRid);
            vBean.setAccountId(issue.getAccountId().toString());
               c2s.dto.DtoUtil.copyProperties(vBean, issueConclusionUg,noCopyProperties);
                vBean.setIssueRid(issueRid.toString());
               if(issueConclusionUg.getAttachmentId()!=null){
              LgDownload downloadLogic = new LgDownload();
              FileInfo fileInfo = new FileInfo();
              fileInfo.setId(issueConclusionUg.getAttachmentId());
              fileInfo.setFilename(issueConclusionUg.getAttachment());
              vBean.setAttachment(downloadLogic.getDownloadUrl(fileInfo));
          }

           } catch (Exception ex) {
               throw new BusinessException("issue.urge.updatepre.copy.exception",
                                           "copy properties error!");
           }
           if(vBean.getUrgedBy()==null) {
               vBean.setUrgedBy("");
           }
           if(vBean.getUrgeTo()==null) {
               vBean.setUrgeTo("");
           }
           if(vBean.getUrgDate()==null) {
                vBean.setUrgDate("");
            }
            if(vBean.getDescription()==null) {
                           vBean.setDescription("");
            }
            if(vBean.getAttachment()==null) {
                           vBean.setAttachment("");
            }
            if(vBean.getAttachmentdesc()==null) {
                          vBean.setAttachmentdesc("");
           }

      return vBean;


}
    /**
     * 根据传入AfIssueConclusionUrge更新IssueConclusionUg对象
     * @param param0 AfIssueConclusionUrge
     */
    public void update(server.essp.issue.issue.conclusion.form.
                       AfIssueConclusionUrge param0,String isMail) {
        Long rid = new Long(param0.getRid());
        String[] noCopyProperties={"attachment"};
        String fileUri=null;
        IssueConclusionUrgeMail mailbean = new  IssueConclusionUrgeMail();
        Long pk = new Long(param0.getIssueRid());
        LgIssue lgissue = new LgIssue();
        Issue issue = lgissue.load(pk);

        LgAccount accountLogic = new LgAccount();
        IDtoAccount account = accountLogic.getAccountByIssueRid(param0.
                    getIssueRid());
        String accountid = account.getId();
        String accountname = account.getName();
        String attachmentname=null;
        try {
            IssueConclusionUg issueConclusionUg = this.load(rid);
            c2s.dto.DtoUtil.copyProperties(issueConclusionUg, param0,noCopyProperties);

            if (issueConclusionUg.getAttachment() != null &&
                issueConclusionUg.getAttachment().length() > 0) {
                String realpath = "/ISSUE/"+accountid+"/";
                String fileformat = issueConclusionUg.getAttachment().substring( issueConclusionUg.getAttachment().indexOf("."),issueConclusionUg.getAttachment().length());
                fileUri = this.getServerRoot() + realpath + issueConclusionUg.getAttachmentId()+fileformat;
                attachmentname = (String)issueConclusionUg.getAttachment();
            }


            if (param0.getAttachment() != null &&
                param0.getAttachment().getFileName() != null &&
                param0.getAttachment().getFileSize() > 0) {
                //1. create FileInfo
                FileInfo fileInfo = new FileInfo();
                fileInfo.setAccountcode(account.getId());
                if (issueConclusionUg.getAttachmentId() != null) {
                 fileInfo.setId( issueConclusionUg.getAttachmentId());
                }

                //2. create LgUpload
                LgUpload uploadLogic = new LgUpload();
                //3. init FileInfo Object
                uploadLogic.initFileInfo(param0.getAttachment(),
                                         fileInfo);

                fileInfo.setFilename(param0.getAttachment().getFileName());
                //4. execute upload
                uploadLogic.upload(param0.getAttachment(), fileInfo);
                issueConclusionUg.setAttachment(fileInfo.getFilename());
                issueConclusionUg.setAttachmentId(fileInfo.getId());

                // 获取fileInfo的路径
                fileUri = fileInfo.getServerFullPath(this.getServerRoot());
            }

            issueConclusionUg.setUrgDate(comDate.toDate(param0.getUrgDate()));
             this.getDbAccessor().update(issueConclusionUg);

             //send mail
             if(isMail.equals("true")){
                 mailbean.setIssue(issue.getIssueId() + "--" + issue.getIssueName());
                 mailbean.setAccount(accountid + "--" + accountname);
                 mailbean.setUrgedBy(param0.getUrgedBy());
                 mailbean.setUrgedByScope(param0.getUrgedByScope());
                 if (param0.getAttachment() != null &&
                     param0.getAttachment().getFileSize() > 0) {
                     mailbean.setAttachment(param0.getAttachment());
                 }
                 mailbean.setAttachmentdesc(param0.getAttachmentdesc());
                 mailbean.setDescription(param0.getDescription());
                 mailbean.setUrgeTo(param0.getUrgeTo());
                 mailbean.setUrgeToScope(param0.getUrgeToScope());
                 mailbean.setIssueRid(param0.getIssueRid());

                 SendHastenMail shm = new SendHastenMail();
                 if (fileUri == null || fileUri.length() == 0) {
                     shm.sendHastenMail(vmFile1, title,
                                        getEmailDate(mailbean, "", ""));
                 } else {
                     shm.sendHastenMail(vmFile1, title,
                                        getEmailDate(mailbean, fileUri, attachmentname));
                 }

             }

        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("issue.conclusion.urge.update.exception",
                                        "update conclusion urge error!");
        }


}


    /**
     * 根据主键删除IssueConclusionUg对象
     * @param rid Long
     */
    public void delete(java.lang.Long rid) {
        try {
            IssueConclusionUg issueConclusionUg =
                (IssueConclusionUg)this.getDbAccessor().getSession().get(
                IssueConclusionUg.class,
                rid);
            if (issueConclusionUg != null) {
                this.getDbAccessor().delete(issueConclusionUg);
                System.out.println("delete reply successful!");
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("conclusion.urge.delete.exception",
                                        "delete uege  error!");

        }

    }
    /**
     * 根据传入的IssueConclusionUrgeMail对象,获取邮件信息
     * @param mailbean IssueConclusionUrgeMail
     * @param Uri String
     * @param attachmentname String
     * @return HashMap
     */

     public HashMap getEmailDate(IssueConclusionUrgeMail mailbean, String Uri,String attachmentname) {

        HashMap hm = new HashMap();
        LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
        String sendEmail="", user,toEmail="";
        user = mailbean.getUrgeTo();
        try {

            ArrayList al = new ArrayList();
            ContentBean cb = new ContentBean();

            al.add(mailbean);
            cb.setUser(user);
            String mail[] = user.split(",");
            for (int i = 0; i < mail.length; i++) {
                if(sendEmail.length()>0){
                    sendEmail=sendEmail+","+ihui.getUserEmail(mail[i]);
                }else{
                    sendEmail = ihui.getUserEmail(mail[i]);
                }
            }

            String cc[] = mailbean.getUrgedBy().split(",");
            for (int i = 0; i < cc.length; i++) {
                if(toEmail.length()>0){
                    toEmail = toEmail+","+ihui.getUserEmail(cc[i]);
                }else{
                    toEmail = ihui.getUserEmail(cc[i]);
                }
            }

            cb.setEmail(sendEmail);
            cb.setMailcontent(al);

            if(Uri!=null && Uri.length()>0){

                FileDataSource fds;
                fds = new FileDataSource(Uri);
                File f = fds.getFile();
                HashMap atts = new HashMap();

                if(mailbean.getAttachment()!= null &&
                        mailbean.getAttachment().getFileSize() > 0){
                    atts.put(mailbean.getAttachment().getFileName(), f);
                }else{
                    atts.put(attachmentname, f);
                }
                cb.setAttachments(atts);
            }
            cb.setCcAddress(toEmail);
            hm.put(user, cb);

        } catch (Throwable tx) {
            String msg = "error get all  Eamildata!";
            log.error(msg, tx);
            throw new BusinessException("", msg, tx);
        }

        return hm;
     }

    /**
     * 根据传入的IssueConclusionUrgeMail对象,获取邮件信息
     * @param mailbean IssueConclusionUrgeMail
     * @param Uri String
     * @return HashMap
     */

     public HashMap getEmailDate(IssueConclusionUrgeMail mailbean, String Uri) {

        HashMap hm = new HashMap();
        LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
        String sendEmail="", user,toEmail="";
        user = mailbean.getUrgeTo();
        try {

            ArrayList al = new ArrayList();
            ContentBean cb = new ContentBean();

            al.add(mailbean);
            cb.setUser(user);
            String mail[] = user.split(",");
            for (int i = 0; i < mail.length; i++) {
                if(sendEmail.length()>0){
                    sendEmail=sendEmail+","+ihui.getUserEmail(mail[i]);
                }else{
                    sendEmail = ihui.getUserEmail(mail[i]);
                }
            }

            String cc[] = mailbean.getUrgedBy().split(",");
            for (int i = 0; i < cc.length; i++) {
                if(toEmail.length()>0){
                    toEmail = toEmail+","+ihui.getUserEmail(cc[i]);
                }else{
                    toEmail = ihui.getUserEmail(cc[i]);
                }
            }

            cb.setEmail(sendEmail);
            cb.setMailcontent(al);

            if(mailbean.getAttachment()!=null && mailbean.getAttachment().getFileSize()>0){
                FileDataSource fds;
                fds = new FileDataSource(Uri);
                File f = fds.getFile();
                HashMap atts = new HashMap();
                atts.put(mailbean.getAttachment().getFileName(), f);
                cb.setAttachments(atts);
            }
            cb.setCcAddress(toEmail);
            hm.put(user, cb);

        } catch (Throwable tx) {
            String msg = "error get all  Eamildata!";
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

}
