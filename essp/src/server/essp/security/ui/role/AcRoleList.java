package server.essp.security.ui.role;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.essp.security.service.role.IRoleService;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.security.service.role.RoleServiceImpl;
import c2s.dto.ITreeNode;
import c2s.essp.common.code.DtoKey;

public class AcRoleList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                         HttpServletResponse response, TransactionData data) throws
          BusinessException {
          InputInfo inputInfo = data.getInputInfo();
          ReturnInfo returnInfo = data.getReturnInfo();
          //IRoleService logic = (IRoleService)this.getBean("RoleService");
          IRoleService  logic = new RoleServiceImpl();
          ITreeNode root =(ITreeNode)logic.getRoleTree();
          returnInfo.setReturnObj(DtoKey.ROOT,root);
    }
}
