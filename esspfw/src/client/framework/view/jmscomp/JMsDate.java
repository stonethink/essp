package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;
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

public class JMsDate extends JTextField {
	BorderLayout borderLayout1 = new BorderLayout();


	private String		_sField_Error	= null;

	/**		setEnabledでの最終要求値	*/
	private boolean		_bEnabled_Save;

	/**		入力値変更フラグ値	*/
	private boolean		_bModified;

	/**		キーフラグ	*/
	private boolean		_bKeyFlag;

	/**		プロテクト・クリア・フラグ	*/
	private boolean		_bProtectClearFlag;

	private int				_iDataType;
	private	boolean			_bFocus;


	public	 static final String		_DATA_PRO_YMD			= "YYYYMMDD";
	public	 static final String		_DATA_PRO_HMS			= "HHMMSS";
	public	 static final String		_DATA_PRO_HM			= "HHMM";
	public	 static final String		_DATA_PRO_YYYY			= "YYYY";
	public	 static final String		_DATA_PRO_YM			= "YYYYMM";
	public	 static final String		_DATA_PRO_MM			= "MM";
	public	 static final String		_DATA_PRO_DD			= "DD";
        //add by yery on 2004/11/12
    public	 static final String            _DATA_PRO_MMDD                  = "MMDD";
        //add by yery on 2004/11/22
    public	 static final String            _DATA_PRO_YYMMDD                = "YYMMDD";
    public	 static final String            _DATA_PRO_YYMM                  = "YYMM";
    //add by xr on 2005/11/22
	private	 final int		_DATA_TYPE_YMD			= 1;
	private	 final int		_DATA_TYPE_HMS			= 2;
	private	 final int		_DATA_TYPE_HM			= 3;
	private	 final int		_DATA_TYPE_YYYY			= 4;
	private	 final int		_DATA_TYPE_YM			= 5;
	private	 final int		_DATA_TYPE_MM			= 6;
	private	 final int		_DATA_TYPE_DD			= 7;
        //add by yery on 2004/11/12
        private	 final int		_DATA_TYPE_MMDD			= 8;
        //add by yery on 2004/11/22
        private final int            _DATA_TYPE_YYMMDD                = 9;
        private final int            _DATA_TYPE_YYMM                  = 10;

	private	 final int		_MAX_INPUT_LENGTH_YMD	= 8;
	private	 final int		_MAX_INPUT_LENGTH_HMS	= 6;
	private	 final int		_MAX_INPUT_LENGTH_HM	= 4;
	private	 final int		_MAX_INPUT_LENGTH_YYYY	= 4;
	private	 final int		_MAX_INPUT_LENGTH_YM	= 6;
	private	 final int		_MAX_INPUT_LENGTH_MM	= 2;
	private	 final int		_MAX_INPUT_LENGTH_DD	= 2;
        //add by yery on 2004/11/12
        private	 final int		_MAX_INPUT_LENGTH_MMDD	= 4;
        //add by yery on 2004/11/22
        private	 final int		_MAX_INPUT_LENGTH_YYMMDD= 6;
        private	 final int		_MAX_INPUT_LENGTH_YYMM	= 4;

	private String dataType;
	private int maxByteLength;
	private String  psText_Save = "";
	private boolean keyType;


	public JMsDate() {
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
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	private void jbInit() throws Exception {
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				this_focusGained(e);
			}
			public void focusLost(FocusEvent e) {
				this_focusLost(e);
			}
		});
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				onKeyPressed(e);
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
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
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
//		setInputStyle ( DefaultComp.INPUT_STYLE );
		setFont( DefaultComp.DATE_FONT );
		this.setSelectedTextColor( DefaultComp.FOREGROUND_COLOR_SELECT );
		this.setSelectionColor( DefaultComp.BACKGROUND_COLOR_SELECT );
		this.setDisabledTextColor( DefaultComp.DISABLED_FONT_COLOR );

		//++****************************
		//	プロパティ初期値
		//--****************************
		setDataType( _DATA_PRO_YMD );
		this.setText( "" );


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
	protected void onKeyPressed(KeyEvent e) {
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
		String  sType;
		int     iMaxLeng;

		//++****************************
		//	入力不可時、フィールド移動
		//--****************************
		if ( isEnabled() == false ) {
			return;
		}

                this._bFocus = true; //add

		//++****************************
		//	入力変換（→直接入力）
		//--****************************
        if(getInputContext()!=null) {
            getInputContext().setCharacterSubsets(null);
        }


		//表示内容の一時対退避
		sStr    = this.getText();

		//++****************************
		//	入力時文字列設定
		//--****************************
/*		if ( _bFocus == false ) {
			try {
				_bFocus = true;
				sStr = _getInputText( sStr );
//				setText( sStr );
			} catch( Exception clsExcept ) {
				setText( "" );
			}
		}
*/
		sStr    =   removeNonNumeric( getText() );



		//日付タイプを取得
		sType   =   this.getDataType();

		//日付タイプによる最大入力桁数の判定
		if ( sType.equals( _DATA_PRO_YMD ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YMD;
		} else if ( sType.equals( _DATA_PRO_HMS ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_HMS;
		} else if ( sType.equals( _DATA_PRO_HM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_HM;
		} else if ( sType.equals( _DATA_PRO_YYYY ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YYYY;
		} else if ( sType.equals( _DATA_PRO_YM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YM;
		} else if ( sType.equals( _DATA_PRO_MM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_MM;
		} else if ( sType.equals( _DATA_PRO_DD ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_DD;
                } else if ( sType.equals( _DATA_PRO_MMDD ) == true ) { //add by yery on 2004/11/12
			iMaxLeng  =   _MAX_INPUT_LENGTH_MMDD;
		} else if( sType.equals( _DATA_PRO_YYMMDD ) == true ) { //add by yery on 2004/11/22
                       iMaxLeng  =   _MAX_INPUT_LENGTH_YYMMDD;
                } else if( sType.equals( _DATA_PRO_YYMM ) == true ) { //add by yery on 2004/11/22
                       iMaxLeng  =   _MAX_INPUT_LENGTH_YYMM;
                } else {
			//プロパティの設定が誤っていた場合はyyyymmddとする
			iMaxLeng  = _MAX_INPUT_LENGTH_YMD;
		}

		//++****************************
		//	文字挿入形式の制御
		//--****************************
		setDocument(new InputDocument( iMaxLeng,  //最大入力桁数
									   1,     //コンポーネントタイプ(数字のみ）
									   false     //２byte文字のtrue/false
									   ));


		 //表示内容を退避から復元
		 this.setText( sStr );


		//++****************************
		//	入力制限値設定
		//--****************************
//		_setStatus_Input();


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
	public void this_focusLost(FocusEvent e) {
		String  sStr;
		String  sOldsStr;
		String  sType   = _DATA_PRO_YMD;
		int     iMaxLeng;

		//++****************************
		//	入力制限値設定
		//--****************************
		try {
//			setEnableType (com.fujitsu.jbk.gui.JFEnableType.TYPE_ALL);
			setMaxByteLength( DefaultComp.DATE_MAX_BYTE_LENGTH );
//			setMaxLength( -1 );
		} catch( Exception clsExcept ) {
		}
                this._bFocus = false; //add

		sOldsStr    =   getText();

		//日付タイプによる最大入力桁数の判定
		if ( sType.equals( _DATA_PRO_YMD ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YMD + 2;
		} else if ( sType.equals( _DATA_PRO_HMS ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_HMS +2;
		} else if ( sType.equals( _DATA_PRO_HM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_HM + 1;
		} else if ( sType.equals( _DATA_PRO_YYYY ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YYYY;
		} else if ( sType.equals( _DATA_PRO_YM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_YM + 1;
		} else if ( sType.equals( _DATA_PRO_MM ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_MM;
		} else if ( sType.equals( _DATA_PRO_DD ) == true ) {
			iMaxLeng  =   _MAX_INPUT_LENGTH_DD;
                } else if ( sType.equals( _DATA_PRO_MMDD ) == true ) {
                        iMaxLeng  =   _MAX_INPUT_LENGTH_MMDD + 1;
                } else if ( sType.equals( _DATA_PRO_YYMMDD ) == true ) {
                        iMaxLeng  =   _MAX_INPUT_LENGTH_YYMMDD + 2;
                } else if ( sType.equals( _DATA_PRO_YYMM ) == true ) {
                        iMaxLeng  =   _MAX_INPUT_LENGTH_YYMM + 1;
                } else {
			//プロパティの設定が誤っていた場合はyyyymmddとする
			iMaxLeng   = _MAX_INPUT_LENGTH_YMD + 2;
		}

		//++****************************
		//	文字挿入形式の制御
		//--****************************
		setDocument(new InputDocument( iMaxLeng,  //最大入力桁数
									   0,     //コンポーネントタイプ(数字のみ）
									   false     //２byte文字のtrue/false
									   ));


		//++****************************
		//	表示文字列設定
		//--****************************
		try {
			sStr	= _getOutputText( sOldsStr );
			setText( sStr );
		} catch( Exception clsExcept ) {
			setText( "" );
		}

		//++******************************
		//	妥当性判定
		//--******************************
//		if ( _checkValue( getText() ) != 0 ) {
//			this.setErrorField( true );
//		} else {
//          this.setErrorField( false );
//        }

		//++****************************
		//	背景色設定
		//--****************************
		_setBackgroundColor();

		//++****************************
		//	選択状態を解除
		//--****************************
		this.setSelectionStart(0);
		setSelectionEnd(0);
	}



	public void setValueText(
		String		prm_sValue
	) {
		String		sStr;
		String		sValue;

		try {
			sValue	= prm_sValue.trim();
			if (sValue.equals( "" ) ) {
				setText( "" );
				return;
			}

                        //++******************************
                        //	add by yery on 2004/11/26
                        //--******************************
                        if(this.getDataType().equals(_DATA_PRO_YYMMDD)) {
                          if(sValue.length()==8) {
                            sValue=sValue.substring(2);
                          }
                        }


			//++******************************
			//	妥当性判定
			//--******************************
			if ( _checkValue( sValue ) != 0 ) {
				setText( "" );
				return;
			}



			//++******************************
			//	該当書式に変換し設定
			//--******************************
			if ( _bFocus == true ) {

			//				_setStatus_Input();
				setText( sValue );
			} else {
//				setEnableType (com.fujitsu.jbk.gui.JFEnableType.TYPE_ALL );
				setMaxByteLength( DefaultComp.DATE_MAX_BYTE_LENGTH );
//				setMaxLength( -1 );
				sStr	= _getOutputText( sValue );
				setText( sStr );


			}
		} catch ( Exception clsExcept ) {
			setText( "" );
		}

		//++****************************
		//	変更判定
		//--****************************
//		_checkModified();

	}
/*
	private void _setStatus_Input(
	) {
		try {
			switch ( _iDataType ) {
				case _DATA_TYPE_YMD:
					setMaxByteLength( _MAX_INPUT_LENGTH_YMD );
					break;
				case _DATA_TYPE_HMS:
					setMaxByteLength( _MAX_INPUT_LENGTH_HMS );
					break;
				case _DATA_TYPE_HM:
					setMaxByteLength( _MAX_INPUT_LENGTH_HM );
					break;
				case _DATA_TYPE_YYYY:
					setMaxByteLength( _MAX_INPUT_LENGTH_YYYY );
					break;
				case _DATA_TYPE_YM:
					setMaxByteLength( _MAX_INPUT_LENGTH_YM );
					break;
				case _DATA_TYPE_MM:
					setMaxByteLength( _MAX_INPUT_LENGTH_MM );
					break;
				case _DATA_TYPE_DD:
					setMaxByteLength( _MAX_INPUT_LENGTH_DD );
					break;
			}
//			setEnableType (com.fujitsu.jbk.gui.JFEnableType.TYPE_DIGIT);
		} catch( Exception clsExcept ) {
		}
	}
*/

	public String getValueText(
	) {
		String		sStr;

		if ( _bFocus == true ) {
			sStr = getText();
		} else {
			sStr = _getInputText( getText() );
		}

		if ( sStr == null ) {
			return "";
		} else {
			return sStr;
		}

	}


	private String _getOutputText(
		String		prm_sDate
	) {

		String	sTemp;
		String	sResult;
		int		iPos1;
		int		iPos2;

		if ( prm_sDate.trim().equals( "" ) == true ) {
			return "";
		}

		iPos1	= prm_sDate.indexOf( "/" );
		iPos2	= prm_sDate.indexOf( ":" );
		if ( ( iPos1 >= 0 ) || ( iPos2 >= 0) ) {
			return prm_sDate;
		}

		sTemp	= prm_sDate + "        ";
		switch ( _iDataType ) {
			case _DATA_TYPE_YMD:
				sResult	= sTemp.substring( 0, 4 )
						+ "/"
						+ sTemp.substring( 4, 6 )
						+ "/"
						+ sTemp.substring( 6, 8 )
						;
				break;

			case _DATA_TYPE_HMS:
				sResult	= sTemp.substring( 0, 2 )
						+ ":"
						+ sTemp.substring( 2, 4 )
						+ ":"
						+ sTemp.substring( 4, 6 )
						;
				break;

			case _DATA_TYPE_HM:
				sResult	= sTemp.substring( 0, 2 )
						+ ":"
						+ sTemp.substring( 2, 4 )
						;
				break;

			case _DATA_TYPE_YYYY:
				sResult	= sTemp.substring( 0, 4 );
				break;

			case _DATA_TYPE_YM:
				sResult	= sTemp.substring( 0, 4 )
						+ "/"
						+ sTemp.substring( 4, 6 )
						;
				break;

			case _DATA_TYPE_MM:
				sResult	= sTemp.substring( 0, 2 );
				break;

			case _DATA_TYPE_DD:
				sResult	= sTemp.substring( 0, 2 );
				break;

                      //add by yery on 2004/11/12
                       case _DATA_TYPE_MMDD:
                               sResult	= sTemp.substring( 0, 2 )+"/"+sTemp.substring( 2, 4 );
                               break;

                      //add by yery on 2004/11/22
                      case _DATA_TYPE_YYMMDD:
                               sResult	= sTemp.substring( 0, 2 )+"/"+sTemp.substring( 2, 4 )+"/"+sTemp.substring( 4, 6 );
                               break;
                               //add by yery on 2004/11/22
                      case _DATA_TYPE_YYMM:
                               sResult	= sTemp.substring( 0, 2 )+"/"+sTemp.substring( 2, 4 );
                               break;
                      default:
				sResult	= "";
		}

		return sResult;
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　表示日付データ変換<BR>
	 *　備　考　：　内部日付データを表示日付データに変換する。<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param   prm_sDate　IN　：　内部日付データ
	 * @return  表示日付データ
	 */
	private String _getInputText(
		String		prm_sDate
	) {

		String	sTemp;
		String	sResult;
		int		iPos1;
		int		iPos2;

		iPos1	= prm_sDate.indexOf( "/" );
		iPos2	= prm_sDate.indexOf( ":" );
		if ( ( iPos1 < 0 ) && ( iPos2 < 0) ) {
			return prm_sDate;
		}


		sTemp	= prm_sDate + "          ";
		switch ( _iDataType ) {
			case _DATA_TYPE_YMD:
				sResult	= sTemp.substring( 0, 4 )
						+ sTemp.substring( 5, 7 )
						+ sTemp.substring( 8, 10 )
						;
				break;

			case _DATA_TYPE_HMS:
				sResult	= sTemp.substring( 0, 2 )
						+ sTemp.substring( 3, 5 )
						+ sTemp.substring( 6, 8 )
						;
				break;

			case _DATA_TYPE_HM:
				sResult	= sTemp.substring( 0, 2 )
						+ sTemp.substring( 3, 5 )
						;
				break;

			case _DATA_TYPE_YYYY:
				sResult	= sTemp.substring( 0, 4 );
				break;

			case _DATA_TYPE_YM:
				sResult	= sTemp.substring( 0, 4 )
						+ sTemp.substring( 5, 7 )
						;
				break;

			case _DATA_TYPE_MM:
				sResult	= sTemp.substring( 0, 2 );
				break;

			case _DATA_TYPE_DD:
				sResult	= sTemp.substring( 0, 2 );
				break;
                       //add by yery on 2004/11/12
                       case _DATA_TYPE_MMDD:
                                sResult	= sTemp.substring( 0, 2 )+sTemp.substring( 3, 5 );
                                break;

                       //add by yery on 2004/11/22
                       case _DATA_TYPE_YYMMDD:
                                sResult	= sTemp.substring( 0, 2 )+sTemp.substring( 3, 5 )+sTemp.substring( 6, 8 );
                                break;

                       //add by yery on 2004/11/22
                       case _DATA_TYPE_YYMM:
                                sResult	= sTemp.substring( 0, 2 )+sTemp.substring( 3, 5 );
                                break;

			default:
				sResult	= "";
		}

		return sResult.trim();
	}


	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　日付、時間データチェック<BR>
	 *　備　考　：　入力された日付、時間データが正しいかチェックする。<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
	public int checkValue(
	) {
		return _checkValue( getValueText() );
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　日付、時間データチェック<BR>
	 *　備　考　：　入力された日付、時間データが正しいかチェックする。<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 */
        private int _checkValue(
            String prm_sValue
            ) {
          String sValue;
          int iYear;
          int iMonth;
          int iDay;
          int iHour;
          int iMinute;
          int iScond;
          boolean bResult;
          try {
            //++***********************************
            //	Null時、未入力エラー
            //--***********************************
            if (prm_sValue == null) {
              return 1;
            }
            //++***********************************
            //	未入力判定
            //--***********************************
            sValue = prm_sValue.trim();
            if (sValue.equals("") == true) {
              return 1;
            }
            //++***********************************
            //	タイプ毎に判定
            //--***********************************
            switch (_iDataType) {
              case _DATA_TYPE_YMD:
                iYear = Integer.valueOf(sValue.substring(0, 4)).intValue();
                iMonth = Integer.valueOf(sValue.substring(4, 6)).intValue();
                iDay = Integer.valueOf(sValue.substring(6, 8)).intValue();
                //++***********************************
                //	入力された年月日が正しいかチェック
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_HMS:
                iHour = Integer.valueOf(sValue.substring(0, 2)).intValue();
                iMinute = Integer.valueOf(sValue.substring(2, 4)).intValue();
                iScond = Integer.valueOf(sValue.substring(4, 6)).intValue();
                //++***********************************
                //	入力された時分秒が正しいかチェック
                //--***********************************
                bResult = _checkTime(iHour, iMinute, iScond);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_HM:
                iHour = Integer.valueOf(sValue.substring(0, 2)).intValue();
                iMinute = Integer.valueOf(sValue.substring(2, 4)).intValue();
                iScond = 0;
                //++***********************************
                //	入力された時分秒が正しいかチェック
                //--***********************************
                bResult = _checkTime(iHour, iMinute, iScond);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_YYYY:
                iYear = Integer.valueOf(sValue.substring(0, 4)).intValue();
                iMonth = 1;
                iDay = 1;
                //++***********************************
                //	入力された年月日が正しいかチェック
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_YM:
                iYear = Integer.valueOf(sValue.substring(0, 4)).intValue();
                iMonth = Integer.valueOf(sValue.substring(4, 6)).intValue();
                iDay = 1;
                //++***********************************
                //	入力された年月日が正しいかチェック
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_MM:
                iYear = 2000;
                iMonth = Integer.valueOf(sValue).intValue();
                iDay = 1;
                //++***********************************
                //	入力された年月日が正しいかチェック
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              case _DATA_TYPE_DD:
                iYear = 2000;
                iMonth = 1;
                iDay = Integer.valueOf(sValue).intValue();
                //++***********************************
                //	入力された年月日が正しいかチェック
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
                //add by yery on 2004/11/12
              case _DATA_TYPE_MMDD:
                iYear = 2000;
                iMonth = Integer.valueOf(sValue.substring(0, 2)).intValue();
                iDay = Integer.valueOf(sValue.substring(2, 4)).intValue();
                //++***********************************
                //	入力された年月日が正しいかチェック
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
                //add by yery on 2004/11/22
              case _DATA_TYPE_YYMMDD:
                iYear = 2000+Integer.valueOf(sValue.substring(0, 2)).intValue();
                iMonth = Integer.valueOf(sValue.substring(2, 4)).intValue();
                iDay = Integer.valueOf(sValue.substring(4, 6)).intValue();
                //++***********************************
                //	入力された年月日が正しいかチェック
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
                //add by yery on 2004/11/22
              case _DATA_TYPE_YYMM:
                iYear = 2000+Integer.valueOf(sValue.substring(0, 2)).intValue();
                iMonth = Integer.valueOf(sValue.substring(2, 4)).intValue();
                iDay = 1;
                //++***********************************
                //	入力された年月日が正しいかチェック
                //--***********************************
                bResult = _checkDate(iYear, iMonth, iDay);
                if (bResult == true) {
                  return 0;
                }
                else {
                  return -1;
                }
              default:
                return -1;
            }
          }
          catch (Exception clsExcept) {
            return -1;
          }
        }

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　日付データチェック<BR>
	 *　備　考　：　入力された日付データが正しいかチェックする。<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param   prm_iYear　IN　	：　年データ
	 * @param   prm_iMonth　IN　：　月データ
	 * @param   prm_iDay　IN　	：　日データ
	 * @return 	trueかfalseを返す。
	 */
	private boolean _checkDate(
		int		prm_iYear,
		int		prm_iMonth,
		int		prm_iDay
	){
		int		iHizuke;

		//++****************************************
		//	年の判定（1990～2020の範囲内であるか？）
		//--****************************************
		// 20040405 edit shimada. sql における最小値(1900/01/01)と最大値(2040/12/31)の間に変更。
		//if (( prm_iYear < 1990 ) || ( prm_iYear > 2020 )) {
//		if (( prm_iYear < 1900 ) || ( prm_iYear > 2040 )) {
//			return false;
//		}
        if(prm_iYear<0) {
            return false;
        }

		//++****************************************
		//	月の判定（1～12月の範囲内であるか？）
		//--****************************************
		if (( prm_iMonth < 1 ) || ( prm_iMonth > 12 )) {
			return false;
		}

		//++**********************************************
		//	日の判定（1～月の最大日数の範囲内であるか？）
		//--**********************************************
		iHizuke = _getDate( prm_iYear, prm_iMonth );
		if (( prm_iDay < 1 ) || ( prm_iDay > iHizuke )) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　月の最大日数の出力<BR>
	 *　備　考　：　入力された年と月によってその月の最大日数を返す。<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param   prm_iYear2　IN　	：　年データ
	 * @param   prm_iMonth2　IN　：　月データ
	 * @return 	月の最大日数を返す。
	 */
	 private int _getDate(
		int		prm_iYear2,
		int		prm_iMonth2
	){
		//++***********************************
		//	月の判定（最大日数が３０日？）
		//--***********************************
		if ( prm_iMonth2 == 4 || prm_iMonth2 == 6 || prm_iMonth2 == 9 || prm_iMonth2 == 11 ) {
			return 30;
		}

		//++***********************************
		//	月の判定（2月以外の月か？）
		//--***********************************
		if ( prm_iMonth2 != 2 ) {
			return 31;
		}

		//++***********************************
		//	月の判定（うるう年の２月か？）
		//--***********************************
		if ( prm_iYear2%400 == 0 || ((prm_iYear2%100 != 0)&&(prm_iYear2%4 == 0 ))) {
			return 29;
		} else {
			return 28;
		}
	}

	/**
	 *<BR>
	 *　タイプ　：　ユーザー定義<BR>
	 *　処理名　：　時間データチェック<BR>
	 *　備　考　：　入力された時間データが正しいかチェックする。<BR>
	 *<BR>
	 *　変更履歴<BR>
	 *<BR>
	 *　　Version　　日　付　　　更新者　　　　　コメント<BR>
	 *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
	 *<BR>
	 * @param   prm_iHour　IN　	：　時データ
	 * @param   prm_iMinute　IN　：　分データ
	 * @param   prm_iScond　IN　	：　秒データ
	 * @return 	trueかfalseを返す。
	 */
	 private boolean _checkTime(
		int		prm_iHour,
		int		prm_iMinute,
		int		prm_iScond
	){
		//++***********************************
		//	時間の判定（時が0～23時であるか？）
		//--***********************************
		if (( prm_iHour < 0 ) || ( prm_iHour > 23 )) {
			return false;
		}

		//++***********************************
		//	時間の判定（分が0～59分であるか？）
		//--***********************************
		if (( prm_iMinute < 0 ) || ( prm_iMinute > 59 )) {
			return false;
		}

		//++***********************************
		//	時間の判定（秒が0～59秒であるか？）
		//--***********************************
		if (( prm_iScond < 0 ) || ( prm_iScond > 59 )) {
			return false;
		} else {
			return true;
		}
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
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
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
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
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
				setValueText( "" );
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
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
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
	 *　　00.00　　2002/06/01　宝来　幸弘　　新規作成<BR>
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




	private String removeNonNumeric( String oldStr ){
		StringBuffer newStr = new StringBuffer();

		boolean bFrg  = false;
		char char2;

		for( int i=0 ; i<oldStr.length() ; i++ ){
			char chr = oldStr.charAt(i);
			//if( Character.isDigit( chr ) ){ これでは全角の数字も通してしまう
			if(  '0' <= chr && chr <= '9' ) {
				try {
					newStr.append( chr );
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}

		return( newStr.toString() );
	}





	public void setDataType( String dataType) {
		this.dataType = dataType;

		//++************************
		//	値を保存
		//--************************
		if ( dataType.equals( _DATA_PRO_YMD ) == true ) {
			_iDataType  =   _DATA_TYPE_YMD;
		} else if ( dataType.equals( _DATA_PRO_HMS ) == true ) {
			_iDataType  =   _DATA_TYPE_HMS;
		} else if ( dataType.equals( _DATA_PRO_HM ) == true ) {
			_iDataType  =   _DATA_TYPE_HM;
		} else if ( dataType.equals( _DATA_PRO_YYYY ) == true ) {
			_iDataType  =   _DATA_TYPE_YYYY;
		} else if ( dataType.equals( _DATA_PRO_YM ) == true ) {
			_iDataType  =   _DATA_TYPE_YM;
		} else if ( dataType.equals( _DATA_PRO_MM ) == true ) {
			_iDataType  =   _DATA_TYPE_MM;
		} else if ( dataType.equals( _DATA_PRO_DD ) == true ) {
			_iDataType  =   _DATA_TYPE_DD;
                } else if ( dataType.equals( _DATA_PRO_MMDD ) == true ) {
			_iDataType  =   _DATA_TYPE_MMDD;
                } else if ( dataType.equals( _DATA_PRO_YYMMDD ) == true ) {
                       _iDataType  =   _DATA_TYPE_YYMMDD;
                } else if ( dataType.equals( _DATA_PRO_YYMM ) == true ) {
                       _iDataType  =   _DATA_TYPE_YYMM;
                } else {
			//プロパティの設定が誤っていた場合はyyyymmddとする
			_iDataType	= _DATA_TYPE_YMD;
		}
	}




	public String getDataType() {
		return dataType;
	}
	public void setMaxByteLength(int maxByteLength) {
		this.maxByteLength = maxByteLength;
	}
	public int getMaxByteLength() {
		return maxByteLength;
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
		String		sCode;

		sCode	= getValueText();
		if ( psText_Save.equals( sCode ) == false ) {
			return true;
		} else {
			return false;
		}
	}

	public void clearModified(
	) {
		psText_Save	= getValueText();
	}




}
