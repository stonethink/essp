package server.essp.common.code.choose.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoKey;
import server.essp.common.code.choose.logic.LgCodeTree;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcCodeTree extends AbstractBusinessAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        String type = (String)inputInfo.getInputObj(DtoKey.TYPE);
        String catalog = (String)inputInfo.getInputObj(DtoKey.CATALOG);

        LgCodeTree logic = new LgCodeTree();
        ITreeNode root = logic.list(type,catalog);

        returnInfo.setReturnObj(DtoKey.ROOT, root);
    }
}
