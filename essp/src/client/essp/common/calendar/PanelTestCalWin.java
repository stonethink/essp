package client.essp.common.calendar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author not attributable
 * @version 1.0
 */
public class PanelTestCalWin extends javax.swing.JPanel {
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JButton       jButton1    = new JButton();
    JTextField    jTextField1 = new JTextField();
    JButton       jButton2    = new JButton();
    JTextField    jTextField2 = new JTextField();
    JPanel        jPanel1     = new JPanel();
    CalendarWin   win1;
    CalendarWin   win2;

    public PanelTestCalWin() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PanelTestCalWin panelTest = new PanelTestCalWin();
        JOptionPane.showMessageDialog(null, panelTest);
    }

    private void jbInit() throws Exception {
        jButton1.setText("Show");
        jButton1.addActionListener(new PanelTestCalWinJButton1ActionAdapter(this));
        this.setLayout(gridBagLayout1);
        jTextField1.setText("jTextField1");
        jButton2.setText("Show");
        jButton2.addActionListener(new PanelTestCalWinJButton2ActionAdapter(this));
        jTextField2.setText("jTextField2");
        this.add(jButton1,
                 new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER,
                                        GridBagConstraints.NONE,
                                        new Insets(0, 0, 0, 0), 0, 0));
        this.add(jTextField1,
                 new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER,
                                        GridBagConstraints.NONE,
                                        new Insets(0, 0, 0, 0), 0, 0));
        this.add(jButton2,
                 new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER,
                                        GridBagConstraints.NONE,
                                        new Insets(0, 0, 0, 0), 0, 0));
        this.add(jPanel1,
                 new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER,
                                        GridBagConstraints.NONE,
                                        new Insets(36, 0, 0, 0), 18, 0));
        this.add(jTextField2,
                 new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER,
                                        GridBagConstraints.NONE,
                                        new Insets(0, 0, 0, 0), 0, 0));
    }

    void jButton1ActionPerformed(ActionEvent e) {
        if (win1 == null) {
            win1 = new CalendarWin(this.jTextField1, this.jButton1);
        }

        win1.show();
    }

    void jButton2ActionPerformed(ActionEvent e) {
        if (win2 == null) {
            win2 = new CalendarWin(this.jTextField2, this.jButton2);
        }

        win2.show();
    }
}


class PanelTestCalWinJButton1ActionAdapter
    implements java.awt.event.ActionListener {
    PanelTestCalWin adaptee;

    PanelTestCalWinJButton1ActionAdapter(PanelTestCalWin adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1ActionPerformed(e);
    }
}


class PanelTestCalWinJButton2ActionAdapter
    implements java.awt.event.ActionListener {
    PanelTestCalWin adaptee;

    PanelTestCalWinJButton2ActionAdapter(PanelTestCalWin adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2ActionPerformed(e);
    }
}
