package client.essp.pwm.workbench;

import java.awt.*;
import javax.swing.*;
import client.essp.common.view.UISetting;
import client.framework.view.vwcomp.VWJApplet;
import com.wits.util.TestPanel;
import com.wits.util.Parameter;

/**
 * 嵌入网页中的Applet界面
 * <p>Title: </p>
 * <p>Description: </p>
 * @author
 * @version 1.0
 */

public class AppletWorkbench extends VWJApplet {

    /** PSP的主界面  */
	private VwWorkbench panelDailyReport;
    /**
     * 构造函数
     * @throws HeadlessException
     */
    public AppletWorkbench() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UISetting.settingUIManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化<code>AppletMain</code>,
     */
    public void initUI() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 界面的初始化
     * @throws java.lang.Exception
     */
    private void jbInit(){
        panelDailyReport = new VwWorkbench();
        panelDailyReport.setParameter(new Parameter());
        panelDailyReport.refreshWorkArea();
        this.getContentPane().add(panelDailyReport, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        AppletWorkbench applet = new AppletWorkbench();
        applet.setStandalone(true);
        applet.setParameter("CONTROLLER_URL","/Controller");
        applet.setParameter("todayDate","20051009");
        applet.setParameter("userId","stone.shi");
        applet.setParameter("userName","fuChen");
        applet.setSize(800,600);
        ((JComponent)applet.getContentPane() )
            .setPreferredSize(new Dimension(800,600));
        //Frame frame;
        //frame = new Frame();
        //frame.setTitle("AppletMain");

        //frame.add(applet, BorderLayout.CENTER);
        //frame.setSize(1000, 630);

        applet.init();
        applet.start();
        //Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        //frame.setLocation( (d.width - frame.getSize().width) / 2,
        //                  (d.height - frame.getSize().height) / 2);
        //frame.setVisible(true);
        //frame.pack();
        //frame.show();
        TestPanel.show(applet);
    }

}
