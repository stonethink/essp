package server.essp.issue.issue.resolution.logic;

import server.essp.issue.issue.resolution.viewbean.VbIssueResolution;
import server.framework.common.BusinessException;
import java.lang.Long;
import db.essp.issue.IssueResolution;
import net.sf.hibernate.Session;
import c2s.dto.DtoUtil;
import db.essp.issue.IssueResolutionInflue;
import server.essp.issue.issue.resolution.viewbean.VbInfluence;
import db.essp.issue.IssueRisk;
import db.essp.issue.Issue;
import server.framework.taglib.util.SelectOptionImpl;
import net.sf.hibernate.Query;
import java.util.List;
import db.essp.issue.IssueCategoryType;
import server.essp.issue.issue.resolution.viewbean.VbCategory;
import db.essp.issue.IssueCategoryValue;
import db.essp.issue.IssueResolutionCategory;
import server.essp.issue.issue.resolution.form.AfIssueResolution;
import server.essp.issue.common.logic.LgAccount;
import c2s.dto.FileInfo;
import server.essp.issue.common.logic.LgUpload;
import java.util.Date;
import com.wits.util.comDate;
import server.essp.issue.common.logic.LgDownload;
import server.essp.issue.common.logic.AbstractISSUELogic;
import server.essp.issue.common.logic.LgIssueType;
import server.essp.issue.common.constant.RelationDate;
import server.framework.common.Constant;
import db.essp.issue.IssueReportStatus;
import c2s.essp.common.user.DtoUser;
import server.essp.issue.common.logic.LgFilledBy;
import server.framework.taglib.util.TagUtils;
import server.essp.issue.issue.logic.LgIssueRiskHistory;
import server.essp.issue.issue.viewbean.VbIssueRiskHistory;
import server.essp.issue.common.constant.Delimiter;
import server.essp.issue.issue.form.AfIssue;
import c2s.essp.common.account.IDtoAccount;
import server.essp.issue.issue.resolution.form.AfIssueGeneralResolution;
import server.essp.common.mail.SendHastenMail;
import itf.hr.HrFactory;
import javax.activation.FileDataSource;
import java.util.ArrayList;
import java.util.HashMap;
import server.essp.common.mail.ContentBean;
import java.io.File;
import itf.hr.LgHrUtilImpl;
import java.text.DateFormat;
import db.essp.issue.IssueStatus;
import server.essp.issue.issue.logic.LgIssueStatusHistory;


public class LgIssueResolution extends AbstractISSUELogic {
    LgIssueType issueTypeLogic = new LgIssueType();
    /**
     * 根据主键获得IssueResolution对象
     * @param rid Long
     * @throws BusinessException
     */
    public IssueResolution get(Long rid) throws BusinessException {
        IssueResolution resolution=null;
            try {
                //取得Hibernate Session
                Session session = this.getDbAccessor().getSession();
                //根据issue rid取得Issue Resolution记录
                resolution = (IssueResolution) session.get(
                    IssueResolution.class, rid);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return resolution;
    }

    /**
     * 返回Resolution Issue页面显示所需内容:
     * 1.Issue对应的IssueType的RiskInfluence和Category，
     * 2.IssueResolution默认的Plan Finish Date即Issue.dueDate
     * 3.根据userId是否为负责人判断页面内容是否可修改,若userId非负责人，不能修改
     * 4.查找主键对应IssueResolution对象，若存在显示其属性
     * @param rid Long
     * @return VbIssueResolution
     * @throws BusinessException
     */
    public static final String vmFile1 = "mail/template/issue/GeneralResolutionMailTempl.html";
    public static final String title = "Please resolve the Issue!";

    public VbIssueResolution resolutionPrepare(String rid) throws
        BusinessException {
        Long issueRid = Long.valueOf(rid);
        try {
            LgAccount accountLogic = new LgAccount();
            //创建View bean
            VbIssueResolution viewBean = new VbIssueResolution();
            //取得Hibernate Session
            Session session = this.getDbAccessor().getSession();
            //根据issue rid取得Issue Resolution记录
            IssueResolution resolution = (IssueResolution) session.get(
                IssueResolution.class, issueRid);
            //如果能找到Issue Resolution记录
            if (resolution != null) {
                DtoUtil.copyProperties(viewBean, resolution);
                if (viewBean.getProbability() == null) {
                    viewBean.setProbability("100");
                }
                Issue issue = resolution.getIssue();
                viewBean.setAccountId(issue.getAccountId().toString());
                if (viewBean.getResolutionBy() == null ||
                    viewBean.getResolutionBy().trim().equals("")) {
                    viewBean.setResolutionBy(issue.getPrincipal());
                }

                String typeName = issue.getIssueType();

                /**
                 * 在这里加入下载代码
                 */
                if (resolution.getAttachmentId() != null) {
                    LgDownload downloadLogic = new LgDownload();
                    FileInfo fileInfo = new FileInfo();

                    String accountRid = issue.getAccountId().toString();
                    String accountCode = accountLogic.getAccountId(accountRid);
                    fileInfo.setAccountcode(accountCode);
                    fileInfo.setId(resolution.getAttachmentId());
                    fileInfo.setFilename(resolution.getAttachment());
                    viewBean.setAttachment(downloadLogic.getDownloadUrl(
                        fileInfo));
                    viewBean.setAttachmentDesc(resolution.getAttachmentdesc());
                }

                Query q = session.createQuery(
                    "from IssueRisk s where s.comp_id.typeName='" + typeName +
                    "' and s.rst='" + Constant.RST_NORMAL +
                    "' order by s.sequence");
                List dbIssueRiskList = q.list();
                if (dbIssueRiskList != null && dbIssueRiskList.size() > 0) {
                    for (int i = 0; i < dbIssueRiskList.size(); i++) {
                        IssueRisk issueRisk = (IssueRisk) dbIssueRiskList.get(i);
                        String influenceName = issueRisk.getComp_id().
                                               getInfluence();
                        long maxLevel = issueRisk.getMaxLevel().longValue();
                        long minLevel = issueRisk.getMinLevel().longValue();
                        //wenjun.yang
                        String description = issueRisk.getDescription();
                        if(description==null){
                            description = "";
                        }
                        Query q2 = session.createQuery(
                            "from IssueResolutionInflue f where f.issueResolution.rid=" +
                            rid + " and f.influenceName='" + influenceName +
                            "' and f.rst='" + Constant.RST_NORMAL + "'");
                        List dbIssueResolutionInflueList = q2.list();
                        if (dbIssueResolutionInflueList != null &&
                            dbIssueResolutionInflueList.size() > 0) {
                            IssueResolutionInflue dbIssueResolutionInflue = (
                                IssueResolutionInflue)
                                dbIssueResolutionInflueList.get(0);
                            String impactLevel = dbIssueResolutionInflue.
                                                 getImpactLevel().toString();
                            String weight = dbIssueResolutionInflue.getWeight().
                                            toString();
                            if (weight == null || weight.trim().equals("")) {
                                weight = "1";
                            }
                            String remark = dbIssueResolutionInflue.getRemark();
                            if (remark == null) {
                                remark = "";
                            }
                            VbInfluence vbInfluence = new VbInfluence();
                            vbInfluence.setInfluence(influenceName);
                            vbInfluence.setImpactLevel(impactLevel);
                            vbInfluence.setWeight(weight);
                            vbInfluence.setRemark(remark);
                            for (long j = minLevel; j <= maxLevel; j++) {
                                SelectOptionImpl option = new SelectOptionImpl(
                                    j +
                                    "", j + "", description+"");
                                vbInfluence.getImpactLevelOptions().add(option);
                            }

                            viewBean.getInfluences().add(vbInfluence);
                        } else {
                            VbInfluence vbInfluence = new VbInfluence();
                            vbInfluence.setInfluence(influenceName);
                            vbInfluence.setImpactLevel("");
                            vbInfluence.setWeight(issueRisk.getWeight().
                                                  toString());
                            vbInfluence.setRemark("");
                            for (long j = minLevel; j <= maxLevel; j++) {
                                SelectOptionImpl option = new SelectOptionImpl(
                                    j +
                                    "", j + "", description+"");
                                vbInfluence.getImpactLevelOptions().add(option);
                            }
                            viewBean.getInfluences().add(vbInfluence);
                        }
                    }
                }

                Query q1 = session.createQuery(
                    "from IssueCategoryType c where c.comp_id.typeName='" +
                    typeName +
                    "' and c.rst='" + Constant.RST_NORMAL +
                    "' order by c.sequence");
                List dbIssueCategoryList = q1.list();
                if (dbIssueCategoryList != null &&
                    dbIssueCategoryList.size() > 0) {
                    for (int i = 0; i < dbIssueCategoryList.size(); i++) {
                        IssueCategoryType issueCategory = (IssueCategoryType)
                            dbIssueCategoryList.get(i);
                        String categoryName = issueCategory.getComp_id().
                                              getCategoryName();
                        String description=issueCategory.getDescription();
                        VbCategory vbCategory = new VbCategory();
                        vbCategory.setCategory(categoryName);
                        //用来tip显示
                        vbCategory.setDescription(description);

                        Query q11 = session.createQuery(
                            "from IssueCategoryValue c where c.comp_id.typeName='" +
                            typeName + "' and c.comp_id.categoryName='" +
                            categoryName +
                            "' and c.rst='" + Constant.RST_NORMAL +
                            "' order by c.sequence");
                        List dbIssueCategoryValueList = q11.list();
                        if (dbIssueCategoryValueList != null &&
                            dbIssueCategoryValueList.size() > 0) {
                            for (int j = 0; j < dbIssueCategoryValueList.size();
                                         j++) {
                                IssueCategoryValue issueCategoryValue = (
                                    IssueCategoryValue)
                                    dbIssueCategoryValueList.get(j);
                                String value = issueCategoryValue.getComp_id().
                                               getCategoryValue();
                                String desc = issueCategoryValue.getDescription();
                                SelectOptionImpl option = new SelectOptionImpl(
                                    value, value,desc);
                                vbCategory.getValueOptions().add(option);
                            }
                        }

                        Query q12 = session.createQuery(
                            "from IssueResolutionCategory c where c.issueResolution.rid=" +
                            rid + " and c.categoryName='" +
                            categoryName + "' and c.rst='" +
                            Constant.RST_NORMAL + "' ");
                        List dbIssueResolutionCategoryList = q12.list();
                        if (dbIssueResolutionCategoryList != null &&
                            dbIssueResolutionCategoryList.size() > 0) {
                            IssueResolutionCategory issueResolutionCategory = (
                                IssueResolutionCategory)
                                dbIssueResolutionCategoryList.get(0);
                            String currentCategoryValue =
                                issueResolutionCategory.getCategoryValue();
                            vbCategory.setValue(currentCategoryValue);
                        } else {
                            vbCategory.setValue("");
                        }
                        viewBean.getCategories().add(vbCategory);
                    }
                }
            } else {
                viewBean.setProbability("100");
                Issue issue = (Issue) session.get(Issue.class, issueRid);
                viewBean.setAccountId(issue.getAccountId().toString());
                viewBean.setResolutionBy(issue.getPrincipal());
                String typeName = issue.getIssueType();

                Query q = session.createQuery(
                    "from IssueRisk s where s.comp_id.typeName='" + typeName +
                    "' and s.rst='" + Constant.RST_NORMAL +
                    "' order by s.sequence");
                List dbIssueRiskList = q.list();
                if (dbIssueRiskList != null && dbIssueRiskList.size() > 0) {
                    for (int i = 0; i < dbIssueRiskList.size(); i++) {
                        IssueRisk issueRisk = (IssueRisk) dbIssueRiskList.get(i);
                        String influenceName = issueRisk.getComp_id().
                                               getInfluence();
                        long maxLevel = issueRisk.getMaxLevel().longValue();
                        long minLevel = issueRisk.getMinLevel().longValue();
                        //wenjun.yang
                        String description = issueRisk.getDescription();
                        if(description==null){
                            description = "";
                        }

                        VbInfluence vbInfluence = new VbInfluence();
                        vbInfluence.setInfluence(influenceName);
                        vbInfluence.setImpactLevel("");
                        vbInfluence.setWeight(issueRisk.getWeight().toString());
                        vbInfluence.setRemark("");
                        for (long j = minLevel; j <= maxLevel; j++) {
                            SelectOptionImpl option = new SelectOptionImpl(
                                j +
                                "", j + "", description+"");
                            vbInfluence.getImpactLevelOptions().add(option);
                        }
                        viewBean.getInfluences().add(vbInfluence);
                    }
                }

                Query q1 = session.createQuery(
                    "from IssueCategoryType c where c.comp_id.typeName='" +
                    typeName +
                    "' and c.rst='" + Constant.RST_NORMAL +
                    "' order by c.sequence");
                List dbIssueCategoryList = q1.list();
                if (dbIssueCategoryList != null &&
                    dbIssueCategoryList.size() > 0) {
                    for (int i = 0; i < dbIssueCategoryList.size(); i++) {
                        IssueCategoryType issueCategory = (IssueCategoryType)
                            dbIssueCategoryList.get(i);
                        String categoryName = issueCategory.getComp_id().
                                              getCategoryName();
                        String description=issueCategory.getDescription();
                        VbCategory vbCategory = new VbCategory();
                        vbCategory.setCategory(categoryName);
                        vbCategory.setDescription(description);
                        vbCategory.setValue("");

                        Query q11 = session.createQuery(
                            "from IssueCategoryValue c where c.comp_id.typeName='" +
                            typeName + "' and c.comp_id.categoryName='" +
                            categoryName +
                            "' and c.rst='" + Constant.RST_NORMAL +
                            "' order by c.sequence");
                        List dbIssueCategoryValueList = q11.list();
                        if (dbIssueCategoryValueList != null &&
                            dbIssueCategoryValueList.size() > 0) {
                            for (int j = 0; j < dbIssueCategoryValueList.size();
                                         j++) {
                                IssueCategoryValue issueCategoryValue = (
                                    IssueCategoryValue)
                                    dbIssueCategoryValueList.get(j);
                                String value = issueCategoryValue.getComp_id().
                                               getCategoryValue();
                                String desc = issueCategoryValue.getDescription();
                                SelectOptionImpl option = new SelectOptionImpl(
                                    value, value,desc);
                                vbCategory.getValueOptions().add(option);
                            }
                        }

                        viewBean.getCategories().add(vbCategory);
                    }
                }

            }

            /**
             *  在这里加入Plan Finish Date的赋值(从Issue中取出Due Date)
             */
            Issue issue = (Issue) session.get(Issue.class, issueRid);
            Date dueDate = issue.getDueDate();
            if (dueDate != null) {
                String strDueDate = comDate.dateToString(dueDate, "yyyyMMdd");
                if (viewBean.getPlanFinishDate() == null ||
                    viewBean.getPlanFinishDate().trim().equals("")) {
                    viewBean.setPlanFinishDate(strDueDate);
                }
            }

            /**
             * 加入resolutionByCustomerList的设置
             */
            LgFilledBy resolutionByLogic = new LgFilledBy();
            List resolutionByCustomerList = resolutionByLogic.
                                            getFilledByListOptions(issue.
                getAccountId().toString(), (String)null);
            if (this.getUser().getUserType().equals(DtoUser.USER_TYPE_CUST)) {
                TagUtils.addSelectedOption(resolutionByCustomerList,
                                           this.getUser().getUserName(),
                                           this.getUser().getUserLoginId());
            }
            viewBean.setResolutionByCustomerList(resolutionByCustomerList);
//            if(viewBean.getResolutionByCustomer()==null) {
//                viewBean.setResolutionByCustomer(this.getUser().getUseLoginName());
//            }



            /**
             * 设置画面控件的可编辑属性
             */
            boolean isPrincipal = false;
            boolean isPm = false;
            boolean isCustomer = false;
            String accountRid = issue.getAccountId().toString();
            String principal = issue.getPrincipal();
            String pmName = accountLogic.getAccountManager(accountRid);
            String userName = this.getUser().getUserLoginId();
            viewBean.setPrincipalFlag("false");
            viewBean.setPmFlag("false");
            viewBean.setCustomerFlag("false");
            if (principal != null && principal.equals(userName)) {
                isPrincipal = true;
                viewBean.setPrincipalFlag("true");
            }
            if (pmName != null && pmName.equals(userName)) {
                isPm = true;
                viewBean.setPmFlag("true");
            }

            if (this.getUser().getUserType().equals(DtoUser.USER_TYPE_CUST)) {
                String issueType = issue.getIssueType();
                String scope = issue.getScope();
                if (issueTypeLogic.isExistScope(issueType, scope,
                                                DtoUser.USER_TYPE_CUST)) {
                    viewBean.setCustomerFlag("true");
                    isCustomer = true;
                }
            }

            viewBean.setProbabilityFlag("false");
            viewBean.setImpactLevelFlag("false");
            viewBean.setWeightLevelFlag("false");
            viewBean.setRemarkFlag("false");
            viewBean.setCategoryValueFlag("false");
            viewBean.setResolutionFlag("false");
            viewBean.setResolutionByFlag("false");
            viewBean.setPlanFinishDateFlag("false");
            viewBean.setAssignedDateFlag("false");
            viewBean.setAttachmentFlag("false");
            viewBean.setAttachmentDescFlag("false");

            if (isPm || isPrincipal || isCustomer) {
                viewBean.setProbabilityFlag("true");
                viewBean.setImpactLevelFlag("true");
                viewBean.setWeightLevelFlag("true");
                viewBean.setRemarkFlag("true");
                viewBean.setCategoryValueFlag("true");
                viewBean.setResolutionFlag("true");
                viewBean.setResolutionByFlag("true");
                viewBean.setPlanFinishDateFlag("true");
                viewBean.setAssignedDateFlag("true");
                viewBean.setAttachmentFlag("true");
                viewBean.setAttachmentDescFlag("true");
            }

            return viewBean;
        } catch (Exception ex) {
            throw new BusinessException("resolutionPrepare",
                                        "Can't load Issue Resolution", ex);
        }
    }

    /**
     * 根据AfIssueResolution分析Issue
     * 1.判断是否已存在Issue对应的IssueResolution对象
     *      if 不存在 then 新增IssueResolution
     *      else 更新 IssueResolution
     * @param param0 AfIssueResolution
     */
    public void resolution(AfIssueResolution form) {
        String rid = form.getRid();
        Long lRid = Long.valueOf(rid);

        //根据AssignDate是否修改了,要修改Issue的状态为与分派日期相关联的状态
        boolean isAssignDateChanged = false;
        try {
            Session session = this.getDbAccessor().getSession();
            IssueResolution issueResolution = (IssueResolution) session.get(
                IssueResolution.class, lRid);

            if (issueResolution == null) {
                isAssignDateChanged = true;
                //保存Resolution主记录
                issueResolution = new IssueResolution();
                DtoUtil.copyProperties(issueResolution, form);

                /**
                 * 在这里加入上传代码(上传功能开始)
                 */
                if (form.getAttachment() != null &&
                    !form.getAttachment().getFileName().equals("") &&
                    form.getAttachment().getFileSize() != 0) {

                    //1.根据accountRid取得accountCode
                    LgAccount accountLogic = new LgAccount();
                    String accountRid = form.getAccountId();
                    String accountCode = accountLogic.getAccountId(accountRid);
                    //2.组织fileInfo
                    FileInfo fileInfo = new FileInfo();
                    //3.设置fileInfo中的Account Code
                    fileInfo.setAccountcode(accountCode);
                    //4.创建 LgUpload
                    LgUpload uploadLogic = new LgUpload();
                    //5.初始化 fileInfo
                    uploadLogic.initFileInfo(form.getAttachment(), fileInfo);
                    //6.设置 fileInfo中的上传文件名
                    fileInfo.setFilename(form.getAttachment().getFileName());
                    //7.执行上传
                    uploadLogic.upload(form.getAttachment(), fileInfo);
                    //8.将上传文件的文件名和文件ID记入数据库
                    issueResolution.setAttachment(fileInfo.getFilename());
                    issueResolution.setAttachmentId(fileInfo.getId());

//                    DateFormat df = DateFormat.getDateInstance();
//                    issueResolution.setAssignedDate(df.parse(form.getAssignedDate()));
                }

                /**
                 * (上传功能结束)
                 */

                this.getDbAccessor().save(issueResolution);

                //保存Influence子记录
                StringBuffer _influence=new StringBuffer();
                if (form.getInfluences() != null &&
                    form.getInfluences().length > 0) {
                    for (int i = 0; i < form.getInfluences().length; i++) {
                        String influenceName = form.getInfluences()[i];
                        String impactLevel = form.getImpactLevels()[i];
                        String weight = form.getWeights()[i];
                        String remark = form.getRemarks()[i];
                        _influence.append(influenceName);
                        _influence.append(Delimiter.ITEM);
                        _influence.append(impactLevel);
                        _influence.append(Delimiter.ITEM);
                        _influence.append(weight);
                        _influence.append(Delimiter.ITEM);
                        _influence.append(remark);
                        _influence.append(Delimiter.GROUP);
                        IssueResolutionInflue issueResolutionInflue = new
                            IssueResolutionInflue();
                        issueResolutionInflue.setIssueResolution(
                            issueResolution);
                        issueResolutionInflue.setInfluenceName(influenceName);
                        issueResolutionInflue.setImpactLevel(Long.valueOf(
                            impactLevel));
                        issueResolutionInflue.setWeight(Double.valueOf(weight));
                        issueResolutionInflue.setRemark(remark);

                        this.getDbAccessor().save(issueResolutionInflue);
                    }
                }

                //保存Category子记录
//                StringBuffer _category=new StringBuffer();
                if (form.getCategories() != null &&
                    form.getCategories().length > 0) {
                    for (int i = 0; i < form.getCategories().length; i++) {
                        String categoryName = form.getCategories()[i];
                        String categoryValue = form.getCategoryValues()[i];
                        if (categoryValue == null || categoryValue.equals("")) {
                            continue;
                        }
//                        _category.append(categoryName);
//                        _category.append(Delimiter.ITEM);
//                        _category.append(categoryValue);
//                        _category.append(Delimiter.GROUP);
                        IssueResolutionCategory issueResolutionCategory = new
                            IssueResolutionCategory();
                        issueResolutionCategory.setIssueResolution(
                            issueResolution);
                        issueResolutionCategory.setCategoryName(categoryName);
                        issueResolutionCategory.setCategoryValue(categoryValue);
                        this.getDbAccessor().save(issueResolutionCategory);
                    }
                }
                //save Resolution to Tabel of IssueRiskHistory
                Issue issue =
                (Issue)this.getDbAccessor().load(Issue.class, lRid);

                //if need to save
                if(issueTypeLogic.isSaveInfluenceHistory(issue.getIssueType())){
                    VbIssueRiskHistory vbIssueRiskHistory = new
                        VbIssueRiskHistory();
                    vbIssueRiskHistory.setIssueRid(rid);
//                    vbIssueRiskHistory.setProbabilityFrom("");
                    if(form.getProbability()!=null && !"null".equalsIgnoreCase(form.getProbability())&&
                        !"".equals(form.getProbability()))
                        vbIssueRiskHistory.setProbabilityTo(form.getProbability());
//                    vbIssueRiskHistory.setRiskLevelFrom("");
                    if(form.getRiskLevel()!=null && !"null".equalsIgnoreCase(form.getRiskLevel())&&
                        !"".equals(form.getRiskLevel()))
                        vbIssueRiskHistory.setRiskLevelTo(form.getRiskLevel());
//                    vbIssueRiskHistory.setInfluenceFrom("");
                    vbIssueRiskHistory.setInfluenceTo(_influence.toString());
//                    vbIssueRiskHistory.setCategoryFrom("");
//                    vbIssueRiskHistory.setCategoryFrom(_category.toString());
                    LgIssueRiskHistory lgIssueRiskHistory = new LgIssueRiskHistory();
                    //save IssueRiskHistory
                    lgIssueRiskHistory.addIssueRiskHistory(vbIssueRiskHistory);
                }
            } else {
                boolean isChange=false;
                boolean probabilityChange=false;
                boolean riskLevelChange=false;
//                String _influenceFrom="";
//                String _categoryFrom="";
//                String _probabilityFrom=issueResolution.getProbability().toString();
//                String _riskLevelFrom=issueResolution.getRiskLevel().toString();
                if(form.getProbability()!=null){
                    if (issueResolution.getProbability() != Double.valueOf(form.getProbability()))
                        probabilityChange = true;
                }
                if(form.getRiskLevel()!=null){
                    if (issueResolution.getRiskLevel() !=
                        Double.valueOf(form.getRiskLevel()))
                        riskLevelChange = true;
                }
                if(probabilityChange||riskLevelChange)
                        isChange=true;

                Date assignedDate = issueResolution.getAssignedDate();
                //判断是否修改了AssignedDate
                if( form.getAssignedDate() != null &&
                    !form.getAssignedDate().equals(comDate.dateToString(assignedDate,"yyyy/MM/dd"))){
                     isAssignDateChanged = true;
                 }else{
                     isAssignDateChanged = false;
                 }
                 if(isAssignDateChanged){
                     //将Issue的状态改为与AssignedDate关联的状态
                     Issue issue = issueResolution.getIssue();
                     String fStatus = issue.getIssueStatus();
                     String tStatus = null;
                     LgIssueType lgIssueType = new LgIssueType();

                     List issueStatuses = issueTypeLogic.getRelationDataStatus(issue.getIssueType(),
                         RelationDate.ASSIGNED_DATE);
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
                             lgIssueStatusHistory.addIssueStatusHistory(issue.
                                 getRid(),
                                 fStatus, tStatus);
                         }
                     }

                 }

               String[] noCopyProperties = {"attachment"};
               DtoUtil.copyProperties(issueResolution, form,noCopyProperties);

                /**
                 * 在这里加入上传代码(上传功能开始)
                 */
                if (form.getAttachment() != null &&
                    !form.getAttachment().getFileName().equals("") &&
                    form.getAttachment().getFileSize() != 0) {

                    //1.根据accountRid取得accountCode
                    LgAccount accountLogic = new LgAccount();
                    String accountRid = form.getAccountId();
                    String accountCode = accountLogic.getAccountId(accountRid);
                    //2.组织fileInfo
                    FileInfo fileInfo = new FileInfo();
                    //3.设置fileInfo中的Account Code
                    fileInfo.setAccountcode(accountCode);
                    //4.创建 LgUpload
                    LgUpload uploadLogic = new LgUpload();
                    //5.初始化 fileInfo
                    uploadLogic.initFileInfo(form.getAttachment(), fileInfo);
                    //6.设置 fileInfo中的上传文件名
                    fileInfo.setFilename(form.getAttachment().getFileName());
                    //7.设置 fileInfo中的上传文件ID
                    if (issueResolution.getAttachmentId() != null) {
                        fileInfo.setId(issueResolution.getAttachmentId());
                    }
                    //8.执行上传
                    uploadLogic.upload(form.getAttachment(), fileInfo);
                    //9.将上传文件的文件名记入数据库
                    issueResolution.setAttachment(fileInfo.getFilename());
                    if (issueResolution.getAttachmentId() == null) {
                        issueResolution.setAttachmentId(fileInfo.getId());
                    }
                    issueResolution.setAttachmentdesc(form.getAttachmentDesc());
                }

                this.getDbAccessor().update(issueResolution);

                /**
                 * 更新reportStatus;
                 */
                if (form.getAssignedDate() != null &&
                    !form.getAssignedDate().equals("")) {
                    IssueReportStatus reportStatus = (IssueReportStatus)this.
                        getDbAccessor().
                        get(IssueReportStatus.class, lRid);
                    if (reportStatus != null) {
                        reportStatus.setProcessingDate(comDate.toDate(form.
                            getAssignedDate()));
                        this.getDbAccessor().update(reportStatus);
                    } else {
                        reportStatus=new IssueReportStatus();
                        reportStatus.setRid(issueResolution.getRid());
                        reportStatus.setProcessingDate(comDate.toDate(form.
                            getAssignedDate()));
                        this.getDbAccessor().save(reportStatus);
                    }
                }
                //保存Influence子记录
                StringBuffer _influence=new StringBuffer();
                if (form.getInfluences() != null &&
                    form.getInfluences().length > 0) {
                    for (int i = 0; i < form.getInfluences().length; i++) {
                        String influenceName = form.getInfluences()[i];
                        String impactLevel = form.getImpactLevels()[i];
                        String weight = form.getWeights()[i];
                        String remark = form.getRemarks()[i];
                        _influence.append(influenceName);
                        _influence.append(Delimiter.ITEM);
                        _influence.append(impactLevel);
                        _influence.append(Delimiter.ITEM);
                        _influence.append(weight);
                        _influence.append(Delimiter.ITEM);
                        _influence.append(remark);
                        _influence.append(Delimiter.GROUP);
                        //load IssueResolutionInflue from hibernate
                        Query q = session.createQuery(
                            "from IssueResolutionInflue f where f.issueResolution.rid=" +
                            rid
                            + " and f.influenceName='" + influenceName +
                            "' and f.rst='" + Constant.RST_NORMAL + "' ");
                        List influences = q.list();
                        IssueResolutionInflue issueResolutionInflue = null;

                        boolean isInsert = false;
                        if (influences != null && influences.size() > 0) {
                            issueResolutionInflue = (IssueResolutionInflue)
                                influences.get(0);
                        } else {
                            isInsert = true;
                            issueResolutionInflue = new
                                IssueResolutionInflue();
                            issueResolutionInflue.setIssueResolution(
                                issueResolution);
                        }
                        issueResolutionInflue.setInfluenceName(influenceName);
                        issueResolutionInflue.setImpactLevel(Long.valueOf(
                            impactLevel));
                        issueResolutionInflue.setWeight(Double.valueOf(weight));
                        issueResolutionInflue.setRemark(remark);

                        if (isInsert) {
                            this.getDbAccessor().save(issueResolutionInflue);
                        } else {
                            this.getDbAccessor().update(
                                issueResolutionInflue);
                        }
                    }
                }

                //保存Category子记录
//                StringBuffer _category=new StringBuffer();
                if (form.getCategories() != null &&
                    form.getCategories().length > 0) {
                    for (int i = 0; i < form.getCategories().length; i++) {
                        String categoryName = form.getCategories()[i];
                        String categoryValue = form.getCategoryValues()[i];
                        IssueResolutionCategory issueResolutionCategory = null;

                        //load IssueResolutionInflue from hibernate
                        Query q = session.createQuery(
                            "from IssueResolutionCategory c where c.issueResolution.rid=" +
                            rid
                            + " and c.categoryName='" + categoryName +
                            "' and c.rst='" + Constant.RST_NORMAL + "'");
                        List categories = q.list();

                        boolean isInsert = false;
                        if (categories != null && categories.size() > 0) {
                            issueResolutionCategory = (IssueResolutionCategory)
                                categories.get(0);
                        } else {
                            isInsert = true;
                            issueResolutionCategory = new
                                IssueResolutionCategory();
                        }

                        if (categoryValue == null || categoryValue.equals("")) {
                            if (!isInsert) {
                                session.delete(issueResolutionCategory);
                            }
                            continue;
                        }

                        issueResolutionCategory.setIssueResolution(
                            issueResolution);
                        issueResolutionCategory.setCategoryName(categoryName);
                        issueResolutionCategory.setCategoryValue(categoryValue);

                        if (isInsert) {
                            this.getDbAccessor().save(
                                issueResolutionCategory);
                        } else {
                            this.getDbAccessor().update(
                                issueResolutionCategory);
                        }
                    }

                }
                //save Resolution to Tabel of IssueRiskHistory
                    Issue issue =
                           (Issue)this.getDbAccessor().load(Issue.class, lRid);

                   //if need to save
                   if(issueTypeLogic.isSaveInfluenceHistory(issue.getIssueType()))
                       if(isChange)
                         {
                                VbIssueRiskHistory vbIssueRiskHistory = new VbIssueRiskHistory();
                                vbIssueRiskHistory.setIssueRid(rid);
//                                vbIssueRiskHistory.setProbabilityFrom(_probabilityFrom);
                                vbIssueRiskHistory.setProbabilityTo(form.getProbability());
//                                vbIssueRiskHistory.setRiskLevelFrom(_riskLevelFrom);
                                vbIssueRiskHistory.setRiskLevelTo(form.getRiskLevel());
//                                vbIssueRiskHistory.setInfluenceFrom(_influenceFrom);
                                vbIssueRiskHistory.setInfluenceTo(_influence.toString());
//                                vbIssueRiskHistory.setCategoryFrom(_categoryFrom);
//                                vbIssueRiskHistory.setCategoryTo(_category.toString());
                                LgIssueRiskHistory lgIssueRiskHistory = new LgIssueRiskHistory();
                                //save IssueRiskHistory
                                lgIssueRiskHistory.addIssueRiskHistory(vbIssueRiskHistory);

                        }
            }
        } catch (Exception e) {
            throw new BusinessException("resolution", "Can't update resolution",
                                        e);
        }
    }

    /**
     * 根据传入AfIssueResolution保存IssueResolution对象
     * 1.计算Issue分析风险等级
     * 2.构造IssueResolution对象
     * 3.保存IssueResolution对应的IssueResolutionCategory和IssueResolutionInfluence对象的集合
     * 4.获得IssueResolution对应的Issue，根据Issue.issueType获得该Issue对应的所有IssueStatus，按sequence升序排列
     * 5.遍历IssueStatus，查找关联日期为“Assigned Date”的第一个状态，设置Issue.issueStatus为该状态
     * @param param0 AfIssueResolution
     */
    public void add(server.essp.issue.issue.resolution.form.AfIssueResolution
                    param0) {
    }

    /**
     * 根据传入AfIssueResolution更新IssueResolution对象
     * 1.计算Issue分析风险等级
     * 2.获得AfIssueResolution对应的IssueResolution对象，设置属性值
     * 3.修改IssueResolutionCategory和IssueResolutionInfluence对象的集合
     * @param param0 AfIssueResolution
     */
    public void update(server.essp.issue.issue.resolution.form.
                       AfIssueResolution param0) {
    }

    /**
     * 计算Issue分析的风险等级，计算方法：
     * (各风险影响因素影响等级*权重）/权重之和
     * @param 待定
     * @return double
     */
    public Long caculateRiskLevel() {
        return null;
    }

    /**
     * 物理删除IssueResolution及其对应的IssueResolutionCategory和IssueResolutionInfluence对象的集合
     * 删除IssueResolution会级联删除IssueResolutionCategory和IssueResolutionInfluence
     * @see IssueResolution.hbm.xml
     * @param rid Long
     */
    public void delete(java.lang.Long rid) {
    }

    /**
     * 通过rid来获取General与Resolution卡片里面的数据
     * @return IssueGeneralResolutionMail
     * @param rid Long
     */
    public AfIssueGeneralResolution GeneralResolution(String rid)throws
        BusinessException {

        AfIssueGeneralResolution afigr = new AfIssueGeneralResolution();
        String accountid = null;
        String accountname = null;
        try{
            Session session = this.getDbAccessor().getSession();
            Long Rid = Long.valueOf(rid);
            Issue issue = (Issue) session.load(Issue.class, Rid);
            String Accountid =issue.getAccountId().toString();
            LgAccount accountLogic = new LgAccount();
            IDtoAccount account = accountLogic.getAccountByRid(Accountid);
            accountid = account.getId();
            accountname = account.getName();
            afigr.setIssuefid(rid);
            afigr.setAccountId(Accountid);
            afigr.setIssueid(issue.getIssueId());
            afigr.setIssuename(issue.getIssueName());

            if(issue.getDescription()!=null && issue.getDescription().length()>0){
                afigr.setIssuedesc(issue.getDescription());
            }
            afigr.setAccount(accountid+"--"+accountname);
            afigr.setIssue(issue.getIssueId()+"--"+issue.getIssueName());
            afigr.setPriority(issue.getPriority());
            afigr.setFillby(issue.getFilleBy());
            afigr.setFilldate(issue.getFilleDate().toString());
            afigr.setDuedate(issue.getDueDate().toString());
            afigr.setStatus(issue.getIssueStatus());
            if(issue.getAttachment()!=null && issue.getAttachment().length()>0){
                afigr.setAttachmentname1(issue.getAttachment());
            }
            if(issue.getAttachmentdesc()!=null && issue.getAttachmentdesc().length()>0){
                afigr.setAttachmentdesc1(issue.getAttachmentdesc());
            }
            IssueResolution IssueResolution = (IssueResolution) session.get(
                IssueResolution.class, Rid);
            afigr.setMailto(IssueResolution.getResolutionBy());
            if(IssueResolution.getResolution()!=null && IssueResolution.getResolution().length()>0){
                afigr.setResolution(IssueResolution.getResolution());
            }
            if(IssueResolution.getAssignedDate()!=null && IssueResolution.getAssignedDate().toString().length()>0){
                afigr.setAssigneddate(IssueResolution.getAssignedDate().toString());
            }
            afigr.setFinishdate(IssueResolution.getPlanFinishDate().toString());
            if(IssueResolution.getAttachment()!=null && IssueResolution.getAttachment().length()>0){
                afigr.setAttachmentname2(IssueResolution.getAttachment());
            }
            if(IssueResolution.getAttachmentdesc()!=null && IssueResolution.getAttachmentdesc().length()>0){
                afigr.setAttachmentdesc2(IssueResolution.getAttachmentdesc());
            }
        }catch(Exception e) {
            throw new BusinessException("GeneralResolution", "Can't Add GeneralResolution",
                                        e);
        }
        return afigr;
    }

    /**
     * GeneralResolution卡片发送邮件
     * @param afgr AfIssueGeneralResolution
     * @throws BusinessException
     */
    public void ResolutionSend(AfIssueGeneralResolution afgr)throws
        BusinessException {
        SendHastenMail shm = new SendHastenMail();
        shm.sendHastenMail(vmFile1, title, getEmailDate(afgr));

    }
    /**
     * 获取邮件模板内容
     * @param afgr AfIssueGeneralResolution
     * @return HashMap
     */
    public HashMap getEmailDate(AfIssueGeneralResolution afgr) {

        HashMap hm = new HashMap();
        HashMap atts = new HashMap();
        LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
        String sendEmail="", user,toEmail="",fileUri;
        user = afgr.getMailto();
        try {

            ArrayList al = new ArrayList();
            ContentBean cb = new ContentBean();

            al.add(afgr);
            cb.setUser(user);
            String mail[] = user.split(",");
            for (int i = 0; i < mail.length; i++) {
                if(sendEmail.length()>0){
                    sendEmail = sendEmail+","+ihui.getUserEmail(mail[i]);
                }else{
                    sendEmail = ihui.getUserEmail(mail[i]);
                }
            }

            String cc[] = afgr.getCc().split(",");
            for (int i = 0; i < cc.length; i++) {
                if(toEmail.length()>0){
                    toEmail = toEmail+","+ihui.getUserEmail(cc[i]);
                }else{
                    toEmail = ihui.getUserEmail(cc[i]);
                }
            }

            cb.setEmail(sendEmail);
            cb.setMailcontent(al);

            Session session = this.getDbAccessor().getSession();
            Long rid = Long.valueOf(afgr.getIssuefid());
            Issue issue = (Issue) session.load(Issue.class, rid);
            IssueResolution IssueResolution = (IssueResolution) session.load(IssueResolution.class, rid);
            String accountid =(String)afgr.getAccount().substring(0, afgr.getAccount().indexOf("--"));
            if (afgr.getAttachmentname1() != null && afgr.getAttachmentname1().length()>0
                                                                    ) {
                FileDataSource fds;

                FileInfo fileInfo = new FileInfo();
                fileInfo.setAccountcode(accountid);
                fileInfo.setId(issue.getAttachmentId().toString());
                fileInfo.setFilename(afgr.getAttachmentname1());
                fileInfo.setModulename("ISSUE");


                // 获取fileInfo的路径
                fileUri = fileInfo.getServerFullPath(this.getServerRoot());


                fds = new FileDataSource(fileUri);
                File f = fds.getFile();
                if (afgr.getAttachmentname1().equals(afgr.getAttachmentname2())) {
                    atts.put("Issue--"+afgr.getAttachmentname1(), f);
                } else {
                    atts.put(afgr.getAttachmentname1(), f);
                }

                cb.setAttachments(atts);
            }

            if (afgr.getAttachmentname2() != null && afgr.getAttachmentname2().length()>0) {

                FileDataSource fds;

                FileInfo fileInfo = new FileInfo();
                fileInfo.setAccountcode(accountid);
                fileInfo.setId(IssueResolution.getAttachmentId().toString());
                fileInfo.setFilename(afgr.getAttachmentname2());
                fileInfo.setModulename("ISSUE");

                // 获取fileInfo的路径
                fileUri = fileInfo.getServerFullPath(this.getServerRoot());


                fds = new FileDataSource(fileUri);
                File f = fds.getFile();
                if(afgr.getAttachmentname1().equals(afgr.getAttachmentname2())){
                    atts.put("Resolution--"+afgr.getAttachmentname2(), f);
                }else{
                    atts.put(afgr.getAttachmentname2(), f);
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

    public void test() throws Exception {
        this.getDbAccessor().newTx();
        Session session = this.getDbAccessor().getSession();
        IssueResolution issueResolution = (IssueResolution) session.load(
            IssueResolution.class, new Long(28));
        IssueResolutionInflue issueResolutionInflue = new IssueResolutionInflue(new
            Long(19));
        issueResolutionInflue.setInfluenceName("Delivery");
        issueResolutionInflue.setImpactLevel(new Long(1));
        issueResolutionInflue.setWeight(new Double(0.1));
        issueResolutionInflue.setRemark("test remark");
        issueResolutionInflue.setIssueResolution(issueResolution);
        session.saveOrUpdate(issueResolutionInflue);
        this.getDbAccessor().endTxCommit();
    }

    public static void main(String[] args) throws Exception {
        new LgIssueResolution().test();
    }
}
