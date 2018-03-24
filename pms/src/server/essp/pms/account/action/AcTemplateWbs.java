package server.essp.pms.account.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.pms.wbs.DtoWbsActivity;
import java.util.List;
import server.essp.pms.wbs.logic.LgWbs;
import c2s.dto.ITreeNode;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.wbs.DtoKEY;

public class AcTemplateWbs extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException{
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        Long acntrid = (Long)inputInfo.getInputObj("acntrid");
        LgWbs wbsLogic=new LgWbs();
        ITreeNode treeNode = wbsLogic.getWbsTree(acntrid);
        returnInfo.setReturnObj(DtoKEY.WBSTREE, treeNode);
    }

}
