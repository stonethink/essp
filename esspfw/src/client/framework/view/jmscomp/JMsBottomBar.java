package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.*;
import client.framework.view.common.*;

/**
 * <p>Title: </p>
 * <p>Description: でん粉販売管理システム</p>
 *   Default Size: 1024 X 60
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: M-Stone</p>
 * @author yery
 * @version 1.0
 */

public class JMsBottomBar extends JPanel {
  JMsButton jMsButton_execute = new JMsButton();
  JMsButton jMsButton_gotoMenu = new JMsButton();
  JMsButton jMsButton_cancel = new JMsButton();
  JLabel jMsLabel_line = new JLabel();

  public JMsBottomBar() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.setBackground(DefaultCommon.COLOR_TITLE_BG);
    jMsButton_execute.setBounds(new Rectangle(465, 19, 74, 32));
    jMsButton_execute.setFont(new java.awt.Font("MS UI Gothic", 0, 13));
    jMsButton_execute.setText("実行");
    this.setLayout(null);
    jMsButton_gotoMenu.setText("ｻﾌﾞﾒﾆｭ\uFFFD");
    jMsButton_gotoMenu.setFont(new java.awt.Font("MS UI Gothic", 0, 13));
    jMsButton_gotoMenu.setBounds(new Rectangle(888, 19, 81, 32));
    jMsButton_cancel.setBounds(new Rectangle(611, 19, 74, 32));
    jMsButton_cancel.setFont(new java.awt.Font("MS UI Gothic", 0, 13));
    jMsButton_cancel.setText("取消");
    jMsLabel_line.setBorder(BorderFactory.createLineBorder(Color.black));
    jMsLabel_line.setText("");
    jMsLabel_line.setBounds(new Rectangle(0, 10, 1025, 1));
    this.add(jMsLabel_line, null);
    this.add(jMsButton_execute, null);
    this.add(jMsButton_cancel, null);
    this.add(jMsButton_gotoMenu, null);
  }

  public JMsButton getExecuteButton() {
    return this.jMsButton_execute;
  }

  public JMsButton getCancelButton() {
    return this.jMsButton_cancel;
  }

  public JMsButton getMenuButton() {
    return this.jMsButton_gotoMenu;
  }
}
