package server.essp.hrbase.site.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.site.service.ISiteService;
import server.framework.common.BusinessException;

public class AcDeleteSite extends AbstractESSPAction {

	/**
	 * 列出所有Site
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param data TransactionData
	 */
	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		String siteRid = request.getParameter("siteRid");
		if(siteRid == null || "".equals(siteRid)) {
			return;
		}
		ISiteService siteService = (ISiteService)this.getBean("siteService");
		siteService.deleteSite(new Long(siteRid));
	}

}
