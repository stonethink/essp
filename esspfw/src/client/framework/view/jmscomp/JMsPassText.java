package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JPasswordField;
import java.awt.event.*;
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

public class JMsPassText extends JPasswordField {

	/**		フィールドエラー判定用	*/
	private String		_sField_Error   = null;

	/**		setEnabledでの最終要求値	*/
	private boolean		_bEnabled_Save;

	/**		プロテクト・クリア・フラグ	*/
	private boolean		_bProtectClearFlag;


	BorderLayout borderLayout1 = new BorderLayout();
	private int maxByteLength;




	public JMsPassText() {
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
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setHorizontalAlignment(SwingConstants.LEFT);
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
		//	初期値設定
		//--****************************
		this._sField_Error	= "";
		_sField_Error	= "";
//		setKey( false );
//		setProtectClear( false );


		//++****************************
		//	文字情報設定
		//--****************************
//		setAutoIME( true );
//		setInputStyle ( defComponent.INPUT_STYLE );
		setFont( DefaultComp.TEXT_FONT );
		this.setSelectedTextColor( DefaultComp.FOREGROUND_COLOR_SELECT );
		this.setSelectionColor( DefaultComp.BACKGROUND_COLOR_SELECT );
		this.setDisabledTextColor( DefaultComp.DISABLED_FONT_COLOR );

		//++****************************
		//	プロパティ初期値
		//--****************************
		this.setMaxByteLength( DefaultComp.PASS_MAX_BYTE_LENGTH );
		this.setText( "" );

		//++****************************
		//	背景色設定
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	その他設定
		//--****************************
//		setSelectedInFocus( true );
		setEnabled( true );
//		setModified( false );

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
	private void _setBackgroundColor(
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
		//Enterキー押下の場合TABキー押下にイベントを変更
//		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
//			this.transferFocus();
//		}
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
	 *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
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
		//	入力変換（→直接入力）
		//--****************************
		getInputContext().setCharacterSubsets( null );

		//表示内容の一時対退避
		sStr    = this.getText();

		//++****************************
		//	文字挿入形式の制御
		//--****************************
		setDocument(new InputDocument( getMaxByteLength(),   //最大入力桁数
									   3,                    //通常文字と数字のみ
									   false                 //２byte文字のtrue/false
									   ));


		 //表示内容を退避から復元
		 this.setText( sStr );


		//++****************************
		//	全選択状態
		//--****************************
		this.selectAll();

		//++****************************
		//	背景色設定
		//--****************************
		setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );
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
	void this_focusLost(FocusEvent e) {
		String  sStr;
		//++****************************
		//	背景色設定
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	大文字変換処理
		//--****************************
//		if ( this.isConvUpperCase() == true ) {
//			sStr	= getText().toUpperCase();
//			setText( sStr );
//		}

		//++****************************
		//	選択状態を解除
		//--****************************
		this.setSelectionStart(0);
		setSelectionEnd(0);
	}


	/**
	 *<BR>
	 *　タイプ　：　プロパティ[ Field_Error ]: get<BR>
	 *　処理名　：　プロパティ[ Field_Error ]の取得メソッド<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	public String getField_Error () {
		//++******************************
		//	戻り値設定
		//--******************************
		return this._sField_Error;
	}

	/**
	 *<BR>
	 *　タイプ　：　プロパティ[ Field_Error ]: set<BR>
	 *　処理名　：　プロパティ[ Field_Error ]の設定メソッド<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
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
				setText( "" );
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
	 *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
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
	 *　タイプ　：　ユーザー関数<BR>
	 *　処理名　：　エラー状態設定処理<BR>
	 *　備　考　：　<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	public void setErrorField(
		boolean		prm_bError
	) {
		//++********************************
		//	プロテクト状態の場合、処理なし
		//--********************************
		if ( _sField_Error.equals( DefaultComp.FIELD_PROTECT ) == true ) {
			return;
		}

		//++********************************
		//	エラー状態設定
		//--********************************
		if ( prm_bError == true ) {
			setField_Error( DefaultComp.FIELD_ERROR );
		} else {
			setField_Error( "" );
		}
	}






	/**
	 *<BR>
	 *　タイプ　：　setTextのオーバーライド関数<BR>
	 *　備　考　：　大文字変換プロパティを調べ、変換後setText<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
/*	public void setText(
		String		prm_sStr
	) {
		if ( this.isConvUpperCase() == true ) {
			super.setText( prm_sStr.toUpperCase() );
		} else {
			super.setText( prm_sStr );
		}
//		_checkModified();
	}
*/
	public void setMaxByteLength(int maxByteLength) {
		this.maxByteLength = maxByteLength;
	}
	public int getMaxByteLength() {
		return maxByteLength;
	}

	public String getText() {
		int i;
		char[] chr;
		String sStr = "";
		chr = this.getPassword();
		for ( i=0 ; i<chr.length; i++ ) {
			sStr = sStr + chr[i];
		}

		sStr = sStr.trim();
		return sStr;
	}

	public void setText( String prm_sStr2) {
		String prm_sStr=prm_sStr2;
		if ( prm_sStr == null ) {
			prm_sStr = "";
		}

		super.setText(prm_sStr);
	}


}
