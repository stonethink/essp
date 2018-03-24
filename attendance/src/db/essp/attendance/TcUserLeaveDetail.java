package db.essp.attendance;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;
import server.framework.common.BusinessException;

/** @author Hibernate CodeGenerator */
public class TcUserLeaveDetail implements Serializable {

    /** identifier field */
    private db.essp.attendance.TcUserLeaveDetailPK comp_id;

    /** nullable persistent field */
    private Long rid;

    /** nullable persistent field */
    private Double canLeaveHours;

    /** nullable persistent field */
    private Double useLeaveHours;

    /** nullable persistent field */
    private Double payedHours;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private db.essp.attendance.TcUserLeave tcUserLeave;

    /** default constructor */
    public TcUserLeaveDetail() {
    }

    public db.essp.attendance.TcUserLeaveDetailPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(db.essp.attendance.TcUserLeaveDetailPK comp_id) {
        this.comp_id = comp_id;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Double getCanLeaveHours() {
        return canLeaveHours == null ? new Double(0) : canLeaveHours;
    }

    public void setCanLeaveHours(Double canLeaveHours) {
        this.canLeaveHours = canLeaveHours;
    }

    public Double getUseLeaveHours() {
        return useLeaveHours == null ? new Double(0) : useLeaveHours;
    }

    public void setUseLeaveHours(Double useLeaveHours) {
        this.useLeaveHours = useLeaveHours;
    }

    public Double getPayedHours() {
        return payedHours == null ? new Double(0) : payedHours;
    }

    public void setPayedHours(Double payedHours) {
        this.payedHours = payedHours;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
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

    public db.essp.attendance.TcUserLeave getTcUserLeave() {
        return this.tcUserLeave;
    }

    public void setTcUserLeave(db.essp.attendance.TcUserLeave tcUserLeave) {
        this.tcUserLeave = tcUserLeave;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcUserLeaveDetail) ) return false;
        TcUserLeaveDetail castOther = (TcUserLeaveDetail) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }
    //合计总已用时间=已休时间+已支付时间
    public Double getUsedHours(){
        double used = getPayedHours().doubleValue() + getUseLeaveHours().doubleValue();
        return new Double(used);
    }
    //合计可用时间=总休假时间-总已用时间
    public Double getUsableHours(){
        double usable = getCanLeaveHours().doubleValue() - getUsedHours().doubleValue();
        return new Double(usable);
    }
    //增加已休时间
    public void addUseLeaveHours(double hours){
        if(hours > getUsableHours().doubleValue())
            throw new BusinessException("TC_LV_0009","There is not enough hours!");
        double useLeave = hours + getUseLeaveHours().doubleValue();
        setUseLeaveHours(new Double(useLeave));
    }
    public void returnUseLeaveHours(double hours){
        if(hours > getUseLeaveHours().doubleValue())
            throw new BusinessException();
        double useLeave = getUseLeaveHours().doubleValue() - hours;
        setUseLeaveHours(new Double(useLeave));
    }

    //增加付钱时间
    public void addPayedeHours(double hours){
        if(hours > getUsableHours().doubleValue())
            throw new BusinessException("TC_LV_0011","There is not enough hours!");
        double payed = hours + getPayedHours().doubleValue();
        setPayedHours(new Double(payed));
    }
    //返还(减少)付钱时间
    public void returnPayedHours(double hours){
        double payed = getPayedHours().doubleValue() - hours;
        if(payed < 0)
            throw new BusinessException("TC_LV_0012", "The return payed hours ["+payed+"] is larger!");
        setPayedHours(new Double(payed));
    }

}
