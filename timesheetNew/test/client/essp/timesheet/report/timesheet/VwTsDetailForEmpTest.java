package client.essp.timesheet.report.timesheet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import c2s.essp.common.user.DtoUser;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import junitpack.HttpServletRequestMock;

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
public class VwTsDetailForEmpTest {
    public static void main(String[] args) {
       VwTsDetailReportForEmp tsDetail = new VwTsDetailReportForEmp();
       Parameter para = new Parameter();
       tsDetail.setParameter(para);
       login("WH0607015");
       TestPanel.show(tsDetail);
   }
   private static void login(String loginId) {
      DtoUser user = new DtoUser();
      user.setUserID(loginId);
      user.setUserLoginId(loginId);
      HttpServletRequest request = new HttpServletRequestMock();
      HttpSession session = request.getSession();
      session.setAttribute(DtoUser.SESSION_USER, user);
      session.setAttribute(DtoUser.SESSION_LOGIN_USER, user);
  }

}
