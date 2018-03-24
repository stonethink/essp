/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.salaryApportion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoUser;
import client.essp.common.view.VWTDWorkArea;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.ThreadVariant;

public class VwSalaryApportionTest  extends VWTDWorkArea{


    public VwSalaryApportionTest() {
        super(300);
        try {
        } catch (Exception ex) {
        }
  
    }

    public static void main(String[] args) {
          DtoUser user = new DtoUser();
          user.setUserLoginId("WH0607014");
          HttpServletRequest request = new HttpServletRequestMock();
          HttpSession session = request.getSession();
          session.setAttribute(DtoUser.SESSION_USER, user);

          ThreadVariant.getInstance().set(DtoUser.SESSION_USER, user);
          VwSalaryApportion frame = new VwSalaryApportion();
          Parameter para = new Parameter();

          frame.setParameter(para);
          frame.refreshWorkArea();
          TestPanel.show(frame);

   }
}
