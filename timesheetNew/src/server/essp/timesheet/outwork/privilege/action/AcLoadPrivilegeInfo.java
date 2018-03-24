/*
 * Created on 2008-5-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.outwork.privilege.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.ITreeNode;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.outwork.DtoUser;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.outwork.privilege.service.IOutWorkerPriService;
import server.framework.common.BusinessException;

public class AcLoadPrivilegeInfo extends AbstractESSPAction {

    /**
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
         String loginId = (String) data.getInputInfo().getInputObj(DtoUser.DTO_LOGIN_ID);
         IOutWorkerPriService service = (IOutWorkerPriService)
         this.getBean("outWorkerPriService");
         ITreeNode root = service.loadQueryPrivilege(loginId);
         data.getReturnInfo().setReturnObj(DtoUser.DTO_TREE_NODE, root);
    }

}
