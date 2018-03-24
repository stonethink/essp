package client.essp.timesheet.period;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoUser;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwTsDatesTest {
	private static void login(String loginId) {
	       DtoUser user = new DtoUser();
	       user.setUserID(loginId);
	       user.setUserLoginId(loginId);
	       HttpServletRequest request = new HttpServletRequestMock();
	       HttpSession session = request.getSession();
	       session.setAttribute(DtoUser.SESSION_USER, user);
	       session.setAttribute(DtoUser.SESSION_LOGIN_USER, user);
	   }
	public static void main(String[] args) {
		login("WH0607015");
		VwTsDates vwTsDates = new VwTsDates();
		Parameter param = new Parameter();
		vwTsDates.setParameter(param);
		TestPanel.show(vwTsDates);
	}
	
}
