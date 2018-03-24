/*
 * Created on 2007-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.humanbase.form;

import org.apache.struts.action.ActionForm;

public class AfHumanBaseLog extends ActionForm {

	private String rid;

	private String baseRid;

	private String employeeId;

	private String unitCode;

	private String englishName;

	private String chineseName;

	private String title;

	private String rank;

	private String resManagerId;

	private String resManagerName;

	private String email;

	private String inDate;

	private String outDate;

	private String site;

	private String attribute;

	private String effectiveDate;

	private String operation;

	private String status;
	
	private String attributeGroupRid;
	
	private String isDirect;

	public String getAttributeGroupRid() {
		return attributeGroupRid;
	}

	public void setAttributeGroupRid(String attributeGroupRid) {
		this.attributeGroupRid = attributeGroupRid;
	}

	public String getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(String isDirect) {
		this.isDirect = isDirect;
	}

	public String getBaseRid() {
		return baseRid;
	}

	public void setBaseRid(String baseRid) {
		this.baseRid = baseRid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getResManagerId() {
		return resManagerId;
	}

	public void setResManagerId(String resManagerId) {
		this.resManagerId = resManagerId;
	}

	public String getResManagerName() {
		return resManagerName;
	}

	public void setResManagerName(String resManagerName) {
		this.resManagerName = resManagerName;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
