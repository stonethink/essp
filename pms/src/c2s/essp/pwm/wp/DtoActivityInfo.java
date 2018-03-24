package c2s.essp.pwm.wp;

import java.io.Serializable;
import c2s.dto.DtoBase;

/**
 *        @hibernate.class
 *         table="essp_sys_account_t"
 *
 */
public class DtoActivityInfo extends DtoBase implements Serializable {

  //Acitivty Info
  /** identifier field */
  private Long activityId;

  /** nullable persistent field */
  private String taskId;

  /** nullable persistent field */
  private String clnitem;


  /** default constructor */
  public DtoActivityInfo() {
    }

  /** minimal constructor */
  public DtoActivityInfo(Long activityId, String schno, String clnitem) {
    this.activityId = activityId;
    this.taskId = schno;
    this.clnitem = clnitem;
  }


  /**
   *            @hibernate.id
   *             generator-class="assigned"
   *             type="java.lang.Long"
   *             column="ID"
   *
   */
  public Long getActivityId() {
    return this.activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

  /**
   *            @hibernate.property
   *             column="SCHNO"
   *             length="42"
   *
   */
  public String getSchno() {
    return this.taskId;
  }

  public void setSchno(String schno) {
    this.taskId = schno;
  }

  /**
   *            @hibernate.property
   *             column="CLNITEM"
   *             length="65535"
   *
   */
  public String getClnitem() {
    return this.clnitem;
  }

  public void setClnitem(String clnitem) {
    this.clnitem = clnitem;
  }
}
