package client.essp.timesheet.rmmaint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoUser;

public class VwRmMaintTest {
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
		VwRmMaint rmMaint = new VwRmMaint();
		Parameter para = new Parameter();
        login("WH0607015");
        rmMaint.setParameter(para);
        rmMaint.refreshWorkArea();
        TestPanel.show(rmMaint);
	}
}
