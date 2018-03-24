package server.essp.projectpre.ui.project.mailprivilege;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.mailprivilege.IMailPrivilegeService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcDeleteMailPrivilege extends AbstractESSPAction {

	/**
	 * 删除选中用户
	 * @throws Exception 
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		String code = null;
		if (request.getParameter("LoginId") != null
				&& !request.getParameter("LoginId").equals("")) {
			code = request.getParameter("LoginId");
		}
		IMailPrivilegeService logic = (IMailPrivilegeService) this
				.getBean("MailPrivilegeLogic");
		logic.deleteByLoginid(code);

	}

}
