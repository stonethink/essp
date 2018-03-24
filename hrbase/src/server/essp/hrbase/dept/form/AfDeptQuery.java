/*
 * Created on 2007-12-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.dept.form;

import java.util.List;
import org.apache.struts.action.ActionForm;

public class AfDeptQuery extends ActionForm{

    private String unitCode;
    private String unitName;
    private String unitFullName;
    private String parentUnitCode;
    private String applicantId;
    private String applicantName;
    private String dmId;
    private String dmName;
    private String tsId;
    private String tsName;
    private String bdId;
    private String bdName;
    private String belongBd;
    private String operation;
    private String belongSite;
    private String acntAttribute;
    private List parentUnitList;
    private List bdList;
    private List siteList;
    private String effectiveBegin;
    private String effectiveEnd;
    private String costBelongUnit;
    private List costBelongUnitList;
    private String isBl;
    
    public String getBlLable() {
    	if("1".equals(isBl)){
    		return "Y";
    	}
		return "N";
	}
    public String getIsBl() {
		return isBl;
	}
	public void setIsBl(String isBl) {
		this.isBl = isBl;
	}
	public List getBdList() {
        return bdList;
    }
    public void setBdList(List bdList) {
        this.bdList = bdList;
    }
    public List getSiteList() {
        return siteList;
    }
    public void setSiteList(List siteList) {
        this.siteList = siteList;
    }
    public String getBelongBd() {
        return belongBd;
    }
    public void setBelongBd(String belongBd) {
        this.belongBd = belongBd;
    }
    public String getAcntAttribute() {
        return acntAttribute;
    }
    public void setAcntAttribute(String acntAttribute) {
        this.acntAttribute = acntAttribute;
    }
    public String getBdId() {
        return bdId;
    }
    public void setBdId(String bdId) {
        this.bdId = bdId;
    }
    public String getBdName() {
        return bdName;
    }
    public void setBdName(String bdName) {
        this.bdName = bdName;
    }
    public String getBelongSite() {
        return belongSite;
    }
    public void setBelongSite(String belongSite) {
        this.belongSite = belongSite;
    }
    public String getDmId() {
        return dmId;
    }
    public void setDmId(String dmId) {
        this.dmId = dmId;
    }
    public String getDmName() {
        return dmName;
    }
    public void setDmName(String dmName) {
        this.dmName = dmName;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getParentUnitCode() {
        return parentUnitCode;
    }
    public void setParentUnitCode(String parentUnitCode) {
        this.parentUnitCode = parentUnitCode;
    }
    public String getTsId() {
        return tsId;
    }
    public void setTsId(String tsId) {
        this.tsId = tsId;
    }
    public String getTsName() {
        return tsName;
    }
    public void setTsName(String tsName) {
        this.tsName = tsName;
    }
    public String getUnitCode() {
        return unitCode;
    }
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    public String getUnitFullName() {
        return unitFullName;
    }
    public void setUnitFullName(String unitFullName) {
        this.unitFullName = unitFullName;
    }
    public String getUnitName() {
        return unitName;
    }
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    public String getApplicantId() {
        return applicantId;
    }
    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }
    public String getApplicantName() {
        return applicantName;
    }
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    public List getParentUnitList() {
        return parentUnitList;
    }
    public void setParentUnitList(List parentUnitList) {
        this.parentUnitList = parentUnitList;
    }

    public String getCostBelongUnit() {
        return costBelongUnit;
    }
    public void setCostBelongUnit(String costBelongUnit) {
        this.costBelongUnit = costBelongUnit;
    }
    public List getCostBelongUnitList() {
        return costBelongUnitList;
    }
    public void setCostBelongUnitList(List costBelongUnitList) {
        this.costBelongUnitList = costBelongUnitList;
    }
    public String getEffectiveBegin() {
        return effectiveBegin;
    }
    public void setEffectiveBegin(String effectiveBegin) {
        this.effectiveBegin = effectiveBegin;
    }
    public String getEffectiveEnd() {
        return effectiveEnd;
    }
    public void setEffectiveEnd(String effectiveEnd) {
        this.effectiveEnd = effectiveEnd;
    }
}
