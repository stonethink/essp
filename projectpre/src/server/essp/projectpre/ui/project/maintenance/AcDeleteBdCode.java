package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * ɾ��bd code��Action
 * 
 * @author wenhaizheng
 * 
 */
public class AcDeleteBdCode extends AbstractESSPAction {
    
    private final static String CODE = "code";

    /**
     * ɾ��bd code
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        String code = null;
        if (request.getParameter(CODE) != null
                && !request.getParameter(CODE).equals("")) {
            code = (String) request.getParameter(CODE);
        }

        // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
        IBdService logic = (IBdService) this.getBean("BdCodeLogic");

        logic.delete(code);

        // Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
        // �����Ҫ�Զ���ForWardId������������
        // data.getReturnInfo().setForwardID("ForwardId");

    }

}
