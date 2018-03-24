package c2s.essp.pwm.wp;

import java.io.Serializable;
import c2s.dto.DtoBase;

/**
 *        @hibernate.class
 *         table="essp_sys_account_t"
 *
 */
public class DtoAccountInfo extends DtoBase implements Serializable {

  /** identifier field */
  private Long id;

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
  private Long accountType;

  /** nullable persistent field */
  private String accountTypeName;

  /** full constructor */
  public DtoAccountInfo(Long id, String accountCode, String accountName, String organization,
                            String manager, Long accountType, String accountTypeName) {
    this.id = id;
    this.accountCode = accountCode;
    this.accountName = accountName;
    this.organization = organization;
    this.manager = manager;
    this.accountType = accountType;
    this.accountTypeName = accountTypeName;
  }

  /** default constructor */
  public DtoAccountInfo() {
    }

  /** minimal constructor */
  public DtoAccountInfo(Long id, String accountCode, String accountName, String organization,
                            String manager, Long accountType) {
    this.id = id;
    this.accountCode = accountCode;
    this.accountName = accountName;
    this.organization = organization;
    this.manager = manager;
    this.accountType = accountType;
    //this.accountTypeName = accountTypeName;
  }

  /**
   *            @hibernate.id
   *             generator-class="assigned"
   *             type="java.lang.Long"
   *             column="ID"
   *
   */
  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
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
  public Long getAccountType() {
    return this.accountType;
  }

  public void setAccountType(Long accountType) {
    this.accountType = accountType;
  }

  public String getAccountTypeName() {
    return this.accountTypeName;
  }

    public String getManagerName() {
        return managerName;
    }

    public void setAccountTypeName(String accountTypeName) {
    this.accountTypeName = accountTypeName;
  }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}
