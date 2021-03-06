package db.essp.pms;

import java.util.Date;


/**
 * PmsActivityGuideline generated by MyEclipse - Hibernate Tools
 */

public class PmsActivityGuideline  implements java.io.Serializable {


    // Fields    

     private PmsActivityGuidelineId id;
     private Long refAcntRid;
     private Long refActRid;
     private String description;
     private String rst;
     private Date rct;
     private Date rut;


    // Constructors

    /** default constructor */
    public PmsActivityGuideline() {
    }

	/** minimal constructor */
    public PmsActivityGuideline(PmsActivityGuidelineId id, Long refAcntRid, Long refActRid) {
        this.id = id;
        this.refAcntRid = refAcntRid;
        this.refActRid = refActRid;
    }
    
    /** full constructor */
    public PmsActivityGuideline(PmsActivityGuidelineId id, Long refAcntRid, Long refActRid, String description, String rst, Date rct, Date rut) {
        this.id = id;
        this.refAcntRid = refAcntRid;
        this.refActRid = refActRid;
        this.description = description;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

   
    // Property accessors

    public PmsActivityGuidelineId getId() {
        return this.id;
    }
    
    public void setId(PmsActivityGuidelineId id) {
        this.id = id;
    }

    public Long getRefAcntRid() {
        return this.refAcntRid;
    }
    
    public void setRefAcntRid(Long refAcntRid) {
        this.refAcntRid = refAcntRid;
    }

    public Long getRefActRid() {
        return this.refActRid;
    }
    
    public void setRefActRid(Long refActRid) {
        this.refActRid = refActRid;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
   








}