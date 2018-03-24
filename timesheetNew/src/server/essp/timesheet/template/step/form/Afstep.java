package server.essp.timesheet.template.step.form;

import org.apache.struts.action.ActionForm;

public class Afstep extends ActionForm {
	
	private String rid;
	private String tempRid;
	private String seqNum;
	private String procName;
	private String procWt;
	private String procDescr;
	private String planStartOffset;
	private String planFinishOffset;
	private String category;
	private String isCorp;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getIsCorp() {
		return isCorp;
	}
	public void setIsCorp(String isCorp) {
		this.isCorp = isCorp;
	}
	public String getPlanFinishOffset() {
		return planFinishOffset;
	}
	public void setPlanFinishOffset(String planFinishOffset) {
		this.planFinishOffset = planFinishOffset;
	}
	public String getPlanStartOffset() {
		return planStartOffset;
	}
	public void setPlanStartOffset(String planStartOffset) {
		this.planStartOffset = planStartOffset;
	}
	public String getProcDescr() {
		return procDescr;
	}
	public void setProcDescr(String procDescr) {
		this.procDescr = procDescr;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getProcWt() {
		return procWt;
	}
	public void setProcWt(String procWt) {
		this.procWt = procWt;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getTempRid() {
		return tempRid;
	}
	public void setTempRid(String tempRid) {
		this.tempRid = tempRid;
	}
	
	
	
}
