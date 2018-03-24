package server.essp.projectpre.ui.project.apply;



import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.*;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.log.ILogService;
import server.essp.projectpre.service.mailprivilege.IMailPrivilegeService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.sendMail.ISendMailService;
import server.essp.projectpre.service.site.ISiteService;
import server.essp.security.service.role.IRoleService;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.user.DtoUserBase;

import com.wits.util.comDate;

/**
 * 更新申请信息的Action（包括新增申请，变更申请）
 * @author wenhaizheng
 *
 * 
 */
public class AcUpdateProjectAcntApp extends AbstractESSPAction{
    public static final String vmFile11 = "mail/template/proj/ApplyMail.html";
    public static final String vmFile12 = "mail/template/proj/ChangeMail.html";
    public String loginId = null;
    public String loginDomain = null;
    /**
     * 更新专案申请相关资料
     *    1.判断是否位提交申请
     *    2.判断是否为专案变更功能
     *    3.更新人员信息
     *    4.更新客户资料
     *    5.更新技术资料
     *    6.更新技术资料中的文本数据
     *    7.发送邮件给BD主管，业务经理和专案经理  
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userLoginId = user.getUserLoginId();
        String domain = user.getDomain();
        String attachPath = (String)request.getSession().getAttribute("attachmentPath");
        String[] otherPerson = (String[])request.getSession().getAttribute("otherPerson");
        String[] otherPersonId = (String[])request.getSession().getAttribute("otherPersonId");
        String[] otherPersonDomain = (String[])request.getSession().getAttribute("otherPersonDomain");
        loginId = userLoginId;
        loginDomain = domain;
        AfProjectAcntApp af = (AfProjectAcntApp) this.getForm();
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        Long rid = Long.valueOf(af.getRid());
        AcntApp acntApp = logic.loadByRid(rid);
        if(acntApp.getApplicationType().equals(Constant.PROJECTCHANGEAPP)){
            AcntApp acntAppNest = logic.loadCloseByTypeAcntId(server.essp.projectpre.service.constant.Constant.PROJECTCONFIRMAPP, acntApp.getAcntId());
            if(acntAppNest!=null){
                throw new BusinessException("error.logic.changeApp.existCloseApp");
            }
        }
        boolean isMail = false;//定义邮件标志位
        //判断是否为提交申请
        if(!acntApp.getApplicationStatus().equals(af.getApplicationStatus())) {
            acntApp.setApplicationStatus(af.getApplicationStatus());
            isMail = true;
            //向LOG表中插入一笔记录
            String mailReceiver = getMailReceiver(otherPerson, otherPersonId, af, acntApp);
            ILogService logicLog=(ILogService)this.getBean("LogLogic");
            PPLog log = new PPLog();
            if(acntApp.getApplicationType().equals(Constant.PROJECTADDAPP)){
                log.setAcntId(acntApp.getRid().toString());
              }else{
                log.setAcntId(acntApp.getAcntId());
              }
            log.setApplicationType(acntApp.getApplicationType());
            log.setDataType(Constant.LOG_PROJECT);
            log.setMailReceiver(mailReceiver);
            log.setOperation(acntApp.getApplicationStatus());
            log.setOperationDate(new Date());
            log.setOperator(user.getUserName());
            logicLog.save(log);
        }
        if(acntApp.getApplicationStatus().equals(Constant.REJECTED)){
            acntApp.setApplicationStatus(Constant.UNSUBMIT);
            
        }
        
        acntApp.setRelPrjType(af.getRelPrjType());
      //判断是否为专案变更功能
      if(!acntApp.getApplicationType().equals(Constant.PROJECTCHANGEAPP)){
        if(af.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_SUB)
                || af.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_RELATED)
                || af.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_FINANCE)){
               acntApp.setRelParentId(af.getParentProject().substring(0, af.getParentProject().indexOf("---")));
               acntApp.setContractAcntId(af.getContractAcntId());
        }
        acntApp.setAcntName(af.getAcntName());
        acntApp.setExecSite(af.getExecSite());
      }
      		acntApp.setAchieveBelong(af.getAchieveBelong());
            acntApp.setAcntAttribute(af.getAcntAttribute());
            acntApp.setAcntFullName(af.getAcntFullName());
            acntApp.setAcntBrief(af.getAcntBrief());
            
            acntApp.setOtherDesc(af.getOtherDesc());

        if(af.getExecUnitName()==null||"".equals(af.getExecUnitName()) || af.getExecUnitName().equals("-1")) {
            acntApp.setExecUnitId("");
        } else{
            acntApp.setExecUnitId(af.getExecUnitName().substring(0, af.getExecUnitName().indexOf("---")));
            
        }
        acntApp.setCostAttachBd(af.getCostAttachBd());
        acntApp.setBizSource(af.getBizSource());
        acntApp.setProductName(af.getProductName()); 
        acntApp.setAcntPlannedStart(comDate.toDate(af.getAcntPlannedStart()));    
        acntApp.setAcntPlannedFinish(comDate.toDate(af.getAcntPlannedFinish()));    
        acntApp.setAcntActualStart(comDate.toDate(af.getAcntActualStart()));     
        acntApp.setAcntActualFinish(comDate.toDate(af.getAcntActualFinish()));
        acntApp.setEstManmonth(af.getEstManmonth());
        acntApp.setActualManmonth(af.getActualManmonth());
        acntApp.setEstSize(af.getEstSize());
        acntApp.setPrimaveraAdapted(af.getPrimaveraAdapted());
        acntApp.setBillingBasis(af.getBillingBasis());
        acntApp.setBizProperty(af.getBizProperty());
        acntApp.setBillType(af.getBillType());
        acntApp.setBl(af.getBl());
        //更新人员信息
        updatePersonApp(rid, af);
        
        //更新客户资料
        updateCustomerInfoApp(rid, af);
        String customer = af.getCustomerId();
        acntApp.setCustomerId(customer);
        
        //更新技术资料
//        updateTechInfoApp(rid, af.getProjectType());
//        List techInfoList = logic.listByRidKindFromTechInfoApp(rid, Constant.PROJECT_TYPE);
          AcntTechinfoApp techInfoApp = logic.loadByRidKindFromTechInfoApp(rid, Constant.PROJECT_TYPE);
          logic.deleteFromTechInfoApp(rid, techInfoApp.getId().getKind(), techInfoApp.getId().getCode());
          techInfoApp = new AcntTechinfoApp();
          techInfoApp.getId().setAcntAppRid(rid);
          techInfoApp.getId().setKind(Constant.PROJECT_TYPE);
          techInfoApp.getId().setCode(af.getProjectType());
          logic.saveInTechInfoApp(techInfoApp);
//        if(techInfoList.size()>0){
//            AcntTechinfoApp techInfoApp =  (AcntTechinfoApp)techInfoList.get(0);
//            techInfoApp.getId().setAcntAppRid(rid);
//            techInfoApp.getId().setKind(Constant.PROJECT_TYPE);
//            techInfoApp.getId().setCode(af.getProjectType());
//            logic.updateTechInfoAppText(techInfoApp);  
//        }
//        techInfoList = logic.listByRidKindFromTechInfoApp(rid, Constant.PROJECT_TYPE);
        updateTechInfoApp(rid, af.getProductType());
        updateTechInfoApp(rid, af.getProductAttribute());
        updateTechInfoApp(rid, af.getWorkItem());
        updateTechInfoApp(rid, af.getTechnicalDomain());
        updateTechInfoApp(rid, af.getOriginalLanguage());
        updateTechInfoApp(rid, af.getTranslationLanguage());
        
        //更新技术资料中的文本数据
        updateTechInfoText(rid, af);
        //如果有附件则将附件标志位设置为"V"
        if(attachPath!=null&&!"".equals(attachPath)){
            acntApp.setHasAttachment("V");
        } else {
            acntApp.setHasAttachment("");
        }
        logic.update(acntApp);
        
//        request.setAttribute("rid",acntApp.getRid());
        
        //发送邮件给BD主管，业务经理和专案经理  
        //并且给所选择的人发送邮件
        if(isMail){
        HashMap attachMap = new HashMap();
        if(attachPath!=null&&!attachPath.equals("")){
            File attach = new File(attachPath);
            attachMap.put(attach.getName(), attach);
        } 
        sendMail(acntApp, af, comDate.dateToString(acntApp.getApplicationDate(), "yyyy-MM-dd"), 
                attachMap, otherPersonId, otherPersonDomain);
        request.getSession().removeAttribute("attachmentPath");
        request.getSession().removeAttribute("otherPersonId");
        request.getSession().removeAttribute("otherPersonDomain");
        }
        
    }
    /**
     * 获取不重复的邮件接收者姓名
     * @param otherPerson
     * @param otherPersonId
     * @param af
     * @return
     */
    private String getMailReceiver(String[] otherPerson, String[] otherPersonId, AfProjectAcntApp af, AcntApp acntApp) {
        Map personMap = new HashMap();
        if(Constant.PROJECTCHANGEAPP.equals(acntApp.getApplicationType())){
            IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
            Acnt acnt = acntLogic.loadByAcntId(af.getAcntId(), "1");
            if(!acnt.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_FINANCE)){
                personMap.put(af.getPMId(),af.getPMName());
                personMap.put(af.getBDId(),af.getBDMName());
                personMap.put(af.getLeaderId(),af.getLeaderName());
            }
        } else {
            if(!af.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_FINANCE)){
                personMap.put(af.getPMId(),af.getPMName());
                personMap.put(af.getBDId(),af.getBDMName());
                personMap.put(af.getLeaderId(),af.getLeaderName());
            }
        }
        String mailReceiver = "";
        if(otherPersonId!=null&&otherPersonId.length>0){
            int len = otherPersonId.length;
            for(int i = 0;i<len;i++){
                if(!personMap.containsKey(otherPersonId[i])){
                    personMap.put(otherPersonId[i],otherPerson[i]);
                }
            }
        } 
        List personList = new ArrayList(personMap.values());
        personMap.clear();
        int size = personList.size();
        for(int i = 0;i<size;i++){
            if(i==0){
                mailReceiver = (String)personList.get(0);
            } else {
                mailReceiver = mailReceiver+","+(String)personList.get(i);
            }
        }
        return mailReceiver;
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
     * 发送邮件给BD主管，业务经理和专案经理
     * @param acntApp
     * @param af
     * @param date
     * @param attachMap
     */
    private void sendMail(AcntApp acntApp, AfProjectAcntApp af, 
            String date, HashMap attachMap,String[] otherPersonId,String[] otherPersonDomain){
      if(!acntApp.getApplicationType().equals(Constant.PROJECTCHANGEAPP)){
          ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
          Site site = siteLogic.loadByCode(af.getExecSite());
          if(site!=null){
          af.setExecSite(site.getSiteName());
          }
          IBdService bdLogic = (IBdService)this.getBean("BdCodeLogic");
          Bd bd = bdLogic.loadByBdCode(af.getCostAttachBd());
          if(bd!=null){
          af.setCostAttachBd(bd.getBdName());
          }
          bd = bdLogic.loadByBdCode(af.getBizSource());
          if(bd!=null){
          af.setBizSource(bd.getBdName());
          }
          bd = bdLogic.loadByBdCode(acntApp.getAchieveBelong());
          if(bd!=null){
          af.setAchieveBelong(bd.getBdName());
          }
          if("-1".equals(af.getExecUnitName())){
              af.setExecUnitName("");
          }
          IParameterService parameterLogic = (IParameterService)this.getBean("ParameterLogic");
          Parameter parameter = parameterLogic.loadByKindCode(Constant.PROJEC_TYPE, af.getAcntAttribute());
          if(parameter!=null){
          af.setAcntAttribute(parameter.getName());
          }
            AfProjectAcntApp contend = new AfProjectAcntApp();
            DtoUtil.copyBeanToBean(contend,af);//拷贝内容到新的对象保证邮件内容不发生错误
            if("1".equals(af.getPrimaveraAdapted())) {
          	  contend.setPrimaveraAdapted("Y");
            } else {
          	  contend.setPrimaveraAdapted("N");
            }
            if("1".equals(af.getBillingBasis())) {
          	  contend.setBillingBasis("Y");
            } else {
          	  contend.setBillingBasis("N");
            }
            Map mailMap = new HashMap();
            if(!af.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_FINANCE)){
                  this.prepareMailMap(mailMap,af.getPMId(), af.getPMDomain());
                  this.prepareMailMap(mailMap,af.getBDId(), af.getBDId());
                  this.prepareMailMap(mailMap,af.getLeaderId(), af.getLeaderDomain());
                
            }
            
            ISendMailService sendLogic = (ISendMailService)this.getBean("SendMailLogic");
//            sendLogic.sendMail(af.getPMId(),af.getPMDomain(), vmFile11, "Project Add Apply", contend, attachMap);
//            sendLogic.sendMail(af.getBDId(),af.getBDDomain(), vmFile11, "Project Add Apply", contend, attachMap);
//            sendLogic.sendMail(af.getLeaderId(),af.getLeaderDomain(), vmFile11, "Project Add Apply", contend, attachMap);
            //准备要发送邮件的Project Office人员
            //发送邮件给维护了接收通知邮件的人员
//            String[] roleIds = {DtoRole.ROLE_HAP};
//            IRoleService roleLogic =(IRoleService)this.getBean("RoleService");
//            List projectOfficeList = roleLogic.listLoginIdUnderRole(roleIds);
//            for(int i=0;i<projectOfficeList.size();i++){
//                DtoUserBase dtoUser = (DtoUserBase)projectOfficeList.get(i);
//                this.prepareMailMap(mailMap,dtoUser.getUserLoginId(), dtoUser.getDomain());
////                sendLogic.sendMail(dtoUser.getUserLoginId(),dtoUser.getDomain(), vmFile11, "Project Add Apply", contend, attachMap);
//            }
            IMailPrivilegeService mailPriLogic = (IMailPrivilegeService) this.getBean("MailPrivilegeLogic");
            List mailPList = mailPriLogic.listToNotice(Constant.PROJECTADDAPP, contend.getExecSite());
            MailPrivilege mailP = null;
            for(int i = 0; i<mailPList.size();i++) {
          	  mailP = (MailPrivilege) mailPList.get(i);
          	  this.prepareMailMap(mailMap, mailP.getLoginId(), mailP.getDomain());
            }
            //准备所选的邮件接收人员
            if(otherPersonId!=null&&otherPersonDomain!=null){
                int idLength = otherPersonId.length;
                for(int i = 0 ; i<idLength;i++){
                    this.prepareMailMap(mailMap,otherPersonId[i], otherPersonDomain[i]);
//                    sendLogic.sendMail(otherPersonId[i],otherPersonDomain[i], vmFile11, "Project Add Apply", contend, attachMap);
                }
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
                sendLogic.sendMail(loginId,domain, vmFile11, "Project Add Apply("+contend.getRid()+")", contend, attachMap);
            }
      }else{
          VbChangeMail vb = new VbChangeMail();
          IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
          try {
              DtoUtil.copyProperties(vb, af);
          } catch (Exception e) {
              e.printStackTrace();
          }
          vb = getTechAfter(acntApp.getRid(), vb);
          
          ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
          Site site = siteLogic.loadByCode(af.getExecSite());
          if(site!=null){
          vb.setExecSite(site.getSiteName());
          }
          IBdService bdLogic = (IBdService)this.getBean("BdCodeLogic");
          Bd bd = bdLogic.loadByBdCode(af.getCostAttachBd());
          if(bd!=null){
          vb.setCostAttachBd(bd.getBdName());
          }
          Acnt execUnit = acntLogic.loadByAcntId(acntApp.getExecUnitId(), "0");
          if(execUnit != null) {
          	vb.setExecUnit(execUnit.getAcntId()+"--"+execUnit.getAcntName());
          }
          bd = bdLogic.loadByBdCode(af.getBizSource());
          if(bd!=null){
          vb.setBizSource(bd.getBdName());
          }
          bd = bdLogic.loadByBdCode(af.getAchieveBelong());
          if(bd!=null){
          vb.setAchieveBelong(bd.getBdName());
          }
          IParameterService parameterLogic = (IParameterService)this.getBean("ParameterLogic");
          Parameter parameter = parameterLogic.loadByKindCode(Constant.PROJEC_TYPE, af.getAcntAttribute());
          if(parameter!=null){
          vb.setAcntAttribute(parameter.getName());
          }
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
          
          
          Acnt acnt = acntLogic.loadByAcntId(acntApp.getAcntId(), "1");
          vb.setAcntId(acnt.getAcntId());
          vb.setApplicationDate(date);
          vb.setAcntFullNameBefore(acnt.getAcntFullName());
         
          parameter = parameterLogic.loadByKindCode(Constant.PROJEC_TYPE, acnt.getAcntAttribute());
          if(parameter!=null){
          vb.setAcntAttributeBefore(parameter.getName());
          }
          site = siteLogic.loadByCode(acnt.getExecSite());
          if(site!=null){
          vb.setExecSiteBefore(site.getSiteName());
          }    
          bd = bdLogic.loadByBdCode(acnt.getCostAttachBd());
          if(bd!=null){
          vb.setCostAttachBdBefore(bd.getBdName());
          }
          execUnit = acntLogic.loadByAcntId(acnt.getExecUnitId(), "0");
          if(execUnit != null) {
          	vb.setExecUnitBefore(execUnit.getAcntId()+"--"+execUnit.getAcntName());
          }
          bd = bdLogic.loadByBdCode(acnt.getBizSource());
          if(bd!=null){
          vb.setBizSourceBefore(bd.getBdName());
          }
          bd = bdLogic.loadByBdCode(acnt.getAchieveBelong());
          if(bd!=null){
          vb.setAchieveBelongBefore(bd.getBdName());
          }
          vb.setAcntBriefBefore(acnt.getAcntBrief());
          vb.setProductNameBefore(acnt.getProductName());
          vb.setAcntPlannedStartBefore(comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd"));
          vb.setAcntPlannedFinishBefore(comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd"));
          vb.setAcntActualStartBefore(comDate.dateToString(acnt.getAcntActualStart(), "yyyy-MM-dd"));
          vb.setAcntActualFinishBefore(comDate.dateToString(acnt.getAcntActualFinish(), "yyyy-MM-dd"));
          vb.setEstManmonthBefore(acnt.getEstManmonth());
          vb.setActualManmonthBefore(acnt.getActualManmonth());
          vb.setEstSizeBefore(acnt.getEstSize());
          vb.setOtherDescBefore(acnt.getOtherDesc());
          if("1".equals(acnt.getPrimaveraAdapted())) {
          	vb.setPrimaveraAdaptedBefore("Y");
          } else {
          	vb.setPrimaveraAdaptedBefore("N");
          }
          if("1".equals(acnt.getBillingBasis())) {
          	vb.setBillingBasisBefore("Y");
          } else {
          	vb.setBillingBasisBefore("N");
          }
          vb.setBizPropertyBefore(acnt.getBizProperty());
          vb.setBillTypeBefore(acnt.getBillType());
          vb.setBlBefore(acnt.getBl());
          
          //人员信息
          vb = personMail(acnt.getRid(), vb);
          
          //技术信息
          vb = techMail(acnt.getRid(), vb);
          
          //客户信息
          vb = customerMail(acnt.getRid(), vb);
          vb.setCustomerIdBefore(acnt.getCustomerId());
          VbChangeMail contend = new VbChangeMail();
          DtoUtil.copyBeanToBean(contend,vb);//拷贝内容到新的对象保证邮件内容不发生错误
          Map mailMap = new HashMap();
          if(!acnt.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_FINANCE)){
                  this.prepareMailMap(mailMap,af.getPMId(), af.getPMDomain());
                  this.prepareMailMap(mailMap,af.getBDId(), af.getBDId());
                  this.prepareMailMap(mailMap,af.getLeaderId(), af.getLeaderDomain());
          } 
          //发送邮件给维护了权限的人员
          IMailPrivilegeService mailPriLogic = (IMailPrivilegeService) this.getBean("MailPrivilegeLogic");
          List mailPList = mailPriLogic.listToNotice(Constant.PROJECTCHANGEAPP, vb.getExecSite());
          MailPrivilege mailP = null;
          for(int i = 0; i<mailPList.size();i++) {
        	  mailP = (MailPrivilege) mailPList.get(i);
        	  this.prepareMailMap(mailMap, mailP.getLoginId(), mailP.getDomain());
          }
          ISendMailService sendLogic = (ISendMailService)this.getBean("SendMailLogic");
//          sendLogic.sendMail(af.getPMId(),af.getPMDomain(), vmFile12, "Project Change Apply", contend);
//          sendLogic.sendMail(af.getBDId(),af.getBDDomain(), vmFile12, "Project Change Apply", contend);
//          sendLogic.sendMail(af.getLeaderId(),af.getLeaderDomain(), vmFile12, "Project Change Apply", contend);
          List mailList = new ArrayList(mailMap.values());
          int size = mailList.size();
          String temp = "";
          String loginId = "";
          String domain = "";
          for(int i = 0;i<size;i++){
              temp = (String)mailList.get(i);
              loginId = temp.substring(0,temp.indexOf(":"));
              domain= temp.substring(temp.indexOf(":")+1);
              sendLogic.sendMail(loginId,domain, vmFile12, "Project Change Apply("+contend.getAcntId()+")", contend);
          }
      }
    }
    /**
     * 生成变更后的技术资料邮件内容
     * @param rid
     * @param vb
     * @return
     */
    private VbChangeMail getTechAfter(Long rid, VbChangeMail vb){
        String projectType = getTechInfo(rid, Constant.PROJECT_TYPE, "after");
        vb.setProjectType(projectType);
        
        String productType = getTechInfo(rid, Constant.PRODUCT_TYPE, "after");
        vb.setProductType(productType);
        
        String productAttribute = getTechInfo(rid, Constant.PRODUCT_ATTRIBUTE, "after");
        vb.setProductAttribute(productAttribute);
        
        String workItem = getTechInfo(rid, Constant.WORK_ITEM, "after");
        vb.setWorkItem(workItem);
        
        String technicalDomain = getTechInfo(rid, Constant.TECHNICAL_DOMAIN, "after");
        vb.setTechnicalDomain(technicalDomain);
        
        String originalLanguage = getTechInfo(rid, Constant.ORIGINAL_LANGUAGE, "after");
        vb.setOriginalLanguage(originalLanguage);
        
        String translationLanguage = getTechInfo(rid, Constant.TRANSLATION_LANGUANGE, "after");
        vb.setTranslationLanguage(translationLanguage);
        
     
        return vb;
    }
    /**
     * 生成变更前的客户资料邮件内容
     * @param rid
     * @param vb
     * @return
     */
    private VbChangeMail customerMail(Long rid, VbChangeMail vb){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        AcntCustContactor acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT);
        vb.setContractBefore(acntCustContactor.getName());
        vb.setContractTelBefore(acntCustContactor.getTelephone());
        vb.setContractEmailBefore(acntCustContactor.getEmail());
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE);
        vb.setExecutiveBefore(acntCustContactor.getName());
        vb.setExecutiveTelBefore(acntCustContactor.getTelephone());
        vb.setExecutiveEmailBefore(acntCustContactor.getEmail());
        
        acntCustContactor = acntLogic.loadByRidTypeFromAcntCustContactor(rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL);
        vb.setFinancialBefore(acntCustContactor.getName());
        vb.setFinancialTelBefore(acntCustContactor.getTelephone());
        vb.setFinancialEmailBefore(acntCustContactor.getEmail());
        
        return vb;
    }
    /**
     * 生成变更前的技术资料邮件内容
     * @param rid
     * @param vb
     * @return
     */
    private VbChangeMail techMail(Long rid, VbChangeMail vb){

        String projectType = getTechInfo(rid, Constant.PROJECT_TYPE, "before");
        vb.setProjectTypeBefore(projectType);
        
        String productType = getTechInfo(rid, Constant.PRODUCT_TYPE, "before");
        vb.setProductTypeBefore(productType);
        
        String productAttribute = getTechInfo(rid, Constant.PRODUCT_ATTRIBUTE, "before");
        vb.setProductAttributeBefore(productAttribute);
        
        String workItem = getTechInfo(rid, Constant.WORK_ITEM, "before");
        vb.setWorkItemBefore(workItem);
        
        String technicalDomain = getTechInfo(rid, Constant.TECHNICAL_DOMAIN, "before");
        vb.setTechnicalDomainBefore(technicalDomain);
        
        String originalLanguage = getTechInfo(rid, Constant.ORIGINAL_LANGUAGE, "before");
        vb.setOriginalLanguageBefore(originalLanguage);
        
        String translationLanguage = getTechInfo(rid, Constant.TRANSLATION_LANGUANGE, "before");
        vb.setTranslationLanguageBefore(translationLanguage);
        
        vb = getTechInfoText(rid, vb);
       
        
        
        return vb;
    }
    /**
     * 生成变更前的技术资料文本信息邮件内容
     * @param rid
     * @param vb
     * @return
     */
    private VbChangeMail getTechInfoText(Long rid, VbChangeMail vb){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        AcntTechinfo techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.JOBSYSTEM);
        vb.setDevelopJobSystemBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.DATABASE);
        vb.setDevelopDataBaseBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.TOOL);
        vb.setDevelopToolBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.NETWORK);
        vb.setDevelopNetWorkBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.PROGRAMLANGUAGE);
        vb.setDevelopProgramLanguageBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.DEVELOPENVIRONMENT, Constant.OTHERS);
        vb.setDevelopOthersBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.JOBSYSTEM);
        vb.setTypeJobSystemBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.DATABASE);
        vb.setTypeDataBaseBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.TOOL);
        vb.setTypeToolBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.NETWORK);
        vb.setTypeNetWorkBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.PROGRAMLANGUAGE);
        vb.setTypeProgramLanguageBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.TRNSLATEPUBLISHTYPE, Constant.OTHERS);
        vb.setTypeOthersBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.HARDREQ, Constant.HARDREQ);
        vb.setHardReqBefore(techInfo.getDescription());
        
        techInfo = acntLogic.loadByRidKindCodeFromTechInfo(rid, Constant.SOFTREQ, Constant.SOFTREQ);
        vb.setSoftReqBefore(techInfo.getDescription());
        
        
        
        return vb;
    }
    /**
     * 生成变更前后的技术资料邮件内容的具体实现
     * @param rid
     * @param kind
     * @param type
     * @return
     */
    private String getTechInfo(Long rid, String kind, String type){
        String valueAll = null;
       
     if(type.equals("before")){   
       IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
       List techInfoList = acntLogic.listByRidKindFromTechInfo(rid, kind);
       AcntTechinfo techInfo = new AcntTechinfo();
       ParameterId parameterId = new ParameterId();
       parameterId.setKind(kind);
       Parameter parameter = new Parameter();
     
       IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
       for(int i=0;i<techInfoList.size();i++) {
           techInfo = (AcntTechinfo) techInfoList.get(i);
           parameterId.setCode(techInfo.getId().getCode());
           parameter = parameterLogic.loadByKey(parameterId);
           if(parameter!=null){
               valueAll = valueAll + "," + parameter.getName();
           }
       }
       if(valueAll!=null){
           valueAll = valueAll.substring(5);
       }else{
           valueAll = "";
       }
     } else {
         IAccountApplicationService logic = (IAccountApplicationService)this.getBean("AccountApplicationLogic");
         List techInfoAppList = logic.listByRidKindFromTechInfoApp(rid, kind);
         AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
         ParameterId parameterId = new ParameterId();
         parameterId.setKind(kind);
         Parameter parameter = new Parameter();
       
         IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
         for(int i=0;i<techInfoAppList.size();i++) {
             techInfoApp = (AcntTechinfoApp) techInfoAppList.get(i);
             parameterId.setCode(techInfoApp.getId().getCode());
             parameter = parameterLogic.loadByKey(parameterId);
             if(parameter!=null){
                 valueAll = valueAll + "," + parameter.getName();
             }
         }
         if(valueAll!=null){
             valueAll = valueAll.substring(5);
         }else{
             valueAll = "";
         }
     }
        
        return valueAll;
    }
    /**
     * 生成变更前的人员资料邮件内容
     * @param rid
     * @param vb
     * @return
     */
    private VbChangeMail personMail(Long rid, VbChangeMail vb){
        IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
        AcntPerson person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_PM);
        vb.setPMNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_TC_SIGNER);
        vb.setTCSNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_BD_MANAGER);
        vb.setBDMNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_LEADER);
        vb.setLeaderNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_SALES);
        vb.setSalesNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        vb.setCustServiceManagerNameBefore(person.getName());
        
        person = acntLogic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        vb.setEngageManagerNameBefore(person.getName());
        
        
        return vb;
    }
    /**
     * 更新客户资料
     * @param Rid 当前申请单号
     * @param af 当前Form
     */
    private void updateCustomerInfoApp(Long Rid ,AfProjectAcntApp af) {
        
        updateCustomertInfoAppDetail(Rid, IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT, af.getContract(), af.getContractTel(), af.getContractEmail());
        updateCustomertInfoAppDetail(Rid, IDtoAccount.CUSTOMER_CONTACTOR_EXE, af.getExecutive(), af.getExecutiveTel(), af.getExecutiveEmail());
        updateCustomertInfoAppDetail(Rid, IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL, af.getFinancial(), af.getFinancialTel(), af.getFinancialEmail());
       
    }
    /**
     * 更新对应的客户相关信息
     * @param Rid 当前申请单号
     * @param Type 联系人类型
     * @param name 联系人姓名
     * @param tel 联系人电话
     * @param email 联系人Email
     */
    private void updateCustomertInfoAppDetail(Long Rid, String Type, String name, String tel, String email) {
        
        AcntCustContactorApp acntCustContactorApp = new AcntCustContactorApp();
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        acntCustContactorApp = logic.loadByRidTypeFromAcntCustContactorApp(Rid, Type);
        if(acntCustContactorApp!=null) {
            if(name!=null) {
                acntCustContactorApp.setName(name);
            }
            if(tel!=null) {
                acntCustContactorApp.setTelephone(tel);
            }
            if(email!=null) {
                acntCustContactorApp.setEmail(email);
            }
            logic.updateCustomerInfoApp(acntCustContactorApp);
        } else {
            AcntCustContactorApp newAcntCustContactorApp = new AcntCustContactorApp();
            newAcntCustContactorApp.setAcntAppRid(Rid);
            newAcntCustContactorApp.setContactorType(Type);
            newAcntCustContactorApp.setName(name);
            newAcntCustContactorApp.setTelephone(tel);
            newAcntCustContactorApp.setEmail(email);
            logic.saveCustomerInfoApp(newAcntCustContactorApp);
            
        }
    }
    /**
     * 更新技术资料表
     * @param Rid 当前的申请单号
     * @param info 前台传入的已选项目字符串
     */
    private void updateTechInfoApp(Long Rid, String info) {
        List selectedCodeList = new ArrayList();    
        String []infoArr=info.split(",");
        String Kind =infoArr[0];
        for(int i=1;i<infoArr.length;i++) {
            selectedCodeList.add(infoArr[i]);
        }
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        List codeList = logic.listByRidKindFromTechInfoApp(Rid, Kind);
        compareAndUpdate(Rid, Kind, codeList, selectedCodeList);
    }
    /**
     * 比较数据库中现有选项与当前已选项，进行删除和新增数据操作
     * @param Rid 当前申请单号
     * @param Kind 插入类型
     * @param codeList 数据库中已有选项
     * @param selectedCodeList 当前已选项
     */
    private void compareAndUpdate(Long Rid, String Kind, List codeList, List selectedCodeList) {
        String code = null;
        
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        for(int i=0;i<codeList.size();i++) {
            AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
            techInfoApp = (AcntTechinfoApp) codeList.get(i);
            logic.deleteFromTechInfoApp(Rid, techInfoApp.getId().getKind(), techInfoApp.getId().getCode());
        }
        for(int j=0;j<selectedCodeList.size();j++) {
            AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
            code = (String) selectedCodeList.get(j);
            techInfoApp.getId().setAcntAppRid(Rid);
            techInfoApp.getId().setKind(Kind);
            techInfoApp.getId().setCode(code);
            logic.saveInTechInfoApp(techInfoApp);  
        }
        
    }
    /**
     * 更新技术资料中的文本信息
     * @param Rid 当前申请单号
     * @param af 当前Form
     */
    private void updateTechInfoText(Long Rid, AfProjectAcntApp af) {
        //更新开发环境
        updateAllType(Rid, Constant.DEVELOPENVIRONMENT, Constant.JOBSYSTEM, af.getDevelopJobSystem());
        updateAllType(Rid, Constant.DEVELOPENVIRONMENT, Constant.DATABASE, af.getDevelopDataBase());
        updateAllType(Rid, Constant.DEVELOPENVIRONMENT, Constant.TOOL, af.getDevelopTool());
        updateAllType(Rid, Constant.DEVELOPENVIRONMENT, Constant.NETWORK, af.getDevelopNetWork());
        updateAllType(Rid, Constant.DEVELOPENVIRONMENT, Constant.PROGRAMLANGUAGE, af.getDevelopProgramLanguage());
        updateAllType(Rid, Constant.DEVELOPENVIRONMENT, Constant.OTHERS, af.getDevelopOthers());
        
        //更新翻译/排版/打字
        updateAllType(Rid, Constant.TRNSLATEPUBLISHTYPE, Constant.JOBSYSTEM, af.getTypeJobSystem());
        updateAllType(Rid, Constant.TRNSLATEPUBLISHTYPE, Constant.DATABASE, af.getTypeDataBase());
        updateAllType(Rid, Constant.TRNSLATEPUBLISHTYPE, Constant.TOOL, af.getTypeTool());
        updateAllType(Rid, Constant.TRNSLATEPUBLISHTYPE, Constant.NETWORK, af.getTypeNetWork());
        updateAllType(Rid, Constant.TRNSLATEPUBLISHTYPE, Constant.PROGRAMLANGUAGE, af.getTypeProgramLanguage());
        updateAllType(Rid, Constant.TRNSLATEPUBLISHTYPE, Constant.OTHERS, af.getTypeOthers());
        
        //更新硬件/软件需求
        updateAllType(Rid, Constant.HARDREQ, Constant.HARDREQ, af.getHardReq());
        updateAllType(Rid, Constant.SOFTREQ, Constant.SOFTREQ, af.getSoftReq());
                 
    }
    /**
     * 更新各个类型的技术资料文本信息
     * @param Rid 当前申请单号
     * @param Kind 技术资料类型
     * @param Code 技术资料代码
     * @param des 技术资料描述信息
     */
    private void updateAllType(Long Rid, String Kind, String Code, String des) {
         IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        
        AcntTechinfoApp techInfoApp = new AcntTechinfoApp();
        techInfoApp = logic.loadByRidKindCodeFromTechInfoApp(Rid, Kind, Code);
        if(techInfoApp!=null) {
           if(des!=null) {
                techInfoApp.setDescription(des);
                logic.updateTechInfoAppText(techInfoApp);
           }
        } else {
            AcntTechinfoApp newTechInfoApp = new AcntTechinfoApp();
            newTechInfoApp.getId().setAcntAppRid(Rid);
            newTechInfoApp.getId().setKind(Kind);
            newTechInfoApp.getId().setCode(Code);
            newTechInfoApp.setDescription(des);
            logic.saveInTechInfoApp(newTechInfoApp);
        }
        
    }
    /**
     * 更新人员信息
     * @param rid 当前申请单号
     * @param af 当前Form
     */
    private void updatePersonApp(Long rid, AfProjectAcntApp af) {
        IAccountApplicationService logic = (IAccountApplicationService)this.getBean("AccountApplicationLogic");
        logic.deleteAllRelatedFromPersonApp(rid);
        AcntPersonApp personApp = new AcntPersonApp();
        
        personApp.setAcntAppRid(rid);
        personApp.setPersonType(IDtoAccount.USER_TYPE_PM);
        personApp.setName(af.getPMName());
        personApp.setLoginId(af.getPMId());
        personApp.setDomain(af.getPMDomain());
        logic.saveAcntPersonApp(personApp);
        
        personApp = new AcntPersonApp();
        personApp.setAcntAppRid(rid);
        personApp.setPersonType(IDtoAccount.USER_TYPE_TC_SIGNER);
        personApp.setName(af.getTCSName());
        personApp.setLoginId(af.getTCSId());
        personApp.setDomain(af.getTCSDomain());
        logic.saveAcntPersonApp(personApp);
        
        personApp = new AcntPersonApp();
        personApp.setAcntAppRid(rid);
        personApp.setPersonType(IDtoAccount.USER_TYPE_BD_MANAGER);
        personApp.setName(af.getBDMName());
        personApp.setLoginId(af.getBDId());
        personApp.setDomain(af.getBDDomain());
        logic.saveAcntPersonApp(personApp);
        
        personApp = new AcntPersonApp();
        personApp.setAcntAppRid(rid);
        personApp.setPersonType(IDtoAccount.USER_TYPE_LEADER);
        personApp.setName(af.getLeaderName());
        personApp.setLoginId(af.getLeaderId());
        personApp.setDomain(af.getLeaderDomain());
        logic.saveAcntPersonApp(personApp);
        
        personApp = new AcntPersonApp();
        personApp.setAcntAppRid(rid);
        personApp.setPersonType(IDtoAccount.USER_TYPE_SALES);
        personApp.setName(af.getSalesName());
        personApp.setLoginId(af.getSalesId());
        personApp.setDomain(af.getSalesDomain());
        logic.saveAcntPersonApp(personApp);
        
        personApp = new AcntPersonApp();
        personApp.setAcntAppRid(rid);
        personApp.setPersonType(IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER);
        personApp.setName(af.getCustServiceManagerName());
        personApp.setLoginId(af.getCustServiceManagerId());
        personApp.setDomain(af.getCustServiceManagerDomain());
        logic.saveAcntPersonApp(personApp);
        
        personApp = new AcntPersonApp();
        personApp.setAcntAppRid(rid);
        personApp.setPersonType(IDtoAccount.USER_TYPE_ENGAGE_MANAGER);
        personApp.setName(af.getEngageManagerName());
        personApp.setLoginId(af.getEngageManagerId());
        personApp.setDomain(af.getEngageManagerDomain());
        logic.saveAcntPersonApp(personApp);
        
    }
}
