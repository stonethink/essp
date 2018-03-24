package client.framework.view.common;

import java.awt.*;
import javax.swing.*;
import client.framework.view.jmscomp.*;
import java.awt.event.*;
import client.framework.common.Constant;

/**
 * <p>É^ÉCÉgÉã: ã§í ä÷êîåQ</p>
 * <p>ê‡ñæ: </p>
 * <p>íòçÏå†: Copyright (c) 2002</p>
 * <p>âÔé–ñº: </p>
 * @author ñ¢ì¸óÕ
 * @version 1.0
 */
public class dlgOKCancel extends JDialog {
  JMsButton BTN_OK = new JMsButton();
  JMsButton BTN_Cancel = new JMsButton();

  private String _sMessage;
  private int _iResult;
  JTextArea jTextArea1 = new JTextArea();
  JCheckBox isPrompt = new JCheckBox();

  public dlgOKCancel(Frame frame, String title, boolean modal, String prm_sMessage) {
    super(frame, title, modal);
    isPrompt.setSelected(false);
    this.isPrompt.setVisible(false);
    _sMessage = prm_sMessage;
    try {
      jbInit();
      InitUser();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public dlgOKCancel(Frame frame, String title, boolean modal, String prm_sMessage, boolean isPrompt) {
    super(frame, title, modal);
    this.isPrompt.setSelected(isPrompt);
    _sMessage = prm_sMessage;
    try {
      jbInit();
      InitUser();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public dlgOKCancel() {
    this(null, "", false, "");
  }

  void jbInit() throws Exception {
    BTN_OK.setFont(Constant.DEFAULT_BUTTON_FONT);
    BTN_OK.setText("OK");
    BTN_OK.setBounds(new Rectangle(34, 103, 104, 32));
    BTN_OK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        BTN_OK_actionPerformed(e);
      }
    });
    BTN_OK.setDefaultCapable(true);
    BTN_OK.setRequestFocusEnabled(true);

    this.getContentPane().setLayout(null);

    BTN_Cancel.setBounds(new Rectangle(154, 103, 104, 32));
    BTN_Cancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        BTN_Cancel_actionPerformed(e);
      }
    });
    BTN_Cancel.setFont(Constant.DEFAULT_BUTTON_FONT);
    BTN_Cancel.setText("Cancel");
    BTN_Cancel.setToolTipText("");

    this.getContentPane().setBackground(new Color(240, 230, 245));

    jTextArea1.setBorder(null);
        jTextArea1.setText("setText");
    jTextArea1.setLineWrap(true);
    jTextArea1.setBounds(new Rectangle(30, 11, 228, 39));
    jTextArea1.setFocusable(false);
    isPrompt.setFont(new java.awt.Font("ÀŒÃÂ", Font.PLAIN, 12));
        isPrompt.setActionCommand("Don\'t remind me");
    isPrompt.setText("Don\'t remind me");
    isPrompt.setBounds(new Rectangle(34, 68, 210, 19));
    isPrompt.setBackground(Color.white);

    this.getContentPane().add(jTextArea1, null);
    this.getContentPane().add(BTN_OK, null);
    this.getContentPane().add(BTN_Cancel, null);
    this.getContentPane().add(isPrompt);
  }

  void InitUser() throws Exception {
    this.jTextArea1.setText(_sMessage);
    this.jTextArea1.setEditable(false);
    this.jTextArea1.setVisible(true);

    _iResult = -1;

    setBounds(344, 250, 304, 167);

    //âÊñ ÇÃíÜâõÇ…îzíu
    java.awt.Dimension d1 = this.getToolkit().getScreenSize();
    java.awt.Dimension d2 = this.getSize();
    this.setLocation(d1.width / 2 - d2.width / 2, d1.height / 2 - d2.height / 2);
  }

  void BTN_OK_actionPerformed(ActionEvent e) {
    _iResult = 1;
    dispose();
  }

  void BTN_Cancel_actionPerformed(ActionEvent e) {
    _iResult = -1;
    dispose();
  }

  public int showDialog(
      ) {
    this.setVisible(true);
    if(this.isPrompt.isVisible()==false) {
      return _iResult;
    } else {
      if(!this.isPrompt.isSelected()) {
        if (_iResult == -1) {
          return Constant.CANCEL;
        } else if (_iResult == 1) {
          return Constant.OK;
        }
      } else {
        if (_iResult == -1) {
          return Constant.ALWAYS_CANCEL;
        } else if (_iResult == 1) {
          return Constant.ALWAYS_OK;
        }
      }
    }
    return _iResult;
  }

  public boolean isPrompt() {
    return this.isPrompt.isSelected();
  }

  class wAdapter extends WindowAdapter {
    public void windowDeactivated(WindowEvent e) {
      if (dlgOKCancel.this.isShowing() == false) {
        dlgOKCancel.this.setVisible(true);
      }
    }
  }
}
