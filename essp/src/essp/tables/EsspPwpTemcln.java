package essp.tables;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="essp_pwp_temcln"
 *     
*/
public class EsspPwpTemcln implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String schno;

    /** nullable persistent field */
    private String clnitem;

    /** nullable persistent field */
    private String clnplace;

    /** nullable persistent field */
    private Date clndate;

    /** nullable persistent field */
    private String clntime;

    /** nullable persistent field */
    private String clnruntime;

    /** nullable persistent field */
    private String plnpatha;

    /** nullable persistent field */
    private String delview;

    /** nullable persistent field */
    private String conIp;

    /** nullable persistent field */
    private String conUser;

    /** nullable persistent field */
    private String conTime;

    /** nullable persistent field */
    private String cost;

    /** nullable persistent field */
    private String userId;

    /** nullable persistent field */
    private String status;

    /** nullable persistent field */
    private String summarization;

    /** nullable persistent field */
    private String actualduration;

    /** nullable persistent field */
    private Date actualdate;

    /** nullable persistent field */
    private String completed;

    /** nullable persistent field */
    private String activityType;

    /** nullable persistent field */
    private String activityStatus;

    /** nullable persistent field */
    private String priority;

    /** nullable persistent field */
    private Date plannedCompletion;

    /** nullable persistent field */
    private Date actualCompletion;

    /** nullable persistent field */
    private String alarm;

    /** nullable persistent field */
    private Long contact;

    /** nullable persistent field */
    private Long account;

    /** nullable persistent field */
    private Long opportunity;

    /** nullable persistent field */
    private String attachment;

    /** nullable persistent field */
    private String attachmentDesc;

    /** nullable persistent field */
    private BigDecimal actual;

    /** nullable persistent field */
    private String actualCurrency;

    /** nullable persistent field */
    private Long contractCode;

    /** nullable persistent field */
    private BigDecimal budget;

    /** nullable persistent field */
    private String budgetCurrency;

    /** nullable persistent field */
    private Long opportunityStep;

    /** nullable persistent field */
    private Long salesType;

    /** nullable persistent field */
    private String owner;

    /** nullable persistent field */
    private String delFlag;

    /** nullable persistent field */
    private String actCode;

    /** nullable persistent field */
    private String wbsId;

    /** nullable persistent field */
    private String cstrType;

    /** nullable persistent field */
    private Date cstrDate;

    /** nullable persistent field */
    private String cstrType2;

    /** nullable persistent field */
    private Date cstrDate2;

    /** nullable persistent field */
    private Long originalDefect;

    /** nullable persistent field */
    private Date planStartDate;

    /** nullable persistent field */
    private Date computeStartDate;

    /** nullable persistent field */
    private Date computeEndDate;

    /** nullable persistent field */
    private BigDecimal estWt;

    /** nullable persistent field */
    private String activityManager;

    /** nullable persistent field */
    private String activityBrief;

    /** nullable persistent field */
    private String accountId;

    /** nullable persistent field */
    private String taskId;

    /** nullable persistent field */
    private Long customer;

    /** nullable persistent field */
    private String planUnit;

    /** nullable persistent field */
    private String actualUnit;

    /** nullable persistent field */
    private String remainUnit;

    /** nullable persistent field */
    private String completeUnit;

    /** nullable persistent field */
    private Long currentDefect;

    /** nullable persistent field */
    private Long distributeDefect;

    /** nullable persistent field */
    private Long completedRate;

    /** nullable persistent field */
    private String actualdateFlag;

    /** nullable persistent field */
    private String actualCompletionFlag;

    /** nullable persistent field */
    private Long originalBudget;

    /** nullable persistent field */
    private Long currentBudget;

    /** nullable persistent field */
    private Long distributeBudget;

    /** nullable persistent field */
    private String templateId;

    /** full constructor */
    public EsspPwpTemcln(Long id, String schno, String clnitem, String clnplace, Date clndate, String clntime, String clnruntime, String plnpatha, String delview, String conIp, String conUser, String conTime, String cost, String userId, String status, String summarization, String actualduration, Date actualdate, String completed, String activityType, String activityStatus, String priority, Date plannedCompletion, Date actualCompletion, String alarm, Long contact, Long account, Long opportunity, String attachment, String attachmentDesc, BigDecimal actual, String actualCurrency, Long contractCode, BigDecimal budget, String budgetCurrency, Long opportunityStep, Long salesType, String owner, String delFlag, String actCode, String wbsId, String cstrType, Date cstrDate, String cstrType2, Date cstrDate2, Long originalDefect, Date planStartDate, Date computeStartDate, Date computeEndDate, BigDecimal estWt, String activityManager, String activityBrief, String accountId, String taskId, Long customer, String planUnit, String actualUnit, String remainUnit, String completeUnit, Long currentDefect, Long distributeDefect, Long completedRate, String actualdateFlag, String actualCompletionFlag, Long originalBudget, Long currentBudget, Long distributeBudget, String templateId) {
        this.id = id;
        this.schno = schno;
        this.clnitem = clnitem;
        this.clnplace = clnplace;
        this.clndate = clndate;
        this.clntime = clntime;
        this.clnruntime = clnruntime;
        this.plnpatha = plnpatha;
        this.delview = delview;
        this.conIp = conIp;
        this.conUser = conUser;
        this.conTime = conTime;
        this.cost = cost;
        this.userId = userId;
        this.status = status;
        this.summarization = summarization;
        this.actualduration = actualduration;
        this.actualdate = actualdate;
        this.completed = completed;
        this.activityType = activityType;
        this.activityStatus = activityStatus;
        this.priority = priority;
        this.plannedCompletion = plannedCompletion;
        this.actualCompletion = actualCompletion;
        this.alarm = alarm;
        this.contact = contact;
        this.account = account;
        this.opportunity = opportunity;
        this.attachment = attachment;
        this.attachmentDesc = attachmentDesc;
        this.actual = actual;
        this.actualCurrency = actualCurrency;
        this.contractCode = contractCode;
        this.budget = budget;
        this.budgetCurrency = budgetCurrency;
        this.opportunityStep = opportunityStep;
        this.salesType = salesType;
        this.owner = owner;
        this.delFlag = delFlag;
        this.actCode = actCode;
        this.wbsId = wbsId;
        this.cstrType = cstrType;
        this.cstrDate = cstrDate;
        this.cstrType2 = cstrType2;
        this.cstrDate2 = cstrDate2;
        this.originalDefect = originalDefect;
        this.planStartDate = planStartDate;
        this.computeStartDate = computeStartDate;
        this.computeEndDate = computeEndDate;
        this.estWt = estWt;
        this.activityManager = activityManager;
        this.activityBrief = activityBrief;
        this.accountId = accountId;
        this.taskId = taskId;
        this.customer = customer;
        this.planUnit = planUnit;
        this.actualUnit = actualUnit;
        this.remainUnit = remainUnit;
        this.completeUnit = completeUnit;
        this.currentDefect = currentDefect;
        this.distributeDefect = distributeDefect;
        this.completedRate = completedRate;
        this.actualdateFlag = actualdateFlag;
        this.actualCompletionFlag = actualCompletionFlag;
        this.originalBudget = originalBudget;
        this.currentBudget = currentBudget;
        this.distributeBudget = distributeBudget;
        this.templateId = templateId;
    }

    /** default constructor */
    public EsspPwpTemcln() {
    }

    /** minimal constructor */
    public EsspPwpTemcln(Long id) {
        this.id = id;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="ID"
     *         
     */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** 
     *            @hibernate.property
     *             column="SCHNO"
     *             length="42"
     *         
     */
    public String getSchno() {
        return this.schno;
    }

    public void setSchno(String schno) {
        this.schno = schno;
    }

    /** 
     *            @hibernate.property
     *             column="CLNITEM"
     *             length="65535"
     *         
     */
    public String getClnitem() {
        return this.clnitem;
    }

    public void setClnitem(String clnitem) {
        this.clnitem = clnitem;
    }

    /** 
     *            @hibernate.property
     *             column="CLNPLACE"
     *             length="65535"
     *         
     */
    public String getClnplace() {
        return this.clnplace;
    }

    public void setClnplace(String clnplace) {
        this.clnplace = clnplace;
    }

    /** 
     *            @hibernate.property
     *             column="CLNDATE"
     *             length="10"
     *         
     */
    public Date getClndate() {
        return this.clndate;
    }

    public void setClndate(Date clndate) {
        this.clndate = clndate;
    }

    /** 
     *            @hibernate.property
     *             column="CLNTIME"
     *             length="16"
     *         
     */
    public String getClntime() {
        return this.clntime;
    }

    public void setClntime(String clntime) {
        this.clntime = clntime;
    }

    /** 
     *            @hibernate.property
     *             column="CLNRUNTIME"
     *             length="16"
     *         
     */
    public String getClnruntime() {
        return this.clnruntime;
    }

    public void setClnruntime(String clnruntime) {
        this.clnruntime = clnruntime;
    }

    /** 
     *            @hibernate.property
     *             column="PLNPATHA"
     *             length="65535"
     *         
     */
    public String getPlnpatha() {
        return this.plnpatha;
    }

    public void setPlnpatha(String plnpatha) {
        this.plnpatha = plnpatha;
    }

    /** 
     *            @hibernate.property
     *             column="DELVIEW"
     *             length="1"
     *         
     */
    public String getDelview() {
        return this.delview;
    }

    public void setDelview(String delview) {
        this.delview = delview;
    }

    /** 
     *            @hibernate.property
     *             column="CON_IP"
     *             length="45"
     *         
     */
    public String getConIp() {
        return this.conIp;
    }

    public void setConIp(String conIp) {
        this.conIp = conIp;
    }

    /** 
     *            @hibernate.property
     *             column="CON_USER"
     *             length="80"
     *         
     */
    public String getConUser() {
        return this.conUser;
    }

    public void setConUser(String conUser) {
        this.conUser = conUser;
    }

    /** 
     *            @hibernate.property
     *             column="CON_TIME"
     *             length="80"
     *         
     */
    public String getConTime() {
        return this.conTime;
    }

    public void setConTime(String conTime) {
        this.conTime = conTime;
    }

    /** 
     *            @hibernate.property
     *             column="COST"
     *             length="50"
     *         
     */
    public String getCost() {
        return this.cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    /** 
     *            @hibernate.property
     *             column="USER_ID"
     *             length="10"
     *         
     */
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** 
     *            @hibernate.property
     *             column="STATUS"
     *             length="20"
     *         
     */
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /** 
     *            @hibernate.property
     *             column="SUMMARIZATION"
     *             length="65535"
     *         
     */
    public String getSummarization() {
        return this.summarization;
    }

    public void setSummarization(String summarization) {
        this.summarization = summarization;
    }

    /** 
     *            @hibernate.property
     *             column="ACTUALDURATION"
     *             length="16"
     *         
     */
    public String getActualduration() {
        return this.actualduration;
    }

    public void setActualduration(String actualduration) {
        this.actualduration = actualduration;
    }

    /** 
     *            @hibernate.property
     *             column="ACTUALDATE"
     *             length="10"
     *         
     */
    public Date getActualdate() {
        return this.actualdate;
    }

    public void setActualdate(Date actualdate) {
        this.actualdate = actualdate;
    }

    /** 
     *            @hibernate.property
     *             column="COMPLETED"
     *             length="1"
     *         
     */
    public String getCompleted() {
        return this.completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    /** 
     *            @hibernate.property
     *             column="ACTIVITY_TYPE"
     *             length="50"
     *         
     */
    public String getActivityType() {
        return this.activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    /** 
     *            @hibernate.property
     *             column="ACTIVITY_STATUS"
     *             length="50"
     *         
     */
    public String getActivityStatus() {
        return this.activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    /** 
     *            @hibernate.property
     *             column="PRIORITY"
     *             length="50"
     *         
     */
    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    /** 
     *            @hibernate.property
     *             column="PLANNED_COMPLETION"
     *             length="10"
     *         
     */
    public Date getPlannedCompletion() {
        return this.plannedCompletion;
    }

    public void setPlannedCompletion(Date plannedCompletion) {
        this.plannedCompletion = plannedCompletion;
    }

    /** 
     *            @hibernate.property
     *             column="ACTUAL_COMPLETION"
     *             length="10"
     *         
     */
    public Date getActualCompletion() {
        return this.actualCompletion;
    }

    public void setActualCompletion(Date actualCompletion) {
        this.actualCompletion = actualCompletion;
    }

    /** 
     *            @hibernate.property
     *             column="ALARM"
     *             length="50"
     *         
     */
    public String getAlarm() {
        return this.alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    /** 
     *            @hibernate.property
     *             column="CONTACT"
     *             length="10"
     *         
     */
    public Long getContact() {
        return this.contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    /** 
     *            @hibernate.property
     *             column="ACCOUNT"
     *             length="10"
     *         
     */
    public Long getAccount() {
        return this.account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    /** 
     *            @hibernate.property
     *             column="OPPORTUNITY"
     *             length="10"
     *         
     */
    public Long getOpportunity() {
        return this.opportunity;
    }

    public void setOpportunity(Long opportunity) {
        this.opportunity = opportunity;
    }

    /** 
     *            @hibernate.property
     *             column="ATTACHMENT"
     *             length="200"
     *         
     */
    public String getAttachment() {
        return this.attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /** 
     *            @hibernate.property
     *             column="ATTACHMENT_DESC"
     *             length="65535"
     *         
     */
    public String getAttachmentDesc() {
        return this.attachmentDesc;
    }

    public void setAttachmentDesc(String attachmentDesc) {
        this.attachmentDesc = attachmentDesc;
    }

    /** 
     *            @hibernate.property
     *             column="ACTUAL"
     *             length="20"
     *         
     */
    public BigDecimal getActual() {
        return this.actual;
    }

    public void setActual(BigDecimal actual) {
        this.actual = actual;
    }

    /** 
     *            @hibernate.property
     *             column="ACTUAL_CURRENCY"
     *             length="5"
     *         
     */
    public String getActualCurrency() {
        return this.actualCurrency;
    }

    public void setActualCurrency(String actualCurrency) {
        this.actualCurrency = actualCurrency;
    }

    /** 
     *            @hibernate.property
     *             column="CONTRACT_CODE"
     *             length="10"
     *         
     */
    public Long getContractCode() {
        return this.contractCode;
    }

    public void setContractCode(Long contractCode) {
        this.contractCode = contractCode;
    }

    /** 
     *            @hibernate.property
     *             column="BUDGET"
     *             length="20"
     *         
     */
    public BigDecimal getBudget() {
        return this.budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    /** 
     *            @hibernate.property
     *             column="BUDGET_CURRENCY"
     *             length="5"
     *         
     */
    public String getBudgetCurrency() {
        return this.budgetCurrency;
    }

    public void setBudgetCurrency(String budgetCurrency) {
        this.budgetCurrency = budgetCurrency;
    }

    /** 
     *            @hibernate.property
     *             column="OPPORTUNITY_STEP"
     *             length="10"
     *         
     */
    public Long getOpportunityStep() {
        return this.opportunityStep;
    }

    public void setOpportunityStep(Long opportunityStep) {
        this.opportunityStep = opportunityStep;
    }

    /** 
     *            @hibernate.property
     *             column="SALES_TYPE"
     *             length="10"
     *         
     */
    public Long getSalesType() {
        return this.salesType;
    }

    public void setSalesType(Long salesType) {
        this.salesType = salesType;
    }

    /** 
     *            @hibernate.property
     *             column="OWNER"
     *             length="20"
     *         
     */
    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    /** 
     *            @hibernate.property
     *             column="DEL_FLAG"
     *             length="1"
     *         
     */
    public String getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /** 
     *            @hibernate.property
     *             column="ACT_CODE"
     *             length="30"
     *         
     */
    public String getActCode() {
        return this.actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    /** 
     *            @hibernate.property
     *             column="WBS_ID"
     *             length="30"
     *         
     */
    public String getWbsId() {
        return this.wbsId;
    }

    public void setWbsId(String wbsId) {
        this.wbsId = wbsId;
    }

    /** 
     *            @hibernate.property
     *             column="CSTR_TYPE"
     *             length="20"
     *         
     */
    public String getCstrType() {
        return this.cstrType;
    }

    public void setCstrType(String cstrType) {
        this.cstrType = cstrType;
    }

    /** 
     *            @hibernate.property
     *             column="CSTR_DATE"
     *             length="10"
     *         
     */
    public Date getCstrDate() {
        return this.cstrDate;
    }

    public void setCstrDate(Date cstrDate) {
        this.cstrDate = cstrDate;
    }

    /** 
     *            @hibernate.property
     *             column="CSTR_TYPE2"
     *             length="20"
     *         
     */
    public String getCstrType2() {
        return this.cstrType2;
    }

    public void setCstrType2(String cstrType2) {
        this.cstrType2 = cstrType2;
    }

    /** 
     *            @hibernate.property
     *             column="CSTR_DATE2"
     *             length="10"
     *         
     */
    public Date getCstrDate2() {
        return this.cstrDate2;
    }

    public void setCstrDate2(Date cstrDate2) {
        this.cstrDate2 = cstrDate2;
    }

    /** 
     *            @hibernate.property
     *             column="ORIGINAL_DEFECT"
     *             length="10"
     *         
     */
    public Long getOriginalDefect() {
        return this.originalDefect;
    }

    public void setOriginalDefect(Long originalDefect) {
        this.originalDefect = originalDefect;
    }

    /** 
     *            @hibernate.property
     *             column="PLAN_START_DATE"
     *             length="10"
     *         
     */
    public Date getPlanStartDate() {
        return this.planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    /** 
     *            @hibernate.property
     *             column="COMPUTE_START_DATE"
     *             length="10"
     *         
     */
    public Date getComputeStartDate() {
        return this.computeStartDate;
    }

    public void setComputeStartDate(Date computeStartDate) {
        this.computeStartDate = computeStartDate;
    }

    /** 
     *            @hibernate.property
     *             column="COMPUTE_END_DATE"
     *             length="10"
     *         
     */
    public Date getComputeEndDate() {
        return this.computeEndDate;
    }

    public void setComputeEndDate(Date computeEndDate) {
        this.computeEndDate = computeEndDate;
    }

    /** 
     *            @hibernate.property
     *             column="EST_WT"
     *             length="15"
     *         
     */
    public BigDecimal getEstWt() {
        return this.estWt;
    }

    public void setEstWt(BigDecimal estWt) {
        this.estWt = estWt;
    }

    /** 
     *            @hibernate.property
     *             column="ACTIVITY_MANAGER"
     *             length="20"
     *         
     */
    public String getActivityManager() {
        return this.activityManager;
    }

    public void setActivityManager(String activityManager) {
        this.activityManager = activityManager;
    }

    /** 
     *            @hibernate.property
     *             column="ACTIVITY_BRIEF"
     *             length="65535"
     *         
     */
    public String getActivityBrief() {
        return this.activityBrief;
    }

    public void setActivityBrief(String activityBrief) {
        this.activityBrief = activityBrief;
    }

    /** 
     *            @hibernate.property
     *             column="ACCOUNT_ID"
     *             length="20"
     *         
     */
    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /** 
     *            @hibernate.property
     *             column="TASK_ID"
     *             length="100"
     *         
     */
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /** 
     *            @hibernate.property
     *             column="CUSTOMER"
     *             length="10"
     *         
     */
    public Long getCustomer() {
        return this.customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    /** 
     *            @hibernate.property
     *             column="PLAN_UNIT"
     *             length="10"
     *         
     */
    public String getPlanUnit() {
        return this.planUnit;
    }

    public void setPlanUnit(String planUnit) {
        this.planUnit = planUnit;
    }

    /** 
     *            @hibernate.property
     *             column="ACTUAL_UNIT"
     *             length="10"
     *         
     */
    public String getActualUnit() {
        return this.actualUnit;
    }

    public void setActualUnit(String actualUnit) {
        this.actualUnit = actualUnit;
    }

    /** 
     *            @hibernate.property
     *             column="REMAIN_UNIT"
     *             length="10"
     *         
     */
    public String getRemainUnit() {
        return this.remainUnit;
    }

    public void setRemainUnit(String remainUnit) {
        this.remainUnit = remainUnit;
    }

    /** 
     *            @hibernate.property
     *             column="COMPLETE_UNIT"
     *             length="10"
     *         
     */
    public String getCompleteUnit() {
        return this.completeUnit;
    }

    public void setCompleteUnit(String completeUnit) {
        this.completeUnit = completeUnit;
    }

    /** 
     *            @hibernate.property
     *             column="CURRENT_DEFECT"
     *             length="10"
     *         
     */
    public Long getCurrentDefect() {
        return this.currentDefect;
    }

    public void setCurrentDefect(Long currentDefect) {
        this.currentDefect = currentDefect;
    }

    /** 
     *            @hibernate.property
     *             column="DISTRIBUTE_DEFECT"
     *             length="10"
     *         
     */
    public Long getDistributeDefect() {
        return this.distributeDefect;
    }

    public void setDistributeDefect(Long distributeDefect) {
        this.distributeDefect = distributeDefect;
    }

    /** 
     *            @hibernate.property
     *             column="COMPLETED_RATE"
     *             length="10"
     *         
     */
    public Long getCompletedRate() {
        return this.completedRate;
    }

    public void setCompletedRate(Long completedRate) {
        this.completedRate = completedRate;
    }

    /** 
     *            @hibernate.property
     *             column="ACTUALDATE_FLAG"
     *             length="1"
     *         
     */
    public String getActualdateFlag() {
        return this.actualdateFlag;
    }

    public void setActualdateFlag(String actualdateFlag) {
        this.actualdateFlag = actualdateFlag;
    }

    /** 
     *            @hibernate.property
     *             column="ACTUAL_COMPLETION_FLAG"
     *             length="1"
     *         
     */
    public String getActualCompletionFlag() {
        return this.actualCompletionFlag;
    }

    public void setActualCompletionFlag(String actualCompletionFlag) {
        this.actualCompletionFlag = actualCompletionFlag;
    }

    /** 
     *            @hibernate.property
     *             column="ORIGINAL_BUDGET"
     *             length="10"
     *         
     */
    public Long getOriginalBudget() {
        return this.originalBudget;
    }

    public void setOriginalBudget(Long originalBudget) {
        this.originalBudget = originalBudget;
    }

    /** 
     *            @hibernate.property
     *             column="CURRENT_BUDGET"
     *             length="10"
     *         
     */
    public Long getCurrentBudget() {
        return this.currentBudget;
    }

    public void setCurrentBudget(Long currentBudget) {
        this.currentBudget = currentBudget;
    }

    /** 
     *            @hibernate.property
     *             column="DISTRIBUTE_BUDGET"
     *             length="10"
     *         
     */
    public Long getDistributeBudget() {
        return this.distributeBudget;
    }

    public void setDistributeBudget(Long distributeBudget) {
        this.distributeBudget = distributeBudget;
    }

    /** 
     *            @hibernate.property
     *             column="TEMPLATE_ID"
     *             length="20"
     *         
     */
    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof EsspPwpTemcln) ) return false;
        EsspPwpTemcln castOther = (EsspPwpTemcln) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
