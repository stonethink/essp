package client.essp.timesheet;

import java.awt.BorderLayout;

import javax.swing.UIManager;

import client.essp.common.view.UISetting;
import client.framework.view.vwcomp.VWJApplet;
import com.wits.util.Parameter;

public class WorkBenchApplet extends VWJApplet {
    /** PSP的主界面  */
    private VwWorkBench panelWorkBench;
    /**
     * 构造函数
     * @throws HeadlessException
     */
    public WorkBenchApplet() {
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
    private void jbInit() {
        panelWorkBench = new VwWorkBench();
        panelWorkBench.setParameter(new Parameter());
        panelWorkBench.refreshWorkArea();
        this.getContentPane().add(panelWorkBench, BorderLayout.CENTER);
    }

}
