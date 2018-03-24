package db.essp.tc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.math.BigDecimal;


/** @author Hibernate CodeGenerator */
public class TcStandardTimecard implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String userId;

    /** nullable persistent field */
    private Date beginPeriod;

    /** nullable persistent field */
    private Date endPeriod;

    /** nullable persistent field */
    private Date realBeginPeriod;

    /** nullable persistent field */
    private Date realEndPeriod;

    /** nullable persistent field */
    private BigDecimal timecardNum;

    /** full constructor */
    public TcStandardTimecard(Long rid, String userId, Date beginPeriod, Date endPeriod, Date realBeginPeriod, Date realEndPeriod, BigDecimal timecardNum) {
        this.rid = rid;
        this.userId = userId;
        this.beginPeriod = beginPeriod;
        this.endPeriod = endPeriod;
        this.realBeginPeriod = realBeginPeriod;
        this.realEndPeriod = realEndPeriod;
        this.timecardNum = timecardNum;
    }

    /** default constructor */
    public TcStandardTimecard() {
    }

    /** minimal constructor */
    public TcStandardTimecard(Long rid) {
        this.rid = rid;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getBeginPeriod() {
        return this.beginPeriod;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public Date getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Date getRealBeginPeriod() {
        return this.realBeginPeriod;
    }

    public void setRealBeginPeriod(Date realBeginPeriod) {
        this.realBeginPeriod = realBeginPeriod;
    }

    public Date getRealEndPeriod() {
        return this.realEndPeriod;
    }

    public void setRealEndPeriod(Date realEndPeriod) {
        this.realEndPeriod = realEndPeriod;
    }

    public BigDecimal getTimecardNum() {
        return this.timecardNum;
    }

    public void setTimecardNum(BigDecimal timecardNum) {
        this.timecardNum = timecardNum;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
