package server.essp.pms.pbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.pbs.DtoPmsPbsFiles;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.logic.LgPmsPbsFiles;
import server.framework.common.BusinessException;

public class AcPmsPbsFilesAdd extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
         InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        DtoPmsPbsFiles dtoPmsPbsFiles = (DtoPmsPbsFiles) inputInfo.getInputObj("dto");

        LgPmsPbsFiles logic = new LgPmsPbsFiles();
        logic.add(dtoPmsPbsFiles);

        dtoPmsPbsFiles.setOp(IDto.OP_NOCHANGE);
        returnInfo.setReturnObj("dto", dtoPmsPbsFiles);
    }
}
