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
 * ����bd code��Action
 * 
 * @author wenhaizheng
 * 
 */
public class AcUpdateBdCode extends AbstractESSPAction {
    
    /**
     * ����bd code
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            
            // �����ActionForm����Ļ����ô˷������ActionFrom
            AfBd af=(AfBd) this.getForm();
            Bd bd=new Bd();
            if(af.getBdCode()!=null) {
                bd.setBdCode(af.getBdCode());
            }
            if(af.getBdName()!=null) {
                bd.setBdName(af.getBdName());
            }
          
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
            bd.setDescription(af.getDescription());
         
           // bd.setRid(new Long(3));
            // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
            IBdService logic = (IBdService) this
                    .getBean("BdCodeLogic");
            
            logic.update(bd);
            request.setAttribute(Constant.VIEW_BEAN_KEY,bd);
            //Ĭ�ϻ�ת��ForwardIdΪsucess��ҳ��
            //�����Ҫ�Զ���ForWardId������������
            //data.getReturnInfo().setForwardID("NULL");

    }

}
