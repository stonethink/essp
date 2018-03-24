package db.essp.hrbase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * HrbHumanBaseLogId generated by MyEclipse - Hibernate Tools
 */

public class HrbHumanBaseLog  implements java.io.Serializable {
	
	public final static String STATUS_PENDING = "Pending";
	public final static String STATUS_COMPLETE = "Completed";
	public final static String STATUS_CANCELED = "Cancelled";
	
	public final static String OPERATION_INSERT = "Insert";
	public final static String OPERATION_UPDATE = "Update";
	public final static String OPERATION_DELETE = "Delete";
	public final static String OPERATION_NONE = "None";
	
	

    // Fields    

     private Long rid;
     private Long baseRid;
     private String employeeId;
     private String unitCode;
     private String englishName;
     private String chineseName;
     private String title;
     private String rank;
     private String resManagerId;
     private String resManagerName;
     private String email;
     private String site;
     private Date inDate;
     private Date outDate;
     private String attribute;
     private String operation;
     private Date effectiveDate;
     private String status;
     private String rst;
     private Date rut;
     private Date rct;
     private String rcu;
     private String ruu;
     private String remark = "";
     private Long attributeGroupRid;
     private String isDirect;

   
    // Property accessors

    public Long getAttributeGroupRid() {
		return attributeGroupRid;
	}

	public void setAttributeGroupRid(Long attributeGroupRid) {
		this.attributeGroupRid = attributeGroupRid;
	}

	public String getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(String isDirect) {
		this.isDirect = isDirect;
	}

	public Long getRid() {
        return this.rid;
    }
    
    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getBaseRid() {
        return this.baseRid;
    }
    
    public void setBaseRid(Long baseRid) {
        this.baseRid = baseRid;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUnitCode() {
        return this.unitCode;
    }
    
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getEnglishName() {
        return this.englishName;
    }
    
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return this.chineseName;
    }
    
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getRank() {
        return this.rank;
    }
    
    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getResManagerId() {
        return this.resManagerId;
    }
    
    public void setResManagerId(String resManagerId) {
        this.resManagerId = resManagerId;
    }

    public String getResManagerName() {
        return this.resManagerName;
    }
    
    public void setResManagerName(String resManagerName) {
        this.resManagerName = resManagerName;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return this.site;
    }
    
    public void setSite(String site) {
        this.site = site;
    }

    public Date getInDate() {
        return this.inDate;
    }
    
    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return this.outDate;
    }
    
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getOperation() {
        return this.operation;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getEffectiveDate() {
        return this.effectiveDate;
    }
    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getRst() {
        return this.rst;
    }
    
    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRut() {
        return this.rut;
    }
    
    public void setRut(Date rut) {
        this.rut = rut;
    }

    public Date getRct() {
        return this.rct;
    }
    
    public void setRct(Date rct) {
        this.rct = rct;
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

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	public String getCanUpdate() {
		if(this.getStatus() == null) {
			return "false";
		} else if(STATUS_PENDING.equals(this.getStatus())) {
			return "true";
		} else {
			return "false";
		}
	}
	
	public String getCanEdit() {
		if(this.getStatus() == null) {
			return "false";
		} else if(STATUS_PENDING.equals(this.getStatus())
				&& (OPERATION_UPDATE.equals(this.getOperation())
						|| OPERATION_INSERT.equals(this.getOperation()))) {
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
     * englishName(chineseName)
     * @return
     */
    public String getFullName() {
        return HrbHumanBase.ecName2FullName(this.getEnglishName(), this.getChineseName());
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
