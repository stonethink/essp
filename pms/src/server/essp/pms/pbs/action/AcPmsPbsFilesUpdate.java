package server.essp.pms.pbs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.logic.LgPmsPbsFilesList;
import server.framework.common.BusinessException;

public class AcPmsPbsFilesUpdate extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
         InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        List fileList = (List) inputInfo.getInputObj("fileList");

        LgPmsPbsFilesList logic = new LgPmsPbsFilesList();
        logic.setDbAccessor(this.getDbAccessor());
        logic.update(fileList);

        returnInfo.setReturnObj("fileList", fileList);
    }
}
