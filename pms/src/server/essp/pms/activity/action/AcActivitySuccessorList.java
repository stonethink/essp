package server.essp.pms.activity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import server.essp.pms.activity.logic.LgActivityRelation;
import c2s.dto.InputInfo;
import java.util.List;

public class AcActivitySuccessorList extends AbstractESSPAction {
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
        Long preRid = (Long) inputInfo.getInputObj("preRid");

        LgActivityRelation logic = new LgActivityRelation();
        List successorList;
        if(preRid!=null){
            successorList = logic.getActivitySuccessorsDto(acntRid, preRid);
        }else{
            successorList = logic.getActivityAllSuccessorsDtoByAcnt(acntRid);
        }
        returnInfo.setReturnObj("successorList",successorList);
    }
}
