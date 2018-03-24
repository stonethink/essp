package server.essp.security.ui.queryPrivilege;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.security.service.queryPrivilege.IQueryPrivilegeService;
import java.util.List;

/**
 * <p>Title: AcGetUserInfo</p>
 *
 * <p>Description: 根据用户loginId获取姓名，角色等信息的Action</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcGetUserInfo extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        String loginIds = (String) data.getInputInfo().getInputObj("loginIds");
        IQueryPrivilegeService service = (IQueryPrivilegeService)
                                         this.getBean("queryPrivilegeService");
        List userList = service.getUserInfo(loginIds);
        data.getReturnInfo().setReturnObj("userList", userList);
    }
}
