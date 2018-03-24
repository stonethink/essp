package server.essp.hrbase.site.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.site.form.AfSite;
import server.essp.hrbase.site.service.ISiteService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcLoadSite extends AbstractESSPAction {

	/**
	 * 列出所有Site
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param data TransactionData
	 */
	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		String siteRid = request.getParameter("siteRid");
		AfSite af;
		if(siteRid != null && "".equals(siteRid) == false) {
			ISiteService siteService = (ISiteService)this.getBean("siteService");
			af = siteService.loadSite(new Long(siteRid));
		} else {
			af = new AfSite();
		}
		request.setAttribute("webVo", af);
	
	}

}
