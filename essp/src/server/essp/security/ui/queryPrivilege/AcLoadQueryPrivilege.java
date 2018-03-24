package server.essp.security.ui.queryPrivilege;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.security.service.queryPrivilege.IQueryPrivilegeService;
import c2s.dto.ITreeNode;

/**
 * <p>Title: AcLoadQueryPrivilege</p>
 *
 * <p>Description: 根据用户loginId获取，专案查询授权信息的Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcLoadQueryPrivilege extends AbstractESSPAction {

    /**
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        String loginId = (String) data.getInputInfo().getInputObj("loginId");
        IQueryPrivilegeService service = (IQueryPrivilegeService)
                                         this.getBean("queryPrivilegeService");
        ITreeNode root = service.loadQueryPrivilege(loginId);
        data.getReturnInfo().setReturnObj("root", root);
    }

}
