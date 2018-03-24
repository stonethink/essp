package server.essp.projectpre.ui.dept.apply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcDeleteDeptApp extends AbstractESSPAction {

    /**
     * 根据申请单号删除申请表中与此申请单号相同的那条记录,
     * 且删除人员申请表中申请单号相同的N条记录
     * @param code 从前台获得RID
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
       Long code= null;
        if (request.getParameter("CODE") != null
                && !request.getParameter("CODE").equals("")) {
            code = Long.valueOf(request.getParameter("CODE"));
        }

        // 通过此方法以接口的形式得到一个服务的实例
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");       
        logic.delete(code);
        logic.deleteAcntPersonApp(code);
      

    }

}

