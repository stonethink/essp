package server.essp.pms.wbs.process.checklist.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.wbs.DtoWbsActivity;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckPoint;
import c2s.essp.pms.qa.DtoQaCheckPoint;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcQaCheckPointList extends AbstractESSPAction {
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

        DtoWbsActivity dto = (DtoWbsActivity) inputInfo.getInputObj(DtoKey.DTO);
        LgQaCheckPoint lg = new LgQaCheckPoint();
        Long acntRid = dto.getAcntRid();
        Long belongRid;
        String belongTo;
        if (dto.isActivity()) {
            belongRid = dto.getActivityRid();
            belongTo = DtoKey.TYPE_ACTIVITY;
        } else {
            belongRid = dto.getWbsRid();
            belongTo = DtoKey.TYPE_WBS;
        }
        List checkPointList = null;
        checkPointList = lg.listCheckPoint(acntRid, belongRid, belongTo);

        returnInfo.setReturnObj(DtoQaCheckPoint.PMS_CHECK_POINT_LIST,
                                checkPointList);
    }
}
