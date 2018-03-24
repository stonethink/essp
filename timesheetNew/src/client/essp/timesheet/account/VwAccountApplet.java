package client.essp.timesheet.account;

import client.framework.view.vwcomp.VWJApplet;
import client.essp.common.view.UISetting;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import com.wits.util.Parameter;

public class VwAccountApplet extends VWJApplet {

    private VwAccount panelAccount;

    public VwAccountApplet() {
        try {
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
           UISetting.settingUIManager();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
    }
    /**
     * initUI
     *
     * @todo Implement this client.framework.view.vwcomp.VWJApplet method
     */
    public void initUI() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() {
        panelAccount = new VwAccount();
        panelAccount.setParameter(new Parameter());
        panelAccount.refreshWorkArea();
        this.getContentPane().add(panelAccount, BorderLayout.CENTER);
    }
}
