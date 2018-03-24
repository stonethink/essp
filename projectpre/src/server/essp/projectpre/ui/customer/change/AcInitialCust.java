package server.essp.projectpre.ui.customer.change;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Customer;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.customer.ICustomerService;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.essp.projectpre.ui.customer.add.AfCustomerApplication;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;

public class AcInitialCust extends AbstractESSPAction {
    /**
     *初始化页面
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {   
             final SelectOptionImpl  firstOption = new SelectOptionImpl("--please select--","","--please select--");  
            AfCustomerApplication af = new AfCustomerApplication();
            ICustomerApplication logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");  
            af.setAttribute("GroupCode");
            String rid = logic.createApplyNo();
            af.setRid(rid);
            // 获得BD的数据源
            af=getBd(af,firstOption);
            //获得SITE的数据源
            af=getSite(af,firstOption);
            //获得客户分类码的数据源
            af=getCustClass(af,firstOption);
            // 获得客户国别码的数据源
            af=getCustCountry(af,firstOption);
            //获得确认表中的客户编号
            af=getCustId(af,firstOption,request);
  
            request.setAttribute(Constant.VIEW_BEAN_KEY,af);
    }
    /**
     * 获得BD源
     * @param af
     * @param firstOption
     * @return
     */
    private AfCustomerApplication getBd(AfCustomerApplication af,SelectOptionImpl firstOption){
        IBdService logicBD = (IBdService) this.getBean("BdCodeLogic");
        List codeList = logicBD.listAllEabled();
        List bdList = new ArrayList();
        bdList.add(firstOption);
        SelectOptionImpl bdOption = null;
        for(int i=0;i<codeList.size();i++){
            Bd bd = (Bd) codeList.get(i);
            bdOption = new SelectOptionImpl(bd.getBdName(),bd.getBdCode(),bd.getBdName());
            bdList.add(bdOption);
        }
        af.setBelongBdList(bdList);
        return af;
    }
    /**
     * 获得SITE源
     * @param af
     * @param firstOption
     * @return
     */
    private AfCustomerApplication getSite(AfCustomerApplication af,SelectOptionImpl firstOption){
        ISiteService logicSite = (ISiteService) this.getBean("AreaCodeLogic");
        List codeListSite = logicSite.listAllEabled();
        List siteList = new ArrayList();
        siteList.add(firstOption);
        SelectOptionImpl bdOption1 = null;
        for(int i=0;i<codeListSite.size();i++){
            Site site = (Site) codeListSite.get(i);
            bdOption1 = new SelectOptionImpl(site.getSiteName(),site.getSiteCode(),site.getSiteName());
            siteList.add(bdOption1);
        }
        af.setBelongSiteList(siteList);
        return af;
    }
    /**
     * 获得客户国别码的数据源
     * @param af
     * @param firstOption
     * @return
     */
    private AfCustomerApplication getCustCountry(AfCustomerApplication af,SelectOptionImpl firstOption){
        IParameterService logicCustCountry = (IParameterService) this.getBean("ParameterLogic");
        List countryList = logicCustCountry.listAllByKindEnable("CountryCode");
        List custCountryList = new ArrayList();
        custCountryList.add(firstOption);
        SelectOptionImpl cOption = null;
        for(int i=0;i<countryList.size();i++){
            Parameter parameter = (Parameter) countryList.get(i);
            cOption = new SelectOptionImpl(parameter.getName(),parameter.getCompId().getCode(),parameter.getName());
            custCountryList.add(cOption);
        }
        af.setCustomerCountryList(custCountryList);
        return af;
    }
    
    /**
     * 获得客户分类码的数据源
     * @param af
     * @param firstOption
     * @return
     */
    private AfCustomerApplication getCustClass(AfCustomerApplication af,SelectOptionImpl firstOption){
        IParameterService logicCustClass = (IParameterService) this.getBean("ParameterLogic");
        List classList = logicCustClass.listAllByKindEnable("BusinessType");
        List custClassList = new ArrayList();
        custClassList.add(firstOption);
        SelectOptionImpl cOption = null;
        for(int i=0;i<classList.size();i++){
            Parameter parameter = (Parameter) classList.get(i);
            cOption = new SelectOptionImpl(parameter.getName(),parameter.getCompId().getCode(),parameter.getName());
            custClassList.add(cOption);
        }
        af.setCustomerClassList(custClassList);
        return af;
    }
    /**
     * 获得客户编号源
     * @param af
     * @param firstOption
     * @param request
     * @return
     */
    private AfCustomerApplication getCustId(AfCustomerApplication af,SelectOptionImpl firstOption,HttpServletRequest request){
        ICustomerService logicCust = (ICustomerService) this.getBean("CustomerLogic");
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userName=user.getUserName();
        List roleList = user.getRoles();
        List codeList = logicCust.listAll(userName, roleList);
        List custList = new ArrayList();
        SelectOptionImpl custOption = null;
        custList.add(firstOption);
        for(int i=0;i<codeList.size();i++){
            Customer customer = (Customer) codeList.get(i);           
            ICustomerApplication customerLogic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");
          if( customerLogic.loadByCustomerId("CustomerChangeApp",customer.getCustomerId()) == null){
              String cust=customer.getCustomerId()+"---"+customer.getShort_();
              custOption = new SelectOptionImpl(cust,customer.getCustomerId(),customer.getCustomerId());
              custList.add(custOption);
          }         
        }
        af.setCustomerIdList(custList);
        return af;
    }
}
