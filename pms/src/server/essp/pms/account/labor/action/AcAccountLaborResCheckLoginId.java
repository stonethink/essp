package server.essp.pms.account.labor.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import itf.hr.IHrUtil;
import itf.hr.HrFactory;
import c2s.essp.common.user.DtoUser;
import server.essp.framework.action.AbstractESSPAction;

public class AcAccountLaborResCheckLoginId extends AbstractESSPAction {
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

        String loginId = (String) inputInfo.getInputObj("loginId");
        IHrUtil hrUtil = HrFactory.create();
        //应调用逻辑方法查找该LoginId对应的人
        DtoUser user = hrUtil.findResouce(loginId);
        DtoAcntLoaborRes res2 = null;
        if(user != null){
            res2 = new DtoAcntLoaborRes();
            res2.setEmpName(user.getUserName());
            res2.setJobcodeId(user.getJobCode());
            res2.setLoginId(loginId);
            returnInfo.setReturnObj("resource",res2);
        }

    }
}
