/*
 * Created on 2008-6-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.busyRate;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;
import client.essp.common.view.VWTDWorkArea;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.ThreadVariant;

public class VwBusyRateTest  extends VWTDWorkArea{


    public VwBusyRateTest() {
        super(300);
        try {
        } catch (Exception ex) {
        }
  
    }

    public static void main(String[] args) {
          DtoUser user = new DtoUser();
          user.setUserLoginId("WH0607015");
          List roles = new ArrayList();
          DtoRole role = new DtoRole();
          role.setRoleId(DtoRole.ROLE_SYSUSER);
          roles.add(role);
          role = new DtoRole();
          role.setRoleId(DtoRole.ROLE_UAP);
          roles.add(role);
          user.setRoles(roles);
          HttpServletRequest request = new HttpServletRequestMock();
          HttpSession session = request.getSession();
          session.setAttribute(DtoUser.SESSION_USER, user);

          ThreadVariant.getInstance().set(DtoUser.SESSION_USER, user);
          VwBusyRateGather frame = new VwBusyRateGather();
          Parameter para = new Parameter();

          frame.setParameter(para);
          frame.refreshWorkArea();
          TestPanel.show(frame);

   }
}
