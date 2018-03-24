package essp.tables;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="qc_qtactv"
 *     
*/
public class QcQtactv implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long projectId;

    /** nullable persistent field */
    private Long activityId;

    /** persistent field */
    private String type;

    /** nullable persistent field */
    private String qualityActivity;

    /** nullable persistent field */
    private String production;

    /** nullable persistent field */
    private String productionUnit;

    /** nullable persistent field */
    private String method;

    /** nullable persistent field */
    private String criterion;

    /** nullable persistent field */
    private BigDecimal productionSizePlan;

    /** nullable persistent field */
    private BigDecimal densityPlan;

    /** nullable persistent field */
    private BigDecimal defectRatePlan;

    /** nullable persistent field */
    private BigDecimal productionSizeAct;

    /** nullable persistent field */
    private BigDecimal densityAct;

    /** nullable persistent field */
    private BigDecimal defectRateAct;

    /** nullable persistent field */
    private Long caseAct;

    /** nullable persistent field */
    private Long defectAct;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private Date rct;

    /** full constructor */
    public QcQtactv(Long rid, Long projectId, Long activityId, String type, String qualityActivity, String production, String productionUnit, String method, String criterion, BigDecimal productionSizePlan, BigDecimal densityPlan, BigDecimal defectRatePlan, BigDecimal productionSizeAct, BigDecimal densityAct, BigDecimal defectRateAct, Long caseAct, Long defectAct, String remark, String rst, Date rut, Date rct) {
        this.rid = rid;
        this.projectId = projectId;
        this.activityId = activityId;
        this.type = type;
        this.qualityActivity = qualityActivity;
        this.production = production;
        this.productionUnit = productionUnit;
        this.method = method;
        this.criterion = criterion;
        this.productionSizePlan = productionSizePlan;
        this.densityPlan = densityPlan;
        this.defectRatePlan = defectRatePlan;
        this.productionSizeAct = productionSizeAct;
        this.densityAct = densityAct;
        this.defectRateAct = defectRateAct;
        this.caseAct = caseAct;
        this.defectAct = defectAct;
        this.remark = remark;
        this.rst = rst;
        this.rut = rut;
        this.rct = rct;
    }

    /** default constructor */
    public QcQtactv() {
    }

    /** minimal constructor */
    public QcQtactv(Long rid, Long projectId, String type) {
        this.rid = rid;
        this.projectId = projectId;
        this.type = type;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="RID"
     *         
     */
    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    /** 
     *            @hibernate.property
     *             column="PROJECT_ID"
     *             length="8"
     *             not-null="true"
     *         
     */
    public Long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /** 
     *            @hibernate.property
     *             column="ACTIVITY_ID"
     *             length="8"
     *         
     */
    public Long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    /** 
     *            @hibernate.property
     *             column="TYPE"
     *             length="50"
     *             not-null="true"
     *         
     */
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /** 
     *            @hibernate.property
     *             column="QUALITY_ACTIVITY"
     *             length="50"
     *         
     */
    public String getQualityActivity() {
        return this.qualityActivity;
    }

    public void setQualityActivity(String qualityActivity) {
        this.qualityActivity = qualityActivity;
    }

    /** 
     *            @hibernate.property
     *             column="PRODUCTION"
     *             length="50"
     *         
     */
    public String getProduction() {
        return this.production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    /** 
     *            @hibernate.property
     *             column="PRODUCTION_UNIT"
     *             length="50"
     *         
     */
    public String getProductionUnit() {
        return this.productionUnit;
    }

    public void setProductionUnit(String productionUnit) {
        this.productionUnit = productionUnit;
    }

    /** 
     *            @hibernate.property
     *             column="METHOD"
     *             length="50"
     *         
     */
    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    /** 
     *            @hibernate.property
     *             column="CRITERION"
     *             length="50"
     *         
     */
    public String getCriterion() {
        return this.criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    /** 
     *            @hibernate.property
     *             column="PRODUCTION_SIZE_PLAN"
     *             length="8"
     *         
     */
    public BigDecimal getProductionSizePlan() {
        return this.productionSizePlan;
    }

    public void setProductionSizePlan(BigDecimal productionSizePlan) {
        this.productionSizePlan = productionSizePlan;
    }

    /** 
     *            @hibernate.property
     *             column="DENSITY_PLAN"
     *             length="8"
     *         
     */
    public BigDecimal getDensityPlan() {
        return this.densityPlan;
    }

    public void setDensityPlan(BigDecimal densityPlan) {
        this.densityPlan = densityPlan;
    }

    /** 
     *            @hibernate.property
     *             column="DEFECT_RATE_PLAN"
     *             length="8"
     *         
     */
    public BigDecimal getDefectRatePlan() {
        return this.defectRatePlan;
    }

    public void setDefectRatePlan(BigDecimal defectRatePlan) {
        this.defectRatePlan = defectRatePlan;
    }

    /** 
     *            @hibernate.property
     *             column="PRODUCTION_SIZE_ACT"
     *             length="8"
     *         
     */
    public BigDecimal getProductionSizeAct() {
        return this.productionSizeAct;
    }

    public void setProductionSizeAct(BigDecimal productionSizeAct) {
        this.productionSizeAct = productionSizeAct;
    }

    /** 
     *            @hibernate.property
     *             column="DENSITY_ACT"
     *             length="8"
     *         
     */
    public BigDecimal getDensityAct() {
        return this.densityAct;
    }

    public void setDensityAct(BigDecimal densityAct) {
        this.densityAct = densityAct;
    }

    /** 
     *            @hibernate.property
     *             column="DEFECT_RATE_ACT"
     *             length="8"
     *         
     */
    public BigDecimal getDefectRateAct() {
        return this.defectRateAct;
    }

    public void setDefectRateAct(BigDecimal defectRateAct) {
        this.defectRateAct = defectRateAct;
    }

    /** 
     *            @hibernate.property
     *             column="CASE_ACT"
     *             length="8"
     *         
     */
    public Long getCaseAct() {
        return this.caseAct;
    }

    public void setCaseAct(Long caseAct) {
        this.caseAct = caseAct;
    }

    /** 
     *            @hibernate.property
     *             column="DEFECT_ACT"
     *             length="8"
     *         
     */
    public Long getDefectAct() {
        return this.defectAct;
    }

    public void setDefectAct(Long defectAct) {
        this.defectAct = defectAct;
    }

    /** 
     *            @hibernate.property
     *             column="REMARK"
     *             length="65535"
     *         
     */
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 
     *            @hibernate.property
     *             column="RST"
     *             length="1"
     *         
     */
    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    /** 
     *            @hibernate.property
     *             column="RUT"
     *             length="19"
     *         
     */
    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    /** 
     *            @hibernate.property
     *             column="RCT"
     *             length="19"
     *         
     */
    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof QcQtactv) ) return false;
        QcQtactv castOther = (QcQtactv) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

}
