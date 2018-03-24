/*
 * Created on 2006-11-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.dept.query;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.ui.dept.query.AfQueryData;
import server.essp.projectpre.ui.dept.query.VbQueryList;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;

public class AcShowData extends AbstractESSPAction {    
    /**
     *根据查询条件查询出符合条件的已经提交的部门资料
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {       
        AfQueryData af = (AfQueryData)this.getForm();
        Acnt acnt =new Acnt();      
        String acntId = af.getAcntId();       
        String belongBd = af.getBelongBd();
        String acntName = af.getAcntName();
        String applicantName = af.getApplicantName();
        String BDMName = af.getBDMName();
        String TCSName = af.getTCSName();
        String deptManager = af.getDeptManager();         
        if (acntId != null && !acntId.equals("")) {
            acnt.setAcntId(acntId);  
        }
        if (acntName != null && !acntName.equals("")) {
            acnt.setAcntName(acntName);
        }
        if (belongBd != null && !belongBd.equals("")) {
            acnt.setAchieveBelong(belongBd);
        }       
        IAccountService logic = (IAccountService) this.getBean("AccountLogic");
        List acntList = logic.listByKey(acnt,applicantName,BDMName,
                TCSName,deptManager);      
               
        List viewBeanList = new ArrayList();        
         for(int i=0;i<acntList.size();i++) {
            VbQueryList vb = new VbQueryList();
            Acnt acntLast = (Acnt)acntList.get(i);
            vb.setAcntId(acntLast.getAcntId());
            vb.setAchieveBelong(acntLast.getAchieveBelong());
            vb.setAcntName(acntLast.getAcntName());
            vb.setAcntFullName(acntLast.getAcntFullName());
            AcntPerson acntPerson = new AcntPerson();
            acntPerson = logic.loadByRidFromPerson(acntLast.getRid(), 
                    "DeptManager");
           
           if(acntPerson!=null){
            vb.setDeptManager(acntPerson.getName());
          }
            acntPerson = logic.loadByRidFromPerson(acntLast.getRid(),
                    IDtoAccount.USER_TYPE_TC_SIGNER);       
           if(acntPerson!=null){
        	   vb.setTCSName(acntPerson.getName());
           }
                           
            acntPerson = logic.loadByRidFromPerson(acntLast.getRid(),
                    IDtoAccount.USER_TYPE_BD_MANAGER);   
            if(acntPerson!=null){
            vb.setBDMName(acntPerson.getName());
            }
            acntPerson=logic.loadByRidFromPerson(acntLast.getRid(),
                    IDtoAccount.USER_TYPE_APPLICANT);
            if(acntPerson!=null){
            vb.setApplicantName(acntPerson.getName());
            }
            viewBeanList.add(vb);
          }
            request.setAttribute(server.framework.common.Constant.
                    VIEW_BEAN_KEY, viewBeanList);  
    }
    }           
          
