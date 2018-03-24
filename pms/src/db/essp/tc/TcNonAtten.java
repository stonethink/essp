package db.essp.tc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TcNonAtten implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String userId;

    /** nullable persistent field */
    private Date timeFrom;

    /** nullable persistent field */
    private Date timeTo;

    /** nullable persistent field */
    private BigDecimal totalHours;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;

    /** full constructor */
    public TcNonAtten(Long rid, String userId, Date timeFrom, Date timeTo, BigDecimal totalHours, String remark, Date rct, Date rut, String rst) {
        this.rid = rid;
        this.userId = userId;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.totalHours = totalHours;
        this.remark = remark;
        this.rct = rct;
        this.rut = rut;
        this.rst = rst;
    }

    /** default constructor */
    public TcNonAtten() {
    }

    /** minimal constructor */
    public TcNonAtten(Long rid) {
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

    public Date getTimeFrom() {
        return this.timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return this.timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }

    public BigDecimal getTotalHours() {
        return this.totalHours;
    }

    public void setTotalHours(BigDecimal totalHours) {
        this.totalHours = totalHours;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
