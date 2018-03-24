package server.essp.projectpre.ui.customer.check;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.comDate;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.db.CustomerApplication;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.customer.ICustomerService;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.essp.projectpre.ui.dept.check.Vb;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcCheckChange   extends AbstractESSPAction {
    private final static String rid="rid";

    /**
     * 将客户变更申请的信息显示在覆核页面上
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            Long code=null;
            String IDFORMATER = "00000000";
            if (request.getParameter(rid) != null) {
                code =Long.valueOf(request.getParameter(rid)) ;
            }                       
            CustomerApplication custApp=new CustomerApplication();          
            VbCustAppList vbCustAppList = new VbCustAppList();
            // 通过此方法以接口的形式得到一个服务的实例
            ICustomerApplication logic = (ICustomerApplication) this
                    .getBean("CustomerApplicationLogic");       
            custApp=logic.loadByRid(code); 
            Long code1 = Long.valueOf(custApp.getCustomerRid());            
            Customer customer = new Customer();
            ICustomerService logic1 = (ICustomerService) this.getBean("CustomerLogic");   
            customer=logic1.loadByRid(code1);
           
            List custList = new ArrayList();
            Vb vb1 = new Vb();
            vb1.setOption("regId");
            vb1.setBeforeChange(customer.getRegId());
            vb1.setAfterChange(custApp.getRegId());
            custList.add(vb1);
            
            Vb vb2 = new Vb();          
            vb2.setOption("groupId");
            vb2.setBeforeChange(customer.getGroupId());
            vb2.setAfterChange(custApp.getGroupId());
            custList.add(vb2);
            
            Vb vb3 = new Vb();          
            vb3.setOption("customerId");
            vb3.setBeforeChange(customer.getCustomerId());
            vb3.setAfterChange(custApp.getCustomerId());
            custList.add(vb3);
            
            Vb vb4 = new Vb();          
            vb4.setOption("customerShort");
            vb4.setBeforeChange(customer.getShort_());
            vb4.setAfterChange(custApp.getCustomerShort());
            custList.add(vb4);
            
            Vb vb5 = new Vb();          
            vb5.setOption("customerNameCn");
            vb5.setBeforeChange(customer.getNameCn());
            vb5.setAfterChange(custApp.getCustomerNameCn());
            custList.add(vb5);
            
            Vb vb6 = new Vb();          
            vb6.setOption("customerNameEn");
            vb6.setBeforeChange(customer.getNameEn());
            vb6.setAfterChange(custApp.getCustomerNameEn());
            custList.add(vb6);
            
            Vb vb7 = new Vb(); 
            IBdService bdLogic = (IBdService)this.getBean("BdCodeLogic");
            Bd bd = bdLogic.loadByBdCode(customer.getBelongBd());
            vb7.setOption("BelongBD");   
            if(bd!=null){
             vb7.setBeforeChange(bd.getBdName());
            }
            bd = bdLogic.loadByBdCode(custApp.getBelongBd());
            if(bd!=null){
             vb7.setAfterChange(bd.getBdName());
            }
            custList.add(vb7);
           
            Vb vb8 = new Vb();   
            ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
            Site site = siteLogic.loadByCode(customer.getBelongSite());
            vb8.setOption("BelongSite");
            if(site!=null){
            vb8.setBeforeChange(site.getSiteName());
            }
            site = siteLogic.loadByCode(custApp.getBelongSite());
            if(site!=null){
            vb8.setAfterChange(site.getSiteName());
            }
            custList.add(vb8);
            
            Vb vb9 = new Vb();     
            IParameterService logicCustClass = (IParameterService) this.getBean("ParameterLogic");
            Parameter parameter = logicCustClass.loadByKindCode("BusinessType",customer.getClass_());
            vb9.setOption("customerClass");
            if(parameter!=null){
            vb9.setBeforeChange(parameter.getName());  
            }
            parameter = logicCustClass.loadByKindCode("BusinessType",custApp.getCustomerClass());
            if(parameter!=null){
            vb9.setAfterChange(parameter.getName());
            }
            custList.add(vb9);
            
            Vb vb11 = new Vb();     
            IParameterService logicCustCountry= (IParameterService) this.getBean("ParameterLogic");
            Parameter paraCountry = logicCustCountry.loadByKindCode("CountryCode",customer.getCountry());
            vb11.setOption("customerCountry");
            if(paraCountry!=null){
            vb11.setBeforeChange(paraCountry.getName());  
            }
            paraCountry = logicCustClass.loadByKindCode("CountryCode",custApp.getCustomerCountry());
            if(paraCountry!=null){
            vb11.setAfterChange(paraCountry.getName());
            }
            custList.add(vb11);
            
            Vb vb10 = new Vb();          
            vb10.setOption("customerDes");
            vb10.setBeforeChange(customer.getCustDescription());
            vb10.setAfterChange(custApp.getCustDescription());
            custList.add(vb10);
            vbCustAppList.setAlterList(custList);
            
            
            
           //格式化申请单号
           DecimalFormat df = new DecimalFormat(IDFORMATER);
           String newRid = df.format(custApp.getRid(), new StringBuffer(),
                                   new FieldPosition(0)).toString();
           vbCustAppList.setRid(newRid);  
           
           vbCustAppList.setApplicationDate(comDate.dateToString(custApp.getApplicationDate(),"yyyy-MM-dd"));
           vbCustAppList.setApplicationStatus("Submitted");
           vbCustAppList.setApplicantName(customer.getCreator());
           vbCustAppList.setDescription("");
           request.setAttribute(Constant.VIEW_BEAN_KEY,vbCustAppList);
    }
}


