package essp.tables;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="tc_tms"
 *     
*/
public class TcTm implements Serializable {

    /** identifier field */
    private essp.tables.TcTmPK comp_id;

    /** persistent field */
    private int rid;

    /** nullable persistent field */
    private String tmcProjSubmitStatus;

    /** nullable persistent field */
    private BigDecimal tmcActualWorkHours;

    /** nullable persistent field */
    private BigDecimal tmcAllocatedWorkHours;

    /** nullable persistent field */
    private BigDecimal tmcPersonalWorkHours;

    /** nullable persistent field */
    private BigDecimal tmcAttenOffsetWork;

    /** nullable persistent field */
    private BigDecimal tmcAttenOvertime;

    /** nullable persistent field */
    private BigDecimal tmcAttenVacation;

    /** nullable persistent field */
    private BigDecimal tmcAttenShiftAdjustment;

    /** nullable persistent field */
    private BigDecimal tmcAttenPrivateLeave;

    /** nullable persistent field */
    private BigDecimal tmcAttenSickLeave;

    /** nullable persistent field */
    private BigDecimal tmcAttenAbsence;

    /** nullable persistent field */
    private BigDecimal tmcAttenBreakingRules;

    /** nullable persistent field */
    private BigDecimal tmcRecongnizedWorkhours;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** full constructor */
    public TcTm(essp.tables.TcTmPK comp_id, int rid, String tmcProjSubmitStatus, BigDecimal tmcActualWorkHours, BigDecimal tmcAllocatedWorkHours, BigDecimal tmcPersonalWorkHours, BigDecimal tmcAttenOffsetWork, BigDecimal tmcAttenOvertime, BigDecimal tmcAttenVacation, BigDecimal tmcAttenShiftAdjustment, BigDecimal tmcAttenPrivateLeave, BigDecimal tmcAttenSickLeave, BigDecimal tmcAttenAbsence, BigDecimal tmcAttenBreakingRules, BigDecimal tmcRecongnizedWorkhours, Date rut, String rst, Date rct) {
        this.comp_id = comp_id;
        this.rid = rid;
        this.tmcProjSubmitStatus = tmcProjSubmitStatus;
        this.tmcActualWorkHours = tmcActualWorkHours;
        this.tmcAllocatedWorkHours = tmcAllocatedWorkHours;
        this.tmcPersonalWorkHours = tmcPersonalWorkHours;
        this.tmcAttenOffsetWork = tmcAttenOffsetWork;
        this.tmcAttenOvertime = tmcAttenOvertime;
        this.tmcAttenVacation = tmcAttenVacation;
        this.tmcAttenShiftAdjustment = tmcAttenShiftAdjustment;
        this.tmcAttenPrivateLeave = tmcAttenPrivateLeave;
        this.tmcAttenSickLeave = tmcAttenSickLeave;
        this.tmcAttenAbsence = tmcAttenAbsence;
        this.tmcAttenBreakingRules = tmcAttenBreakingRules;
        this.tmcRecongnizedWorkhours = tmcRecongnizedWorkhours;
        this.rut = rut;
        this.rst = rst;
        this.rct = rct;
    }

    /** default constructor */
    public TcTm() {
    }

    /** minimal constructor */
    public TcTm(essp.tables.TcTmPK comp_id, int rid) {
        this.comp_id = comp_id;
        this.rid = rid;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *         
     */
    public essp.tables.TcTmPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(essp.tables.TcTmPK comp_id) {
        this.comp_id = comp_id;
    }

    /** 
     *            @hibernate.property
     *             column="RID"
     *             unique="true"
     *             length="8"
     *             not-null="true"
     *         
     */
    public int getRid() {
        return this.rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_PROJ_SUBMIT_STATUS"
     *             length="1"
     *         
     */
    public String getTmcProjSubmitStatus() {
        return this.tmcProjSubmitStatus;
    }

    public void setTmcProjSubmitStatus(String tmcProjSubmitStatus) {
        this.tmcProjSubmitStatus = tmcProjSubmitStatus;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_ACTUAL_WORK_HOURS"
     *             length="8"
     *         
     */
    public BigDecimal getTmcActualWorkHours() {
        return this.tmcActualWorkHours;
    }

    public void setTmcActualWorkHours(BigDecimal tmcActualWorkHours) {
        this.tmcActualWorkHours = tmcActualWorkHours;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_ALLOCATED_WORK_HOURS"
     *             length="8"
     *         
     */
    public BigDecimal getTmcAllocatedWorkHours() {
        return this.tmcAllocatedWorkHours;
    }

    public void setTmcAllocatedWorkHours(BigDecimal tmcAllocatedWorkHours) {
        this.tmcAllocatedWorkHours = tmcAllocatedWorkHours;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_PERSONAL_WORK_HOURS"
     *             length="8"
     *         
     */
    public BigDecimal getTmcPersonalWorkHours() {
        return this.tmcPersonalWorkHours;
    }

    public void setTmcPersonalWorkHours(BigDecimal tmcPersonalWorkHours) {
        this.tmcPersonalWorkHours = tmcPersonalWorkHours;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_ATTEN_OFFSET_WORK"
     *             length="8"
     *         
     */
    public BigDecimal getTmcAttenOffsetWork() {
        return this.tmcAttenOffsetWork;
    }

    public void setTmcAttenOffsetWork(BigDecimal tmcAttenOffsetWork) {
        this.tmcAttenOffsetWork = tmcAttenOffsetWork;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_ATTEN_OVERTIME"
     *             length="8"
     *         
     */
    public BigDecimal getTmcAttenOvertime() {
        return this.tmcAttenOvertime;
    }

    public void setTmcAttenOvertime(BigDecimal tmcAttenOvertime) {
        this.tmcAttenOvertime = tmcAttenOvertime;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_ATTEN_VACATION"
     *             length="8"
     *         
     */
    public BigDecimal getTmcAttenVacation() {
        return this.tmcAttenVacation;
    }

    public void setTmcAttenVacation(BigDecimal tmcAttenVacation) {
        this.tmcAttenVacation = tmcAttenVacation;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_ATTEN_SHIFT_ADJUSTMENT"
     *             length="8"
     *         
     */
    public BigDecimal getTmcAttenShiftAdjustment() {
        return this.tmcAttenShiftAdjustment;
    }

    public void setTmcAttenShiftAdjustment(BigDecimal tmcAttenShiftAdjustment) {
        this.tmcAttenShiftAdjustment = tmcAttenShiftAdjustment;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_ATTEN_PRIVATE_LEAVE"
     *             length="8"
     *         
     */
    public BigDecimal getTmcAttenPrivateLeave() {
        return this.tmcAttenPrivateLeave;
    }

    public void setTmcAttenPrivateLeave(BigDecimal tmcAttenPrivateLeave) {
        this.tmcAttenPrivateLeave = tmcAttenPrivateLeave;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_ATTEN_SICK_LEAVE"
     *             length="8"
     *         
     */
    public BigDecimal getTmcAttenSickLeave() {
        return this.tmcAttenSickLeave;
    }

    public void setTmcAttenSickLeave(BigDecimal tmcAttenSickLeave) {
        this.tmcAttenSickLeave = tmcAttenSickLeave;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_ATTEN_ABSENCE"
     *             length="8"
     *         
     */
    public BigDecimal getTmcAttenAbsence() {
        return this.tmcAttenAbsence;
    }

    public void setTmcAttenAbsence(BigDecimal tmcAttenAbsence) {
        this.tmcAttenAbsence = tmcAttenAbsence;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_ATTEN_BREAKING_RULES"
     *             length="8"
     *         
     */
    public BigDecimal getTmcAttenBreakingRules() {
        return this.tmcAttenBreakingRules;
    }

    public void setTmcAttenBreakingRules(BigDecimal tmcAttenBreakingRules) {
        this.tmcAttenBreakingRules = tmcAttenBreakingRules;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_RECONGNIZED_WORKHOURS"
     *             length="8"
     *         
     */
    public BigDecimal getTmcRecongnizedWorkhours() {
        return this.tmcRecongnizedWorkhours;
    }

    public void setTmcRecongnizedWorkhours(BigDecimal tmcRecongnizedWorkhours) {
        this.tmcRecongnizedWorkhours = tmcRecongnizedWorkhours;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcTm) ) return false;
        TcTm castOther = (TcTm) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}
