package client.essp.common.security.role;

import java.awt.BorderLayout;

import client.essp.common.view.UISetting;
import client.framework.view.vwcomp.VWJApplet;
import com.wits.util.Parameter;

public class VwRoleMenuApplet extends VWJApplet {

    private VwRoleMenu vwRoleMenu;

    /**
     * initUI
     *
     * @todo Implement this client.framework.view.vwcomp.VWJApplet method
     */
    public void initUI() {
        try {
            UISetting.settingUIManager();
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 初始化界面
     */
    private void jbInit() {
          vwRoleMenu = new VwRoleMenu();
          vwRoleMenu.setParameter(new Parameter());
          vwRoleMenu.refreshWorkArea();
          this.getContentPane().add(vwRoleMenu, BorderLayout.CENTER);
      }

}
