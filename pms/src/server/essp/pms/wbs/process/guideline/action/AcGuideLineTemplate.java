package server.essp.pms.wbs.process.guideline.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.logic.LgTemplate;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description:得到所有可以参考的template </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class AcGuideLineTemplate extends AbstractESSPAction {
    public AcGuideLineTemplate() {
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

        LgTemplate logic = new LgTemplate();

        Vector resultVector = logic.getOspTemplateCombox(true);

        data.getReturnInfo().setReturnObj("comboTemplate", resultVector);
    }
}
