package c2s.essp.tc.nonattend;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoNonattend extends DtoBase {
   private Long rid;
   private String loginId;
   private Date date;
   private Date dateFrom;
   private Date dateTo;
   private Date timeFrom;
   private Date timeTo;
   private Double totalHours;
   private String remark;
    public Date getDate() {
        return date;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getRemark() {
        return remark;
    }

    public Long getRid() {
        return rid;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }
}
