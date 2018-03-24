package client.framework.view.jmscomp;

import javax.swing.*;
import java.awt.*;
import client.framework.view.common.*;

/**
 *
 * <p>Title: </p>
 * <p>Description: Ç≈ÇÒï≤îÃîÑä«óùÉVÉXÉeÉÄ TitleBar</p>
*     Default Size: 1024 X 76
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: M-Stone</p>
 * @author yery
 * @version 1.0
 */
public class JMsTitleBar extends JPanel {
  ImageIcon zennohImg=new ImageIcon(this.getClass().getResource("zennoh.png"));
  JLabel jMsLabel_menuID = new JLabel();
  JLabel sysICON = new JLabel();
  JLabel jMsLabel_sysName = new JLabel();
  JLabel jMsLabel_menuName = new JLabel();
  JLabel jMsLabel_Date = new JLabel();
  JLabel jMsLabel_Addr = new JLabel();
  JLabel jMsLabel_line = new JLabel();
  public JMsTitleBar() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setBackground(DefaultCommon.COLOR_TITLE_BG);
    jMsLabel_menuID.setFont(new java.awt.Font("Dialog", 0, 16));
    jMsLabel_menuID.setText("M000");
    jMsLabel_menuID.setBounds(new Rectangle(18, 10, 49, 16));
    this.setLayout(null);
    sysICON.setBounds(new Rectangle(68, 10, 98, 31));
    sysICON.setMaximumSize(new Dimension(98, 31));
    sysICON.setMinimumSize(new Dimension(98, 31));
    sysICON.setPreferredSize(new Dimension(98, 31));
    sysICON.setIcon(zennohImg);
    sysICON.setText("");
    jMsLabel_sysName.setBounds(new Rectangle(328, 10, 369, 18));
    jMsLabel_sysName.setText("* * *  Ç≈ÇÒï≤îÃîÑä«óùÉVÉXÉeÉÄÅ@* * *");
    jMsLabel_sysName.setHorizontalAlignment(SwingConstants.CENTER);
    jMsLabel_sysName.setHorizontalTextPosition(SwingConstants.CENTER);
    jMsLabel_sysName.setFont(new java.awt.Font("ÇlÇr ÉSÉVÉbÉN", 0, 20));
    jMsLabel_menuName.setFont(new java.awt.Font("ÇlÇr ÉSÉVÉbÉN", 1, 20));
    jMsLabel_menuName.setText("Çw Çw Çw îÃ îÑ ãN ï[ ëO í˘ ê≥");
    jMsLabel_menuName.setHorizontalAlignment(SwingConstants.CENTER);
    jMsLabel_menuName.setHorizontalTextPosition(SwingConstants.CENTER);
    jMsLabel_menuName.setBounds(new Rectangle(302, 38, 407, 18));
    jMsLabel_Date.setFont(new java.awt.Font("ÇlÇr ÉSÉVÉbÉN", 0, 16));
    jMsLabel_Date.setHorizontalAlignment(SwingConstants.TRAILING);
    jMsLabel_Date.setHorizontalTextPosition(SwingConstants.TRAILING);
    jMsLabel_Date.setText("DATE 04/08/20Å@15:30:15 ");
    jMsLabel_Date.setBounds(new Rectangle(761, 10, 223, 20));
    jMsLabel_Addr.setBounds(new Rectangle(709, 38, 275, 20));
    jMsLabel_Addr.setText("ñ{èäÇmÅi XXXXXX )");
    jMsLabel_Addr.setFont(new java.awt.Font("ÇlÇr ÉSÉVÉbÉN", 0, 16));
    jMsLabel_Addr.setHorizontalAlignment(SwingConstants.TRAILING);
    jMsLabel_line.setBorder(BorderFactory.createLineBorder(Color.black));
    jMsLabel_line.setText("");
    jMsLabel_line.setBounds(new Rectangle(0, 65, 1024, 1));
    this.add(jMsLabel_line, null);
    this.add(sysICON, null);
    this.add(jMsLabel_Date, null);
    this.add(jMsLabel_Addr, null);
    this.add(jMsLabel_sysName, null);
    this.add(jMsLabel_menuName, null);
    this.add(jMsLabel_menuID, null);
  }

  public void setTime(java.applet.Applet applet) {
    if(applet!=null) {
      String sSysTime=applet.getParameter("SysTime");
      if(sSysTime!=null) {
        jMsLabel_Date.setText("DATE "+sSysTime+" ");
      }
    }
  }

  /**
   * @deprecated
   * @see applet.common.JMsTitleBar#setTime(java.applet.Applet applet)
   * @param sTime String as YY/MM/DD HH:MM:SS
   */
  public void setTime(String sTime) {
    jMsLabel_Date.setText("DATE "+sTime+" ");
  }

  public void setMenuID(String sMenuID) {
    jMsLabel_menuID.setText(sMenuID);
  }

  public void setMenuName(String sMenuName) {
    jMsLabel_menuName.setText(sMenuName);
  }

  public void setAddr(String sAddr) {
    jMsLabel_Addr.setText(sAddr);
  }
}
