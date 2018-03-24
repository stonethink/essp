package essp.tables;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="tc_timecard"
 *     
*/
public class TcTimecard implements Serializable {

    /** identifier field */
    private essp.tables.TcTimecardPK comp_id;

    /** persistent field */
    private Long rid;

    /** nullable persistent field */
    private String tmcProjCode;

    /** nullable persistent field */
    private String tmcProjName;

    /** nullable persistent field */
    private String tmcEmpCode;

    /** nullable persistent field */
    private String tmcEmpName;

    /** nullable persistent field */
    private String tmcEmpPositionType;

    /** nullable persistent field */
    private Date tmcEmpStart;

    /** nullable persistent field */
    private Date tmcEmpFinish;

    /** nullable persistent field */
    private BigDecimal tmcPersonalWorkHours;

    /** nullable persistent field */
    private BigDecimal tmcActualWorkHours;

    /** nullable persistent field */
    private BigDecimal tmcAllocatedWorkHours;

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
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public TcTimecard(essp.tables.TcTimecardPK comp_id, Long rid, String tmcProjCode, String tmcProjName, String tmcEmpCode, String tmcEmpName, String tmcEmpPositionType, Date tmcEmpStart, Date tmcEmpFinish, BigDecimal tmcPersonalWorkHours, BigDecimal tmcActualWorkHours, BigDecimal tmcAllocatedWorkHours, BigDecimal tmcAttenOffsetWork, BigDecimal tmcAttenOvertime, BigDecimal tmcAttenVacation, BigDecimal tmcAttenShiftAdjustment, BigDecimal tmcAttenPrivateLeave, BigDecimal tmcAttenSickLeave, BigDecimal tmcAttenAbsence, BigDecimal tmcAttenBreakingRules, String rst, Date rct, Date rut) {
        this.comp_id = comp_id;
        this.rid = rid;
        this.tmcProjCode = tmcProjCode;
        this.tmcProjName = tmcProjName;
        this.tmcEmpCode = tmcEmpCode;
        this.tmcEmpName = tmcEmpName;
        this.tmcEmpPositionType = tmcEmpPositionType;
        this.tmcEmpStart = tmcEmpStart;
        this.tmcEmpFinish = tmcEmpFinish;
        this.tmcPersonalWorkHours = tmcPersonalWorkHours;
        this.tmcActualWorkHours = tmcActualWorkHours;
        this.tmcAllocatedWorkHours = tmcAllocatedWorkHours;
        this.tmcAttenOffsetWork = tmcAttenOffsetWork;
        this.tmcAttenOvertime = tmcAttenOvertime;
        this.tmcAttenVacation = tmcAttenVacation;
        this.tmcAttenShiftAdjustment = tmcAttenShiftAdjustment;
        this.tmcAttenPrivateLeave = tmcAttenPrivateLeave;
        this.tmcAttenSickLeave = tmcAttenSickLeave;
        this.tmcAttenAbsence = tmcAttenAbsence;
        this.tmcAttenBreakingRules = tmcAttenBreakingRules;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public TcTimecard() {
    }

    /** minimal constructor */
    public TcTimecard(essp.tables.TcTimecardPK comp_id, Long rid) {
        this.comp_id = comp_id;
        this.rid = rid;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *         
     */
    public essp.tables.TcTimecardPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(essp.tables.TcTimecardPK comp_id) {
        this.comp_id = comp_id;
    }

    /** 
     *            @hibernate.property
     *             column="RID"
     *             length="8"
     *             not-null="true"
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
     *             column="TMC_PROJ_CODE"
     *             length="20"
     *         
     */
    public String getTmcProjCode() {
        return this.tmcProjCode;
    }

    public void setTmcProjCode(String tmcProjCode) {
        this.tmcProjCode = tmcProjCode;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_PROJ_NAME"
     *             length="100"
     *         
     */
    public String getTmcProjName() {
        return this.tmcProjName;
    }

    public void setTmcProjName(String tmcProjName) {
        this.tmcProjName = tmcProjName;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_EMP_CODE"
     *             length="20"
     *         
     */
    public String getTmcEmpCode() {
        return this.tmcEmpCode;
    }

    public void setTmcEmpCode(String tmcEmpCode) {
        this.tmcEmpCode = tmcEmpCode;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_EMP_NAME"
     *             length="100"
     *         
     */
    public String getTmcEmpName() {
        return this.tmcEmpName;
    }

    public void setTmcEmpName(String tmcEmpName) {
        this.tmcEmpName = tmcEmpName;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_EMP_POSITION_TYPE"
     *             length="150"
     *         
     */
    public String getTmcEmpPositionType() {
        return this.tmcEmpPositionType;
    }

    public void setTmcEmpPositionType(String tmcEmpPositionType) {
        this.tmcEmpPositionType = tmcEmpPositionType;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_EMP_START"
     *             length="10"
     *         
     */
    public Date getTmcEmpStart() {
        return this.tmcEmpStart;
    }

    public void setTmcEmpStart(Date tmcEmpStart) {
        this.tmcEmpStart = tmcEmpStart;
    }

    /** 
     *            @hibernate.property
     *             column="TMC_EMP_FINISH"
     *             length="10"
     *         
     */
    public Date getTmcEmpFinish() {
        return this.tmcEmpFinish;
    }

    public void setTmcEmpFinish(Date tmcEmpFinish) {
        this.tmcEmpFinish = tmcEmpFinish;
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
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcTimecard) ) return false;
        TcTimecard castOther = (TcTimecard) other;
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
