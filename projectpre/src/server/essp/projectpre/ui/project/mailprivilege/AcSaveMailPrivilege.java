package server.essp.projectpre.ui.project.mailprivilege;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.common.ldap.LDAPUtil;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.db.MailPrivilege;
import server.essp.projectpre.service.mailprivilege.IMailPrivilegeService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;

public class AcSaveMailPrivilege extends AbstractESSPAction {

	/**
	 * 更新用户信息
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		AfMailPrivilege af = (AfMailPrivilege) this.getForm();
		IMailPrivilegeService logic = (IMailPrivilegeService) this
				.getBean("MailPrivilegeLogic");
		MailPrivilege mailp = new MailPrivilege();
		if (af != null) {
			mailp.setLoginId(af.getLoginId());
            LDAPUtil ldapUtil = new LDAPUtil();
            DtoUser dtoUser = ldapUtil.findUser(af.getLoginId());
            String userName=dtoUser.getUserName();
			mailp.setLoginName(userName);
			mailp.setDomain(af.getDomain());
			mailp.setAddAudit(change2Bool(af.getAddAudit()));
			mailp.setAddInform(change2Bool(af.getAddInform()));
			mailp.setChangeAudit(change2Bool(af.getChangeAudit()));
			mailp.setChangeInform(change2Bool(af.getChangeInform()));
			mailp.setCloseAudit(change2Bool(af.getCloseAudit()));
			mailp.setCloseInform(change2Bool(af.getCloseInform()));
			mailp.setDataScope((af.getDataScope()));
		}
		if (("").equals(af.getRid()))
			logic.add(mailp);
		else {
			mailp.setRid(Long.parseLong(af.getRid()));
			logic.update(mailp);
		}
	}

	private Boolean change2Bool(String i) {
		if (null != i && ("1").equals(i)) {
			return true;
		}
		return false;
	}

}
