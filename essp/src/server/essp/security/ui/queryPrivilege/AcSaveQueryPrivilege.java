package server.essp.security.ui.queryPrivilege;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.ITreeNode;
import server.essp.security.service.queryPrivilege.IQueryPrivilegeService;

/**
 * <p>Title: AcSaveQueryPrivilege</p>
 *
 * <p>Description: 保存查询授权的Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcSaveQueryPrivilege extends AbstractESSPAction {

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
        ITreeNode root = (ITreeNode) data.getInputInfo().getInputObj("root");
        IQueryPrivilegeService service = (IQueryPrivilegeService)
                                         this.getBean("queryPrivilegeService");
        service.saveQueryPrivilege(loginId, root);
    }

}
