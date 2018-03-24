package server.essp.projectpre.ui.dept.change;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.ui.dept.apply.AfDeptApp;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;

public class AcSelectAcntId extends AbstractESSPAction {
    /**
     *����ѡ�еĲ��Ŵ��뽫��˲��Ŵ�����ͬ��������¼����ϸ��Ϣ��ʾ�ڲ��ű������ҳ����
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {    
            final SelectOptionImpl  firstOption = new SelectOptionImpl("--please select--","","--please select--");  
            AfDeptApp af =  (AfDeptApp)this.getForm();
            IAccountService logic = (IAccountService) this.getBean("AccountLogic");                 
            Acnt acnt = new Acnt();
            Acnt acntNest = null;
            if(!af.getAcntId().equals("")){
                acnt = logic.loadByAcntId(af.getAcntId(), "0");
                AfDeptApp newAf = new  AfDeptApp();
                newAf.setRid(af.getRid());
                newAf.setAcntName(acnt.getAcntName());
                newAf.setAcntFullName(acnt.getAcntFullName());
                newAf.setAcntAttribute(acnt.getAcntAttribute());
                newAf.setAchieveBelong(acnt.getAchieveBelong());
                newAf.setStatus(acnt.getIsEnable());
                newAf.setAcntId(af.getAcntId());
                if(acnt.getRelParentId() != null && !"".equals(acnt.getRelParentId())) {
                	acntNest = logic.loadByAcntId(acnt.getRelParentId(), "0");
                	newAf.setParentDept(acnt.getRelParentId()+"---"+acntNest.getAcntName());
                }
//              ��������Ա,�������ž�����ʱ��ǩ���ߡ�BD���ܡ�������             
                newAf = getPerson(newAf, logic, acnt.getRid());

//              �����Ŵ�������Դ���ڲ��Ŵ���LIST��
                IAccountService logicAcnt = (IAccountService) this.getBean("AccountLogic");   
                DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
                String userLoginId = user.getUserLoginId();
                List acntIdList = logicAcnt.listAllDept(userLoginId);
                List deptList = new ArrayList();
                deptList.add(firstOption);
                SelectOptionImpl deptOption = null;;
                for(int i=0;i<acntIdList.size();i++){
                    Acnt acntDept = (Acnt) acntIdList.get(i);
                    IAccountApplicationService Logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
                    if( Logic.loadByTypeAcntId("DeptChangeApp",acntDept.getAcntId())== null){
                        deptOption = new SelectOptionImpl(acntDept.getAcntId(),acntDept.getAcntId(),acntDept.getAcntId());
                        deptList.add(deptOption);
                    }
                    
                }
                newAf.setAcntIdList(deptList);              
                //���ҵ��������λ��Դ
                IBdService logicBD = (IBdService) this.getBean("BdCodeLogic");
                List codeList = logicBD.listAllEabled();
                List bdList = new ArrayList();
                bdList.add(firstOption);
                SelectOptionImpl bdOption = null;
                for(int i=0;i<codeList.size();i++){
                    Bd bd = (Bd) codeList.get(i);
                    bdOption = new SelectOptionImpl(bd.getBdName(),bd.getBdCode(),bd.getBdName());
                    bdList.add(bdOption);
                }
                newAf.setBelongBdList(bdList);               
                newAf.setApplicationStatus("Confirmed");
                request.setAttribute(Constant.VIEW_BEAN_KEY,newAf);                            
            }

    }
    /**
     * ��ȡ�벿����ص���Ա����
     * @param af
     * @param logic
     * @param rid
     * @return ����Ա������af
     */
    private AfDeptApp getPerson(AfDeptApp newAf, IAccountService  logic, Long rid) {
//      ��������Ա,�������ž�����ʱ��ǩ���ߡ�BD���ܡ�������
        AcntPerson acntPerson = new AcntPerson();
        acntPerson = logic.loadByRidFromPerson(rid, "DeptManager");        
        newAf.setDeptManager(acntPerson.getName());
        newAf.setDMLoginId(acntPerson.getLoginId());
        
        acntPerson = logic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_TC_SIGNER);      
        newAf.setTCSName(acntPerson.getName());
        newAf.setTCSLoginId(acntPerson.getLoginId());
        
        acntPerson = logic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_BD_MANAGER);        
        newAf.setBDMName(acntPerson.getName());
        newAf.setBDLoginId(acntPerson.getLoginId());
             
        acntPerson = logic.loadByRidFromPerson(rid, IDtoAccount.USER_TYPE_APPLICANT);        
        newAf.setApplicantName(acntPerson.getName());
        newAf.setApplicantId(acntPerson.getLoginId());
        return newAf;
    }
}



