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
public class QcQtgoalRelease implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long qtgoalId;

    /** persistent field */
    private String vesion;

    /** nullable persistent field */
    private Date releaseDate;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Date statDate;

    /** nullable persistent field */
    private Long size;

    /** nullable persistent field */
    private Long problems;

    /** nullable persistent field */
    private Long defects;

    /** nullable persistent field */
    private BigDecimal qualificationRate;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;

    /** full constructor */
    public QcQtgoalRelease(Long rid, Long qtgoalId, String vesion, Date releaseDate, String description,  Date statDate, Long size, Long problems, Long defects, BigDecimal qualificationRate, Date rct, Date rut, String rst) {
        this.rid = rid;
        this.qtgoalId = qtgoalId;
        this.vesion = vesion;
        this.releaseDate = releaseDate;
        this.description = description;
        this.statDate = statDate;
        this.size = size;
        this.problems = problems;
        this.defects = defects;
        this.qualificationRate = qualificationRate;
        this.rct = rct;
        this.rut = rut;
        this.rst = rst;
    }

    /** default constructor */
    public QcQtgoalRelease() {
    }

    /** minimal constructor */
    public QcQtgoalRelease(Long rid, Long qtgoalId) {
        this.rid = rid;
        this.qtgoalId = qtgoalId;
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
     *            @hibernate.id
     *             type="java.lang.Long"
     *             column="QTGOAL_ID"
     *
     */
    public Long getQtgoalId() {
      return qtgoalId;
    }

    public void setQtgoalId(Long qtgoalId) {
      this.qtgoalId = qtgoalId;
    }

    /**
     *            @hibernate.id
     *             type="java.lang.String"
     *             column="VESION"
     *
     */
    public String getVesion() {
        return vesion;
    }

     public void setVesion(String vesion) {
       this.vesion = vesion;
    }

    /**
     *            @hibernate.id
     *             type="java.util.Date"
     *             column="RELEASE_DATE"
     *
     */
    public Date getReleaseDate() {
       return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
      this.releaseDate = releaseDate;
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
     *             column="STAT_DATE"
     *             length="10"
     *
     */
    public Date getStatDate() {
      return statDate;
    }

    public void setStatDate(Date statDate) {
       this.statDate = statDate;
    }

    /**
     *            @hibernate.property
     *             column="SIZE"
     *             length="8"
     *
     */
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    /**
     *            @hibernate.property
     *             column="PROBLEMS"
     *             length="8"
     *
     */
    public Long getProblems() {
       return problems;
    }

    public void setProblems(Long problems) {
      this.problems = problems;
    }

    /**
     *            @hibernate.property
     *             column="DEFECTS"
     *             length="8"
     *
     */
    public Long getDefects() {
      return defects;
    }

    public void setDefects(Long defects) {
       this.defects = defects;
    }

    /**
     *            @hibernate.property
     *             column="QUALIFICATION_RATE"
     *             length="8"
     *
     */
    public BigDecimal getQualificationRate() {
        return qualificationRate;
    }

    public void setQualificationRate(BigDecimal qualificationRate) {
        this.qualificationRate = qualificationRate;
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
