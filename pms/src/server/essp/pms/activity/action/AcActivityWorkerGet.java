package server.essp.pms.activity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.essp.pms.wbs.DtoActivityWorker;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import server.framework.common.BusinessException;

public class AcActivityWorkerGet extends AbstractESSPAction{
    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long) inputInfo.getInputObj("acntRid");
        String loginId = (String) inputInfo.getInputObj("loginId");

        LgAccountLaborRes logic = new LgAccountLaborRes();
        DtoAcntLoaborRes dto = logic.findResourceDto(acntRid,loginId);
        DtoActivityWorker worker = new DtoActivityWorker();
        //DtoAcntLoaborRes和DtoActivityWorker包含相同名字但意义不同的字段
        worker.setAcntRid(dto.getAcntRid());
        worker.setEmpName(dto.getEmpName());
        worker.setJobCode(dto.getJobCode());
        worker.setJobcodeId(dto.getJobcodeId());
        worker.setLoginId(dto.getLoginId());
        worker.setRoleName(dto.getRoleName());

        returnInfo.setReturnObj("dto",worker);
    }
}
