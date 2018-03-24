package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import client.framework.view.common.*;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class JMsRadioButton extends JRadioButton {
	BorderLayout borderLayout1 = new BorderLayout();
    private boolean keyType;
    private boolean  pbSelect_Save = false;

	public JMsRadioButton() {
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
		//this.setBackground( DefaultComp.BACKGROUND_COLOR_PANEL );
		this.setBorder(BorderFactory.createLoweredBevelBorder());
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
		setFont( DefaultComp.DEFUALT_BOLD_FONT );
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
