package server.essp.hrbase.site.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.site.form.AfSite;
import server.essp.hrbase.site.service.ISiteService;
import server.framework.common.BusinessException;

public class AcSiteList extends AbstractESSPAction {

	/**
	 * 列出所有Site
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param data TransactionData
	 */
	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		ISiteService siteService = (ISiteService)this.getBean("siteService");
		List siteList = siteService.listSites();
		request.setAttribute("webVo", siteList);
	}

}
