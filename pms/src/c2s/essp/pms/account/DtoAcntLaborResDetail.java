package c2s.essp.pms.account;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoAcntLaborResDetail extends DtoBase {
    private Long rid;
    private Long resRid;
    private Date beginDate;
    private Date endDate;
    private Long acntRid;
    private String loginId;
    private Long seq;
    private Long percent;
    private Double hour;
    public Long getAcntRid() {
        return acntRid;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Double getHour() {
        return hour;
    }

    public String getLoginId() {
        return loginId;
    }

    public Long getPercent() {
        return percent;
    }

    public Long getRid() {
        return rid;
    }

    public Long getSeq() {
        return seq;
    }

    public Long getResRid() {
        return resRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setHour(Double hour) {
        this.hour = hour;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setPercent(Long percent) {
        this.percent = percent;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setResRid(Long resRid) {
        this.resRid = resRid;
    }

}
