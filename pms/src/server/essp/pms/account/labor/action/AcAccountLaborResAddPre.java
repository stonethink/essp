package server.essp.pms.account.labor.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;

public class AcAccountLaborResAddPre extends AbstractESSPAction {
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
                           HttpServletResponse response,
                           TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        IHrUtil hrUtil = HrFactory.create();
        Vector jobCodeOptions = hrUtil.listComboJobCode();
        returnInfo.setReturnObj("jobCodeOptions",jobCodeOptions);


    }
}
