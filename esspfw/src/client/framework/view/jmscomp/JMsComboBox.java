package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import client.framework.view.common.*;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class JMsComboBox extends JComboBox {
	BorderLayout borderLayout1 = new BorderLayout();


	private String		_sField_Error	= null;
	/**		setEnabledでの最終要求値	*/
	private boolean		_bEnabled_Save;
	/**		前回入力値	*/
	private String		_sText_Save;
	/**		キーフラグ	*/
	private boolean		_bKeyFlag;
	/**		プロテクト・クリア・フラグ	*/
	private boolean		_bProtectClearFlag;
	private boolean		_bSelectByKey;
	private boolean		_bNoSelect;
	private boolean		_bFocus;
	Border border1;


	public JMsComboBox() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		try {
			InitUser();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

	}

	private void jbInit() throws Exception {
//		border1 = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(),border1);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				this_mouseClicked(e);
			}
		});

		this.registerKeyboardAction (new keyAction(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		this.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				this_focusGained(e);
			}
			public void focusLost(FocusEvent e) {
				this_focusLost(e);
			}
		});
	}

	private void InitUser() throws Exception {
		//++****************************
		//	初期値設定
		//--****************************
		_sField_Error	    = "";
		_bSelectByKey		= false;
		_bNoSelect			= false;
//		_bFocus				= false;
//		setKey( false );
//		setProtectClear( false );

		//++****************************
		//	文字情報設定
		//--****************************
		setFont ( DefaultComp.COMBO_BOX_FONT );
//        this.setModel( DefaultComp.DISABLED_FONT_COLOR );

		//++****************************
		//	背景色設定
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	その他設定
		//--****************************
		setEnabled( true );
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー関数<BR>
	 *　処理名　：　背景色設定処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	public void _setBackgroundColor(
	) {
		if ( isEnabled() == true ) {
			setBackground( DefaultComp.BACKGROUND_COLOR_ENABLED );
		} else {
			setBackground( DefaultComp.BACKGROUND_COLOR_DISABLED );
			this.setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　イベント<BR>
	 *　処理名　：　フォーカス時の処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/06/24    宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	void this_focusGained(FocusEvent e) {

	   String  sStr;

		//++****************************
		//	入力不可時、フィールド移動
		//--****************************
		if ( isEnabled() == false ) {
			return;
		}

		//++****************************
		//	状態設定
		//--****************************
		_bFocus		= true;
		_bSelectByKey		= false;

		//++****************************
		//	背景色設定
		//--****************************
//		setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );

	}


	/**
	 *<BR>
	 *　タイプ　：　イベント<BR>
	 *　処理名　：　ロスとフォーカス時の処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	public void this_focusLost(FocusEvent e) {
		String  sStr;

		//++****************************
		//	背景色設定
		//--****************************
//		_setBackgroundColor(); //modify by stone

		//++****************************
		//	状態設定
		//--****************************
		_bFocus		= false;

	}
	/**
	 *<BR>
	 *　タイプ　：　プロパティ[ Field_ERROR ]: get<BR>
	 *　処理名　：　プロパティ[ Field_ERROR ]の取得メソッド<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2000/09/05　亀井　雅敏　　新規作成<BR>
	 *<BR>
	 */
	public String getField_ERROR () {

		//++******************************
		//	戻り値設定
		//--******************************
		return _sField_Error;

	}

	/**
	 *<BR>
	 *　タイプ　：　プロパティ[ Field_ERROR ]: set<BR>
	 *　処理名　：　プロパティ[ Field_ERROR ]の設定メソッド<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2000/09/05　亀井　雅敏　　新規作成<BR>
	 *<BR>
	 */
	public void setField_ERROR (
		String		prm_sValue
	) {
		//++******************************
		//	入力値を保存
		//--******************************
		_sField_Error	= prm_sValue;

		//++************************
		//	文字色設定
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_ERROR ) == true ) {
			setForeground( DefaultComp.FOREGROUND_COLOR_ERROR );
		} else {
			setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
		}

		//++************************
		//	入力可否設定
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			//++********************************
			//	プロテクト時のクリア処理
			//--********************************
			if ( _bProtectClearFlag == true ) {
				this.setSelectedIndex( -1 );
//				setText( "" );
			}

			//++********************************
			//	入力不可に設定
			//--********************************
			super.setEnabled( false );
			_setBackgroundColor();
		} else {
			//++********************************
			//	最終setEnabled設定要求値に設定
			//--********************************
			super.setEnabled( _bEnabled_Save );
			_setBackgroundColor();
		}

	}


	public boolean checkSelectByKey(
	) {
		return _bSelectByKey;
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
	 *　　00.00　　2000/09/05　亀井　雅敏　　新規作成<BR>
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
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			return;
		}

		//++****************************
		//	入力不可設定
		//--****************************
		super.setEnabled( prm_bValue );
		_setBackgroundColor();

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
	 *　　00.00　　2002/06/24    宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param   なし
	 * @return  なし
	 */
	protected void this_keyPressed(KeyEvent e) {
		switch ( e.getKeyCode() ) {
			case KeyEvent.VK_ENTER:
				//	次フィールドへ移動
				transferFocus();
				break;

			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
				_bSelectByKey		= true;
				break;
/*
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_BACK_SPACE:
				//++******************************
				//	未選択可能な場合、未選択に設定
				//--******************************
				if ( _bNoSelect == true ) {
					select( -1 );
				}
			break;

*/
		}
	}

	void this_mouseClicked(MouseEvent e) {
		_bSelectByKey		= false;
	}



	/**
	 *<BR>
	 *　タイプ　：　プロパティ[ Field_IF_xxx_E ]: get<BR>
	 *　処理名　：　プロパティ[ Field_IF_xxx_E ]の取得メソッド<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2000/09/05　亀井　雅敏　　新規作成<BR>
	 *<BR>
	 */
	public String getField_Error () {

		//++******************************
		//	戻り値設定
		//--******************************
		return _sField_Error;

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
	 *　　00.00　　2000/09/05　亀井　雅敏　　新規作成<BR>
	 *<BR>
	 */
	public void setField_Error (
		String		prm_sValue
	) {
		//++******************************
		//	入力値を保存
		//--******************************
		_sField_Error	= prm_sValue;

		//++************************
		//	文字色設定
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_ERROR ) == true ) {
			setForeground( DefaultComp.FOREGROUND_COLOR_ERROR );
		} else {
			setForeground( DefaultComp.FOREGROUND_COLOR_NORMAL );
		}

		//++************************
		//	入力可否設定
		//--************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			//++********************************
			//	プロテクト時のクリア処理
			//--********************************
			if ( _bProtectClearFlag == true ) {
//				select( -1 );
//				setText( "" );
			}

			//++********************************
			//	入力不可に設定
			//--********************************
			super.setEnabled( false );
			_setBackgroundColor();
		} else {
			//++********************************
			//	最終setEnabled設定要求値に設定
			//--********************************
			super.setEnabled( _bEnabled_Save );
			_setBackgroundColor();
		}

	}
}

class keyAction	extends	AbstractAction
{
	public	keyAction (){
	}

	public	void	actionPerformed (ActionEvent	e)
	{
		JComponent combo = (JComponent) e.getSource();
		combo.transferFocus();
	}
}
