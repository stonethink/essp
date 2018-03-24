package server.essp.projectpre.ui.customer.query;

import java.util.Date;

public class VbQuery {

    
   /**
    *客户性质
    */
    private String attribute;
    /**
     * 客户编码
     */
    private String customerId;
    /**
     * 集团代码
     */
    private String groupId;
    /**
     * 简称
     */
    private String short_;
    /**
     * 所属Bd
     */
    private String belongBd;
    /**
     * 所属SITE
     */
    private String belongSite;
    /**
     * 客户分类码
     */
    private String class_;
    /**
     * 客户国别码
     */
    private String country;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * getAttribute
     * @return String
     */
    public String getAttribute() {
        return attribute;
    }
    /**
     * setAttribute
     * @param attribute
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    public String getBelongBd() {
        return belongBd;
    }
    /**
     * setBelongBd
     * @param belongBd
     */
    public void setBelongBd(String belongBd) {
        this.belongBd = belongBd;
    }
    /**
     * getBelongSite
     * @return String
     */
    public String getBelongSite() {
        return belongSite;
    }
    /**
     * setBelongSite
     * @param belongSite
     */
    public void setBelongSite(String belongSite) {
        this.belongSite = belongSite;
    }
    /**
     * getClass_
     * @return String
     */
    public String getClass_() {
        return class_;
    }
    /**
     * setClass_
     * @param class_
     */
    public void setClass_(String class_) {
        this.class_ = class_;
    }
    /**
     * getCountry
     * @return String
     */
    public String getCountry() {
        return country;
    }
    /**
     * setCountry
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * getCreateDate
     * @return Date
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * setCreateDate
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     *  getCustomerId
     * @return String
     */
    public String getCustomerId() {
        return customerId;
    }
    /**
     * setCustomerId
     * @param customerId
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    /**
     * getGroupId
     * @return String
     */
    public String getGroupId() {
        return groupId;
    }
    /**
     * setGroupId
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    /**
     * getShort_
     * @return String
     */
    public String getShort_() {
        return short_;
    }
    /**
     * setShort_
     * @param short_
     */
    public void setShort_(String short_) {
        this.short_ = short_;
    }
   
}
