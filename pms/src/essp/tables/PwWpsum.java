package essp.tables;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 *        @hibernate.class
 *         table="pw_wpsum"
 *     
*/
public class PwWpsum implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long wpId;

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
    private Long wpDefectRmn;

    /** nullable persistent field */
    private String wpRemark;

    /** nullable persistent field */
    private String wpAttachUrl;

    /** nullable persistent field */
    private String wpFilename;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public PwWpsum(Long rid, Long wpId, String wpTechtype, String wpSizeUnit, BigDecimal wpSizePlan, BigDecimal wpSizeAct, String wpDensityrateUnit, BigDecimal wpDensityratePlan, BigDecimal wpDensityrateAct, String wpProductivityUnit, BigDecimal wpProductivityPlan, BigDecimal wpProductivityAct, String wpDefectrateUnit, BigDecimal wpDefectratePlan, BigDecimal wpDefectrateAct, BigDecimal wpDensityPlan, BigDecimal wpDensityAct, Long wpDefectPlan, Long wpDefectAct, Long wpDefectRmv, Long wpDefectRmn, String wpRemark, String wpAttachUrl, String wpFilename, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.wpId = wpId;
        this.wpTechtype = wpTechtype;
        this.wpSizeUnit = wpSizeUnit;
        this.wpSizePlan = wpSizePlan;
        this.wpSizeAct = wpSizeAct;
        this.wpDensityrateUnit = wpDensityrateUnit;
        this.wpDensityratePlan = wpDensityratePlan;
        this.wpDensityrateAct = wpDensityrateAct;
        this.wpProductivityUnit = wpProductivityUnit;
        this.wpProductivityPlan = wpProductivityPlan;
        this.wpProductivityAct = wpProductivityAct;
        this.wpDefectrateUnit = wpDefectrateUnit;
        this.wpDefectratePlan = wpDefectratePlan;
        this.wpDefectrateAct = wpDefectrateAct;
        this.wpDensityPlan = wpDensityPlan;
        this.wpDensityAct = wpDensityAct;
        this.wpDefectPlan = wpDefectPlan;
        this.wpDefectAct = wpDefectAct;
        this.wpDefectRmv = wpDefectRmv;
        this.wpDefectRmn = wpDefectRmn;
        this.wpRemark = wpRemark;
        this.wpAttachUrl = wpAttachUrl;
        this.wpFilename = wpFilename;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public PwWpsum() {
    }

    /** minimal constructor */
    public PwWpsum(Long rid, Long wpId) {
        this.rid = rid;
        this.wpId = wpId;
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
     *             column="WP_ID"
     *             length="8"
     *             not-null="true"
     *         
     */
    public Long getWpId() {
        return this.wpId;
    }

    public void setWpId(Long wpId) {
        this.wpId = wpId;
    }

    /** 
     *            @hibernate.property
     *             column="WP_TECHTYPE"
     *             length="50"
     *         
     */
    public String getWpTechtype() {
        return this.wpTechtype;
    }

    public void setWpTechtype(String wpTechtype) {
        this.wpTechtype = wpTechtype;
    }

    /** 
     *            @hibernate.property
     *             column="WP_SIZE_UNIT"
     *             length="50"
     *         
     */
    public String getWpSizeUnit() {
        return this.wpSizeUnit;
    }

    public void setWpSizeUnit(String wpSizeUnit) {
        this.wpSizeUnit = wpSizeUnit;
    }

    /** 
     *            @hibernate.property
     *             column="WP_SIZE_PLAN"
     *             length="8"
     *         
     */
    public BigDecimal getWpSizePlan() {
        return this.wpSizePlan;
    }

    public void setWpSizePlan(BigDecimal wpSizePlan) {
        this.wpSizePlan = wpSizePlan;
    }

    /** 
     *            @hibernate.property
     *             column="WP_SIZE_ACT"
     *             length="8"
     *         
     */
    public BigDecimal getWpSizeAct() {
        return this.wpSizeAct;
    }

    public void setWpSizeAct(BigDecimal wpSizeAct) {
        this.wpSizeAct = wpSizeAct;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DENSITYRATE_UNIT"
     *             length="50"
     *         
     */
    public String getWpDensityrateUnit() {
        return this.wpDensityrateUnit;
    }

    public void setWpDensityrateUnit(String wpDensityrateUnit) {
        this.wpDensityrateUnit = wpDensityrateUnit;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DENSITYRATE_PLAN"
     *             length="8"
     *         
     */
    public BigDecimal getWpDensityratePlan() {
        return this.wpDensityratePlan;
    }

    public void setWpDensityratePlan(BigDecimal wpDensityratePlan) {
        this.wpDensityratePlan = wpDensityratePlan;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DENSITYRATE_ACT"
     *             length="8"
     *         
     */
    public BigDecimal getWpDensityrateAct() {
        return this.wpDensityrateAct;
    }

    public void setWpDensityrateAct(BigDecimal wpDensityrateAct) {
        this.wpDensityrateAct = wpDensityrateAct;
    }

    /** 
     *            @hibernate.property
     *             column="WP_PRODUCTIVITY_UNIT"
     *             length="50"
     *         
     */
    public String getWpProductivityUnit() {
        return this.wpProductivityUnit;
    }

    public void setWpProductivityUnit(String wpProductivityUnit) {
        this.wpProductivityUnit = wpProductivityUnit;
    }

    /** 
     *            @hibernate.property
     *             column="WP_PRODUCTIVITY_PLAN"
     *             length="8"
     *         
     */
    public BigDecimal getWpProductivityPlan() {
        return this.wpProductivityPlan;
    }

    public void setWpProductivityPlan(BigDecimal wpProductivityPlan) {
        this.wpProductivityPlan = wpProductivityPlan;
    }

    /** 
     *            @hibernate.property
     *             column="WP_PRODUCTIVITY_ACT"
     *             length="8"
     *         
     */
    public BigDecimal getWpProductivityAct() {
        return this.wpProductivityAct;
    }

    public void setWpProductivityAct(BigDecimal wpProductivityAct) {
        this.wpProductivityAct = wpProductivityAct;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DEFECTRATE_UNIT"
     *             length="50"
     *         
     */
    public String getWpDefectrateUnit() {
        return this.wpDefectrateUnit;
    }

    public void setWpDefectrateUnit(String wpDefectrateUnit) {
        this.wpDefectrateUnit = wpDefectrateUnit;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DEFECTRATE_PLAN"
     *             length="8"
     *         
     */
    public BigDecimal getWpDefectratePlan() {
        return this.wpDefectratePlan;
    }

    public void setWpDefectratePlan(BigDecimal wpDefectratePlan) {
        this.wpDefectratePlan = wpDefectratePlan;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DEFECTRATE_ACT"
     *             length="8"
     *         
     */
    public BigDecimal getWpDefectrateAct() {
        return this.wpDefectrateAct;
    }

    public void setWpDefectrateAct(BigDecimal wpDefectrateAct) {
        this.wpDefectrateAct = wpDefectrateAct;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DENSITY_PLAN"
     *             length="8"
     *         
     */
    public BigDecimal getWpDensityPlan() {
        return this.wpDensityPlan;
    }

    public void setWpDensityPlan(BigDecimal wpDensityPlan) {
        this.wpDensityPlan = wpDensityPlan;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DENSITY_ACT"
     *             length="8"
     *         
     */
    public BigDecimal getWpDensityAct() {
        return this.wpDensityAct;
    }

    public void setWpDensityAct(BigDecimal wpDensityAct) {
        this.wpDensityAct = wpDensityAct;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DEFECT_PLAN"
     *             length="8"
     *         
     */
    public Long getWpDefectPlan() {
        return this.wpDefectPlan;
    }

    public void setWpDefectPlan(Long wpDefectPlan) {
        this.wpDefectPlan = wpDefectPlan;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DEFECT_ACT"
     *             length="8"
     *         
     */
    public Long getWpDefectAct() {
        return this.wpDefectAct;
    }

    public void setWpDefectAct(Long wpDefectAct) {
        this.wpDefectAct = wpDefectAct;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DEFECT_RMV"
     *             length="8"
     *         
     */
    public Long getWpDefectRmv() {
        return this.wpDefectRmv;
    }

    public void setWpDefectRmv(Long wpDefectRmv) {
        this.wpDefectRmv = wpDefectRmv;
    }

    /** 
     *            @hibernate.property
     *             column="WP_DEFECT_RMN"
     *             length="8"
     *         
     */
    public Long getWpDefectRmn() {
        return this.wpDefectRmn;
    }

    public void setWpDefectRmn(Long wpDefectRmn) {
        this.wpDefectRmn = wpDefectRmn;
    }

    /** 
     *            @hibernate.property
     *             column="WP_REMARK"
     *             length="65535"
     *         
     */
    public String getWpRemark() {
        return this.wpRemark;
    }

    public void setWpRemark(String wpRemark) {
        this.wpRemark = wpRemark;
    }

    /** 
     *            @hibernate.property
     *             column="WP_ATTACH_URL"
     *             length="100"
     *         
     */
    public String getWpAttachUrl() {
        return this.wpAttachUrl;
    }

    public void setWpAttachUrl(String wpAttachUrl) {
        this.wpAttachUrl = wpAttachUrl;
    }

    /** 
     *            @hibernate.property
     *             column="WP_FILENAME"
     *             length="50"
     *         
     */
    public String getWpFilename() {
        return this.wpFilename;
    }

    public void setWpFilename(String wpFilename) {
        this.wpFilename = wpFilename;
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
     *             column="RCT"
     *             length="10"
     *         
     */
    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    /** 
     *            @hibernate.property
     *             column="RUT"
     *             length="10"
     *         
     */
    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
