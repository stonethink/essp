package server.essp.projectpre.ui.customer.add;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.TransactionData;

public class AcInitialCustApp extends AbstractESSPAction {
    /**
     * 初始化客户申请页面，默认情况下选中集团码，状态为未提交
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        final SelectOptionImpl  firstOption = new SelectOptionImpl("--please select--","","--please select--");            
            AfCustomerApplication af = new AfCustomerApplication();
            ICustomerApplication logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");  
            af.setAttribute("GroupCode");
            af.setApplicationStatus("UnSubmit");
            af.setRid(logic.createApplyNo());
            af.setGroupId("");
            //获得BD的数据源
            af=getBd(af,firstOption);
            //获得SITE的数据源
            af=getSite(af,firstOption);
            //获得客户分类码的数据源
            af=getCustClass(af,firstOption);
            //获得客户国别码的数据源
            af=getCustCountry(af,firstOption);           
            request.setAttribute(Constant.VIEW_BEAN_KEY,af);
    }
    /**
     * 获得BD源
     * @param af
     * @param firstOption
     * @return AfCustomerApplication
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
     * 获得客户分类码的数据源
     * @param af
     * @param firstOption
     * @return AfCustomerApplication
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
     * 获得客户国别码的数据源
     * @param af
     * @param firstOption
     * @return AfCustomerApplication
     */
    private AfCustomerApplication getCustCountry(AfCustomerApplication af,SelectOptionImpl firstOption){
        IParameterService logicCustCou = (IParameterService) this.getBean("ParameterLogic");
        List countryList = logicCustCou.listAllByKindEnable("CountryCode");
        List countryClassList = new ArrayList();
        countryClassList.add(firstOption);
        SelectOptionImpl cOption1 = null;
        for(int i=0;i<countryList.size();i++){
            Parameter parameter = (Parameter) countryList.get(i);
            cOption1 = new SelectOptionImpl(parameter.getName(),parameter.getCompId().getCode(),parameter.getName());
            countryClassList.add(cOption1);
        }
        af.setCustomerCountryList(countryClassList);
        return af;
    }
    
}
