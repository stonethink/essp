package client.framework.view.vwcomp;

import javax.swing.UIManager;

import client.framework.view.jmscomp.JMsLabel;

public class VWJLabel extends JMsLabel {
    public VWJLabel() {
        this.setBorder(UIManager.getBorder("Label.border"));
        //this.setDisplayedMnemonic('E');
    }
}
