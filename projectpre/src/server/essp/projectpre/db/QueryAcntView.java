package server.essp.projectpre.db;

import java.util.Date;

import com.wits.util.comDate;

import server.essp.projectpre.service.constant.Constant;


/**
 * QueryAcntView generated by MyEclipse - Hibernate Tools
 */

public class QueryAcntView  implements java.io.Serializable {


    // Fields    

     private String acntId;
     private String appName;
     private String appLoginid;
     private String acntStatus;
     private Date applicationDate;
     private String achieveBelong;
     private String pmName;
     private String pmLoginid;
     private String pmDomain;
     private String deptLoginid;
     private String acntName;
     private String acntFullName;
     private String tcName;
     private String bdName;
     private String bdLoginid;
     private String productName;
     private String acntBrief;
     private String execUnitId;
     private String execSite;
     private String siteName;
     private String costAttachBd;
     private String salesName;
     private String serviceManagerName;
     private String engageManagerName;
     private String bizSource;
     private String relParentId;
     private String contractAcntId;
     private String acntAttribute;
     private String customerId;
     private String cusShort;
     private String cusNamecn;
     private String cusNameen;
     private String cusGroupid;
     private String cusCountry;
     private String cusDesc;
     private String leaderName;
     private String projectType;
     private String projectTypeName;
     private String productType;
     private String productTypeName;
     private String productAttribute;
     private String productAttributeName;
     private String workItem;
     private String workItemName;
     private String technicalDomain;
     private String technicalDomainName;
     private String originalLanguage;
     private String originalLanguageName;
     private String translationLanguage;
     private String translationLanguageName;
     private String devJob;
     private String devDb;
     private String devTool;
     private String devNetwork;
     private String devPl;
     private String devOthers;
     private String tranJob;
     private String tranDb;
     private String tranTool;
     private String tranNetwork;
     private String tranPl;
     private String tranOthers;
     private String hardreq;
     private String softreq;
     private Date acntPlannedStart;
     private Date acntPlannedFinish;
     private Date acntActualStart;
     private Date acntActualFinish;
     private Long estSize;
     private String otherDesc;
     private String hasAttachment;
     private Double estManmonth;
     private String primaveraAdapted;
     private String billingBasis;
     private String isAcnt;
     private String bizProperty;
     private String billType;
     private String relPrjType;
     
     public String getRelPrjType() {
		return relPrjType;
	}
	public void setRelPrjType(String relPrjType) {
		this.relPrjType = relPrjType;
	}
	public String getBizProperty() {
		return bizProperty;
	}
	public void setBizProperty(String bizProperty) {
		this.bizProperty = bizProperty;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getIsAcnt() {
		return isAcnt;
	}
	public void setIsAcnt(String isAcnt) {
		this.isAcnt = isAcnt;
	}
	public String getPrimaveraAdaptedValue() {
    	if("1".equals(getPrimaveraAdapted())) {
    		return "Y";
    	}
 		return "N";
 	}
     public String getBillingBasisValue() {
    	 if("1".equals(getBillingBasis())) {
     		return "Y";
     	}
  		return "N";
 	}
     public String getBillingBasis() {
		return billingBasis;
	}

	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	public String getPrimaveraAdapted() {
		return primaveraAdapted;
	}

	public void setPrimaveraAdapted(String primaveraAdapted) {
		this.primaveraAdapted = primaveraAdapted;
	}

	public Double getEstManmonth() {
        return estManmonth;
    }

    public void setEstManmonth(Double estManmonth) {
        this.estManmonth = estManmonth;
    }

    public String getHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(String hasAttachment) {
        this.hasAttachment = hasAttachment;
    }
    public String getPlannedStartString(){
        return comDate.dateToString(this.getAcntPlannedStart(), "yyyy-MM-dd");
    }
    public String getPlannedFinishString(){
        return comDate.dateToString(this.getAcntPlannedFinish(), "yyyy-MM-dd");
    }
    /**
      * ��nullת�ɿմ�
      * @param str
      * @return
      */
     private String converNull(String str){
         if(str==null){
             return "";
         } 
         return str;
     }
     public String getEstManmonthValue() {
         if(this.getEstManmonth()==null){
             return "0.0";
         }
         return this.getEstManmonth().toString();
     }
    public String getAnticipateWorkQuantity() {
        if(this.getEstSize()==null){
            return "0";
        }
        return this.getEstSize().toString();
    }

    public String getCustomerToolTip() {
        String temp = "CustomerID:"+converNull(this.getCustomerId())+"\r\n"
                    +"NameCn:"+converNull(this.getCusNamecn())+"\r\n"
                    +"NameEn:"+converNull(this.getCusNameen())+"\r\n"
                    +"GroupId:"+converNull(this.getCusGroupid())+"\r\n"
                    +"Country:"+converNull(this.getCusCountry())+"\r\n"
                    +"CusDesc:"+converNull(this.getCusDesc());
       return temp;
   }
    public String getProjectScheduleAnticipate() {
         String dateTem = comDate.dateToString(this.getAcntPlannedStart(), "yyyy-MM-dd")
                     +"~"+comDate.dateToString(this.getAcntPlannedFinish(), "yyyy-MM-dd");
         return dateTem;
     }

     public String getProjectScheduleFact() {
         String dateTem = null;
         if(this.getAcntActualStart()==null&&this.getAcntActualFinish()==null){
             dateTem = "";
         } else {
             dateTem = comDate.dateToString(this.getAcntActualStart(), "yyyy-MM-dd")
                     +"~"+comDate.dateToString(this.getAcntActualFinish(), "yyyy-MM-dd");
         }
         return dateTem;
     }
     public String getTranslatePublishTypeToolTip() {
         StringBuffer temp = new StringBuffer();
         temp.append(Constant.JOBSYSTEM+":"+converNull(this.getTranJob()));
         temp.append("\r\n"+Constant.DATABASE+":"+converNull(this.getTranDb()));
         temp.append("\r\n"+Constant.TOOL+":"+converNull(this.getTranTool()));
         temp.append("\r\n"+Constant.NETWORK+":"+converNull(this.getTranNetwork()));
         temp.append("\r\n"+Constant.PROGRAMLANGUAGE+":"+converNull(this.getTranPl()));
         temp.append("\r\n"+Constant.OTHERS+":"+converNull(this.getTranOthers()));
         return temp.toString();
     }

     public String getTranslatePublishType() {
         String temp = Constant.JOBSYSTEM+":"+converNull(this.getTranJob());
         return temp;
     }

     public String getDevelopEnvironmentToolTip() {
         StringBuffer temp = new StringBuffer();
         temp.append(Constant.JOBSYSTEM+":"+converNull(this.getDevJob()));
         temp.append("\r\n"+Constant.DATABASE+":"+converNull(this.getDevDb()));
         temp.append("\r\n"+Constant.TOOL+":"+converNull(this.getDevTool()));
         temp.append("\r\n"+Constant.NETWORK+":"+converNull(this.getDevNetwork()));
         temp.append("\r\n"+Constant.PROGRAMLANGUAGE+":"+converNull(this.getDevPl()));
         temp.append("\r\n"+Constant.OTHERS+":"+converNull(this.getDevOthers()));
         return temp.toString();
     }

     public String getDevelopEnvironment() {
         String temp = Constant.JOBSYSTEM+":"+converNull(this.getDevJob());
         return temp;
     }


     public String getSupportLanguage() {
         String temp = Constant.ORIGINAL_LANGUAGE+":"+converNull(this.getOriginalLanguageName())+","
                      +Constant.TRANSLATION_LANGUANGE+":"+converNull(this.getTranslationLanguageName());
         return temp;
     }

    // Constructors

    /** default constructor */
    public QueryAcntView() {
    }

	/** minimal constructor */
    public QueryAcntView(String acntId, String acntName) {
        this.acntId = acntId;
        this.acntName = acntName;
    }
    
    /** full constructor */
    public QueryAcntView(String acntId, String appName, String appLoginid, String acntStatus, Date applicationDate, String achieveBelong, String pmName, String pmLoginid, String pmDomain, String deptLoginid, String acntName, String acntFullName, String tcName, String bdName, String bdLoginid, String productName, String acntBrief, String execUnitId, String execSite, String siteName,String costAttachBd, String salesName, String serviceManagerName, String engageManagerName, String bizSource, String relParentId, String contractAcntId, String acntAttribute, String customerId, String cusShort, String cusNamecn, String cusNameen, String cusGroupid, String cusCountry, String cusDesc, String leaderName, String projectType, String projectTypeName, String productType, String productTypeName, String productAttribute, String productAttributeName, String workItem, String workItemName, String technicalDomain, String technicalDomainName, String originalLanguage, String originalLanguageName, String translationLanguage, String translationLanguageName, String devJob, String devDb, String devTool, String devNetwork, String devPl, String devOthers, String tranJob, String tranDb, String tranTool, String tranNetwork, String tranPl, String tranOthers, String hardreq, String softreq, Date acntPlannedStart, Date acntPlannedFinish, Date acntActualStart, Date acntActualFinish, Long estSize, String otherDesc, String hasAttachment, Double estManmonth, String primaveraAdapted, String billingBasis, String isAcnt) {
        this.acntId = acntId;
        this.appName = appName;
        this.appLoginid = appLoginid;
        this.acntStatus = acntStatus;
        this.applicationDate = applicationDate;
        this.achieveBelong = achieveBelong;
        this.pmName = pmName;
        this.pmLoginid = pmLoginid;
        this.pmDomain = pmDomain;
        this.deptLoginid = deptLoginid;
        this.acntName = acntName;
        this.acntFullName = acntFullName;
        this.tcName = tcName;
        this.bdName = bdName;
        this.bdLoginid = bdLoginid;
        this.productName = productName;
        this.acntBrief = acntBrief;
        this.execUnitId = execUnitId;
        this.execSite = execSite;
        this.siteName = siteName;
        this.costAttachBd = costAttachBd;
        this.salesName = salesName;
        this.serviceManagerName = serviceManagerName;
        this.engageManagerName = engageManagerName;
        this.bizSource = bizSource;
        this.relParentId = relParentId;
        this.contractAcntId = contractAcntId;
        this.acntAttribute = acntAttribute;
        this.customerId = customerId;
        this.cusShort = cusShort;
        this.cusNamecn = cusNamecn;
        this.cusNameen = cusNameen;
        this.cusGroupid = cusGroupid;
        this.cusCountry = cusCountry;
        this.cusDesc = cusDesc;
        this.leaderName = leaderName;
        this.projectType = projectType;
        this.projectTypeName = projectTypeName;
        this.productType = productType;
        this.productTypeName = productTypeName;
        this.productAttribute = productAttribute;
        this.productAttributeName = productAttributeName;
        this.workItem = workItem;
        this.workItemName = workItemName;
        this.technicalDomain = technicalDomain;
        this.technicalDomainName = technicalDomainName;
        this.originalLanguage = originalLanguage;
        this.originalLanguageName = originalLanguageName;
        this.translationLanguage = translationLanguage;
        this.translationLanguageName = translationLanguageName;
        this.devJob = devJob;
        this.devDb = devDb;
        this.devTool = devTool;
        this.devNetwork = devNetwork;
        this.devPl = devPl;
        this.devOthers = devOthers;
        this.tranJob = tranJob;
        this.tranDb = tranDb;
        this.tranTool = tranTool;
        this.tranNetwork = tranNetwork;
        this.tranPl = tranPl;
        this.tranOthers = tranOthers;
        this.hardreq = hardreq;
        this.softreq = softreq;
        this.acntPlannedStart = acntPlannedStart;
        this.acntPlannedFinish = acntPlannedFinish;
        this.acntActualStart = acntActualStart;
        this.acntActualFinish = acntActualFinish;
        this.estSize = estSize;
        this.otherDesc = otherDesc;
        this.hasAttachment = hasAttachment;
        this.estManmonth = estManmonth;
        this.primaveraAdapted = primaveraAdapted;
        this.billingBasis = billingBasis;
        this.isAcnt = isAcnt;
    }

   
    // Property accessors

    public String getAcntId() {
        return this.acntId;
    }
    
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }

    public String getAppName() {
        return this.appName;
    }
    
    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppLoginid() {
        return this.appLoginid;
    }
    
    public void setAppLoginid(String appLoginid) {
        this.appLoginid = appLoginid;
    }

    public String getAcntStatus() {
        return this.acntStatus;
    }
    
    public void setAcntStatus(String acntStatus) {
        this.acntStatus = acntStatus;
    }

    public Date getApplicationDate() {
        return this.applicationDate;
    }
    
    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getAchieveBelong() {
        return this.achieveBelong;
    }
    
    public void setAchieveBelong(String achieveBelong) {
        this.achieveBelong = achieveBelong;
    }

    public String getPmName() {
        return this.pmName;
    }
    
    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getPmLoginid() {
        return this.pmLoginid;
    }
    
    public void setPmLoginid(String pmLoginid) {
        this.pmLoginid = pmLoginid;
    }

    public String getDeptLoginid() {
        return this.deptLoginid;
    }
    
    public void setDeptLoginid(String deptLoginid) {
        this.deptLoginid = deptLoginid;
    }

    public String getAcntName() {
        return this.acntName;
    }
    
    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public String getAcntFullName() {
        return this.acntFullName;
    }
    
    public void setAcntFullName(String acntFullName) {
        this.acntFullName = acntFullName;
    }

    public String getTcName() {
        return this.tcName;
    }
    
    public void setTcName(String tcName) {
        this.tcName = tcName;
    }

    public String getBdName() {
        return this.bdName;
    }
    
    public void setBdName(String bdName) {
        this.bdName = bdName;
    }

    public String getBdLoginid() {
        return this.bdLoginid;
    }
    
    public void setBdLoginid(String bdLoginid) {
        this.bdLoginid = bdLoginid;
    }

    public String getProductName() {
        return this.productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAcntBrief() {
        return this.acntBrief;
    }
    
    public void setAcntBrief(String acntBrief) {
        this.acntBrief = acntBrief;
    }

    public String getExecUnitId() {
        return this.execUnitId;
    }
    
    public void setExecUnitId(String execUnitId) {
        this.execUnitId = execUnitId;
    }

    public String getExecSite() {
        return this.execSite;
    }
    
    public void setExecSite(String execSite) {
        this.execSite = execSite;
    }

    public String getCostAttachBd() {
        return this.costAttachBd;
    }
    
    public void setCostAttachBd(String costAttachBd) {
        this.costAttachBd = costAttachBd;
    }

    public String getSalesName() {
        return this.salesName;
    }
    
    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getServiceManagerName() {
        return this.serviceManagerName;
    }
    
    public void setServiceManagerName(String serviceManagerName) {
        this.serviceManagerName = serviceManagerName;
    }

    public String getEngageManagerName() {
        return this.engageManagerName;
    }
    
    public void setEngageManagerName(String engageManagerName) {
        this.engageManagerName = engageManagerName;
    }

    public String getBizSource() {
        return this.bizSource;
    }
    
    public void setBizSource(String bizSource) {
        this.bizSource = bizSource;
    }

    public String getRelParentId() {
        return this.relParentId;
    }
    
    public void setRelParentId(String relParentId) {
        this.relParentId = relParentId;
    }

    public String getContractAcntId() {
        return this.contractAcntId;
    }
    
    public void setContractAcntId(String contractAcntId) {
        this.contractAcntId = contractAcntId;
    }

    public String getAcntAttribute() {
        return this.acntAttribute;
    }
    
    public void setAcntAttribute(String acntAttribute) {
        this.acntAttribute = acntAttribute;
    }

    public String getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCusShort() {
        return this.cusShort;
    }
    
    public void setCusShort(String cusShort) {
        this.cusShort = cusShort;
    }

    public String getCusNamecn() {
        return this.cusNamecn;
    }
    
    public void setCusNamecn(String cusNamecn) {
        this.cusNamecn = cusNamecn;
    }

    public String getCusNameen() {
        return this.cusNameen;
    }
    
    public void setCusNameen(String cusNameen) {
        this.cusNameen = cusNameen;
    }

    public String getCusGroupid() {
        return this.cusGroupid;
    }
    
    public void setCusGroupid(String cusGroupid) {
        this.cusGroupid = cusGroupid;
    }

    public String getCusCountry() {
        return this.cusCountry;
    }
    
    public void setCusCountry(String cusCountry) {
        this.cusCountry = cusCountry;
    }

    public String getCusDesc() {
        return this.cusDesc;
    }
    
    public void setCusDesc(String cusDesc) {
        this.cusDesc = cusDesc;
    }

    public String getLeaderName() {
        return this.leaderName;
    }
    
    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getProjectType() {
        return this.projectType;
    }
    
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeName() {
        return this.projectTypeName;
    }
    
    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getProductType() {
        return this.productType;
    }
    
    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductTypeName() {
        return this.productTypeName;
    }
    
    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductAttribute() {
        return this.productAttribute;
    }
    
    public void setProductAttribute(String productAttribute) {
        this.productAttribute = productAttribute;
    }

    public String getProductAttributeName() {
        return this.productAttributeName;
    }
    
    public void setProductAttributeName(String productAttributeName) {
        this.productAttributeName = productAttributeName;
    }

    public String getWorkItem() {
        return this.workItem;
    }
    
    public void setWorkItem(String workItem) {
        this.workItem = workItem;
    }

    public String getWorkItemName() {
        return this.workItemName;
    }
    
    public void setWorkItemName(String workItemName) {
        this.workItemName = workItemName;
    }

    public String getTechnicalDomain() {
        return this.technicalDomain;
    }
    
    public void setTechnicalDomain(String technicalDomain) {
        this.technicalDomain = technicalDomain;
    }

    public String getTechnicalDomainName() {
        return this.technicalDomainName;
    }
    
    public void setTechnicalDomainName(String technicalDomainName) {
        this.technicalDomainName = technicalDomainName;
    }

    public String getOriginalLanguage() {
        return this.originalLanguage;
    }
    
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguageName() {
        return this.originalLanguageName;
    }
    
    public void setOriginalLanguageName(String originalLanguageName) {
        this.originalLanguageName = originalLanguageName;
    }

    public String getTranslationLanguage() {
        return this.translationLanguage;
    }
    
    public void setTranslationLanguage(String translationLanguage) {
        this.translationLanguage = translationLanguage;
    }

    public String getTranslationLanguageName() {
        return this.translationLanguageName;
    }
    
    public void setTranslationLanguageName(String translationLanguageName) {
        this.translationLanguageName = translationLanguageName;
    }

    public String getDevJob() {
        return this.devJob;
    }
    
    public void setDevJob(String devJob) {
        this.devJob = devJob;
    }

    public String getDevDb() {
        return this.devDb;
    }
    
    public void setDevDb(String devDb) {
        this.devDb = devDb;
    }

    public String getDevTool() {
        return this.devTool;
    }
    
    public void setDevTool(String devTool) {
        this.devTool = devTool;
    }

    public String getDevNetwork() {
        return this.devNetwork;
    }
    
    public void setDevNetwork(String devNetwork) {
        this.devNetwork = devNetwork;
    }

    public String getDevPl() {
        return this.devPl;
    }
    
    public void setDevPl(String devPl) {
        this.devPl = devPl;
    }

    public String getDevOthers() {
        return this.devOthers;
    }
    
    public void setDevOthers(String devOthers) {
        this.devOthers = devOthers;
    }

    public String getTranJob() {
        return this.tranJob;
    }
    
    public void setTranJob(String tranJob) {
        this.tranJob = tranJob;
    }

    public String getTranDb() {
        return this.tranDb;
    }
    
    public void setTranDb(String tranDb) {
        this.tranDb = tranDb;
    }

    public String getTranTool() {
        return this.tranTool;
    }
    
    public void setTranTool(String tranTool) {
        this.tranTool = tranTool;
    }

    public String getTranNetwork() {
        return this.tranNetwork;
    }
    
    public void setTranNetwork(String tranNetwork) {
        this.tranNetwork = tranNetwork;
    }

    public String getTranPl() {
        return this.tranPl;
    }
    
    public void setTranPl(String tranPl) {
        this.tranPl = tranPl;
    }

    public String getTranOthers() {
        return this.tranOthers;
    }
    
    public void setTranOthers(String tranOthers) {
        this.tranOthers = tranOthers;
    }

    public String getHardreq() {
        return this.hardreq;
    }
    
    public void setHardreq(String hardreq) {
        this.hardreq = hardreq;
    }

    public String getSoftreq() {
        return this.softreq;
    }
    
    public void setSoftreq(String softreq) {
        this.softreq = softreq;
    }

    public Date getAcntPlannedStart() {
        return this.acntPlannedStart;
    }
    
    public void setAcntPlannedStart(Date acntPlannedStart) {
        this.acntPlannedStart = acntPlannedStart;
    }

    public Date getAcntPlannedFinish() {
        return this.acntPlannedFinish;
    }
    
    public void setAcntPlannedFinish(Date acntPlannedFinish) {
        this.acntPlannedFinish = acntPlannedFinish;
    }

    public Date getAcntActualStart() {
        return this.acntActualStart;
    }
    
    public void setAcntActualStart(Date acntActualStart) {
        this.acntActualStart = acntActualStart;
    }

    public Date getAcntActualFinish() {
        return this.acntActualFinish;
    }
    
    public void setAcntActualFinish(Date acntActualFinish) {
        this.acntActualFinish = acntActualFinish;
    }

    public Long getEstSize() {
        return this.estSize;
    }
    
    public void setEstSize(Long estSize) {
        this.estSize = estSize;
    }

    public String getOtherDesc() {
        return this.otherDesc;
    }
    
    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }
    public String getSiteName() {
        return siteName;
    }
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPmDomain() {
        return pmDomain;
    }

    public void setPmDomain(String pmDomain) {
        this.pmDomain = pmDomain;
    }
   








}