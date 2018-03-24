package client.framework.view.common;

import java.awt.*;
import javax.swing.*;
import client.framework.view.jmscomp.*;
import java.awt.event.*;
import client.framework.common.Constant;
import javax.swing.border.*;

/**
 * <p>É^ÉCÉgÉã: ã§í ä÷êîåQ</p>
 * <p>ê‡ñæ: </p>
 * <p>íòçÏå†: Copyright (c) 2002</p>
 * <p>âÔé–ñº: </p>
 * @author ñ¢ì¸óÕ
 * @version 1.0
 */

public class dlgOK extends JDialog {

  private String _sTitle;
  private String _sMessage;
  private int _iResult;
  private Frame frame;

  JMsButton BTN_OK = new JMsButton();
  JTextArea jTextArea1 = new JTextArea();
  JScrollPane scrollPane = new JScrollPane();

  public dlgOK(Frame frame, boolean modal, String title, String prm_sMessage) {
    super(frame, title, modal);

    this.frame = frame;
    _sMessage = prm_sMessage;

    try {
      jbInit();
      initUser();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public dlgOK() {
    this(null, false, "", "");
  }

  void jbInit() throws Exception {
    BTN_OK.setToolTipText("");
    BTN_OK.setFont(Constant.DEFAULT_BUTTON_FONT);
    BTN_OK.setText("πÿ±’");
    BTN_OK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        BTN_OK_actionPerformed(e);
      }
    });
    BTN_OK.setActionCommand("πÿ±’");
    BTN_OK.setBounds(new Rectangle(134, 240, 104, 32));
    BTN_OK.setDefaultCapable(true);
    BTN_OK.setRequestFocusEnabled(true);

    this.getContentPane().setLayout(null);
    this.getContentPane().setBackground(new Color(240, 230, 245));

    jTextArea1.setFont(new java.awt.Font("ÀŒÃÂ", 0, 12));
    jTextArea1.setBorder(null);
    jTextArea1.setText("jTextArea1");
    //jTextArea1.setBounds(new Rectangle(16, 17, 340, 197));
    jTextArea1.setFocusable(false);
    scrollPane.setBounds(new Rectangle(16, 17, 340, 197));
    scrollPane.getViewport().add(jTextArea1);

    this.getContentPane().add(scrollPane, null);
    this.getContentPane().add(BTN_OK, null);

  }

  void BTN_OK_actionPerformed(ActionEvent e) {
    _iResult = 1;
    dispose();
  }

  /*
   * ÉÜÅ[ÉUíËã`èâä˙âªèàóù.
   */
  protected void initUser() {
    setBounds(344, 250, 377, 320);
    jTextArea1.setLineWrap(true);
    jTextArea1.setText(_sMessage);
    jTextArea1.setEditable(false);
    jTextArea1.setVisible(true);

    _iResult = -1;

    //âÊñ ÇÃíÜâõÇ…îzíu
    java.awt.Dimension d1 = this.getToolkit().getScreenSize();
    java.awt.Dimension d2 = this.getSize();
    this.setLocation(d1.width / 2 - d2.width / 2, d1.height / 2 - d2.height / 2);
  }

  public void showDialog() {
    this.setVisible(true);
  }

  class wAdapter extends WindowAdapter {
    public void windowDeactivated(WindowEvent e) {
      if (dlgOK.this.isShowing() == false) {
        dlgOK.this.setVisible(true);
      }
    }
  }
}
