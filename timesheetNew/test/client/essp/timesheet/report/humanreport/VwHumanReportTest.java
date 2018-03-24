package client.essp.timesheet.report.humanreport;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;

public class VwHumanReportTest {
	
	public static void main(String[] args) {
		VwHumanReport humanReport = new VwHumanReport();
		login("WH0607015");
		 Parameter para = new Parameter();
		 humanReport.setParameter(para);
	     
	     TestPanel.show(humanReport);
	}

	private static void login(String loginId) {
	      DtoUser user = new DtoUser();
	      user.setUserID(loginId);
	      user.setUserLoginId(loginId);
	      user.setDomain("wh");
	      user.setPassword("666");
	      List roles = new ArrayList();
	      DtoRole role = new DtoRole();
	      role.setRoleId(DtoRole.ROLE_UAP);
	      roles.add(role);
	      role = new DtoRole();
	      role.setRoleId(DtoRole.ROLE_HAP);
	      roles.add(role);
	      user.setRoles(roles);
	      HttpServletRequest request = new HttpServletRequestMock();
	      HttpSession session = request.getSession();
	      session.setAttribute(DtoUser.SESSION_USER, user);
	      session.setAttribute(DtoUser.SESSION_LOGIN_USER, user);
	  }
}
