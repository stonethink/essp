package server.essp.pms.activity.process.guideline.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.activity.process.guideline.logic.LgActivityGuideLine;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description:获得活动列表 </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class AcActivityGLAcList extends AbstractESSPAction {
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
                          HttpServletResponse response, TransactionData data) throws
       BusinessException {
       Long refAcntRid = (Long) data.getInputInfo().getInputObj("selectedTemplate");
       LgActivityGuideLine lg = new LgActivityGuideLine();
       Vector resultVector = lg.getActivityCombox(refAcntRid);
       data.getReturnInfo().setReturnObj("comboActivity", resultVector);
   }
}
