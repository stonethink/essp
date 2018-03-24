package server.essp.projectpre.ui.project.maintenance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

/**
 * 查看所有bd code的Action
 * 
 * @author wenhaizheng
 * 
 */
public class AcListAllBdCode extends AbstractESSPAction{
    /**
     * 查看所有bd code
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {

        // 通过此方法以接口的形式得到一个服务的实例
        IBdService logic = (IBdService) this.getBean("BdCodeLogic");

        List codeList = logic.listAll();

        // 将需要返回的数据放到Request中，所有业务操作的数据不允许放到Session中
        request.setAttribute(Constant.VIEW_BEAN_KEY, codeList);

        // 默认会转向ForwardId为sucess的页面
        // 如果需要自定义ForWardId，用下面的语句
        // data.getReturnInfo().setForwardID("NULL");

    }

}
