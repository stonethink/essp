package c2s.essp.pwm.wp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.DtoBase;
import com.wits.util.StringUtil;

/**
 *        @hibernate.class
 *         table="pw_wp"
 *
 */
public class DtoPwWp extends DtoBase implements Serializable {
    public final static String COMPETE_RATE_TYPE_HOUR = "0";
    public final static String COMPETE_RATE_TYPE_MANUAL = "1";
    public final static int CODE_NUMBER_LENGTH = 5;

    //// copy from PwWp
    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long projectId;
    private String accountId;
    private String accountName;
    private String accountManagerName;
    private String accountTypeName;


    /** persistent field */
    private Long activityId;
    private String activityCode;
    private String activityName;

    /** persistent field */
    private int wpSequence;

    /** persistent field */
    private String wpPwporwp;

    /** nullable persistent field */
    private String wpCode;

    /** persistent field */
    private String wpName;

    /** persistent field */
    private String wpType;

    /** persistent field */
    private String wpAssignby;

    /** persistent field */
    private String wpAssignbyName;

    /** persistent field */
    private Date wpAssigndate;

    /** persistent field */
    private String wpWorker;
    private String wpWorkerName;

    /** nullable persistent field */
    private BigDecimal wpReqWkhr;

    /** nullable persistent field */
    private BigDecimal wpPlanWkhr;

    /** nullable persistent field */
    private BigDecimal wpActWkhr;

    /** nullable persistent field */
    private Date wpPlanStart;

    /** nullable persistent field */
    private Date wpPlanFihish;

    /** nullable persistent field */
    private Date wpActStart;

    /** nullable persistent field */
    private Date wpActFinish;

    /** nullable persistent field */
    private String wpStatus;

    /** nullable persistent field */
    private String wpCmpltrateType;

    /** nullable persistent field */
    private Long wpCmpltrate;

    /** nullable persistent field */
    private String wpRequirement;

    //// copy from PwWpSum
    /** identifier field */
//    private Long rid;

    /** nullable persistent field */
    private String wpTechtype;

    /** nullable persistent field */
    private String wpSizeUnit;

    /** nullable persistent field */
    private BigDecimal wpSizePlan;

    /** nullable persistent field */
    private BigDecimal wpSizeAct;

    /** nullable persistent field */
    private String wpDensityrateUnit;

    /** nullable persistent field */
    private BigDecimal wpDensityratePlan;

    /** nullable persistent field */
    private BigDecimal wpDensityrateAct;

    /** nullable persistent field */
    private String wpProductivityUnit;

    /** nullable persistent field */
    private BigDecimal wpProductivityPlan;

    /** nullable persistent field */
    private BigDecimal wpProductivityAct;

    /** nullable persistent field */
    private String wpDefectrateUnit;

    /** nullable persistent field */
    private BigDecimal wpDefectratePlan;

    /** nullable persistent field */
    private BigDecimal wpDefectrateAct;

    /** nullable persistent field */
    private BigDecimal wpDensityPlan;

    /** nullable persistent field */
    private BigDecimal wpDensityAct;

    /** nullable persistent field */
    private Long wpDefectPlan;

    /** nullable persistent field */
    private Long wpDefectAct;

    /** nullable persistent field */
    private Long wpDefectRmv;

    /** nullable persistent field */
    private String wpRemark;

    /** nullable persistent field */
    private String wpAttachUrl;

    /** nullable persistent field */
    private String wpFilename;

    private boolean isAssignBy = false;
    private Long wpTypeCode;

    public DtoPwWp() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Date getWpActFinish() {
        return wpActFinish;
    }

    public void setWpActFinish(Date wpActFinish) {
        this.wpActFinish = wpActFinish;
    }

    public Date getWpActStart() {
        return wpActStart;
    }

    public void setWpActStart(Date wpActStart) {
        this.wpActStart = wpActStart;
    }

    public BigDecimal getWpActWkhr() {
        return wpActWkhr;
    }

    public void setWpActWkhr(BigDecimal wpActWkhr) {
        this.wpActWkhr = wpActWkhr;
        this.wpActWkhr = this.wpActWkhr.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String getWpAssignby() {
        return wpAssignby;
    }

    public void setWpAssignby(String wpAssignby) {
        this.wpAssignby = wpAssignby;
    }

    public Date getWpAssigndate() {
        return wpAssigndate;
    }

    public void setWpAssigndate(Date wpAssigndate) {
        this.wpAssigndate = wpAssigndate;
    }

    public String getWpAttachUrl() {
        return wpAttachUrl;
    }

    public void setWpAttachUrl(String wpAttachUrl) {
        this.wpAttachUrl = wpAttachUrl;
    }

    public Long getWpCmpltrate() {
        return wpCmpltrate;
    }

    public void setWpCmpltrate(Long wpCmpltrate) {
        this.wpCmpltrate = wpCmpltrate;
        //this.wpCmpltrate = this.wpCmpltrate.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String getWpCmpltrateType() {
        return wpCmpltrateType;
    }

    public void setWpCmpltrateType(String wpCmpltrateType) {
        this.wpCmpltrateType = wpCmpltrateType;
    }

    public String getWpCode() {
        return wpCode;
    }

    public void setWpCode(String wpCode) {
        this.wpCode = wpCode;
    }

    public Long getWpDefectAct() {
        return wpDefectAct;
    }

    public void setWpDefectAct(Long wpDefectAct) {
        this.wpDefectAct = wpDefectAct;
    }

    public Long getWpDefectPlan() {
        return wpDefectPlan;
    }

    public void setWpDefectPlan(Long wpDefectPlan) {
        this.wpDefectPlan = wpDefectPlan;
    }

    public BigDecimal getWpDefectrateAct() {
        return wpDefectrateAct;
    }

    public void setWpDefectrateAct(BigDecimal wpDefectrateAct) {
        this.wpDefectrateAct = wpDefectrateAct;
        this.wpDefectrateAct = this.wpDefectrateAct.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getWpDefectratePlan() {
        return wpDefectratePlan;
    }

    public void setWpDefectratePlan(BigDecimal wpDefectratePlan) {
        this.wpDefectratePlan = wpDefectratePlan;
        this.wpDefectratePlan = this.wpDefectratePlan.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String getWpDefectrateUnit() {
        return wpDefectrateUnit;
    }

    public void setWpDefectrateUnit(String wpDefectrateUnit) {
        this.wpDefectrateUnit = wpDefectrateUnit;
    }

    public Long getWpDefectRmv() {
        return wpDefectRmv;
    }

    public void setWpDefectRmv(Long wpDefectRmv) {
        this.wpDefectRmv = wpDefectRmv;
    }

    public BigDecimal getWpDensityAct() {
        return wpDensityAct;
    }

    public void setWpDensityAct(BigDecimal wpDensityAct) {
        this.wpDensityAct = wpDensityAct;
        this.wpDensityAct = this.wpDensityAct.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getWpDensityPlan() {
        return wpDensityPlan;
    }

    public void setWpDensityPlan(BigDecimal wpDensityPlan) {
        this.wpDensityPlan = wpDensityPlan;
        this.wpDensityPlan = this.wpDensityPlan.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getWpDensityrateAct() {
        return wpDensityrateAct;
    }

    public void setWpDensityrateAct(BigDecimal wpDensityrateAct) {
        this.wpDensityrateAct = wpDensityrateAct;
        this.wpDensityrateAct = this.wpDensityrateAct.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getWpDensityratePlan() {
        return wpDensityratePlan;
    }

    public void setWpDensityratePlan(BigDecimal wpDensityratePlan) {
        this.wpDensityratePlan = wpDensityratePlan;
        this.wpDensityratePlan = this.wpDensityratePlan.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String getWpDensityrateUnit() {
        return wpDensityrateUnit;
    }

    public void setWpDensityrateUnit(String wpDensityrateUnit) {
        this.wpDensityrateUnit = wpDensityrateUnit;
    }

    public String getWpFilename() {
        return wpFilename;
    }

    public void setWpFilename(String wpFilename) {
        this.wpFilename = wpFilename;
    }

    public String getWpName() {
        return wpName;
    }

    public void setWpName(String wpName) {
        this.wpName = wpName;
    }

    public Date getWpPlanFihish() {
        return wpPlanFihish;
    }

    public void setWpPlanFihish(Date wpPlanFihish) {
        this.wpPlanFihish = wpPlanFihish;
    }

    public Date getWpPlanStart() {
        return wpPlanStart;
    }

    public void setWpPlanStart(Date wpPlanStart) {
        this.wpPlanStart = wpPlanStart;
    }

    public BigDecimal getWpPlanWkhr() {
        return wpPlanWkhr;
    }

    public void setWpPlanWkhr(BigDecimal wpPlanWkhr) {
        this.wpPlanWkhr = wpPlanWkhr;
        this.wpPlanWkhr = this.wpPlanWkhr.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getWpProductivityAct() {
        return wpProductivityAct;
    }

    public void setWpProductivityAct(BigDecimal wpProductivityAct) {
        this.wpProductivityAct = wpProductivityAct;
        this.wpProductivityAct = this.wpProductivityAct.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getWpProductivityPlan() {
        return wpProductivityPlan;
    }

    public void setWpProductivityPlan(BigDecimal wpProductivityPlan) {
        this.wpProductivityPlan = wpProductivityPlan;
        this.wpProductivityPlan = this.wpProductivityPlan.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String getWpProductivityUnit() {
        return wpProductivityUnit;
    }

    public void setWpProductivityUnit(String wpProductivityUnit) {
        this.wpProductivityUnit = wpProductivityUnit;
    }

    public String getWpPwporwp() {
        return wpPwporwp;
    }

    public void setWpPwporwp(String wpPwporwp) {
        this.wpPwporwp = wpPwporwp;
    }

    public String getWpRemark() {
        return wpRemark;
    }

    public void setWpRemark(String wpRemark) {
        this.wpRemark = wpRemark;
    }

    public String getWpRequirement() {
        return wpRequirement;
    }

    public void setWpRequirement(String wpRequirement) {
        this.wpRequirement = wpRequirement;
    }

    public BigDecimal getWpReqWkhr() {
        return wpReqWkhr;
    }

    public void setWpReqWkhr(BigDecimal wpReqWkhr) {
        this.wpReqWkhr = wpReqWkhr;
        this.wpReqWkhr = this.wpReqWkhr.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public int getWpSequence() {
        return wpSequence;
    }

    public void setWpSequence(int wpSequence) {
        this.wpSequence = wpSequence;
    }

    public BigDecimal getWpSizeAct() {
        return wpSizeAct;
    }

    public void setWpSizeAct(BigDecimal wpSizeAct) {
        this.wpSizeAct = wpSizeAct;
        this.wpSizeAct = this.wpSizeAct.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getWpSizePlan() {
        return wpSizePlan;
    }

    public void setWpSizePlan(BigDecimal wpSizePlan) {
        this.wpSizePlan = wpSizePlan;
        this.wpSizePlan = this.wpSizePlan.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String getWpSizeUnit() {
        return wpSizeUnit;
    }

    public void setWpSizeUnit(String wpSizeUnit) {
        this.wpSizeUnit = wpSizeUnit;
    }

    public String getWpStatus() {
        return wpStatus;
    }

    public void setWpStatus(String wpStatus) {
        this.wpStatus = wpStatus;
    }

    public String getWpTechtype() {
        return wpTechtype;
    }

    public void setWpTechtype(String wpTechtype) {
        this.wpTechtype = wpTechtype;
    }

    public String getWpType() {
        return wpType;
    }

    public void setWpType(String wpType) {
        this.wpType = wpType;
    }

    public String getWpWorker() {
        return wpWorker;
    }

    public void setWpWorker(String wpWorker) {
        this.wpWorker = wpWorker;
    }

    public String getWpAssignbyName() {
        return wpAssignbyName;
    }

    public String getWpWorkerName() {
        return wpWorkerName;
    }

    public boolean isIsAssignBy() {
        return isAssignBy;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountManagerName() {
        return accountManagerName;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public Long getWpTypeCode() {
        return wpTypeCode;
    }

    public void setWpAssignbyName(String wpAssignbyName) {
        this.wpAssignbyName = wpAssignbyName;
    }

    public void setWpWorkerName(String wpWorkerName) {
        this.wpWorkerName = wpWorkerName;
    }

    public void setIsAssignBy(boolean isAssignBy) {
        this.isAssignBy = isAssignBy;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountManagerName(String accountManagerName) {
        this.accountManagerName = accountManagerName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public void setWpTypeCode(Long wpTypeCode) {
        this.wpTypeCode = wpTypeCode;
    }

    private void jbInit() throws Exception {
    }

    //为table的显示增加下面的函数
    public String getWpSizePlanAndUnit() {
        if (getWpSizePlan() != null) {
            return getWpSizePlan().doubleValue() + " " + StringUtil.nvl(getWpSizeUnit());
        } else {
            return "";
        }
    }

    public String getWpDensityratePlanAndUnit() {
        if (getWpDensityratePlan() != null) {
            return getWpDensityratePlan().doubleValue() + " " + StringUtil.nvl(getWpDensityrateUnit()) ;
        } else {
            return "";
        }
    }

    public String getWpDefectratePlanAndUnit() {
        if (getWpDefectratePlan() != null) {
            return getWpDefectratePlan().doubleValue() + " " + StringUtil.nvl(getWpDefectrateUnit());
        } else {
            return "";
        }
    }

}
