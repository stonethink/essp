/*
 *
 */
package client.framework.view.vwcomp;

import java.awt.BorderLayout;

import javax.swing.UIManager;

import client.framework.view.common.DefaultComp;

public class VWJDispReal extends VWJReal {
    private BorderLayout borderLayout1 = new BorderLayout();

    /**
     *
     */
    public VWJDispReal() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            initBeanUser();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.setBorder(UIManager.getBorder("TextField.border"));
    }

    public void setErrorField(boolean flag) {
    }

    public void setEnabled ( boolean isEnabled ){
        super.setEnabled(false);
    }

    private void initBeanUser() throws Exception {
        setEnabled(false);

        setFont(DefaultComp.DISP_NUMBER_FONT);

        setBackground(DefaultComp.DISP_NUMBER_BACKGROUND_COLOR);
        this.setDisabledTextColor(DefaultComp.DISP_NUMBER_INACT_FOREGROUND_COLOR);
    }

}
