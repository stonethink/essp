package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

/**
 * 预览bd code的Action
 * 
 * @author wenhaizheng
 * 
 */
public class AcPreviewBdCode extends AbstractESSPAction {
    
    private final static String CODE="code";

    /**
     * 预览bd code
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
          
            String code =request.getParameter(CODE);
          
            
            Bd bd=new Bd();
            
            // 通过此方法以接口的形式得到一个服务的实例
            IBdService logic = (IBdService) this
                    .getBean("BdCodeLogic");
            
            bd=logic.loadByBdCode(code);
            
            request.setAttribute(Constant.VIEW_BEAN_KEY,bd);
//          throw new BusinessException("error.system.db");
            //默认会转向ForwardId为sucess的页面
            //如果需要自定义ForWardId，用下面的语句
            //data.getReturnInfo().setForwardID("ForwardId");
    
    }

}
