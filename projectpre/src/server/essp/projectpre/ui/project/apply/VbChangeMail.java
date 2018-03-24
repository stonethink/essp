
package server.essp.projectpre.ui.project.apply;

import java.lang.reflect.Field;







public class VbChangeMail {
    public String applicationDate;
    public String applicantName;
    public String acntAttribute;
    public String pmName;
    public String acntFullName;
    public String tcSName;
    public String acntBrief;
    public String bdMName;
    public String leaderName;
    public String execSite;
    public String costAttachBd;
    public String execUnit;
    public String achieveBelong;
    public String bizSource;
    public String salesName;
    public String productName;
    public String acntPlannedStart;
    public String acntPlannedFinish;
    public String estManmonth;
    public String estSize;
    public String acntActualStart;
    public String acntActualFinish;
    public String actualManmonth;
    public String projectType;
    public String productType;
    public String productAttribute;
    public String workItem;
    public String technicalDomain;
    public String originalLanguage;
    public String translationLanguage;
    public String developJobSystem;
    public String developDataBase;
    public String developTool;
    public String developNetWork;
    public String developProgramLanguage;
    public String developOthers;
    public String typeJobSystem;
    public String typeDataBase;
    public String typeTool;
    public String typeNetWork;
    public String typeProgramLanguage;
    public String typeOthers;
    public String hardReq;
    public String softReq;
    public String customerId;
    public String contract;
    public String contractTel;
    public String contractEmail;
    public String executive;
    public String executiveTel;
    public String executiveEmail;
    public String financial;
    public String financialTel;
    public String financialEmail;
    public String custServiceManagerName;
    public String engageManagerName;
    public String otherDesc;
    public String primaveraAdapted;
    public String billingBasis;
    public String bizProperty;
    public String billType;
    public String bl;
    

    public String acntId;
    public String acntFullNameBefore;
    public String acntAttributeBefore;
    public String execSiteBefore;
    public String costAttachBdBefore;
    public String execUnitBefore;
    public String achieveBelongBefore;
    public String bizSourceBefore;
    public String productNameBefore;
    public String acntPlannedStartBefore;
    public String acntPlannedFinishBefore;
    public String acntActualStartBefore;
    public String acntActualFinishBefore;
    public Double estManmonthBefore;
    public Double actualManmonthBefore;
    public Long estSizeBefore;
    public String acntBriefBefore;
    public String otherDescBefore;
    public String pmNameBefore;
    public String tcSNameBefore;
    public String bdMNameBefore;
    public String leaderNameBefore;
    public String salesNameBefore;
    public String projectTypeBefore;
    public String productTypeBefore;
    public String productAttributeBefore;
    public String workItemBefore;
    public String technicalDomainBefore;
    public String originalLanguageBefore;
    public String translationLanguageBefore;
    public String developJobSystemBefore;
    public String developDataBaseBefore;
    public String developToolBefore;
    public String developNetWorkBefore;
    public String developProgramLanguageBefore;
    public String developOthersBefore;
    public String typeJobSystemBefore;
    public String typeDataBaseBefore;
    public String typeToolBefore;
    public String typeNetWorkBefore;
    public String typeProgramLanguageBefore;
    public String typeOthersBefore;
    public String hardReqBefore;
    public String softReqBefore;
    public String customerIdBefore;
    public String contractBefore;
    public String contractTelBefore;
    public String contractEmailBefore;
    public String executiveBefore;
    public String executiveTelBefore;
    public String executiveEmailBefore;
    public String financialBefore;
    public String financialTelBefore;
    public String financialEmailBefore;
    public String custServiceManagerNameBefore;
    public String engageManagerNameBefore;
    public String primaveraAdaptedBefore;
    public String billingBasisBefore;
    public String bizPropertyBefore;
    public String billTypeBefore;
    public String blBefore;
    
    private String reMark;
  
    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }

    public String getPropertyHtml(String property){
        String propertyBefore = property + "Before";
        try {
            Field field = this.getClass().getField(property);
            Field fieldBefore = this.getClass().getField(propertyBefore);
            String value = "";
            String valueBefore ="";
            if(field.get(this)!=null){
               value = field.get(this).toString();
            }
            if(fieldBefore.get(this)!=null){
               valueBefore = fieldBefore.get(this).toString();         
            }
            if(!value.equals(valueBefore)){
                return "<font color='red'>"+value+"</font>";
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getAcntActualFinish() {
        return getPropertyHtml("acntActualFinish");
    }
    public void setAcntActualFinish(String acntActualFinish) {
        this.acntActualFinish = acntActualFinish;
    }
    public String getAcntActualStart() {
        return getPropertyHtml("acntActualStart");
    }
    public void setAcntActualStart(String acntActualStart) {
        this.acntActualStart = acntActualStart;
    }
   
    public String getAcntBrief() {
        return getPropertyHtml("acntBrief");
    }
    public void setAcntBrief(String acntBrief) {
        this.acntBrief = acntBrief;
    }
    public String getAcntFullName() {
        return getPropertyHtml("acntFullName");
    }
    public void setAcntFullName(String acntFullName) {
        this.acntFullName = acntFullName;
    }
    public String getAcntId() {
        return acntId;
    }
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }
  
    public String getAcntPlannedFinish() {
        return getPropertyHtml("acntPlannedFinish");
    }
    public void setAcntPlannedFinish(String acntPlannedFinish) {
        this.acntPlannedFinish = acntPlannedFinish;
    }
    public String getAcntPlannedStart() {
        return getPropertyHtml("acntPlannedStart");
    }
    public void setAcntPlannedStart(String acntPlannedStart) {
        this.acntPlannedStart = acntPlannedStart;
    }
    public String getActualManmonth() {
        return getPropertyHtml("actualManmonth");
    }
    public void setActualManmonth(String actualManmonth) {
        this.actualManmonth = actualManmonth;
    }
    public String getApplicantName() {
        return applicantName;
    }
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
 
    public String getBDMName() {
        return getPropertyHtml("bdMName");
    }
    public void setBDMName(String name) {
        bdMName = name;
    }
    public String getBizSource() {
        return getPropertyHtml("bizSource");
    }
    public void setBizSource(String bizSource) {
        this.bizSource = bizSource;
    }
 
    public String getContract() {
        return getPropertyHtml("contract");
    }
    public void setContract(String contract) {
        this.contract = contract;
    }
    public String getContractEmail() {
        return getPropertyHtml("contractEmail");
    }
    public void setContractEmail(String contractEmail) {
        this.contractEmail = contractEmail;
    }
    public String getContractTel() {
        return getPropertyHtml("contractTel");
    }
    public void setContractTel(String contractTel) {
        this.contractTel = contractTel;
    }
    public String getCostAttachBd() {
        return getPropertyHtml("costAttachBd");
    }
    public void setCostAttachBd(String costAttachBd) {
        this.costAttachBd = costAttachBd;
    }
    public String getCustomerId() {
        return getPropertyHtml("customerId");
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCustServiceManagerName() {
        return getPropertyHtml("custServiceManagerName");
    }
    public void setCustServiceManagerName(String custServiceManagerName) {
        this.custServiceManagerName = custServiceManagerName;
    }
    public String getDevelopDataBase() {
        return getPropertyHtml("developDataBase");
    }
    public void setDevelopDataBase(String developDataBase) {
        this.developDataBase = developDataBase;
    }
    public String getDevelopJobSystem() {
        return getPropertyHtml("developJobSystem");
    }
    public void setDevelopJobSystem(String developJobSystem) {
        this.developJobSystem = developJobSystem;
    }
    public String getDevelopNetWork() {
        return getPropertyHtml("developNetWork");
    }
    public void setDevelopNetWork(String developNetWork) {
        this.developNetWork = developNetWork;
    }
    public String getDevelopOthers() {
        return getPropertyHtml("developOthers");
    }
    public void setDevelopOthers(String developOthers) {
        this.developOthers = developOthers;
    }
    public String getDevelopProgramLanguage() {
        return getPropertyHtml("developProgramLanguage");
    }
    public void setDevelopProgramLanguage(String developProgramLanguage) {
        this.developProgramLanguage = developProgramLanguage;
    }
    public String getDevelopTool() {
        return getPropertyHtml("developTool");
    }
    public void setDevelopTool(String developTool) {
        this.developTool = developTool;
    }
    public String getEngageManagerName() {
        return getPropertyHtml("engageManagerName");
    }
    public void setEngageManagerName(String engageManagerName) {
        this.engageManagerName = engageManagerName;
    }
    public String getEstManmonth() {
        return getPropertyHtml("estManmonth");
    }
    public void setEstManmonth(String estManmonth) {
        this.estManmonth = estManmonth;
    }
    public String getEstSize() {
        return getPropertyHtml("estSize");
    }
    public void setEstSize(String estSize) {
        this.estSize = estSize;
    }
    public String getExecSite() {
        return getPropertyHtml("execSite");
    }
    public void setExecSite(String execSite) {
        this.execSite = execSite;
    }
   
    public String getExecutive() {
        return getPropertyHtml("executive");
    }
    public void setExecutive(String executive) {
        this.executive = executive;
    }
    public String getExecutiveEmail() {
        return getPropertyHtml("executiveEmail");
    }
    public void setExecutiveEmail(String executiveEmail) {
        this.executiveEmail = executiveEmail;
    }
    public String getExecutiveTel() {
        return getPropertyHtml("executiveTel");
    }
    public void setExecutiveTel(String executiveTel) {
        this.executiveTel = executiveTel;
    }
    public String getFinancial() {
        return getPropertyHtml("financial");
    }
    public void setFinancial(String financial) {
        this.financial = financial;
    }
    public String getFinancialEmail() {
        return getPropertyHtml("financialEmail");
    }
    public void setFinancialEmail(String financialEmail) {
        this.financialEmail = financialEmail;
    }
    public String getFinancialTel() {
        return getPropertyHtml("financialTel");
    }
    public void setFinancialTel(String financialTel) {
        this.financialTel = financialTel;
    }
    public String getHardReq() {
        return getPropertyHtml("hardReq");
    }
    public void setHardReq(String hardReq) {
        this.hardReq = hardReq;
    }
    public String getLeaderName() {
        return getPropertyHtml("leaderName");
    }
    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
    public String getOriginalLanguage() {
        return getPropertyHtml("originalLanguage");
    }
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
    public String getOtherDesc() {
        return getPropertyHtml("otherDesc");
    }
    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }
  
    public String getPMName() {
        return getPropertyHtml("pmName");
    }
    public void setPMName(String name) {
        pmName = name;
    }
    public String getProductAttribute() {
        return getPropertyHtml("productAttribute");
    }
    public void setProductAttribute(String productAttribute) {
        this.productAttribute = productAttribute;
    }
    public String getProductName() {
        return getPropertyHtml("productName");
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductType() {
        return getPropertyHtml("productType");
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getProjectType() {
        return getPropertyHtml("projectType");
    }
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getSalesName() {
        return getPropertyHtml("salesName");
    }
    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }
    public String getSoftReq() {
        return getPropertyHtml("softReq");
    }
    public void setSoftReq(String softReq) {
        this.softReq = softReq;
    }
    public String getTCSName() {
        return getPropertyHtml("tcSName");
    }
    public void setTCSName(String name) {
        tcSName = name;
    }
    public String getTechnicalDomain() {
        return getPropertyHtml("technicalDomain");
    }
    public void setTechnicalDomain(String technicalDomain) {
        this.technicalDomain = technicalDomain;
    }
    public String getTranslationLanguage() {
        return getPropertyHtml("translationLanguage");
    }
    public void setTranslationLanguage(String translationLanguage) {
        this.translationLanguage = translationLanguage;
    }
    public String getTypeDataBase() {
        return getPropertyHtml("typeDataBase");
    }
    public void setTypeDataBase(String typeDataBase) {
        this.typeDataBase = typeDataBase;
    }
    public String getTypeJobSystem() {
        return getPropertyHtml("typeJobSystem");
    }
    public void setTypeJobSystem(String typeJobSystem) {
        this.typeJobSystem = typeJobSystem;
    }
    public String getTypeNetWork() {
        return getPropertyHtml("typeNetWork");
    }
    public void setTypeNetWork(String typeNetWork) {
        this.typeNetWork = typeNetWork;
    }
    public String getTypeOthers() {
        return getPropertyHtml("typeOthers");
    }
    public void setTypeOthers(String typeOthers) {
        this.typeOthers = typeOthers;
    }
    public String getTypeProgramLanguage() {
        return getPropertyHtml("typeProgramLanguage");
    }
    public void setTypeProgramLanguage(String typeProgramLanguage) {
        this.typeProgramLanguage = typeProgramLanguage;
    }
    public String getTypeTool() {
        return getPropertyHtml("typeTool");
    }
    public void setTypeTool(String typeTool) {
        this.typeTool = typeTool;
    }
    public String getWorkItem() {
        return getPropertyHtml("workItem");
    }
    public void setWorkItem(String workItem) {
        this.workItem = workItem;
    }

  
    public String getAcntBriefBefore() {
        return acntBriefBefore;
    }
    public void setAcntBriefBefore(String acntBriefBefore) {
        this.acntBriefBefore = acntBriefBefore;
    }
    public String getAcntFullNameBefore() {
        return acntFullNameBefore;
    }
    public void setAcntFullNameBefore(String acntFullNameBefore) {
        this.acntFullNameBefore = acntFullNameBefore;
    }

    public Double getActualManmonthBefore() {
        return actualManmonthBefore;
    }
    public void setActualManmonthBefore(Double actualManmonthBefore) {
        this.actualManmonthBefore = actualManmonthBefore;
    }
    public String getBDMNameBefore() {
        return bdMNameBefore;
    }
    public void setBDMNameBefore(String nameBefore) {
        bdMNameBefore = nameBefore;
    }
    public String getBizSourceBefore() {
        return bizSourceBefore;
    }
    public void setBizSourceBefore(String bizSourceBefore) {
        this.bizSourceBefore = bizSourceBefore;
    }
    public String getContractBefore() {
        return contractBefore;
    }
    public void setContractBefore(String contractBefore) {
        this.contractBefore = contractBefore;
    }
    public String getContractEmailBefore() {
        return contractEmailBefore;
    }
    public void setContractEmailBefore(String contractEmailBefore) {
        this.contractEmailBefore = contractEmailBefore;
    }
    public String getContractTelBefore() {
        return contractTelBefore;
    }
    public void setContractTelBefore(String contractTelBefore) {
        this.contractTelBefore = contractTelBefore;
    }
    public String getCostAttachBdBefore() {
        return costAttachBdBefore;
    }
    public void setCostAttachBdBefore(String costAttachBdBefore) {
        this.costAttachBdBefore = costAttachBdBefore;
    }
    public String getCustomerIdBefore() {
        return customerIdBefore;
    }
    public void setCustomerIdBefore(String customerIdBefore) {
        this.customerIdBefore = customerIdBefore;
    }
    public String getCustServiceManagerNameBefore() {
        return custServiceManagerNameBefore;
    }
    public void setCustServiceManagerNameBefore(String custServiceManagerNameBefore) {
        this.custServiceManagerNameBefore = custServiceManagerNameBefore;
    }
    public String getDevelopDataBaseBefore() {
        return developDataBaseBefore;
    }
    public void setDevelopDataBaseBefore(String developDataBaseBefore) {
        this.developDataBaseBefore = developDataBaseBefore;
    }
    public String getDevelopJobSystemBefore() {
        return developJobSystemBefore;
    }
    public void setDevelopJobSystemBefore(String developJobSystemBefore) {
        this.developJobSystemBefore = developJobSystemBefore;
    }
    public String getDevelopNetWorkBefore() {
        return developNetWorkBefore;
    }
    public void setDevelopNetWorkBefore(String developNetWorkBefore) {
        this.developNetWorkBefore = developNetWorkBefore;
    }
    public String getDevelopOthersBefore() {
        return developOthersBefore;
    }
    public void setDevelopOthersBefore(String developOthersBefore) {
        this.developOthersBefore = developOthersBefore;
    }
    public String getDevelopProgramLanguageBefore() {
        return developProgramLanguageBefore;
    }
    public void setDevelopProgramLanguageBefore(String developProgramLanguageBefore) {
        this.developProgramLanguageBefore = developProgramLanguageBefore;
    }
    public String getDevelopToolBefore() {
        return developToolBefore;
    }
    public void setDevelopToolBefore(String developToolBefore) {
        this.developToolBefore = developToolBefore;
    }
    public String getEngageManagerNameBefore() {
        return engageManagerNameBefore;
    }
    public void setEngageManagerNameBefore(String engageManagerNameBefore) {
        this.engageManagerNameBefore = engageManagerNameBefore;
    }
    public Double getEstManmonthBefore() {
        return estManmonthBefore;
    }
    public void setEstManmonthBefore(Double estManmonthBefore) {
        this.estManmonthBefore = estManmonthBefore;
    }
    public Long getEstSizeBefore() {
        return estSizeBefore;
    }
    public void setEstSizeBefore(Long estSizeBefore) {
        this.estSizeBefore = estSizeBefore;
    }
    public String getExecSiteBefore() {
        return execSiteBefore;
    }
    public void setExecSiteBefore(String execSiteBefore) {
        this.execSiteBefore = execSiteBefore;
    }
    public String getExecutiveBefore() {
        return executiveBefore;
    }
    public void setExecutiveBefore(String executiveBefore) {
        this.executiveBefore = executiveBefore;
    }
    public String getExecutiveEmailBefore() {
        return executiveEmailBefore;
    }
    public void setExecutiveEmailBefore(String executiveEmailBefore) {
        this.executiveEmailBefore = executiveEmailBefore;
    }
    public String getExecutiveTelBefore() {
        return executiveTelBefore;
    }
    public void setExecutiveTelBefore(String executiveTelBefore) {
        this.executiveTelBefore = executiveTelBefore;
    }
    public String getFinancialBefore() {
        return financialBefore;
    }
    public void setFinancialBefore(String financialBefore) {
        this.financialBefore = financialBefore;
    }
    public String getFinancialEmailBefore() {
        return financialEmailBefore;
    }
    public void setFinancialEmailBefore(String financialEmailBefore) {
        this.financialEmailBefore = financialEmailBefore;
    }
    public String getFinancialTelBefore() {
        return financialTelBefore;
    }
    public void setFinancialTelBefore(String financialTelBefore) {
        this.financialTelBefore = financialTelBefore;
    }
    public String getHardReqBefore() {
        return hardReqBefore;
    }
    public void setHardReqBefore(String hardReqBefore) {
        this.hardReqBefore = hardReqBefore;
    }
    public String getLeaderNameBefore() {
        return leaderNameBefore;
    }
    public void setLeaderNameBefore(String leaderNameBefore) {
        this.leaderNameBefore = leaderNameBefore;
    }
    public String getOriginalLanguageBefore() {
        return originalLanguageBefore;
    }
    public void setOriginalLanguageBefore(String originalLanguageBefore) {
        this.originalLanguageBefore = originalLanguageBefore;
    }
    public String getOtherDescBefore() {
        return otherDescBefore;
    }
    public void setOtherDescBefore(String otherDescBefore) {
        this.otherDescBefore = otherDescBefore;
    }
    public String getPMNameBefore() {
        return pmNameBefore;
    }
    public void setPMNameBefore(String nameBefore) {
        pmNameBefore = nameBefore;
    }
    public String getProductAttributeBefore() {
        return productAttributeBefore;
    }
    public void setProductAttributeBefore(String productAttributeBefore) {
        this.productAttributeBefore = productAttributeBefore;
    }
    public String getProductNameBefore() {
        return productNameBefore;
    }
    public void setProductNameBefore(String productNameBefore) {
        this.productNameBefore = productNameBefore;
    }
    public String getProductTypeBefore() {
        return productTypeBefore;
    }
    public void setProductTypeBefore(String productTypeBefore) {
        this.productTypeBefore = productTypeBefore;
    }
    public String getProjectTypeBefore() {
        return projectTypeBefore;
    }
    public void setProjectTypeBefore(String projectTypeBefore) {
        this.projectTypeBefore = projectTypeBefore;
    }
    public String getSalesNameBefore() {
        return salesNameBefore;
    }
    public void setSalesNameBefore(String salesNameBefore) {
        this.salesNameBefore = salesNameBefore;
    }
    public String getSoftReqBefore() {
        return softReqBefore;
    }
    public void setSoftReqBefore(String softReqBefore) {
        this.softReqBefore = softReqBefore;
    }
    public String getTCSNameBefore() {
        return tcSNameBefore;
    }
    public void setTCSNameBefore(String nameBefore) {
        tcSNameBefore = nameBefore;
    }
    public String getTechnicalDomainBefore() {
        return technicalDomainBefore;
    }
    public void setTechnicalDomainBefore(String technicalDomainBefore) {
        this.technicalDomainBefore = technicalDomainBefore;
    }
    public String getTranslationLanguageBefore() {
        return translationLanguageBefore;
    }
    public void setTranslationLanguageBefore(String translationLanguageBefore) {
        this.translationLanguageBefore = translationLanguageBefore;
    }
    public String getTypeDataBaseBefore() {
        return typeDataBaseBefore;
    }
    public void setTypeDataBaseBefore(String typeDataBaseBefore) {
        this.typeDataBaseBefore = typeDataBaseBefore;
    }
    public String getTypeJobSystemBefore() {
        return typeJobSystemBefore;
    }
    public void setTypeJobSystemBefore(String typeJobSystemBefore) {
        this.typeJobSystemBefore = typeJobSystemBefore;
    }
    public String getTypeNetWorkBefore() {
        return typeNetWorkBefore;
    }
    public void setTypeNetWorkBefore(String typeNetWorkBefore) {
        this.typeNetWorkBefore = typeNetWorkBefore;
    }
    public String getTypeOthersBefore() {
        return typeOthersBefore;
    }
    public void setTypeOthersBefore(String typeOthersBefore) {
        this.typeOthersBefore = typeOthersBefore;
    }
    public String getTypeProgramLanguageBefore() {
        return typeProgramLanguageBefore;
    }
    public void setTypeProgramLanguageBefore(String typeProgramLanguageBefore) {
        this.typeProgramLanguageBefore = typeProgramLanguageBefore;
    }
    public String getTypeToolBefore() {
        return typeToolBefore;
    }
    public void setTypeToolBefore(String typeToolBefore) {
        this.typeToolBefore = typeToolBefore;
    }
    public String getWorkItemBefore() {
        return workItemBefore;
    }
    public void setWorkItemBefore(String workItemBefore) {
        this.workItemBefore = workItemBefore;
    }
    public String getAcntActualFinishBefore() {
        return acntActualFinishBefore;
    }
    public void setAcntActualFinishBefore(String acntActualFinishBefore) {
        this.acntActualFinishBefore = acntActualFinishBefore;
    }
    public String getAcntActualStartBefore() {
        return acntActualStartBefore;
    }
    public void setAcntActualStartBefore(String acntActualStartBefore) {
        this.acntActualStartBefore = acntActualStartBefore;
    }
    public String getAcntPlannedFinishBefore() {
        return acntPlannedFinishBefore;
    }
    public void setAcntPlannedFinishBefore(String acntPlannedFinishBefore) {
        this.acntPlannedFinishBefore = acntPlannedFinishBefore;
    }
    public String getAcntPlannedStartBefore() {
        return acntPlannedStartBefore;
    }
    public void setAcntPlannedStartBefore(String acntPlannedStartBefore) {
        this.acntPlannedStartBefore = acntPlannedStartBefore;
    }
    public String getApplicationDate() {
        return applicationDate;
    }
    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }
    public String getAcntAttribute() {
        return getPropertyHtml("acntAttribute");
    }
    public void setAcntAttribute(String acntAttribute) {
        this.acntAttribute = acntAttribute;
    }
    public String getAcntAttributeBefore() {
        return acntAttributeBefore;
    }
    public void setAcntAttributeBefore(String acntAttributeBefore) {
        this.acntAttributeBefore = acntAttributeBefore;
    }

	public String getAchieveBelong() {
		return getPropertyHtml("achieveBelong");
	}

	public void setAchieveBelong(String achieveBelong) {
		this.achieveBelong = achieveBelong;
	}

	public String getAchieveBelongBefore() {
		return achieveBelongBefore;
	}

	public void setAchieveBelongBefore(String achieveBelongBefore) {
		this.achieveBelongBefore = achieveBelongBefore;
	}

	public String getBillingBasisBefore() {
		return billingBasisBefore;
	}

	public void setBillingBasisBefore(String billingBasisBefore) {
		this.billingBasisBefore = billingBasisBefore;
	}

	public String getPrimaveraAdaptedBefore() {
		return primaveraAdaptedBefore;
	}

	public void setPrimaveraAdaptedBefore(String primaveraAdaptedBefore) {
		this.primaveraAdaptedBefore = primaveraAdaptedBefore;
	}

	public String getBillingBasis() {
		return getPropertyHtml("billingBasis");
	}

	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	public String getPrimaveraAdapted() {
		return getPropertyHtml("primaveraAdapted");
	}

	public void setPrimaveraAdapted(String primaveraAdapted) {
		this.primaveraAdapted = primaveraAdapted;
	}

	public String getBillType() {
		return getPropertyHtml("billType");
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillTypeBefore() {
		return billTypeBefore;
	}

	public void setBillTypeBefore(String billTypeBefore) {
		this.billTypeBefore = billTypeBefore;
	}

	public String getBizProperty() {
		return getPropertyHtml("bizProperty");
	}

	public void setBizProperty(String bizProperty) {
		this.bizProperty = bizProperty;
	}

	public String getBizPropertyBefore() {
		return bizPropertyBefore;
	}

	public void setBizPropertyBefore(String bizPropertyBefore) {
		this.bizPropertyBefore = bizPropertyBefore;
	}

	public String getExecUnit() {
		return getPropertyHtml("execUnit");
	}

	public void setExecUnit(String execUnit) {
		this.execUnit = execUnit;
	}

	public String getExecUnitBefore() {
		return execUnitBefore;
	}

	public void setExecUnitBefore(String execUnitBefore) {
		this.execUnitBefore = execUnitBefore;
	}

	public String getBl() {
		return getPropertyHtml("bl");
	}

	public void setBl(String bl) {
		this.bl = bl;
	}

	public String getBlBefore() {
		return blBefore;
	}

	public void setBlBefore(String blBefore) {
		this.blBefore = blBefore;
	}
    

   
    
   

}
