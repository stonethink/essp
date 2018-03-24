package server.essp.projectpre.ui.project.mailprivilege;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.MailPrivilege;
import server.essp.projectpre.db.Site;
import server.essp.projectpre.service.mailprivilege.IMailPrivilegeService;
import server.essp.projectpre.service.site.ISiteService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;

public class AcPreviewMailPrivilege extends AbstractESSPAction {

	/**
	 * 获取用户所有权限记录
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		String code = null;
		if (request.getParameter("loginId") != null
				&& !request.getParameter("loginId").equals("")) {
			code = request.getParameter("loginId");
		}
		// 获取用户的所有权限信息
		IMailPrivilegeService logic = (IMailPrivilegeService) this
				.getBean("MailPrivilegeLogic");
		MailPrivilege mailp = logic.loadByLoginid(code);

		// 获取所有的site列表
		ISiteService siteLogic = (ISiteService) this.getBean("AreaCodeLogic");
		List eableSite = siteLogic.listAllEabled();

		// 创建输出视图
		AfMailPrivilege viewBean = new AfMailPrivilege();

		if (mailp != null) {
			viewBean.setLoginId(mailp.getLoginId());
			viewBean.setLoginName(mailp.getLoginName());
			viewBean.setRid(mailp.getRid()+"");
			viewBean.setDomain(mailp.getDomain());
			viewBean.setAddAudit(changeBool(mailp.getAddAudit()));
			viewBean.setAddInform(changeBool(mailp.getAddInform()));
			viewBean.setChangeAudit(changeBool(mailp.getChangeAudit()));
			viewBean.setChangeInform(changeBool(mailp.getChangeInform()));
			viewBean.setCloseAudit(changeBool(mailp.getCloseAudit()));
			viewBean.setCloseInform(changeBool(mailp.getCloseInform()));
			List scopeList = getScopeList(eableSite, mailp.getDataScope());
			viewBean.setDataScopeList(scopeList);
		}else{
			viewBean.setLoginId(code);
			viewBean.setLoginName(request.getParameter("loginName"));
			viewBean.setRid("");
			viewBean.setDomain(request.getParameter("domain"));
			viewBean.setAddAudit("0");
			viewBean.setAddInform("0");
			viewBean.setChangeAudit("0");
			viewBean.setChangeInform("0");
			viewBean.setCloseAudit("0");
			viewBean.setCloseInform("0");			
			List scopeList = getScopeList(eableSite,null);
			viewBean.setDataScopeList(scopeList);
		}

		request.setAttribute(Constant.VIEW_BEAN_KEY, viewBean);
	}

	private List getScopeList(List eableSite, String scopData) {
		List<VbScopeData> scopeList = new ArrayList<VbScopeData>();
		if(scopData==null)
			scopData="";	
		for (Iterator iter = eableSite.iterator(); iter.hasNext();) {
			Site date = (Site) iter.next();
			String site = date.getSiteName();
			VbScopeData viewScop = new VbScopeData();
			if (scopData.indexOf(site) != -1)				
				viewScop.setStatus("checked");
			viewScop.setSiteName(site);
			scopeList.add(viewScop);
		}		
		return scopeList;
	}

	private String changeBool(boolean i) {
		if (i) {
			return "1";
		} else
			return "0";
	}

}
