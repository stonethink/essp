package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import java.awt.event.*;
import client.framework.view.common.*;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class JMsCheckBox extends JCheckBox {
	BorderLayout borderLayout1 = new BorderLayout();
    private boolean keyType;
    private boolean  pbSelect_Save = false;
	public JMsCheckBox() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			initBeanUser();
		}
		catch(Exception ex) {
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
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				this_keyPressed(e);
			}
		});
		this.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				this_focusGained(e);
			}
			public void focusLost(FocusEvent e) {
				this_focusLost(e);
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
		setFont( DefaultComp.CHECK_BOX_FONT );
	}

	void this_focusGained(FocusEvent e) {
		setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );
	}

	void this_focusLost(FocusEvent e) {
		setBackground( DefaultComp.BACKGROUND_COLOR_ENABLED );
	}

        /**
         *<BR>
         *　タイプ　：　イベント<BR>
         *　処理名　：　キー押下時の処理<BR>
         *　備　考　：　Enterキー押下でフィールド移動<BR>
         *<BR>
         *　変更履歴<BR>
         *<BR>
         *　　Version　　日　付　　　更新者　　　　　コメント<BR>
         *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
         *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
         *   00.01   2004/11/30  Yery        Enterイベントの処理を削除
         *<BR>
         */
	void this_keyPressed(KeyEvent e) {
          //ここのENTERイベントで問題を発生するので、削除するはずです。comFORM.setEnterOrder()を参照ください
//		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
//			this.transferFocus();
//		}

	}
    public void setKeyType(boolean keyType) {
        this.keyType = keyType;
    }
    public boolean isKeyType() {
        return keyType;
    }

	/**
	 *<BR>
	 *　タイプ　：　ユーザー関数<BR>
	 *　処理名　：　キー項目変更チェックメソッド<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2003/03/24　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	public boolean checkModified(
	) {
		boolean		bState;

		bState	= isSelected();
		if ( pbSelect_Save != bState ) {
			return true;
		} else {
            return false;
        }
	}

	public void clearModified(
	) {
		pbSelect_Save	= isSelected();
	}

}
