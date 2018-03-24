/*
 * Created on 2007-12-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.humanbase.viewbean;

import java.util.Date;
import java.util.List;

import com.wits.util.comDate;

public class VbHumanBase {
	private Long rid;

	private String employeeId;

	private String unitCode;

	private String englishName;

	private String chineseName;

	private String title;

	private String rank;

	private String resManagerId;

	private String resManagerName;

	private String email;

	private Date inDate;

	private Date outDate;

	private String site;

	private Long attributeGroupRid;

	private String attribute;
	
	private String hrAttribute;

	private Date effectiveDate;

	private String operation;
	
	private String isDirect;
	
	private String isFormal;
	
	private String canUpdate;

	public String getIsFormal() {
		return isFormal;
	}

	public void setIsFormal(String isFormal) {
		this.isFormal = isFormal;
	}

	public String getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(String isDirect) {
		this.isDirect = isDirect;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
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

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
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

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
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

	public Long getAttributeGroupRid() {
		return attributeGroupRid;
	}

	public void setAttributeGroupRid(Long attributeGroupRid) {
		this.attributeGroupRid = attributeGroupRid;
	}

	public String getHrAttribute() {
		return hrAttribute;
	}

	public void setHrAttribute(String hrAttribute) {
		this.hrAttribute = hrAttribute;
	}
	
	public String getIsFormalName() {
		return formalValue2Name(this.getIsFormal());
	}
	
	private static String formalValue2Name(String value) {
		if("1".equals(value)) {
			return "Y";
		} else if("0".equals(value)) {
			return "N";
		} else {
			return "";
		}
	}
	
	public String getIsDirectName() {
		return directValue2Name(this.getIsDirect());
	}
	
	private static String directValue2Name(String value) {
		if("D".equals(value)) {
			return "Direct";
		} else if("I".equals(value)) {
			return "Indirect";
		} else {
			return "";
		}
	}

	public String getCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(String canUpdate) {
		this.canUpdate = canUpdate;
	}
	
	public String getOnJob(){
		Date today = new Date();
		if(this.getOutDate() == null || this.getOutDate().after(today)) {
			return "Y";
		} else if(this.getOutDate().before(today) || this.getOutDate().compareTo(today) == 0) {
			return "N";
		}
		return "";
	}
}
