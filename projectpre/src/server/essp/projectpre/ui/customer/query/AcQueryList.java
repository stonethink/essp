package server.essp.projectpre.ui.customer.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.customer.ICustomerService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;

public class AcQueryList extends AbstractESSPAction {	
	/**
     * 根据查询页面上的查询条件查询出已经确认的符合查询条件的客户资料
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		
		AfQueryData theform = (AfQueryData) this.getForm();
		Customer customer =new Customer();
		
		String attribute = theform.getAttribute();
	    String customerId = theform.getCustomerId().trim();
	    String belongBd = theform.getBelongBd();
	    String belongSite = theform.getBelongSite();
	    String class_ = theform.getClass_();
	    String country = theform.getCountry();
		String addSub = theform.getAddSub();
		String short_ = theform.getShort_();
		if(short_!=null&&!short_.equals("")){
			customer.setShort_(short_);
		}
	    if (attribute != null && !attribute.equals("")) {
	        customer.setAttribute(attribute);  
	    }
		if (customerId != null && !customerId.equals("")) {
			customer.setCustomerId(customerId);
		}
		if (belongBd != null && !belongBd.equals("")) {
			customer.setBelongBd(belongBd);
		}
		if (belongSite != null && !belongSite.equals("")) {
			customer.setBelongSite(belongSite);
		}
		if (class_ != null && !class_.equals("")) {
			customer.setClass_(class_);
		}
		if (country != null && !country.equals("")) {
			customer.setCountry(country);
		}	
		ICustomerService logicCustomer = (ICustomerService) this.getBean("CustomerLogic");
		List customerList = logicCustomer.listByKey(customer);		
		//当check了包含子公司，还需要做以下搜索
		if (addSub != null) {			
            if (customerId != null && !customerId.equals("")) {
				customer.setGroupId(customerId);
				customer.setCustomerId(null);
			}
			if (attribute != null && !attribute.equals("")) {
				customer.setAttribute(null);               
			}  
			List custList = logicCustomer.listByKey(customer);
			for (Iterator it = custList.iterator();it.hasNext();) {
				Customer cust = (Customer)it.next();
				if(!customerList.contains(cust)){
					customerList.add(cust);			
                }
            }
            if(attribute!=null){
              List newCustList = new ArrayList();
               for(int i = 0;i<customerList.size();i++){
                 Customer customerNest = (Customer)customerList.get(i);
                   if(customerNest.getAttribute().equals("Group")){
                        newCustList.add(customerNest);
                   }else if(customerNest.getAttribute().equals("Company")){
                     if(customerNest.getGroupId()!=null&&!customerNest.getGroupId().equals("")){
                           newCustList.add(customerNest);
                      }   
                 }  
              }
                   customerList = newCustList;
            }
        }
  
        ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
        Site site = new Site();
        IBdService bdLogic = (IBdService)this.getBean("BdCodeLogic");
        Bd bd = new Bd();
        List newCustomerList = new ArrayList();
        for(int i = 0;i<customerList.size();i++){
            Customer customerNest = (Customer)customerList.get(i);
            VbQuery vb = new VbQuery();
            try {
                DtoUtil.copyProperties(vb, customerNest);
            } catch (Exception e) {            
                e.printStackTrace();
            }
            site = siteLogic.loadByCode(customerNest.getBelongSite());
            if(site!=null){
            vb.setBelongSite(site.getSiteName());
            }
            bd = bdLogic.loadByBdCode(customerNest.getBelongBd());
            if(bd!=null){
            vb.setBelongBd(bd.getBdName());
            }
            IParameterService logicCustClass = (IParameterService) this.getBean("ParameterLogic");
            Parameter parameter = logicCustClass.loadByKindCode("BusinessType",customerNest.getClass_());
            if(parameter!=null){
               vb.setClass_(parameter.getName());
            }
            parameter = logicCustClass.loadByKindCode("CountryCode",customerNest.getCountry());
            if(parameter!=null){
               vb.setCountry(parameter.getName());
            }
            newCustomerList.add(vb);           
        }
		request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY, newCustomerList);

	}

}
