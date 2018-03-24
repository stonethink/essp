package server.essp.projectpre.ui.customer.change;

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
import server.essp.projectpre.ui.customer.add.AfCustomerApplication;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;

public class AcDisplayCustApp extends AbstractESSPAction {
    private final static String rid="rid";

    /**
     * ��ʾ��Ӧ���ݵ���ϸ��Ϣ
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
        final SelectOptionImpl  firstOption = new SelectOptionImpl("--please select--","","--please select--");  
            Long code=null;
            if (request.getParameter(rid) != null) {
                code =Long.valueOf(request.getParameter(rid)) ;
            } 
            String IDFORMATER = "00000000";
            CustomerApplication customerApplication=new CustomerApplication();
            AfCustomerApplication af = new AfCustomerApplication();
            // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
            ICustomerApplication logic = (ICustomerApplication) this
                    .getBean("CustomerApplicationLogic");         
            customerApplication=logic.loadByRid(code);
            try {
                DtoUtil.copyProperties(af, customerApplication);
            } catch (Exception e) {           
                e.printStackTrace();
            }
            //�ñ�־λ�����Ʊ�ע������ʾ���
            if(customerApplication.getDescription()!=null&&!customerApplication.getDescription().equals("") ){
                request.setAttribute("Desc","true");
            }else{
                request.setAttribute("Desc","false");
            }
            //���ݱ�־λ�����Ƽ��Ŵ��ŵ���ʾ���
            if(customerApplication.getAttribute().equals("Company") ){
                request.setAttribute("company","true");
            }else{
                request.setAttribute("company","false");
            }
            //����ѡ��ļ��Ŵ��Ž���Ӧ����λ��ʾ����
            if( customerApplication.getGroupId()!=null){
                Customer customer=new Customer();
                ICustomerService logicCust = (ICustomerService) this.getBean("CustomerLogic");
                customer=logicCust.loadByCustomerId(customerApplication.getGroupId());
                af.setCreateDate(comDate.dateToString(customer.getCreateDate(), "yyyy-MM-dd"));
                af.setAlterDate(comDate.dateToString(customer.getAlterDate(), "yyyy-MM-dd"));
                af.setCreator(customer.getCreator());                    
                String  cust=customer.getCustomerId()+"---"+customer.getShort_();
                af.setGroupId(cust);

            }

            //���BD������Դ
            af=getBd(af,firstOption);
            //���SITE������Դ
            af=getSite(af,firstOption);
            //��ÿͻ������������Դ
            af=getCustClass(af,firstOption);  
            //   ��ÿͻ������������Դ
            af=getCustCountry(af,firstOption);
            //��ʽ�����뵥�� 
            DecimalFormat df = new DecimalFormat(IDFORMATER);
            String newRid = df.format(customerApplication.getRid(), new StringBuffer(),
                                    new FieldPosition(0)).toString();
            af.setRid(newRid);
            request.setAttribute(Constant.VIEW_BEAN_KEY,af);
    }
    /**
     * ���BDԴ
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
     * ���SITEԴ
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
     * ��ÿͻ������������Դ
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
     * ��ÿͻ������������Դ
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
}

