package server.essp.timesheet.template.step.form;

public class VbStep {
	
	private Long rid;
	private Long tempRid;
	private Long seqNum;
	private String procName;
	private Double procWt;
	private Long planStartOffset;
	private Long planFinishOffset;
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
	public Long getPlanFinishOffset() {
		return planFinishOffset;
	}
	public void setPlanFinishOffset(Long planFinishOffset) {
		this.planFinishOffset = planFinishOffset;
	}
	public Long getPlanStartOffset() {
		return planStartOffset;
	}
	public void setPlanStartOffset(Long planStartOffset) {
		this.planStartOffset = planStartOffset;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public Double getProcWt() {
		return procWt;
	}
	public void setProcWt(Double procWt) {
		this.procWt = procWt;
	}
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public Long getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}
	public Long getTempRid() {
		return tempRid;
	}
	public void setTempRid(Long tempRid) {
		this.tempRid = tempRid;
	}
	
	

}
