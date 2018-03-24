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
        //���־û����п������Կ�����ViewBean��
        try {
            DtoUtil.copyProperties(af, acntNest);
        } catch (Exception e) {
            e.printStackTrace();
        }
//      ��ȡִ�е�λ
        Acnt acnt = acntLogic.loadByAcntId(acntNest.getExecUnitId(), "0");
        String execUnitName = null;
        if(acnt==null) {
            execUnitName="";
        } else {
            execUnitName = acnt.getAcntId()+"---"+acnt.getAcntName();
        }
        af.setExecUnitName(execUnitName);
        af.setExecUnitNameList(getExecUnitNameList());
//      ��ʽ������
        af.setAcntPlannedStart(comDate.dateToString(acntNest.getAcntPlannedStart(), "yyyy-MM-dd"));
        af.setAcntPlannedFinish(comDate.dateToString(acntNest.getAcntPlannedFinish(), "yyyy-MM-dd"));
        af.setAcntActualStart(comDate.dateToString(acntNest.getAcntActualStart(), "yyyy-MM-dd"));
        af.setAcntActualFinish(comDate.dateToString(acntNest.getAcntActualFinish(), "yyyy-MM-dd"));
        //���ҵ��������ҵ����Դ     
        af.setAchieveBelongList(getBdCodeList());
//      ��ȡר������
        af.setAcntAttributeList(getAcntAttributeList());
        //��ȡҵ������
        af.setBizPropertyList(getBizPropertyList());
        //��ȡ�շ�����
        af.setBillTypeList(getBillTypeList());
        
        af = getAcntPerson(af, acntLogic, rid);
        
//      ��ViewBean���õ�ǰ̨
        request.setAttribute(Constant.VIEW_BEAN_KEY,af);
    }
    /**
     * ��ȡAcnt���е������Ա��Ϣ
     * @param viewBean ������ʾ��ViewBean
     * @param anctLogic Acnt�ķ���
     * @param rid ��ǰAcnt��Rid
     * @return ����Acnt��Ա��Ϣ��ViewBean
     */
    private AfProjectEdit getAcntPerson(AfProjectEdit af, IAccountService acntLogic, Long rid) {
       // ��������Ա,����ר��������ʱ��ǩ���ߡ�BD���ܡ�ҵ����ҵ�����
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
     * ��ȡִ�е�λ��List���ɱ�ź����ƹ���
     * @return ִ�е�λ�б�
     */
     private List getExecUnitNameList() {
//      ��ȡִ�е�λ
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
      * ��ȡר������
      * @return  �����б�
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
     * ��ȡҵ��������ҵ����Դ��List
     * @return BD�б�
     */
     private List getBdCodeList() {
         //���ҵ��������ҵ����Դ
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
      * ��ȡҵ������������ѡ��
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
      * ��ȡ�շ�����������˵�
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
