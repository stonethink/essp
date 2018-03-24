package c2s.essp.timecard.timecard;

import c2s.dto.DtoBase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;


/**
 *        @hibernate.class
 *         table="tc_timecard"
 *
 */
public class DtoTcTimecard extends DtoBase implements Serializable {
    /** identifier field */
    private Long rid;

    /** persistent field */
    private Date tmcWeeklyStart;

    /** persistent field */
    private Date tmcWeeklyFinish;

    /** persistent field */
    private Long tmcProjId;

    /** nullable persistent field */
    private String tmcProjCode;

    /** nullable persistent field */
    private String tmcProjName;

    /** nullable persistent field */
    private String tmcEmpId;

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
    private List       dtoPwWkitemList;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public DtoTcTimecard(Long       rid,
                         Date       tmcWeeklyStart,
                         Date       tmcWeeklyFinish,
                         Long       tmcProjId,
                         String     tmcProjCode,
                         String     tmcProjName,
                         String     tmcEmpId,
                         String     tmcEmpCode,
                         String     tmcEmpName,
                         String     tmcEmpPositionType,
                         Date       tmcEmpStart,
                         Date       tmcEmpFinish,
                         BigDecimal tmcPersonalWorkHours,
                         BigDecimal tmcActualWorkHours,
                         BigDecimal tmcAllocatedWorkHours,
                         BigDecimal tmcAttenOffsetWork,
                         BigDecimal tmcAttenOvertime,
                         BigDecimal tmcAttenVacation,
                         BigDecimal tmcAttenShiftAdjustment,
                         BigDecimal tmcAttenPrivateLeave,
                         BigDecimal tmcAttenSickLeave,
                         BigDecimal tmcAttenAbsence,
                         BigDecimal tmcAttenBreakingRules,
                         String     rst,
                         Date       rct,
                         Date       rut) {
        this.rid                     = rid;
        this.tmcWeeklyStart          = tmcWeeklyStart;
        this.tmcWeeklyFinish         = tmcWeeklyFinish;
        this.tmcProjId               = tmcProjId;
        this.tmcProjCode             = tmcProjCode;
        this.tmcProjName             = tmcProjName;
        this.tmcEmpId                = tmcEmpId;
        this.tmcEmpCode              = tmcEmpCode;
        this.tmcEmpName              = tmcEmpName;
        this.tmcEmpPositionType      = tmcEmpPositionType;
        this.tmcEmpStart             = tmcEmpStart;
        this.tmcEmpFinish            = tmcEmpFinish;
        this.tmcPersonalWorkHours    = tmcPersonalWorkHours;
        this.tmcActualWorkHours      = tmcActualWorkHours;
        this.tmcAllocatedWorkHours   = tmcAllocatedWorkHours;
        this.tmcAttenOffsetWork      = tmcAttenOffsetWork;
        this.tmcAttenOvertime        = tmcAttenOvertime;
        this.tmcAttenVacation        = tmcAttenVacation;
        this.tmcAttenShiftAdjustment = tmcAttenShiftAdjustment;
        this.tmcAttenPrivateLeave    = tmcAttenPrivateLeave;
        this.tmcAttenSickLeave       = tmcAttenSickLeave;
        this.tmcAttenAbsence         = tmcAttenAbsence;
        this.tmcAttenBreakingRules   = tmcAttenBreakingRules;
        this.rst                     = rst;
        this.rct                     = rct;
        this.rut                     = rut;
    }

    /** default constructor */
    public DtoTcTimecard() {
    }

    /** minimal constructor */
    public DtoTcTimecard(Long rid,
                         Date tmcWeeklyStart,
                         Date tmcWeeklyFinish,
                         Long tmcProjId) {
        this.rid             = rid;
        this.tmcWeeklyStart  = tmcWeeklyStart;
        this.tmcWeeklyFinish = tmcWeeklyFinish;
        this.tmcProjId       = tmcProjId;
    }

    /**
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Integer"
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
     *             column="TMC_WEEKLY_START"
     *             length="10"
     *             not-null="true"
     *
     */
    public Date getTmcWeeklyStart() {
        return this.tmcWeeklyStart;
    }

    public void setTmcWeeklyStart(Date tmcWeeklyStart) {
        this.tmcWeeklyStart = tmcWeeklyStart;
    }

    /**
     *            @hibernate.property
     *             column="TMC_WEEKLY_FINISH"
     *             length="10"
     *             not-null="true"
     *
     */
    public Date getTmcWeeklyFinish() {
        return this.tmcWeeklyFinish;
    }

    public void setTmcWeeklyFinish(Date tmcWeeklyFinish) {
        this.tmcWeeklyFinish = tmcWeeklyFinish;
    }

    /**
     *            @hibernate.property
     *             column="TMC_PROJ_ID"
     *             length="8"
     *             not-null="true"
     *
     */
    public Long getTmcProjId() {
        return this.tmcProjId;
    }

    public void setTmcProjId(Long tmcProjId) {
        this.tmcProjId = tmcProjId;
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
     *             column="TMC_EMP_ID"
     *             length="20"
     *
     */
    public String getTmcEmpId() {
        return this.tmcEmpId;
    }

    public void setTmcEmpId(String tmcEmpId) {
        this.tmcEmpId = tmcEmpId;
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

    public List getDtoPwWkitemList() {
        return dtoPwWkitemList;
    }

    public void setDtoPwWkitemList(List dtoPwWkitemList) {
        this.dtoPwWkitemList = dtoPwWkitemList;
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
        return new ToStringBuilder(this).append("rid", getRid()).toString();
    }

    public boolean equals(Object other) {
        if (!(other instanceof DtoTcTimecard)) {
            return false;
        }

        DtoTcTimecard castOther = (DtoTcTimecard) other;

        return new EqualsBuilder().append(this.getRid(), castOther.getRid())
                                  .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getRid()).toHashCode();
    }
}
