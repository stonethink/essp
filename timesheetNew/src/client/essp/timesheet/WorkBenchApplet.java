package client.essp.timesheet;

import java.awt.BorderLayout;

import javax.swing.UIManager;

import client.essp.common.view.UISetting;
import client.framework.view.vwcomp.VWJApplet;
import com.wits.util.Parameter;

public class WorkBenchApplet extends VWJApplet {
    /** PSP��������  */
    private VwWorkBench panelWorkBench;
    /**
     * ���캯��
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
     * ��ʼ��<code>AppletMain</code>,
     */
    public void initUI() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ����ĳ�ʼ��
     * @throws java.lang.Exception
     */
    private void jbInit() {
        panelWorkBench = new VwWorkBench();
        panelWorkBench.setParameter(new Parameter());
        panelWorkBench.refreshWorkArea();
        this.getContentPane().add(panelWorkBench, BorderLayout.CENTER);
    }

}
