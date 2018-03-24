package essp.tables;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="qc_qtgoal"
 *
*/
public class QcQtgoal implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long projectId;

    /** persistent field */
    private String seq;

    /** nullable persistent field */
    private String deliveryProduct;

    /** nullable persistent field */
    private String productUnit;

    /** nullable persistent field */
    private BigDecimal productSizePlan;

    /** nullable persistent field */
    private BigDecimal qualityRatePlan;

    /** nullable persistent field */
    private BigDecimal productSizeAct;

    /** nullable persistent field */
    private BigDecimal qualityRateAct;

    /** nullable persistent field */
    private Long defectAct;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private boolean sumSize;

    /** nullable persistent field */
    private boolean sumDefects;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;

    /** full constructor */
    public QcQtgoal(Long rid, Long projectId, String seq, String deliveryProduct, String productUnit, BigDecimal productSizePlan, BigDecimal qualityRatePlan, BigDecimal productSizeAct, BigDecimal qualityRateAct, Long defectAct, String description, boolean sumSize, boolean sumDefects, Date rct, Date rut, String rst) {
        this.rid = rid;
        this.projectId = projectId;
        this.seq = seq;
        this.deliveryProduct = deliveryProduct;
        this.productUnit = productUnit;
        this.productSizePlan = productSizePlan;
        this.qualityRatePlan = qualityRatePlan;
        this.productSizeAct = productSizeAct;
        this.qualityRateAct = qualityRateAct;
        this.defectAct = defectAct;
        this.description = description;
        this.sumSize = sumSize;
        this.sumDefects = sumDefects;
        this.rct = rct;
        this.rut = rut;
        this.rst = rst;
    }

    /** default constructor */
    public QcQtgoal() {
    }

    /** minimal constructor */
    public QcQtgoal(Long rid, Long projectId, String seq) {
        this.rid = rid;
        this.projectId = projectId;
        this.seq = seq;
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
     *             column="SEQ"
     *             length="50"
     *             not-null="true"
     *
     */
    public String getSeq() {
        return this.seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    /**
     *            @hibernate.property
     *             column="DELIVERY_PRODUCT"
     *             length="50"
     *
     */
    public String getDeliveryProduct() {
        return this.deliveryProduct;
    }

    public void setDeliveryProduct(String deliveryProduct) {
        this.deliveryProduct = deliveryProduct;
    }

    /**
     *            @hibernate.property
     *             column="PRODUCT_UNIT"
     *             length="50"
     *
     */
    public String getProductUnit() {
        return this.productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    /**
     *            @hibernate.property
     *             column="PRODUCT_SIZE_PLAN"
     *             length="8"
     *
     */
    public BigDecimal getProductSizePlan() {
        return this.productSizePlan;
    }

    public void setProductSizePlan(BigDecimal productSizePlan) {
        this.productSizePlan = productSizePlan;
    }

    /**
     *            @hibernate.property
     *             column="QUALITY_RATE_PLAN"
     *             length="8"
     *
     */
    public BigDecimal getQualityRatePlan() {
        return this.qualityRatePlan;
    }

    public void setQualityRatePlan(BigDecimal qualityRatePlan) {
        this.qualityRatePlan = qualityRatePlan;
    }

    /**
     *            @hibernate.property
     *             column="PRODUCT_SIZE_ACT"
     *             length="8"
     *
     */
    public BigDecimal getProductSizeAct() {
        return this.productSizeAct;
    }

    public void setProductSizeAct(BigDecimal productSizeAct) {
        this.productSizeAct = productSizeAct;
    }

    /**
     *            @hibernate.property
     *             column="QUALITY_RATE_ACT"
     *             length="8"
     *
     */
    public BigDecimal getQualityRateAct() {
        return this.qualityRateAct;
    }

    public void setQualityRateAct(BigDecimal qualityRateAct) {
        this.qualityRateAct = qualityRateAct;
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
     *             column="DESCRIPTION"
     *             length="65535"
     *
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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
     *             column="SUM_DEFECT"
     *             length="1"
     *
     */
    public boolean isSumDefects() {
           return sumDefects;
    }

    public void setSumDefects(boolean sumDefects) {
        this.sumDefects = sumDefects;
    }
    /**
     *            @hibernate.property
     *             column="SUM_SIZE"
     *             length="1"
     *
     */
    public boolean isSumSize() {
           return sumSize;
    }

    public void setSumSize(boolean sumSize) {
        this.sumSize = sumSize;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof QcQtgoal) ) return false;
        QcQtgoal castOther = (QcQtgoal) other;
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
