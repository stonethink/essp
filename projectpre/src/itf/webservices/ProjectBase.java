package itf.webservices;

import com.wits.util.*;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ProjectBase {
    public ProjectBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private String projId; //专案代码
    private String projName; //专案全称
    private String nickName; //专案简称
    private String manager; //执行单位
    private String leader; //业务经理
    private String costDept; //成本归属单位
    private String tcSigner; //工时表签核者
    private String planFrom; //专案预计开始日期
    private String planTo; //专案预计结束日期
    private String custShort; //客户资料简称
    private Double estManmonth; //预计人月
    private String closeMark; //是否结案
    private String achieveBelong;
    private String sales;
    private String actuFrom;
    private String actuTo;
    private Double manMonth;
    private String productName;
    private String projectDesc;
    private String applicant;
    private String divisionManager;
    private String projectManager;
    private String projectExecUnit;
    private String custServiceManager;
    private String engageManager;
    private String bizSource;
    private String masterProjects;
    private String projectProperty;
    private Double estimatedWords;
    private String projectType;
    private String projTypeNo;
    private String productType;
    private String productProperty;
    private String workItem;
    private String skillDomain;
    private String originalLan;
    private String transLan;
    private String hardWareReq;
    private String softWareReq;
    private String devEnvOs;
    private String devEnvDb;
    private String devEnvTool;
    private String devEnvNetwork;
    private String devEnvLang;
    private String devEnvOther;
    private String tranEnvOs;
    private String tranEnvDb;
    private String tranEnvTool;
    private String tranEnvNetwork;
    private String tranEnvLang;
    private String tranEnvOther;
    private String ccontact;
    private String ccontTel;
    private String ccontEmail;
    private String pcontact;
    private String pcontTel;
    private String pcontEmail;
    private String icontact;
    private String icontTel;
    private String icontEmail;

    public String getCustShort() {
        return StringUtil.nvl(custShort);
    }

    public void setCustShort(String custShort) {
        this.custShort = custShort;
    }

    public Double getEstManmonth() {
        if (estManmonth == null) {
            return new Double(0);
        }
        return estManmonth;
    }

    public void setEstManmonth(Double estManmonth) {
        this.estManmonth = estManmonth;
    }

    public String getLeader() {
        return StringUtil.nvl(leader);
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getNickName() {
        return StringUtil.nvl(nickName);
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProjId() {
        return StringUtil.nvl(projId);
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return StringUtil.nvl(projName);
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getTcSigner() {
        return StringUtil.nvl(tcSigner);
    }

    public void setTcSigner(String tcSigner) {
        this.tcSigner = tcSigner;
    }

    public String getCostDept() {
        return StringUtil.nvl(costDept);
    }

    public void setCostDept(String costDept) {
        this.costDept = costDept;
    }

    public String getManager() {
        return StringUtil.nvl(manager);
    }

    public String getCloseMark() {
        return StringUtil.nvl(closeMark);
    }

    public String getPlanFrom() {
        return StringUtil.nvl(planFrom);
    }

    public String getPlanTo() {
        return StringUtil.nvl(planTo);
    }

    public String getAchieveBelong() {
        return StringUtil.nvl(achieveBelong);
    }

    public String getActuFrom() {
        return StringUtil.nvl(actuFrom);
    }

    public String getActuTo() {
        return StringUtil.nvl(actuTo);
    }

    public String getApplicant() {
        return StringUtil.nvl(applicant);
    }

    public String getBizSource() {
        return StringUtil.nvl(bizSource);
    }

    public String getCcontact() {
        return StringUtil.nvl(ccontact);
    }

    public String getCcontEmail() {
        return StringUtil.nvl(ccontEmail);
    }

    public String getCcontTel() {
        return StringUtil.nvl(ccontTel);
    }

    public String getCustServiceManager() {
        return StringUtil.nvl(custServiceManager);
    }

    public String getDevEnvDb() {
        return StringUtil.nvl(devEnvDb);
    }

    public String getDevEnvLang() {
        return StringUtil.nvl(devEnvLang);
    }

    public String getDevEnvNetwork() {
        return StringUtil.nvl(devEnvNetwork);
    }

    public String getDevEnvOs() {
        return StringUtil.nvl(devEnvOs);
    }

    public String getDevEnvOther() {
        return StringUtil.nvl(devEnvOther);
    }

    public String getDevEnvTool() {
        return StringUtil.nvl(devEnvTool);
    }

    public String getDivisionManager() {
        return StringUtil.nvl(divisionManager);
    }

    public String getEngageManager() {
        return StringUtil.nvl(engageManager);
    }

    public Double getEstimatedWords() {
        if(estimatedWords==null){
        return new Double(0);
        }
        return estimatedWords;
    }

    public String getHardWareReq() {
        return StringUtil.nvl(hardWareReq);
    }

    public String getIcontact() {
        return StringUtil.nvl(icontact);
    }

    public String getIcontEmail() {
        return StringUtil.nvl(icontEmail);
    }

    public String getIcontTel() {
        return StringUtil.nvl(icontTel);
    }

    public Double getManMonth() {
        if(manMonth==null){
            return new Double(0);
        }
        return manMonth;
    }

    public String getMasterProjects() {
        return StringUtil.nvl(masterProjects);
    }

    public String getOriginalLan() {
        return StringUtil.nvl(originalLan);
    }

    public String getPcontact() {
        return StringUtil.nvl(pcontact);
    }

    public String getPcontEmail() {
        return StringUtil.nvl(pcontEmail);
    }

    public String getPcontTel() {
        return StringUtil.nvl(pcontTel);
    }

    public String getProductName() {
        return StringUtil.nvl(productName);
    }

    public String getProductProperty() {
        return StringUtil.nvl(productProperty);
    }

    public String getProductType() {
        return StringUtil.nvl(productType);
    }

    public String getProjectDesc() {
        return StringUtil.nvl(projectDesc);
    }

    public String getProjectExecUnit() {
        return StringUtil.nvl(projectExecUnit);
    }

    public String getProjectManager() {
        return StringUtil.nvl(projectManager);
    }

    public String getProjectProperty() {
        return StringUtil.nvl(projectProperty);
    }

    public String getProjectType() {
        return StringUtil.nvl(projectType);
    }

    public String getProjTypeNo() {
        return StringUtil.nvl(projTypeNo);
    }

    public String getSales() {
        return StringUtil.nvl(sales);
    }

    public String getSkillDomain() {
        return StringUtil.nvl(skillDomain);
    }

    public String getSoftWareReq() {
        return StringUtil.nvl(softWareReq);
    }

    public String getTranEnvDb() {
        return StringUtil.nvl(tranEnvDb);
    }

    public String getTranEnvLang() {
        return StringUtil.nvl(tranEnvLang);
    }

    public String getTranEnvNetwork() {
        return StringUtil.nvl(tranEnvNetwork);
    }

    public String getTranEnvOs() {
        return StringUtil.nvl(tranEnvOs);
    }

    public String getTranEnvOther() {
        return StringUtil.nvl(tranEnvOther);
    }

    public String getTranEnvTool() {
        return StringUtil.nvl(tranEnvTool);
    }

    public String getTransLan() {
        return StringUtil.nvl(transLan);
    }

    public String getWorkItem() {
        return StringUtil.nvl(workItem);
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setCloseMark(String closeMark) {
        this.closeMark = closeMark;
    }

    public void setPlanFrom(String planFrom) {
        this.planFrom = planFrom;
    }

    public void setPlanTo(String planTo) {
        this.planTo = planTo;
    }

    public void setAchieveBelong(String achieveBelong) {
        this.achieveBelong = achieveBelong;
    }

    public void setActuFrom(String actuFrom) {
        this.actuFrom = actuFrom;
    }

    public void setWorkItem(String workItem) {
        this.workItem = workItem;
    }

    public void setTranEnvTool(String tranEnvTool) {
        this.tranEnvTool = tranEnvTool;
    }

    public void setTranEnvOther(String tranEnvOther) {
        this.tranEnvOther = tranEnvOther;
    }

    public void setTransLan(String transLan) {
        this.transLan = transLan;
    }

    public void setTranEnvOs(String tranEnvOs) {
        this.tranEnvOs = tranEnvOs;
    }

    public void setTranEnvNetwork(String tranEnvNetwork) {
        this.tranEnvNetwork = tranEnvNetwork;
    }

    public void setTranEnvLang(String tranEnvLang) {
        this.tranEnvLang = tranEnvLang;
    }

    public void setTranEnvDb(String tranEnvDb) {
        this.tranEnvDb = tranEnvDb;
    }

    public void setSoftWareReq(String softWareReq) {
        this.softWareReq = softWareReq;
    }

    public void setSkillDomain(String skillDomain) {
        this.skillDomain = skillDomain;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public void setProjTypeNo(String projTypeNo) {
        this.projTypeNo = projTypeNo;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public void setProjectProperty(String projectProperty) {
        this.projectProperty = projectProperty;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public void setProjectExecUnit(String projectExecUnit) {
        this.projectExecUnit = projectExecUnit;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setProductProperty(String productProperty) {
        this.productProperty = productProperty;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPcontTel(String pcontTel) {
        this.pcontTel = pcontTel;
    }

    public void setPcontEmail(String pcontEmail) {
        this.pcontEmail = pcontEmail;
    }

    public void setPcontact(String pcontact) {
        this.pcontact = pcontact;
    }

    public void setOriginalLan(String originalLan) {
        this.originalLan = originalLan;
    }

    public void setMasterProjects(String masterProjects) {
        this.masterProjects = masterProjects;
    }

    public void setManMonth(Double manMonth) {
        this.manMonth = manMonth;
    }

    public void setIcontTel(String icontTel) {
        this.icontTel = icontTel;
    }

    public void setIcontEmail(String icontEmail) {
        this.icontEmail = icontEmail;
    }

    public void setIcontact(String icontact) {
        this.icontact = icontact;
    }

    public void setHardWareReq(String hardWareReq) {
        this.hardWareReq = hardWareReq;
    }

    public void setEstimatedWords(Double estimatedWords) {
        this.estimatedWords = estimatedWords;
    }

    public void setEngageManager(String engageManager) {
        this.engageManager = engageManager;
    }

    public void setDivisionManager(String divisionManager) {
        this.divisionManager = divisionManager;
    }

    public void setDevEnvTool(String devEnvTool) {
        this.devEnvTool = devEnvTool;
    }

    public void setDevEnvOther(String devEnvOther) {
        this.devEnvOther = devEnvOther;
    }

    public void setDevEnvOs(String devEnvOs) {
        this.devEnvOs = devEnvOs;
    }

    public void setDevEnvNetwork(String devEnvNetwork) {
        this.devEnvNetwork = devEnvNetwork;
    }

    public void setDevEnvLang(String devEnvLang) {
        this.devEnvLang = devEnvLang;
    }

    public void setDevEnvDb(String devEnvDb) {
        this.devEnvDb = devEnvDb;
    }

    public void setCustServiceManager(String custServiceManager) {
        this.custServiceManager = custServiceManager;
    }

    public void setCcontTel(String ccontTel) {
        this.ccontTel = ccontTel;
    }

    public void setCcontEmail(String ccontEmail) {
        this.ccontEmail = ccontEmail;
    }

    public void setCcontact(String ccontact) {
        this.ccontact = ccontact;
    }

    public void setBizSource(String bizSource) {
        this.bizSource = bizSource;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public void setActuTo(String actuTo) {
        this.actuTo = actuTo;
    }

    private void jbInit() throws Exception {
    }


}
