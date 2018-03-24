package server.essp.projectpre.ui.customer.change;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.comDate;
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

public class AcSelectCustId extends AbstractESSPAction {
    /**
     *����ѡ��Ŀͻ���Ž���˿ͻ���Ŷ�Ӧ��������¼�������Ϣ��ʾ��ҳ����
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {    
        final SelectOptionImpl  firstOption = new SelectOptionImpl("--please select--","","--please select--");  
            AfCustomerApplication af =  (AfCustomerApplication)this.getForm();
            // ͨ���˷����Խӿڵ���ʽ�õ�һ�������ʵ��
            ICustomerService logic = (ICustomerService) this
                    .getBean("CustomerLogic");
            Customer customer = new Customer();
            if(!af.getCustomerId().equals("--please select--")){
                customer = logic.loadByCustomerId(af.getCustomerId());
                AfCustomerApplication newAf = new AfCustomerApplication();
                newAf.setRid(af.getRid());
                newAf.setApplicantName(af.getApplicantName());
                if(customer.getGroupId()!=null){
                newAf.setGroupId(customer.getGroupId());
                }
                newAf.setCustomerShort(customer.getShort_());
                newAf.setRegId(customer.getRegId());
                newAf.setCustomerNameCn(customer.getNameCn());
                newAf.setCustomerNameEn(customer.getNameEn());
                newAf.setBelongBd(customer.getBelongBd());
                newAf.setBelongSite(customer.getBelongSite());
                newAf.setCustomerClass(customer.getClass_());
                newAf.setCustomerCountry(customer.getCountry());
                newAf.setCustDescription(customer.getCustDescription());
                newAf.setCustomerId(af.getCustomerId());
                newAf.setAttribute(customer.getAttribute());
                //���ݼ��Ŵ��Ž���Ӧ����λ��ʾ����
                if( customer.getGroupId()!=null){
                    Customer customerG = new Customer();
                    customerG=logic.loadByCustomerId(customer.getGroupId());
                    newAf.setCreateDate(comDate.dateToString(customerG.getCreateDate(), "yyyy-MM-dd"));
                    newAf.setAlterDate(comDate.dateToString(customerG.getAlterDate(), "yyyy-MM-dd"));
                    newAf.setCreator(customerG.getCreator());                    
                    String  cust=customer.getCustomerId()+"---"+customer.getShort_();
                    newAf.setGroupId(cust);
                }
                //���BD������Դ
                newAf=getBd(newAf,firstOption);
                //���SITE������Դ
                newAf=getSite(newAf,firstOption);
                //��ÿͻ������������Դ
                newAf=getCustClass(newAf,firstOption);
                //��ÿͻ������������Դ
                newAf=getCustCountry(newAf,firstOption);
                //���ȷ�ϱ��еĿͻ����
                newAf=getCustId(newAf,firstOption,request);         
                newAf.setApplicationStatus("Confirmed");
                //���ݱ�־λ�����Ƽ��Ŵ�����ʾ���
                if(customer.getAttribute().equals("Company") ){
                    request.setAttribute("company","true");
                }else{
                    request.setAttribute("company","false");
                }
                request.setAttribute(Constant.VIEW_BEAN_KEY,newAf);                            
            }
    }
    /**
     * ���BDԴ
     * @param af
     * @param firstOption
     * @return
     */
    private AfCustomerApplication getBd(AfCustomerApplication newAf,SelectOptionImpl firstOption){
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
        newAf.setBelongBdList(bdList);
        return newAf;
    }
    /**
     * ���SITEԴ
     * @param af
     * @param firstOption
     * @return
     */
    private AfCustomerApplication getSite(AfCustomerApplication newAf,SelectOptionImpl firstOption){
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
        newAf.setBelongSiteList(siteList);
        return newAf;
    }
    /**
     * ��ÿͻ������������Դ
     * @param af
     * @param firstOption
     * @return
     */
    private AfCustomerApplication getCustClass(AfCustomerApplication newAf,SelectOptionImpl firstOption){
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
        newAf.setCustomerClassList(custClassList);
        return newAf;
    }
    
    /**
     * ��ÿͻ������������Դ
     * @param af
     * @param firstOption
     * @return
     */
    private AfCustomerApplication getCustCountry(AfCustomerApplication newAf,SelectOptionImpl firstOption){
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
        newAf.setCustomerCountryList(custCountryList);
        return newAf;
    }
    /**
     * ��ÿͻ����Դ
     * @param af
     * @param firstOption
     * @param request
     * @return
     */
    private AfCustomerApplication getCustId(AfCustomerApplication newAf,SelectOptionImpl firstOption,HttpServletRequest request){
        ICustomerService logicCust = (ICustomerService) this.getBean("CustomerLogic");
        DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        String userName=user.getUserName();
        List roleList = user.getRoles();
        List codeList = logicCust.listAll(userName,roleList);
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
        newAf.setCustomerIdList(custList);
        return newAf;
    }
}

