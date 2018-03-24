package c2s.essp.timesheet.tsremind;

import java.util.List;

public class DtoRmMailContent {
	
	private List unFillList;
	private List unSubmitList;
	private List approveList;
	public List getApproveList() {
		return approveList;
	}
	public void setApproveList(List approveList) {
		this.approveList = approveList;
	}
	public List getUnFillList() {
		return unFillList;
	}
	public void setUnFillList(List unFillList) {
		this.unFillList = unFillList;
	}
	public List getUnSubmitList() {
		return unSubmitList;
	}
	public void setUnSubmitList(List unSubmitList) {
		this.unSubmitList = unSubmitList;
	}

}
