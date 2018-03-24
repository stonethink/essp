package server.essp.security.ui.menu;

import server.essp.framework.action.AbstractESSPAction;
import c2s.dto.ReturnInfo;
import server.framework.common.BusinessException;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.security.service.menu.MenuServiceImp;
import c2s.essp.common.menu.DtoMenuItem;
import java.util.List;
import server.essp.security.service.menu.IMenuService;

public class AcSaveMenu extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                      HttpServletResponse response, TransactionData data) throws
       BusinessException {
       InputInfo inputInfo = data.getInputInfo();
       ReturnInfo returnInfo = data.getReturnInfo();
       List list = (List)inputInfo.getInputObj("SaveMenu");
       String roleId = (String)inputInfo.getInputObj("RoleId");
       String[] menu = null;
       String Menus =null;
       //IRoleService logic = (IRoleService)this.getBean("MenuService");
       IMenuService logic = new MenuServiceImp();
       if(list.size()>0){
           for (int i = 0; i < list.size(); i++) {
               DtoMenuItem dto = (DtoMenuItem) list.get(i);
               if (Menus == null) {
                   Menus = dto.getFunctionID();
               } else {
                   Menus = Menus + "," + dto.getFunctionID();
               }
           }
           menu = Menus.split(",");
           logic.saveOrUpdateRoleMenu(roleId, menu);
       }else{
           logic.saveOrUpdateRoleMenu(roleId, null);
       }
   }
}
