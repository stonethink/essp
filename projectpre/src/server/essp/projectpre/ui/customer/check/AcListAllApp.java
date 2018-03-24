package server.essp.projectpre.ui.customer.check;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.CustomerApplication;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.essp.projectpre.ui.customer.check.VbCustAppList;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
import com.wits.util.comDate;
public class AcListAllApp extends AbstractESSPAction {

    /**
     * 显示所有提交了的客户新增申请和客户变更申请的记录
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {      
        String appTypeAdd= server.essp.projectpre.service.constant.Constant.CUSTOMERADDAPP;
        String appTypeChange = server.essp.projectpre.service.constant.Constant.CUSTOMERCHANGEAPP; 
        String applicationStatus = server.essp.projectpre.service.constant.Constant.SUBMIT;       
        ICustomerApplication   logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");       
        List codeList = logic.listAllSub(applicationStatus);
        List viewBean = new ArrayList();
        String IDFORMATER = "00000000";
        String newRid1 = null;
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        for(int i=0;i<codeList.size();i++){         
            CustomerApplication customerApplication = (CustomerApplication) codeList.get(i);        
            VbCustAppList vbCustAppList = new VbCustAppList();          
            newRid1 = df.format(customerApplication.getRid(), new StringBuffer(),
                    new FieldPosition(0)).toString();
            vbCustAppList.setRid(newRid1);
            vbCustAppList.setRegId(customerApplication.getRegId());
            vbCustAppList.setCustomerShort(customerApplication.getCustomerShort());
            vbCustAppList.setApplicantName(customerApplication.getApplicantName());       
            vbCustAppList.setApplicationDate(comDate.dateToString(customerApplication.getApplicationDate(),"yyyy-MM-dd"));
            if(customerApplication.getApplicationType().equals(appTypeAdd)){
               vbCustAppList.setApplicationType(customerApplication.getApplicationType());
            }else if(customerApplication.getApplicationType().equals(appTypeChange)){
                vbCustAppList.setApplicationType(customerApplication.getApplicationType());
            }
           
            viewBean.add(vbCustAppList);           
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, viewBean);
    }
}

        
        
        
        
        
        
        
        




