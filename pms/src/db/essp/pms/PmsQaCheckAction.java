package db.essp.pms;

import java.util.Date;


/**
 * PmsQaCheckAction generated by MyEclipse - Hibernate Tools
 */

public class PmsQaCheckAction  implements java.io.Serializable {


    // Fields

     private PmsQaCheckActionPK PK;
    private Date planDate;
     private String planPerformer;
     private Date actDate;
     private String actPerformer;
     private String content;
     private String result;
     private String nrcNo;
     private String rst;
     private Date rct;
     private Date rut;
     private Long cpRid;
    private String occasion;
    private String status;


    // Constructors

    /** default constructor */
    public PmsQaCheckAction() {
    }

	/** minimal constructor */
    public PmsQaCheckAction(PmsQaCheckActionPK PK, Long cpRid) {
        this.PK = PK;
        this.cpRid = cpRid;
    }

    /** full constructor */
    public PmsQaCheckAction(PmsQaCheckActionPK PK, Date planDate, String planPerformer, Date actDate, String actPerformer, String content, String result, String nrcNo, String rst, Date rct, Date rut, Long cpRid) {
        this.PK = PK;
        this.planDate = planDate;
        this.planPerformer = planPerformer;
        this.actDate = actDate;
        this.actPerformer = actPerformer;
        this.content = content;
        this.result = result;
        this.nrcNo = nrcNo;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.cpRid = cpRid;
    }


    // Property accessors

    public PmsQaCheckActionPK getPK() {

        return PK;
    }

    public void setPK(PmsQaCheckActionPK PK) {

        this.PK = PK;
    }

    public Date getPlanDate() {
        return this.planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getPlanPerformer() {
        return this.planPerformer;
    }

    public void setPlanPerformer(String planPerformer) {
        this.planPerformer = planPerformer;
    }

    public Date getActDate() {
        return this.actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public String getActPerformer() {
        return this.actPerformer;
    }

    public void setActPerformer(String actPerformer) {
        this.actPerformer = actPerformer;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNrcNo() {
        return this.nrcNo;
    }

    public void setNrcNo(String nrcNo) {
        this.nrcNo = nrcNo;
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

    public Long getCpRid() {
        return this.cpRid;
    }

    public String getOccasion() {
        return occasion;
    }

    public String getStatus() {
        return status;
    }

    public void setCpRid(Long cpRid) {
        this.cpRid = cpRid;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
