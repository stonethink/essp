package server.essp.projectpre.ui.dept.change;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.ui.dept.apply.AfDeptApp;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;

public class AcInitialDeptApp extends AbstractESSPAction{
    /**
     * 初始化部门变更申请页面
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                 TransactionData data) throws BusinessException {
        final SelectOptionImpl  firstOption = new SelectOptionImpl("--please select--","","--please select--");            
        AfDeptApp af = new AfDeptApp();
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
        
//      获得确认表中的部门编号
        IAccountService logicAcnt = (IAccountService) this.getBean("AccountLogic"); 
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userLoginId = user.getUserLoginId();
        List acntIdList = logicAcnt.listAllDept(userLoginId);
        List deptList = new ArrayList();
        deptList.add(firstOption);
        SelectOptionImpl deptOption = null;
        for(int i=0;i<acntIdList.size();i++){
            Acnt acnt = (Acnt) acntIdList.get(i);
            IAccountApplicationService Logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
            if( Logic.loadByTypeAcntId("DeptChangeApp",acnt.getAcntId())== null){
                deptOption = new SelectOptionImpl(acnt.getAcntId(),acnt.getAcntId(),acnt.getAcntId());
                deptList.add(deptOption);
            }
        }
        af.setAcntIdList(deptList);    
        af.setStatus("0");
        request.setAttribute(Constant.VIEW_BEAN_KEY,af);
    }

    
   
}

