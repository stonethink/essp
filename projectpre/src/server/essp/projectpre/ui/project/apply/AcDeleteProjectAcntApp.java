package server.essp.projectpre.ui.project.apply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.accountapplication.IAccountApplicationService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * ɾ��ѡ�е������Action��������������ͱ�����룩
 * @author wenhaizheng
 *
 * 
 */
public class AcDeleteProjectAcntApp  extends AbstractESSPAction {

           private static final String RID = "rid";
    /**
     * ɾ��ר�������������
     *   1.ɾ�������Ա
     *   2.ɾ������������Ϣ
     *   3.ɾ���ͻ�����
     *   4.ɾ������
     * @param request
     * @param response
     * @param data
     * @throws BusinessException
     */              
    public void executeAct(HttpServletRequest request, HttpServletResponse response, 
                        TransactionData data) throws BusinessException {
   
        Long rid = null;
        if (request.getParameter(RID) != null
                && !request.getParameter(RID).equals("")) {
            rid = Long.valueOf(request.getParameter(RID));
        }
        
        IAccountApplicationService logic = (IAccountApplicationService) this.getBean("AccountApplicationLogic");
        
        //ɾ�������Ա
        logic.deleteAllRelatedFromPersonApp(rid);
        
        //ɾ������������Ϣ
        logic.deleteAllRelatedFromTechInfoApp(rid);
        
        //ɾ���ͻ�����
        logic.deleteAllRelatedFromCustomerInfoApp(rid);
        
        //ɾ������
        logic.delete(rid);
        

        
    }
    

  

}
