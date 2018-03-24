package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import client.framework.view.common.*;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
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
   *�@�^�C�v�@�F�@������<BR>
   *�@�������@�F�@�����l�ݒ菈��<BR>
   *�@���@�l�@�F�@<BR>
   *<BR>
   *�@�ύX����<BR>
   *<BR>
   *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
   *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
   *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
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
   *�@�^�C�v�@�F�@������<BR>
   *�@�������@�F�@���[�U�����l�ݒ菈��<BR>
   *�@���@�l�@�F�@<BR>
   *<BR>
   *�@�ύX����<BR>
   *<BR>
   *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
   *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
   *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
   *<BR>
   */
  private void initBeanUser() throws Exception {
    //++****************************
    //	�������ݒ�
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
