package server.essp.pms.account.cost.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.cost.logic.LgAcntCost;
import java.util.List;
import java.util.ArrayList;
import c2s.essp.pms.account.cost.DtoAcntCost;


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
public class AcAcntCostList extends AbstractESSPAction { /**
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
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();

        DtoAcntCost dto = (DtoAcntCost) inputInfo.getInputObj("acntcost");
        String acntRid=new String();
                      acntRid= dto.getAcntRid().toString();
        LgAcntCost lg = new LgAcntCost();
        List costlist = new ArrayList();
        costlist = lg.load(acntRid);
        returnInfo.setReturnObj("acntcost", costlist);

    }
 }

