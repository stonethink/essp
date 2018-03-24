package server.essp.projectpre.ui.customer.maintenance;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.IdSetting;
import server.essp.projectpre.service.idsetting.IIdSettingService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;


public class AcPreviewIdSetting extends AbstractESSPAction {
    /**
     *��ʼ���ͻ���Ϣ���ã�����idSetting�еĴ������ɷ�ʽ��ֵ��ѡ����ϵͳ���뻹���˹����룬
     *��idSetting�е������Ϣ��ʾ��ҳ����
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            String IDFORMATER = "0";
            String IDFORMATER1 = "0";
            AfIdSetting af = (AfIdSetting) this.getForm();
            String kind = server.essp.projectpre.service.constant.Constant.GROUP_CODE;
            String kind1 = server.essp.projectpre.service.constant.Constant.CLIENT_CODE;
            IdSetting idSetting=new IdSetting();
            IdSetting idSetting1=new IdSetting();
            
            // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
            IIdSettingService logic = (IIdSettingService) this
                    .getBean("IdSettingLogic");
            idSetting=logic.loadByKey(kind);
            idSetting1=logic.loadByKey(kind1);
            if (idSetting!=null){
                Long iLong =idSetting.getLength();
                int i = iLong.intValue();
                for(int k = 2;k < i;k++) {
                    IDFORMATER = IDFORMATER + "0";
                }
                DecimalFormat df = new DecimalFormat(IDFORMATER);
                String newCurrentSeq = df.format(idSetting.getCurrentSeq(), new StringBuffer(),
                                          new FieldPosition(0)).toString();
                af.setLength(idSetting.getLength().toString());
                af.setCurrentSeq(newCurrentSeq);
                af.setFirstChar(idSetting.getFirstChar());
                af.setCodingMethod(idSetting.getCodingMethod());
            }
            
            if (idSetting1!=null){
                Long iLong =idSetting1.getLength();
                int i = iLong.intValue();
                for(int k = 2;k < i;k++) {
                    IDFORMATER1 = IDFORMATER1 + "0";
                }
                DecimalFormat df = new DecimalFormat(IDFORMATER1);
                String newCurrentSeq = df.format(idSetting1.getCurrentSeq(), new StringBuffer(),
                                          new FieldPosition(0)).toString();
                af.setLength1(idSetting1.getLength().toString());
                af.setCurrentSeq1(newCurrentSeq);         
                af.setFirstChar1(idSetting1.getFirstChar());            
                af.setCodingMethod1(idSetting1.getCodingMethod());
            }
            if(idSetting!=null){
            af.setCodingGenerate(idSetting.getCodingGenerate());
            }         
            request.setAttribute(Constant.VIEW_BEAN_KEY,af);
    }

}
