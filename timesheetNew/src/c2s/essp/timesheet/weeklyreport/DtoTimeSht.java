package c2s.essp.timesheet.weeklyreport;

import java.util.Date;

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
public class DtoTimeSht extends c2s.dto.DtoBase {
    private Long tsId;
    private Long rsrcId;
    private String dailyFlag;
    private String statusCode;
    private Long userId;
    private Date lastRecvDate;
    private Date statusDate;
    private String tsNotes;
    public String getDailyFlag() {
        return dailyFlag;
    }

    public Date getLastRecvDate() {
        return lastRecvDate;
    }

    public Long getRsrcId() {
        return rsrcId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public Long getTsId() {
        return tsId;
    }

    public String getTsNotes() {
        return tsNotes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setDailyFlag(String dailyFlag) {
        this.dailyFlag = dailyFlag;
    }

    public void setLastRecvDate(Date lastRecvDate) {
        this.lastRecvDate = lastRecvDate;
    }

    public void setRsrcId(Long rsrcId) {
        this.rsrcId = rsrcId;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public void setTsId(Long tsId) {
        this.tsId = tsId;
    }

    public void setTsNotes(String tsNotes) {
        this.tsNotes = tsNotes;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
