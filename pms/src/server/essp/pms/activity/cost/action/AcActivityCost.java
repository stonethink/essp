package server.essp.pms.activity.cost.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.activity.cost.logic.LgActivityCost;
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
public class AcActivityCost extends AbstractESSPAction { /**
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
        String acntRid=(String)inputInfo.getInputObj("acntRid");
        String activityId=(String)inputInfo.getInputObj("activityId");

        LgActivityCost lg = new LgActivityCost();
        List costlist = new ArrayList();

        costlist = lg.load(acntRid,activityId);
        returnInfo.setReturnObj("activityCost", costlist);

    }
 }
