package server.essp.security.ui.menu;

import server.essp.framework.action.AbstractESSPAction;
import c2s.dto.ReturnInfo;
import server.framework.common.BusinessException;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.security.service.menu.MenuServiceImp;
import c2s.dto.ITreeNode;
import server.essp.security.service.menu.IMenuService;
import java.util.List;
import c2s.essp.common.code.DtoKey;


public class AcMenuTree extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
                         HttpServletResponse response, TransactionData data) throws
          BusinessException {
          InputInfo inputInfo = data.getInputInfo();
          ReturnInfo returnInfo = data.getReturnInfo();
          List list = null;
          String SelectRole =(String)inputInfo.getInputObj("SelectRole");
          String SelectParentId = (String)inputInfo.getInputObj("SelectParentId");
          //IRoleService logic = (IRoleService)this.getBean("MenuService");
          IMenuService logic = new MenuServiceImp();
          ITreeNode root = logic.getMenuTree();
          if(SelectRole!=null){
              list = (List) logic.getRoleMenuTree(SelectRole);
              if(list.size()==0 && SelectParentId!=null){
                  list = (List) logic.getRoleMenuTree(SelectParentId);
              }
          }
          returnInfo.setReturnObj(DtoKey.ROOT,root);
          returnInfo.setReturnObj("SelectMenu",list);
    }
}
