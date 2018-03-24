package server.essp.projectpre.ui.project.apply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * 删除选中的申请的Action（包括新增申请和变更申请）
 * @author wenhaizheng
 *
 * 
 */
public class AcDeleteProjectAcntApp  extends AbstractESSPAction {

           private static final String RID = "rid";
    /**
     * 删除专案申请相关资料
     *   1.删除相关人员
     *   2.删除技术资料信息
     *   3.删除客户资料
     *   4.删除申请
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */              
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                        TransactionData data) throws BusinessException {
   
        Long rid = null;
        if (request.getParameter(RID) != null
                && !request.getParameter(RID).equals("")) {
            rid = Long.valueOf(request.getParameter(RID));
        }
        
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        
        //删除相关人员
        logic.deleteAllRelatedFromPersonApp(rid);
        
        //删除技术资料信息
        logic.deleteAllRelatedFromTechInfoApp(rid);
        
        //删除客户资料
        logic.deleteAllRelatedFromCustomerInfoApp(rid);
        
        //删除申请
        logic.delete(rid);
        

        
    }
    

  

}
