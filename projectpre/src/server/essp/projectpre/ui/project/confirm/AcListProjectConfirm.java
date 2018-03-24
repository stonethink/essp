package server.essp.projectpre.ui.project.confirm;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.comDate;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.customer.ICustomerService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

/**
 * 列出当前登陆者可以结案的所有专案的Action
 * @author Stephen.zheng
 *
 * 
 */
public class AcListProjectConfirm extends AbstractESSPAction{
  
    private String applicationType = server.essp.projectpre.service.constant.Constant.PROJECTCONFIRMAPP;
    /**
     * 列出当前登陆者可以结案的所有专案
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                              TransactionData data) throws BusinessException {
         
           DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
           String userLoginId = user.getUserLoginId();
           IAccountService logic = (IAccountService) this.getBean("AccountLogic");
           IAccountApplicationService acntAppLogic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
          
           List acntList = logic.listAll(userLoginId);
           List acntIdList = new ArrayList();
           for(int i = 0; i<acntList.size();i++) {
               Acnt acnt = (Acnt) acntList.get(i);
               AcntApp acntApp = acntAppLogic.loadByTypeAcntId(server.essp.projectpre.service.constant.Constant.PROJECTCHANGEAPP, acnt.getAcntId());  
               if(acntApp==null) {
               acntIdList.add(acnt);
               }
           }         
           List viewBean = new ArrayList();
          
          
           for(int i=0;i<acntIdList.size();i++) {
               Acnt acnt = (Acnt) acntIdList.get(i);
               
               VbProjectConfirm vbProjectConfirm = new VbProjectConfirm();
               vbProjectConfirm.setAcntId(acnt.getAcntId());
               AcntPerson person = logic.loadByRidFromPerson(acnt.getRid(), IDtoAccount.USER_TYPE_APPLICANT);
               vbProjectConfirm.setApplicant(person.getName());
              
               vbProjectConfirm.setAcntType(acnt.getRelPrjType());
            
               
               AcntApp acntApp = acntAppLogic.loadByTypeAcntId(applicationType, acnt.getAcntId());
               if(acntApp!=null) {
                   vbProjectConfirm.setAcntStatus(acntApp.getApplicationStatus());
               } else if(acnt.getAcntStatus().equals(server.essp.projectpre.service.constant.Constant.NORMAL)){
                   vbProjectConfirm.setAcntStatus(server.essp.projectpre.service.constant.Constant.UNAPPLY);
               }
               if(acnt.getCustomerId()!=null){
               ICustomerService customerLogic = (ICustomerService) this.getBean("CustomerLogic");
               Customer customer = customerLogic.loadByCustomerId(acnt.getCustomerId());
               if(customer!=null) {
                   vbProjectConfirm.setCustomerShort(customer.getShort_());
                   }
               }
               
               vbProjectConfirm.setAcntPlannedStart(comDate.dateToString(acnt.getAcntPlannedStart(), "yyyy-MM-dd"));
               vbProjectConfirm.setAcntPlannedFinish(comDate.dateToString(acnt.getAcntPlannedFinish(), "yyyy-MM-dd"));
               viewBean.add(vbProjectConfirm);
           }
           
           request.setAttribute(Constant.VIEW_BEAN_KEY,viewBean);
        
    }

}
