package client.essp.timesheet.report.utilizationRate.gather;


import java.util.ArrayList;
import java.util.List;

import com.wits.util.Parameter;
import client.essp.common.view.VWTDWorkArea;
import client.essp.timesheet.report.attendance.VwAttendance;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;
import com.wits.util.TestPanel;
import junitpack.HttpServletRequestMock;
import com.wits.util.ThreadVariant;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwUtilRateGatherTest extends VWTDWorkArea{


    public VwUtilRateGatherTest() {
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
          role.setRoleId(DtoRole.ROLE_EMP);
          roles.add(role);
          role = new DtoRole();
          role.setRoleId(DtoRole.ROLE_SYSUSER);
          roles.add(role);
          user.setRoles(roles);
          HttpServletRequest request = new HttpServletRequestMock();
          HttpSession session = request.getSession();
          session.setAttribute(DtoUser.SESSION_USER, user);

          ThreadVariant.getInstance().set(DtoUser.SESSION_USER, user);
          VwUtilRateGather frame = new VwUtilRateGather();
//          VwAttendance frame = new VwAttendance();
          Parameter para = new Parameter();

          frame.setParameter(para);
          frame.refreshWorkArea();
          TestPanel.show(frame);

   }
}
