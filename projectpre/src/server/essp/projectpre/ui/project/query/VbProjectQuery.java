package server.essp.projectpre.ui.project.query;


import java.util.List;

public class VbProjectQuery {

	
    private List execSiteList;//���Г��c
    private List execUnitList;//���І�λ
    private List costAttachBdList;//�ɱ��w�ن�λ
    private List bizSourceList;//�I�Ձ�Դ
    private List acntAttributeList;//��������
    private List acntTypeList;//�������
    private List productTypeList;//�aƷ���
    private List productPropertyList;//�aƷ����
    private List workItemList;//�����Ŀ
    private List technicalAreaList;//���g�I��
    private List supportLangugeList;//֧Ԯ�Z��
    private List bizPropertyList;//�I�Ռ���
    private List billTypeList;//���M���
    private String applicationDateBegin;
    private String applicationDateEnd;
    private String acntPlannedStart;
    private String acntPlannedFinish;
    
	public String getApplicationDateBegin() {
        return applicationDateBegin;
    }
    public void setApplicationDateBegin(String applicationDateBegin) {
        this.applicationDateBegin = applicationDateBegin;
    }
    public String getApplicationDateEnd() {
        return applicationDateEnd;
    }
    public void setApplicationDateEnd(String applicationDateEnd) {
        this.applicationDateEnd = applicationDateEnd;
    }
    public List getAcntAttributeList() {
		return acntAttributeList;
	}
	public void setAcntAttributeList(List acntAttributeList) {
		this.acntAttributeList = acntAttributeList;
	}
	public List getAcntTypeList() {
		return acntTypeList;
	}
	public void setAcntTypeList(List acntTypeList) {
		this.acntTypeList = acntTypeList;
	}
	public List getBizSourceList() {
		return bizSourceList;
	}
	public void setBizSourceList(List bizSourceList) {
		this.bizSourceList = bizSourceList;
	}
	public List getCostAttachBdList() {
		return costAttachBdList;
	}
	public void setCostAttachBdList(List costAttachBdList) {
		this.costAttachBdList = costAttachBdList;
	}
	public List getExecSiteList() {
		return execSiteList;
	}
	public void setExecSiteList(List execSiteList) {
		this.execSiteList = execSiteList;
	}
	public List getProductPropertyList() {
		return productPropertyList;
	}
	public void setProductPropertyList(List productPropertyList) {
		this.productPropertyList = productPropertyList;
	}
	public List getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List productTypeList) {
		this.productTypeList = productTypeList;
	}
	public List getSupportLangugeList() {
		return supportLangugeList;
	}
	public void setSupportLangugeList(List supportLangugeList) {
		this.supportLangugeList = supportLangugeList;
	}
	public List getTechnicalAreaList() {
		return technicalAreaList;
	}
	public void setTechnicalAreaList(List technicalAreaList) {
		this.technicalAreaList = technicalAreaList;
	}
	public List getWorkItemList() {
		return workItemList;
	}
	public void setWorkItemList(List workItemList) {
		this.workItemList = workItemList;
	}
    public List getExecUnitList() {
        return execUnitList;
    }
    public void setExecUnitList(List execUnitList) {
        this.execUnitList = execUnitList;
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
	public List getBizPropertyList() {
		return bizPropertyList;
	}
	public void setBizPropertyList(List bizPropertyList) {
		this.bizPropertyList = bizPropertyList;
	}
	public List getBillTypeList() {
		return billTypeList;
	}
	public void setBillTypeList(List billTypeList) {
		this.billTypeList = billTypeList;
	}

  
     
   
}
