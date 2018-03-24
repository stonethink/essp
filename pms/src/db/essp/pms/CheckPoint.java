package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CheckPoint implements Serializable {

    /** identifier field */
    private db.essp.pms.CheckPointPK pk;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private Double weight;

    /** nullable persistent field */
    private Date dueDate;

    /** nullable persistent field */
    private Date finishDate;

    /** nullable persistent field */
    private String completed;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public CheckPoint(db.essp.pms.CheckPointPK pk, String name, Double weight, Date dueDate, Date finishDate, String completed, String remark, String rst, Date rct, Date rut) {
        this.pk = pk;
        this.name = name;
        this.weight = weight;
        this.dueDate = dueDate;
        this.finishDate = finishDate;
        this.completed = completed;
        this.remark = remark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public CheckPoint() {
    }

    /** minimal constructor */
    public CheckPoint(db.essp.pms.CheckPointPK pk) {
        this.pk = pk;
    }

    public db.essp.pms.CheckPointPK getPk() {
        return this.pk;
    }

    public void setPk(db.essp.pms.CheckPointPK pk) {
        this.pk = pk;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getCompleted() {
        return this.completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CheckPoint) ) return false;
        CheckPoint castOther = (CheckPoint) other;
        return new EqualsBuilder()
            .append(this.getPk(), castOther.getPk())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getPk())
            .toHashCode();
    }

}
