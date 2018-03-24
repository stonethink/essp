package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * 删除bd code的Action
 * 
 * @author wenhaizheng
 * 
 */
public class AcDeleteBdCode extends AbstractESSPAction {
    
    private final static String CODE = "code";

    /**
     * 删除bd code
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        String code = null;
        if (request.getParameter(CODE) != null
                && !request.getParameter(CODE).equals("")) {
            code = (String) request.getParameter(CODE);
        }

        // 通过此方法以接口的形式得到一个服务的实例
        IBdService logic = (IBdService) this.getBean("BdCodeLogic");

        logic.delete(code);

        // 默认会转向ForwardId为sucess的页面
        // 如果需要自定义ForWardId，用下面的语句
        // data.getReturnInfo().setForwardID("ForwardId");

    }

}
