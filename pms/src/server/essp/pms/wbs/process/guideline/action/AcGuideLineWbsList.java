package server.essp.pms.wbs.process.guideline.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.wbs.process.guideline.logic.LgWbsGuideLine;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description:得到一定account/template的wbs </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class AcGuideLineWbsList extends AbstractESSPAction {

    public AcGuideLineWbsList() {
        super();
    }

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

       LgWbsGuideLine logic = new LgWbsGuideLine();
        Long refAcntRid=(Long)data.getInputInfo().getInputObj("selectedTemplate");
        Vector resultVector = logic.getWbsCombox(refAcntRid);
       data.getReturnInfo().setReturnObj("comboWbs", resultVector);
   }

}
