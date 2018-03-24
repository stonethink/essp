package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TcTimecardPK implements Serializable {

    /** identifier field */
    private Date tmcWeeklyStart;

    /** identifier field */
    private Date tmcWeeklyFinish;

    /** identifier field */
    private Long tmcProjId;

    /** identifier field */
    private String tmcEmpId;

    /** full constructor */
    public TcTimecardPK(Date tmcWeeklyStart, Date tmcWeeklyFinish, Long tmcProjId, String tmcEmpId) {
        this.tmcWeeklyStart = tmcWeeklyStart;
        this.tmcWeeklyFinish = tmcWeeklyFinish;
        this.tmcProjId = tmcProjId;
        this.tmcEmpId = tmcEmpId;
    }

    /** default constructor */
    public TcTimecardPK() {
    }

    /** 
     *                @hibernate.property
     *                 column="TMC_WEEKLY_START"
     *                 length="10"
     *             
     */
    public Date getTmcWeeklyStart() {
        return this.tmcWeeklyStart;
    }

    public void setTmcWeeklyStart(Date tmcWeeklyStart) {
        this.tmcWeeklyStart = tmcWeeklyStart;
    }

    /** 
     *                @hibernate.property
     *                 column="TMC_WEEKLY_FINISH"
     *                 length="10"
     *             
     */
    public Date getTmcWeeklyFinish() {
        return this.tmcWeeklyFinish;
    }

    public void setTmcWeeklyFinish(Date tmcWeeklyFinish) {
        this.tmcWeeklyFinish = tmcWeeklyFinish;
    }

    /** 
     *                @hibernate.property
     *                 column="TMC_PROJ_ID"
     *                 length="8"
     *             
     */
    public Long getTmcProjId() {
        return this.tmcProjId;
    }

    public void setTmcProjId(Long tmcProjId) {
        this.tmcProjId = tmcProjId;
    }

    /** 
     *                @hibernate.property
     *                 column="TMC_EMP_ID"
     *                 length="20"
     *             
     */
    public String getTmcEmpId() {
        return this.tmcEmpId;
    }

    public void setTmcEmpId(String tmcEmpId) {
        this.tmcEmpId = tmcEmpId;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("tmcWeeklyStart", getTmcWeeklyStart())
            .append("tmcWeeklyFinish", getTmcWeeklyFinish())
            .append("tmcProjId", getTmcProjId())
            .append("tmcEmpId", getTmcEmpId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcTimecardPK) ) return false;
        TcTimecardPK castOther = (TcTimecardPK) other;
        return new EqualsBuilder()
            .append(this.getTmcWeeklyStart(), castOther.getTmcWeeklyStart())
            .append(this.getTmcWeeklyFinish(), castOther.getTmcWeeklyFinish())
            .append(this.getTmcProjId(), castOther.getTmcProjId())
            .append(this.getTmcEmpId(), castOther.getTmcEmpId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTmcWeeklyStart())
            .append(getTmcWeeklyFinish())
            .append(getTmcProjId())
            .append(getTmcEmpId())
            .toHashCode();
    }

}
