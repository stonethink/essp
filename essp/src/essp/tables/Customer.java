package essp.tables;

/**
 * @author Administrator
 * @hibernate.class table="essp_sys_project_customer"
 *
 */
public class Customer {
  private String id;
  private String name;
  private String organization;
  private String title;
  private String isManager;
  private String phone;
  private String fax;
  private String email;
  private String userName;
  private String password;
  private String accountid;
  private String enable;

  /**
   * @hibernate.property column="accountid" type="string" length="20"
   * @return String
   */
  public String getAccountid() {
    return accountid;
  }

  /**
   * @hibernate.property column="email" type="string" length="50"
   * @return String
   */
  public String getEmail() {
    return email;
  }

  /**
   * @hibernate.property column="enable" type="string" length="4"
   * @return String
   */
  public String getEnable() {
    return enable;
  }

  /**
   * @hibernate.property column="fax" type="string" length="50"
   * @return String
   */
  public String getFax() {
    return fax;
  }

  /**
   * @hibernate.id generator-class="assigned"
   * column="id"
   * @return String
   */
  public String getId() {
    return id;
  }

  /**
   * @hibernate.property column="is_manager" type="string" length="4"
   * @return String
   */
  public String getIsManager() {
    return isManager;
  }

  /**
   * @hibernate.property column="name" type="string" length="100"
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * @hibernate.property column="organization" type="string" length="20"
   * @return String
   */
  public String getOrganization() {
    return organization;
  }

  /**
   * @hibernate.property column="password" type="string" length="50"
   * @return String
   */
  public String getPassword() {
    return password;
  }

  /**
   * @hibernate.property column="phone" type="string" length="50"
   * @return String
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @hibernate.property column="title" type="string" length="255"
   * @return String
   */
  public String getTitle() {
    return title;
  }

  /**
   * @hibernate.property column="username" type="string" length="50"
   * @return String
   */
  public String getUserName() {
    return userName;
  }

  public void setAccountid(String accountid) {
    this.accountid = accountid;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setEnable(String enable) {
    this.enable = enable;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setIsManager(String isManager) {
    this.isManager = isManager;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
