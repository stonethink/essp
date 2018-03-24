package server.essp.projectpre.ui.project.apply;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;

import com.wits.util.comDate;

/**
 * Ԥ��ר�����Ͽ�Ƭ��Action
 * @author wenhaizheng
 *
 * 
 */
public class AcPreviewProjectAcntApp extends AbstractESSPAction {
    private final static String RID="rid";
    private String defaultStr = "--please select--";
    private SelectOptionImpl  firstOption = new SelectOptionImpl(defaultStr,"");
    /**
     * ���ݲ�ͬ�����Ԥ��ר���޸������ר�����Ͽ�Ƭҳ��
     *    1.�ж��Ƿ��Ǹ���ר������
     *    2.���־û����п������Կ�����ViewBean��
     *    3.��ȡ��ص���Ա����
     *    4.����������
     *    5.��ViewBean���õ�ǰ̨
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        String accessType = request.getParameter("accessType");
        String appType = request.getParameter("appType");
        String confirmCheck = request.getParameter("confirmCheck");
        String confirmStatus = request.getParameter("confirmStatus");
        
        Long rid=null;
        if (request.getParameter(RID) != null) {
            rid = Long.valueOf(request.getParameter(RID));
        }
        String acntId = null;
        if(request.getParameter("acntId")!=null) {
            acntId = (String)request.getParameter("acntId");
        }
 
        VbProjectAcntApp viewBean = new VbProjectAcntApp();
        // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
        IAccountApplicationService logic = (IAccountApplicationService) this
                .getBean("AccountApplicationLogic");
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        
        //���ø���ר���ı�־λ������������ר����������������
        String selectStatus = null;
        if(request.getParameter("selectAcnt")!=null){
         selectStatus = request.getParameter("selectAcnt");
        }
        
        
        String execUnitId = null;
        Date acntPlannedStart = null;
        Date acntPlannedFinish = null;
        Date acntActualStart = null;
        Date acntActualFinish = null;
        String relPrjType = null;
        String relParentId = null;
        String contractAcntId = null;
        //String applicationStatus = null;
        AcntApp acntApp = new AcntApp();
        Acnt acntNest = new Acnt();
        //�ж��Ƿ��Ǹ���ר������
        if(selectStatus==null&&!appType.equals("confirm")){  
             acntApp = logic.loadByRid(rid);    
            //���־û����п������Կ�����ViewBean��
            try {
                DtoUtil.copyProperties(viewBean, acntApp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            execUnitId = acntApp.getExecUnitId();
            acntPlannedStart = acntApp.getAcntPlannedStart();
            acntPlannedFinish = acntApp.getAcntPlannedFinish();
            acntActualStart = acntApp.getAcntActualStart();
            acntActualFinish = acntApp.getAcntActualFinish();
            relPrjType = acntApp.getRelPrjType();
            relParentId = acntApp.getRelParentId();
            contractAcntId = acntApp.getContractAcntId();
            //applicationStatus = acntApp.getApplicationStatus();
        } else if(selectStatus!=null||appType.equals("confirm")){
            if(rid!=null){
            acntNest = acntLogic.loadByRid(rid);
            } else {
                acntNest = acntLogic.loadByAcntId(acntId, "1");
            }
            //���־û����п������Կ�����ViewBean��
            try {
                DtoUtil.copyProperties(viewBean, acntNest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if("true".equals(this.getRequest().getParameter("isFinance"))){
                viewBean.setRelPrjType(IDtoAccount.PROJECT_RELATION_FINANCE);
            } else if(IDtoAccount.PROJECT_RELATION_FINANCE.equals(acntNest.getRelPrjType())){
                viewBean.setRelPrjType(IDtoAccount.PROJECT_RELATION_MASTER);
            }
            execUnitId = acntNest.getExecUnitId();
            acntPlannedStart = acntNest.getAcntPlannedStart();
            acntPlannedFinish = acntNest.getAcntPlannedFinish();
            acntActualStart = acntNest.getAcntActualStart();
            acntActualFinish = acntNest.getAcntActualFinish();
            relPrjType = acntNest.getRelPrjType();
            relParentId = acntNest.getRelParentId();
            contractAcntId = acntNest.getContractAcntId();
            Acnt acntParent = acntLogic.loadByAcntId(relParentId, "1");
            if(selectStatus!=null&&!appType.equals("confirm")){
                request.setAttribute("parentExecSite", acntParent.getExecSite());
            }
            //applicationStatus = acntNest.getAcntStatus();
        }
        
        
        
        //��ʽ�����뵥�ţ�Rid��
        if(rid!=null){
        String IDFORMATER = "00000000";
        if(!accessType.equals("create")){
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        String newRid = df.format(rid, new StringBuffer(),
                                  new FieldPosition(0)).toString();
        viewBean.setRid(newRid);
        }
        }
        
        //��ȡר������
        viewBean.setAcntAttributeList(getAcntAttributeList());
       
        
        //��ȡ���ܾ��ı�ע����
        //if(applicationStatus.equals(server.essp.projectpre.service.constant.Constant.REJECTED)){
            viewBean.setComment(acntApp.getRemark());
       // }
        if(appType.equals("confirm")){
            AcntApp appNest = logic.loadByTypeAcntId(server.essp.projectpre.service.constant.Constant.PROJECTCONFIRMAPP, acntId);
            if(appNest!=null){
            viewBean.setComment(appNest.getRemark());
            }
        }
     
        
        //��ȡִ�е�λ
        Acnt acnt = acntLogic.loadByAcntId(execUnitId, "0");
        String execUnitName = null;
        if(acnt==null) {
            execUnitName="";
        } else {
            execUnitName = acnt.getAcntId()+"---"+acnt.getAcntName();
            
        }
        viewBean.setExecUnitName(execUnitName);
        viewBean.setExecUnitNameList(getExecUnitNameList());
            
        //��ʽ������
        viewBean.setAcntPlannedStart(comDate.dateToString(acntPlannedStart, "yyyy-MM-dd"));
        viewBean.setAcntPlannedFinish(comDate.dateToString(acntPlannedFinish, "yyyy-MM-dd"));
        viewBean.setAcntActualStart(comDate.dateToString(acntActualStart, "yyyy-MM-dd"));
        viewBean.setAcntActualFinish(comDate.dateToString(acntActualFinish, "yyyy-MM-dd"));
       
        
         //���ҵ��������ҵ����Դ     
         viewBean.setAchieveBelongList(getBdCodeList());

         //���ִ�оݵ�  
         viewBean.setExecSiteList(getExecSiteList());
         //��ȡҵ������
         viewBean.setBizPropertyList(getBizPropertyList());
         //��ȡ�շ�����
         viewBean.setBillTypeList(getBillTypeList());
         //��ȡҵ���߲���
         viewBean.setBlList(getBlList());
         
        
         
         //��������Ա,����ר��������ʱ��ǩ���ߡ�BD���ܡ�ҵ����ҵ�����
         if(selectStatus==null&&!appType.equals("confirm")) {
             viewBean = getPerson(viewBean, logic, rid);
         } else if(selectStatus!=null&&!appType.equals("confirm")){
             viewBean = getAcntPerson(viewBean, acntLogic, rid);
         } else if(appType.equals("confirm")) {
             viewBean = getAcntPerson(viewBean, acntLogic, acntId);
         }
         
         //����Ǵ���������ר�������ר����ȡ��Ӧ�ĸ�ר��
       if(!appType.equals("change")){
         if(relPrjType.equals(IDtoAccount.PROJECT_RELATION_SUB)
                 ||relPrjType.equals(IDtoAccount.PROJECT_RELATION_RELATED)
                 ||relPrjType.equals(IDtoAccount.PROJECT_RELATION_FINANCE)) {
          
             acnt = acntLogic.loadMasterProjectFromAcnt(relParentId);
             String parentProject =acnt.getAcntId()+"---"+acnt.getAcntName();
             viewBean.setParentProject(parentProject);
             viewBean.setContractAcntId(contractAcntId);
             request.setAttribute("parentExecSite", acnt.getExecSite());
             if(("true".equals(this.getRequest().getParameter("isFinance"))&&appType.equals("add"))
                     ||appType.equals("confirm")
                     ||appType.equals("check")){
                 if(relPrjType.equals(IDtoAccount.PROJECT_RELATION_FINANCE)){
                     this.getRequest().setAttribute("isFinance","true");
                     viewBean.setRelPrjType(IDtoAccount.PROJECT_RELATION_FINANCE);
                 }
             }
             
         }
       }
       
       //�����Ƿ���ʾcomment�Լ��Ƿ�ɱ༭
       if(selectStatus==null&&!appType.equals("confirm")){
         if(viewBean.getApplicationStatus().equals("Rejected")){
             request.setAttribute("showComment", "true");
             request.setAttribute("readOnly", "true");
         } 
       }
       if(appType.equals("check")){
           request.setAttribute("showComment", "true");
           request.setAttribute("readOnly", "false");
           viewBean.setComment("");
       }
       if(appType.equals("confirm")){
           if(confirmStatus!=null&&confirmStatus.equals("Rejected")){
               request.setAttribute("showComment", "true");
               request.setAttribute("readOnly", "true");
           }
       }
       if(confirmCheck!=null&&confirmCheck.equals("true")){
           request.setAttribute("showComment", "true");
           request.setAttribute("readOnly", "false");
           viewBean.setComment("");
       }
         
         //��ViewBean���õ�ǰ̨
         request.setAttribute(Constant.VIEW_BEAN_KEY,viewBean);
         request.setAttribute("accessType", accessType);
         request.setAttribute("appType", appType);
      
      
    }
    /**
     * ��ȡҵ���߲���List
     * @return
     */
    private List getBlList() {
 	   IAccountService service = (IAccountService) this.getBean("AccountLogic");
 	   List<Acnt> blList = service.queryBlDept();
 	   List blOptions = new ArrayList();
 	   blOptions.add(firstOption);
 	   SelectOptionImpl blOption = null; 
 	   for (Acnt acnt : blList) {
 		   blOption = new SelectOptionImpl(acnt.getAcntId()+"---"+acnt.getAcntName(),acnt.getAcntId(),
 				   	acnt.getAcntId()+"--"+acnt.getAcntName());
 		   blOptions.add(blOption);
 	   }
 	   return blOptions;
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
     * ��ȡִ�оݵ��List
     * @return ִ�оݵ���б�
     */
    private List getExecSiteList() {
//      ���ִ�оݵ�
        ISiteService siteLogic = (ISiteService) this.getBean("AreaCodeLogic");                     
        List siteList = siteLogic.listAllEabled();
        List execSiteList = new ArrayList();
        SelectOptionImpl siteOption = null;
        execSiteList.add(firstOption);
        for(int i = 0; i<siteList.size();i++) {
            Site site = (Site) siteList.get(i);
            siteOption = new SelectOptionImpl(site.getSiteName(), site.getSiteCode(), site.getSiteName());
        
            execSiteList.add(siteOption);
        }         
        return execSiteList;
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
    /**
     * ��ȡ��ר����ص���Ա����
     * @param viewBean ������ʾ��ViewBean 
     * @param logic AcntApp�ķ���
     * @param rid ��ǰAcntApp��Rid
     * @return ����Ա������ViewBean
     */
    private VbProjectAcntApp getPerson(VbProjectAcntApp viewBean, IAccountApplicationService logic, Long rid) {
        //��������Ա,����ר��������ʱ��ǩ���ߡ�BD���ܡ�ҵ����ҵ�����
        AcntPersonApp acntPersonApp = new AcntPersonApp();
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_PM);
        if(acntPersonApp!=null){
            viewBean.setPMName(acntPersonApp.getName());
            viewBean.setPMId(acntPersonApp.getLoginId());
            viewBean.setPMDomain(acntPersonApp.getDomain());
        } 
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_TC_SIGNER);
        if(acntPersonApp!=null){
            viewBean.setTCSName(acntPersonApp.getName());
            viewBean.setTCSId(acntPersonApp.getLoginId());
            viewBean.setTCSDomain(acntPersonApp.getDomain());
        }
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_BD_MANAGER);
        if(acntPersonApp!=null){
            viewBean.setBDMName(acntPersonApp.getName());
            viewBean.setBDId(acntPersonApp.getLoginId());
            viewBean.setBDDomain(acntPersonApp.getDomain());
        }
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_LEADER);
        if(acntPersonApp!=null){
            viewBean.setLeaderName(acntPersonApp.getName());
            viewBean.setLeaderId(acntPersonApp.getLoginId());
            viewBean.setLeaderDomain(acntPersonApp.getDomain());
        }
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_SALES);
        if(acntPersonApp!=null){
            viewBean.setSalesName(acntPersonApp.getName());
            viewBean.setSalesId(acntPersonApp.getLoginId());
            viewBean.setSalesDomain(acntPersonApp.getDomain());
        } 
        
        return viewBean;
    }
    /**
     * ��ȡAcnt���е������Ա��Ϣ
     * @param viewBean ������ʾ��ViewBean
     * @param anctLogic Acnt�ķ���
     * @param rid ��ǰAcnt��Rid
     * @return ����Acnt��Ա��Ϣ��ViewBean
     */
    private VbProjectAcntApp getAcntPerson(VbProjectAcntApp viewBean, IAccountService acntLogic, Long rid) {
       // ��������Ա,����ר��������ʱ��ǩ���ߡ�BD���ܡ�ҵ����ҵ�����
        AcntPerson acntPerson = new AcntPerson();
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_PM);
        if(acntPerson!=null){
            viewBean.setPMName(acntPerson.getName());
            viewBean.setPMId(acntPerson.getLoginId());
            viewBean.setPMDomain(acntPerson.getDomain());
        } 
        
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_TC_SIGNER);
        if(acntPerson!=null){
            viewBean.setTCSName(acntPerson.getName());
            viewBean.setTCSId(acntPerson.getLoginId());
            viewBean.setTCSDomain(acntPerson.getDomain());
        }
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_BD_MANAGER);
        if(acntPerson!=null){
            viewBean.setBDMName(acntPerson.getName());
            viewBean.setBDId(acntPerson.getLoginId());
            viewBean.setBDDomain(acntPerson.getDomain());
        }
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_LEADER);
        if(acntPerson!=null){
            viewBean.setLeaderName(acntPerson.getName());
            viewBean.setLeaderId(acntPerson.getLoginId());
            viewBean.setLeaderDomain(acntPerson.getDomain());
        }
        acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_SALES);
        if(acntPerson!=null){
            viewBean.setSalesName(acntPerson.getName());
            viewBean.setSalesId(acntPerson.getLoginId());
            viewBean.setSalesDomain(acntPerson.getDomain());
        } 
        
        return viewBean;
    }
    /**
     * ��������Ա,����ר��������ʱ��ǩ���ߡ�BD���ܡ�ҵ����ҵ�����
     * @param viewBean
     * @param acntLogic
     * @param acntId
     * @return
     */
    private VbProjectAcntApp getAcntPerson(VbProjectAcntApp viewBean, IAccountService acntLogic, String acntId) {
        // ��������Ա,����ר��������ʱ��ǩ���ߡ�BD���ܡ�ҵ����ҵ�����
         AcntPerson acntPerson = new AcntPerson();
         Acnt acnt = acntLogic.loadByAcntId(acntId, "1");
         Long rid = acnt.getRid();
         acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_APPLICANT);
         if(acntPerson!=null){
             viewBean.setApplicantName(acntPerson.getName());
         } 
         acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_PM);
         if(acntPerson!=null){
             viewBean.setPMName(acntPerson.getName());
             viewBean.setPMId(acntPerson.getLoginId());
             viewBean.setPMDomain(acntPerson.getDomain());
         } 
         
         acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_TC_SIGNER);
         if(acntPerson!=null){
             viewBean.setTCSName(acntPerson.getName());
             viewBean.setTCSId(acntPerson.getLoginId());
             viewBean.setTCSDomain(acntPerson.getDomain());
         }
         acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_BD_MANAGER);
         if(acntPerson!=null){
             viewBean.setBDMName(acntPerson.getName());
             viewBean.setBDId(acntPerson.getLoginId());
             viewBean.setBDDomain(acntPerson.getDomain());
         }
         acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_LEADER);
         if(acntPerson!=null){
             viewBean.setLeaderName(acntPerson.getName());
             viewBean.setLeaderId(acntPerson.getLoginId());
             viewBean.setLeaderDomain(acntPerson.getDomain());
         }
         acntPerson = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_SALES);
         if(acntPerson!=null){
             viewBean.setSalesName(acntPerson.getName());
             viewBean.setSalesId(acntPerson.getLoginId());
             viewBean.setSalesDomain(acntPerson.getDomain());
         } 
         
         return viewBean;
     }
}
