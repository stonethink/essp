package server.essp.security.ui.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import server.framework.common.Constant;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import server.framework.action.AbstractBusinessAction;
import server.essp.security.service.role.IRoleService;
import java.util.ArrayList;
import server.essp.common.ldap.LDAPConfig;
import server.essp.framework.action.AbstractESSPAction;

public class AcUserRoleList extends  AbstractESSPAction{

        public static final String Selected="selectd";
        /**
         * 列出用户可见的角色以及用户可用的角色。
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData data)
                        throws BusinessException {

        IRoleService logic = (IRoleService)this.getBean("RoleService");
        List listRole = logic.listEnabled2VisibleRole();
        List list = new ArrayList();
        String loginId = (String)request.getParameter("LoginId");
        String domain = (String)request.getParameter("Domain");
    
        if(domain == null) {//add by lipengxu, for HR/ConfigRole get default domain
            domain = LDAPConfig.getDefaultDomainId();
        }

        if(loginId ==null||domain==null){
            request.setAttribute(Constant.VIEW_BEAN_KEY, list);
            return;
        }else{
            List selectRole = (List) logic.listUserAllRole(loginId);
            request.getSession().setAttribute(Selected, selectRole);
            request.getSession().setAttribute("loginId",loginId);
            request.getSession().setAttribute("domain", domain);
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, listRole);
    }

}
