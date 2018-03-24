package server.essp.projectpre.ui.customer.add;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.comDate;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.db.CustomerApplication;
import server.essp.projectpre.service.customer.ICustomerService;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.essp.projectpre.ui.customer.add.VbCustAppList;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;

public class AcListAllCustAppInfo extends AbstractESSPAction {

    /**
     * 根据用户ID，状态为非确认，申请类型为客户新增申请查询所有满足以上条件的客户记录
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {

        // 通过此方法以接口的形式得到一个服务的实例
        String applicationType = server.essp.projectpre.service.constant.Constant.CUSTOMERADDAPP;
        String applicationStatus = server.essp.projectpre.service.constant.Constant.CONFIRMED;
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userLoginId = user.getUserLoginId();
        ICustomerApplication   logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");
        List codeList = logic.listAll(userLoginId,applicationType, applicationStatus);
        List viewBean = new ArrayList();
        String IDFORMATER = "00000000";
        String newRid = null;
        DecimalFormat df = new DecimalFormat(IDFORMATER);
        for(int i=0;i<codeList.size();i++){
            CustomerApplication customerApplication = (CustomerApplication) codeList.get(i);               
            VbCustAppList vbCustAppList = new VbCustAppList();        
            newRid = df.format(customerApplication.getRid(), new StringBuffer(),
                    new FieldPosition(0)).toString();
            vbCustAppList.setRid(newRid);
            vbCustAppList.setCustomerShort(customerApplication.getCustomerShort());
            vbCustAppList.setRegId(customerApplication.getRegId());
            vbCustAppList.setApplicationDate(comDate.dateToString(customerApplication.getApplicationDate(),"yyyy-MM-dd"));
            vbCustAppList.setApplicationStatus(customerApplication.getApplicationStatus());
            
            ICustomerService logicCust = (ICustomerService) this.getBean("CustomerLogic");
            Customer customer=new Customer();
            //根据客户编号查询出客户名称，将客户编号和客户名称拼接在一起显示在页面上
            if(customerApplication.getGroupId()!=null){
             customer=logicCust.loadByCustomerId(customerApplication.getGroupId());
             vbCustAppList.setGroupId(customer.getCustomerId()+"---"+customer.getShort_());
            }
            viewBean.add(vbCustAppList);
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, viewBean);
    }

}

        
        
        
        
        
        
        
        



