package server.essp.projectpre.ui.project.mailprivilege;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.MailPrivilege;
import server.essp.projectpre.service.mailprivilege.IMailPrivilegeService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcListUser extends AbstractESSPAction {

	/**
	 * 用户列表
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		IMailPrivilegeService logic = (IMailPrivilegeService) this
				.getBean("MailPrivilegeLogic");
		List userList = logic.listAllUser();
		List viewBeanList = new ArrayList();
		for (int i = 0; i < userList.size(); i++) {
			MailPrivilege mailP = (MailPrivilege) userList.get(i);
			VbMailPrivilege viewbean = new VbMailPrivilege();
			viewbean.setLoginId(mailP.getLoginId());
			viewbean.setLoginName(mailP.getLoginName());
			viewbean.setDomain(mailP.getDomain());
			viewBeanList.add(viewbean);
		}
		request.setAttribute(Constant.VIEW_BEAN_KEY, viewBeanList);
	}

}
