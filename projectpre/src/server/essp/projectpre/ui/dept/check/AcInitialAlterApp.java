package server.essp.projectpre.ui.dept.check;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import com.wits.util.comDate;

public class AcInitialAlterApp  extends AbstractESSPAction {
    private final static String rid="rid";

    /**
     *初始化部门变更申请页面
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            Long code=null;
            String IDFORMATER = "00000000";
            if (request.getParameter(rid) != null) {
                code =Long.valueOf(request.getParameter(rid)) ;
            }                            
            VbDeptListApp vbDeptListApp = new VbDeptListApp();
           // 通过此方法以接口的形式得到一个服务的实例
            IAccountApplicationService logic = (IAccountApplicationService) this
                    .getBean("AccountApplicationLogic");    
            AcntApp acntApp=new AcntApp();  
            acntApp=logic.loadByRid(code); 
            AcntPerson acntPerson = new AcntPerson();         
            Acnt acnt=new Acnt();
            Acnt acntNest = null;
            IAccountService logicAcnt = (IAccountService) this.getBean("AccountLogic");   
            acnt=logicAcnt.loadByRid(code);
            Vb vb1 = new Vb();
            List deptList = new ArrayList();
            vb1.setOption("acntId");
            vb1.setBeforeChange(acnt.getAcntId());
            vb1.setAfterChange(acntApp.getAcntId());
            deptList.add(vb1);
            
            Vb vb2 = new Vb();          
            vb2.setOption("acntName");
            vb2.setBeforeChange(acnt.getAcntName());
            vb2.setAfterChange(acntApp.getAcntName());
            deptList.add(vb2);
            
            Vb vb3 = new Vb();          
            vb3.setOption("acntFullName");
            vb3.setBeforeChange(acnt.getAcntFullName());
            vb3.setAfterChange(acntApp.getAcntFullName());
            deptList.add(vb3);
            
            Vb vb4 = new Vb();   
            IBdService logicBD = (IBdService) this.getBean("BdCodeLogic");
            Bd bd = logicBD.loadByBdCode(acnt.getAchieveBelong());
            vb4.setOption("achieveBelong");
            if(bd!=null){
            vb4.setBeforeChange(bd.getBdName());
             }
            bd = logicBD.loadByBdCode(acntApp.getAchieveBelong());
            if(bd!=null){
            vb4.setAfterChange(bd.getBdName());
            }
            deptList.add(vb4);
            
            Vb vb5 = new Vb();          
            vb5.setOption("parentDept");
            String parentBefore = "";
            String parentAfter = "";
            if(acnt.getRelParentId() != null && !"".equals(acnt.getRelParentId())) {
            	acntNest = logicAcnt.loadByAcntId(acnt.getRelParentId(), "0");
            	parentBefore = acnt.getRelParentId()+"---"+acntNest.getAcntName();
            }
            if(acntApp.getRelParentId() != null && !"".equals(acntApp.getRelParentId())) {
            	acntNest = logicAcnt.loadByAcntId(acntApp.getRelParentId(), "0");
            	parentAfter = acntApp.getRelParentId()+"---"+acntNest.getAcntName();
            }
            vb5.setBeforeChange(parentBefore);
            vb5.setAfterChange(parentAfter);
            deptList.add(vb5);
            
            //显示相关Person的变化
            deptList = showPerson(code,deptList,logic,logicAcnt);
            Vb vb = new Vb();          
            vb.setOption("isEnable");
            vb.setBeforeChange(acnt.getIsEnable());
            vb.setAfterChange(acntApp.getIsEnable());        
            deptList.add(vb);
            
            acntPerson = logicAcnt.loadByRidFromPerson(code, IDtoAccount.USER_TYPE_APPLICANT);
            vbDeptListApp.setApplicantName(acntPerson.getName()); 
            vbDeptListApp.setDeptList(deptList);                                
            vbDeptListApp.setApplicationDate(comDate.dateToString(acntApp.getApplicationDate(),"yyyy-MM-dd"));
            vbDeptListApp.setApplicationStatus("Submitted");
            vbDeptListApp.setRemark("");
           //格式化申请单号           
            DecimalFormat df = new DecimalFormat(IDFORMATER);
            String newRid = df.format(acntApp.getRid(), new StringBuffer(),new FieldPosition(0)).toString();                                
            vbDeptListApp.setRid(newRid);
            request.setAttribute(Constant.VIEW_BEAN_KEY,vbDeptListApp);
    }
    /**
     * 显示Person
     * @param code
     * @param deptList
     * @param logic
     * @param logicAcnt
     * @return
     */
    private List showPerson(Long code,List deptList,IAccountApplicationService logic,IAccountService logicAcnt) {
        AcntPerson acntPerson = new AcntPerson();
        AcntPersonApp acntPersonApp = new AcntPersonApp();
        
        acntPerson = logicAcnt.loadByRidFromPerson(code, "DeptManager");
        acntPersonApp = logic.loadByRidFromPersonApp(code, "DeptManager");
        Vb vbDM = new Vb();
        vbDM.setOption("DeptManager");
        vbDM.setBeforeChange(acntPerson.getName());
        vbDM.setAfterChange(acntPersonApp.getName());
        deptList.add(vbDM);
   
        acntPerson = logicAcnt.loadByRidFromPerson(code, IDtoAccount.USER_TYPE_BD_MANAGER);
        acntPersonApp = logic.loadByRidFromPersonApp(code, IDtoAccount.USER_TYPE_BD_MANAGER);
        Vb vbBD = new Vb();
        vbBD.setOption("BDName");
        vbBD.setBeforeChange(acntPerson.getName());
        vbBD.setAfterChange(acntPersonApp.getName());
        deptList.add(vbBD);
             
        acntPerson = logicAcnt.loadByRidFromPerson(code, IDtoAccount.USER_TYPE_TC_SIGNER);
        acntPersonApp = logic.loadByRidFromPersonApp(code, IDtoAccount.USER_TYPE_TC_SIGNER);
        Vb vbTC = new Vb();
        vbTC.setOption("TCSName");
        vbTC.setBeforeChange(acntPerson.getName());
        vbTC.setAfterChange(acntPersonApp.getName());
        deptList.add(vbTC);
        
        return deptList;  
    }
}


