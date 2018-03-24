package server.essp.pms.activity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.essp.pms.activity.logic.LgActivityWorker;
import java.util.List;
import java.util.Vector;
import itf.hr.IHrUtil;
import itf.hr.HrFactory;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import server.essp.framework.action.AbstractESSPAction;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.dto.DtoUtil;
import java.util.ArrayList;
import c2s.essp.pms.wbs.DtoActivityWorker;

public class AcActivityWorkerList extends AbstractESSPAction {
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
        Long activityRid = (Long) inputInfo.getInputObj("activityRid");
        IHrUtil hrUtil = HrFactory.create();
        Vector jobCodeOptions = hrUtil.listComboJobCode();

        LgActivityWorker logic = new LgActivityWorker();
        List activityWorkerList = logic.listActivityWorkerDto(acntRid,activityRid,jobCodeOptions);
        returnInfo.setReturnObj("activityWorkerList",activityWorkerList);
    }
}
