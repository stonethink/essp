package server.essp.projectpre.ui.customer.check;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.db.CustomerApplication;
import server.essp.projectpre.db.IdSetting;
import server.essp.projectpre.service.customer.ICustomerService;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.essp.projectpre.service.idsetting.IIdSettingService;
import server.essp.projectpre.ui.customer.add.AfCustomerApplication;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;


public class AcConfirmAddApp extends AbstractESSPAction {

    /**
     *如果客户新增申请确认：
     *1.如果编码生成方式是系统生成，则会将当前最大的客户编号加1作为当前的客户编号。
     *2.如果編碼生成方式是手工添寫，則覆核者填写客户编号，如果填写的客户编号已存在则会报错
     *3.将申请表中的数据复制到正式表中并删除申请表中的记录
     *如果客户新增申请被拒绝，则将申请状态改为拒绝，且关闭窗口
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {       
            // 如果有ActionForm传入的话，用此方法获得ActionFrom
            AfCustomerApplication af=(AfCustomerApplication) this.getForm();
            ICustomerApplication logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");  
            CustomerApplication customerApplication =logic.loadByRid(Long.valueOf(af.getRid()));     
            Customer customer=new Customer();
            ICustomerService logic1 = (ICustomerService) this.getBean("CustomerLogic");  
            if(!customerApplication.getApplicationStatus().equals(af.getApplicationStatus())){      
               final String rid="rid";
               Long code=null;
               if (request.getParameter(rid) != null) {
               code =Long.valueOf(request.getParameter(rid)) ;
              }
               customerApplication=logic.loadByRid(code);
               String rid1 = logic1.createApplyNo();
               customer.setRid(Long.valueOf(rid1));
               //生成客户编码;            
                String kind = server.essp.projectpre.service.constant.Constant.CLIENT_CODE;
                String kindG = server.essp.projectpre.service.constant.Constant.GROUP_CODE;
                IdSetting idSetting=new IdSetting();
                String IDFORMATER = "0";
                IIdSettingService logicIdSetting = (IIdSettingService) this.getBean("IdSettingLogic");  
                if(customerApplication.getAttribute().equals("Company")){ 
                   idSetting=logicIdSetting.loadByKey(kind);
                }else{
                    idSetting=logicIdSetting.loadByKey(kindG);  
                }
                if (idSetting.getCodingGenerate().equals("SystemCode")){               
                     Long  CurrentSeq = idSetting.getCurrentSeq();
                     int tem;
                     tem = (CurrentSeq.intValue()+1);
                     CurrentSeq = Long.valueOf(String.valueOf(tem)); 
                     Long iLong =idSetting.getLength();
                     int i = iLong.intValue();
                     for(int k = 2;k < i;k++) {
                         IDFORMATER = IDFORMATER + "0";
                     }
                     DecimalFormat df = new DecimalFormat(IDFORMATER);
                     String newCurrentSeq = df.format(CurrentSeq, new StringBuffer(),
                                               new FieldPosition(0)).toString();              
                     String customerId = idSetting.getFirstChar() + newCurrentSeq;
                     ICustomerService logicCust = (ICustomerService) this.getBean("CustomerLogic");             
                      if(logicCust.loadByCustomerId((customerId))!=null){
                          throw new BusinessException("error.logic.customerServiceImpl.customerIdduplicated");                  
                       }else{
                          customer.setCustomerId(customerId);
                        }                   
                     idSetting.setCurrentSeq(CurrentSeq);
                     logicIdSetting.update(idSetting);
                
               }else{
                   ICustomerService logicCust = (ICustomerService) this.getBean("CustomerLogic");                    
                     if(logicCust.loadByCustomerId((af.getCustomerId().trim()))!=null){
                        throw new BusinessException("error.logic.customerServiceImpl.customerIdduplicate");                  
                     }else{
                         customer.setCustomerId(af.getCustomerId().trim());
                      }            
               }              
               customer.setAttribute(customerApplication.getAttribute());
               customer.setGroupId(customerApplication.getGroupId());
               customer.setRegId(customerApplication.getRegId());      
               customer.setShort_(customerApplication.getCustomerShort()); 
               customer.setNameCn(customerApplication.getCustomerNameCn());
               customer.setNameEn(customerApplication.getCustomerNameEn());
               customer.setCreator(customerApplication.getApplicantName());
               customer.setBelongBd(customerApplication.getBelongBd());              
               customer.setBelongSite(customerApplication.getBelongSite());
               customer.setClass_(customerApplication.getCustomerClass());
               customer.setCountry(customerApplication.getCustomerCountry());
               customer.setCustDescription(customerApplication.getCustDescription());                
               customer.setCreateDate(customerApplication.getApplicationDate());
               customer.setDescription(customerApplication.getDescription());
              
               logic1.save(customer);
               logic.delete(code);
          }else{
               customerApplication.setApplicationStatus("Rejected");
               customerApplication.setDescription(af.getDescription());
               logic.update(customerApplication);     
    }
           
  }
             
}

