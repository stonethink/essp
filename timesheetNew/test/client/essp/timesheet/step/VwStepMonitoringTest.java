/*
 * Created on 2008-6-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.step;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoUser;
import client.essp.common.view.VWTDWorkArea;
import client.essp.timesheet.step.monitoring.VwMonitoring;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.ThreadVariant;

public class VwStepMonitoringTest extends VWTDWorkArea{

    public VwStepMonitoringTest()  {
        super(300);
    }
      

 
    public static void main(String[] args) {
       DtoUser user = new DtoUser();
       user.setUserLoginId("WH0607014");
       user.setPassword("/*-+");
       user.setDomain("wh");
       HttpServletRequest request = new HttpServletRequestMock();
       HttpSession session = request.getSession();
       session.setAttribute(DtoUser.SESSION_USER, user);
       session.setAttribute(DtoUser.SESSION_LOGIN_USER, user);
       ThreadVariant.getInstance().set(DtoUser.SESSION_USER, user);
       VwMonitoring frame = new VwMonitoring();
       Parameter para = new Parameter();
       frame.setParameter(para);
       frame.refreshWorkArea();
       TestPanel.show(frame);
    }
}

