package client.essp.tc;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import c2s.essp.common.user.DtoUser;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.dmView.byAcnt.VwDmViewByAcnt;
import client.essp.tc.dmView.byWorker.VwDmViewByWorker;
import client.essp.tc.hrmanager.VwHrManage;
import client.essp.tc.pmmanage.VwPmManage;
import client.essp.tc.search.VwTcSearchByPm;
import client.essp.tc.search.VwTcSearchByDm;
import client.essp.tc.weeklyreport.VwWeeklyReport;
import client.framework.common.Global;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import junitpack.HttpServletRequestMock;
import c2s.essp.pwm.workbench.DtoKey;
//import client.essp.pwm.workbench.VwWorkbench;
import client.essp.pms.account.VwAccount;
import client.essp.pms.activity.VwActivity;

public class TestTc2 extends VWWorkArea implements ActionListener {
//    VwPmManage vwPmManage = new VwPmManage();
//    VwDmViewByWorker vwDmViewByWorker = new VwDmViewByWorker();
//    VwDmViewByAcnt vwDmViewByAcnt = new VwDmViewByAcnt();
//    VwWeeklyReport vwWeeklyReport = new VwWeeklyReport();
//    VwTcSearchByDm vwTcSearchByDm = new VwTcSearchByDm();
//    VwTcSearchByPm vwTcSearchByPm = new VwTcSearchByPm();
    VwHrManage vwHrManage = new VwHrManage();
//    VwWorkbench vwWorkbench = new VwWorkbench();
//    VwAccount account = new VwAccount();
//    VwActivity activityPanel = new VwActivity();
    VwPmManage vwPmManage = new VwPmManage();
    VwDmViewByWorker vwDmViewByWorker = new VwDmViewByWorker();
    VwDmViewByAcnt vwDmViewByAcnt = new VwDmViewByAcnt();
    VwWeeklyReport vwWeeklyReport = new VwWeeklyReport();
    VwTcSearchByDm vwTcSearchByDm = new VwTcSearchByDm();
    VwTcSearchByPm vwTcSearchByPm = new VwTcSearchByPm();
//    VwHrManage vwHrManage = new VwHrManage();
//    VwWorkbench vwWorkbench = new VwWorkbench();
//    VwAccount account = new VwAccount();
//    VwActivity activityPanel = new VwActivity();

    VWWorkArea userPanel = new VWWorkArea();
    VWJText userName = new VWJText();
    VWJDate beginPeriod = new VWJDate();
    VWJDate endPeriod = new VWJDate();
    VWJButton loginBtn = new VWJButton("login");

    static {
        Global.todayDateStr = "2005-12-03";
        Global.todayDatePattern = "yyyy-MM-dd";
//        Global.userId = "aidong.huang";
        Global.userId = "WH0409001";
        DtoUser user = new DtoUser();
        user.setUserLoginId(Global.userId);
        user.setOrganization("Dept 4");
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);
    }

    public TestTc2() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        userPanel.setLayout(null);
        userPanel.setPreferredSize(new Dimension(400, 300));

        userName.setMaxByteLength(20);
        userName.setInput2ByteOk(true);
        userName.setBounds(0, 0, 100, 20);
        userName.setText(Global.userId);

        loginBtn.setBounds(110, 0, 60, 20);
        loginBtn.addActionListener(this);

        beginPeriod.setBounds(10, 100,100,20);
        beginPeriod.setCanSelect(true);
        beginPeriod.setValueText("20051203");
        endPeriod.setBounds(10, 220,100,20);
        endPeriod.setCanSelect(true);
        endPeriod.setValueText("20051209");

        userPanel.add(userName);
        userPanel.add(loginBtn);
        userPanel.add(beginPeriod);
        userPanel.add(endPeriod);

        this.addTab("Login", userPanel);
//        this.addTab("Weekly Report", vwWeeklyReport);
//        this.addTab("vwPmManage", vwPmManage);
//        this.addTab("account", account);
//        this.addTab("activityPanel", activityPanel);
//        this.addTab("vwDmViewByWorker", vwDmViewByWorker);
//        this.addTab("vwDmViewByAcnt", vwDmViewByAcnt);
//        this.addTab("vwTcSearchByDm", vwTcSearchByDm);
//        this.addTab("vwTcSearchByPm", vwTcSearchByPm);
        this.addTab("vwHrManage", vwHrManage);
//        this.addTab("vwWorkbench", vwWorkbench);
        this.addTab("Weekly Report", vwWeeklyReport);
        this.addTab("vwPmManage", vwPmManage);
//        this.addTab("account", account);
//        this.addTab("activityPanel", activityPanel);
        this.addTab("vwDmViewByWorker", vwDmViewByWorker);
        this.addTab("vwDmViewByAcnt", vwDmViewByAcnt);
        this.addTab("vwTcSearchByDm", vwTcSearchByDm);
        this.addTab("vwTcSearchByPm", vwTcSearchByPm);
        this.addTab("vwHrManage", vwHrManage);
//        this.addTab("vwWorkbench", vwWorkbench);
    }

    public void actionPerformed(ActionEvent e) {
        DtoUser user = new DtoUser();
        Global.userId = userName.getText();
        user.setUserLoginId(Global.userId);
        user.setOrganization("Dept 4");
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);

        Parameter param = new Parameter();
        param.put(DtoKey.SELECTED_DATE, (Date)beginPeriod.getUICValue());
        param.put(DtoTcKey.END_PERIOD, (Date)endPeriod.getUICValue());
        vwWeeklyReport.setParameter(param);

//        vwPmManage.setParameter(new Parameter());
//        vwPmManage.refreshWorkArea();
//        vwTcSearchByPm.refreshWorkArea();
//        vwTcSearchByDm.refreshWorkArea();
//        vwDmViewByWorker.refreshWorkArea();
//        vwDmViewByAcnt.refreshWorkArea();
        vwHrManage.refreshWorkArea();
        vwHrManage.vwPeriod.reInitUI();
//        vwWorkbench.setParameter(new Parameter());
//        vwWorkbench.refreshWorkArea();
        vwPmManage.setParameter(new Parameter());
        vwPmManage.refreshWorkArea();
        vwTcSearchByPm.refreshWorkArea();
        vwTcSearchByDm.refreshWorkArea();
        vwDmViewByWorker.refreshWorkArea();
        vwDmViewByAcnt.refreshWorkArea();
        vwHrManage.refreshWorkArea();
        vwHrManage.vwPeriod.reInitUI();
//        vwWorkbench.setParameter(new Parameter());
//        vwWorkbench.refreshWorkArea();
    }

    public static void main(String[] args) {
        TestPanel.show(new TestTc2());
    }
}
