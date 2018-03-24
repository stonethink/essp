package server.essp.common.code.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoKey;
import server.essp.common.code.logic.LgCode;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcCodeList extends AbstractBusinessAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        String type = (String)inputInfo.getInputObj(DtoKey.TYPE);

        LgCode logic = new LgCode();
        ITreeNode root = logic.list(type);

        returnInfo.setReturnObj(DtoKey.ROOT, root);
    }
}
