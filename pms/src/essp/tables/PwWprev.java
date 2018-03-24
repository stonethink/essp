package essp.tables;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 *        @hibernate.class
 *         table="pw_wprev"
 *     
*/
public class PwWprev implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long wpId;

    /** nullable persistent field */
    private BigDecimal wprevWkyield;

    /** nullable persistent field */
    private BigDecimal wprevTime;

    /** nullable persistent field */
    private BigDecimal wprevQuality;

    /** nullable persistent field */
    private BigDecimal wprevCost;

    /** nullable persistent field */
    private BigDecimal wprevPerformance;

    /** nullable persistent field */
    private String wprevComment;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public PwWprev(Long rid, Long wpId, BigDecimal wprevWkyield, BigDecimal wprevTime, BigDecimal wprevQuality, BigDecimal wprevCost, BigDecimal wprevPerformance, String wprevComment, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.wpId = wpId;
        this.wprevWkyield = wprevWkyield;
        this.wprevTime = wprevTime;
        this.wprevQuality = wprevQuality;
        this.wprevCost = wprevCost;
        this.wprevPerformance = wprevPerformance;
        this.wprevComment = wprevComment;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public PwWprev() {
    }

    /** minimal constructor */
    public PwWprev(Long rid, Long wpId) {
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
     *             column="WPREV_WKYIELD"
     *             length="8"
     *         
     */
    public BigDecimal getWprevWkyield() {
        return this.wprevWkyield;
    }

    public void setWprevWkyield(BigDecimal wprevWkyield) {
        this.wprevWkyield = wprevWkyield;
    }

    /** 
     *            @hibernate.property
     *             column="WPREV_TIME"
     *             length="8"
     *         
     */
    public BigDecimal getWprevTime() {
        return this.wprevTime;
    }

    public void setWprevTime(BigDecimal wprevTime) {
        this.wprevTime = wprevTime;
    }

    /** 
     *            @hibernate.property
     *             column="WPREV_QUALITY"
     *             length="8"
     *         
     */
    public BigDecimal getWprevQuality() {
        return this.wprevQuality;
    }

    public void setWprevQuality(BigDecimal wprevQuality) {
        this.wprevQuality = wprevQuality;
    }

    /** 
     *            @hibernate.property
     *             column="WPREV_COST"
     *             length="8"
     *         
     */
    public BigDecimal getWprevCost() {
        return this.wprevCost;
    }

    public void setWprevCost(BigDecimal wprevCost) {
        this.wprevCost = wprevCost;
    }

    /** 
     *            @hibernate.property
     *             column="WPREV_PERFORMANCE"
     *             length="8"
     *         
     */
    public BigDecimal getWprevPerformance() {
        return this.wprevPerformance;
    }

    public void setWprevPerformance(BigDecimal wprevPerformance) {
        this.wprevPerformance = wprevPerformance;
    }

    /** 
     *            @hibernate.property
     *             column="WPREV_COMMENT"
     *             length="65535"
     *         
     */
    public String getWprevComment() {
        return this.wprevComment;
    }

    public void setWprevComment(String wprevComment) {
        this.wprevComment = wprevComment;
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
