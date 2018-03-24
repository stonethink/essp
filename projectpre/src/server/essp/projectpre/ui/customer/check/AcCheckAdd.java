package server.essp.projectpre.ui.customer.check;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.comDate;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.CustomerApplication;
import server.essp.projectpre.db.IdSetting;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.customerapplication.ICustomerApplication;
import server.essp.projectpre.service.idsetting.IIdSettingService;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.essp.projectpre.ui.customer.add.AfCustomerApplication;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.DtoUtil;
import c2s.dto.TransactionData;
public class AcCheckAdd extends AbstractESSPAction {
    private final static String rid="rid";

    /**
     * 显示符合客户新增申请的详细信息
     */
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
            Long code=null;
            if (request.getParameter(rid) != null) {
                code =Long.valueOf(request.getParameter(rid)) ;
            }       
            //将编码生成方式传到前台
            String kind = server.essp.projectpre.service.constant.Constant.CLIENT_CODE;
            IdSetting idSetting=new IdSetting();
            IIdSettingService logicSet = (IIdSettingService) this.getBean("IdSettingLogic");       
            idSetting=logicSet.loadByKey(kind);
            String codingGenerate=idSetting.getCodingGenerate();
            request.setAttribute("codingGenerate",codingGenerate);
            
            String IDFORMATER = "00000000";
            CustomerApplication customerApplication=new CustomerApplication();
            AfCustomerApplication af = new AfCustomerApplication();
            // 通过此方法以接口的形式得到一个服务的实例
            ICustomerApplication logic = (ICustomerApplication) this.getBean("CustomerApplicationLogic");                           
            customerApplication=logic.loadByRid(code);
            try {
                DtoUtil.copyProperties(af, customerApplication);
            } catch (Exception e) {            
                e.printStackTrace();
            }
            IBdService bdLogic = (IBdService)this.getBean("BdCodeLogic");
            Bd bd = bdLogic.loadByBdCode(customerApplication.getBelongBd());
            if(bd!=null){
             af.setBelongBd(bd.getBdName());
            } else {
                af.setBelongBd("");
            }
            
            ISiteService siteLogic = (ISiteService)this.getBean("AreaCodeLogic");
            Site site = siteLogic.loadByCode(customerApplication.getBelongSite());
            if(site!=null){
            af.setBelongSite(site.getSiteName());
            } else {
                af.setBelongSite("");
            }
            
            IParameterService logicCustClass = (IParameterService) this.getBean("ParameterLogic");
            Parameter parameter = logicCustClass.loadByKindCode("BusinessType",customerApplication.getCustomerClass());
            if(parameter!=null){
            af.setCustomerClass(parameter.getName());
            } else {
                af.setCustomerClass("");
            }
            
            IParameterService logicCustCou = (IParameterService) this.getBean("ParameterLogic");
            Parameter parameterCou = logicCustCou.loadByKindCode("CountryCode",customerApplication.getCustomerCountry());
            if(parameterCou!=null){
            af.setCustomerCountry(parameterCou.getName());
            } else {
                af.setCustomerCountry("");
            }
            
            af.setDescription("");   
            if(!customerApplication.getAttribute().equals("Group")){
                request.setAttribute("groupNo","true");
            }else{
                request.setAttribute("groupNo","false");
            }        
      //格式化申请单号
            DecimalFormat df = new DecimalFormat(IDFORMATER);
            String newRid = df.format(customerApplication.getRid(), new StringBuffer(),
                                    new FieldPosition(0)).toString();
            af.setRid(newRid);
      //格式化日期
            af.setApplicationDate(comDate.dateToString(customerApplication.getApplicationDate(),"yyyy-MM-dd"));
            request.setAttribute(Constant.VIEW_BEAN_KEY,af);
      
    }
}


