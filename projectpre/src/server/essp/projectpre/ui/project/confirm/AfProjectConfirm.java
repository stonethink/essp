package server.essp.projectpre.ui.project.confirm;

import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class AfProjectConfirm extends ActionForm{
	private String acntBrief;
	private String productType;
	private String customerId;
	private String acntType;
    private String applicant;
    private String acntId;
    private String acntStatus;// î‘B
    private String leader;
    private String leaderId;
    private String leaderDomain;
    private String PMName;
    private String PMId;
    private String PMDomain;
    private String TCSName;
    private String BDName;
    private String BDId;
    private String BDDomain;
    private String acntName;
    private String acntFullName;
    private String acntAttribute;
    private String acntPlannedStart;
    private String acntPlannedFinish;
    private String acntActualStart;
    private String acntActualFinish;
    private Double actualManmonth;
    private String productAttribute;
    private String workItem;
    private String acntAnticpated;
    private String comment;
    private List acntStatusList;
    private String otherDesc;
    public String getOtherDesc() {
		return otherDesc;
	}
	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}
	public String getAcntAnticpated() {
        return acntAnticpated;
    }
    public void setAcntAnticpated(String acntAnticpated) {
        this.acntAnticpated = acntAnticpated;
    }
    public String getAcntAttribute() {
        return acntAttribute;
    }
    public void setAcntAttribute(String acntAttribute) {
        this.acntAttribute = acntAttribute;
    }
    public String getAcntId() {
        return acntId;
    }
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }
    public String getAcntName() {
        return acntName;
    }
    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }
    public String getApplicant() {
        return applicant;
    }
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
    public String getBDName() {
        return BDName;
    }
    public void setBDName(String name) {
        BDName = name;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getLeader() {
        return leader;
    }
    public void setLeader(String leader) {
        this.leader = leader;
    }
    public String getPMName() {
        return PMName;
    }
    public void setPMName(String name) {
        PMName = name;
    }
    public String getProductAttribute() {
        return productAttribute;
    }
    public void setProductAttribute(String productAttribute) {
        this.productAttribute = productAttribute;
    }
  
    public String getTCSName() {
        return TCSName;
    }
    public void setTCSName(String name) {
        TCSName = name;
    }
    public String getWorkItem() {
        return workItem;
    }
    public void setWorkItem(String workItem) {
        this.workItem = workItem;
    }
    public String getAcntFullName() {
        return acntFullName;
    }
    public void setAcntFullName(String acntFullName) {
        this.acntFullName = acntFullName;
    }
      public String getBDDomain() {
        return BDDomain;
    }
    public void setBDDomain(String domain) {
        BDDomain = domain;
    }
    public String getLeaderDomain() {
        return leaderDomain;
    }
    public void setLeaderDomain(String leaderDomain) {
        this.leaderDomain = leaderDomain;
    }
    public String getPMDomain() {
        return PMDomain;
    }
    public void setPMDomain(String domain) {
        PMDomain = domain;
    }
    public String getBDId() {
        return BDId;
    }
    public void setBDId(String id) {
        BDId = id;
    }
    public String getLeaderId() {
        return leaderId;
    }
    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }
    public String getPMId() {
        return PMId;
    }
    public void setPMId(String id) {
        PMId = id;
    }
	public String getAcntStatus() {
		return acntStatus;
	}
	public void setAcntStatus(String acntStatus) {
		this.acntStatus = acntStatus;
	}
	public String getAcntType() {
		return acntType;
	}
	public void setAcntType(String acntType) {
		this.acntType = acntType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAcntPlannedFinish() {
		return acntPlannedFinish;
	}
	public void setAcntPlannedFinish(String acntPlannedFinish) {
		this.acntPlannedFinish = acntPlannedFinish;
	}
	public String getAcntPlannedStart() {
		return acntPlannedStart;
	}
	public void setAcntPlannedStart(String acntPlannedStart) {
		this.acntPlannedStart = acntPlannedStart;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getAcntBrief() {
		return acntBrief;
	}
	public void setAcntBrief(String acntBrief) {
		this.acntBrief = acntBrief;
	}
	public List getAcntStatusList() {
		return acntStatusList;
	}
	public void setAcntStatusList(List acntStatusList) {
		this.acntStatusList = acntStatusList;
	}
	public String getAcntActualStart() {
		return acntActualStart;
	}
	public void setAcntActualStart(String acntActualStart) {
		this.acntActualStart = acntActualStart;
	}
	public String getAcntActualFinish() {
		return acntActualFinish;
	}
	public void setAcntActualFinish(String acntActualFinish) {
		this.acntActualFinish = acntActualFinish;
	}
	public Double getActualManmonth() {
		return actualManmonth;
	}
	public void setActualManmonth(Double actualManmonth) {
		this.actualManmonth = actualManmonth;
	}
 

}
