package db.essp.hrbase;

import java.util.Date;


/**
 * HrbSiteRelation generated by MyEclipse - Hibernate Tools
 */

public class HrbSitePrivilege  implements java.io.Serializable {


    // Fields    

     private Long rid;
     private String siteName;
     private String loginId;
     private String loginName;
     private String domain;
     private String email;
     private Date rct;
     private Date rut;
     private String rcu;
     private String ruu;


    // Constructors

    /** default constructor */
    public HrbSitePrivilege() {
    }

	/** minimal constructor */
    public HrbSitePrivilege(Long rid) {
        this.rid = rid;
    }
    
    /** full constructor */
    public HrbSitePrivilege(Long rid, String siteName, String loginId,
            String loginName,String domain,String email, Date rct, Date rut, String rcu, String ruu) {
        this.rid = rid;
        this.siteName = siteName;
        this.loginId = loginId;
        this.loginName = loginName;
        this.domain = domain;
        this.email = email;
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

    public String getSiteName() {
        return this.siteName;
    }
    
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getLoginId() {
        return this.loginId;
    }
    
    public void setLoginId(String loginId) {
        this.loginId = loginId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

}