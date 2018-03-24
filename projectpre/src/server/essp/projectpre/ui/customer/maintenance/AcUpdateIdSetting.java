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
     * 1.如果当前选中的代码生成方式为系统代码， 如果记录已存在则更新idSetting
     * 中对应的两条记录（kind为GroupCode和kind为ClientCode），如不存在则新增。
     * 再次进入该页面默认会显示代码生成方式为系统代码的这条信息
     * 2.如果当前选中的代码生成方式为人工编码，再次进入该页面默认会显示代码生成方式为人工编码的这条信息
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            IIdSettingService logic = (IIdSettingService) this
                             .getBean("IdSettingLogic");
            // 如果有ActionForm传入的话，用此方法获得ActionFrom
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
            // 通过此方法以接口的形式得到一个服务的实例        
            request.setAttribute(Constant.VIEW_BEAN_KEY,idSetting);
            request.setAttribute(Constant.VIEW_BEAN_KEY,idSetting1);
          }
    
    }
 
 
    
    


