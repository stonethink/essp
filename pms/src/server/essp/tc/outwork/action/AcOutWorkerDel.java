package server.essp.tc.outwork.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.tc.outwork.logic.LgOutWork;

public class AcOutWorkerDel extends AbstractESSPAction {
    public AcOutWorkerDel() {
    }

    /**
     * 删除一条出差记录
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data)
            throws BusinessException {
        String rid="";
        if(request.getParameter("rid")!=null){
            rid=request.getParameter("rid");
        }
        LgOutWork logic=new LgOutWork();
        logic.delete(new Long(rid));

    }
}
