package client.essp.timesheet.report.timesheet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class VwTsDetailForPmTest {
    public static void main(String[] args) {
        login("WH0607015");
        VwTsDetailReportForPm tsDetail = new VwTsDetailReportForPm();
        Parameter para = new Parameter();
        tsDetail.setParameter(para);
        TestPanel.show(tsDetail);

    }
    private static void login(String loginId) {
     DtoUser user = new DtoUser();
     user.setUserID(loginId);
     user.setUserLoginId(loginId);
     List roles = new ArrayList();
     DtoRole role = new DtoRole();
     role.setRoleId(DtoRole.ROLE_SYSUSER);
     roles.add(role);
     role = new DtoRole();
     role.setRoleId(DtoRole.ROLE_EMP);
     roles.add(role);
     user.setRoles(roles);
     
     HttpServletRequest request = new HttpServletRequestMock();
     HttpSession session = request.getSession();
     session.setAttribute(DtoUser.SESSION_USER, user);
     session.setAttribute(DtoUser.SESSION_LOGIN_USER, user);
 }

}
