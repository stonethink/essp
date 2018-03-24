package client.essp.common.view;

import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;

public class VWJStatusBar extends JPanel {
    JLabel label = new JLabel();
    public VWJStatusBar() {
        label.setHorizontalAlignment( SwingConstants.LEFT );
        label.setVerticalAlignment( SwingConstants.CENTER );
//        label.setBorder( BorderFactory.createLineBorder( Color.BLUE ));
        label.setFont( new Font("ËÎÌו", Font.PLAIN, 12) ) ;
        this.setLayout( new BorderLayout() );
        this.add(label, BorderLayout.NORTH);

    }

    public void setStatus(String s) {
        this.label.setText( " " + s );
        try{
            if (s != null) {
                java.awt.Container co = this.getParent();
                while (co != null && ! (co instanceof VMJApplet)) {
                    //System.out.println("The co class is not applete");
                    co = co.getParent();
                }
                ( (VMJApplet) co).setstatusstr(s);
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
}
