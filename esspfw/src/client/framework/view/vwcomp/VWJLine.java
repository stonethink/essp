package client.framework.view.vwcomp;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class VWJLine extends JLabel {
    BorderLayout borderLayout1 = new BorderLayout();
    Border border1;

    public VWJLine() {
        try {
            jbInit();
            super.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createLineBorder(Color.white, 2);
        this.setBackground(Color.gray);
        this.setBorder(border1);
        this.setText("     ");
    }
}
