package c2s.essp.timesheet.rmmaint;

import c2s.dto.DtoBase;

public class DtoRmMaint extends DtoBase {
	
	public final static String DTO_LOGINIDS = "Ts_DtoLoginIds";
	public final static String DTO_RESULTS = "Ts_DtoResults";
	public final static String DTO = "Ts_Dto";
	public final static String DTO_LOGINID = "Ts_DtoLoginId";
	public final static String DTO_RMID = "Ts_DtoRMId";
	private Long rid;
	private String loginId;
	private String name;
	private String dept;
	private String rmId;
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public String getRmId() {
		return rmId;
	}
	public void setRmId(String rmId) {
		this.rmId = rmId;
	}

}
