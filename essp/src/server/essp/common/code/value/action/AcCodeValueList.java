package server.essp.common.code.value.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoKey;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.common.code.value.logic.LgCodeValue;

public class AcCodeValueList extends AbstractBusinessAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long codeRid = (Long)inputInfo.getInputObj(DtoKey.CODE_RID);

        LgCodeValue logic = new LgCodeValue();
        ITreeNode root = logic.list(codeRid);

        returnInfo.setReturnObj(DtoKey.ROOT, root);
    }
}
