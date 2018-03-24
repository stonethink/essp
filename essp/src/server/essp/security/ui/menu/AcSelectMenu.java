package server.essp.security.ui.menu;

import server.essp.framework.action.AbstractESSPAction;
import c2s.dto.ReturnInfo;
import server.framework.common.BusinessException;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.security.service.menu.MenuServiceImp;
import java.util.List;
import server.essp.security.service.menu.IMenuService;

public class AcSelectMenu extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                      HttpServletResponse response, TransactionData data) throws
       BusinessException {
       InputInfo inputInfo = data.getInputInfo();
       ReturnInfo returnInfo = data.getReturnInfo();
       String SelectRole =(String)inputInfo.getInputObj("SelectRole");
       //IRoleService logic = (IRoleService)this.getBean("MenuService");
       IMenuService logic = new MenuServiceImp();
       List list = (List)logic.getRoleMenuTree(SelectRole);
       returnInfo.setReturnObj("SelectMenu",list);
   }
}
