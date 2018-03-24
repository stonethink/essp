package server.essp.security.ui.auth;

import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.framework.common.Constant;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import c2s.essp.common.user.DtoUser;
import c2s.essp.common.user.DtoUserBase;
import server.essp.security.service.user.IUserService;
import server.essp.security.service.menu.IMenuService;
import c2s.essp.common.menu.DtoMenu;

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
public class AcLogin extends AbstractBusinessAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
    	
        AfLogin afLogin = (AfLogin)data.getInputInfo().getInputObj(Constant.ACTION_FORM_KEY);
        HttpSession session = request.getSession();
        if(session == null) {
        	session = this.getSession();
        }
    	
        try {
            //验证用户是否合法,返回DtoUserBase
            IUserService userService = (IUserService)this.getBean("UserService");
            DtoUserBase dtoUserBase = userService.authenticationUser(
                    afLogin.getUserType(),
                    afLogin.getDomain(),
                    afLogin.getLoginId(),
                    afLogin.getPassword());
            afLogin.setLoginId(dtoUserBase.getUserLoginId());
            //构造DtoUser对象,获取用户的角色清单;并将DtoUser放入Http Seesion对应的KEY = SESSION_USER & SESSION_LOGIN_USER
            DtoUser dtoUser = userService.getUser(afLogin.getUserType(),
                                                  afLogin.getDomain(),
                                                  afLogin.getLoginId(),
                                                  afLogin.getPassword()
                              );
            if(dtoUser.getUserName() == null) {
                dtoUser.setUserName(dtoUserBase.getUserName());
            }
            session.setAttribute(DtoUser.SESSION_USER, dtoUser);
            session.setAttribute(DtoUser.SESSION_LOGIN_USER, dtoUser);

            //依据用户角色构造功能菜单;并将该用户的菜单放入Http Seesion
            IMenuService menuService = (IMenuService)this.getBean("MenuService");
            DtoMenu dtoMenu = menuService.getUserMenu(dtoUser);
            session.setAttribute(DtoMenu.SESSION_MENU, dtoMenu);
            session.setAttribute(DtoMenu.SESSION_LOGIN_MENU, dtoMenu);

        } catch (BusinessException ex) {
            data.getReturnInfo().setForwardID("failure");
            throw ex;
        } catch (Exception ex) {
            data.getReturnInfo().setForwardID("failure");
            throw new BusinessException("LOGIN",
                                        "System Error,please contact system administrator ",
                                        ex);
        }
    }  
}
