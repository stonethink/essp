package server.essp.pms.wbs.process.checklist.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckPoint;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.dto.IDto;

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
public class AcQaCheckPointListSave extends AbstractESSPAction {
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

        List list = (List) inputInfo.getInputObj(DtoQaCheckPoint.
                                                 PMS_CHECK_POINT_LIST);
        LgQaCheckPoint lg = new LgQaCheckPoint();
        lg.saveList(list);
        returnInfo.setReturnObj(DtoQaCheckPoint.PMS_CHECK_POINT_LIST,
                                list);
    }
}
