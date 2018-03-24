package server.essp.projectpre.ui.project.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

/**
 * 添加area code的Action
 * 
 * @author Robin
 * 
 */
public class AcAddAreaCode extends AbstractESSPAction {

	/**
	 * 添加area code
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {

		// 如果有ActionForm传入的话，用此方法获得ActionFrom
		AfSite af = (AfSite) this.getForm();
		Site site = new Site();
		if (af.getSiteName() != null && !af.getSiteName().trim().equals("")) {
			site.setSiteName(af.getSiteName().trim());
		}
		if (af.getSiteCode() != null && !af.getSiteCode().trim().equals("")) {
			site.setSiteCode(af.getSiteCode().trim());
		}
        if (af.getManager() != null && !af.getManager().trim().equals("")) {
            site.setManager(af.getManager().trim());
        }
        site.setDescription(af.getDescription());
		site.setStatus(true);
		site.setSiteLoading(af.getSiteLoading());
		// 通过此方法以接口的形式得到一个服务的实例
		ISiteService logic = (ISiteService) this.getBean("AreaCodeLogic");

		logic.save(site);

		// 默认会转向ForwardId为sucess的页面
		// 如果需要自定义ForWardId，用下面的语句
		// data.getReturnInfo().setForwardID("ForwardId");
	}

}
