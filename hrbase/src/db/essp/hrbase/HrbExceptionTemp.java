package db.essp.hrbase;

import java.util.Date;


/**
 * HrbExceptionTemp generated by MyEclipse - Hibernate Tools
 */

public class HrbExceptionTemp  implements java.io.Serializable {


    // Fields    

     private Long rid;
     private Long resDataRid;
     private String model;
     private Date effectiveDate;
     private String operation;
     private String status;
     private String errorMessage;
     private String resType;
     private Date rut;
     private Date rct;
     private String rcu;
     private String ruu;
     


    public Date getRct() {
		return rct;
	}

	public void setRct(Date rct) {
		this.rct = rct;
	}

	public String getRcu() {
		return rcu;
	}

	public void setRcu(String rcu) {
		this.rcu = rcu;
	}

	public Date getRut() {
		return rut;
	}

	public void setRut(Date rut) {
		this.rut = rut;
	}

	public String getRuu() {
		return ruu;
	}

	public void setRuu(String ruu) {
		this.ruu = ruu;
	}

    public Long getRid() {
        return this.rid;
    }
    
    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getResDataRid() {
        return this.resDataRid;
    }
    
    public void setResDataRid(Long resDataRid) {
        this.resDataRid = resDataRid;
    }

    public Date getEffectiveDate() {
        return this.effectiveDate;
    }
    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

}