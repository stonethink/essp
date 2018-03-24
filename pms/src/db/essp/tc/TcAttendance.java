package db.essp.tc;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import server.framework.hibernate.HBComAccess;


/** @author Hibernate CodeGenerator */
public class TcAttendance implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String userId;

    /** nullable persistent field */
    private String type;

    /** nullable persistent field */
    private Date date;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;

    /** full constructor */
    public TcAttendance(Long rid, String userId, String type, Date date, String remark, Date rct, Date rut, String rst) {
        this.rid = rid;
        this.userId = userId;
        this.type = type;
        this.date = date;
        this.remark = remark;
        this.rct = rct;
        this.rut = rut;
        this.rst = rst;
    }

    /** default constructor */
    public TcAttendance() {
    }

    /** minimal constructor */
    public TcAttendance(Long rid) {
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
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
    public static void main(String[] args){
        HBComAccess hb = new HBComAccess();

        TcAttendance att = new TcAttendance();
        att.setDate(new Date());
        att.setType("abccc");
        att.setUserId("stone.shi");
        att.setRemark("sssss");
        try {
            hb.followTx();
            hb.save(att);
            hb.endTxCommit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
