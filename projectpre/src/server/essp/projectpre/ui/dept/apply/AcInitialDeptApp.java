package server.essp.projectpre.ui.dept.apply;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.TransactionData;
public class AcInitialDeptApp extends AbstractESSPAction{
    /**
     * 初始化部门新增申请页面
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                 TransactionData data) throws BusinessException {
        final SelectOptionImpl  firstOption = new SelectOptionImpl("--please select--","","--please select--");            
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        AfDeptApp af = new AfDeptApp();
       
        //获取申请单号
        String rid = logic.createApplyNo();
        af.setRid(rid);
        af.setApplicationStatus("Unsubmit");
        af.setAcntAttribute("Dept");
      
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
        
//      将af设置到前台
        request.setAttribute(Constant.VIEW_BEAN_KEY,af);
    }

    
   
}

