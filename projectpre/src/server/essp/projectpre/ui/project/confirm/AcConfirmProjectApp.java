package server.essp.projectpre.ui.project.confirm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wits.util.comDate;

import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.*;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.log.ILogService;
import server.essp.projectpre.service.mailprivilege.IMailPrivilegeService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.sendMail.ISendMailService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;

/**
 * 提交结案申请的Action 
 * @author Stephen.zheng
 *
 * 
 */
public class AcConfirmProjectApp extends AbstractESSPAction {
    public static final String vmFile11 = "mail/template/proj/ConfirmMail.html";
    /**
     * 提交结案申请
     *   1.获取相关人员
     *   2.获取技术资料
     *   3.发送邮件给BD主管,业务经理和专案经理
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
            TransactionData data) throws BusinessException {
            DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
            String userLoginId = user.getUserLoginId();
            String userName = user.getUserName();
            String domain = user.getDomain();
            String acntId = null;
            if(request.getParameter("acntId")!=null){
                acntId = request.getParameter("acntId");
                
            }
            AfProjectConfirm af =  (AfProjectConfirm) this.getForm();
            IAccountService acntLogic = (IAccountService)this.getBean("AccountLogic");
            Acnt acnt = acntLogic.loadByAcntId(acntId, "1");
            af.setAcntId(acntId);
            //获取相关人员
            af = getPerson(acnt.getRid(), af);
            
            //获取技术资料
            af = getTechInfo(acnt.getRid(), af);
            if(acnt!=null){
                IParameterService parameterLogic = (IParameterService)this.getBean("ParameterLogic");
                Parameter parameter = parameterLogic.loadByKindCode(server.essp.projectpre.service.constant.Constant.PROJEC_TYPE, acnt.getAcntAttribute());
                if(parameter!=null){
                af.setAcntAttribute(parameter.getName());
                }
            af.setAcntName(acnt.getAcntName());
            af.setAcntFullName(acnt.getAcntFullName());
            af.setAcntBrief(acnt.getAcntBrief());
            String date = comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd")+"~"+comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd");
            af.setAcntAnticpated(date);
            }
            IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
            AcntApp acntAppNest = logic.loadByTypeAcntId(server.essp.projectpre.service.constant.Constant.PROJECTCHANGEAPP, af.getAcntId());
            if(acntAppNest!=null){
                throw new BusinessException("error.logic.closeApp.existChangeApp");
            }
            
            
            AcntApp acntApp = logic.loadByTypeAcntId(Constant.PROJECTCONFIRMAPP,af.getAcntId());
            
            if(acntApp!=null) {
                acntApp.setApplicationStatus(Constant.SUBMIT);
                logic.update(acntApp);
            } else {
                acntApp = new AcntApp();
                acntApp.setRid(Long.valueOf(logic.createApplyNo()));
                acntApp.setApplicationType(Constant.PROJECTCONFIRMAPP);
                acntApp.setAcntId(af.getAcntId());
                acntApp.setExecSite(acnt.getExecSite());//申請表中加入site以便使用權限過濾
                acntApp.setApplicationStatus(Constant.SUBMIT);
                acntApp.setAcntAttribute(af.getAcntAttribute());
                acntApp.setAcntName(af.getAcntName());
                acntApp.setApplicationDate(new Date());
                acntApp.setIsAcnt("1");
                acntApp.setApplicantId(userLoginId);
                acntApp.setApplicantName(userName);
                acntApp.setDomain(domain);
                logic.save(acntApp);
            }
            //修改实际开始，实际结束，实际人月数，其他叙述
            acnt.setAcntActualStart(comDate.toDate(af.getAcntActualStart()));
            acnt.setAcntActualFinish(comDate.toDate(af.getAcntActualFinish()));
            acnt.setActualManmonth(af.getActualManmonth());
            acnt.setOtherDesc(af.getOtherDesc());
            acntLogic.update(acnt);
            ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
            Site site = siteLogic.loadByCode(acnt.getExecSite());
            String siteName = "";
            if(site != null) {
            	siteName = site.getSiteName();
            }
            AfProjectConfirm contend = new AfProjectConfirm();
            DtoUtil.copyBeanToBean(contend,af);//拷贝内容到新的对象保证邮件内容不发生错误
            //发送邮件给BD主管,业务经理和专案经理
            Map mailMap = new HashMap();
            if(!acnt.getRelPrjType().equals(IDtoAccount.ACCOUNT_TYPE_FINANCE)){
                this.prepareMailMap(mailMap,af.getPMId(), af.getPMDomain());
                this.prepareMailMap(mailMap,af.getBDId(), af.getBDDomain());
                this.prepareMailMap(mailMap,af.getLeaderId(), af.getLeaderDomain());
            }
            //发送邮件给维护权限的人员
            IMailPrivilegeService mailPriLogic = (IMailPrivilegeService) this.getBean("MailPrivilegeLogic");
            List mailPList = mailPriLogic.listToNotice(Constant.PROJECTCONFIRMAPP, siteName);
            MailPrivilege mailP = null;
            for(int i = 0; i<mailPList.size();i++) {
          	  mailP = (MailPrivilege) mailPList.get(i);
          	  this.prepareMailMap(mailMap, mailP.getLoginId(), mailP.getDomain());
            }
            ISendMailService sendLogic = (ISendMailService)this.getBean("SendMailLogic");
//            sendLogic.sendMail(af.getBDId(), af.getBDDomain(), vmFile11, "Project Confirm Apply", contend);
//            sendLogic.sendMail(af.getPMId(), af.getPMDomain(), vmFile11, "Project Confirm Apply", contend);
//            sendLogic.sendMail(af.getLeaderId(), af.getLeaderDomain(), vmFile11, "Project Confirm Apply", contend);
            List mailList = new ArrayList(mailMap.values());
            int size = mailList.size();
            String temp = "";
            String loginId = "";
            domain = "";
            for(int i = 0;i<size;i++){
                temp = (String)mailList.get(i);
                loginId = temp.substring(0,temp.indexOf(":"));
                domain= temp.substring(temp.indexOf(":")+1);
                sendLogic.sendMail(loginId, domain, vmFile11, "Project Confirm Apply("+contend.getAcntId()+")", contend);
            }
            //向LOG表中插入一笔记录
            String mailReceiver = af.getPMName()+","+af.getBDName()+","+af.getLeader();
            ILogService logicLog=(ILogService)this.getBean("LogLogic");
            PPLog log = new PPLog();
            log.setAcntId(acntApp.getAcntId());
            log.setApplicationType(Constant.PROJECTCONFIRMAPP);
            log.setDataType(Constant.LOG_PROJECT);
            log.setMailReceiver(mailReceiver);
            log.setOperation(acntApp.getApplicationStatus());
            log.setOperationDate(new Date());
            log.setOperator(userName);
            logicLog.save(log);
            
            
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
     * 获取相关人员的信息
     * @param Rid 当前戴结案专案的Rid
     * @param af 当前的ActionForm
     * @return 含有人员信息的ActionForm
     */
    private AfProjectConfirm getPerson(Long Rid, AfProjectConfirm af) {
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        
        AcntPerson acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_APPLICANT);
        if(acntPerson!=null) {
        af.setApplicant(acntPerson.getName());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_LEADER);
        if(acntPerson!=null) {
        af.setLeader(acntPerson.getName());
        af.setLeaderId(acntPerson.getLoginId());
        af.setLeaderDomain(acntPerson.getDomain());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_PM);
        if(acntPerson!=null) {
        af.setPMName(acntPerson.getName());
        af.setPMId(acntPerson.getLoginId());
        af.setPMDomain(acntPerson.getDomain());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_TC_SIGNER);
        if(acntPerson!=null) {
        af.setTCSName(acntPerson.getName());
        }
        
        acntPerson = acntLogic.loadByRidFromPerson(Rid, IDtoAccount.USER_TYPE_BD_MANAGER);
        if(acntPerson!=null) {
        af.setBDName(acntPerson.getName());
        af.setBDId(acntPerson.getLoginId());
        af.setBDDomain(acntPerson.getDomain());
        }
        
        return af;
        
    }
    /**
     * 获取相关的技术资料
     * @param Rid 当前待结案专案的Rid
     * @param af 当前的ActionForm
     * @return 含有技术资料的ActionForm
     */
    private AfProjectConfirm getTechInfo(Long Rid, AfProjectConfirm af) {
        
        IAccountService acntLogic = (IAccountService) this.getBean("AccountLogic");
        
        List productList = acntLogic.listByRidKindFromTechInfo(Rid, server.essp.projectpre.service.constant.Constant.PRODUCT_TYPE);
        List productAttributeList = acntLogic.listByRidKindFromTechInfo(Rid, server.essp.projectpre.service.constant.Constant.PRODUCT_ATTRIBUTE);
        List workItemList = acntLogic.listByRidKindFromTechInfo(Rid, server.essp.projectpre.service.constant.Constant.WORK_ITEM);
        
        String productStr = null;
        String productAttributeStr = null;
        String workItemStr = null;
        
        ParameterId parameterId = new ParameterId();
        Parameter parameter = new Parameter();
        IParameterService parameterLogic = (IParameterService) this.getBean("ParameterLogic");
        
        for(int i=0;i<productList.size();i++) {
            AcntTechinfo techInfo = (AcntTechinfo) productList.get(i);
            parameterId.setCode(techInfo.getId().getCode());
            parameterId.setKind(techInfo.getId().getKind());
            parameter = parameterLogic.loadByKey(parameterId);
            if(parameter!=null){
             productStr = productStr +","+ parameter.getName() ;
            }
        }
        if(productStr!=null) {
        af.setProductType(productStr.substring(5));
        }
        
        for(int i=0;i<productAttributeList.size();i++) {
            AcntTechinfo techInfo = (AcntTechinfo) productAttributeList.get(i);
            parameterId.setCode(techInfo.getId().getCode());
            parameterId.setKind(techInfo.getId().getKind());
            parameter = parameterLogic.loadByKey(parameterId);
            if(parameter!=null){
             productAttributeStr = productAttributeStr +","+ parameter.getName();
            }
        }
        if(productAttributeStr!=null) {
        af.setProductAttribute(productAttributeStr.substring(5));
        }
        
        for(int i=0;i<workItemList.size();i++) {
            AcntTechinfo techInfo = (AcntTechinfo) workItemList.get(i);
            parameterId.setCode(techInfo.getId().getCode());
            parameterId.setKind(techInfo.getId().getKind());
            parameter = parameterLogic.loadByKey(parameterId);
            if(parameter!=null){
              workItemStr = workItemStr +","+ parameter.getName();
            }
        }
        if(workItemStr!=null) {
        af.setWorkItem(workItemStr.substring(5));
        }
        
        return af;
    }

}
