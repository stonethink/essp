package server.essp.pms.templatePop.action;

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
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcGuideLinePopComboTemplate extends AbstractESSPAction{


    public void executeAct(HttpServletRequest request,
                            HttpServletResponse response, TransactionData data) throws
         BusinessException {

         LgTemplate logic = new LgTemplate();

         Vector resultVector = logic.getOspTemplateCombox(false);

        data.getReturnInfo().setReturnObj("comboTemplate", resultVector);
     }

}
