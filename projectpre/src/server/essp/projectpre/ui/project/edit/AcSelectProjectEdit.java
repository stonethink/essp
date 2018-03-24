/*
 * Created on 2007-4-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.project.edit;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wits.util.comDate;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;

public class AcSelectProjectEdit extends AbstractESSPAction {
    private final static String RID="rid";
    private String defaultStr = "--please select--";
    private SelectOptionImpl  firstOption = new SelectOptionImpl(defaultStr,"");
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        Long rid=null;
        if (request.getParameter(RID) != null) {
            rid = Long.valueOf(request.getParameter(RID));
        }
        if(request.getAttribute(RID)!=null){
            rid = (Long)request.getAttribute(RID);
        }
        AfProjectEdit af = new AfProjectEdit();
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        Acnt acntNest = acntLogic.loadByRid(rid);
        //将持久化类中可用属性拷贝到ViewBean中
        try {
            DtoUtil.copyProperties(af, acntNest);
        } catch (Exception e) {
            e.printStackTrace();
        }
//      获取执行单位
        Acnt acnt = acntLogic.loadByAcntId(acntNest.getExecUnitId(), "0");
        String execUnitName = null;
        if(acnt==null) {
            execUnitName="";
        } else {
            execUnitName = acnt.getAcntId()+"---"+acnt.getAcntName();
        }
        af.setExecUnitName(execUnitName);
        af.setExecUnitNameList(getExecUnitNameList());
//      格式化日期
        af.setAcntPlannedStart(comDate.dateToString(acntNest.getAcntPlannedStart(), "yyyy-MM-dd"));
        af.setAcntPlannedFinish(comDate.dateToString(acntNest.getAcntPlannedFinish(), "yyyy-MM-dd"));
        af.setAcntActualStart(comDate.dateToString(acntNest.getAcntActualStart(), "yyyy-MM-dd"));
        af.setAcntActualFinish(comDate.dateToString(acntNest.getAcntActualFinish(), "yyyy-MM-dd"));
        //获得业绩归属，业务来源     
        af.setAchieveBelongList(getBdCodeList());
//      获取专案属性
        af.setAcntAttributeList(getAcntAttributeList());
        //获取业务属性
        af.setBizPropertyList(getBizPropertyList());
        //获取收费类型
        af.setBillTypeList(getBillTypeList());
        
        af = getAcntPerson(af, acntLogic, rid);
        
//      将ViewBean设置到前台
        request.setAttribute(Constant.VIEW_BEAN_KEY,af);
    }
    /**
     * 获取Acnt表中的相关人员信息
     * @param viewBean 用于显示的ViewBean
     * @param anctLogic Acnt的服务
     * @param rid 当前Acnt的Rid
     * @return 含有Acnt人员信息的ViewBean
     */
    private AfProjectEdit getAcntPerson(AfProjectEdit af, IAccountService acntLogic, Long rid) {
       // 获得相关人员,包括专案经理、工时表签核者、BD主管、业务经理、业务代表
        AcntPerson acntPerson = new AcntPerson();
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_APPLICANT);
        if(acntPerson!=null){
            af.setApplicantName(acntPerson.getName());
            af.setAPId(acntPerson.getLoginId());
            af.setAPDomain(acntPerson.getDomain());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_PM);
        if(acntPerson!=null){
            af.setPMName(acntPerson.getName());
            af.setPMId(acntPerson.getLoginId());
            af.setPMDomain(acntPerson.getDomain());
        } 
        
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_TC_SIGNER);
        if(acntPerson!=null){
            af.setTCSName(acntPerson.getName());
            af.setTCSId(acntPerson.getLoginId());
            af.setTCSDomain(acntPerson.getDomain());
        }
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_BD_MANAGER);
        if(acntPerson!=null){
            af.setBDMName(acntPerson.getName());
            af.setBDId(acntPerson.getLoginId());
            af.setBDDomain(acntPerson.getDomain());
        }
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_LEADER);
        if(acntPerson!=null){
            af.setLeaderName(acntPerson.getName());
            af.setLeaderId(acntPerson.getLoginId());
            af.setLeaderDomain(acntPerson.getDomain());
        }
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_SALES);
        if(acntPerson!=null){
            af.setSalesName(acntPerson.getName());
            af.setSalesId(acntPerson.getLoginId());
            af.setSalesDomain(acntPerson.getDomain());
        } 
        
        return af;
    }
    /**
     * 获取执行单位的List，由编号和名称构成
     * @return 执行单位列表
     */
     private List getExecUnitNameList() {
//      获取执行单位
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        Acnt acnt = new Acnt();
        List acntList = acntLogic.listDept();
        List execUnitNameList = new ArrayList();
        String execUnitName = null;
        SelectOptionImpl execUnitOption = null;
        execUnitNameList.add(firstOption);
        for(int i=0;i<acntList.size();i++) {
            acnt = (Acnt) acntList.get(i);
            execUnitName = acnt.getAcntId()+"---"+acnt.getAcntName();
            execUnitOption = new SelectOptionImpl(execUnitName, execUnitName, execUnitName);
            execUnitNameList.add(execUnitOption);
        }
        return execUnitNameList;
    }
     /**
      * 获取专案属性
      * @return  属性列表
      */
    private List getAcntAttributeList(){
        List acntAttributeList = new ArrayList();
        IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
        List parameterList = parameterLogic.listAllByKindEnable(server.essp.projectpre.service.constant.Constant.PROJEC_TYPE);
        SelectOptionImpl parameterOption = null; 
        acntAttributeList.add(firstOption);
        Parameter parameter = new Parameter();
        for(int i = 0; i<parameterList.size();i++) {
            parameter = (Parameter) parameterList.get(i);
            parameterOption = new SelectOptionImpl(parameter.getName(), parameter.getCompId().getCode(), parameter.getName());
           
            acntAttributeList.add(parameterOption);
        }
        
        return acntAttributeList;
        
    }
     
    /**
     * 获取业绩归属，业务来源的List
     * @return BD列表
     */
     private List getBdCodeList() {
         //获得业绩归属，业务来源
         IBdService  bdLogic = (IBdService) this.getBean("BdCodeLogic");
         List bdList = bdLogic.listEabledBelongs(true);
         List achieveBelongList = new ArrayList();
         SelectOptionImpl bdOption = null;  
         achieveBelongList.add(firstOption);
         Bd bd = new Bd();
        for(int i = 0; i<bdList.size();i++) {
            bd = (Bd) bdList.get(i);
            bdOption = new SelectOptionImpl(bd.getBdName(),bd.getBdCode(),bd.getBdName());
           
            achieveBelongList.add(bdOption);
        }
        return achieveBelongList;
     }
     /**
      * 获取业务属性下拉框选项
      * @return
      */
     private List getBizPropertyList() {
    	 List bizPropertyList = new ArrayList();
         SelectOptionImpl siteOption = null;
         bizPropertyList.add(firstOption);
         siteOption = new SelectOptionImpl("Contract", "Contract", "Contract");
         bizPropertyList.add(siteOption);
         siteOption = new SelectOptionImpl("Sub-Contract ", "Sub-Contract", "Sub-Contract");
         bizPropertyList.add(siteOption);
         siteOption = new SelectOptionImpl("Investment", "Investment", "Investment");
         bizPropertyList.add(siteOption);
         siteOption = new SelectOptionImpl("Purchase Order", "Purchase Order", "Purchase Order");
         bizPropertyList.add(siteOption);
         siteOption = new SelectOptionImpl("others", "others", "others");
         bizPropertyList.add(siteOption);
         return bizPropertyList;
     }
     /**
      * 获取收费类型下拉框菜单
      * @return
      */
     private List getBillTypeList() {
    	 List billTypeList = new ArrayList();
    	 SelectOptionImpl siteOption = null;
    	 billTypeList.add(firstOption);
         siteOption = new SelectOptionImpl("Fixed Price", "Fixed Price", "Fixed Price");
         billTypeList.add(siteOption);
         siteOption = new SelectOptionImpl("Time&Material", "Time&Material", "Time&Material");
         billTypeList.add(siteOption);
         siteOption = new SelectOptionImpl("others", "others", "others");
         billTypeList.add(siteOption);
    	 return billTypeList;
     }

}
