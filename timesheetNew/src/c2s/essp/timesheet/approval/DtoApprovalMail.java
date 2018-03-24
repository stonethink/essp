package c2s.essp.timesheet.approval;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class DtoApprovalMail {
    private String approvaler;
    private String acntId;
    private String begin;
    private String end;
    private String reason;
    public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setAcntId(String acntId) {
        this.acntId = acntId;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setApprovaler(String approvaler) {
        this.approvaler = approvaler;
    }

    public String getAcntId() {
        return acntId;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public String getApprovaler() {
        return approvaler;
    }

}
