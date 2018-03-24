package server.essp.projectpre.db;

import java.util.Date;


/**
 * PcCustomer generated by MyEclipse - Hibernate Tools
 */

public class Customer  implements java.io.Serializable {


    // Fields    

     private Long rid;
     private String attribute;
     private String customerId;
     private String regId;
     private String groupId;
     private String short_;
     private String nameCn;
     private String creator;
     private String nameEn;
     private String belongBd;
     private String belongSite;
     private String class_;
     private String country;
     private String custDescription;
     private Date createDate;
     private Date alterDate;
     private String description;
     private String rst;
     private Date rct;
     private Date rut;
     private String rcu;
     private String ruu;


    // Constructors

    /** default constructor */
    public Customer() {
    }

	/** minimal constructor */
    public Customer(Long rid) {
        this.rid = rid;
    }
    
    /** full constructor */

   

    public Customer(Long rid, String attribute, String customerId, String regId, String groupId, String short_, String nameCn, String nameEn,Date createDate,Date alterDate, String belongBd, String belongSite, String class_, String country,String custDescription,String creator, String description, String rst, Date rct, Date rut, String rcu, String ruu) {

        this.rid = rid;
        this.attribute = attribute;
        this.customerId = customerId;
        this.regId = regId;
        this.groupId = groupId;
        this.short_ = short_;
        this.nameCn = nameCn;
        this.nameEn = nameEn;
        this.belongBd = belongBd;
        this.belongSite = belongSite;
        this.class_ = class_;
        this.creator = creator;
        this.createDate = createDate;
        this.alterDate = alterDate;
        this.country = country;
        this.custDescription=custDescription;
        this.description = description;
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

    public String getAttribute() {
        return this.attribute;
    }
    
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRegId() {
        return this.regId;
    }
    
    public void setRegId(String regId) {
        this.regId = regId;
    }



    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getShort_() {
        return this.short_;
    }
    
    public void setShort_(String short_) {
        this.short_ = short_;
    }

    public String getNameCn() {
        return this.nameCn;
    }
    
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return this.nameEn;
    }
    
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getBelongBd() {
        return this.belongBd;
    }
    
    public void setBelongBd(String belongBd) {
        this.belongBd = belongBd;
    }

    public String getBelongSite() {
        return this.belongSite;
    }
    
    public void setBelongSite(String belongSite) {
        this.belongSite = belongSite;
    }

    public String getClass_() {
        return this.class_;
    }
    
    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String country) {
        this.country = country;
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

	public String getCustDescription() {
		return custDescription;
	}

	public void setCustDescription(String custDescription) {
		this.custDescription = custDescription;
	}



	public Date getAlterDate() {
		return alterDate;
	}

	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
   

}