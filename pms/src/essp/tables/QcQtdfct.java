package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="qc_qtdfct"
 *     
*/
public class QcQtdfct implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long qtactvId;

    /** nullable persistent field */
    private Long testRound;

    /** nullable persistent field */
    private String injectedPhase;

    /** nullable persistent field */
    private Long defectNum;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public QcQtdfct(Long rid, Long qtactvId, Long testRound, String injectedPhase, Long defectNum, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.qtactvId = qtactvId;
        this.testRound = testRound;
        this.injectedPhase = injectedPhase;
        this.defectNum = defectNum;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public QcQtdfct() {
    }

    /** minimal constructor */
    public QcQtdfct(Long rid, Long qtactvId) {
        this.rid = rid;
        this.qtactvId = qtactvId;
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
     *             column="QTACTV_ID"
     *             length="8"
     *             not-null="true"
     *         
     */
    public Long getQtactvId() {
        return this.qtactvId;
    }

    public void setQtactvId(Long qtactvId) {
        this.qtactvId = qtactvId;
    }

    /** 
     *            @hibernate.property
     *             column="TEST_ROUND"
     *             length="8"
     *         
     */
    public Long getTestRound() {
        return this.testRound;
    }

    public void setTestRound(Long testRound) {
        this.testRound = testRound;
    }

    /** 
     *            @hibernate.property
     *             column="INJECTED_PHASE"
     *             length="50"
     *         
     */
    public String getInjectedPhase() {
        return this.injectedPhase;
    }

    public void setInjectedPhase(String injectedPhase) {
        this.injectedPhase = injectedPhase;
    }

    /** 
     *            @hibernate.property
     *             column="DEFECT_NUM"
     *             length="8"
     *         
     */
    public Long getDefectNum() {
        return this.defectNum;
    }

    public void setDefectNum(Long defectNum) {
        this.defectNum = defectNum;
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
        if ( !(other instanceof QcQtdfct) ) return false;
        QcQtdfct castOther = (QcQtdfct) other;
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
