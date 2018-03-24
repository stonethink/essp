package client.essp.tc.hrmanager;


import com.wits.util.TestPanel;
import client.essp.common.view.VWWorkArea;
import com.wits.util.Parameter;
import c2s.essp.common.user.DtoUser;
import javax.servlet.http.HttpServletRequest;
import junitpack.HttpServletRequestMock;
import javax.servlet.http.HttpSession;
import client.framework.view.vwcomp.VWJText;
import java.awt.Dimension;
import client.framework.view.vwcomp.VWJButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.framework.common.Global;
import client.essp.tc.hrmanager.overtime.VwOverTimeList;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import java.util.Calendar;
import client.framework.view.vwcomp.VWJDate;
import client.essp.tc.hrmanager.leave.VwLeaveList;

public class TestTCPanel extends VWWorkArea implements ActionListener{

    VWWorkArea userPanel = new VWWorkArea();
    VWJText userName = new VWJText();
    VWJButton loginBtn = new VWJButton("login");
    VWJDate begineDate = new VWJDate();
    VWJDate endDate = new VWJDate();
    VwOverTimeList overTime = new VwOverTimeList();
    VwLeaveList leave = new VwLeaveList();
    static{
        Global.todayDateStr = "2005-12-03";
        Global.todayDatePattern = "yyyy-MM-dd";
        DtoUser user = new DtoUser();
        user.setUserID("hua.xiao");
        user.setUserLoginId("hua.xiao");
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);
    }

    public TestTCPanel() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        userName.setMaxByteLength(20);
        userName.setInput2ByteOk(true);
        userPanel.setLayout(null);
        userPanel.setPreferredSize(new Dimension(400, 300));
        userPanel.add(userName);
        userPanel.add(loginBtn);
        userPanel.add(begineDate);
        userPanel.add(endDate);
        userName.setBounds(0, 0, 100, 20);
        userName.setText("stone.shi");
        loginBtn.setBounds(110, 0, 60, 20);
        begineDate.setBounds(110, 30, 60, 20);
        begineDate.setCanSelect(true);
        endDate.setBounds(110, 60, 60, 20);
        endDate.setCanSelect(true);
        loginBtn.addActionListener(this);

        Parameter param = new Parameter();
        param.put(DtoTcKey.USER_ID, "hua.xiao");
        Calendar begin = Calendar.getInstance();
        begin.set(2006,0,1);
        Calendar end = Calendar.getInstance();
        end.set(2006,0,8);
        param.put(DtoTcKey.BEGIN_PERIOD, begin.getTime());
        param.put(DtoTcKey.END_PERIOD, end.getTime());
        overTime.setParameter(param);
        leave.setParameter(param);
//        ebs.setParameter(param2);
//        account.setParameter(param);
////        wbsPanel.setParameter(param);
////        pbs.setParameter(param);
//        activityPanel.setParameter(param);
//        code.setParameter(param);
//        wp.setParameter(param);
//        vwWorkbench.setParameter(param);
        this.addTab("User",userPanel);
        this.addTab("overTime", overTime);
        this.addTab("leave", leave);
//        this.addTab("EBS", ebs);
//        this.addTab("ACCOUNT", account);
////        this.addTab("WBS", wbsPanel);
////        this.addTab("PBS", pbsPanel);
//        this.addTab("ACTIVITY", activityPanel);
////        this.addTab("PBS", pbs);
//        this.addTab("Code", code);
//        this.addTab("vwWorkbench", vwWorkbench);
    }

    public void actionPerformed(ActionEvent e) {
        DtoUser user = new DtoUser();
        user.setUserID(userName.getText());
        user.setUserLoginId(userName.getText());
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);

        Parameter param = new Parameter();
        param.put(DtoTcKey.USER_ID, userName.getText());
        param.put(DtoTcKey.BEGIN_PERIOD, begineDate.getUICValue());
        param.put(DtoTcKey.END_PERIOD, endDate.getUICValue());
        overTime.setParameter(param);
        leave.setParameter(param);
//        account.setParameter(param);
////        wbsPanel.setParameter(param);
//        activityPanel.setParameter(param);
////        pbsPanel.setParameter(param);
//        code.setParameter(param);
//        wp.setParameter(param);
//        vwWorkbench.setParameter(param);
    }

    public static void main(String[] args) {
        TestPanel.show(new TestTCPanel());
    }
}
