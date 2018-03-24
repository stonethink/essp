package client.framework.view.vwcomp;

import java.awt.BorderLayout;

import client.framework.view.common.DefaultComp;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */

public class VWJDispNumber extends VWJNumber{
    public VWJDispNumber() {
        this.setLayout(new BorderLayout());
        try {
            initBeanUser();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

    }


    private void initBeanUser() throws Exception {

        //++****************************
        //	èÛë‘ê›íË
        //--****************************
        setEnabled ( false );

        //++****************************
        //	ï∂éöèÓïÒê›íË
        //--****************************
        setFont( DefaultComp.DISP_NUMBER_FONT );

        //++****************************
        //	êFê›íË
        //--****************************
        setBackground ( DefaultComp.DISP_NUMBER_BACKGROUND_COLOR );
        this.setDisabledTextColor ( DefaultComp.DISP_NUMBER_INACT_FOREGROUND_COLOR );
    }

    public void setErrorField(boolean flag) {
    }

    public void setEnabled(boolean isEnabled) {
        super.setEnabled(false);
    }
}
