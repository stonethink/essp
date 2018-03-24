package db.essp.hrbase;

import java.util.Date;


/**
 * HrbBd generated by MyEclipse - Hibernate Tools
 */

public class HrbBd  implements java.io.Serializable {


    // Fields    

     private Long rid;
     private String bdCode;
     private String bdName;
     private String description;
     private Long sequence;
     private String rst;
     private Date rct;
     private Date rut;
     private String rcu;
     private String ruu;
     private boolean status;
     private boolean achieveBelong;


    // Constructors

    public boolean getAchieveBelong() {
        return achieveBelong;
    }

    public void setAchieveBelong(boolean achieveBelong) {
        this.achieveBelong = achieveBelong;
    }

    /** default constructor */
    public HrbBd() {
    }

	/** minimal constructor */
    public HrbBd(Long rid, String bdCode) {
        this.rid = rid;
        this.bdCode = bdCode;
    }
    
    /** full constructor */
    public HrbBd(Long rid, String bdCode, String bdName, String description, Long sequence, String rst, Date rct, Date rut, String rcu, String ruu,  boolean status) {
        this.rid = rid;
        this.bdCode = bdCode;
        this.bdName = bdName;
        this.description = description;
        this.sequence = sequence;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.rcu = rcu;
        this.ruu = ruu;
        this.status = status;
    }

   
    // Property accessors

    public Long getRid() {
        return this.rid;
    }
    
    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getBdCode() {
        return this.bdCode;
    }
    
    public void setBdCode(String bdCode) {
        this.bdCode = bdCode;
    }

    public String getBdName() {
        return this.bdName;
    }
    
    public void setBdName(String bdName) {
        this.bdName = bdName;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSequence() {
        return this.sequence;
    }
    
    public void setSequence(Long sequence) {
        this.sequence = sequence;
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

    public String getRcu() {
        return this.rcu;
    }
    
    public void setRcu(String rcu) {
        this.rcu = rcu;
    }

    public String getRuu() {
        return this.ruu;
    }
    
    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    public boolean getStatus() {
        return this.status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
   








}