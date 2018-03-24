package client.essp.common.humanAllocate.test;

import client.framework.view.vwcomp.VWJApplet;

public class AllocTestApplet extends VWJApplet {

    AllocTestPanel testPanel = new AllocTestPanel();

    public void initUI() {
        System.out.println( "AllocTestApplet.initUI()" );
        this.getContentPane().setLayout(null);

        testPanel.setBounds(0,0,500,500);
        this.getContentPane().add(testPanel);
    }


}
