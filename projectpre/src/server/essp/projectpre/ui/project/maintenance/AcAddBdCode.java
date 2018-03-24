package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * ���bd code��Action
 * 
 * @author wenhaizheng
 * 
 */
public class AcAddBdCode extends AbstractESSPAction {
    
    /**
     * ���bd code
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {

        // �����ActionForm����Ļ����ô˷������ActionFrom
        AfBd af = (AfBd) this.getForm();
        Bd bd = new Bd();
        if (af.getBdCode() != null && !af.getBdCode().trim().equals("")) {
            bd.setBdCode(af.getBdCode().trim());
        }
        if (af.getBdName() != null && !af.getBdName().trim().equals("")) {
            bd.setBdName(af.getBdName().trim());
        }
        bd.setDescription(af.getDescription());
        if(af.getStatus()!=null&&af.getStatus().equals("true")) {
            bd.setStatus(true);
        }else {
            bd.setStatus(false);
        }
        if(af.getAchieveBelong()!=null&&af.getAchieveBelong().equals("true")) {
            bd.setAchieveBelong(true);
        }else {
            bd.setAchieveBelong(false);
        }
        // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
        IBdService logic = (IBdService) this.getBean("BdCodeLogic");

        logic.save(bd);

        // Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
        // �����Ҫ�Զ���ForWardId������������
        // data.getReturnInfo().setForwardID("ForwardId");
    }

}
