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


public class JMsListButton extends JButton {
	BorderLayout borderLayout1 = new BorderLayout();
	TitledBorder titledBorder1;

	/**		CORBA通信データ IF_xxx_E フィールド値	*/
	private String		_sField_IF_xxx_E	= null;

	/**		setEnabledでの最終要求値	*/
	private boolean		_bEnabled_Save = true;

	public JMsListButton() {
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
		titledBorder1 = new TitledBorder("");
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setText("×");
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
					this_keyPressed(e);
			}
		});
		this.setLayout(borderLayout1);

		//++****************************
		//	初期値設定
		//--****************************
		_sField_IF_xxx_E	= "";

	}

	/**
	 *<BR>
	 *　タイプ　：　プロパティ[ Field_IF_xxx_E ]: set<BR>
	 *　処理名　：　プロパティ[ Field_IF_xxx_E ]の設定メソッド<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2000/09/04　奥村　進一　　新規作成<BR>
	 *<BR>
	 */
	public void setField_Error (
		String		prm_sValue
	) {
		//++******************************
		//	入力値を保存
		//--******************************
		_sField_IF_xxx_E	= prm_sValue;

		//++************************
		//	入力可否設定
		//--************************
		if ( _sField_IF_xxx_E.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			//++********************************
			//	入力不可に設定
			//--********************************
			super.setEnabled( false );
		} else {
			//++********************************
			//	最終setEnabled設定要求値に設定
			//--********************************
			super.setEnabled( _bEnabled_Save );
		}

	}

	/**
	 *<BR>
	 *　タイプ　：　プロパティ[ Enabled ]: set<BR>
	 *　処理名　：　プロパティ[ Enabled ]の設定メソッド<BR>
	 *　備　考　：　setEnabledのオーバーライド関数<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2000/09/04　奥村　進一　　新規作成<BR>
	 *<BR>
	 */
	public void setEnabled(
		boolean		prm_bValue
	) {

		//++****************************
		//	要求値保存
		//--****************************
		_bEnabled_Save	= prm_bValue;

		//++**************************************
		//	入力不可の場合、処理キャンセル
		//--**************************************
		if ( _sField_IF_xxx_E.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			return;
		}

		//++****************************
		//	入力不可設定
		//--****************************
		super.setEnabled( prm_bValue );

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
//        this.setRequestFocusEnabled(false);
//        this.setFocusPainted( false );
		this.setDefaultCapable( false );

		setFont( DefaultComp.BUTTON_FONT );

	}

	void this_keyPressed(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_ENTER ){
			this.doClick();
		}
	}

}
