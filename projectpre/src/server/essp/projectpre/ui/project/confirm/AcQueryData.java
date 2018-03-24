package server.essp.projectpre.ui.project.confirm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.FastHashMap;
import com.wits.util.comDate;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.customer.ICustomerService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

public class AcQueryData extends AbstractESSPAction {    
    /**
     *根据查询条件查询出符合条件的结案记录
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {      
    	Map cache = new FastHashMap();
    	IAccountApplicationService acntAppLogic = (IAccountApplicationService) 
    	this.getBean("AccountApplicationLogic");
    	ICustomerService cutomerLogic = (ICustomerService) this.getBean("CustomerLogic");
    	
    	String applicationType = server.essp.projectpre.service.constant.Constant.PROJECTCONFIRMAPP;
    	AfProjectConfirm af = (AfProjectConfirm)this.getForm();
    	DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userLoginId = user.getUserLoginId();
        IAccountService logic = (IAccountService) this.getBean("AccountLogic");
        List acntList = logic.listCloseData(af,userLoginId);      
        List typeList =acntAppLogic.loadByType(applicationType);  
        for(int k=0;k<typeList.size();k++){
        	AcntApp app=(AcntApp)typeList.get(k);
        	cache.put(app.getAcntId(),app);
        }
        List viewBeanList = new ArrayList();        
         for(int i=0;i<acntList.size();i++) {
        	VbProjectConfirm vb = new VbProjectConfirm();
            Acnt acntLast = (Acnt)acntList.get(i);
            vb.setAcntId(acntLast.getAcntId());
            vb.setAcntName(acntLast.getAcntName());
            if(acntLast.getAcntPlannedStart()!=null){
            String acntStart=comDate.dateToString(acntLast.getAcntPlannedStart(),"yyyy-MM-dd");
            vb.setAcntPlannedStart(acntStart);
            }
            if(acntLast.getAcntPlannedFinish()!=null){
            String acntFinish=comDate.dateToString(acntLast.getAcntPlannedFinish(),"yyyy-MM-dd");
            vb.setAcntPlannedFinish(acntFinish);
            }
             AcntApp acntApp=(AcntApp)cache.get(acntLast.getAcntId());
            if(acntApp!=null) {
                vb.setAcntStatus(acntApp.getApplicationStatus());
            } else if(Constant.NORMAL.equals(acntLast.getAcntStatus())){
                vb.setAcntStatus(server.essp.projectpre.service.constant.Constant.UNAPPLY);
               
            }
            //如果狀態碼不為空時過濾掉與選中的狀態不一致的紀錄
            if(!"".equals(af.getAcntStatus())){
             if(!vb.getAcntStatus().equals(af.getAcntStatus())){
            	continue;
             }
            }
            
            vb.setAcntType(acntLast.getRelPrjType());
            List list = cutomerLogic.queryCustNameById(acntLast.getCustomerId());
            if(list!=null&&list.size()>0){
            Customer customer = (Customer)list.get(0);
            vb.setCustomerShort(customer.getShort_());
            }
            AcntPerson acntPerson = new AcntPerson();          
            acntPerson=logic.loadByRidFromPerson(acntLast.getRid(),
                    IDtoAccount.USER_TYPE_APPLICANT);
            vb.setApplicant(acntPerson.getName()); 
            viewBeanList.add(vb);
          }
            request.setAttribute(server.framework.common.Constant.
                    VIEW_BEAN_KEY, viewBeanList);  
    }
    }           
          
