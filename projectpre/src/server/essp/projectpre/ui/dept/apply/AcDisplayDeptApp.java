package server.essp.projectpre.ui.dept.apply;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntApp;
import server.essp.projectpre.db.AcntPersonApp;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;


public class AcDisplayDeptApp extends AbstractESSPAction {
    private final static String RID="rid";
    /**
     * 显示部门资料的详细信息
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        Long rid=null;
        final SelectOptionImpl  firstOption = new SelectOptionImpl("--please select--","","--please select--");  
        String IDFORMATER = "00000000";
        if (request.getParameter(RID) != null) {
            rid = Long.valueOf(request.getParameter(RID));
        }  
        AcntApp acntApp = new AcntApp();
        Acnt acnt = null;
        AfDeptApp  af = new AfDeptApp();
        IAccountApplicationService logic = (IAccountApplicationService) this
                .getBean("AccountApplicationLogic");    
        IAccountService logicAcnt = (IAccountService) this.getBean("AccountLogic");
        acntApp=logic.loadByRid(rid);      
        //将持久化类中可用属性拷贝到af中
        try {
            DtoUtil.copyProperties(af, acntApp);
        } catch (Exception e) {
            e.printStackTrace();
        }      
        if(acntApp.getRelParentId() != null && !"".equals(acntApp.getRelParentId())) {
        	acnt = logicAcnt.loadByAcntId(acntApp.getRelParentId(), "0");
        	af.setParentDept(acntApp.getRelParentId()+"---"+acnt.getAcntName());
        }
        //格式化申请单号（Rid）
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        String newRid = df.format(acntApp.getRid(), new StringBuffer(),
                                  new FieldPosition(0)).toString();
        af.setRid(newRid);
  
         //获得相关人员,包括部门经理、工时表签核者、BD主管、申请人
        af = getPerson(af, logic, rid);
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
        af.setBelongBdList(bdList);   
        request.setAttribute(Constant.VIEW_BEAN_KEY,af);
    }
 
    
  
    /**
     * 获取与部门相关的人员名字
     * @param af
     * @param logic
     * @param rid
     * @return 含人员姓名的af
     */
    private AfDeptApp getPerson(AfDeptApp af, IAccountApplicationService logic, Long rid) {
//      获得相关人员,包括部门经理、工时表签核者、BD主管、申请人
        AcntPersonApp acntPersonApp = new AcntPersonApp();
        acntPersonApp = logic.loadByRidFromPersonApp(rid, "DeptManager");
        af.setDeptManager(acntPersonApp.getName());
        af.setDMLoginId(acntPersonApp.getLoginId());
           
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_TC_SIGNER);       
        af.setTCSName(acntPersonApp.getName());
        af.setTCSLoginId(acntPersonApp.getLoginId());
                
        acntPersonApp = logic.loadByRidFromPersonApp(rid, IDtoAccount.USER_TYPE_BD_MANAGER);       
        af.setBDMName(acntPersonApp.getName());
        af.setBDLoginId(acntPersonApp.getLoginId());
               
        return af;
    }
}
