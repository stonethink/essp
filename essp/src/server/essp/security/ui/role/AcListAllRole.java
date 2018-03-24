package server.essp.security.ui.role;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.security.service.role.IRoleService;
import java.util.List;
import server.framework.common.Constant;
import server.essp.framework.action.AbstractESSPAction;

public class AcListAllRole extends AbstractESSPAction {

        /**
         * 列出所有的角色.
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData data)
                        throws BusinessException {

                IRoleService logic = (IRoleService) this.getBean("RoleService");
                List codeList = logic.listAllRole();
                request.setAttribute(Constant.VIEW_BEAN_KEY, codeList);
        }

}
