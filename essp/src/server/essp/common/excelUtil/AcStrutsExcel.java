package server.essp.common.excelUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.common.excelUtil.IExcelParameter;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.framework.controller.CTActionLoad;

/**
 * <p>Title: AcStrutsExcel</p>
 *
 * <p>Description: 使用统一架构，并使AbstractExcelAction的实现类可以使用Spring</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcStrutsExcel extends AbstractESSPAction {

    /**
     * executeAct
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData transactionData) throws
            BusinessException {
        run(httpServletRequest, httpServletResponse);
    }

    public void run(
            HttpServletRequest request,
            HttpServletResponse response) throws BusinessException {


            //从request取得action代号，并查找对应的action
            String actionID = request.getParameter(IExcelParameter.ACTION_ID);
            if (actionID == null || actionID.length() == 0) {
                throw new BusinessException("Input invalid action id!");
            }
            log.info("actionID=" + actionID);

            String actionName = CTActionLoad.getInstance().getValue(actionID);
            if (actionName == null || actionName.length() == 0) {
                throw new BusinessException("Didn't define [" + actionID + "]!");
            }
            log.info("actionName=" + actionName);
            try {
                //调用action
                Class cls = Class.forName(actionName);
                AbstractExcelAction action = (AbstractExcelAction) cls.
                                              newInstance();
                 action.setWebContext(this.getWebApplicationContext());
                ServletOutputStream os = response.getOutputStream();
                action.execute(request, response, os);
            } catch (Exception e) {
                if(e instanceof BusinessException) {
                    throw (BusinessException) e;
                } else {
                    throw new BusinessException("error.system",
                        "System Error.");
                }
            }
    }



}
