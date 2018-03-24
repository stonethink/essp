package server.essp.projectpre.ui.project.check;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntCustContactor;
import server.essp.projectpre.db.AcntCustContactorApp;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.AcntTechinfo;
import server.essp.projectpre.db.AcntTechinfoApp;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;

import com.wits.util.comDate;

/**
 * 预览变更申请复核的Action
 * @author Stephen.zheng
 *
 * 
 */
public class AcPreviewCheckProjectChange extends AbstractESSPAction {
	/**
	 * 预览变更申请复核页面
	 *   1.获取变更前后的专案资料
	 *   2.获取变更前后的技术资料
	 *   3.获取变更前后客户相关信息
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
            TransactionData data) throws BusinessException {
        Long rid = null;
        VbCheckProjectChange viewBean = new VbCheckProjectChange();
        if (request.getParameter("rid") != null) {
            rid = Long.valueOf(request.getParameter("rid"));
        }
        
        //获取变更前后的专案资料
        List masterDataList = getMasterData(rid);
        
        //获取变更前后的技术资料
        List techInfoList = getTechInfo(rid);
        
        //获取变更前后客户相关信息
        List customerInfoList = getCustomerInfo(rid);
        
        
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        AcntApp acntApp = logic.loadByRid(rid);
        viewBean.setAcntId(acntApp.getAcntId());
        viewBean.setApplicant(acntApp.getApplicantName());
        viewBean.setApplicationDate(comDate.dateToString(acntApp.getApplicationDate(), "yyyy-MM-dd"));
        viewBean.setMasterList(masterDataList);
        viewBean.setTechInfoList(techInfoList);
        viewBean.setCustomerInfoList(customerInfoList);
        
        request.setAttribute(Constant.VIEW_BEAN_KEY,viewBean);
        
        
    }
    /**
     * 获得变更前后的专案资料
     * @param rid 当前申请的申请单号
     * @return 含有变更前后信息的专案资料
     */
    private List getMasterData(Long rid) {
                
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        AcntApp acntApp = new AcntApp();
        Acnt acnt = new Acnt();
        AcntPerson acntPerson = new AcntPerson();
        AcntPersonApp acntPersonApp = new AcntPersonApp();
        acntApp = logic.loadByRid(rid);
        String acntId = acntApp.getAcntId();
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        acnt = acntLogic.loadByAcntId(acntId, "1");
        Long acntRid = acnt.getRid();
        List masterDataList = new ArrayList();
        
        VbCompare vb = new VbCompare();
        vb.setOption("acntFullName");
        vb.setValueBeforeChange(acnt.getAcntFullName());
        vb.setValueAfterChange(acntApp.getAcntFullName());
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("acntAttribute");
        IParameterService parameterLogic = (IParameterService)this.getBean("ParameterLogic");
        Parameter parameter = parameterLogic.loadByKindCode(server.essp.projectpre.service.constant.Constant.PROJEC_TYPE, acnt.getAcntAttribute());
        if(parameter!=null){
        vb.setValueBeforeChange(parameter.getName());
        }     
        parameter = parameterLogic.loadByKindCode(server.essp.projectpre.service.constant.Constant.PROJEC_TYPE, acntApp.getAcntAttribute());
        if(parameter!=null){
        vb.setValueAfterChange(parameter.getName());
        }
        masterDataList.add(vb);
        
        acntPerson = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_PM);
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_PM);
        vb = new VbCompare();
        vb.setOption("PMName");
        vb.setValueBeforeChange(acntPerson.getName());
        vb.setValueAfterChange(acntPersonApp.getName());
        masterDataList.add(vb);
        
//        acntPerson = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_TC_SIGNER);
//        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_TC_SIGNER);
//        vb = new VbCompare();
//        vb.setOption("TCSName");
//        vb.setValueBeforeChange(acntPerson.getName());
//        vb.setValueAfterChange(acntPersonApp.getName());
//        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("acntBrief");
        vb.setValueBeforeChange(acnt.getAcntBrief());
        vb.setValueAfterChange(acntApp.getAcntBrief());
        masterDataList.add(vb);
        
        acntPerson = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_BD_MANAGER);
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_BD_MANAGER);
        vb = new VbCompare();
        vb.setOption("BDName");
        vb.setValueBeforeChange(acntPerson.getName());
        vb.setValueAfterChange(acntPersonApp.getName());
        masterDataList.add(vb);
        
        acntPerson = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_LEADER);
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_LEADER);
        vb = new VbCompare();
        vb.setOption("leader");
        vb.setValueBeforeChange(acntPerson.getName());
        vb.setValueAfterChange(acntPersonApp.getName());
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("execSite");
        ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
        Site site = siteLogic.loadByCode(acnt.getExecSite());
        vb.setValueBeforeChange(site.getSiteName());
        site = siteLogic.loadByCode(acntApp.getExecSite());
        if(site!=null){
        vb.setValueAfterChange(site.getSiteName());
        }
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("costAttachBd");
        IBdService bdLogic = (IBdService)this.getBean("BdCodeLogic");
        Bd bd = bdLogic.loadByBdCode(acnt.getCostAttachBd());
        if(bd!=null){
        vb.setValueBeforeChange(bd.getBdName());
        }
        bd = bdLogic.loadByBdCode(acntApp.getCostAttachBd());
        if(bd!=null){
        vb.setValueAfterChange(bd.getBdName());
        }
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("bl");
        Acnt bl = acntLogic.loadByAcntId(acnt.getBl(), "0");
        if(bl!=null){
        vb.setValueBeforeChange(bl.getAcntId()+"--"+bl.getAcntName());
        }
        bl = acntLogic.loadByAcntId(acntApp.getBl(), "0");
        if(bd!=null){
        vb.setValueAfterChange(bl.getAcntId()+"--"+bl.getAcntName());
        }
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("execUnit");
        Acnt execUnit = acntLogic.loadByAcntId(acnt.getExecUnitId(), "0");
        if(execUnit!=null){
        vb.setValueBeforeChange(execUnit.getAcntId()+"--"+execUnit.getAcntName());
        }
        execUnit = acntLogic.loadByAcntId(acntApp.getExecUnitId(), "0");
        if(bd!=null){
        vb.setValueAfterChange(execUnit.getAcntId()+"--"+execUnit.getAcntName());
        }
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("achieveBelong");
        bd = bdLogic.loadByBdCode(acnt.getAchieveBelong());
        if(bd!=null){
        vb.setValueBeforeChange(bd.getBdName());
        } 
        bd = bdLogic.loadByBdCode(acntApp.getAchieveBelong());
        if(bd!=null){
        vb.setValueAfterChange(bd.getBdName());
        }
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("bizSource");
        bd = bdLogic.loadByBdCode(acnt.getBizSource());
        if(bd!=null){
        vb.setValueBeforeChange(bd.getBdName());
        } 
        bd = bdLogic.loadByBdCode(acntApp.getBizSource());
        if(bd!=null){
        vb.setValueAfterChange(bd.getBdName());
        }
        masterDataList.add(vb);
        
        acntPerson = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_SALES);
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_SALES);
        vb = new VbCompare();
        vb.setOption("sales");
        vb.setValueBeforeChange(acntPerson.getName());
        vb.setValueAfterChange(acntPersonApp.getName());
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("productName");
        vb.setValueBeforeChange(acnt.getProductName());
        vb.setValueAfterChange(acntApp.getProductName());
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("acntPlannedStart");
        vb.setValueBeforeChange(comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd"));
        vb.setValueAfterChange(comDate.dateToString(acntApp.getAcntPlannedStart(), "yyyy-MM-dd"));
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("acntPlannedFinish");
        vb.setValueBeforeChange(comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd"));
        vb.setValueAfterChange(comDate.dateToString(acntApp.getAcntPlannedFinish(), "yyyy-MM-dd"));
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("acntActualStart");
        vb.setValueBeforeChange(comDate.dateToString(acnt.getAcntActualStart(), "yyyy-MM-dd"));
        vb.setValueAfterChange(comDate.dateToString(acntApp.getAcntActualStart(), "yyyy-MM-dd"));
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("acntActualFinish");
        vb.setValueBeforeChange(comDate.dateToString(acnt.getAcntActualFinish(), "yyyy-MM-dd"));
        vb.setValueAfterChange(comDate.dateToString(acntApp.getAcntActualFinish(), "yyyy-MM-dd"));
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("estManmonth");
        vb.setValueBeforeChange(acnt.getEstManmonth().toString());
        vb.setValueAfterChange(acntApp.getEstManmonth().toString());
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("actualManmonth");
        vb.setValueBeforeChange(acnt.getActualManmonth().toString());
        vb.setValueAfterChange(acntApp.getActualManmonth().toString());
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("estSize");
        vb.setValueBeforeChange(acnt.getEstSize().toString());
        vb.setValueAfterChange(acntApp.getEstSize().toString());
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("primaveraAdapted");
        String primaveraAdaptedBefore = "N";
        String primaveraAdaptedAfter = "N";
        if("1".equals(acnt.getPrimaveraAdapted())) {
        	primaveraAdaptedBefore = "Y";
        }
        if("1".equals(acntApp.getPrimaveraAdapted())) {
        	primaveraAdaptedAfter = "Y";
        }
        vb.setValueBeforeChange(primaveraAdaptedBefore);
        vb.setValueAfterChange(primaveraAdaptedAfter);
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("billingBasis");
        String billingBasisBefore = "N";
        String billingBasisAfter = "N";
        if("1".equals(acnt.getBillingBasis())) {
        	billingBasisBefore = "Y";
        }
        if("1".equals(acntApp.getBillingBasis())) {
        	billingBasisAfter = "Y";
        }
        vb.setValueBeforeChange(billingBasisBefore);
        vb.setValueAfterChange(billingBasisAfter);
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("bizProperty");
        vb.setValueBeforeChange(acnt.getBizProperty());
        vb.setValueAfterChange(acntApp.getBizProperty());
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("billType");
        vb.setValueBeforeChange(acnt.getBillType());
        vb.setValueAfterChange(acntApp.getBillType());
        masterDataList.add(vb);
        
        vb = new VbCompare();
        vb.setOption("otherDesc");
        vb.setValueBeforeChange(acnt.getOtherDesc());
        vb.setValueAfterChange(acntApp.getOtherDesc());
        masterDataList.add(vb);
        
        return masterDataList;
    }
    /**
     * 获取变更前后的技术资料
     * @param rid 当前申请单号
     * @return 含有变更前后信息的技术资料
     */
    private List getTechInfo(Long rid) {
        List techList = new ArrayList();
        VbCompare vb = new VbCompare();
        vb = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PROJECT_TYPE);
        techList.add(vb);
        
        vb = new VbCompare();
        vb = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PRODUCT_TYPE);
        techList.add(vb);
        
        vb = new VbCompare();
        vb = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.PRODUCT_ATTRIBUTE);
        techList.add(vb);
        
        vb = new VbCompare();
        vb = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.WORK_ITEM);
        techList.add(vb);
        
        vb = new VbCompare();
        vb = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.TECHNICAL_DOMAIN);
        techList.add(vb);
        
        vb = new VbCompare();
        vb = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.ORIGINAL_LANGUAGE);
        techList.add(vb);
        
        vb = new VbCompare();
        vb = getTechInfoVb(rid, server.essp.projectpre.service.constant.Constant.TRANSLATION_LANGUANGE);
        techList.add(vb);
        
        techList = getTechInfoTextList(rid, techList);
        
        return techList;
    }
    /**
     * 获得每种类型的技术资料的ViewBean
     * @param rid 当前申请单号
     * @param type 技术资料类型
     * @return 该类型技术资料的ViewBean
     */
    private VbCompare getTechInfoVb(Long rid, String type) {
        AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
        AcntTechinfo techInfo = new AcntTechinfo();
        String valueBefore = null;
        String valueAfter = null;
        ParameterId parameterId = new ParameterId();
        parameterId.setKind(type);
        Parameter parameter = new Parameter();
        
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        AcntApp acntApp = logic.loadByRid(rid);
        Acnt acnt = acntLogic.loadByAcntId(acntApp.getAcntId(), "1");
        IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
        List techinfoAppList = logic.listByRidKindFromTechInfoApp(rid, type);
        List techinfoList = acntLogic.listByRidKindFromTechInfo(acnt.getRid(), type);
       
        for(int i=0;i<techinfoAppList.size();i++) {
            techInfoApp = (AcntTechinfoApp)techinfoAppList.get(i);
            parameterId.setCode(techInfoApp.getId().getCode());
            parameter = parameterLogic.loadByKey(parameterId);
            if(parameter!=null){
            valueAfter = valueAfter + "," + parameter.getName();
            }
        }
        if(valueAfter!=null){
            valueAfter = valueAfter.substring(5);
        }else{
            valueAfter = "";
        }
        
        for(int i=0;i<techinfoList.size();i++) {
            techInfo = (AcntTechinfo) techinfoList.get(i);
            parameterId.setCode(techInfo.getId().getCode());
            parameter = parameterLogic.loadByKey(parameterId);
            if(parameter!=null){
            valueBefore = valueBefore + "," + parameter.getName();
            }
        }
        if(valueBefore!=null){
            valueBefore = valueBefore.substring(5);
        }else {
            valueBefore = "";
        }
        
        VbCompare vb = new VbCompare();
        vb.setOption(type);
        vb.setValueBeforeChange(valueBefore);
        vb.setValueAfterChange(valueAfter);
  
        return vb;
    }
    /**
     * 获取变更前后技术资料中文本信息
     * @param rid 当前申请单号
     * @param techList 已经含有变更前后选项信息的技术资料列表
     * @return 包含变更前后技术资料文本信息的列表
     */
    private List getTechInfoTextList(Long rid, List techList) {
       
 
        VbCompare vb = new VbCompare();
        vb = getTechInfoTextDetail(rid, server.essp.projectpre.service.constant.Constant.DEVELOPENVIRONMENT);
        techList.add(vb);
        
       
        
        vb = new VbCompare();
        vb = getTechInfoTextDetail(rid, server.essp.projectpre.service.constant.Constant.TRNSLATEPUBLISHTYPE);
        techList.add(vb);
        
        
        
        AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
        AcntTechinfo techInfo = new AcntTechinfo();
        String valueBefore = null;
        String valueAfter = null;
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        AcntApp acntApp = logic.loadByRid(rid);
        Acnt acnt = acntLogic.loadByAcntId(acntApp.getAcntId(), "1");
        techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, server.essp.projectpre.service.constant.Constant.HARDREQ, server.essp.projectpre.service.constant.Constant.HARDREQ);
        valueAfter = techInfoApp.getDescription();
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(acnt.getRid(), server.essp.projectpre.service.constant.Constant.HARDREQ, server.essp.projectpre.service.constant.Constant.HARDREQ);
        valueBefore = techInfo.getDescription();
        vb = new VbCompare();
        vb.setOption(server.essp.projectpre.service.constant.Constant.HARDREQ);
        vb.setValueBeforeChange(valueBefore);
        vb.setValueAfterChange(valueAfter);
        techList.add(vb);
        
        techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, server.essp.projectpre.service.constant.Constant.SOFTREQ, server.essp.projectpre.service.constant.Constant.SOFTREQ);
        valueAfter = techInfoApp.getDescription();
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(acnt.getRid(), server.essp.projectpre.service.constant.Constant.SOFTREQ, server.essp.projectpre.service.constant.Constant.SOFTREQ);
        valueBefore = techInfo.getDescription();
        vb = new VbCompare();
        vb.setOption(server.essp.projectpre.service.constant.Constant.SOFTREQ);
        vb.setValueBeforeChange(valueBefore);
        vb.setValueAfterChange(valueAfter);
        techList.add(vb);
        
        return techList;
    }
    /**
     * 获取每种类型下的各个代码中的文本信息
     * @param rid 当前申请单号
     * @param kind 技术资料类型
     * @param code 该类型中的代码
     * @return 含有变更前后文本信息的ViewBean
     */
    private VbCompare getTechInfoTextDetail(Long rid, String kind){
//        String valueAfter = null;
//        String valueBefore = null;
        VbCompare vb = new VbCompare();
        vb.setOption(kind);
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        AcntTechinfoApp techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, kind, server.essp.projectpre.service.constant.Constant.JOBSYSTEM);
        vb.setJobSystem(techInfoApp.getDescription());
//        valueAfter = valueAfter+","+server.essp.projectpre.service.constant.Constant.JOBSYSTEM+":"+techInfoApp.getDescription();
        techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, kind, server.essp.projectpre.service.constant.Constant.DATABASE);
        vb.setDataBase(techInfoApp.getDescription());
//        valueAfter = valueAfter+","+server.essp.projectpre.service.constant.Constant.DATABASE+":"+techInfoApp.getDescription();
        techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, kind, server.essp.projectpre.service.constant.Constant.TOOL);
        vb.setTool(techInfoApp.getDescription());
//        valueAfter = valueAfter+","+server.essp.projectpre.service.constant.Constant.TOOL+":"+techInfoApp.getDescription()+"\r\n";
        techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, kind, server.essp.projectpre.service.constant.Constant.NETWORK);
        vb.setNetWork(techInfoApp.getDescription());
//        valueAfter = valueAfter+server.essp.projectpre.service.constant.Constant.NETWORK+":"+techInfoApp.getDescription();
        techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, kind, server.essp.projectpre.service.constant.Constant.PROGRAMLANGUAGE);
        vb.setProgramLanguage(techInfoApp.getDescription());
//        valueAfter = valueAfter+","+server.essp.projectpre.service.constant.Constant.PROGRAMLANGUAGE+":"+techInfoApp.getDescription();
        techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(rid, kind, server.essp.projectpre.service.constant.Constant.OTHERS);
        vb.setOthers(techInfoApp.getDescription());
//        valueAfter = valueAfter+","+server.essp.projectpre.service.constant.Constant.OTHERS+":"+techInfoApp.getDescription();
//        valueAfter = valueAfter.substring(5);
    
        AcntApp acntApp = logic.loadByRid(rid);
        Acnt acnt = acntLogic.loadByAcntId(acntApp.getAcntId(), "1");
        AcntTechinfo techInfo = acntLogic.loadByRidKindCodeFromTechInfo(acnt.getRid(), kind, server.essp.projectpre.service.constant.Constant.JOBSYSTEM);
        vb.setJobSystemBefore(techInfo.getDescription());
//        valueBefore = valueBefore +","+server.essp.projectpre.service.constant.Constant.JOBSYSTEM+":"+techInfo.getDescription();
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(acnt.getRid(), kind, server.essp.projectpre.service.constant.Constant.DATABASE);
        vb.setDataBaseBefore(techInfo.getDescription());
//        valueBefore = valueBefore +","+server.essp.projectpre.service.constant.Constant.DATABASE+":"+techInfo.getDescription();
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(acnt.getRid(), kind, server.essp.projectpre.service.constant.Constant.TOOL);
        vb.setToolBefore(techInfo.getDescription());
//        valueBefore = valueBefore +","+server.essp.projectpre.service.constant.Constant.TOOL+":"+techInfo.getDescription()+"\r\n";
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(acnt.getRid(), kind, server.essp.projectpre.service.constant.Constant.NETWORK);
        vb.setNetWorkBefore(techInfo.getDescription());
//        valueBefore = valueBefore +server.essp.projectpre.service.constant.Constant.NETWORK+":"+techInfo.getDescription();
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(acnt.getRid(), kind, server.essp.projectpre.service.constant.Constant.PROGRAMLANGUAGE);
        vb.setProgramLanguageBefore(techInfo.getDescription());
//        valueBefore = valueBefore +","+server.essp.projectpre.service.constant.Constant.PROGRAMLANGUAGE+":"+techInfo.getDescription();
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(acnt.getRid(), kind, server.essp.projectpre.service.constant.Constant.OTHERS);
        vb.setOthersBefore(techInfo.getDescription());
//        valueBefore = valueBefore +","+server.essp.projectpre.service.constant.Constant.OTHERS+":"+techInfo.getDescription();
//        valueBefore = valueBefore.substring(5);
        
       
        
//        vb.setValueBeforeChange(valueBefore);
//        vb.setValueAfterChange(valueAfter);
        return vb;
    }
    /**
     * 获取客户的相关信息
     * @param rid 当前申请的Rid
     * @return 返回包含客户信息的List
     */
    private List getCustomerInfo(Long rid) {
        List customerInfoList = new ArrayList();
        AcntApp acntApp = new AcntApp();
        Acnt acnt = new Acnt();
        String valueAfter = null;
        String valueBefore = null;
        AcntPerson acntPerson = new AcntPerson();
        AcntPersonApp acntPersonApp = new AcntPersonApp();
       
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        
        acntApp = logic.loadByRid(rid);
        String acntId = acntApp.getAcntId();
        acnt = acntLogic.loadByAcntId(acntId, "1");
        valueAfter = acntApp.getCustomerId();
        valueBefore = acnt.getCustomerId();
        VbCompare vb = new VbCompare();
        vb.setOption("customerId");
        vb.setValueBeforeChange(valueBefore);
        vb.setValueAfterChange(valueAfter);
        customerInfoList.add(vb);
        
        acntPerson = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        vb = new VbCompare();
        vb.setOption(IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        vb.setValueBeforeChange(acntPerson.getName());
        vb.setValueAfterChange(acntPersonApp.getName());
        customerInfoList.add(vb);
        
        acntPerson = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        vb = new VbCompare();
        vb.setOption(IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        vb.setValueBeforeChange(acntPerson.getName());
        vb.setValueAfterChange(acntPersonApp.getName());
        customerInfoList.add(vb);
        
        customerInfoList = getCustContactor(rid, customerInfoList);
        
        return customerInfoList;
    }
    /**
     * 获取联系人的相关信息
     * @param rid 当前申请的Rid
     * @param customerInfoList 含有客户资料的List
     * @return 含有客户资料和客户联系人信息的List
     */
    private List getCustContactor(Long rid, List customerInfoList){
        AcntCustContactor acntCustContactor = new AcntCustContactor();
        AcntCustContactorApp acntCustContactorApp = new AcntCustContactorApp();

        VbCompare vb = new VbCompare();
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        AcntApp acntApp = logic.loadByRid(rid);
        Acnt acnt = acntLogic.loadByAcntId(acntApp.getAcntId(), "1");
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(acnt.getRid(), IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        acntCustContactorApp = logic.loadByRidTypeFromAcntCustContactorApp(rid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        vb.setOption(IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        vb.setName(acntCustContactorApp.getName());
        vb.setTel(acntCustContactorApp.getTelephone());
        vb.setEmail(acntCustContactorApp.getEmail());
        vb.setNameBefore(acntCustContactor.getName());
        vb.setTelBefore(acntCustContactor.getTelephone());
        vb.setEmailBefore(acntCustContactor.getEmail());
        customerInfoList.add(vb);
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(acnt.getRid(), IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        acntCustContactorApp = logic.loadByRidTypeFromAcntCustContactorApp(rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        vb = new VbCompare();
        vb.setOption(IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        vb.setName(acntCustContactorApp.getName());
        vb.setTel(acntCustContactorApp.getTelephone());
        vb.setEmail(acntCustContactorApp.getEmail());
        vb.setNameBefore(acntCustContactor.getName());
        vb.setTelBefore(acntCustContactor.getTelephone());
        vb.setEmailBefore(acntCustContactor.getEmail());
        customerInfoList.add(vb);
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(acnt.getRid(), IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        acntCustContactorApp = logic.loadByRidTypeFromAcntCustContactorApp(rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        vb = new VbCompare();
        vb.setOption(IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        vb.setName(acntCustContactorApp.getName());
        vb.setTel(acntCustContactorApp.getTelephone());
        vb.setEmail(acntCustContactorApp.getEmail());
        vb.setNameBefore(acntCustContactor.getName());
        vb.setTelBefore(acntCustContactor.getTelephone());
        vb.setEmailBefore(acntCustContactor.getEmail());
        customerInfoList.add(vb);
        
          return customerInfoList;
    }

}
