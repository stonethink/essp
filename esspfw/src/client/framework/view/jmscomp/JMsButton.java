package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import client.framework.view.common.*;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
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
   *　タイプ　：　初期化<BR>
   *　処理名　：　初期値設定処理<BR>
   *　備　考　：　<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
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
   *　タイプ　：　初期化<BR>
   *　処理名　：　ユーザ初期値設定処理<BR>
   *　備　考　：　<BR>
   *<BR>
   *　変更履歴<BR>
   *<BR>
   *　　Version　　日　付　　　更新者　　　　　コメント<BR>
   *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
   *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
   *<BR>
   */
  private void initBeanUser() throws Exception {
    //++****************************
    //	文字情報設定
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
