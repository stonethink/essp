package server.essp.projectpre.ui.project.check;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.*;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.mailprivilege.IMailPrivilegeService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;

import com.wits.util.comDate;

/**
 * 列出当前登陆者可以复核的申请的Action
 * @author Stephen.zheng
 *
 *
 */
public class AcListProjectAppCheck extends AbstractESSPAction {
	
	/**
	 * 列出当前登陆者可以复核的申请
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                                   TransactionData data) throws BusinessException {
           IMailPrivilegeService mailLogic = (IMailPrivilegeService) this.getBean("MailPrivilegeLogic");
           //获取审批权限
           MailPrivilege mailP = mailLogic.loadByLoginid(this.getUser().getUserLoginId());
           if(mailP == null) {//如果空则没有权限
        	   request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY,new ArrayList());
        	   return;
           }
           Map mailMap = new HashMap();
           if(mailP.getAddAudit()) {
        	   mailMap.put(Constant.PROJECTADDAPP, Constant.PROJECTADDAPP);
           } 
           if(mailP.getChangeAudit()){
        	   mailMap.put(Constant.PROJECTCHANGEAPP, Constant.PROJECTCHANGEAPP);
           } 
           if(mailP.getCloseAudit()) {
        	   mailMap.put(Constant.PROJECTCONFIRMAPP, Constant.PROJECTCONFIRMAPP);
           }
           String dataScope = mailP.getDataScope();
           IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
           List acntAppList = logic.listAllProjectApp("1");
           List viewList = new ArrayList();
           
           String IDFORMATER = "00000000";
           Site site = null;
           String siteName = "";
           for(int i = 0;i<acntAppList.size();i++) {
        	   siteName = "";//使用前清空上一記錄的內容
               VbProjectCheck viewBean = new VbProjectCheck();
               AcntApp acntApp = (AcntApp) acntAppList.get(i);
               ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
               site = siteLogic.loadByCode(acntApp.getExecSite());
               if(site != null) {
               	siteName = site.getSiteName();
               }
               if(!mailMap.containsKey(acntApp.getApplicationType())
                 || !dataScope.contains(siteName)){
            	   continue;
               }
               DecimalFormat df = new DecimalFormat(IDFORMATER);
               String newRid = df.format(acntApp.getRid(), new StringBuffer(),
                                         new FieldPosition(0)).toString();
               viewBean.setRid(newRid);
               viewBean.setApplicantName(acntApp.getApplicantName());
               viewBean.setAcntName(acntApp.getAcntName());
               viewBean.setApplicationDate(comDate.dateToString(acntApp.getApplicationDate(), "yyyy-MM-dd"));
               if(acntApp.getApplicationType().equals(Constant.PROJECTADDAPP)) {
                   if(acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_MASTER)) {
                       viewBean.setAcntType(Constant.MASTERPROJECTADDCONFIRM);
                       
                   } else if(acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_RELATED)) {
                        viewBean.setAcntType(Constant.RELATEDPROJECTADDCONFIRM);
                       
                   } else if(acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_SUB)) {
                       
                        viewBean.setAcntType(Constant.SUBPROJECTADDCONFIRM);
                   } else if(acntApp.getRelPrjType().equals(IDtoAccount.PROJECT_RELATION_FINANCE)){
                       viewBean.setAcntType(Constant.FINANCEPROJECTADDCONFIRM);
                   }
                   
               } else if(acntApp.getApplicationType().equals(Constant.PROJECTCHANGEAPP)) {
                   
                       viewBean.setAcntType(Constant.PROJECTCHANGECONFIRM);
                       viewBean.setAcntId(acntApp.getAcntId());
               } else if(acntApp.getApplicationType().equals(Constant.PROJECTCONFIRMAPP)) {
                       viewBean.setAcntType(Constant.PROJECTCLOSECONFIRM);
                       viewBean.setAcntId(acntApp.getAcntId());
                   
               } 
//               else if(acntApp.getApplicationType().equals(Constant.CONTRACTCHANGEAPP)) {
//                       viewBean.setAcntType(Constant.CONTRACTPROJECTCHANGECONFIRM);
//                       viewBean.setAcntId(acntApp.getContractAcntId());
//                       viewBean.setAcntName(acntApp.getContractAcntName());
//                   
//               }
               
               
               viewList.add(viewBean);
           }
           
           request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY,viewList);
    }

}
