package server.essp.projectpre.ui.dept.apply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcDeleteDeptApp extends AbstractESSPAction {

    /**
     * �������뵥��ɾ���������������뵥����ͬ��������¼,
     * ��ɾ����Ա����������뵥����ͬ��N����¼
     * @param code ��ǰ̨���RID
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
       Long code= null;
        if (request.getParameter("CODE") != null
                && !request.getParameter("CODE").equals("")) {
            code = Long.valueOf(request.getParameter("CODE"));
        }

        // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");       
        logic.delete(code);
        logic.deleteAcntPersonApp(code);
      

    }

}

