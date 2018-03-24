package c2s.essp.pms.account;

import c2s.dto.DtoBase;

public class DtoAcntKeyPersonnel extends DtoBase {
    /**
     * Key personnels的所有类型
     */
    public static final String[] KEY_PERSON_TYPES = {"Customer","Sales","Manager","Team Member"};
    /**
     * Enable的可选值
     */
    public static final String ENABLE = "Yes";
    public static final String DISABLE = "No";

    private Long rid;
    private String typeName;
    private String loginId;
    private String userName;
    private String organization;
    private String title;
    private String phone;
    private String fax;
    private String email;
    private String enable;
    private Long acntRid;
    private String password;
    private boolean isExisted = false;

    public Long getAcntRid() {
        return acntRid;
    }

    public String getEmail() {
        return email;
    }

    public String getEnable() {
        return enable;
    }

    public String getFax() {
        return fax;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getOrganization() {
        return organization;
    }

    public String getPhone() {
        return phone;
    }

    public Long getRid() {
        return rid;
    }

    public String getTitle() {
        return title;
    }

    public String getUserName() {
        return userName;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isExisted(){
        return isExisted;
    }
    public void setIsExisted(boolean b){
        this.isExisted = b;
    }
}
