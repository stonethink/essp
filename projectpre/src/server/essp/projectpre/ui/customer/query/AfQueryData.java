package server.essp.projectpre.ui.customer.query;

import org.apache.struts.action.ActionForm;

public class AfQueryData extends ActionForm { 
    //	���Ŵ���
	private String groupId;
    //	�ͻ����
	private String customerId;
    //	BD��
	private String belongBd;
	//  site��
	private String belongSite;
	//  �ͻ�������
	private String class_;
	//  �ͻ�������
	private String country;
	// �ͻ����ʣ�����or��˾
	private String attribute;
	// �Ƿ�Ҫ�����ӹ�˾
	private String addSub;
	//�͑����Q
	private String short_;
	/**
     * setGroupId
     * @param groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
     *getGroupId 
     * @return String
	 */
	public String getGroupId() {
		return groupId;
	}
	
	/**
     * setCustomerId
     * @param customerId
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
     * getCustomerId
     * @return String
	 */
	public String getCustomerId() {
		return customerId;
	}
	
	/**
     * setBelongBd
     * @param belongBd
	 */
	public void setBelongBd(String belongBd) {
		this.belongBd = belongBd;
	}
	/**
     * getBelongBd
     * @return String
	 */
	public String getBelongBd() {
		return belongBd;
	}
	
	/**
     * setBelongSite
     * @param belongSite
	 */
	public void setBelongSite(String belongSite) {
		this.belongSite = belongSite;
	}
	/**
     * getBelongSite
     * @return String
	 */
	public String getBelongSite() {
		return belongSite;
	}
	
	/**
     * setClass_
     * @param class_
	 */
	public void setClass_(String class_) {
		this.class_ = class_;
	}
 	/**
     * getClass_
     * @return String
 	 */
	public String getClass_() {
		return class_;
	}
	
	/**
     *  setCountry
     * @param country
	 */
	public void setCountry(String country) {
		this.country = country;		
	}
	/**
     * getCountry
     * @return String
	 */
	public String getCountry() {
		return country;
	}
	
	/**
     * setAttribute
     * @param attribute
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	/**
     * getAttribute
     * @return String
	 */
	public String getAttribute() {
		return attribute;
	}
	
	/**
     * setAddSub
     * @param addSub
	 */
	public void setAddSub(String addSub) {
		this.addSub = addSub;
	}
	/**
     * getAddSub
     * @return String
	 */
	public String getAddSub() {
		return addSub;
	}
	public String getShort_() {
		return short_;
	}
	public void setShort_(String short_) {
		this.short_ = short_;
	}

}
