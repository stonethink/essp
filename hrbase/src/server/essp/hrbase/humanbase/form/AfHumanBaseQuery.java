package server.essp.hrbase.humanbase.form;

import org.apache.struts.action.ActionForm;

public class AfHumanBaseQuery extends ActionForm {
	private String employee;
	private String rm;
	private String site;
	private String unitCode;
	private String email;
	private String inDateBegin;
	private String inDateEnd;
	private String outDateBegin;
	private String outDateEnd;
	private String attribute;
	private String rank;
	private String title;
	private String isDirect;
	private String formal;
	private String onJob;
	
	public String getOnJob() {
		return onJob;
	}
	public void setOnJob(String onJob) {
		this.onJob = onJob;
	}
	public String getIsDirect() {
		return isDirect;
	}
	public void setIsDirect(String isDirect) {
		this.isDirect = isDirect;
	}
	public String getFormal() {
		return formal;
	}
	public void setFormal(String formal) {
		this.formal = formal;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getInDateBegin() {
		return inDateBegin;
	}
	public void setInDateBegin(String inDateBegin) {
		this.inDateBegin = inDateBegin;
	}
	public String getInDateEnd() {
		return inDateEnd;
	}
	public void setInDateEnd(String inDateEnd) {
		this.inDateEnd = inDateEnd;
	}
	public String getOutDateBegin() {
		return outDateBegin;
	}
	public void setOutDateBegin(String outDateBegin) {
		this.outDateBegin = outDateBegin;
	}
	public String getOutDateEnd() {
		return outDateEnd;
	}
	public void setOutDateEnd(String outDateEnd) {
		this.outDateEnd = outDateEnd;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
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
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
