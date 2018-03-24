package server.essp.issue.issue.logic;

import db.essp.issue.Issue;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import com.wits.util.comDate;
import java.util.Date;
import server.essp.issue.issue.viewbean.VbIssue;
import server.essp.issue.common.logic.AbstractISSUELogic;
import net.sf.hibernate.Session;
import c2s.essp.common.account.IDtoAccount;
import java.util.List;
import server.essp.issue.issue.form.AfIssue;
import db.essp.issue.IssueConclusion;
import db.essp.issue.IssueReportStatus;
import db.essp.issue.IssueResolution;
import net.sf.hibernate.Query;
import db.essp.issue.IssueType;
import java.util.ArrayList;
import server.framework.taglib.util.SelectOptionImpl;
import java.util.Date;
import server.essp.issue.common.logic.LgIssueType;
import c2s.dto.FileInfo;
import server.essp.issue.common.logic.LgUpload;
import server.essp.issue.common.logic.LgAccount;
import server.essp.issue.common.logic.LgDownload;
import db.essp.issue.IssueStatus;
import db.essp.issue.IssueStatusPK;
import server.essp.issue.common.constant.Status;
import c2s.essp.pms.account.DtoAcntKeyPersonnel;
import server.essp.issue.common.logic.LgFilledBy;
import server.essp.issue.common.logic.LgIssueBase;
import server.framework.taglib.util.TagUtils;
import server.essp.issue.issue.mail.contbean.IssueConclusionGeneralMail;
import server.essp.common.mail.SendHastenMail;
import itf.hr.HrFactory;
import javax.activation.FileDataSource;
import java.util.HashMap;
import server.essp.common.mail.ContentBean;
import java.io.File;
import itf.hr.LgHrUtilImpl;
import server.essp.issue.issue.mail.contbean.IssueConclusionUrgeMail;
import java.sql.ResultSet;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusion;
import server.essp.issue.common.constant.RelationDate;
import net.sf.hibernate.*;
import itf.issue.IIssueUtil;
import c2s.essp.common.issue.IDtoIssue;
import c2s.essp.common.issue.*;
import c2s.essp.common.user.DtoUserBase;

public class LgIssue extends AbstractISSUELogic implements IIssueUtil{

    public static final String vmFile1 ="mail/template/issue/GeneralMailTempl.html";
    public static final String title1 = "Please resolve the Issue!";
    public static final String vmFile2 ="mail/template/issue/GeneralMailTemplByClose.html";
    public static final String title2 = "The Issue has been resolved!";
    /**
     * 返回新增Issue页面显示所需内容
     * 1.用户有访问权限项目Id列表和对应项目经理
     * 2.所有IssueType及IssuePriority，IssueScope和IssueStatus列表
     * 从LgTypeList，LgPriorityList和LgStatusList获得(状态为可用)
     * 3.Issue.filledDate,默认为当前日期
     * @param userId String
     * @return VbIssueDetail
     */
    public VbIssue addPre(AfIssue issueForm, boolean isForEssp) throws
        BusinessException {
        /**
         * 取到Hibernate的session对象
         * 取出上一次的account(此处要判断是否上一次的accout为空的情况)
         * 把取出的account放到viewbean中
         * 取出viewbean中的值
         * 注意除了取出上一次的account以外，还需要取出下拉框中的所有可以显示出来的选项
         * 取出IssueType下拉框的数据
         * 取出当前系统时间，与登陆的当前用户
         */
        try {
            VbIssue webVo = new VbIssue();
            webVo.setAccountReadonly("false");
            webVo.setIssueTypeReadonly("false");
            webVo.setDuplationIssueDisabled(true);

            IDtoAccount account = null;
            LgAccount accountLogic = new LgAccount();
            LgIssueType issueTypeLogic = new LgIssueType();
            LgIssueBase idLogic = new LgIssueBase();

            //判断是否传进来accountId或accountCode信息
            //如果有的话，就设为只读，如果没有的话，执行this.getAccount()
            if (issueForm.getAccountId() != null &&
                !issueForm.getAccountId().trim().equals("")) { //有accountId的情况
                account = accountLogic.getAccount(issueForm.getAccountId());
                if (account != null) {
                    webVo.setAccountReadonly("true");
                }
            } else if (issueForm.getAccountCode() != null &&
                       !issueForm.getAccountCode().trim().equals("")) { //无accountId,但有accountCode的情况
                account = accountLogic.getAccountByCode(issueForm.
                    getAccountCode());
                if (account != null) {
                    webVo.setAccountReadonly("true");
                }
            } else {
//                account = this.getAccount();
            }

            String issueType = issueForm.getIssueType();
            String priority = issueForm.getPriority();
            String scope = issueForm.getScope();
            String issueStatus = issueForm.getIssueStatus();

            //加一个条件：当accountCode和issueType都不为空的时候才进入
            //原来只判断当issueType不为空就进入
            //modified by : Robin.zhang
            if (issueForm.getAccountCode() != null &&
                !issueForm.getAccountCode().equals("") && issueType != null &&
                !issueType.trim().equals("")) {
                if (issueTypeLogic.isExistIssueType(issueType)) { //判断是否为IssueType
                    webVo.setIssueType(issueType);
                    webVo.setIssueTypeReadonly("true");

                    List priorityOptions = issueTypeLogic.getPriorityOptions(
                        issueType);
                    webVo.setPriorityList(priorityOptions);
                    if (priority != null && !priority.trim().equals("")) {
                        if (issueTypeLogic.isExistPriority(issueType, priority)) {
                            webVo.setPriority(priority);
                            webVo.setPriorityReadonly("true");
                        }
                    }

                    List scopeOptions = issueTypeLogic.getScopeOptions(
                        issueType,
                        this.getUser().getUserType());
                    webVo.setScopeList(scopeOptions);
                    if (scope != null && !scope.trim().equals("")) {
                        if (issueTypeLogic.isExistScope(issueType, scope)) {
                            webVo.setScope(scope);
                            webVo.setScopeReadonly("true");
                        }
                    }

                    List issueStatusOptions = issueTypeLogic.getStatusOptions(
                        issueType);
                    webVo.setStatusList(issueStatusOptions);

                    if (issueStatus != null && !issueStatus.trim().equals("")) {
                        if (issueTypeLogic.isExistStatus(issueType, issueStatus)) {
                            webVo.setIssueStatus(issueStatus);
                            webVo.setIssueStatusReadonly("true");
                            String statusBelongto = issueTypeLogic.
                                getStatusBelongTo(
                                    issueType, issueStatus);
                            if (statusBelongto != null) {
                                webVo.setIssueStatusBelongto(statusBelongto);
                                if (statusBelongto.equals(Status.DUPLATION)) {
                                    webVo.setDuplationIssueDisabled(false);
                                    if (account != null) {
                                        List dbDuplationList = idLogic.
                                            getDuplationIssueOptions(
                                                account.getRid().toString(),
                                                null);
                                        webVo.setDuplationIssueList(
                                            dbDuplationList);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                TagUtils.addSelectedOption(webVo.getScopeList(),
                                           "  ----  Please Select  ----  ", "");
                TagUtils.addSelectedOption(webVo.getStatusList(),
                                           "  ----  Please Select  ----  ", "");
                TagUtils.addSelectedOption(webVo.getPriorityList(),
                                           "  ----  Please Select  ----  ", "");
            }

            webVo.setFilleBy(this.getUser().getUserLoginId());
            webVo.setFilleByScope(this.getUser().getUserType());
            webVo.setPhone(this.getUser().getPhone());
            webVo.setFax(this.getUser().getFax());
            webVo.setEmail(this.getUser().getEmail());

            LgFilledBy filledByLogic = new LgFilledBy();
            List list = this.getAccountOptions();

            if (account != null) {
                String accountRid = account.getRid().toString();
                webVo.setAccountId(accountRid);

                List filledByList = filledByLogic.getFilledByListOptions(
                    accountRid, this.getUser());
                webVo.setFilleByList(filledByList);
                String pm = accountLogic.getAccountManager(accountRid);
                webVo.setPrincipal(pm);
                webVo.setPrincipalScope(DtoUserBase.USER_TYPE_EMPLOYEE);

                /**
                 * 自动产生Issue Id
                 */
                if (issueType != null && !issueType.trim().equals("")) {
                    String newId = idLogic.getIssueId(issueType,
                        Long.valueOf(accountRid).longValue());
                    webVo.setIssueId(newId);
                }

                boolean hasFindAccount = false;
                for (int i = 0; i < list.size(); i++) {
                    SelectOptionImpl option = (SelectOptionImpl) list.get(i);
                    if (option.getValue().equals(account.getRid().toString())) {
                        hasFindAccount = true;
                        break;
                    }
                }
                if (!hasFindAccount) {
                    SelectOptionImpl option = new SelectOptionImpl(account.
                        getId() + "---" +
                        account.getName(),
                        account.getRid().toString(),account.
                        getId() + "---" +
                        account.getName());
                    list.add(option);
                }
            } else {
                String filledBy = this.getUser().getUserLoginId();
                webVo.setFilleBy(filledBy);

                List filledByList = new ArrayList();
                //confirmByList.add(new SelectOptionImpl("  ----  Please Select  ----  ",""));
                filledByList.add(new SelectOptionImpl(this.getUser().
                    getUserName(),
                    this.getUser().getUserLoginId()));
                webVo.setFilleByList(filledByList);
            }

            webVo.setAccountList(list);

            List issueTypeOptions = issueTypeLogic.getIssueTypeOptions();
            webVo.setIssueTypeList(issueTypeOptions);
            if (issueType != null && !issueType.trim().equals("")) {
                if (issueTypeLogic.isExistIssueType(issueType)) { //判断是否为IssueType
                    webVo.setIssueType(issueType);
                }
            }
            String sysDate = comDate.dateToString(new java.util.Date());
            webVo.setFilleDate(sysDate);
            if (!isForEssp) {
                webVo.setAccountReadonly("false");
            }
            return webVo;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }


    /**
     * 返回修改Issue页面显示所需内容
     * 1.新增页面所需内容
     * 2.对应Issue对象的属性
     * 3.依据userId是否为填写人或负责人，判断对Principal和Issue Status的修改权限
     *   填写人可修改两个栏位，负责人可修改Issue Status
     * @param userId String
     * @return VbIssueDetail
     */
    /**
     * 取到Hibernate的session对象，要取得Form中的rid；
     * 根据rid查询Issue的信息；
     * 取得下拉框中所有可以列出的account、issuetype等；
     * 把取得的数据放入ViewBean中
     * @param issueUpdatePreForm AfIssue
     * @return VbIssueDetail
     */
    public VbIssue updatePre(AfIssue issueUpdatePreForm) {
        try {
            LgIssueBase issueBaseLogic = new LgIssueBase();
            Session session = this.getDbAccessor().getSession();
            Long rid = new Long(issueUpdatePreForm.getRid());
            VbIssue webVo = new VbIssue();
            Issue issue = (Issue) session.load(Issue.class, rid);

            if (issue.getAttachmentId() != null) {
                LgDownload downloadLogic = new LgDownload();
                FileInfo fileInfo = new FileInfo();
                fileInfo.setId(issue.getAttachmentId());
                String accountRid = issue.getAccountId().toString();
                LgAccount accountLogic = new LgAccount();
                String accountCode = accountLogic.getAccountId(accountRid);
                fileInfo.setAccountcode(accountCode);
                fileInfo.setFilename(issue.getAttachment());
                webVo.setAttachment(downloadLogic.getDownloadUrl(fileInfo));
                webVo.setAttachmentdesc(issue.getAttachmentdesc());
            } else {
                webVo.setAttachment("");
                webVo.setAttachmentdesc("");
            }

            webVo.setRid(issue.getRid().toString());

            webVo.setIssueType(issue.getIssueType());
            Query q;
            ArrayList selectList = new ArrayList();
            q = session.createQuery("from IssueType s order by s.sequence");
            List result = q.list();
            SelectOptionImpl option = new SelectOptionImpl(
                "  ----  Please Select  ----  ", "");
            selectList.add(option);
            for (int i = 0; i < result.size(); i++) {
                IssueType issueType = (IssueType) result.get(i);
                option = new SelectOptionImpl(issueType.getTypeName(),
                                              issueType.getTypeName());
                selectList.add(option);
            }
            webVo.setIssueTypeList(selectList);

            webVo.setPriority(issue.getPriority());

            webVo.setFilleBy(issue.getFilleBy());
            webVo.setFilleByScope(issue.getFilleByScope());
            Date fillDate = issue.getFilleDate();
            webVo.setFilleDate(comDate.dateToString(fillDate));

            String accountRid = issue.getAccountId().toString();
            webVo.setAccountId(accountRid);
            List list = this.getAccountOptions();
            webVo.setAccountList(list);

            /**
             * filled by栏位下拉框，取出对应的account rid
             */
            LgFilledBy filledByLogic = new LgFilledBy();
            List filleByList = filledByLogic.getFilledByListOptions(accountRid,
                issue.getFilleBy());
            webVo.setFilleByList(filleByList);

            webVo.setPhone(issue.getPhone());
            webVo.setEmail(issue.getEmail());
            webVo.setFax(issue.getFax());
            webVo.setScope(issue.getScope());
            webVo.setIssueId(issue.getIssueId());

            webVo.setIssueName(issue.getIssueName());
            webVo.setDescription(issue.getDescription());
            //webVo.setAttachment(issue.getAttachment());
            //webVo.setAttachmentdesc(issue.getAttachmentdesc());
            webVo.setPrincipal(issue.getPrincipal());
            webVo.setPrincipalScope(issue.getPrincipalScope());
            webVo.setDueDate(comDate.dateToString(issue.getDueDate()));

            webVo.setIssueStatus(issue.getIssueStatus());

            //如果状态为关闭时才加载关闭的信息
            if (issue.getIssueStatus().equals("Closed")) {
                IssueConclusion issueCon = new IssueConclusion();
                LgIssueConclusion lgic = new LgIssueConclusion();
                issueCon = lgic.get(rid);
                webVo.setConfirmBy(issueCon.getConfirmBy());
                webVo.setConfirmByScope(issueCon.getConfirmByScope());
                Date d = issueCon.getConfirmDate();
                webVo.setConfirmDate(comDate.dateToString(d, "yyyy/MM/dd"));
                webVo.setInstructionOfClosure(issueCon.getInstructionClosure());
            } else { //其它状态预填默认信息
                webVo.setConfirmBy(issueUpdatePreForm.getConfirmBy());
                webVo.setConfirmByScope(issueUpdatePreForm.getConfirmByScope());
                webVo.setConfirmDate(issueUpdatePreForm.getConfirmDate());
            }

            LgIssueType issueTypeLogic = new LgIssueType();
            if (webVo.getIssueType() != null && !webVo.getIssueType().equals("")) {
                String issueType = webVo.getIssueType();
                String userType = this.getUser().getUserType();
                List priorityList = issueTypeLogic.getPriorityOptions(issueType,
                    issue.getPriority());
                List scopeList = issueTypeLogic.getScopeOptions(
                    issueType, userType, issue.getScope());
                List statusList = issueTypeLogic.getStatusOptions(issueType,
                    issue.getIssueStatus());
                webVo.setPriorityList(priorityList);
                webVo.setScopeList(scopeList);
                webVo.setStatusList(statusList);
            }

            if (issue.getDuplationIssue() != null) {
                webVo.setDuplationIssue(issue.getDuplationIssue().toString());
                List dbDuplationList = issueBaseLogic.getDuplationIssueOptions(
                    issue.getAccountId().toString(), rid.toString());
                webVo.setDuplationIssueList(dbDuplationList);
            }

            String issueType = issue.getIssueType();
            String currentStatus = issue.getIssueStatus();
            String belongTo = issueTypeLogic.getStatusBelongTo(issueType,
                currentStatus);
            if (belongTo != null && belongTo.equals(Status.DUPLATION)) {
                webVo.setDuplationIssueDisabled(false);
            } else {
                webVo.setDuplationIssueDisabled(true);
            }

            /**
             * 增加Status Belongto设置
             */
            String statusBelongto = issueTypeLogic.getStatusBelongTo(issueType,
                webVo.getIssueStatus());
            webVo.setIssueStatusBelongto(statusBelongto);

            initAuthority(webVo);
            return webVo;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException(ex);
        }

    }

    private void initAuthority(VbIssue viewBean) {
        String userName = this.getUser().getUserLoginId();
        if (userName.equals(viewBean.getPrincipal())) {
            viewBean.setPrincipalFlag("true");
        } else {
            viewBean.setPrincipalFlag("false");
        }

        String accountRid = viewBean.getAccountId();
        LgAccount logic = new LgAccount();
        String pm = logic.getAccountManager(accountRid);
        if (userName.equals(pm)) {
            viewBean.setPmFlag("true");
        } else {
            viewBean.setPmFlag("false");
        }

        String filleBy = viewBean.getFilleBy();
        if (userName.equals(filleBy)) {
            viewBean.setFilleByFlag("true");
        } else {
            viewBean.setFilleByFlag("false");
        }
    }

    /**
     * 根据传入的AfIssue新增Issue对象
     * 1.计算Issue的dueDate,计算方法：
     * 从LgPriority查找该Issue选择的Priority对应的duration(天),dueDate = fillDate + duration
     * 2.判断AfIssue.projId下是否已存在AfIssue.issueId的Issue对象，判断方法
     *     from Issue as issue where issue.projId = :projId and issue.issueId = :issueId
     *     if 存在该Issue
     *         if Issue可用 then 报错退出
     *         else Issue不可用，设置其状态可用，覆盖其他属性
     *     else then
     * 3.构造Issue对象，this.getDbAccessor().save()保存
     * 4.新增IssueReportStatus对象，设置rid,projId,issueType,issueStatus为Issue对应的属性，
     * processingDate为当前日期
     * @param param0 AfIssue
     * @throws BusinessException
     */
    /**
     * 取到Hibernate的session对象
     * 创建使用的数据库对象
     * 从Hibernate中取出Form
     * 把日期类型的进行转换
     * 保存到数据库
     * 注意到新增一条issue的时候，会在数据库中三个一对一表中自动的生成rid
     * 再把取出的rid保存到数据库中
     * 注意在Hibernate中，表conclusion与表resolution已经建立了关联
     * @param issueAddForm AfIssue
     * @throws BusinessException
     */
    public String add(server.essp.issue.issue.form.AfIssue issueAddForm,
                      String isMail) throws
        BusinessException {
        String fileUri = null;
        String accountid = null;
        String accountname = null;

        try {
            Session session = this.getDbAccessor().getSession();
            Issue issue = new Issue();
            String[] noCopyProperties = {"attachment"};
            DtoUtil.copyProperties(issue, issueAddForm, noCopyProperties);
            LgAccount accountLogic = new LgAccount();
            IDtoAccount account = accountLogic.getAccountByRid(issueAddForm.
                getAccountId());
            accountid = account.getId();
            accountname = account.getName();

            if (issueAddForm.getAttachment() != null &&
                issueAddForm.getAttachment().getFileName() != null &&
                issueAddForm.getAttachment().getFileSize() > 0) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setAccountcode(account.getId());
                LgUpload uploadLogic = new LgUpload();
                uploadLogic.initFileInfo(issueAddForm.getAttachment(), fileInfo);
                fileInfo.setFilename(issueAddForm.getAttachment().getFileName());
                uploadLogic.upload(issueAddForm.getAttachment(), fileInfo);

                issue.setAttachment(fileInfo.getFilename());
                issue.setAttachmentId(fileInfo.getId());
                issue.setAttachmentdesc(issueAddForm.getAttachmentdesc());

                // 获取fileInfo的路径
                fileUri = fileInfo.getServerFullPath(this.getServerRoot());
            }

            Date filleDate = comDate.toDate(issueAddForm.getFilleDate());
            Date dueDate = comDate.toDate(issueAddForm.getDueDate());
            this.getDbAccessor().save(issue);

            IssueConclusion conclusion = new IssueConclusion();
            conclusion.setRid(issue.getRid());
            //conclusion.setIssue(issue);

            IssueReportStatus reportStatus = new IssueReportStatus();
            reportStatus.setRid(issue.getRid());
            reportStatus.setAccountId(issue.getAccountId());
            reportStatus.setIssueStatus(issue.getIssueStatus());
            reportStatus.setIssueType(issue.getIssueType());

            IssueResolution resolution = new IssueResolution();
            resolution.setRid(issue.getRid());
            //resolution.setIssue(issue);

            this.getDbAccessor().save(conclusion);
            this.getDbAccessor().save(reportStatus);
            this.getDbAccessor().save(resolution);

            issue.setResolution(resolution);
            issue.setIssueConclusion(conclusion);

            LgIssueType lgIssueType = new LgIssueType();
            //if need to save
            if (lgIssueType.isSaveStatusHistory(issueAddForm.getIssueType())) {
                //记录状态
                String fStatus = "";
                String tStatus = issue.getIssueStatus();
                LgIssueStatusHistory lgIssueStatusHistory =
                    new LgIssueStatusHistory();
                lgIssueStatusHistory.addIssueStatusHistory(issue.getRid(),
                    fStatus, tStatus);
            }

            Issue iss = (Issue) session.load(Issue.class, issue.getRid());
            updateStatusRelationDate(iss, issueAddForm.getIssueStatus());


            //发送邮件
            if (isMail.equals("true")) {
                IssueConclusionGeneralMail icgm = new
                                                  IssueConclusionGeneralMail();
                icgm.setAccount(accountid + "--" + accountname);
                icgm.setIssue(issueAddForm.getIssueId() + "--" +
                              issueAddForm.getIssueName());
                icgm.setAttachmentdesc(issueAddForm.getAttachmentdesc());
                icgm.setDuedate(issueAddForm.getDueDate());
                icgm.setFillby(issueAddForm.getFilleBy());
                icgm.setFilldate(issueAddForm.getFilleDate());
                icgm.setIssueid(issue.getIssueId());
                icgm.setIssuetitle(issueAddForm.getIssueName());
                icgm.setPrincipal(issueAddForm.getPrincipal());
                icgm.setPrincipalScope(issueAddForm.getPrincipalScope());
                icgm.setPriority(issueAddForm.getPriority());
                icgm.setStatus(issueAddForm.getIssueStatus());
                if (issueAddForm.getAttachment() != null &&
                    issueAddForm.getAttachment().getFileSize() > 0) {
                    icgm.setAttachment(issueAddForm.getAttachment());
                }
                icgm.setIssuedescription(issueAddForm.getDescription());
                SendHastenMail shm = new SendHastenMail();
                shm.sendHastenMail(vmFile1, title1, getEmailDate(icgm, fileUri));
            }

            return issue.getRid().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException(ex);
        }

    }

    /**
     * 根据传入主键load() Issue对象
     * @param rid Long
     * @return Issue
     * @throws BusinessException
     */
    public Issue load(java.lang.Long issueId) throws BusinessException {
        Issue issue = null;
        try {
            Session session = this.getDbAccessor().getSession();
            issue = (Issue) session.get(Issue.class, issueId);
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.conclusionUg.exception",
                                        "Get issue conclusionUg error!");
        }
        return issue;

    }

    /**
     * 根据传入AfIssue更新Issue对象
     * 1.获得AfIssue对应的Issue对象
     * 2.比较AfIssue.issueStatus和Issue.issueStatus，若状态修改，除修改Issue.issueStatus外
     * 3.根据AfIssue.typeName和AfIssue.issueStatus到LgStatus获得issueStatus的BelongTo
     * 4.获得Issue对应的IssueReportStatus，根据BelongTo修改IssueReportStatus对应属性为当前日期
     * @param param0 AfIssue
     * @throws BusinessException
     */

    public VbIssue update(server.essp.issue.issue.form.AfIssue issueForm,
                          String isMail) throws
        BusinessException {
        String fileUri = null;
        String accountid = null;
        String accountname = null;
        String attachmentname = null;
        try {
            Session session = this.getDbAccessor().getSession();
            Long rid = Long.valueOf(issueForm.getRid());
            Issue issue = (Issue) session.load(Issue.class, rid);

            String fStatus = issue.getIssueStatus();
            String tStatus = issueForm.getIssueStatus();
            String[] noCopyProperties = {"attachment","filleByScope",
                                        "actualFilledBy","phone","fax","email"};
            DtoUtil.copyProperties(issue, issueForm, noCopyProperties);
            LgAccount accountLogic = new LgAccount();
            IDtoAccount account = accountLogic.getAccountByRid(issueForm.
                getAccountId());
            accountid = account.getId();
            accountname = account.getName();

            if (issue.getAttachment() != null &&
                issue.getAttachment().length() > 0) {
                String realpath = "/ISSUE/"+accountid+"/";
                String fileformat = issue.getAttachment().substring(
                    issue.getAttachment().indexOf("."),
                    issue.getAttachment().length());
                fileUri = this.getServerRoot() + realpath +
                          issue.getAttachmentId() + fileformat;
                attachmentname = (String) issue.getAttachment();
            }

            if (issueForm.getAttachment() != null &&
                issueForm.getAttachment().getFileName() != null &&
                issueForm.getAttachment().getFileSize() > 0) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setAccountcode(account.getId());
                fileInfo.setId(issue.getAttachmentId());
                LgUpload uploadLogic = new LgUpload();
                uploadLogic.initFileInfo(issueForm.getAttachment(), fileInfo);
                fileInfo.setFilename(issueForm.getAttachment().getFileName());
                uploadLogic.upload(issueForm.getAttachment(), fileInfo);

                issue.setAttachment(fileInfo.getFilename());
                issue.setAttachmentId(fileInfo.getId());

                // 获取fileInfo的路径
                fileUri = fileInfo.getServerFullPath(this.getServerRoot());
            }
            //当状态调整时，如果与状态关联的日期为空，则将当前系统日期赋入
            if(!fStatus.equalsIgnoreCase(tStatus)){
                updateStatusRelationDate(issue, tStatus);
            }

            Date filleDate = comDate.toDate(issueForm.getFilleDate());
            Date dueDate = comDate.toDate(issueForm.getDueDate());
            this.getDbAccessor().update(issue);
            LgIssueType lgIssueType = new LgIssueType();
            //if need to save
            if (lgIssueType.isSaveStatusHistory(issue.getIssueType())) {
                if (!fStatus.equalsIgnoreCase(tStatus)) {
                    LgIssueStatusHistory lgIssueStatusHistory =
                        new LgIssueStatusHistory();
                    lgIssueStatusHistory.addIssueStatusHistory(issue.getRid(),
                        fStatus, tStatus);
                }
            }

            //如果状态为关闭时才更新关闭所需的信息
            if (tStatus.equals("Closed")) {
                IssueConclusion issueCon = new IssueConclusion();
                LgIssueConclusion lgic = new LgIssueConclusion();
                issueCon = lgic.get(rid);

                String conDate = issueForm.getConfirmDate();
                String conBy = issueForm.getConfirmBy();
                String conByS = issueForm.getConfirmByScope();
                String insOfClosure = issueForm.getInstructionOfClosure();

                issueCon.setConfirmDate(comDate.toDate(conDate));
                issueCon.setConfirmBy(conBy);
                issueCon.setConfirmByScope(conByS);
                issueCon.setInstructionClosure(insOfClosure);
                this.getDbAccessor().save(issueCon);
            }

            //发送邮件
            if (isMail.equals("true")) {
                IssueConclusionGeneralMail icgm = new
                                                  IssueConclusionGeneralMail();
                icgm.setAccount(accountid + "--" + accountname);
                icgm.setIssue(issueForm.getIssueId() + "--" +
                              issueForm.getIssueName());
                icgm.setAttachmentdesc(issueForm.getAttachmentdesc());
                icgm.setDuedate(issueForm.getDueDate());
                icgm.setFillby(issueForm.getFilleBy());
                icgm.setFilldate(issueForm.getFilleDate());
                icgm.setIssueid(issueForm.getIssueId());
                icgm.setIssuetitle(issueForm.getIssueName());
                icgm.setPrincipal(issueForm.getPrincipal());
                icgm.setPrincipalScope(issueForm.getPrincipalScope());
                icgm.setPriority(issueForm.getPriority());
                icgm.setStatus(issueForm.getIssueStatus());
                if (issueForm.getAttachment() != null &&
                    issueForm.getAttachment().getFileSize() > 0) {
                    icgm.setAttachment(issueForm.getAttachment());
                }
                icgm.setIssuedescription(issueForm.getDescription());
                SendHastenMail shm = new SendHastenMail();
                if (issueForm.getIssueStatus().equals("Closed")) {

                  if (fileUri == null || fileUri.length() == 0) {
                       shm.sendHastenMail(vmFile2, title2,
                                          getEmailDate(icgm, "", ""));
                   } else {
                       shm.sendHastenMail(vmFile2, title2,
                                          getEmailDate(icgm, fileUri,
                                                       attachmentname));
                   }

                } else {
                    if (fileUri == null || fileUri.length() == 0) {
                        shm.sendHastenMail(vmFile1, title1,
                                           getEmailDate(icgm, "", ""));
                    } else {
                        shm.sendHastenMail(vmFile1, title1,
                                           getEmailDate(icgm, fileUri,
                                                        attachmentname));
                    }

                }
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return null;
    }

    private void updateStatusRelationDate(Issue issue,
                                          String tStatus) throws
        Exception {
        Session session = this.getDbAccessor().getSession();
        LgIssueType lgIssueType = new LgIssueType();
        IssueStatus issueStatus = lgIssueType.getIssueStatus(issue.getIssueType(),tStatus);
        String relationDate = issueStatus.getRelationDate();
        //更新AssignDate
        if(RelationDate.ASSIGNED_DATE.equals(relationDate)){
            IssueResolution resolution = issue.getResolution();
            if(resolution == null){
                resolution = new IssueResolution();
                resolution.setRid(issue.getRid());
                resolution.setAssignedDate(new Date());
                session.save(resolution);
            }else if(resolution.getAssignedDate() == null){
                resolution.setAssignedDate(new Date());
                session.update(resolution);
            }
        }else{
            IssueConclusion conclusion = issue.getIssueConclusion();
            if (conclusion == null) {
                    conclusion = new IssueConclusion();
                    conclusion.setRid(issue.getRid());
            }
            //更新FinishedDate
            if(RelationDate.FINISHED_DATE.equals(relationDate) &&
                conclusion.getFinishedDate() == null){
                conclusion.setFinishedDate(new Date());
            }//更新DeliveredDate
            else if(RelationDate.DELIVERED_DATE.equals(relationDate) &&
                conclusion.getDeliveredDate() == null){
                conclusion.setDeliveredDate(new Date());
            }//更新ConfirmedDate
            else if(RelationDate.CONFIRM_DATE.equals(relationDate) &&
                conclusion.getConfirmDate() == null){
                conclusion.setConfirmDate(new Date());
            }else{
                log.error("illegal relation date:"+relationDate);
            }
             session.saveOrUpdate(conclusion);
        }
    }

    /**
     * 查找该主键对应Issue对象，设置其status不可用
     * 物理删除Issue对应的IssueResolution，IssueDiscussTitle，IssueConclusion和IssueReportStatus
     * 分别调用LgIssueResolution，
     * @param rid Long
     * @throws BusinessException
     */
    public void delete(AfIssue issueForm) throws BusinessException {
        try {
            Session session = this.getDbAccessor().getSession();
            Long rid = new Long(issueForm.getRid());

            Issue issue = (Issue) session.load(Issue.class, rid);
            /**
             * 1.找到Issue Status
             */
            String typeName = issue.getIssueType();
            String status = issue.getIssueStatus();
            if (status != null) {
                /**
                 * 2.根据Issue Status找到 IssueStatus Hibernate对象
                 */
                IssueStatusPK statusPK = new IssueStatusPK(typeName, status);
                IssueStatus issueStatus = (IssueStatus) session.load(
                    IssueStatus.class, statusPK);

                /**
                 * 3.从IssueStatus中取出belongto，检查belong是否等于"Received"
                 */
                String belongto = issueStatus.getBelongTo();
                if (belongto != null && belongto.equals(Status.RECEIVED)) {
                    session.delete(issue);

                    /**
                     * 4.删除相关记录
                     */
                    /**
                     * 4.1删除Resolution相关数据
                     */
                    //删除issue_resolution_category表中的记录
                    this.getDbAccessor().executeUpdate(
                        "delete from issue_resolution_category where issue_rid=" +
                        issueForm.getRid());
                    //删除issue_resolution_influe表中的记录
                    this.getDbAccessor().executeUpdate(
                        "delete from issue_resolution_influe where issue_rid=" +
                        issueForm.getRid());
                    //删除issue_resolution主记录
                    this.getDbAccessor().executeUpdate(
                        "delete from issue_resolution where rid=" +
                        issueForm.getRid());

                    /**
                     * 4.2删除Discuss相关数据
                     */
                    //删除issue_discuss_reply表中的记录
                    this.getDbAccessor().executeUpdate(
                        "delete from issue_discuss_reply where title_id in " +
                        "(select rid from issue_discuss_title where issue_rid=" +
                        issueForm.getRid() + ")");
                    //删除issue_discuss_title表中的记录
                    this.getDbAccessor().executeUpdate(
                        "delete from issue_discuss_title where issue_rid=" +
                        issueForm.getRid());

                    /**
                     * 4.2删除Conclusion相关数据
                     */
                    //删除issue_conclusion_ug表中的记录
                    this.getDbAccessor().executeUpdate(
                        "delete from issue_conclusion_ug where issue_rid=" +
                        issueForm.getRid());
                    //删除issue_conclusion表中的记录
                    this.getDbAccessor().executeUpdate(
                        "delete from issue_conclusion where rid=" +
                        issueForm.getRid());
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }

//           Session session = this.getDbAccessor().getSession();
//           Long rid = new Long(issueUpdatePreForm.getRid());
//           VbIssue webVo = new VbIssue();
//           Issue issue = (Issue) session.load(Issue.class, rid);
//           webVo.setRid(issue.getRid().toString());

    }

    /**
     * 根据传入的IssueConclusionUrgeMail对象,获取邮件信息
     * @param mailbean IssueConclusionUrgeMail
     * @param Uri String
     * @param attachmentname String
     * @return HashMap
     */

    public HashMap getEmailDate(IssueConclusionGeneralMail icgm, String Uri,
                                String attachmentname) {

        HashMap hm = new HashMap();
        LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
        String sendEmail = "", user, toEmail = "";
        user = icgm.getPrincipal();
        try {

            ArrayList al = new ArrayList();
            ContentBean cb = new ContentBean();

            al.add(icgm);
            cb.setUser(user);
            String mail[] = user.split(",");

            for (int i = 0; i < mail.length; i++) {
                if (sendEmail.length() > 0) {
                    sendEmail = sendEmail + "," + ihui.getUserEmail(mail[i]);
                } else {
                    sendEmail = ihui.getUserEmail(mail[i]);
                }
            }

            String cc[] = icgm.getFillby().split(",");
            for (int i = 0; i < cc.length; i++) {
                if (toEmail.length() > 0) {
                    toEmail = toEmail + "," + ihui.getUserEmail(cc[i]);
                } else {
                    toEmail = ihui.getUserEmail(cc[i]);
                }
            }

            cb.setEmail(sendEmail);
            cb.setMailcontent(al);

            if (Uri != null && Uri.length() > 0) {

                FileDataSource fds;
                fds = new FileDataSource(Uri);
                File f = fds.getFile();
                HashMap atts = new HashMap();

                if (icgm.getAttachment() != null &&
                    icgm.getAttachment().getFileSize() > 0) {
                    atts.put(icgm.getAttachment().getFileName(), f);
                } else {
                    atts.put(attachmentname, f);
                }
                cb.setAttachments(atts);
            }
            cb.setCcAddress(toEmail);
            hm.put(user, cb);

        } catch (Throwable tx) {
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
     * @return HashMap
     */

    public HashMap getEmailDate(IssueConclusionGeneralMail icgm, String Uri) {

        HashMap hm = new HashMap();
        LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
        String sendEmail = "", user, toEmail = "";
        user = icgm.getPrincipal();
        try {

            ArrayList al = new ArrayList();
            ContentBean cb = new ContentBean();

            al.add(icgm);
            cb.setUser(user);
            String mail[] = user.split(",");

            for (int i = 0; i < mail.length; i++) {
                if (sendEmail.length() > 0) {
                    sendEmail = sendEmail + "," + ihui.getUserEmail(mail[i]);
                } else {
                    sendEmail = ihui.getUserEmail(mail[i]);
                }
            }

            String cc[] = icgm.getFillby().split(",");
            for (int i = 0; i < cc.length; i++) {
                if (toEmail.length() > 0) {
                    toEmail = toEmail + "," + ihui.getUserEmail(cc[i]);
                } else {
                    toEmail = ihui.getUserEmail(cc[i]);
                }
            }

            cb.setEmail(sendEmail);
            cb.setMailcontent(al);

            if (icgm.getAttachment() != null &&
                icgm.getAttachment().getFileSize() > 0) {

                FileDataSource fds;
                fds = new FileDataSource(Uri);
                File f = fds.getFile();
                HashMap atts = new HashMap();
                atts.put(icgm.getAttachment().getFileName(), f);
                cb.setAttachments(atts);
            }
            cb.setCcAddress(toEmail);
            hm.put(user, cb);

        } catch (Throwable tx) {
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
    public String getServerRoot() {
        String root = null;
        try {
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

        } catch (Throwable tx) {
            String msg = "error get all ServerRootPath!";
            log.error(msg, tx);
            throw new BusinessException("", msg, tx);

        }
        return root + "/../attachmentFiles";
    }

    public String addIssue(IDtoIssue dtoIssue, boolean isMail) {

//       AfIssue issueAddForm = new AfIssue();
       try {
           Session session = this.getDbAccessor().getSession();
           Issue issue = new Issue();
           issue.setAccountId(Long.valueOf(dtoIssue.getAccountId()));
           issue.setIssueType(dtoIssue.getIssueType());
           issue.setScope(dtoIssue.getScope());
           issue.setPriority(dtoIssue.getPriority());
           issue.setIssueId(dtoIssue.getIssueId());
           issue.setIssueName(dtoIssue.getIssueName());
           issue.setDescription(dtoIssue.getDescription());
           issue.setFilleDate(comDate.toDate(dtoIssue.getFilleDate()));
           issue.setFilleBy(dtoIssue.getFilleBy());
           issue.setFilleByScope(DtoUserBase.USER_TYPE_EMPLOYEE);
           issue.setPrincipal(dtoIssue.getPrincipal());
           issue.setPrincipalScope(DtoUserBase.USER_TYPE_EMPLOYEE);
           issue.setDueDate(comDate.toDate(dtoIssue.getDueDate()));
           issue.setIssueStatus(dtoIssue.getIssueStatus());

//           LgAccount accountLogic = new LgAccount();
//           IDtoAccount account = accountLogic.getAccountByRid(dtoIssue.
//               getAccountId());
//           accountid = account.getId();
//           accountname = account.getName();
//
//
//
//           Date filleDate = comDate.toDate(dtoIssue.getFilleDate());
//           Date dueDate = comDate.toDate(dtoIssue.getDueDate());
           this.getDbAccessor().save(issue);

           IssueConclusion conclusion = new IssueConclusion();
           conclusion.setRid(issue.getRid());
           if(issue.getIssueStatus().equals("Closed")){
               conclusion.setConfirmBy(dtoIssue.getConfirmBy());
               conclusion.setConfirmByScope(dtoIssue.getConfirmByScope());
               conclusion.setConfirmDate(comDate.toDate(dtoIssue.getCloseDate()));
           }
           //conclusion.setIssue(issue);

           IssueReportStatus reportStatus = new IssueReportStatus();
           reportStatus.setRid(issue.getRid());
           reportStatus.setAccountId(issue.getAccountId());
           reportStatus.setIssueStatus(issue.getIssueStatus());
           reportStatus.setIssueType(issue.getIssueType());

           IssueResolution resolution = new IssueResolution();
           resolution.setRid(issue.getRid());
           //resolution.setIssue(issue);

           this.getDbAccessor().save(conclusion);
           this.getDbAccessor().save(reportStatus);
           this.getDbAccessor().save(resolution);

           issue.setResolution(resolution);
           issue.setIssueConclusion(conclusion);

           LgIssueType lgIssueType = new LgIssueType();
           //if need to save
           if (lgIssueType.isSaveStatusHistory(dtoIssue.getIssueType())) {
               //记录状态
               String fStatus = "";
               String tStatus = issue.getIssueStatus();
               LgIssueStatusHistory lgIssueStatusHistory =
                   new LgIssueStatusHistory();
               lgIssueStatusHistory.addIssueStatusHistory(issue.getRid(),
                   fStatus, tStatus);
           }

           Issue iss = (Issue) session.load(Issue.class, issue.getRid());
           updateStatusRelationDate(iss, dtoIssue.getIssueStatus());

           //发送邮件
            if (isMail) {
                IssueConclusionGeneralMail icgm = new
                                                  IssueConclusionGeneralMail();
                LgAccount accountLogic = new LgAccount();
                IDtoAccount account = accountLogic.getAccountByRid(issue.getAccountId().toString());

                icgm.setAccount(account.getId() + "--" + account.getName());
                icgm.setIssue(iss.getIssueId() + "--" +
                              iss.getIssueName());
                icgm.setAttachmentdesc(iss.getAttachmentdesc());
                icgm.setDuedate(comDate.dateToString(iss.getDueDate(), "yyyy/MM/dd"));
                icgm.setFillby(iss.getFilleBy());
                icgm.setFilldate(comDate.dateToString(iss.getFilleDate(), "yyyy/MM/dd"));
                icgm.setIssueid(issue.getIssueId());
                icgm.setIssuetitle(iss.getIssueName());
                icgm.setPrincipal(iss.getPrincipal());
                icgm.setPrincipalScope(iss.getPrincipalScope());
                icgm.setPriority(iss.getPriority());
                icgm.setStatus(iss.getIssueStatus());
                icgm.setIssuedescription(iss.getDescription());
                SendHastenMail shm = new SendHastenMail();
                shm.sendHastenMail(vmFile1, title1, getEmailDate(icgm, ""));
            }


           return issue.getIssueId();
       } catch (Exception ex) {
           ex.printStackTrace();
           throw new BusinessException(ex);
       }
    }

    public String updateIssue(IDtoIssue dtoIssue, boolean isMail) {
      try {
          Session session = this.getDbAccessor().getSession();
          Long rid = Long.valueOf(dtoIssue.getRid());
          Issue issue = (Issue) session.load(Issue.class, rid);
          String fStatus = issue.getIssueStatus();
          String tStatus = dtoIssue.getIssueStatus();

          issue.setScope(dtoIssue.getScope());
          issue.setPriority(dtoIssue.getPriority());
          issue.setIssueName(dtoIssue.getIssueName());
          issue.setDescription(dtoIssue.getDescription());
          issue.setPrincipal(dtoIssue.getPrincipal());
          issue.setPrincipalScope(dtoIssue.getPrincipalScope());
          issue.setDueDate(comDate.toDate(dtoIssue.getDueDate()));
          issue.setIssueStatus(dtoIssue.getIssueStatus());

          this.getDbAccessor().update(issue);
          LgIssueType lgIssueType = new LgIssueType();
          //if need to save
          if (lgIssueType.isSaveStatusHistory(issue.getIssueType())) {
              if (!fStatus.equalsIgnoreCase(tStatus)) {
                  LgIssueStatusHistory lgIssueStatusHistory =
                      new LgIssueStatusHistory();
                  lgIssueStatusHistory.addIssueStatusHistory(issue.getRid(),
                      fStatus, tStatus);
              }
          }

          //如果状态为关闭时才更新关闭所需的信息
          if (tStatus.equals("Closed")) {
              IssueConclusion issueCon = new IssueConclusion();
              LgIssueConclusion lgic = new LgIssueConclusion();
              issueCon = lgic.get(issue.getRid());

              String conDate = dtoIssue.getCloseDate();
              String conBy = dtoIssue.getConfirmBy();
              String conByS = dtoIssue.getConfirmByScope();
//              String insOfClosure = issueForm.getInstructionOfClosure();

              issueCon.setConfirmDate(comDate.toDate(conDate));
              issueCon.setConfirmBy(conBy);
              issueCon.setConfirmByScope(conByS);
//              issueCon.setInstructionClosure(insOfClosure);
              this.getDbAccessor().save(issueCon);
          }

          //发送邮件
            if (isMail) {
                IssueConclusionGeneralMail icgm = new
                                                  IssueConclusionGeneralMail();
                LgAccount accountLogic = new LgAccount();
                IDtoAccount account = accountLogic.getAccountByRid(issue.getAccountId().toString());
                icgm.setAccount(account.getId() + "--" + account.getName());
                icgm.setIssue(issue.getIssueId() + "--" +
                              issue.getIssueName());
                icgm.setAttachmentdesc(issue.getAttachmentdesc());
                icgm.setDuedate(comDate.dateToString(issue.getDueDate(), "yyyy/MM/dd"));
                icgm.setFillby(issue.getFilleBy());
                icgm.setFilldate(comDate.dateToString(issue.getFilleDate(), "yyyy/MM/dd"));
                icgm.setIssueid(issue.getIssueId());
                icgm.setIssuetitle(issue.getIssueName());
                icgm.setPrincipal(issue.getPrincipal());
                icgm.setPrincipalScope(issue.getPrincipalScope());
                icgm.setPriority(issue.getPriority());
                icgm.setStatus(issue.getIssueStatus());
                icgm.setIssuedescription(issue.getDescription());
                SendHastenMail shm = new SendHastenMail();
                if (issue.getIssueStatus().equals("Closed")) {
                    shm.sendHastenMail(vmFile2, title2,
                                       getEmailDate(icgm, "", ""));
                } else {
                    shm.sendHastenMail(vmFile1, title1,
                                       getEmailDate(icgm, "", ""));
                }
            }


          return issue.getIssueId();
      } catch (Exception ex) {
          throw new BusinessException(ex);
      }


    }

    public IDtoIssue getIssue(String issueId) {
        try {
           LgIssueBase issueBaseLogic = new LgIssueBase();
           Session session = this.getDbAccessor().getSession();

           IDtoIssue dtoIssue = new DtoIssue();
           Issue issue = (Issue) session.createQuery("from Issue t where t.issueId='"+issueId+"'")
                         .setMaxResults(1)
                         .uniqueResult();
    if(issue!=null){
           dtoIssue.setRid(String.valueOf(issue.getRid()));
           dtoIssue.setIssueType(issue.getIssueType());
           dtoIssue.setPriority(issue.getPriority());
           dtoIssue.setFilleBy(issue.getFilleBy());
           Date fillDate = issue.getFilleDate();
           dtoIssue.setFilleDate(comDate.dateToString(fillDate, "yyyy/MM/dd"));
           dtoIssue.setScope(issue.getScope());
           dtoIssue.setIssueId(issue.getIssueId());
           dtoIssue.setIssueName(issue.getIssueName());
           dtoIssue.setDescription(issue.getDescription());
           dtoIssue.setPrincipal(issue.getPrincipal());
           dtoIssue.setPrincipalScope(issue.getPrincipalScope());
           dtoIssue.setDueDate(comDate.dateToString(issue.getDueDate(), "yyyy/MM/dd"));
           dtoIssue.setIssueStatus(issue.getIssueStatus());


           //如果状态为关闭时才加载关闭的信息
           if (issue.getIssueStatus().equals("Closed")) {
               IssueConclusion issueCon = new IssueConclusion();
               LgIssueConclusion lgic = new LgIssueConclusion();
               issueCon = lgic.get(issue.getRid());
               dtoIssue.setConfirmBy(issueCon.getConfirmBy());
               dtoIssue.setConfirmByScope(issueCon.getConfirmByScope());
               Date d = issueCon.getConfirmDate();
               dtoIssue.setCloseDate(comDate.dateToString(d, "yyyy/MM/dd"));
           } else { //其它状态预填默认信息
           }
    }
           return dtoIssue;

       } catch (Exception ex) {
           ex.printStackTrace();
           throw new BusinessException(ex);
       }
    }

    public String generateIssueId(String issueType, Long acntRid,
                                  IDtoAccount account) {
        return "";
    }


}
