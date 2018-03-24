package server.essp.hrbase.dept.viewbean;

import java.util.Date;

import c2s.dto.DtoBase;

public class VbSyncUnitBase extends DtoBase {
	
	public static final String OPERATION_INSERT = "Insert";
	public static final String OPERATION_UPDATE = "Update";
	public static final String OPERATION_DELETE = "Delete";
	
	private Long rid;
    private String unitCode;
    private String unitName;
    private String unitFullName;
    private String parentUnitCode;
    private String dmId;
    private String dmName;
    private String tsId;
    private String tsName;
    private String bdId;
    private String bdName;
    private String applicantId;
    private String applicantName;
    private String belongBd;
    private Date effectiveDate;
    private String operation;
    private String belongSite;
    private String costBelongUnit;
    private String acntAttribute;
    private String isBl;
    private Date rct;
    private String rcu;
    private Date rut;
    private String ruu;
	public Date getRut() {
		return rut;
	}
	public void setRut(Date rut) {
		this.rut = rut;
	}
	public String getRuu() {
		return ruu;
	}
	public void setRuu(String ruu) {
		this.ruu = ruu;
	}
	public Date getRct() {
		return rct;
	}
	public void setRct(Date rct) {
		this.rct = rct;
	}
	public String getRcu() {
		return rcu;
	}
	public void setRcu(String rcu) {
		this.rcu = rcu;
	}
	public String getAcntAttribute() {
		return acntAttribute;
	}
	public void setAcntAttribute(String acntAttribute) {
		this.acntAttribute = acntAttribute;
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
	public String getBelongBd() {
		return belongBd;
	}
	public void setBelongBd(String belongBd) {
		this.belongBd = belongBd;
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
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
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
	public long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
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
	public String getCostBelongUnit() {
		return costBelongUnit;
	}
	public void setCostBelongUnit(String costBelongUnit) {
		this.costBelongUnit = costBelongUnit;
	}
	public String getIsBl() {
		return isBl;
	}
	public void setIsBl(String isBl) {
		this.isBl = isBl;
	}

}
