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
 * Ԥ��bd code��Action
 * 
 * @author wenhaizheng
 * 
 */
public class AcPreviewBdCode extends AbstractESSPAction {
    
    private final static String CODE="code";

    /**
     * Ԥ��bd code
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
          
            String code =request.getParameter(CODE);
          
            
            Bd bd=new Bd();
            
            // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
            IBdService logic = (IBdService) this
                    .getBean("BdCodeLogic");
            
            bd=logic.loadByBdCode(code);
            
            request.setAttribute(Constant.VIEW_BEAN_KEY,bd);
//          throw new BusinessException("error.system.db");
            //Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
            //�����Ҫ�Զ���ForWardId������������
            //data.getReturnInfo().setForwardID("ForwardId");
    
    }

}
