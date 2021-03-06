package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import client.framework.view.common.*;

/**
 * <p>^Cg: JMsComp </p>
 * <p>à¾: Javax.Swingp³ÌIWiR|[lgQ</p>
 * <p>ì : milestone Copyright (c) 2002</p>
 * <p>ïÐ¼: }CXg[®ïÐ</p>
 * @author ¢üÍ
 * @version 1.0
 */

public class JMsButton extends JButton {
  BorderLayout borderLayout1 = new BorderLayout();
  TitledBorder titledBorder1;

  public JMsButton() {
    this("");
  }

  public JMsButton(String text) {
    super(text);
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    try {
      initBeanUser();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   *<BR>
   *@^Cv@F@ú»<BR>
   *@¼@F@úlÝè<BR>
   *@õ@l@F@<BR>
   *<BR>
   *@ÏXð<BR>
   *<BR>
   *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
   *@|||||||||||||||||||||||||||||||||||<BR>
   *@@00.00@@2002/05/30@ó@KO@@VKì¬<BR>
   *<BR>
   */
  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder("");
    this.setBorder(BorderFactory.createRaisedBevelBorder());
    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });
    this.setLayout(borderLayout1);
  }

  /**
   *<BR>
   *@^Cv@F@ú»<BR>
   *@¼@F@[UúlÝè<BR>
   *@õ@l@F@<BR>
   *<BR>
   *@ÏXð<BR>
   *<BR>
   *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
   *@|||||||||||||||||||||||||||||||||||<BR>
   *@@00.00@@2002/05/30@ó@KO@@VKì¬<BR>
   *<BR>
   */
  private void initBeanUser() throws Exception {
    //++****************************
    //	¶îñÝè
    //--****************************
    this.setDefaultCapable(false);
    this.enableEvents(0);
    setFont(DefaultComp.BUTTON_FONT);
  }

  void this_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      this.doClick();
    }
  }
}
