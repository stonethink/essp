package server.essp.pms.account.cost.action;


import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import java.util.ArrayList;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import server.essp.pms.account.cost.logic.LgAcntAcCost;
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
public class AcAcntAcCostList extends AbstractESSPAction { /**
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

        DtoAcntCost dto = (DtoAcntCost) inputInfo.getInputObj("acntactivitycost");

        String acntRid=new String();
                      acntRid= dto.getAcntRid().toString();
        LgAcntAcCost lg = new LgAcntAcCost();
        List costlist = new ArrayList();
        costlist = lg.load(acntRid);
        returnInfo.setReturnObj("acntactivitycost", costlist);

    }
 }

