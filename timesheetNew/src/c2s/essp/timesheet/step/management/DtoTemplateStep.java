package c2s.essp.timesheet.step.management;

import c2s.dto.DtoBase;

public class DtoTemplateStep extends DtoBase {
	public static String KEY_TEMPLATE_STEP_LIST="TemplateStepList";
	private Long rid;
	private Long seqNum;
	private String procName;
	private Double procWt;
	private String procDescr;
	private Long planStartOffset;
	private Long planFinishOffset;
	private String category;
	private Boolean isCorp;

	public Long getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
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

	public String getProcDescr() {
		return procDescr;
	}

	public void setProcDescr(String procDescr) {
		this.procDescr = procDescr;
	}

	public Long getPlanStartOffset() {
		return planStartOffset;
	}

	public void setPlanStartOffset(Long planStartOffset) {
		this.planStartOffset = planStartOffset;
	}

	public Long getPlanFinishOffset() {
		return planFinishOffset;
	}

	public void setPlanFinishOffset(Long planFinishOffset) {
		this.planFinishOffset = planFinishOffset;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getIsCorp() {
		return isCorp;
	}

	public void setIsCorp(Boolean isCorp) {
		this.isCorp = isCorp;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

}
