package server.essp.timecard.timecard.data;

import java.math.*;
import java.util.*;

import c2s.essp.timecard.timecard.*;


/**
 *
 * <p>Title: 员工工时记录</p>
 * <p>Description: 依项目记录一个员工在一个周期内的的工时记录，包括工作日和差勤记录</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class EmpTimeCard {
    private java.sql.Date weeklyStart;
    private java.sql.Date weeklyFinish;
    private int           projID;
    private String        projCode         = "";
    private String        projName         = "";
    private String        empID            = "";
    private String        empCode          = "";
    private String        empName          = "";
    private String        empPositionType = "";
    private java.sql.Date empStart;
    private java.sql.Date empFinish;

    //差勤记录
    private ArrayList attendances            = new ArrayList();
    private double    actualWorkHours;
    private double    allocatedWorkHours;
    private double    attenAbsence;
    private double    attenBreakingRules;
    private double    attenOffsetWork;
    private double    attenOverTime;
    private double    attenPrivateLeave;
    private double    attenShiftAdjustment;
    private double    attenSickLeave;
    private double    attenVacation;
    private double    personalWorkHours;

    private Long      rid;
    private String    op;

    public EmpTimeCard() {
    }

    public void setAttendances(ArrayList attendances) {
        this.attendances = attendances;
    }

    public ArrayList getAttendances() {
        return attendances;
    }

    public double getActualWorkHours() {
        return actualWorkHours;
    }

    public double getAttenAbsence() {
        return attenAbsence;
    }

    public double getAttenOffsetWork() {
        return attenOffsetWork;
    }

    public double getAttenPrivateLeave() {
        return attenPrivateLeave;
    }

    public double getAttenShiftAdjustment() {
        return attenShiftAdjustment;
    }

    public double getAttenSickLeave() {
        return attenSickLeave;
    }

    public double getAttenVacation() {
        return attenVacation;
    }

    public java.sql.Date getEmpFinish() {
        return empFinish;
    }

    public String getEmpID() {
        return empID;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpPositionType() {
        return empPositionType;
    }

    public java.sql.Date getEmpStart() {
        return empStart;
    }

    public double getPerSonalWorkHours() {
        return personalWorkHours;
    }

    public String getProjCode() {
        return projCode;
    }

    public int getProjID() {
        return projID;
    }

    public String getProjName() {
        return projName;
    }

    public java.sql.Date getWeeklyFinish() {
        return weeklyFinish;
    }

    public java.sql.Date getWeeklyStart() {
        return weeklyStart;
    }

    public void setActualWorkHours(double actualWorkHours) {
        this.actualWorkHours = actualWorkHours;
    }

    public void setAttenAbsence(double attenAbsence) {
        this.attenAbsence = attenAbsence;
    }

    public void setAttenBreakingRulles(double attenBreakingRulles) {
        this.attenBreakingRules = attenBreakingRulles;
    }

    public void setAttenOffsetWork(double attenOffsetWork) {
        this.attenOffsetWork = attenOffsetWork;
    }

    public void setAttenOvertime(double attenOvertime) {
        this.attenOverTime = attenOvertime;
    }

    public void setAttenShiftAdjustment(double attenShiftAdjustment) {
        this.attenShiftAdjustment = attenShiftAdjustment;
    }

    public void setAttenSickLeave(double attenSickLeave) {
        this.attenSickLeave = attenSickLeave;
    }

    public void setAttenVacation(double attenVaction) {
        this.attenVacation = attenVaction;
    }

    public void setEmpFinish(java.sql.Date empFinish) {
        this.empFinish = empFinish;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setEmpPositionType(String empPositionType) {
        this.empPositionType = empPositionType;
    }

    public void setEmpStart(java.sql.Date empStart) {
        this.empStart = empStart;
    }

    public void setPersonalWorkHours(double personalWorkHours) {
        this.personalWorkHours = personalWorkHours;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public void setProjID(int projID) {
        this.projID = projID;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public void setWeeklyFinish(java.sql.Date weeklyFinish) {
        this.weeklyFinish = weeklyFinish;
    }

    public void setWeeklyStart(java.sql.Date weekklyStart) {
        this.weeklyStart = weekklyStart;
    }

    public double getAttenBreakingRulles() {
        return attenBreakingRules;
    }

    public double getAttenOverTime() {
        return attenOverTime;
    }

    public void setAttenPrivateLeave(double attenPrivateLeave) {
        this.attenPrivateLeave = attenPrivateLeave;
    }

    public DtoTcTimecard getVDData() {
        DtoTcTimecard dtt = new DtoTcTimecard();

        try {
            dtt.setTmcWeeklyStart(weeklyStart);
            dtt.setTmcWeeklyFinish(weeklyFinish);
            dtt.setTmcProjId(new Long(projID));
            dtt.setTmcProjCode(projCode);
            dtt.setTmcProjName(projName);
            dtt.setTmcEmpId(empID);
            dtt.setTmcEmpCode(empCode);
            dtt.setTmcEmpName(empName);
            dtt.setTmcEmpPositionType(empPositionType);
            dtt.setTmcEmpStart(empStart);
            dtt.setTmcEmpFinish(empFinish);
            dtt.setTmcPersonalWorkHours(new BigDecimal(personalWorkHours));
            dtt.setTmcActualWorkHours(new BigDecimal(actualWorkHours));
            dtt.setTmcAllocatedWorkHours(new BigDecimal(allocatedWorkHours));
            dtt.setTmcAttenOffsetWork(new BigDecimal(attenOffsetWork));
            dtt.setTmcAttenOvertime(new BigDecimal(attenOverTime));
            dtt.setTmcAttenVacation(new BigDecimal(attenVacation));
            dtt.setTmcAttenShiftAdjustment(new BigDecimal(attenShiftAdjustment));
            dtt.setTmcAttenPrivateLeave(new BigDecimal(attenPrivateLeave));
            dtt.setTmcAttenSickLeave(new BigDecimal(attenSickLeave));
            dtt.setTmcAttenAbsence(new BigDecimal(attenAbsence));
            dtt.setTmcAttenBreakingRules(new BigDecimal(attenBreakingRules));
            if ( ! op.equals("") ) {
              dtt.setOp(op);
            }
            //dtt.setRid(RID);
        } catch (Exception ex) {
            ex.toString();
        }

        return dtt;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public double getAllocatedWorkHours() {
        this.allocatedWorkHours = this.actualWorkHours
                                    - (this.attenOverTime
                                    - this.attenVacation
                                    - this.attenShiftAdjustment
                                    - this.attenPrivateLeave
                                    - this.attenSickLeave
                                    - this.attenAbsence);

        return this.allocatedWorkHours;
    }

    public void setVDData(DtoTcTimecard dtt) {
        try {
            this.weeklyStart           = coverToSQLDate(dtt.getTmcWeeklyStart());
            this.weeklyFinish          = coverToSQLDate(dtt
                                                          .getTmcWeeklyFinish());
            this.projID                = dtt.getTmcProjId().intValue();
            this.projCode              = dtt.getTmcProjCode();
            this.projName              = dtt.getTmcProjName();
            this.empID                 = dtt.getTmcEmpId();
            this.empCode               = dtt.getTmcEmpCode();
            this.empName               = dtt.getTmcEmpName();
            this.empPositionType      = dtt.getTmcEmpPositionType();
            this.empStart              = coverToSQLDate(dtt.getTmcEmpStart());
            this.empFinish             = coverToSQLDate(dtt.getTmcEmpFinish());
            this.personalWorkHours    = dtt.getTmcPersonalWorkHours()
                                             .doubleValue();
            this.actualWorkHours      = dtt.getTmcActualWorkHours()
                                             .doubleValue();
            this.allocatedWorkHours   = dtt.getTmcAllocatedWorkHours()
                                             .doubleValue();
            this.attenOffsetWork      = dtt.getTmcAttenOffsetWork()
                                             .doubleValue();
            this.attenOverTime         = dtt.getTmcAttenOvertime().doubleValue();
            this.attenVacation         = dtt.getTmcAttenVacation().doubleValue();
            this.attenShiftAdjustment = dtt.getTmcAttenShiftAdjustment()
                                             .doubleValue();
            this.attenPrivateLeave    = dtt.getTmcAttenPrivateLeave()
                                             .doubleValue();
            this.attenSickLeave       = dtt.getTmcAttenSickLeave()
                                             .doubleValue();
            this.attenAbsence          = dtt.getTmcAttenAbsence().doubleValue();
            this.attenBreakingRules   = dtt.getTmcAttenBreakingRules()
                                             .doubleValue();
            this.op                     = dtt.getOp();

            //this.RID = dtt.getRid();
        } catch (Exception ex) {
            ex.toString();
        }
    }

    /**
     * 日报记录判断，只有是同一员工，同一项目，并且在同一周期内的才算是同一日报数据
     * @param eptc EmpTimeCard
     * @return boolean
     */
    public boolean isSameEmp(EmpTimeCard eptc) {
        if (this.empID.equals(eptc.empID) && (this.projID == eptc.projID)
                && this.weeklyStart.equals(eptc.weeklyStart)
                && this.weeklyFinish.equals(eptc.weeklyFinish)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将相同的日报记录进行合并到自身
     * @param eptc 日报汇总记录
     */
    public void merge(EmpTimeCard eptc) {
        if (isSameEmp(eptc)) {
            this.mergeDateTime(eptc);
            this.mergeWorkDays(eptc);
        }
    }

    /**
     * 合并时间数据
     * @param eptc EmpTimeCard
     */
    public void mergeDateTime(EmpTimeCard eptc) {
        long iNumber = eptc.getEmpStart().getTime() - this.empStart.getTime();

        if (iNumber < 0) {
            this.empStart = eptc.getEmpStart();
        }

        iNumber = eptc.getEmpFinish().getTime() - this.empFinish.getTime();

        if (iNumber > 0) {
            this.empFinish = eptc.getEmpFinish();
        }
    }

    /**
     * 合并工作时间
     * @param eptc EmpTimeCard
     */
    public void mergeWorkDays(EmpTimeCard eptc) {
        if (this.actualWorkHours == this.personalWorkHours) {
            this.actualWorkHours = eptc.actualWorkHours;
        }

        this.personalWorkHours = eptc.personalWorkHours;
    }

    //有可能是Timestamp类型，需要转捥
    public static java.sql.Date coverToSQLDate(Object obj) {
        java.sql.Date retSQLDate = null;

        if (obj instanceof java.sql.Timestamp) {
            retSQLDate = new java.sql.Date(((java.sql.Timestamp) obj).getTime());
        } else if (obj instanceof java.sql.Date) {
            retSQLDate = (java.sql.Date) obj;
        } else if (obj instanceof java.util.Date) {
            retSQLDate = new java.sql.Date(((java.util.Date) obj).getTime());
        }

        return retSQLDate;
    }

    public void sumAttendance() {
        for (int i = 0; i < this.attendances.size(); i++) {
            Attendance atten = (Attendance) this.attendances.get(i);

            if (atten.attendTYPE.toLowerCase().equals("overtime")) {
                this.attenOverTime = this.attenOverTime
                                      + atten.getWeeklyTime();
            } else if (atten.attendTYPE.toLowerCase().equals("shift-adjustment")) {
                this.attenShiftAdjustment = this.attenShiftAdjustment
                                              + atten.getWeeklyTime();
            } else if (atten.attendTYPE.toLowerCase().equals("private leave")) {
                this.attenPrivateLeave = this.attenPrivateLeave
                                           + atten.getWeeklyTime();
            } else if (atten.attendTYPE.toLowerCase().equals("paid leave")) {
                this.attenVacation = this.attenVacation
                                      + atten.getWeeklyTime();
            } else if (atten.attendTYPE.toLowerCase().equals("annual-leave")) {
                this.attenVacation = this.attenVacation
                                      + atten.getWeeklyTime();
            } else if (atten.attendTYPE.toLowerCase().equals("sick leave")) {
                this.attenSickLeave = this.attenSickLeave
                                        + atten.getWeeklyTime();
            }
        }
    }

    public Long getRID() {
        return rid;
    }

    public void setRID(Long rid) {
        this.rid = rid;
    }

  public String getOP() {
    return op;
  }

  public void setOP(String op) {
    this.op = op;
  }
}
