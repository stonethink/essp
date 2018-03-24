package server.essp.projectpre.ui.project.check;



import itf.webservices.IAccountWService;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.*;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.idsetting.IIdSettingService;
import server.essp.projectpre.service.log.ILogService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.sendMail.ISendMailService;
import server.essp.projectpre.service.site.ISiteService;
import server.essp.projectpre.service.syncException.ISyncExceptionService;
import server.essp.projectpre.ui.project.apply.AfProjectAcntApp;
import server.essp.security.service.role.IRoleService;
import server.essp.syncproject.ISyncToHrms;
import server.essp.syncproject.SyncService;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.*;

import com.wits.util.comDate;

/**
 * 复核专案新增申请的Action
 * @author Stephen.zheng
 *
 */
public class AcCheckProjectApp extends AbstractESSPAction{
    public static final String vmFile11 = "mail/template/proj/CheckApplyMail.html";
    public static final String vmFile12 = "mail/template/proj/RejectMail.html";
    String acntId = null;
    Long acntRid = null;
    /**
     * 对专案新增申请进行审核操作
     *   1.创建并赋予专案代码
     *   2.保存技术资料
     *   3.保存客户资料
     *   4.保存人员资料
     *   5.数据同步（财务_新增）
     *   6.发送邮件给申请人
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                         TransactionData data) throws BusinessException {
        String status = request.getParameter("status");
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userName = user.getUserName();
        AfProjectAcntApp af = (AfProjectAcntApp) this.getForm();
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountService  acntLogic = (IAccountService) this.getBean("AccountLogic"); 
        IAccountWService finLogic = (IAccountWService)this.getBean("FinAccountService");
        IAccountWService tsLogic = (IAccountWService)this.getBean("TSAccountService");
        ISyncToHrms syncToHrms = (ISyncToHrms)this.getBean("SyncToHrms");
        Long rid = Long.valueOf(af.getRid());
        AcntApp acntApp = logic.loadByRid(rid);
         if(status.equals(Constant.CONFIRMED)) {
            Acnt acnt = new Acnt();
            HBComAccess.assignedRid(acnt);
            acntRid = acnt.getRid();
            try {
                DtoUtil.copyProperties(acnt, acntApp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            acnt.setIsPrinted("0");
            acnt.setRid(acntRid); 
            String customerId = "";
            String custShort = ""; 
            if(af.getCustomerId().indexOf("---")>-1){
                customerId = af.getCustomerId().substring(0,af.getCustomerId().indexOf("---"));
                custShort = af.getCustomerId().substring(af.getCustomerId().indexOf("-")+3);  
            } else {
                customerId = af.getCustomerId();
                custShort = af.getCustomerId();
            }
            acnt.setCustomerId(customerId);
            //创建并赋予专案代码
            IIdSettingService idLogic = (IIdSettingService) this.getBean("IdSettingLogic");
            IdSetting idSetting = idLogic.loadByKey(Constant.PROJECT_CODE);
            String newAcntId = null;
            
            if(idSetting.getCodingMethod().equals(Constant.SERIAL_NUMBER)){
                if(acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_MASTER)
                        ||acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_RELATED)){
                newAcntId = acntLogic.createProjectCode(idSetting.getLength(), idSetting.getCurrentSeq());            
                idSetting.setCurrentSeq(Long.valueOf(newAcntId));
                idLogic.update(idSetting);
                } else if(acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_SUB)
                              ||acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_FINANCE)){
//                    int length = idSetting.getLength().intValue();
                    int length = acntApp.getRelParentId().length();
                    newAcntId = acntApp.getRelParentId().substring(0, length-1);
                  
                }
            } else {
                if(acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_MASTER)
                        ||acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_RELATED)){
                newAcntId = acntLogic.createYearAcntId(idSetting.getLength(), idSetting.getCurrentSeq());
                String newCurrentSeq = newAcntId.substring(2);
                idSetting.setCurrentSeq(Long.valueOf(newCurrentSeq));
                idLogic.update(idSetting);
                } else if(acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_SUB)
                              ||acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_FINANCE)){
//                    int length = idSetting.getLength().intValue();
                    int length = acntApp.getRelParentId().length();
                    newAcntId = acntApp.getRelParentId().substring(0, length-1);
                   
                }

            }
                acntId = newAcntId+acntApp.getExecSite();
                Acnt acntNest = acntLogic.loadByAcntId(acntId, "1");
                if(acntNest!=null){
                    throw new BusinessException("error.logic.acntServiceImpl.acntIdduplicate");
                }
                acnt.setAcntId(acntId);
            acnt.setAcntStatus(Constant.NORMAL);
            
            if(acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_MASTER))
            {
            acnt.setRelParentId(acntId);
            acnt.setContractAcntId(acntId);
            }
            acntLogic.save(acnt);
            
            //保存技术资料
            saveTechInfo(rid,acnt.getRid());
            //保存客户资料
            saveCustomerInfo(rid,acnt.getRid());
            //保存人员资料
            savePerson(rid,acnt.getRid());
            //数据同步（财务_新增,TimeSheet）
            SyncService syncLogic = (SyncService) this.getBean("SyncService");
            Map map = new HashMap();
            map.put("projId", acnt.getAcntId());
            map.put("projName", acnt.getAcntFullName());
            map.put("nickName", acnt.getAcntName());
            map.put("manager", acnt.getCostAttachBd());
            AcntPerson person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_LEADER);
            map.put("leader", person.getLoginId());
            map.put("costDept", acnt.getExecUnitId());
            person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_TC_SIGNER);
            map.put("tcSigner", person.getLoginId());
            map.put("planFrom", comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd"));
            map.put("planTo", comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd"));
//            ICustomerService custLogic = (ICustomerService)this.getBean("CustomerLogic");
//            Customer customer = custLogic.loadByCustomerId(af.getCustomerId().substring(0,af.getCustomerId().indexOf("---")));
            map.put("custShort", custShort);
            map.put("estManmonth", acnt.getEstManmonth());
            map.put("closeMark", "N");
            map.put("achieveBelong", acnt.getAchieveBelong());
            person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_SALES);     
            map.put("sales", person.getLoginId());
            map.put("actuFrom", comDate.dateToString(acnt.getAcntActualStart(), "yyyy-MM-dd"));
            map.put("actuTo", comDate.dateToString(acnt.getAcntActualFinish(),"yyyy-MM-dd"));
            map.put("manMonth", acnt.getActualManmonth());
            map.put("productName", acnt.getProductName());
            map.put("projectDesc", acnt.getAcntBrief());
            person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_APPLICANT);  
            map.put("applicant", person.getLoginId());
            person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_BD_MANAGER);
            map.put("divisionManager", person.getLoginId());
            person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_PM);
            //根据新需求专案经理字段放入员工的名字 
            //将PM的ID放入另一KEY中 add by wenhaizheng 20080305
            //结转到财务的PM还是存放PM的ID mark by wenhaizheng 20080509
            map.put("projectManager", person.getLoginId());
            map.put("PM", person.getLoginId());
            map.put("projectExecUnit", acnt.getExecUnitId());
            person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
            map.put("custServiceManager", person.getLoginId());
            person = acntLogic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
            map.put("engageManager", person.getLoginId());
            map.put("bizSource", acnt.getBizSource());
            map.put("masterProjects", acnt.getContractAcntId());
            map.put("projectProperty", acnt.getAcntAttribute());
            map.put("estimatedWords", acnt.getEstSize());
            map.put("UsePrimavera", acnt.getPrimaveraAdapted());
            map.put("Billable", acnt.getBillingBasis());
            map.put("AchieveBelong", acnt.getAchieveBelong());
            map.put("bl", acnt.getBl());
            map = setTechinfo(map, acnt.getRid());
            map = getTechInfoTextList(map, acnt.getRid());
            map = getTechInfoTextDetail(map, acnt.getRid(), Constant.DEVELOPENVIRONMENT);
            map = getTechInfoTextDetail(map, acnt.getRid(), Constant.TRNSLATEPUBLISHTYPE);
            map = getCustContactor(map, acnt.getRid());
            try{
                finLogic.addAccount(map);
            }catch (BusinessException e){
               String errMessge = e.getErrorCode();
               if(errMessge.equals("INSERT Finance ERROR")||errMessge.
                       equals("INSERT Finance Rollback ERROR")){        
                 addSyncException(acnt,"FinAccountWServiceImpl",errMessge,user.getUserLoginId());        
                 request.setAttribute("Message","Carry forward fail!");
               }        
            }
            try{
            	if(!"V".equalsIgnoreCase(acnt.getExecSite())){
            		tsLogic.addAccount(map);
            	}
            }catch (BusinessException e){
                String errMessge = e.getErrorCode();
                if(errMessge.equals("Synchronize Insert TimeSheet Error")){
                    addSyncException(acnt,"SyncAccountImp",errMessge,user.getUserLoginId());
                    request.setAttribute("Message","Carry forward fail!");
                }
            }
            ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
            Site site = siteLogic.loadByCode(acnt.getExecSite());
            String siteName = "";
            if(site != null) {
            	siteName = site.getSiteName();
            }
            if (siteName.equalsIgnoreCase("TP")) {
				try {
					syncToHrms.addProjcet(acnt, siteName, (String)map.get("PM"));
				} catch (BusinessException e) {
						addSyncException(acnt, "SyncToHrms", e.getErrorMsg(), user
								.getUserLoginId());
						request.setAttribute("Message", "Carry forward fail!");
				}
			}
          } else {
            acntApp.setRemark(af.getComment()); 
          }
            acntApp.setApplicationStatus(status);
            logic.update(acntApp);
            ISendMailService sendLogic = (ISendMailService)this.getBean("SendMailLogic");
            //申请被拒绝或被确认都发送邮件给申请人
            VbCheckMail vb = null;
            if(status.equals(Constant.CONFIRMED)){
                vb = prepareMailContext(af, acntApp, "acnt");
                Map mailMap = new HashMap();
                this.prepareMailMap(mailMap,acntApp.getApplicantId(), acntApp.getDomain());
//                sendLogic.sendMail(acntApp.getApplicantId(),acntApp.getDomain(), vmFile11, "Project Apply Check", vb);
                String mailReceiver = this.sendMailToPeople(acntApp, vb, mailMap);
                //只给HQ的PMO发送邮件
                String[] roleIds = {DtoRole.ROLE_HAP};
                IRoleService roleLogic =(IRoleService)this.getBean("RoleService");
                List projectOfficeList = roleLogic.listLoginIdUnderRole(roleIds);
                for(int i=0;i<projectOfficeList.size();i++){
                    DtoUserBase dtoUser = (DtoUserBase)projectOfficeList.get(i);
                    this.prepareMailMap(mailMap,dtoUser.getUserLoginId(), dtoUser.getDomain());
//                    sendLogic.sendMail(dtoUser.getUserLoginId(),dtoUser.getDomain(), vmFile11, "Project Apply Check", vb);
                }
                List mailList = new ArrayList(mailMap.values());
                int size = mailList.size();
                String temp = "";
                String loginId = "";
                String domain = "";
                for(int i = 0;i<size;i++){
                    temp = (String)mailList.get(i);
                    loginId = temp.substring(0,temp.indexOf(":"));
                    domain= temp.substring(temp.indexOf(":")+1);
                    sendLogic.sendMail(loginId,domain, vmFile11, "Project Apply Check("+vb.getAcntId()+")", vb);
                }
                //向LOG表中插入一笔记录
                if(mailReceiver!=null&&!"".equals(mailReceiver)){
                    mailReceiver = mailReceiver+","+acntApp.getApplicantName();
                } else {
                    mailReceiver = acntApp.getApplicantName();
                }
                ILogService logicLog=(ILogService)this.getBean("LogLogic");
                PPLog log = new PPLog();
                log.setAcntId(acntId);
                log.setApplicationType(Constant.PROJECTADDAPP);
                log.setDataType(Constant.LOG_PROJECT);
                log.setMailReceiver(mailReceiver);
                log.setOperation(status);
                log.setOperationDate(new Date());
                log.setOperator(userName);
                logicLog.save(log);
            } else if(status.equals(Constant.REJECTED)) {
                vb = prepareMailContext(af, acntApp, "acntApp");
                vb.setReMark(af.getComment());
                sendLogic.sendMail(acntApp.getApplicantId(),acntApp.getDomain(), vmFile12, "Project Apply Rejected", vb);
                //给执行者发送邮件
                sendLogic.sendMail(user.getUserLoginId(),user.getDomain(), vmFile12, "Project Apply Rejected", vb);
                //向LOG表中插入一笔记录
                ILogService logicLog=(ILogService)this.getBean("LogLogic");
                PPLog log = new PPLog();
                log.setAcntId(acntApp.getRid().toString());
                log.setApplicationType(Constant.PROJECTADDAPP);
                log.setDataType(Constant.LOG_PROJECT);
                log.setMailReceiver(acntApp.getApplicantName());
                log.setOperation(status);
                log.setOperationDate(new Date());
                log.setOperator(userName);
                logicLog.save(log);
            }
         
    }
    
    /**
     * 截转失败时向同步异常信息表中插入一笔数据
     * @param acnt
     * @param impClassName
     * @param errorMessge
     * @param loginId
     */
    private void addSyncException(Acnt acnt,String impClassName,
            String errorMessge,String loginId){
        ISyncExceptionService syncLogic = (ISyncExceptionService) this.getBean("SyncExceptionLogic");
        PpSyncException syncExcption = new PpSyncException();
        syncExcption.setAcntRid(acnt.getRid());
        syncExcption.setAcntId(acnt.getAcntId());
        syncExcption.setAcntName(acnt.getAcntName());
        syncExcption.setImpClassName(impClassName);
        if(impClassName.equals("FinAccountWServiceImpl")){
            syncExcption.setModel("Finance");
        }else if("SyncAccountImp".equals(impClassName)){
            syncExcption.setModel("TimeSheet");
        } else if("SyncToHrms".equals(impClassName)) {
        	syncExcption.setModel("Hrms");
        }
        syncExcption.setStatus(Constant.ACTIVE);
        syncExcption.setType("Apply");
        syncExcption.setErrorMessage(errorMessge);
        syncExcption.setRcu(loginId);
        syncExcption.setRct(new Date());
        syncLogic.save(syncExcption);
    }
    
    /**
     * 将要邮件接收人员放入不重复的Map中
     * @param mailMap
     * @param loginId
     * @param domain
     */
    private void prepareMailMap(Map mailMap, String loginId, String domain){
        if(mailMap.containsKey(loginId)){
            return;
        }
        mailMap.put(loginId,loginId+":"+domain);
    }
    /**
     * 发送邮件给PM,BD主管,业务经理
     * @param acntApp
     * @param vb
     * @param sendLogic
     * @return String
     */
    private String sendMailToPeople(AcntApp acntApp, VbCheckMail vb, Map mailMap){
        String mailReceiver = "";
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        AcntPersonApp personApp = logic.loadByRidFromPersonApp(acntApp.getRid(), IDtoAccount.USER_TYPE_PM);
        if(!acntApp.getRelPrjType().equals(IDtoAccount.ACCOUNT_TYPE_FINANCE)){
            this.prepareMailMap(mailMap, personApp.getLoginId(), personApp.getDomain());
        
//        sendLogic.sendMail(personApp.getLoginId(),personApp.getDomain(), vmFile11, "Project Apply Check", vb);
            if(!personApp.getName().equals(acntApp.getApplicantName())){
                mailReceiver = personApp.getName();
            }
        }
        personApp = logic.loadByRidFromPersonApp(acntApp.getRid(), IDtoAccount.USER_TYPE_BD_MANAGER);
        if(!acntApp.getRelPrjType().equals(IDtoAccount.ACCOUNT_TYPE_FINANCE)){
            this.prepareMailMap(mailMap, personApp.getLoginId(), personApp.getDomain());
        
//        sendLogic.sendMail(personApp.getLoginId(),personApp.getDomain(), vmFile11, "Project Apply Check", vb);
            if(!personApp.getName().equals(acntApp.getApplicantName())){
                mailReceiver = mailReceiver+","+personApp.getName();
            }
        }
        personApp = logic.loadByRidFromPersonApp(acntApp.getRid(), IDtoAccount.USER_TYPE_LEADER);
        if(!acntApp.getRelPrjType().equals(IDtoAccount.ACCOUNT_TYPE_FINANCE)){
            this.prepareMailMap(mailMap, personApp.getLoginId(), personApp.getDomain());
        
//        sendLogic.sendMail(personApp.getLoginId(),personApp.getDomain(), vmFile11, "Project Apply Check", vb);
            if(!personApp.getName().equals(acntApp.getApplicantName())){
                mailReceiver = mailReceiver+","+personApp.getName();
            }
        }
        return mailReceiver;
    }
    /**
     * 准备待发送的邮件内容
     * @param af
     * @param acntApp
     * @param type 
     * @return
     */
    private VbCheckMail prepareMailContext(AfProjectAcntApp af, AcntApp acntApp, String type) {
        VbCheckMail vb = new VbCheckMail();
        vb.setRid(af.getRid());
        vb.setAcntId(acntId);
        vb.setApplicantName(acntApp.getApplicantName());
        vb.setApplicationStatus(acntApp.getApplicationStatus());
        vb.setRelPrjType(acntApp.getRelPrjType());
        vb.setAcntName(acntApp.getAcntName());
        vb.setAcntFullName(acntApp.getAcntFullName());
        vb.setAcntBrief(acntApp.getAcntBrief());
        vb.setProductName(acntApp.getProductName());
        ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
        Site site = siteLogic.loadByCode(acntApp.getExecSite());
        if(site!=null){
        vb.setExecSite(site.getSiteName());
        }
        IBdService bdLogic = (IBdService)this.getBean("BdCodeLogic");
        Bd bd = bdLogic.loadByBdCode(acntApp.getCostAttachBd());
        if(bd!=null){
        vb.setCostAttachBd(bd.getBdName());
        }
        bd = bdLogic.loadByBdCode(acntApp.getBizSource());
        if(bd!=null){
        vb.setBizSource(bd.getBdName());
        }
        bd = bdLogic.loadByBdCode(acntApp.getAchieveBelong());
        if(bd!=null){
        vb.setAchieveBelong(bd.getBdName());
        }
        IParameterService parameterLogic = (IParameterService)this.getBean("ParameterLogic");
        Parameter parameter = parameterLogic.loadByKindCode(Constant.PROJEC_TYPE, acntApp.getAcntAttribute());
        if(parameter!=null){
        vb.setAcntAttribute(parameter.getName());
        }
        vb.setExecUnitId(acntApp.getExecUnitId());
        vb.setEstSize(acntApp.getEstSize());
        vb.setEstManmonth(acntApp.getEstManmonth());
        vb.setActualManmonth(acntApp.getActualManmonth());
        vb.setAcntPlannedStart(comDate.dateToString(acntApp.getAcntPlannedStart(), "yyyy-MM-dd"));
        vb.setAcntPlannedFinish(comDate.dateToString(acntApp.getAcntPlannedFinish(), "yyyy-MM-dd"));
        vb.setAcntActualStart(comDate.dateToString(acntApp.getAcntActualStart(), "yyyy-MM-dd"));
        vb.setAcntActualFinish(comDate.dateToString(acntApp.getAcntActualFinish(), "yyyy-MM-dd"));
        if("1".equals(acntApp.getPrimaveraAdapted())) {
        	vb.setPrimaveraAdapted("Y");
        } else {
        	vb.setPrimaveraAdapted("N");
        }
        if("1".equals(acntApp.getBillingBasis())) {
        	vb.setBillingBasis("Y");
        } else {
        	vb.setBillingBasis("N");
        }
        vb.setBizProperty(acntApp.getBizProperty());
        vb.setBillType(acntApp.getBillType());
        vb.setBl(acntApp.getBl());
        
        if(type.equals("acnt")){
            //获取人员信息
            vb = getPerson(acntRid, vb);
            //获取用户信息
            vb = getCustomer(acntRid, vb);
        } else {
            //拒绝时获取人员和用户信息
            vb = getPersonApp(acntApp.getRid(), vb);
            vb = getCustomerApp(acntApp.getRid(), vb);
        }
//        ICustomerService custLogic = (ICustomerService)this.getBean("CustomerLogic");
//        Customer cust = custLogic.loadByCustomerId(af.getCustomerId());
        vb.setCustomerId(af.getCustomerId());
        return vb;
    }
    /**
     * 生成客户资料邮件内容(申请表)
     * @param acntRid
     * @param vb
     * @return
     */
    private VbCheckMail getCustomerApp(Long acntAppRid, VbCheckMail vb){
        IAccountApplicationService acntAppLogic = (IAccountApplicationService)this.getBean("AccountApplicationLogic");
        AcntCustContactorApp acntCustContactorApp = acntAppLogic.loadByRidTypeFromAcntCustContactorApp(acntAppRid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        vb.setContract(acntCustContactorApp.getName());
        vb.setContractTel(acntCustContactorApp.getTelephone());
        vb.setContractEmail(acntCustContactorApp.getEmail());
        
        acntCustContactorApp = acntAppLogic.loadByRidTypeFromAcntCustContactorApp(acntAppRid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        vb.setExecutive(acntCustContactorApp.getName());
        vb.setExecutiveTel(acntCustContactorApp.getTelephone());
        vb.setExecutiveEmail(acntCustContactorApp.getEmail());
        
        acntCustContactorApp = acntAppLogic.loadByRidTypeFromAcntCustContactorApp(acntAppRid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        vb.setFinancial(acntCustContactorApp.getName());
        vb.setFinancialTel(acntCustContactorApp.getTelephone());
        vb.setFinancialEmail(acntCustContactorApp.getEmail());
        return vb;
        
    }
    /**
     * 生成人员资料邮件内容
     * @param acntRid
     * @param vb
     * @return
     */
    private VbCheckMail getPersonApp(Long acntAppRid, VbCheckMail vb){
        IAccountApplicationService acntAppLogic = (IAccountApplicationService)this.getBean("AccountApplicationLogic");
        AcntPersonApp personApp = acntAppLogic.loadByRidFromPersonApp(acntAppRid, IDtoAccount.USER_TYPE_PM);
        vb.setPMName(personApp.getName());
        
        personApp = acntAppLogic.loadByRidFromPersonApp(acntAppRid, IDtoAccount.USER_TYPE_TC_SIGNER);
        vb.setTCSName(personApp.getName());
        
        personApp = acntAppLogic.loadByRidFromPersonApp(acntAppRid, IDtoAccount.USER_TYPE_BD_MANAGER);
        vb.setBDMName(personApp.getName());
        
        personApp = acntAppLogic.loadByRidFromPersonApp(acntAppRid, IDtoAccount.USER_TYPE_LEADER);
        vb.setLeaderName(personApp.getName());
        
        personApp = acntAppLogic.loadByRidFromPersonApp(acntAppRid, IDtoAccount.USER_TYPE_SALES);
        vb.setSalesName(personApp.getName());
        
        personApp = acntAppLogic.loadByRidFromPersonApp(acntAppRid, IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        vb.setCustServiceManagerName(personApp.getName());
        
        personApp = acntAppLogic.loadByRidFromPersonApp(acntAppRid, IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        vb.setEngageManagerName(personApp.getName());
        
        
        return vb;
        
    }
    /**
     * 生成客户资料邮件内容
     * @param acntRid
     * @param vb
     * @return
     */
    private VbCheckMail getCustomer(Long acntRid, VbCheckMail vb){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        AcntCustContactor acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(acntRid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        vb.setContract(acntCustContactor.getName());
        vb.setContractTel(acntCustContactor.getTelephone());
        vb.setContractEmail(acntCustContactor.getEmail());
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(acntRid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        vb.setExecutive(acntCustContactor.getName());
        vb.setExecutiveTel(acntCustContactor.getTelephone());
        vb.setExecutiveEmail(acntCustContactor.getEmail());
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(acntRid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        vb.setFinancial(acntCustContactor.getName());
        vb.setFinancialTel(acntCustContactor.getTelephone());
        vb.setFinancialEmail(acntCustContactor.getEmail());
        return vb;
        
    }
    
    /**
     * 生成人员资料邮件内容
     * @param acntRid
     * @param vb
     * @return
     */
    private VbCheckMail getPerson(Long acntRid, VbCheckMail vb){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        AcntPerson person = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_PM);
        vb.setPMName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_TC_SIGNER);
        vb.setTCSName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_BD_MANAGER);
        vb.setBDMName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_LEADER);
        vb.setLeaderName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_SALES);
        vb.setSalesName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        vb.setCustServiceManagerName(person.getName());
        
        person = acntLogic.loadByRidFromPerson(acntRid, IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        vb.setEngageManagerName(person.getName());
        
        
        return vb;
        
    }
    
    /**
     * 保存技术资料相关信息
     * @param rid 当前申请的RID
     */ 
    private void saveTechInfo(Long rid, Long acntRid) {
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountService  acntLogic = (IAccountService) this.getBean("AccountLogic"); 
        List techInfoAppList = logic.listByRidFromTechInfoApp(rid);
        for(int i=0;i<techInfoAppList.size();i++) {
            AcntTechinfoApp techInfoApp = (AcntTechinfoApp)techInfoAppList.get(i);
            AcntTechinfo techInfo = new AcntTechinfo();
            techInfo.getId().setAcntRid(acntRid);
            techInfo.getId().setCode(techInfoApp.getId().getCode());
            techInfo.getId().setKind(techInfoApp.getId().getKind());
            techInfo.setDescription(techInfoApp.getDescription());
            acntLogic.saveAcntTechinfo(techInfo);
        }
    }
    /**
     * 保存客户资料相关信息
     * @param rid 当前申请的RID
     */
    private void saveCustomerInfo(Long rid, Long acntRid) {
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountService  acntLogic = (IAccountService) this.getBean("AccountLogic"); 
        List customerInfoAppList = logic.listByRidFromCustomerInfoApp(rid);
        for(int i=0;i<customerInfoAppList.size();i++) {
            AcntCustContactorApp acntCustContactorApp = (AcntCustContactorApp)customerInfoAppList.get(i);
            AcntCustContactor acntCustContactor = new AcntCustContactor();
            acntCustContactor.setAcntRid(acntRid);
            acntCustContactor.setContactorType(acntCustContactorApp.getContactorType());
            acntCustContactor.setName(acntCustContactorApp.getName());
            acntCustContactor.setTelephone(acntCustContactorApp.getTelephone());
            acntCustContactor.setEmail(acntCustContactorApp.getEmail());
            acntLogic.saveAcntCustContactor(acntCustContactor);
        }
    }
    /**
     * 保存人员资料相关信息
     * @param rid 当前申请的RID
     */
    private void savePerson(Long rid, Long acntRid) {
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        IAccountService  acntLogic = (IAccountService) this.getBean("AccountLogic"); 
        List personAppList = logic.listByRidFromPersonApp(rid);
        for(int i=0;i<personAppList.size();i++) {
            AcntPersonApp acntPersonApp = (AcntPersonApp)personAppList.get(i);
            AcntPerson acntPerson = new AcntPerson();
            acntPerson.setAcntRid(acntRid);
            acntPerson.setPersonType(acntPersonApp.getPersonType());
            acntPerson.setLoginId(acntPersonApp.getLoginId());
            acntPerson.setName(acntPersonApp.getName());
            acntPerson.setDomain(acntPersonApp.getDomain());
            acntLogic.saveAcntPerson(acntPerson);
        }
        AcntApp acntApp = logic.loadByRid(rid);
        AcntPerson acntPerson = new AcntPerson();
        acntPerson.setAcntRid(acntRid);
        acntPerson.setPersonType(IDtoAccount.USER_TYPE_APPLICANT);
        acntPerson.setLoginId(acntApp.getApplicantId());
        acntPerson.setName(acntApp.getApplicantName());
        acntPerson.setDomain(acntApp.getDomain());
        acntLogic.saveAcntPerson(acntPerson);
    }
    
    /**
     * 将查询到的技术资料代码按类型放入MAP中,作为传到财务系统的数据
     * @param map
     * @param rid
     * @return
     */
    private Map setTechinfo(Map map, Long rid){
        String str = "";
        str = getTechInfoVb(rid, Constant.PROJECT_TYPE);
        map.put("projectType",str);
        map.put("projTypeNo", str);
        
        str = getTechInfoName(rid);//获取专案类型的中文名称
        map.put("AccountTypeName", str);
       
        str = getTechInfoVb(rid, Constant.PRODUCT_TYPE);
        map.put("productType", str);
        
       
        str = getTechInfoVb(rid, Constant.PRODUCT_ATTRIBUTE);
        map.put("productProperty", str);
        
       
        str = getTechInfoVb(rid, Constant.WORK_ITEM);
        map.put("workItem", str);
        
      
        str = getTechInfoVb(rid, Constant.TECHNICAL_DOMAIN);
        map.put("skillDomain", str);
        
      
        str = getTechInfoVb(rid, Constant.ORIGINAL_LANGUAGE);
        map.put("originalLan", str);
      
        str = getTechInfoVb(rid, Constant.TRANSLATION_LANGUANGE);
        map.put("transLan", str);
        return map;
    }
    /**
     * 根据专案的RID查出相关技术资料的代码
     * @param rid
     * @param type
     * @return
     */
    private String getTechInfoVb(Long rid, String type) {
        AcntTechinfo techInfo = new AcntTechinfo();
        String value = null;
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        List techinfoList = acntLogic.listByRidKindFromTechInfo(rid, type);
        for(int i=0;i<techinfoList.size();i++) {
            techInfo = (AcntTechinfo) techinfoList.get(i);
            value = value + "," + techInfo.getId().getCode();
        }
        if(value!=null){
            value = value.substring(5);
        }else {
            value = "";
        }
        return value;
    }
    /**
     * 根据专案的RID获取专案类型的名称
     * @param rid
     * @return
     */
    private String getTechInfoName(Long rid){
    	String name = "";
    	IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
    	IParameterService paraLogic = (IParameterService)this.getBean("ParameterLogic");
        List techinfoList = acntLogic.listByRidKindFromTechInfo(rid, Constant.PROJECT_TYPE);
        if(techinfoList!=null&&techinfoList.size()>0){
        	AcntTechinfo techInfo = (AcntTechinfo) techinfoList.get(0);
        	Parameter para = paraLogic.loadByKindCode(
        			Constant.PROJECT_TYPE, techInfo.getId().getCode());
            name = para.getName();
        }
    	return name;
    }
    /**
     * 将查询到的技术资料文本放入MAP中,作为传到财务系统的数据
     * @param map
     * @param rid
     * @return
     */
    private Map getTechInfoTextList(Map map,Long rid) {
      
//        str = getTechInfoTextDetail(rid, server.essp.projectpre.service.constant.Constant.DEVELOPENVIRONMENT);
//         
//      
//        vb = getTechInfoTextDetail(rid, server.essp.projectpre.service.constant.Constant.TRNSLATEPUBLISHTYPE);
      
        AcntTechinfo techInfo = new AcntTechinfo();
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
      
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.HARDREQ, Constant.HARDREQ);
        map.put("hardWareReq", techInfo.getDescription());
       
      
      
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.SOFTREQ, Constant.SOFTREQ);
        map.put("softWareReq", techInfo.getDescription());
       
       
       
        return map;
    }
    
    /**
     * 将查询到的技术资料文本详细资料放入MAP中,作为传到财务系统的数据
     * @param map
     * @param rid
     * @param kind
     * @return
     */
    private Map getTechInfoTextDetail(Map map,Long rid, String kind){
     

      IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
      AcntTechinfo techInfo = new AcntTechinfo();
     if(kind.equals(Constant.DEVELOPENVIRONMENT)){
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.JOBSYSTEM);
      map.put("devEnvOs", techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.DATABASE);
      map.put("devEnvDb", techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.TOOL);
      map.put("devEnvTool", techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.NETWORK);
      map.put("devEnvNetwork", techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.PROGRAMLANGUAGE);
      map.put("devEnvLang", techInfo.getDescription());
      
      techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.OTHERS);
      map.put("devEnvOther", techInfo.getDescription());
     } else if(kind.equals(Constant.TRNSLATEPUBLISHTYPE)) {
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.JOBSYSTEM);
         map.put("tranEnvOs", techInfo.getDescription());
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.DATABASE);
         map.put("tranEnvDb", techInfo.getDescription());
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.TOOL);
         map.put("tranEnvTool", techInfo.getDescription());
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.NETWORK);
         map.put("tranEnvNetwork", techInfo.getDescription());
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.PROGRAMLANGUAGE);
         map.put("tranEnvLang", techInfo.getDescription());
         
         techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, kind, Constant.OTHERS);
         map.put("tranEnvOther", techInfo.getDescription());
     }
     
      
      return map;
  }
    /**
     * 将查询到的客户资料放入MAP中,作为传到财务系统的数据
     * @param map
     * @param rid
     * @return
     */
    private Map getCustContactor(Map map,Long rid){
        AcntCustContactor acntCustContactor = new AcntCustContactor();
       

       
       
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
      
    
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        map.put("ccontact", acntCustContactor.getName());
        map.put("ccontTel", acntCustContactor.getTelephone());
        map.put("ccontEmail", acntCustContactor.getEmail());
      
       
      
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        map.put("pcontact", acntCustContactor.getName());
        map.put("pcontTel", acntCustContactor.getTelephone());
        map.put("pcontEmail", acntCustContactor.getEmail());
 
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        map.put("icontact", acntCustContactor.getName());
        map.put("icontTel", acntCustContactor.getTelephone());
        map.put("icontEmail", acntCustContactor.getEmail());
       
      
        
          return map;
    }

}
