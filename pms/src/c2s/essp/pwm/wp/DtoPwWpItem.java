package c2s.essp.pwm.wp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.DtoBase;

/**
 *        @hibernate.class
 *         table="pw_wp"
 *
 */

public class DtoPwWpItem extends DtoBase implements Serializable {
    /**PWP��״̬*/
    public static final String ASSIGNED_STATUS = "Assigned";
    public static final String FINISH_STATUS = "Finish";
    public static final String CLOSED_STATUS = "Closed";
    public static final String REJECT_STATUS = "Reject";
    public static final String REWORK_STATUS = "Rework";
    public static final String CANCEL_STATUS = "Cancel";

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long projectId;
    private String projectCode;
    private String projectName;

    private Long activityId;
    private String activityCode;
    private String activityName;
    /** persistent field */
    private int wpSequence;

    /** nullable persistent field */
    private String wpCode;

    /** persistent field */
    private String wpName;

    /** nullable persistent field */
    private Date wpPlanStart;

    /** nullable persistent field */
    private Date wpPlanFihish;

    /** nullable persistent field */
    private Date wpActStart;

    /** nullable persistent field */
    private Date wpActFinish;

    /** nullable persistent field */
    private String wpStatus;

    /** nullable persistent field */
    private BigDecimal wpCmpltrate;

    private String wpWorker;
    private String wpWorkerName;

    private String pwpflag="0";                          //0:Ϊpwp;1:Ϊaccount

    public DtoPwWpItem() {
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Date getWpActFinish() {
        return wpActFinish;
    }

    public void setWpActFinish(Date wpActFinish) {
        this.wpActFinish = wpActFinish;
    }

    public Date getWpActStart() {
        return wpActStart;
    }

    public void setWpActStart(Date wpActStart) {
        this.wpActStart = wpActStart;
    }

    public String getWpCode() {
        return wpCode;
    }

    public void setWpCode(String wpCode) {
        this.wpCode = wpCode;
    }

    public String getWpName() {
        return wpName;
    }

    public void setWpName(String wpName) {
        this.wpName = wpName;
    }

    public Date getWpPlanFihish() {
        return wpPlanFihish;
    }

    public void setWpPlanFihish(Date wpPlanFihish) {
        this.wpPlanFihish = wpPlanFihish;
    }

    public Date getWpPlanStart() {
        return wpPlanStart;
    }

    public void setWpPlanStart(Date wpPlanStart) {
        this.wpPlanStart = wpPlanStart;
    }

    public int getWpSequence() {
        return wpSequence;
    }

    public void setWpSequence(int wpSequence) {
        this.wpSequence = wpSequence;
    }
    public String getPwpflag() {
        return pwpflag;
    }
    public void setPwpflag(String pwpflag) {
        this.pwpflag = pwpflag;
    }
    public String getProjectCode() {
        return projectCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectName() {
        return projectName;
    }
    public String getWpStatus() {
        return wpStatus;
    }
    public void setWpStatus(String wpStatus) {
        this.wpStatus = wpStatus;
    }
  public Long getActivityId() {
    return activityId;
  }
  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }
  public String getActivityName() {
    return activityName;
  }
  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }
  public String getWpWorker() {
    return wpWorker;
  }
  public String getWpWorkerName() {
    return wpWorkerName;
  }
  public void setWpWorker(String wpWorker) {
    this.wpWorker = wpWorker;
  }
  public void setWpWorkerName(String wpWorkerName) {
    this.wpWorkerName = wpWorkerName;
  }
  public BigDecimal getWpCmpltrate() {
    return wpCmpltrate;
  }

    public String getActivityCode() {
        return activityCode;
    }

    public void setWpCmpltrate(BigDecimal wpCmpltrate) {
    this.wpCmpltrate = wpCmpltrate;
  }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

}
