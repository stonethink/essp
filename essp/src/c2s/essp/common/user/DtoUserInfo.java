package c2s.essp.common.user;

import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoUserInfo extends DtoUserBase {
    public DtoUserInfo() {
    }

    public DtoUserInfo(DtoUserBase user){
        super.setUserType(user.getUserType());
        super.setDomain(user.getDomain());
        super.setUserLoginId(user.getUserLoginId());
        super.setUserName(user.getUserName());
    }

    public String getChineseName() {
        return chineseName;
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

    public Date getInDate() {
        return inDate;
    }

    public String getJobCode() {
        return jobCode;
    }

    public String getOrganization() {
        return organization;
    }

    public String getOrgId() {
        return orgId;
    }

    public Date getOutDate() {
        return outDate;
    }

    public String getPhone() {
        return phone;
    }

    public String getPtype() {
        return ptype;
    }

    public String getTitle() {
        return title;
    }

    public String getUserID() {
        return userID;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
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

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    ///// HR Information, HR系统所记录的人员信息
    //用户在系统内的编号，由系统产生
    private String userID; //中文姓名
    private String chineseName;
    //职级编号
    private String jobCode;
    //属于组织结构,于OBS中定义
    private String orgId;
    private String organization;

//    //存放于AD中OU
//    private List ouList;

    //职位
    private String title;
    //职位类别
    private String ptype;

    private String enable; //
    private Date inDate; //到职日
    private Date outDate; //离职日

    //User的联系方式，联系方式
    private String phone;
    private String fax;
    private String email;

}
