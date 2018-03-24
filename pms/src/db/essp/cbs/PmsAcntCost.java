package db.essp.cbs;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="pms_acnt_cost"
 *         dynamic-update="true"
 *         dynamic-insert="true"
 *     
*/
public class PmsAcntCost implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Long antiRid;

    /** nullable persistent field */
    private Double antiPm;

    /** nullable persistent field */
    private Double antiAmt;

    /** nullable persistent field */
    private Long baseRid;

    /** nullable persistent field */
    private String baseId;

    /** nullable persistent field */
    private Double basePm;

    /** nullable persistent field */
    private Double baseAmt;

    /** nullable persistent field */
    private Long propRid;

    /** nullable persistent field */
    private Double propPm;

    /** nullable persistent field */
    private Double propAmt;

    /** nullable persistent field */
    private Date costDate;

    /** nullable persistent field */
    private Long costRid;

    /** nullable persistent field */
    private Double costPm;

    /** nullable persistent field */
    private Double costAmt;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public PmsAcntCost(Long rid, Long antiRid, Double antiPm, Double antiAmt, Long baseRid, String baseId, Double basePm, Double baseAmt, Long propRid, Double propPm, Double propAmt, Date costDate, Long costRid, Double costPm, Double costAmt, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.antiRid = antiRid;
        this.antiPm = antiPm;
        this.antiAmt = antiAmt;
        this.baseRid = baseRid;
        this.baseId = baseId;
        this.basePm = basePm;
        this.baseAmt = baseAmt;
        this.propRid = propRid;
        this.propPm = propPm;
        this.propAmt = propAmt;
        this.costDate = costDate;
        this.costRid = costRid;
        this.costPm = costPm;
        this.costAmt = costAmt;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public PmsAcntCost() {
    }

    /** minimal constructor */
    public PmsAcntCost(Long rid) {
        this.rid = rid;
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
     *             column="ANTI_RID"
     *             length="8"
     *         
     */
    public Long getAntiRid() {
        return this.antiRid;
    }

    public void setAntiRid(Long antiRid) {
        this.antiRid = antiRid;
    }

    /** 
     *            @hibernate.property
     *             column="ANTI_PM"
     *             length="10"
     *         
     */
    public Double getAntiPm() {
        return this.antiPm;
    }

    public void setAntiPm(Double antiPm) {
        this.antiPm = antiPm;
    }

    /** 
     *            @hibernate.property
     *             column="ANTI_AMT"
     *             length="14"
     *         
     */
    public Double getAntiAmt() {
        return this.antiAmt;
    }

    public void setAntiAmt(Double antiAmt) {
        this.antiAmt = antiAmt;
    }

    /** 
     *            @hibernate.property
     *             column="BASE_RID"
     *             length="8"
     *         
     */
    public Long getBaseRid() {
        return this.baseRid;
    }

    public void setBaseRid(Long baseRid) {
        this.baseRid = baseRid;
    }

    /** 
     *            @hibernate.property
     *             column="BASE_ID"
     *             length="50"
     *         
     */
    public String getBaseId() {
        return this.baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    /** 
     *            @hibernate.property
     *             column="BASE_PM"
     *             length="10"
     *         
     */
    public Double getBasePm() {
        return this.basePm;
    }

    public void setBasePm(Double basePm) {
        this.basePm = basePm;
    }

    /** 
     *            @hibernate.property
     *             column="BASE_AMT"
     *             length="14"
     *         
     */
    public Double getBaseAmt() {
        return this.baseAmt;
    }

    public void setBaseAmt(Double baseAmt) {
        this.baseAmt = baseAmt;
    }

    /** 
     *            @hibernate.property
     *             column="PROP_RID"
     *             length="8"
     *         
     */
    public Long getPropRid() {
        return this.propRid;
    }

    public void setPropRid(Long propRid) {
        this.propRid = propRid;
    }

    /** 
     *            @hibernate.property
     *             column="PROP_PM"
     *             length="10"
     *         
     */
    public Double getPropPm() {
        return this.propPm;
    }

    public void setPropPm(Double propPm) {
        this.propPm = propPm;
    }

    /** 
     *            @hibernate.property
     *             column="PROP_AMT"
     *             length="14"
     *         
     */
    public Double getPropAmt() {
        return this.propAmt;
    }

    public void setPropAmt(Double propAmt) {
        this.propAmt = propAmt;
    }

    /** 
     *            @hibernate.property
     *             column="COST_DATE"
     *             length="19"
     *         
     */
    public Date getCostDate() {
        return this.costDate;
    }

    public void setCostDate(Date costDate) {
        this.costDate = costDate;
    }

    /** 
     *            @hibernate.property
     *             column="COST_RID"
     *             length="8"
     *         
     */
    public Long getCostRid() {
        return this.costRid;
    }

    public void setCostRid(Long costRid) {
        this.costRid = costRid;
    }

    /** 
     *            @hibernate.property
     *             column="COST_PM"
     *             length="10"
     *         
     */
    public Double getCostPm() {
        return this.costPm;
    }

    public void setCostPm(Double costPm) {
        this.costPm = costPm;
    }

    /** 
     *            @hibernate.property
     *             column="COST_AMT"
     *             length="14"
     *         
     */
    public Double getCostAmt() {
        return this.costAmt;
    }

    public void setCostAmt(Double costAmt) {
        this.costAmt = costAmt;
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
     *             length="19"
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
     *             length="19"
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

    public boolean equals(Object other) {
        if ( !(other instanceof PmsAcntCost) ) return false;
        PmsAcntCost castOther = (PmsAcntCost) other;
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
