/**
 * 
 */
package server.essp.projectpre.ui.customer.query;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.bd.IBdService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import server.framework.taglib.util.SelectOptionImpl;


/**
 * @author bird
 *
 */
public class AcQueryData extends AbstractESSPAction{


	private List tmpList = null;
	private String defaultStr = "--please select--";
	private SelectOptionImpl defaultOption = new SelectOptionImpl(defaultStr, "");
	/**
     * ��ò�ѯҳ����BD�e,Site�e,�͑���a�Ϳ͑���e�a������Դ
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		
        VbQueryData view = new VbQueryData();

		//��ȡBd���м�¼��BD_name,BD_code��λ
		IBdService logicBd = (IBdService) this.getBean("BdCodeLogic");
		
		Bd bd = null;
		SelectOptionImpl bdOption = null;
		List bdList = new ArrayList();
		bdList.add(defaultOption);
		tmpList = logicBd.listAllEabled();
		
		for (int i=0;i<tmpList.size();i++) {
			bd = (Bd) tmpList.get(i);
			bdOption = new SelectOptionImpl(bd.getBdName(),bd.getBdCode());
			bdList.add(bdOption);			
		}
		
		view.setBdList(bdList);
		
		// ��ȡsite���м�¼��site_name,site_code��λ
		ISiteService logicSite = (ISiteService) this.getBean("AreaCodeLogic");

		Site site = null;
		SelectOptionImpl siteOption = null;
		List siteList = new ArrayList();
		siteList.add(defaultOption);
		tmpList = logicSite.listAllEabled();
		
		for (int i=0;i<tmpList.size();i++) {
			site = (Site) tmpList.get(i);
			siteOption = new SelectOptionImpl(site.getSiteName(), site.getSiteCode());
			siteList.add(siteOption);
		}
		
		view.setSiteList(siteList);
		
		//����kind��ȡ��Ӧ��code
		
		IParameterService logicParameter = (IParameterService) this.getBean("ParameterLogic");
		
        //�ͻ������
		List parameterClassList = new ArrayList();
		parameterClassList.add(defaultOption);
		tmpList = logicParameter.listAllByKindEnable(Constant.BUSINESS_TYPE);
		
		for (int i=0;i<tmpList.size();i++) {
            String customerClass = ((Parameter)tmpList.get(i)).getCompId().getCode();
            String className = ((Parameter)tmpList.get(i)).getName();
            parameterClassList.add(new SelectOptionImpl(className,customerClass));
            
		}
		
		view.setParameterClassList(parameterClassList);
		
		//�ͻ�������
		List parameterCountryList = new ArrayList();
		parameterCountryList.add(defaultOption);
		tmpList = logicParameter.listAllByKindEnable(Constant.COUNTRY_CODE);
				
		for (int i=0;i<tmpList.size();i++) {
			String customerCountry = ((Parameter)tmpList.get(i)).getCompId().getCode();
            String countryName = ((Parameter)tmpList.get(i)).getName();
			parameterCountryList.add(new SelectOptionImpl(countryName,customerCountry));
		}

		view.setParameterCountryList(parameterCountryList);
		// ����Ҫ���ص����ݷŵ�Request�У�����ҵ����������ݲ�����ŵ�Session��
		request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY, view);

	}
}
