package server.essp.projectpre.db;

import java.util.Date;


/**
 * PpAcntPersonApp generated by MyEclipse - Hibernate Tools
 */

public class AcntPersonApp  implements java.io.Serializable {


    // Fields    

     private Long rid;
     private Long acntAppRid;
     private String personType;
     private String domain;
     private String loginId;
     private String name;
     private String rst;
     private Date rct;
     private Date rut;
     private String rcu;
     private String ruu;


    // Constructors

    /** default constructor */
    public AcntPersonApp() {
    }

	/** minimal constructor */
    public AcntPersonApp(Long rid) {
        this.rid = rid;
    }
    
    /** full constructor */
    public AcntPersonApp(Long rid, Long acntAppRid, String personType,String domain, String loginId, String name, String rst, Date rct, Date rut, String rcu, String ruu) {
        this.rid = rid;
        this.acntAppRid = acntAppRid;
        this.personType = personType;
        this.domain = domain;
        this.loginId = loginId;
        this.name = name;
        this.rst = rst;
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

    public Long getAcntAppRid() {
        return this.acntAppRid;
    }
    
    public void setAcntAppRid(Long acntAppRid) {
        this.acntAppRid = acntAppRid;
    }

    public String getPersonType() {
        return this.personType;
    }
    
    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getLoginId() {
        return this.loginId;
    }
    
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
   








}