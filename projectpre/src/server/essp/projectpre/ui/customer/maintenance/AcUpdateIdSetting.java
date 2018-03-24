package server.essp.projectpre.ui.customer.maintenance;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.IdSetting;
import server.essp.projectpre.service.idsetting.IIdSettingService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;

import c2s.dto.TransactionData;

public class AcUpdateIdSetting extends AbstractESSPAction {

    /**
     * 1.�����ǰѡ�еĴ������ɷ�ʽΪϵͳ���룬 �����¼�Ѵ��������idSetting
     * �ж�Ӧ��������¼��kindΪGroupCode��kindΪClientCode�����粻������������
     * �ٴν����ҳ��Ĭ�ϻ���ʾ�������ɷ�ʽΪϵͳ�����������Ϣ
     * 2.�����ǰѡ�еĴ������ɷ�ʽΪ�˹����룬�ٴν����ҳ��Ĭ�ϻ���ʾ�������ɷ�ʽΪ�˹������������Ϣ
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            IIdSettingService logic = (IIdSettingService) this
                             .getBean("IdSettingLogic");
            // �����ActionForm����Ļ����ô˷������ActionFrom
            AfIdSetting af = (AfIdSetting) this.getForm();
            IdSetting idSetting= new IdSetting(); 
            IdSetting idSetting1= new IdSetting();   
            idSetting = logic.loadByKey("GroupCode");
            idSetting1 = logic.loadByKey("ClientCode");
            if(idSetting!=null){
               if(af.getLength()!=null) {
                    idSetting.setLength(Long.valueOf(af.getLength()));
                }
                 
                   if(af.getFirstChar()!=null) {
                       idSetting.setFirstChar(af.getFirstChar());
                   }
                   if(af.getCodingMethod()!=null) {
                       idSetting.setCodingMethod(af.getCodingMethod());
                   }
                   if(af.getCurrentSeq()!=null) {
                       idSetting.setCurrentSeq(Long.valueOf(af.getCurrentSeq()));
                   }
                   idSetting.setCodingGenerate(af.getCodingGenerate());
                   logic.update(idSetting);
               } else {
                   idSetting = new IdSetting();
                   idSetting.setLength(Long.valueOf(af.getLength()));
                   idSetting.setFirstChar(af.getFirstChar());
                   idSetting.setCodingMethod(af.getCodingMethod());
                   idSetting.setCurrentSeq(Long.valueOf(af.getCurrentSeq()));
                   idSetting.setCodingGenerate(af.getCodingGenerate());
                   idSetting.setIdType("GroupCode");
                   logic.save(idSetting);
               }
              
              
              if(idSetting1!=null){
                 if(af.getLength1()!=null) {
                      idSetting1.setLength(Long.valueOf(af.getLength1()));
                 }                 
                 if(af.getFirstChar1()!=null) {
                       idSetting1.setFirstChar(af.getFirstChar1());
                 }
                 if(af.getCodingMethod1()!=null) {
                       idSetting1.setCodingMethod(af.getCodingMethod1());
                 }
                 if(af.getCurrentSeq1()!=null) {
                       idSetting1.setCurrentSeq(Long.valueOf(af.getCurrentSeq1()));
                 }
                       idSetting1.setCodingGenerate(af.getCodingGenerate());
                       logic.update(idSetting1);
              } else {
                  idSetting1 = new IdSetting();
                  idSetting1.setLength(Long.valueOf(af.getLength1()));
                  idSetting1.setFirstChar(af.getFirstChar1());
                  idSetting1.setCodingMethod(af.getCodingMethod1());
                  idSetting1.setCurrentSeq(Long.valueOf(af.getCurrentSeq1()));
                  idSetting1.setCodingGenerate(af.getCodingGenerate());
                  idSetting1.setIdType("ClientCode");
                  logic.save(idSetting1);
                  
              }
            // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��        
            request.setAttribute(Constant.VIEW_BEAN_KEY,idSetting);
            request.setAttribute(Constant.VIEW_BEAN_KEY,idSetting1);
          }
    
    }
 
 
    
    


