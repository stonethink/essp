package client.essp.common.security.queryPrivilege;

import java.awt.BorderLayout;

import client.essp.common.view.UISetting;
import client.framework.view.vwcomp.VWJApplet;
import com.wits.util.Parameter;

/**
 * <p>Title: VwQueryPrivilegeApplet</p>
 *
 * <p>Description: 查询授权Applet</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwQueryPrivilegeApplet extends VWJApplet {

    private VwQueryPrivilege vwQueryPrivilege;

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
          vwQueryPrivilege = new VwQueryPrivilege();
          vwQueryPrivilege.setParameter(new Parameter());
          vwQueryPrivilege.refreshWorkArea();
          this.getContentPane().add(vwQueryPrivilege, BorderLayout.CENTER);
      }

}
