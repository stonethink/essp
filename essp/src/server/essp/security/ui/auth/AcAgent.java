package server.essp.security.ui.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.common.menu.DtoMenu;
import c2s.essp.common.user.DtoUser;
import itf.user.LgEmpUserUtilImpl;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.security.service.authorize.IAuthService;
import server.essp.security.service.menu.IMenuService;
import server.essp.security.service.role.IRoleService;
import server.essp.security.service.user.IUserService;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcAgent extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
//        HttpSession session=request.getSession(true);
        //如果 functionType = "agent",即调用agent;
//        agent(user);
        //如果 functionType = "dis-agent", 即调用dis-agent;
//        disagent();
        String functionType =(String)request.getParameter("functionType");
        if(functionType.equals("agent")){
            DtoUser dtoUser = (DtoUser)this.getSession().getAttribute(DtoUser.
                SESSION_LOGIN_USER);
            String selected = (String)request.getParameter("selected");
            IAuthService logic = (IAuthService)this.getBean("AuthService");
            if(logic.checkAgent(selected, dtoUser.getUserLoginId())) {
                this.getSession().setAttribute("SelectUser",selected);
                agent(selected);
            } else {
                throw new BusinessException("error.system.acAgent","insufficient privileges");
            }

        }else if(functionType.equals("dis-agent")){
            disagent();
        }
    }

    private void agent(String agentLoginId) throws BusinessException {
        //将原有的ID对应的菜单在Session进行备份
        IUserService userService = (IUserService)this.getBean("UserService");
        DtoUser dtoUser = userService.getUserByLoginId(agentLoginId);
        this.getSession().setAttribute(DtoUser.SESSION_USER, dtoUser);

        IMenuService menuService = (IMenuService)this.getBean("MenuService");
        DtoMenu dtoMenu = menuService.getUserMenu(dtoUser);
        this.getSession().setAttribute(DtoMenu.SESSION_MENU, dtoMenu);

    }

    private void disagent() throws BusinessException {
        this.getSession().setAttribute(DtoUser.SESSION_USER,
                                       this.getSession().
                                       getAttribute(DtoUser.SESSION_LOGIN_USER));
        this.getSession().setAttribute(DtoMenu.SESSION_MENU,
                                       this.getSession().
                                       getAttribute(DtoMenu.SESSION_LOGIN_MENU));
        this.getSession().setAttribute("SelectUser","");
    }

}
