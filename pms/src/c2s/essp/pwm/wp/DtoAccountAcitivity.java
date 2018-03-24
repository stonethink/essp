package c2s.essp.pwm.wp;

import java.io.Serializable;
import c2s.dto.DtoBase;

/**
 *        @hibernate.class
 *         table="essp_sys_account_t"
 *
 */
public class DtoAccountAcitivity extends DtoBase implements Serializable {

  /** identifier field */
  private Long projectId;

  /** persistent field */
  private String accountCode;

  /** persistent field */
  private String accountName;

  /** nullable persistent field */
  private String organization;

  /** nullable persistent field */
  private String manager;
  private String managerName;

  /** nullable persistent field */
  private String accountType;

  /** nullable persistent field */
  private String accountTypeName;

  //Acitivty Info
  /** identifier field */
  private Long activityId;

  /** nullable persistent field */
  private String taskId;

  /** nullable persistent field */
  private String clnitem;


  /** default constructor */
  public DtoAccountAcitivity() {
    }

  /**
   *            @hibernate.id
   *             generator-class="assigned"
   *             type="java.lang.Long"
   *             column="ID"
   *
   */
  public Long getProjectId() {
    return this.projectId;
  }

  public void setProjectId(Long id) {
    this.projectId = id;
  }

  /**
   *            @hibernate.property
   *             column="ACCOUNT_CODE"
   *             length="50"
   *             not-null="true"
   *
   */
  public String getAccountCode() {
    return this.accountCode;
  }

  public void setAccountCode(String accountCode) {
    this.accountCode = accountCode;
  }

  /**
   *            @hibernate.property
   *             column="ACCOUNT_NAME"
   *             length="50"
   *             not-null="true"
   *
   */
  public String getAccountName() {
    return this.accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  /**
   *            @hibernate.property
   *             column="ORGANIZATION"
   *             length="20"
   *
   */
  public String getOrganization() {
    return this.organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  /**
   *            @hibernate.property
   *             column="MANAGER"
   *             length="20"
   *
   */
  public String getManager() {
    return this.manager;
  }

  public void setManager(String manager) {
    this.manager = manager;
  }

  /**
   *            @hibernate.property
   *             column="ACCOUNT_TYPE"
   *             length="10"
   *
   */
  public String getAccountType() {
    return this.accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountTypeName() {
    return this.accountTypeName;
  }

  public void setAccountTypeName(String accountTypeName) {
    this.accountTypeName = accountTypeName;
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
   *             column="CLNITEM"
   *             length="65535"
   *
   */
  public String getClnitem() {
    return this.clnitem;
  }

    public String getTaskId() {
        return taskId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setClnitem(String clnitem) {
    this.clnitem = clnitem;
  }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}
