package server.essp.timecard.timecard.data;

import java.math.*;
import java.util.*;

import c2s.essp.timecard.timecard.*;


/**
 *
 * <p>Title: 项目组的工时表记录</p>
 * <p>Description: 记录项目组的工时表信息，包括项目组中员工的工时表信息</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 * @see EmpTimeCard
 */
public class ProjectTimeCard {
    public static final String  STATUS_SUBMIT = "1";
    public static final String  STATUS_OPEN = "0";
    private java.sql.Date weeklyStart;
    private java.sql.Date weeklyFinish;
    private int           projID;
    private String        projSubmitStatus = STATUS_OPEN;

    //以下为项目下员工的汇总数据
    //项目组员工数据
    private ArrayList empTimeCard;
    private double    actualWorkHours;
    private double    allocatedWorkHours;
    private double    attenAbsence;
    private double    attenBreakingRulles;
    private double    attenOffsetWork;
    private double    attenOverTime;
    private double    attenPrivateLeave;
    private double    attenShiftAdjustMent;
    private double    attenSickLeave;
    private double    attenVacation;
    private double    recongnizedWorkhours;
    private double    personalWorkHours;

    private Long      rid;
    private String    op;

    public ProjectTimeCard() {
    }

    public ArrayList getEmpTimeCard() {
        return empTimeCard;
    }

    public int getProjID() {
        return projID;
    }

    public String getProjSubmitStatus() {
        return projSubmitStatus;
    }

    public double getRecongnizedWorkHours() {
        return recongnizedWorkhours;
    }

    public java.sql.Date getWeeklyFinish() {
        return weeklyFinish;
    }

    public java.sql.Date getWeeklyStart() {
        return weeklyStart;
    }

    public void setEmpTimeCard(ArrayList empTimeCard) {
        this.empTimeCard = empTimeCard;
    }

    public void setProjID(int projID) {
        this.projID = projID;
    }

    public void setProjSubmitStatus(String projSubmitStatus) {
        this.projSubmitStatus = projSubmitStatus;
    }

    public void setRecongnizedWorkHours(double recongnizedWorkHours) {
        this.recongnizedWorkhours = recongnizedWorkHours;
    }

    public void setWeeklyFinish(java.sql.Date weeklyFinish) {
        this.weeklyFinish = weeklyFinish;
    }

    public void setWeeklyStart(java.sql.Date weeklyStart) {
        this.weeklyStart = weeklyStart;
    }

    public void getSum() {
        if (this.empTimeCard == null) {
            return;
        }

        for (int i = 0; i < this.empTimeCard.size(); i++) {
            //差勤汇总
            this.attenAbsence          = this.attenAbsence
                                          + ((EmpTimeCard) this.empTimeCard.get(i))
                                            .getAttenAbsence();
            this.attenBreakingRulles   = this.attenBreakingRulles
                                          + ((EmpTimeCard) this.empTimeCard.get(i))
                                            .getAttenBreakingRulles();
            this.attenOffsetWork      = this.attenOffsetWork
                                          + ((EmpTimeCard) this.empTimeCard.get(i))
                                            .getAttenOffsetWork();
            this.attenOverTime         = this.attenOverTime
                                          + ((EmpTimeCard) this.empTimeCard.get(i))
                                            .getAttenOverTime();
            this.attenPrivateLeave    = this.attenPrivateLeave
                                          + ((EmpTimeCard) this.empTimeCard.get(i))
                                            .getAttenPrivateLeave();
            this.attenShiftAdjustMent = this.attenShiftAdjustMent
                                          + ((EmpTimeCard) this.empTimeCard.get(i))
                                            .getAttenShiftAdjustment();
            this.attenSickLeave       = this.attenSickLeave
                                          + ((EmpTimeCard) this.empTimeCard.get(i))
                                            .getAttenSickLeave();
            this.attenVacation         = this.attenVacation
                                          + ((EmpTimeCard) this.empTimeCard.get(i))
                                            .getAttenVacation();

            //工作时间汇总
            this.personalWorkHours  = this.personalWorkHours
                                        + ((EmpTimeCard) this.empTimeCard.get(i))
                                          .getPerSonalWorkHours();
            this.actualWorkHours    = this.actualWorkHours
                                        + ((EmpTimeCard) this.empTimeCard.get(i))
                                          .getActualWorkHours();
            this.allocatedWorkHours = this.allocatedWorkHours
                                        + ((EmpTimeCard) this.empTimeCard.get(i))
                                          .getAllocatedWorkHours();
        }
    }

    public DtoTcTimecard getVDData() {
        DtoTcTimecard dtt = new DtoTcTimecard();

        try {
            dtt.setTmcWeeklyStart(weeklyStart);
            dtt.setTmcWeeklyFinish(weeklyFinish);
            dtt.setTmcProjId(new Long(projID));
            dtt.setTmcPersonalWorkHours(new BigDecimal(personalWorkHours));
            dtt.setTmcActualWorkHours(new BigDecimal(actualWorkHours));
            dtt.setTmcAllocatedWorkHours(new BigDecimal(allocatedWorkHours));
            dtt.setTmcAttenOffsetWork(new BigDecimal(attenOffsetWork));
            dtt.setTmcAttenOvertime(new BigDecimal(attenOverTime));
            dtt.setTmcAttenVacation(new BigDecimal(attenVacation));
            dtt.setTmcAttenShiftAdjustment(new BigDecimal(attenShiftAdjustMent));
            dtt.setTmcAttenPrivateLeave(new BigDecimal(attenPrivateLeave));
            dtt.setTmcAttenSickLeave(new BigDecimal(attenSickLeave));
            dtt.setTmcAttenAbsence(new BigDecimal(attenAbsence));
            dtt.setTmcAttenBreakingRules(new BigDecimal(attenBreakingRulles));

            if ( ! op.equals("") ) {
              dtt.setOp(op);
            }

            //dtt.setRid(RID);
        } catch (Exception ex) {
            ex.toString();
        }

        return dtt;
    }

    public double getPersonalWorkHours() {
        return personalWorkHours;
    }

    public void setPersonalWorkHours(double personalWorkHours) {
        this.personalWorkHours = personalWorkHours;
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
