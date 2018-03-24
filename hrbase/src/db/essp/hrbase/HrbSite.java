package db.essp.hrbase;

import java.util.Date;


/**
 * HrbSite generated by MyEclipse - Hibernate Tools
 */

public class HrbSite  implements java.io.Serializable {


    // Fields    

     private Long rid;
     private String name;
     private String description;
     private String isEnable;
     private Long seq;
     private Date rct;
     private Date rut;
     private String rcu;
     private String ruu;


    // Constructors

    /** default constructor */
    public HrbSite() {
    }

	/** minimal constructor */
    public HrbSite(Long rid) {
        this.rid = rid;
    }
    
    /** full constructor */
    public HrbSite(Long rid, String name, String description, String isEnable, Long seq, Date rct, Date rut, String rcu, String ruu) {
        this.rid = rid;
        this.name = name;
        this.description = description;
        this.isEnable = isEnable;
        this.seq = seq;
        this.rct = rct;
        this.rut = rut;
        this.rcu = rcu;
        this.ruu = ruu;
    }

   
    // Property accessors

    public Long getRid() {
        return this.rid;
    }
    
    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsEnable() {
        return this.isEnable;
    }
    
    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public Long getSeq() {
        return this.seq;
    }
    
    public void setSeq(Long seq) {
        this.seq = seq;
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
}