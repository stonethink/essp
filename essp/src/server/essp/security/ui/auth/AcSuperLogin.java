package server.essp.security.ui.auth;

import server.framework.action.*;
import c2s.essp.common.menu.DtoMenu;
import javax.servlet.http.HttpServletRequest;
import c2s.essp.common.user.DtoUser;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.security.service.menu.IMenuService;
import server.essp.security.service.user.IUserService;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;

public class AcSuperLogin extends AbstractBusinessAction {
    public static List SUPER_USER = new ArrayList();
    static{
        SUPER_USER.add("WH0302008");//stone
        SUPER_USER.add("TP0709007");//Gavin
        SUPER_USER.add("WH0607016");//baohuitu
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        DtoUser currentUser = (DtoUser) getSession().getAttribute(DtoUser.SESSION_LOGIN_USER);
        String loginId = currentUser.getUserLoginId();
        if(!SUPER_USER.contains(loginId))
            throw new BusinessException("error.superLogin","You are not a super user!");
        AfLogin afLogin = (AfLogin)this.getForm();
        try {
            IUserService userService = (IUserService)this.getBean("UserService");

            //����DtoUser����,��ȡ�û��Ľ�ɫ�嵥;����DtoUser����Http Seesion��Ӧ��KEY = SESSION_USER & SESSION_LOGIN_USER
            DtoUser dtoUser = userService.getUserByLoginId(afLogin.getLoginId());
            this.getSession().setAttribute(DtoUser.SESSION_USER, dtoUser);
            //this.getSession().setAttribute(DtoUser.SESSION_LOGIN_USER, dtoUser);

            //�����û���ɫ���칦�ܲ˵�;�������û��Ĳ˵�����Http Seesion
            IMenuService menuService = (IMenuService)this.getBean("MenuService");
            DtoMenu dtoMenu = menuService.getUserMenu(dtoUser);
            this.getSession().setAttribute(DtoMenu.SESSION_MENU, dtoMenu);
            this.getSession().setAttribute(DtoMenu.SESSION_LOGIN_MENU, dtoMenu);
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
