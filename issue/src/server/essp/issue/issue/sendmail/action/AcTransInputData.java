package server.essp.issue.issue.sendmail.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;

public class AcTransInputData extends AbstractISSUEAction {
    public AcTransInputData() {
    }

    /**
     * 专门用来传递inputDate
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
        BusinessException {
        if(request.getAttribute("inputData")!=null){
            request.getSession().setAttribute("inputData",request.getAttribute("inputData"));
        }else{
            throw new BusinessException("","得到数据失败");
        }

    }
}
