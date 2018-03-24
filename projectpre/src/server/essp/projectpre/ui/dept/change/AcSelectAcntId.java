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
     *根据选中的部门代码将与此部门代码相同的那条记录的详细信息显示在部门变更申请页面上
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
//              获得相关人员,包括部门经理、工时表签核者、BD主管、申请人             
                newAf = getPerson(newAf, logic, acnt.getRid());

//              将部门代号数据源放在部门代号LIST中
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
                //获得业绩归属单位来源
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
     * 获取与部门相关的人员名字
     * @param af
     * @param logic
     * @param rid
     * @return 含人员姓名的af
     */
    private AfDeptApp getPerson(AfDeptApp newAf, IAccountService  logic, Long rid) {
//      获得相关人员,包括部门经理、工时表签核者、BD主管、申请人
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



