package c2s.essp.timesheet.tsdates;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoTsDates extends DtoBase {
	
	

	private Date startDate;
	private Date endDate;
	private String createWay;
	private int endsOn;
	
	public String getCreateWay() {
		return createWay;
	}
	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}
	public int getEndsOn() {
		return endsOn;
	}
	public void setEndsOn(int endsOn) {
		this.endsOn = endsOn;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
