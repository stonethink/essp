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
 * �鿴����bd code��Action
 * 
 * @author wenhaizheng
 * 
 */
public class AcListAllBdCode extends AbstractESSPAction{
    /**
     * �鿴����bd code
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {

        // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
        IBdService logic = (IBdService) this.getBean("BdCodeLogic");

        List codeList = logic.listAll();

        // ����Ҫ���ص����ݷŵ�Request�У�����ҵ����������ݲ�����ŵ�Session��
        request.setAttribute(Constant.VIEW_BEAN_KEY, codeList);

        // Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
        // �����Ҫ�Զ���ForWardId������������
        // data.getReturnInfo().setForwardID("NULL");

    }

}
